package base_connectivity;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyBooleanTableCellRenderer implements TableCellRenderer
{
	public MyBooleanTableCellRenderer()
	{
		C_Box = new JCheckBox();
		C_Box.setBackground(new Color(90,90,90));
		C_Box.setAlignmentX(Component.CENTER_ALIGNMENT);
		C_Box.setAlignmentY(Component.CENTER_ALIGNMENT);
	}
	 private JCheckBox C_Box;
		@Override
		public Component getTableCellRendererComponent(JTable table, Object cbox,
            boolean isSelected, boolean hasFocus,
            int row, int column) 
		{
			if (isSelected){C_Box.setBackground(table.getSelectionBackground());}
				else {C_Box.setBackground(table.getBackground());}
			C_Box.setSelected((Boolean)cbox);
			C_Box.setEnabled(true);
			return C_Box;
		}
}
