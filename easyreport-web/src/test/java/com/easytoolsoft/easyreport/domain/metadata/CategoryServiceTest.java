package com.easytoolsoft.easyreport.domain.metadata;

import com.easytoolsoft.easyreport.data.metadata.dao.ICategoryDao;
import com.easytoolsoft.easyreport.data.metadata.po.Category;
import com.easytoolsoft.easyreport.domain.BaseDomainTest;
import com.easytoolsoft.easyreport.domain.metadata.service.impl.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.hamcrest.Matchers.equalTo;

@Slf4j
@PrepareForTest(CategoryService.class)
public class CategoryServiceTest extends BaseDomainTest {
    @Mock
    private ICategoryDao categoryDao;
    @InjectMocks
    private CategoryService categoryService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addTest() throws Exception {
        final Category record = Category.builder().id(0).parentId(9).build();
        PowerMockito.when(categoryDao.insert(record)).thenAnswer(invocationOnMock -> {
            record.setId(10);
            return 1;
        });
        PowerMockito.when(categoryDao.selectById(9)).thenReturn(record);
        PowerMockito.when(categoryService, "updateHasChild", record.getId(), true).thenReturn(1);
        categoryService.add(record);

        Assert.assertThat(record.getPath(), equalTo("9,10"));
    }
}
