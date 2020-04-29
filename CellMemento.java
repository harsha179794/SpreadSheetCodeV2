public class CellMemento {

    private String equation;
    private String value;

    public int col;
    public int row;

    public CellMemento(String equation, String value, int row, int col){
            this.equation = equation;
            this.value = value;
            this.row = row;
            this.col= col;
    }

    public String getEquation() {
        return equation;
    }

    public String getValue() {
        return value;
    }

    public int getCol(){
        return col;
    }

    public int getRow(){
        return row;
    }

    public CellMemento save(){

        return new CellMemento(equation,value,row,col);
    }

    public void restore(CellMemento cell){
        this.value = cell.getValue();
        this.equation= cell.getEquation();
        this.row = cell.getRow();
        this.col = cell.getCol();
    }
}
