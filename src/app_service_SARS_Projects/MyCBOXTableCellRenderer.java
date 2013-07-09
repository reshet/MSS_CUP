package app_service_SARS_Projects;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyCBOXTableCellRenderer implements TableCellRenderer
{
	public MyCBOXTableCellRenderer()
	{
		//C_Box = new JComboBox();
		//C_Box.setBackground(new Color(90,90,90));
		//C_Box.setAlignmentX(Component.CENTER_ALIGNMENT);
		//C_Box.setAlignmentY(Component.CENTER_ALIGNMENT);
	}
	// private JComboBox C_Box;
		@Override
		public Component getTableCellRendererComponent(JTable table, Object cbox,
            boolean isSelected, boolean hasFocus,
            int row, int column) 
		{
			JComboBox c_box = (JComboBox)cbox;
			if (isSelected){c_box.setBackground(table.getSelectionBackground());}
				else {c_box.setBackground(table.getBackground());}
			return c_box;
		}
}
