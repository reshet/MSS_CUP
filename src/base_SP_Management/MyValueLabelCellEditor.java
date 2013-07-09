package base_SP_Management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import com.explodingpixels.macwidgets.IAppWidgetFactory;

public class MyValueLabelCellEditor implements TableCellEditor
{
	public MyValueLabelCellEditor()
	{
		panel = new JPanel(new BorderLayout(2,2));
		panel.setPreferredSize(new Dimension(400,300));
		panel.setBackground(new Color(90,90,90));
		list = new JList(new DefaultListModel());
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setSelectedIndex(0);
		Border border = BorderFactory.createEmptyBorder(0,0,1,1);
		list.setBorder(border);
		list.setBackground(new Color(50,50,50));
		list.setForeground(new Color(210,210,220));
		
		JScrollPane scroller = new JScrollPane(list);
		scroller.setBackground(new Color(70,70,70));
		scroller.setAutoscrolls(true);
		IAppWidgetFactory.makeIAppScrollPane(scroller);
		scroller.setFocusable(true);
		scroller.setPreferredSize(new Dimension(370, 320));
		panel.add(scroller);
	}
	 private JPanel panel;
	 private JList list;
	 private String str;
		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
		str = (String)value;
		System.out.println(str);
		String [] strs = str.split(";");
		DefaultListModel model = new DefaultListModel();
		for(String st:strs)
		{
			model.addElement(st);
		}
		list.setModel(model);
		JDialog dlg = new JDialog();
		dlg.setTitle("ValueLabels");
		dlg.setModal(true);
		//dlg.setUndecorated(true);
		dlg.setSize(new Dimension(500,500));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dlg.setLocation((int)screenSize.getWidth()/2 - (int)dlg.getSize().getWidth()/2,
				(int)screenSize.getHeight()/2 - (int)dlg.getSize().getHeight()/2);
		dlg.add(panel);
		dlg.setVisible(true);
		return new JLabel(str);
	}
		@Override
		public void addCellEditorListener(CellEditorListener arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void cancelCellEditing() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public boolean isCellEditable(EventObject arg0) {
			// TODO Auto-generated method stub
			return true;
		}
		@Override
		public void removeCellEditorListener(CellEditorListener arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public boolean shouldSelectCell(EventObject arg0) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean stopCellEditing() {
			// TODO Auto-generated method stub
			return false;
		}
}
