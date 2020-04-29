package cellPackage;

import expressionValuation.InterpreterPattern;

import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

public class Cell extends Observable implements Observer {

    private String value ;
    private String equation;
    private int row;
    private int col;
    private InterpreterPattern ip;
    private String[] columns = {"$A","$B","$C","$D","$E","$F","$G","$H","$I"};


    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public Cell(String value,String equation) {
        this.value = value;
        this.equation = equation;
    }

    public String getValue() {
        return value;
    }


    public void setValue(String value, Cell[][] data,int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;
        if(tryParseDouble(value)){
            data[row][col].value  = value;
            setChanged();
            notifyObservers(data);
        }
        else if (value.contains("Error"))
            setErrorValue(value,data);
        else{
            setObserverValue(data);
        }
    }
    @Override
    public void update(Observable o, Object arg) {

        setObserverValue((Cell[][]) arg);
    }

    public void setErrorValue(String value,Cell[][] data){
        data[row][col].value = value;
    }

    public void setObserverValue(Cell[][] data){

        ip = new InterpreterPattern(data[row][col].getEquation());
        Object result = ip.Interpret(columns,data);
        result = new DecimalFormat("#.##").format(result);
        data[row][col].value = (String) result;
        setChanged();
        notifyObservers(data);
    }
    public boolean tryParseDouble(String value){

        try{
            Double.parseDouble(value);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
