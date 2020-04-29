import cellPackage.Cell;


public class ValueState implements State {
	
	private StateHandler handler;
	public CellCareTaker cellCareTaker;
	CellMemento cellMemento;

	public ValueState(StateHandler handler) {

		this.handler = handler;
		cellCareTaker = new CellCareTaker();
	}

	@Override
	public void setCellData(String value, int row, int col, Cell[][] data) {

		cellMemento = new CellMemento(data[row][col].getValue(),data[row][col].getEquation(),row,col);
		cellCareTaker.save(cellMemento);
		data[row][col].setValue(value,data,row,col);

	}

	public CellCareTaker getCellCareTaker() {

		return cellCareTaker;
	}

	@Override
	public void setState() {
		handler.setState(handler.getEquationState());
	}

	@Override
	public String getCellData(Cell[][] data,int row,int col) {
		return data[row][col].getValue();
	}
}
