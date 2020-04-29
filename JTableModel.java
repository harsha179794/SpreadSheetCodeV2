import cellPackage.Cell;

import javax.swing.table.AbstractTableModel;


public class JTableModel extends AbstractTableModel {
	
	private String[] columns;
	public Cell[][] data;
	private StateHandler handler;

	
	JTableModel(String[] columns, Cell[][] data, StateHandler handler){
		this.columns = columns;
		this.data = data;
		this.handler = handler;
	}

	public void setValueAt(Object value, int row, int col) {
		handler.getState().setCellData((String) value,row,col,data);
	}
	
	@Override
	public Object getValueAt(int row, int col) {

		return handler.getState().getCellData(this.data,row,col);
	}

	@Override
    public int getRowCount() {
		
        return data.length;
    }

    @Override
    public int getColumnCount() {
    	
        return this.columns.length;
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
   	/*
	 *TODO
	 * I have figure out how to use the state object and handle the view of the grid changing the values of the cells.
	 * I have to use handle request and compute the values accordingly.
	 */

}
