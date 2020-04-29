package expressionValuation;
import cellPackage.Cell;
import java.util.Stack;

public class InterpreterPattern {
	
	private String expression;
	private Stack<Expression> expressionStack = new Stack<Expression>();
	private Expression rightExpression;
	private Expression leftExpression;
	private String[] columns;

	public InterpreterPattern(String exp) {
		
		this.expression = exp;
	}

	/*
	* This method takes the context object and expression string and interpret the values from references and calculate the value of the equation
	*/
	public double Interpret(String[] columns, Cell[][] data){

		this.columns = columns;
		parseExpression(data);
		double result;
		String[] tokensArray = expression.split(" ");
		
		for(String token:tokensArray) {
			Expression operator;
			if(isDoubleOperator(token)) {
				rightExpression =  expressionStack.pop();
				leftExpression =  expressionStack.pop();
				operator = getOperatorInstance(token);
				result = operator.interpret();
				expressionStack.push(new NumberExpression(result));
			}
			else if(isSingleOperator(token)) {
				rightExpression = expressionStack.pop();
				operator = getOperatorInstance(token);
				result = operator.interpret();
				expressionStack.push(new NumberExpression(result));
			}
			
			else {
				Expression integer = new NumberExpression(token);
				expressionStack.push(integer);
			}
		}
		return (expressionStack.pop()).interpret();
	}

	/*
	* To check whether it is double operator*/
	private boolean isDoubleOperator(String s) {
		return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
	}
	/*
	* To check whether it is urinary operator*/
	private boolean isSingleOperator(String s)
	{
		return s.equals("lg") || s.equals("sin");
	}

	/*
	*Get the correct operator instance and evaluate the expression accordingly.
	 */
	private Expression getOperatorInstance(String token) {

		Expression operation = null;
		switch (token) {
		case "+":
			operation = new Addition(leftExpression, rightExpression);
			break;
		case "-":
			operation = new Subtraction(leftExpression, rightExpression);
			break;
		case "*":
			operation = new Multiplication(leftExpression, rightExpression);
			break;
		case "/":
			operation = new Division(leftExpression,rightExpression);
			break;
		case "lg":
			operation = new Logarthmic(rightExpression);
			break;
		case "sin":
			operation = new Sine(rightExpression);
			break;
		}
		return operation;
	}

	/*
	* Parse the equations and replace the cell references with the values
	*/
	public void parseExpression(Cell[][] data){
		int index = -1;
		String[]  expressionTokens = expression.split(" ");
		StringBuilder numberBuilder = new StringBuilder();
		for(String token : expressionTokens){
			if(isNumber(token)) {
				numberBuilder.append(token);
				numberBuilder.append(" ");
			}else {
				index = getIndex(token);
				if (index != -1) {
					numberBuilder.append(data[0][index].getValue());
					numberBuilder.append(" ");
				}
				else{
					numberBuilder.append(token);
					numberBuilder.append(" ");
				}
			}
		}
		expression = numberBuilder.toString();
	}


	private int getIndex(String target)
	{
		for (int i = 0; i < this.columns.length; i++)
			if (this.columns[i].equals(target))
				return i;
		return -1;
	}

	private boolean isNumber(String token){
		try{
			Double.parseDouble(token);
			return true;
		}
		catch (Exception e){
			return false;
		}
	}
}
