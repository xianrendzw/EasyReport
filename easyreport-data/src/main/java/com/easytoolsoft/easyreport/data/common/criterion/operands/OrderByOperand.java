package com.easytoolsoft.easyreport.data.common.criterion.operands;

import org.apache.commons.lang3.StringUtils;

public class OrderByOperand extends AbstractOperand {
    private final String _sortType;
    private final String[] _columnNames;

    public OrderByOperand(String sortType, String... columnNames) {
        this._sortType = sortType;
        this._columnNames = columnNames;
    }

    @Override
    protected String toExpression() {
        if (this._columnNames == null || this._columnNames.length <= 0) {
            return "";
        }
        return String.format("ORDER BY %s %s ",
                StringUtils.join(this._columnNames, ','), this._sortType);
    }
}
