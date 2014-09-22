package org.easyframework.report.data.criterion.operands;

import org.apache.commons.lang3.StringUtils;
import org.easyframework.report.data.SortType;

public class OrderByOperand extends AbstractOperand {

	private SortType _sortType;
	private String[] _columnNames;

	public OrderByOperand(SortType sortType, String... columnNames) {
		this._sortType = sortType;
		this._columnNames = columnNames;
	}

	@Override
	protected String toExpression() {
		if (this._columnNames == null || this._columnNames.length <= 0) {
			return "";
		}
		return String.format("ORDER BY %s %s ",
				StringUtils.join(this._columnNames, ','), this._sortType.toString());
	}
}
