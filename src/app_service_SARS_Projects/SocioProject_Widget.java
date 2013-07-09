package app_service_SARS_Projects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import app_service_quiz.ListGroupsPanel;
import app_service_quiz.TasksTablePanel;
import base_SP_Management.PM_Header;
import base_SP_Management.SP_ProjectPanelsToolbarWidget;
import base_SP_Management.SP_Tags_Panel;
import base_SP_Management.SocioProject;


public class SocioProject_Widget extends JPanel {
	/**Igor Reshetnev
	 * 8.10.2010
	 * SocioLogic project
	 */
	private static final long serialVersionUID = -3168455789998983274L;
	private JList list;
	@SuppressWarnings("unused")
	private DefaultListModel model;
	private SP_ProjectPanelsToolbarWidget toolbar;
	private SocioProject connectedPanel;
	private PM_Header header;

	public ListGroupsPanel getPanelss_panel() {
		return panels_panel;
	}
	/*
	public ListTasksPanel getTasks_panel() {
		return tasks_panel;
	}
	*/
	private SP_Tags_Panel tags_panel;
	private TasksTablePanel anket_table;
	public TasksTablePanel getAnket_table() {
		return anket_table;
	}
	private ListAgregatedAnketsPanel agr_panel;
	@SuppressWarnings("unused")
	private JTable table;
	@SuppressWarnings("unused")
	private DefaultTableModel tmodel;
	
	private ListGroupsPanel panels_panel;
	//private ListTasksPanel tasks_panel;
	public void setTmodel(DefaultTableModel tmodel) {
		this.tmodel = tmodel;
		//if (tmodel != null)table.setModel(tmodel);
	}
	public int getSelectedAnketID()
	{
		return anket_table.getSelectedTask();
	}
	public String getSelectedAnketName()
	{
		return anket_table.getSelectedTaskName();
	}
	public JTable getAnketTable()
	{
		return anket_table.getTable();
	}
	public JList getAgregList()
	{
		return agr_panel.getList();
	}
	public SocioProject getSocioProject()
	{
		return connectedPanel;
	}
	public SocioProject_Widget()
	{	
		setLayout(new BorderLayout());
		panels_panel = new ListGroupsPanel(-1);
		anket_table = new TasksTablePanel(-1);
		anket_table.setPreferredSize(new Dimension(500,400));
		agr_panel = new ListAgregatedAnketsPanel();
		//tasks_panel = new ListTasksPanel();
		/*
		table = new JTable();
		tmodel = new DefaultTableModel(50, 30);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int i =0;i<50;i++)
		{
			for (int j =0;j<30;j++)
			{
				tmodel.setValueAt("empty_cell", i, j);
			}
		}
		table.setModel(tmodel);
		TableColumnModel col_model = table.getColumnModel();
		for (int j =0;j<30;j++)
		{
			col_model.getColumn(j).setWidth(20);
		}
		
		JScrollPane scroller = new JScrollPane(table);
		*/
		//IAppWidgetFactory.makeIAppScrollPane(scroller);
		//scroller.setFocusable(true);
		//scroller.setPreferredSize(new Dimension(170, 220));
		
		//header = new PM_Header();
		tags_panel = new SP_Tags_Panel(new DefaultListModel());
		toolbar = new SP_ProjectPanelsToolbarWidget();
		toolbar.setPreferredSize(new Dimension(170,100));
		JLabel text = new JLabel("Панель:");
		Font f = new Font(Font.SANS_SERIF,Font.BOLD,15);
		text.setFont(f);
		text.setText("Панель:");
		text.setBackground(new Color(55,95,95));
		text.setForeground(new Color(120,120,120));
		text.setAlignmentX(CENTER_ALIGNMENT);
		//this.add(header,BorderLayout.NORTH);
		//this.add(tags_panel, BorderLayout.EAST);
		this.add(anket_table, BorderLayout.CENTER);
		this.add(agr_panel, BorderLayout.EAST);
		this.add(new JScrollPane(panels_panel),BorderLayout.WEST);
		this.add(toolbar,BorderLayout.SOUTH);
	}
	public PM_Header getHeader() {
		return header;
	}
	public void adjustSocioProject(SocioProject pnl)
	{
		this.connectedPanel = pnl;
		connectedPanel.setList(this.list);
		connectedPanel.setWidget(this);
		toolbar.setDesk(connectedPanel.getDesk());
		toolbar.getDesk().setPanel_list(this.list);
		panels_panel.setGroupID(connectedPanel.getID_GroupOwn());
		anket_table.setGroupID(connectedPanel.getID_GroupOwn());
		anket_table.update();
		agr_panel.setSocioProject(pnl);
		/*
		DefaultListModel mdl = new DefaultListModel();
		for(SocioPanelMember memb:connectedPanel.getMembers())
		{
			mdl.addElement(memb);
		}
		this.list.setModel(mdl);
	*/
		tags_panel.setModel(connectedPanel.getListTagsModel());
		//users_panel.setGroupID(connectedPanel.getID_GroupOwn());
		//users_panel.getServDisp().refreshGroupMembers(new Integer[]{users_panel.getGroupID()});
		//tags_panel.setTags(connectedPanel.getSp_tags());
		//tags_panel.upModel();
	}
}
