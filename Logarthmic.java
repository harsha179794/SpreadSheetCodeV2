package expressionValuation;

public class Logarthmic implements Expression{

	Expression number;
	
	public Logarthmic(Expression right) {
		
		this.number = right;
	}
	
	@Override
	public double interpret() {
		
		double logNumber = number.interpret();
		return  (Math.log(logNumber)/Math.log(2));
	}
}
