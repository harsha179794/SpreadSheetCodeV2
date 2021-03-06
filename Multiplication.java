package expressionValuation;

public class Multiplication implements Expression {

	Expression leftExpression;
	Expression rightExpression;
	
	public Multiplication(Expression leftExp, Expression rightExp){
		this.leftExpression = leftExp;
		this.rightExpression = rightExp;
	}
	
	@Override
	public double interpret() {
		
		return leftExpression.interpret() * rightExpression.interpret();	
	}
}
