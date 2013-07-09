package app_service_SARS_Projects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import app_service_SARS_Panels.Setting_AssocPair;
import app_service_SARS_Panels.UserSettingsAbstractAsker;
import app_service_SARS_Panels.WaitDialog;
import app_service_quiz.TasksTablePanel;
import base_connectivity.MyBooleanTableCellRenderer;

import com.explodingpixels.macwidgets.IAppWidgetFactory;

public class UserSettingsAnketPanelMatcher extends UserSettingsAbstractAsker<DefaultTableModel>{
	private JTable table;
	private DefaultTableModel _Model;
	private ArrayList<Setting_AssocPair<String, Integer>> panels,ankets;
	
	public UserSettingsAnketPanelMatcher(ArrayList<Setting_AssocPair<String, Integer>> panels,ArrayList<Setting_AssocPair<String, Integer>> ankets) {
		super("Установите соответсвия между панелями и анкетами по данному проекту.");
		this.ankets = ankets;
		this.panels = panels;
		Object [][] data = new Object[ankets.size()][panels.size()+1];
		Object [][] data_ans = new Object[ankets.size()][panels.size()+1];
		int ii=0;
		for(Setting_AssocPair<String, Integer> pair:ankets)
		{
			Object[] row = new Object[panels.size()+1];
			row[0] = new String(pair.getLeft()+"["+pair.getRight()+"]");
			Object[] row_ans = new Object[panels.size()+1];
			row_ans[0] = new String(String.valueOf(pair.getRight()));
			for(int j = 1; j < panels.size()+1;j++)
			{
				row[j] = new Boolean(false);
				row_ans[j] = new Boolean(false);
			}
			data[ii] = row;
			data_ans[ii] = row_ans;
			ii++;
		}
		//ID="185" Description="Task description" Visability="0" Aviability="0" Accepted="0" Subscribed="0";
		String []columnNames = new String[panels.size()+1];
		columnNames[0]="Анкета";
		String []columnNames_ans = new String[panels.size()+1];
		columnNames_ans[0]="Анкета";
		int j = 1;
		for(Setting_AssocPair<String, Integer> pair:panels)
		{
			columnNames[j] = pair.getLeft()+"["+pair.getRight()+"]";
			columnNames_ans[j] = String.valueOf(pair.getRight());
			++j;
		}
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		_Model = new DefaultTableModel(data_ans, columnNames_ans);
		askServerSubscriptionState(model);
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
		//panel.add(new JLabel("This is DEMO SETTINGS LABEL!"));
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 4036252704300393935L;

	@Override
	public void processSettings() {
		for(int i = 0; i < table.getModel().getRowCount();i++)
		{
			for(int j = 1; j < table.getModel().getColumnCount();j++)
			{
				_Model.setValueAt(table.getModel().getValueAt(i, j), i, j);
			}
		}
		_setting = _Model;
	}
	private void askServerSubscriptionState(DefaultTableModel model)
	{
		WaitDialog dlg = new WaitDialog("Запрос данных о соответствиях с сервера...");
		int panel_col = 1;
		for (Setting_AssocPair<String, Integer> panel:panels)
		{
			dlg.setProgress(panel_col/panels.size()*100);
			int gr_ID = panel.getRight();
			TasksTablePanel t_table = new TasksTablePanel(gr_ID);
			t_table.update_plain();
			ArrayList<Integer> tasks_forproject = new ArrayList<Integer>();		
			ArrayList<Integer> tasks_forgroup = new ArrayList<Integer>();
			ArrayList<Integer> tasks_forgroup_inproject = new ArrayList<Integer>();
			for(int i = 0; i < t_table.getTable().getModel().getRowCount();i++)
			{
				tasks_forgroup.add(Integer.parseInt((String)t_table.getTable().getModel().getValueAt(i, 1)));
			}
			for(Setting_AssocPair<String, Integer> elem:ankets)
			{
				tasks_forproject.add(elem.getRight());
			}
			for(Integer elem:tasks_forgroup)
			{
				if(tasks_forproject.contains(elem))tasks_forgroup_inproject.add(elem);
			}
			for(int i = 0; i <model.getRowCount();i++)
			{
				int anketID = Integer.parseInt((String)_Model.getValueAt(i, 0));
				if (tasks_forgroup_inproject.contains(anketID))model.setValueAt(new Boolean(true), i, panel_col);
			}
			panel_col++;
		}
		dlg.setProgress(100);
		dlg.getJDialog().setVisible(false);
		//table.setModel(model);
	}
}
