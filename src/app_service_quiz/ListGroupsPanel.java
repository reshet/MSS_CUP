package app_service_quiz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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


public class ListGroupsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3168455789998983274L;
	private ServerDispatcher servDisp;
	private JList list;
	@SuppressWarnings("unused")
	private DefaultListModel model;
	private ListGroupsToolbar toolbar;
	int idRoot;
	int groupID;
	public void setGroupID(int groupID) {
		this.groupID = groupID;
		getToolbar().setGroupID(groupID);
	}
	public ListGroupsPanel(int idRootGroup)
	{	
		idRoot = idRootGroup;
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setSelectedIndex(0);
		list.setCellRenderer(new ListGroupsElementRenderer());
		//list.setModel(model);
		
		Border border = BorderFactory.createEmptyBorder(5,5,5,5);
		list.setBorder(border);
		list.setBackground(new Color(50,50,50));
		list.setForeground(new Color(210,210,220));
		
		JScrollPane scroller = new JScrollPane(list);
		IAppWidgetFactory.makeIAppScrollPane(scroller);
		scroller.setFocusable(true);
		scroller.setPreferredSize(new Dimension(170, 220));
		
		servDisp = new ListGroupsServerDispatcher(list);
		setToolbar(new ListGroupsToolbar(servDisp,list));
		getToolbar().setGroupID(idRoot);
		//toolbar.refresh();
		getToolbar().setPreferredSize(new Dimension(170,40));
		JLabel text = new JLabel("Список груп:");
		Font f = new Font(Font.SANS_SERIF,Font.BOLD,15);
		text.setFont(f);
		text.setText("Список груп:");
		text.setBackground(new Color(55,95,95));
		text.setForeground(new Color(80,80,80));
		//text.setLineWrap(true);
		
		
		this.setLayout(new BorderLayout());
		this.add(text,BorderLayout.NORTH);
		this.add(scroller,BorderLayout.CENTER);
		this.add(getToolbar(),BorderLayout.SOUTH);
	}
	public JList getList() {
		return list;
	}
	private void setToolbar(ListGroupsToolbar toolbar) {
		this.toolbar = toolbar;
	}
	private ListGroupsToolbar getToolbar() {
		return toolbar;
	}
	
}
