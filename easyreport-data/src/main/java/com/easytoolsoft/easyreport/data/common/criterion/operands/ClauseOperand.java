package com.easytoolsoft.easyreport.data.common.criterion.operands;

public class ClauseOperand extends AbstractOperand {

    private SqlClause _sqlClause = SqlClause.None;

    public ClauseOperand(SqlClause sqlClause) {
        this._sqlClause = sqlClause;
    }

    @Override
    public String toString() {
        if (this.operands == null)
            this._sqlClause = SqlClause.None;

        return super.toString();
    }

    @Override
    protected String toExpression() {
        String strSqlClause = this._sqlClause == SqlClause.None ? "" : this._sqlClause.toString();
        return String.format("%s ", strSqlClause);
    }
}
