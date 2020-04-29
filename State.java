import cellPackage.Cell;

public interface State {

	void setCellData(String content, int row, int col, Cell[][] data);
	
	void setState();

	String getCellData(Cell[][] data, int row, int col);

}
