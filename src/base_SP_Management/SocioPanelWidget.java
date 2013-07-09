package base_SP_Management;

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

import app_service_quiz.ListUsersPanel;


public class SocioPanelWidget extends JPanel {
	/**Igor Reshetnev
	 * 8.10.2010
	 * SocioLogic project
	 */
	private static final long serialVersionUID = -3168455789998983274L;
	private JList list;
	@SuppressWarnings("unused")
	private DefaultListModel model;
	private SP_ToolbarWidget toolbar;
	private SocioPanel connectedPanel;
	private PM_Header header;

	public ListUsersPanel getUsers_panel() {
		return users_panel;
	}
	/*
	public ListTasksPanel getTasks_panel() {
		return tasks_panel;
	}
	*/
	private SP_Tags_Panel tags_panel;
	@SuppressWarnings("unused")
	private JTable table;
	@SuppressWarnings("unused")
	private DefaultTableModel tmodel;
	
	private ListUsersPanel users_panel;
	//private ListTasksPanel tasks_panel;
	public void setTmodel(DefaultTableModel tmodel) {
		this.tmodel = tmodel;
		//if (tmodel != null)table.setModel(tmodel);
	}
	public SocioPanelWidget()
	{	
		setLayout(new BorderLayout());
		users_panel = new ListUsersPanel(-1);
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
		toolbar = new SP_ToolbarWidget();
		toolbar.setPreferredSize(new Dimension(170,40));
		JLabel text = new JLabel("Панель:");
		Font f = new Font(Font.SANS_SERIF,Font.BOLD,15);
		text.setFont(f);
		text.setText("Панель:");
		text.setBackground(new Color(55,95,95));
		text.setForeground(new Color(120,120,120));
		text.setAlignmentX(CENTER_ALIGNMENT);
		//this.add(header,BorderLayout.NORTH);
		this.add(tags_panel, BorderLayout.EAST);
		this.add(new JScrollPane(users_panel),BorderLayout.CENTER);
		this.add(toolbar,BorderLayout.SOUTH);
	}
	public PM_Header getHeader() {
		return header;
	}
	public void adjustSocioPanel(SocioPanel pnl)
	{
		this.connectedPanel = pnl;
		connectedPanel.setList(this.list);
		connectedPanel.setWidget(this);
		toolbar.setDesk(connectedPanel.getDesk());
		toolbar.getDesk().setPanel_list(this.list);
		users_panel.setGroupID(connectedPanel.getID_GroupOwn());
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
