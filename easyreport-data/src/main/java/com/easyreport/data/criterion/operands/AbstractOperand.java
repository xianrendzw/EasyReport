package com.easyreport.data.criterion.operands;

import java.util.ArrayList;

public abstract class AbstractOperand {

	protected ArrayList<AbstractOperand> operands;

	public AbstractOperand append(AbstractOperand operand) {
		if (this.operands == null) {
			this.operands = new ArrayList<AbstractOperand>(5);
		}

		this.operands.add(operand);
		return this;
	}

	@Override
	public String toString() {
		if (this.operands == null) {
			return this.toExpression();
		}

		StringBuilder expr = new StringBuilder();
		expr.append(this.toExpression());
		for (AbstractOperand operand : this.operands) {
			expr.append(operand.toString());
		}

		return expr.toString();
	}

	protected abstract String toExpression();
}
