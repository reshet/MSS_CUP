package app_service_quiz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import com.explodingpixels.macwidgets.IAppWidgetFactory;


public class ListUsersPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3168455789998983274L;
	private ServerDispatcher servDisp;
	public ServerDispatcher getServDisp()
	{
		return servDisp;
	}
	private JList list;
	public JList getList()
	{
		return list;
	}
	@SuppressWarnings("unused")
	private DefaultListModel model;
	private ListUsersToolbar toolbar;
	private int groupID;
	public ListUsersToolbar getToolbar() {
		return toolbar;
	}
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
		toolbar.setGroupID(groupID);
		toolbar.refresh();
	}
	//public ListUsersPanel(){groupID = 31;}//only for arrays
	public ListUsersPanel(int idGroup)
	{	
		list = new JList();
		groupID = idGroup;
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setSelectedIndex(0);
		list.setCellRenderer(new ListUsersElementRenderer());
		//list.setModel(model);
		
		Border border = BorderFactory.createEmptyBorder(5,5,5,5);
		list.setBorder(border);
		list.setBackground(new Color(50,50,50));
		list.setForeground(new Color(210,210,220));
		
		JScrollPane scroller = new JScrollPane(list);
		IAppWidgetFactory.makeIAppScrollPane(scroller);
		scroller.setFocusable(true);
		//scroller.setPreferredSize(new Dimension(170, 220));
		
		servDisp = new ListUsersServerDispatcher(list);
		toolbar = new ListUsersToolbar(servDisp,list);
		toolbar.setGroupID(idGroup);
		Integer [] params = new Integer[1];
		params[0] = idGroup;
		toolbar.getDispatcher().refresh(params);
		//toolbar.setPreferredSize(new Dimension(170,40));
		JLabel text = new JLabel("Список членів панелі:");
		Font f = new Font(Font.SANS_SERIF,Font.BOLD,15);
		text.setFont(f);
		text.setText("Список членів панелі:");
		text.setBackground(new Color(55,55,65));
		//text.setForeground(new Color(220,220,220));
		//text.setLineWrap(true);
		
		
		this.setLayout(new BorderLayout());
		this.add(text,BorderLayout.NORTH);
		this.add(scroller,BorderLayout.CENTER);
		this.add(toolbar,BorderLayout.SOUTH);
	}
	public void setModel(DefaultListModel model) {
		this.model = model;
	}
}
