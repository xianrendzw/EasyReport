package com.easytoolsoft.easyreport.web.controller.report;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.easytoolsoft.easyreport.meta.domain.DataSource;
import com.easytoolsoft.easyreport.meta.domain.example.DataSourceExample;
import com.easytoolsoft.easyreport.meta.service.DataSourceService;
import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import com.easytoolsoft.easyreport.support.annotation.OpLog;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import com.easytoolsoft.easyreport.web.controller.common.BaseController;
import com.easytoolsoft.easyreport.web.model.DataGridPager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报表数据源控制器
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@RestController
@RequestMapping(value = "/rest/report/ds")
public class DataSourceController
    extends BaseController<DataSourceService, DataSource, DataSourceExample, Integer> {

    @GetMapping(value = "/listAll")
    @OpLog(name = "获取所有数据源")
    @RequiresPermissions("report.ds:view")
    public List<DataSource> listAll() {
        return this.service.getAll().stream()
            .map(x -> DataSource.builder()
                .id(x.getId())
                .uid(x.getUid())
                .name(x.getName())
                .build())
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取数据源列表")
    @RequiresPermissions("report.ds:view")
    public Map<String, Object> list(final DataGridPager pager, final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<DataSource> list = this.service.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "新增数据源")
    @RequiresPermissions("report.ds:add")
    public ResponseResult add(final DataSource po) {
        po.setGmtCreated(new Date());
        po.setUid(UUID.randomUUID().toString());
        this.service.add(po);
        return ResponseResult.success("");
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑数据源")
    @RequiresPermissions("report.ds:edit")
    public ResponseResult edit(final DataSource po) {
        this.service.editById(po);
        return ResponseResult.success("");
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除数据源")
    @RequiresPermissions("report.ds:remove")
    public ResponseResult remove(final Integer id) {
        this.service.removeById(id);
        return ResponseResult.success("");
    }

    @PostMapping(value = "/testConnection")
    @OpLog(name = "测试数据源")
    @RequiresPermissions("report.ds:view")
    public ResponseResult testConnection(final String driverClass, final String url, final String pass,
                                         final String user) {
        if (this.service.testConnection(driverClass, url, user, pass)) {
            return ResponseResult.success("");
        }
        return ResponseResult.failure(20000, "数据源测试失败", "");
    }

    @PostMapping(value = "/testConnectionById")
    @OpLog(name = "测试数据源")
    @RequiresPermissions("report.ds:view")
    public ResponseResult testConnection(final Integer id) {
        final DataSource dsPo = this.service.getById(id);
        final boolean testResult = this.service.testConnection(
            dsPo.getDriverClass(),
            dsPo.getJdbcUrl(),
            dsPo.getUser(), dsPo.getPassword());

        if (testResult) {
            return ResponseResult.success("");
        }
        return ResponseResult.failure(10005, "数据源测试失败", "");
    }
}