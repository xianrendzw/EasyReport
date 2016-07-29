package com.easytoolsoft.easyreport.data.common.criterion.operands;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Tom Deng
 */
public class GroupByOperand extends AbstractOperand {

    private final String[] _columnNames;

    public GroupByOperand(String... columnNames) {
        this._columnNames = columnNames;
    }

    @Override
    protected String toExpression() {
        if (this._columnNames == null || this._columnNames.length <= 0) {
            return "";
        }

        return String.format("GROUP BY %s ", StringUtils.join(_columnNames));
    }
}
