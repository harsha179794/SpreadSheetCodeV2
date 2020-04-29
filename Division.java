package expressionValuation;

public class Division implements Expression {
	
	Expression leftExpression;
	Expression rightExpression;
	
	public Division(Expression leftExp, Expression rightExp){
		this.leftExpression = leftExp;
		this.rightExpression = rightExp;
	}
	
	@Override
	public double interpret() {
		
		return  (int) leftExpression.interpret() / rightExpression.interpret();	
	}
}
