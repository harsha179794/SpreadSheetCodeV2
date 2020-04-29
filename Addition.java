package expressionValuation;

public class Addition implements Expression{
	
	Expression leftExpression;
	Expression rightExpression;
	
	public Addition(Expression leftExp, Expression rightExp) {
		this.leftExpression = leftExp;
		this.rightExpression = rightExp;
	}

	@Override
	public double interpret() {
		
		return leftExpression.interpret() + rightExpression.interpret();
	}
}
