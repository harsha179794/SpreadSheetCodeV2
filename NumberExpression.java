package expressionValuation;

public class NumberExpression implements Expression {
	
	double number;

	public NumberExpression(double i) {
		number = i;
	}

	public NumberExpression(String s) {
		number = Double.parseDouble(s);
	}

	@Override
	public double interpret() {
		return number;
	}
}
