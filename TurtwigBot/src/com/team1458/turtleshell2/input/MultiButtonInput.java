package com.team1458.turtleshell2.input;

/**
 * @author asinghani
 */
public class MultiButtonInput extends SampleButtonInput {
	private Operator operator;
	private ButtonInput[] buttonInputs;

	public MultiButtonInput(Operator operator, ButtonInput... buttonInputs) {
		this.operator = operator;
		this.buttonInputs = buttonInputs;
	}

	@Override
	public boolean getButton() {
		boolean value = operator == Operator.AND ? true : false;
		for(ButtonInput buttonInput : buttonInputs) {
			switch(operator) {
				case AND:
					value = value && buttonInput.getButton();
					break;
				case OR:
					value = value || buttonInput.getButton();
					break;
				case XOR:
					value = value ^ buttonInput.getButton();
					break;
				case NAND:
					value = !(value && buttonInput.getButton());
					break;
			}
		}

		return value;
	}

	public enum Operator {
		AND, OR, XOR, NAND
	}
}
