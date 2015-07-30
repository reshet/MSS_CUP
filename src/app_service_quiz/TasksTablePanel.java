package app_service_quiz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_TableFiller;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;
import base_gui.ToolMainWidget;

import com.explodingpixels.macwidgets.HudWidgetFactory;
import com.explodingpixels.macwidgets.IAppWidgetFactory;

public class TasksTablePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3139497629474804219L;
	private JTable table;
	private DefaultTableModel model;
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}
	private JList linked_list;
	public TaskEditingPanel getLinked_editor() {
		return linked_editor;
	}
	public void setLinked_editor(TaskEditingPanel linkedEditor) {
		linked_editor = linkedEditor;
	}
	private TaskEditingPanel linked_editor;
	private MSS_RQ_TableDescriptor QuizTDesc;
	private MSS_RQ_XMLtoTableDescriptor QuizT_XML_Desc;
	private MSS_RQ_TableFiller QuizUpdater;
	private MSS_RQ_Admin reqHandlerAdmin;
	private JPanel toolbar;
	private int idGroup;
	private String[] columnNames;
	private Thread update_thr;
	private Map<Integer,Integer> interviews_collected;
	//private String URL;
	//private TableColumnModel col_model;
	public void setGroupID(int groupID) {
		this.idGroup = groupID;
		//toolbar.setGroupID(groupID);
	}
	public TasksTablePanel(int idGroup)
	{
		//this.URL = ToolMainWidget.URL;
		this.idGroup = idGroup;
		this.setLayout(new BorderLayout());
		reqHandlerAdmin = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
		Object [][] data = {
				{"2342342","32534534","242353rwerfsdf",new Boolean(false),"dgdg,drgert34"}
				};
		//ID="185" Description="Task description" Visability="0" Aviability="0" Accepted="0" Subscribed="0";
		columnNames = new String[]{"№", "ID", "Опис","Видимість","Доступність","Пройдено","Підписано"};
		model = new DefaultTableModel(data, columnNames);
		//MacServiceTable = MacWidgetFactory.createITunesTable(model);
		//JScrollPane scrollPane = new JScrollPane(MacServiceTable);
		//MacServiceTable.setFillsViewportHeight(true);
		//IAppWidgetFactory.makeIAppScrollPane(scrollPane);
		//TableColumnModel tcol= MacServiceTable.getColumnModel(); 
		//JTableHeader header = new JTableHeader(tcol);
		//MacServiceTable.setTableHeader(header);
		table = new JTable(model);
		TableColumnModel tcol= table.getColumnModel(); 
		JTableHeader header = new JTableHeader(tcol);
		table.setTableHeader(header);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroller = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setBackground(new Color(90,90,90));
		table.setForeground(new Color(190,190,255));
		table.setSelectionBackground(new Color(90,90,200));
		table.setSelectionForeground(new Color(200,200,90));
		scroller.setPreferredSize(new Dimension(300,300));
		scroller.setBackground(new Color(70,70,70));
		scroller.setForeground(new Color(60,60,60));
		IAppWidgetFactory.makeIAppScrollPane(scroller);
		
		table.getModel().addTableModelListener(new TableModelListener() {		
			@Override
			public void tableChanged(TableModelEvent e) {
				//int row = e.getFirstRow();
		        //int column = e.getColumn();
		        //TableModel model = (TableModel)e.getSource();
		       // String columnName = model.getColumnName(column);
		       // Object data = model.getValueAt(row, column);
			}
		});
		update();
		toolbar = new JPanel();
		final JCheckBox inspect = new JCheckBox("Отслеживать");
		inspect.setOpaque(false);
		JLabel period_l = new JLabel(" период обновления: ");
		Color clr = new Color(200,255,200);
		inspect.setForeground(clr);
		period_l.setForeground(clr);
		final JTextField period = new JTextField("10");
		period.setSize(40,30);
		JButton upd = HudWidgetFactory.createHudButton("Оновити!");
		upd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			update();	
			}
		});
		interviews_collected = new HashMap<Integer, Integer>();
		
		update_thr = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(!Thread.currentThread().isInterrupted())
				{
					int delay = 10;
					try{
						delay = Integer.parseInt(period.getText());
					}
					catch (NumberFormatException e) {
						e.printStackTrace();
					}
					if(inspect.isSelected())
					{
						update_plain();
					}
					try {
						Thread.sleep(delay*1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		update_thr.start();
		toolbar.setPreferredSize(new Dimension(400,40));
		toolbar.setBackground(new Color(60,60,60));
		toolbar.add(upd);
		toolbar.add(inspect);
		toolbar.add(period_l);
		toolbar.add(period);
		
		//toolbar.add(m_card);
		this.add(scroller);
		this.add(toolbar,BorderLayout.SOUTH);
	}
	public int getSelectedTask()
	{
		int sel_id = 0;
		int selected = -1;
		try 
		{
			selected = table.getSelectedRow();
		} catch (Exception e){}
		if (selected != -1)
		{
			sel_id = Integer.parseInt((String)table.getValueAt(selected,1));
		}
		return sel_id;
	}
	public String getSelectedTaskName()
	{
		String sel_nm = "";
		int selected = -1;
		try 
		{
			selected = table.getSelectedRow();
		} catch (Exception e){}
		if (selected != -1)
		{
			sel_nm = (String)table.getValueAt(selected,2);
		}
		return sel_nm;
	}
	public void update()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				QuizTDesc = new MSS_RQ_TableDescriptor(columnNames, 
						new Class[]{Integer.class, Integer.class, String.class,Boolean.class,Boolean.class,Integer.class,Integer.class});
				QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"№","ID","Description","Visability","Aviability","Accepted","Subscribed"});
				QuizUpdater = new MSS_RQ_TableFiller(QuizTDesc,QuizT_XML_Desc);
				reqHandlerAdmin = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
				
				{
					int selected = -1;
					try 
					{
						selected = table.getSelectedRow();
					} catch (Exception e){}
					if (selected != -1)
					{
						MSS_RQ_Request.http_request(reqHandlerAdmin.geatherStatistics(Integer.parseInt((String)table.getValueAt(selected,1))),ToolMainWidget.URL);
					}
					
					
				}
				
				String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.listTasksOwnW(idGroup),ToolMainWidget.URL);
				QuizUpdater.updateData(xml_arr_ans);
				QuizUpdater.fillTable(table);
			
			}
		}).start();
	}
	public void update_plain()
	{
		QuizTDesc = new MSS_RQ_TableDescriptor(columnNames, 
				new Class[]{Integer.class, Integer.class, String.class,Boolean.class,Boolean.class,Integer.class,Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"№","ID","Description","Visability","Aviability","Accepted","Subscribed"});
		QuizUpdater = new MSS_RQ_TableFiller(QuizTDesc,QuizT_XML_Desc);
		String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.listTasksOwnW(idGroup),ToolMainWidget.URL);
		QuizUpdater.updateData(xml_arr_ans);
		QuizUpdater.fillTable(table);	
		
		for(int i = 0;i < table.getRowCount();i++)
		{
			int int_ID = Integer.parseInt((String)table.getModel().getValueAt(i, 1));
			int int_collected = Integer.parseInt((String)table.getModel().getValueAt(i, 5));
			if (!interviews_collected.containsKey(int_ID))
			{
				interviews_collected.put(int_ID, int_collected);
				JOptionPane.showMessageDialog(null, int_ID+": "+int_collected+" новых интервью!");
			} else
			{
				int new_int_count = int_collected - interviews_collected.get(int_ID);
				if (new_int_count > 0) 	
				{
					JOptionPane.showMessageDialog(null, int_ID+": "+new_int_count+" новых интервью!");
					interviews_collected.put(int_ID, int_collected);
				}
			}
		}
	}
	public void geatherStatAll()
	{
		reqHandlerAdmin.enableBulk();
		for(int i = 0;i < table.getRowCount();i++)
		{
			reqHandlerAdmin.geatherStatistics(Integer.parseInt((String)table.getValueAt(i,1)));
		}
		MSS_RQ_Request.http_request(reqHandlerAdmin.processBulk(),ToolMainWidget.URL);
	}
	
	@SuppressWarnings("unused")
	private void update_constrained(int idGroup)
	{
		QuizTDesc = new MSS_RQ_TableDescriptor(columnNames, 
				new Class[]{Integer.class, Integer.class, String.class,Boolean.class,Boolean.class,Integer.class,Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"ID","Description","Visability","Aviability","Accepted","Subscribed"});
		QuizUpdater = new MSS_RQ_TableFiller(QuizTDesc,QuizT_XML_Desc);
		reqHandlerAdmin = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
		String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.listTasksOwnW(idGroup),ToolMainWidget.URL);
		QuizUpdater.updateData(xml_arr_ans);
		QuizUpdater.fillTable(table);
		
		JList permList = new JList();
		
		MSS_RQ_TableDescriptor PermTDesc = new MSS_RQ_TableDescriptor(columnNames, 
				new Class[]{Integer.class, Integer.class, String.class,Boolean.class,Boolean.class,Integer.class,Integer.class});
		MSS_RQ_XMLtoTableDescriptor PermT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"ID","Description","Visability","Aviability","Accepted","Subscribed"});
		MSS_RQ_CxListFiller PermUpdater = new MSS_RQ_CxListFiller(PermTDesc, PermT_XML_Desc,ListTasksElementData.class);
		String xml_arr_ans2 = MSS_RQ_Request.http_request(reqHandlerAdmin.listTasksAviable(),ToolMainWidget.URL);
		PermUpdater.updateData(xml_arr_ans2);
		PermUpdater.fillCxList(permList);
		
		//get list of ankets for this group
		DefaultListModel model = (DefaultListModel)permList.getModel();
		ArrayList<Integer> arr_list = new ArrayList<Integer>();
		for(int i = 0; i < model.getSize();i++)
		{
			ListTasksElementData data = (ListTasksElementData)model.getElementAt(i);
			arr_list.add(Integer.parseInt(data.getID()));
		}
		
		//exclude from all tasks av only to this group
		DefaultTableModel t_model = (DefaultTableModel)table.getModel();
		for(int i = 0; i < t_model.getRowCount(); i++)
		{
			int cur_id = Integer.parseInt((String)t_model.getValueAt(i, 1));
			if (!arr_list.contains(cur_id))t_model.removeRow(i);
		}
		
		table.setModel(t_model);
		//***
		//
		
		int selected = -1;
		try 
		{
			selected = table.getSelectedRow();
		} catch (Exception e){}
		if (selected != -1)
		{
			MSS_RQ_Request.http_request(reqHandlerAdmin.geatherStatistics(Integer.parseInt((String)table.getValueAt(selected,1))),ToolMainWidget.URL);
		}
		
	}
	public JList getLinked_list() {
		return linked_list;
	}
	public void setLinked_list(JList linkedList) {
		linked_list = linkedList;
	}
}