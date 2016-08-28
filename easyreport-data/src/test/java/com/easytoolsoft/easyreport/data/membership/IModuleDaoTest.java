package com.easytoolsoft.easyreport.data.membership;

import com.easytoolsoft.easyreport.data.BaseDaoTest;
import com.easytoolsoft.easyreport.data.membership.dao.IModuleDao;
import com.easytoolsoft.easyreport.data.membership.example.ModuleExample;
import com.easytoolsoft.easyreport.data.membership.po.Module;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.util.Date;
import java.util.List;

@Slf4j
@FixMethodOrder(MethodSorters.DEFAULT)
public class IModuleDaoTest extends BaseDaoTest<IModuleDao, Module, ModuleExample> {
    @Override
    protected Integer getId() {
        ModuleExample example = new ModuleExample();
        example.or().andNameEqualTo("DaoTestId" + 999);
        Module module = this.dao.selectOneByExample(example);
        return module == null ? 0 : module.getId();
    }

    @Override
    protected Module getPo(Integer id) {
        return Module.builder()
                .id(id)
                .parentId(0)
                .name("DaoTest" + id)
                .code(RandomStringUtils.randomAlphanumeric(10))
                .comment("DaoTest" + id)
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
        example.or().andNameEqualTo("DaoTest1000");
        return example;
    }

    @Override
    protected void assertThat(Module actualPo, Module expectPo) {
        Assert.assertThat(actualPo.getId(), IsNot.not(expectPo.getId()));
    }

    @Override
    protected List<Module> getRecords() {
        ModuleExample example = new ModuleExample();
        example.or().andFieldLike("name", "%DaoTest%");
        List<Module> list = this.dao.selectByExample(example);
        return CollectionUtils.isNotEmpty(list) ? list : super.getRecords();
    }
}
