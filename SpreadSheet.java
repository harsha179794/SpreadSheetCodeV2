import cellPackage.Cell;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JLabel;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class SpreadSheet extends JFrame implements TableModelListener {

	private final String[] columns = {"$A","$B","$C","$D","$E","$F","$G","$H","$I"};
	Cell[][] data = {{new Cell("0","0"),new Cell("0","0"),new Cell("0","0"),new Cell("0","0"),new Cell("0","0"),new Cell("0","0"),
						new Cell("0","0"),new Cell("0","0"),new Cell("0","0")}};
	private JTable table;
	private final JToggleButton viewStateButton;
	private final JLabel lblViewState;
	private StateHandler handler;
	private static SpreadSheet frame;
	CellCareTaker cellCareTaker;
	JButton undoButton;
	JTableModel model;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new SpreadSheet();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public SpreadSheet() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 650, 450);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		viewStateButton = new JToggleButton("Value View");
		viewStateButton.setBounds(49, 171, 97, 25);
		contentPane.add(viewStateButton);

		setTableModel();
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(12, 47, 610, 87);
		contentPane.add(pane);
		
		lblViewState = new JLabel("Value View");
		lblViewState.setBounds(12, 13, 106, 21);
		contentPane.add(lblViewState);

		undoButton = new JButton("Undo");
		undoButton.setBounds(459, 171, 97, 25);
		contentPane.add(undoButton);
		
		stateChangeButton();
		undoButtonPress();
	}

	public void undoButtonPress(){

		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				cellCareTaker = ((ValueState) handler.getValueState()).getCellCareTaker();
				CellMemento cellMemento = cellCareTaker.revert();
				model.setValueAt(cellMemento.getValue(), cellMemento.getRow(),cellMemento.getCol());
				repaint();
			}
		});
	}

	private void stateChangeButton() {

		viewStateButton.addActionListener(e -> {

			if(handler.getState().toString().contains("ValueState")) {
				viewStateButton.setText("Equation View");
				lblViewState.setText("Equation View");
				handler.setState(handler.getEquationState());
			}
			else {
				viewStateButton.setText("Value View");
				lblViewState.setText("Value View");
				handler.setState(handler.getValueState());
			}
			repaint();
		});
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		repaint();
	}
	
	private void setTableModel() {
		this.handler = new StateHandler();
		model = new JTableModel(columns, data, handler);
		model.addTableModelListener(this);
		this.table = new JTable(model);
	}
}


