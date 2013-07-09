package app_service_SARS_Projects;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class MySimpleBooleanTableCellEditor extends AbstractCellEditor implements TableCellEditor
{
	/**
	 * 
	 */
	class MyCbox extends JCheckBox
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 5968064895292001183L;
		//public Boolean value;
		public MyCbox() {
		//	value = isSelected();
		}
		@Override
		public void setSelected(boolean b) {
			super.setSelected(b);
		//	value = isSelected();
		}
	}
	class AbstractActionListener implements ActionListener
	{
		@SuppressWarnings("unused")
		private JTable table;
		int row,col;
		public AbstractActionListener(JTable tbl,int col, int row) {
			table = tbl;
			this.row = row;
			this.col = col;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
		//	table.getModel().setValueAt(!(Boolean)table.getModel().getValueAt(row,col), row, col);
		}
	}
	private static final long serialVersionUID = -3642414324068332819L;
	public MySimpleBooleanTableCellEditor()
	{
		cbox = new MyCbox();
		cbox.setBackground(new Color(90,90,90));
		cbox.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	private MyCbox cbox;
	
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int col) {
		//cbox.setSelected((Boolean)value);
		cbox.setSelected((Boolean)value);
		cbox.addActionListener(new AbstractActionListener(table,col,row));
		if (isSelected){cbox.setBackground(table.getSelectionBackground());}
		else {cbox.setBackground(table.getBackground());}
		return cbox;
	}
	@Override
	public Object getCellEditorValue() {
		return cbox.isSelected();
	}
}