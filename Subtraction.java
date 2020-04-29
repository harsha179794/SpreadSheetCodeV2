package expressionValuation;

public class Subtraction implements Expression{
	
	Expression leftExpression;
	Expression rightExpression;
	
	public Subtraction(Expression leftExp, Expression rightExp) {
		this.leftExpression = leftExp;
		this.rightExpression = rightExp;
	}

	@Override
	public double interpret() {
		
		return leftExpression.interpret() - rightExpression.interpret();
	}

}
