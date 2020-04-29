import cellPackage.Cell;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;


public class UnitTestCases {

    private JTableModel model;
    private StateHandler handler;
    private final String[] columns={"A","B","C","D","E","F","G","H","I"};
    private final Cell[][] data = {{new Cell("0","0"),new Cell("0","0"),new Cell("0","0"),new Cell("0","0"),new Cell("0","0"),new Cell("0","0"),
            new Cell("0","0"),new Cell("0","0"),new Cell("0","0")}};

    private CellCareTaker cellCareTaker;

    //Model object is initialized and state is being set.
    @BeforeEach
    public void setUp(){
        handler = new StateHandler();
        model = new JTableModel(columns,data,handler);
        cellCareTaker = new CellCareTaker();
    }


    //Entering the values at two cells and send the reference of cells into the third cell and performing addition operation
    @Test
    public void testAddition(){

        model.setValueAt("450",0,3);
        model.setValueAt("234",0,6);
        handler.setState(handler.getEquationState());
        model.setValueAt("$D $G +",0,1);
        assertEquals(data[0][1].getValue(),"684");
    }
    //Entering the values at two cells and send the reference of cells into the third cell and performing subtraction operation
    @Test
    public void testSubtraction(){

        model.setValueAt("124",0,1);
        model.setValueAt("900",0,2);
        handler.setState(handler.getEquationState());
        model.setValueAt("$B $C -",0,4);
       assertEquals(data[0][4].getValue(),"-776");
    }

    //Entering the values at two cells and send the reference of cells into the third cell and performing multiplication operation
    @Test
    public void testMultiplication(){

        model.setValueAt("100",0,1);
        model.setValueAt("8",0,2);
        handler.setState(handler.getEquationState());
        model.setValueAt("$B $C *",0,4);
        assertEquals(data[0][4].getValue(),"800");
    }


    //Entering the values at two cells and send the reference of cells into the third cell and performing division operation
    @Test
    public void testDivision(){

        model.setValueAt("45",0,8);
        model.setValueAt("9",0,7);
        handler.setState(handler.getEquationState());
        model.setValueAt("$I $H /",0,5);
        assertEquals(data[0][5].getValue(),"5");
    }


    //Entering the value in one cell and perform Log of value to base 2.
    @Test
    public void testLog(){

        model.setValueAt("45",0,6);
        handler.setState(handler.getEquationState());
        model.setValueAt("$G lg",0,7);
        assertEquals(data[0][7].getValue(),"5.49");
    }

    @Test
    public void testSine(){

        model.setValueAt("45",0,6);
        handler.setState(handler.getEquationState());
        model.setValueAt("$G sin",0,7);
        assertEquals(data[0][7].getValue(),"0.85");
    }

    // Test case to interpret and evaluate the complex expression.
    @Test
    public void testComplexCalculation(){

        model.setValueAt("1967",0,0);
        model.setValueAt("21",0,1);
        model.setValueAt("3",0,2);
        handler.setState(handler.getEquationState());
        model.setValueAt("$A $B + $C sin *",0,4);
        assertEquals(data[0][4].getValue(),"280.55");

    }

    // This test case to verify the dependency condition. Value of observer cell changes when it's observable cell value changes.
    @Test
    public void testDependecy(){

        model.setValueAt("1967",0,0);
        model.setValueAt("21",0,1);
        model.setValueAt("3",0,2);
        handler.setState(handler.getEquationState());
        model.setValueAt("$A $B + $C sin *",0,4);
        assertEquals(data[0][4].getValue(),"280.55");
        model.setValueAt("5382",0,1);// Observable value changed in Cell 0.
        assertEquals(data[0][4].getValue(),"1037.09");
    }

    // This test case to verify the dependency condition. When the observer equation is modified/changed to value then it's observables delete this observer.
    @Test
    public void testRemoveDependency(){

        model.setValueAt("1967",0,0);
        model.setValueAt("21",0,1);
        model.setValueAt("3",0,2);
        handler.setState(handler.getEquationState());
        model.setValueAt("$A $B + $C sin *",0,4);
        assertEquals(data[0][4].getValue(),"280.55");

         /* State is in equation mode and observer equation is changed in Cell 4. Dependencies changes/deleted
                Now any change in any observable cell values does not effect this cell value
        */
        model.setValueAt("211",0,4);
        assertEquals(data[0][4].getValue(),"211");
    }

    //Test Method for Circular dependency.
    @Test
    public void testCircularDependency(){

        /*
         * In the equation state, created the circular dependency cell 0 dependent on cell 1 and vice versa. So the value of each cell is set to Error.

         */
        handler.setState(handler.getEquationState());
        model.setValueAt("$B",0,0);
        model.setValueAt("$A",0,1);
        assertTrue(data[0][0].getValue().contains("Error"));
        assertTrue(data[0][1].getValue().contains("Error"));
        //To test the equations of the cells are un changed and contains the same formula.
        assertEquals("$B", data[0][0].getEquation());
        assertEquals("$A", data[0][1].getEquation());
    }

    /*
    * Test method to simulate and verify the undo mechanism.
    * */
    @Test
    public void testUndoValue(){

        /*
        * Intially the cell 8 is set to 45 and saved in the caretaker.
        * */
        handler.setState(handler.getValueState());
        model.setValueAt("45",0,8);
        cellCareTaker.save(new CellMemento(model.data[0][8].getEquation(),model.data[0][8].getValue(),0,8));
        // Value 90 is now set to the same cell 8.
        model.setValueAt("9",0,8);
        CellMemento cellMemento =  cellCareTaker.revert();
        assertEquals(cellMemento.getValue(),"45");
    }

    @Test
    public void testUndoEquation(){

        /*
         * Initially the cell 8 is set to equation $B and saved in the caretaker.
         * */
        handler.setState(handler.getEquationState());
        model.setValueAt("$B",0,8);
        cellCareTaker.save(new CellMemento(model.data[0][8].getEquation(),model.data[0][8].getValue(),0,8));
        // Equation $A $C + is now set to the same cell 8.
        model.setValueAt("$A $C +",0,8);
        CellMemento cellMemento =  cellCareTaker.revert();
        assertEquals(cellMemento.getEquation(),"$B");
    }

}
