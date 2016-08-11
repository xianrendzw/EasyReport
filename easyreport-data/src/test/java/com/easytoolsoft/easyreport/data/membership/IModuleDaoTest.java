package com.easytoolsoft.easyreport.data.membership;

import com.easytoolsoft.easyreport.data.BaseDaoTest;
import com.easytoolsoft.easyreport.data.membership.dao.IModuleDao;
import com.easytoolsoft.easyreport.data.membership.example.ModuleExample;
import com.easytoolsoft.easyreport.data.membership.po.Module;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

@Slf4j
public class IModuleDaoTest extends BaseDaoTest<IModuleDao, Module, ModuleExample> {
    @Override
    protected Module getPo(Integer id) {
        return Module.builder()
                .id(id)
                .parentId(0)
                .name("test_" + id)
                .code(RandomStringUtils.random(10))
                .comment("test_" + id)
                .gmtCreated(new Date())
                .gmtModified(new Date())
                .hasChild((byte) 0)
                .icon("test")
                .linkType((byte) 0)
                .params("")
                .path("")
                .sequence(10)
                .status((byte) 1)
                .target("")
                .url("")
                .build();
    }

    @Override
    protected ModuleExample getExample() {
        ModuleExample example = new ModuleExample();
        example.or().andNameEqualTo("test_1000");
        return null;
    }
}
