package com.easytoolsoft.easyreport.web.viewmodel;

import com.easytoolsoft.easyreport.data.common.helper.PageInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        return new PageInfo((this.page - 1) * this.rows,
                this.rows, this.sort, this.order);
    }
}
