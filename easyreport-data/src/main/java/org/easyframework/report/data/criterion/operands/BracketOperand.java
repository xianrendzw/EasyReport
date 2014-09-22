package org.easyframework.report.data.criterion.operands;

public class BracketOperand extends AbstractOperand {

	private Bracket bracket;

	public BracketOperand(Bracket bracket) {
		this.bracket = bracket;
	}

	@Override
	protected String toExpression() {
		switch (this.bracket) {
		case Left:
			return "(";
		case Rgiht:
			return ")";
		default:
			return "";
		}
	}
}
