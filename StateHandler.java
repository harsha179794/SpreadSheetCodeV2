public class StateHandler {
	
	private State valueView;
	private State equationView;
	private State state;
	
	public StateHandler() {
		
		valueView = new ValueState(this);
		equationView = new EquationState(this);
		state = valueView;
	}
	
	public State getState(){
		
		return state;
	}
	
	public State getEquationState(){
		
		return  equationView;
		
	}
	
	public State getValueState(){
		
		return  valueView;
	}
	
	public void setState(State state) {
		this.state = state;
		
	}
}
