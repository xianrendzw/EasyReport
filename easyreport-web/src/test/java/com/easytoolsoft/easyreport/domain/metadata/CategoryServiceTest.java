package com.easytoolsoft.easyreport.domain.metadata;

import com.easytoolsoft.easyreport.data.metadata.dao.ICategoryDao;
import com.easytoolsoft.easyreport.data.metadata.po.Category;
import com.easytoolsoft.easyreport.domain.BaseDomainTest;
import com.easytoolsoft.easyreport.metadata.service.impl.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

@Slf4j
public class CategoryServiceTest extends BaseDomainTest {
    @InjectMocks
    private CategoryService mockCategoryService;
    @Mock
    private ICategoryDao categoryDao;

    @Test
    public void addTest() {
        Category record = Category.builder().id(10).parentId(9).build();
        when(categoryDao.selectById(9)).thenReturn(record);
        mockCategoryService.add(record);
        Assert.assertThat(record.getPath(), IsEqual.equalTo("9,10"));
    }
}
