package app_service_SARS_Projects;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class MyBTNTableCellEditor extends AbstractCellEditor implements TableCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3642414324068332819L;
	public MyBTNTableCellEditor()
	{
		//cbox = new JCheckBox();
		//cbox.setBackground(new Color(90,90,90));
		//cbox.setAlignmentX(Component.CENTER_ALIGNMENT);
		//reqHandlerAdmin = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
	}
	private JButton btn;
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int col) {
		btn = (JButton)value;
		return btn;
	}
	@Override
	public Object getCellEditorValue() {
		return btn;
	}
}