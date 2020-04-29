package expressionValuation;

import static java.lang.StrictMath.PI;

public class Sine implements Expression {

	Expression number ;
	
	public Sine(Expression number) {
		
		this.number = number;
	}
	
	@Override
	public double interpret() {

		double sinNumber  = number.interpret();
		return (Math.sin((sinNumber)));
	}
}
