import java.util.Stack;

public class CellCareTaker {

    private Stack<CellMemento> cellHistroy = new Stack<>();

    public void save(CellMemento cell){
        cellHistroy.push(cell.save());
    }

    public CellMemento revert(){
        return cellHistroy.pop();
    }
}


