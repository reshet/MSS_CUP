package app_service_SARS_Projects;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyBTNTableCellRenderer implements TableCellRenderer
{
	public MyBTNTableCellRenderer()
	{
		//C_Box = new JComboBox();
		//C_Box.setBackground(new Color(90,90,90));
		//C_Box.setAlignmentX(Component.CENTER_ALIGNMENT);
		//C_Box.setAlignmentY(Component.CENTER_ALIGNMENT);
	}
	// private JComboBox C_Box;
		@Override
		public Component getTableCellRendererComponent(JTable table, Object btn,
            boolean isSelected, boolean hasFocus,
            int row, int column) 
		{
			JButton j_btn = (JButton)btn;
			//if (isSelected){c_box.setBackground(table.getSelectionBackground());}
			//	else {c_box.setBackground(table.getBackground());}
			return j_btn;
		}
}
