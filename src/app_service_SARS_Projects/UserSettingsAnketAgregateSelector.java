package app_service_SARS_Projects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import app_service_SARS_Panels.Setting_AssocPair;
import app_service_SARS_Panels.UserSettingsAbstractAsker;
import base_connectivity.MyBooleanTableCellRenderer;

import com.explodingpixels.macwidgets.IAppWidgetFactory;

public class UserSettingsAnketAgregateSelector extends UserSettingsAbstractAsker<Setting_AssocPair<String,ArrayList<Integer>>>{
	private JTable table;
	@SuppressWarnings("unused")
	private ArrayList<Setting_AssocPair<String, Integer>> ankets;
	private ArrayList<Integer> selected_arr;
	private JTextField agreg_name;
	private JLabel lbl=new JLabel("Имя агрегата:");
	private JPanel name_pnl;
	public UserSettingsAnketAgregateSelector(ArrayList<Setting_AssocPair<String, Integer>> ankets) {
		super("Выберите анкеты для создания агрегированного представления.");
		this.ankets = ankets;
		agreg_name = new JTextField("Новый агрегат");
		name_pnl= new JPanel();
		name_pnl.setLayout(new GridLayout(1,2));
		name_pnl.add(lbl);
		name_pnl.add(agreg_name);
		
		Object [][] data = new Object[ankets.size()][2];
		selected_arr = new ArrayList<Integer>();
		int ii=0;
		for(Setting_AssocPair<String, Integer> pair:ankets)
		{
			Object[] row = new Object[2];
			row[0] = new String(pair.getLeft()+"["+pair.getRight()+"]");
			selected_arr.add(pair.getRight());
			row[1] = new Boolean(false);
			data[ii] = row;
			ii++;
		}
		//ID="185" Description="Task description" Visability="0" Aviability="0" Accepted="0" Subscribed="0";
		String []columnNames = new String[2];
		columnNames[0]="Анкета";
		columnNames[1]="Выбрано";
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table = new JTable(model);
		TableColumnModel tcol= table.getColumnModel(); 
		JTableHeader header = new JTableHeader(tcol);
		table.setTableHeader(header);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroller = new JScrollPane(table);
		//table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setBackground(new Color(90,90,90));
		table.setForeground(new Color(190,190,255));
		table.setSelectionBackground(new Color(90,90,200));
		table.setSelectionForeground(new Color(200,200,90));
		scroller.setPreferredSize(new Dimension(300,300));
		scroller.setBackground(new Color(70,70,70));
		scroller.setForeground(new Color(60,60,60));
		IAppWidgetFactory.makeIAppScrollPane(scroller);
		
		TableCellEditor TCEditor =  new MySimpleBooleanTableCellEditor();
		TableCellRenderer TCRenderer = new MyBooleanTableCellRenderer();
    	//int class_count = class_desc.length;
    	//int col_count = tbl.getColumnCount();
        for (int i = 1; i < tcol.getColumnCount();i++)
		{
			tcol.getColumn(i).setCellEditor(TCEditor);
			tcol.getColumn(i).setCellRenderer(TCRenderer);
		}
        table.setColumnModel(tcol);
        panel.setLayout(new BorderLayout(3,3));
		panel.add(scroller);
		name_pnl.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
		panel.add(name_pnl, BorderLayout.SOUTH);
		//panel.add(new JLabel("This is DEMO SETTINGS LABEL!"));
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 4036252704300393935L;

	@Override
	public void processSettings() {
		ArrayList<Integer> _setting_arr = new ArrayList<Integer>();
		for(int i = 0; i < table.getModel().getRowCount();i++)
		{
			if((Boolean)table.getModel().getValueAt(i, 1))_setting_arr.add(selected_arr.get(i));
		}
		String name = agreg_name.getText();
		name+="(";
		for(Integer el:_setting_arr)
		{
			name+=el.toString()+",";
		}
		name =name.substring(0, name.length()-1);
		name+=")";
		_setting = new Setting_AssocPair<String, ArrayList<Integer>>(name, _setting_arr);
	}
}
