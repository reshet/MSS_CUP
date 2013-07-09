package base_gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractCellEditor;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;


public class ListTableEditor extends AbstractCellEditor implements TableCellEditor 
{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = -1894999677682735949L;
	// This is the component that will handle the editing of the cell value 
	public ListTableEditor()
	{
		super();
		//((JComboBox)component)
		component.setBackground(new Color(22,22,160));
		//component.setForeground(new Color(160,22,22));
		component.setFont(new Font("Helvetica",3,12));
	}
	ComboBoxModel model = new DefaultComboBoxModel(new String []{"One","Two","Three"});
	JComponent component = new JComboBox(model); 
	// This method is called when a cell value is edited by the user.
	public JComponent getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex)
	{ 
		// 'value' is value contained in the cell located at (rowIndex, vColIndex)
		if (isSelected)
		{
			JDialog dl = new JDialog();
			dl.setSize(200,200);
			dl.setTitle("DKwjet4 ");
			dl.setVisible(true);
		}
		// Configure the component with the specified value
		//((JList)component).set((String)value); 
		// Return the configured component 
		return component; 
	} 
	// This method is called when editing is completed. 
	// It must return the new value to be stored in the cell. 
	public Object getCellEditorValue() 
	{ 
		return ((JComboBox)component).getSelectedItem(); 
	} 
}