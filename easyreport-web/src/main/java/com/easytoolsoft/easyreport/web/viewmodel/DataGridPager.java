package com.easytoolsoft.easyreport.web.viewmodel;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * easyui gridview控件分页类 <br>
 */
@Data
@NoArgsConstructor
public class DataGridPager {
    private Integer page = 1;
    private Integer rows = 30;
    private String sort = "id";
    private String order = "desc";

    public PageInfo toPageInfo() {
        return this.toPageInfo("");
    }

    public PageInfo toPageInfo(String tablePrefix) {
        String prefix = StringUtils.defaultString(tablePrefix, "").trim();
        String name = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(this.sort), '_');
        String sorter = prefix + StringUtils.defaultString(name, "");
        return new PageInfo((this.page - 1) * this.rows,
                this.rows, sorter, this.order);
    }
}
