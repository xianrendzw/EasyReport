package com.easytoolsoft.easyreport.service;

import com.easytoolsoft.easyreport.dao.ReportingDao;
import com.easytoolsoft.easyreport.data.jdbc.BaseService;
import com.easytoolsoft.easyreport.po.ReportingPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 报表树服务类
 */
@Service
public class ReportingTreeService extends BaseService<ReportingDao, ReportingPo> {
    @Resource
    private ReportingService reportingService;

    @Autowired
    public ReportingTreeService(ReportingDao dao) {
        super(dao);
    }

    public boolean editNode(ReportingPo entity) {
        return this.dao.updateTreeNode(entity) > 0;
    }

    public void dragNode(int sourceId, int targetId, int sourcePid) {
        // 修改source节点的pid与path，hasChild值
        this.dao.updatePid(sourceId, targetId);
        this.reportingService.setHasChild(targetId);
        this.dao.updatePath(sourceId, this.reportingService.getPath(targetId, sourceId));
        // 递归修改source节点的所有子节点的path值
        this.rebuildPathById(sourceId);
        // 修改source节点的父节点hasChild值
        this.dao.updateHasChild(sourcePid, this.dao.countChildBy(sourcePid) > 0);
    }

    public ReportingPo pasteNode(int sourceId, int targetId, String createUser) {
        ReportingPo entity = this.dao.queryByKey(sourceId);
        int count = this.dao.countChildBy(targetId, entity.getName());
        if (count > 0) {
            entity.setName(String.format("%s_复件%s", entity.getName(), count));
        }
        entity.setUid(UUID.randomUUID().toString());
        entity.setPid(targetId);
        entity.setCreateUser(createUser);
        entity.setId(this.dao.insertWithId(entity));
        return this.dao.queryByKey(entity.getId());
    }

    public void rebuildPathById(int id) {
        List<ReportingPo> enities = this.dao.queryByPid(id);
        for (ReportingPo entity : enities) {
            String path = this.reportingService.getPath(entity.getPid(), entity.getId());
            this.dao.updateHasChild(entity.getPid(), path.split(",").length > 1);
            this.dao.updatePath(entity.getId(), path);
            this.rebuildPathById(entity.getId());
        }
    }

    public void cloneNode(int sourceId, int targetId, int dsId) {
        List<ReportingPo> children = this.dao.queryAllChild(sourceId);
        for (ReportingPo child : children) {
            if (child.getId() == sourceId) {
                child.setPid(targetId);
                child.setDsId(dsId);
                child.setUid(UUID.randomUUID().toString());
                int newId = this.addWithId(child);
                this.recursionCloneNode(children, newId, child.getId(), dsId);
            }
        }
    }

    private void recursionCloneNode(List<ReportingPo> children, int newPid, int srcPid, int dsId) {
        for (ReportingPo child : children) {
            if (child.getPid() == srcPid) {
                child.setPid(newPid);
                child.setDsId(dsId);
                child.setUid(UUID.randomUUID().toString());
                int newId = this.addWithId(child);
                this.recursionCloneNode(children, newId, child.getId(), dsId);
            }
        }
    }

    public void rebuildAllPath() {
        List<ReportingPo> entities = this.dao.query();
        this.rebuildPath(entities);
    }

    private void rebuildPath(List<ReportingPo> entities) {
        for (ReportingPo entity : entities) {
            String path = this.reportingService.getPath(entity.getPid(), entity.getId());
            this.dao.updateHasChild(entity.getPid(), path.split(",").length > 1);
            this.dao.updatePath(entity.getId(), path);
        }
    }
}