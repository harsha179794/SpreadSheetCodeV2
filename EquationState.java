import cellPackage.Cell;

import java.util.ArrayList;
import java.util.List;

public class EquationState implements State {

	private StateHandler handler;
	String[] columns= {"$A","$B","$C","$D","$E","$F","$G","$H","$I"};
	Graph graph;

	public EquationState(StateHandler handler) {
		
		this.handler = handler;
		graph = new Graph(9);
	}

	@Override
	public void setCellData(String value, int row, int col, Cell[][] data) {
		int parentIndex=-1;
		String[] tokens;
		String equation;
		String setValue="";

		boolean isValue=tryParseDouble(value);
		if(!isValue) {
			tokens = (value).split(" ");
			if(!checkCycle(tokens,row,col)) {
				for (String token : tokens) {
					parentIndex = getIndex(token);
					if (parentIndex != -1) {
						data[row][parentIndex].addObserver(data[row][col]);
					}
					setValue = value;
				}
			}
			else {
				setValue = "Error";
			}
		}
		else{
			equation = data[row][col].getEquation();
			tokens = (equation).split(" ");
			for (String token:tokens){
				parentIndex = getIndex(token);
				if (parentIndex != -1) {
					data[row][parentIndex].deleteObserver(data[row][col]);
				}
			}
			setValue = value;
		}
		data[row][col].setEquation(value);
		data[row][col].setValue(setValue,data,row,col);
	}

	private boolean checkCycle(String[] tokens, int row, int col){

		int parentIndex = -1;
		List<Integer> connections = new ArrayList<>();
		for(String token: tokens){
			parentIndex = getIndex(token);
			if(parentIndex!=-1){
				connections.add(parentIndex);
			}
		}
		for (int parentVertice: connections) {
			graph.addEdge(parentVertice,col);
		}
		if(graph.isCyclic())
			return true;
		return false;
	}

	private int getIndex(String target)
	{
		for (int i = 0; i < this.columns.length; i++)
			if (this.columns[i].equals(target))
				return i;
		return -1;
	}

	private boolean tryParseDouble(String value){
		try{
			Double.parseDouble(value);
			return true;
		}
		catch (Exception e){
			return false;
		}
	}

	@Override
	public void setState() {
		handler.setState(handler.getValueState());
	}

	@Override
	public String getCellData(Cell[][] data,int row, int col) {

	 	if(data[row][col].getEquation().contains("0"))
	 		return data[row][col].getValue();
		return data[row][col].getEquation();

	}

}
