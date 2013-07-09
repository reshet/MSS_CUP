package app_service_SARS_Projects;

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

import base_SP_Management.SocioProject;

import com.explodingpixels.macwidgets.IAppWidgetFactory;


public class ListAgregatedAnketsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3168455789998983274L;
	private JList list;
	@SuppressWarnings("unused")
	private DefaultListModel model;
	private ListAgrAnketsToolbar toolbar;
	public ListAgregatedAnketsPanel()
	{	
		list = new JList(new DefaultListModel());
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setSelectedIndex(0);
		list.setCellRenderer(new ListAgrAnketsElementRenderer());
		//list.setModel(model);
		
		Border border = BorderFactory.createEmptyBorder(5,5,5,5);
		list.setBorder(border);
		list.setBackground(new Color(50,50,50));
		list.setForeground(new Color(210,210,220));
		
		JScrollPane scroller = new JScrollPane(list);
		IAppWidgetFactory.makeIAppScrollPane(scroller);
		scroller.setFocusable(true);
		scroller.setPreferredSize(new Dimension(170, 220));
		
		///servDisp = new ListGroupsServerDispatcher(list);
		toolbar = new ListAgrAnketsToolbar(list,null);
		toolbar.setPreferredSize(new Dimension(170,40));
		JLabel text = new JLabel("Объединенные анкеты:");
		Font f = new Font(Font.SANS_SERIF,Font.BOLD,15);
		text.setFont(f);
		text.setText("Объединенные анкеты:");
		text.setBackground(new Color(55,95,95));
		text.setForeground(new Color(80,80,80));
		//text.setLineWrap(true);
		
		
		this.setLayout(new BorderLayout());
		this.add(text,BorderLayout.NORTH);
		this.add(scroller,BorderLayout.CENTER);
		this.add(toolbar,BorderLayout.SOUTH);
	}
	public void setSocioProject(SocioProject sprj)
	{
		toolbar.setSocioProject(sprj);
	}
	public JList getList() {
		return list;
	}
	
}
