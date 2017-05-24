package com.easytoolsoft.easyreport.web.model;

import com.easytoolsoft.easyreport.mybatis.pager.PageInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * easyui gridview控件分页类 <br>
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Data
@NoArgsConstructor
public class DataGridPager {
    private final Integer page = 1;
    private final Integer rows = 50;
    private final String sort = "id";
    private final String order = "desc";

    public PageInfo toPageInfo() {
        return this.toPageInfo("");
    }

    public PageInfo toPageInfo(final String tablePrefix) {
        final String prefix = StringUtils.defaultString(tablePrefix, "").trim();
        final String name = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(this.sort), '_');
        final String sortField = prefix + StringUtils.defaultString(name, "").toLowerCase();
        return new PageInfo((this.page - 1) * this.rows,
            this.rows, sortField, this.order);
    }
}
