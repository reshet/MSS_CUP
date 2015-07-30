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
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import com.explodingpixels.macwidgets.IAppWidgetFactory;


public class ListTasksPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -764196241346792559L;
	private ServerDispatcher servDisp;
	private JList list;
	private JTable linked_table;
	public JTable getLinked_table() {
		return linked_table;
	}
	public void setLinked_table(JTable linkedTable) {
		linked_table = linkedTable;
	}
	@SuppressWarnings("unused")
	private DefaultListModel model;
	private ListTasksToolbar toolbar;
	public ListTasksPanel()
	{	
		setLayout(new BorderLayout());
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setSelectedIndex(0);
		//list.setBorder(border)
		Border border = BorderFactory.createEmptyBorder(0,0,5,5);
		list.setBorder(border);
		list.setCellRenderer(new ListTasksElementRenderer());
		//list.setModel(model);
		list.setBackground(new Color(50,50,50));
		list.setForeground(new Color(210,210,220));
		
		//Border border2 = BorderFactory.createEmptyBorder(5,5,5,5);
		JScrollPane scroller = new JScrollPane(list);
		//scroller.setBorder(border2);
		scroller.setBackground(new Color(70,70,70));
		scroller.setAutoscrolls(true);
		IAppWidgetFactory.makeIAppScrollPane(scroller);
		scroller.setFocusable(true);
		scroller.setPreferredSize(new Dimension(170, 220));
		
		servDisp = new ListTasksServerDispatcher(list);
		toolbar = new ListTasksToolbar(servDisp,list);
		toolbar.setPreferredSize(new Dimension(170,40));
		JLabel text = new JLabel();
		Font f = new Font(Font.SANS_SERIF,Font.BOLD,15);
		text.setFont(f);
		text.setText("Список завдань:");
		text.setBackground(new Color(55,95,95));
		text.setForeground(new Color(220,220,220));
		//text.setLineWrap(true);
		
		
		//this.setLayout(new BorderLayout(5,5));
		this.add(text,BorderLayout.NORTH);
		this.add(scroller,BorderLayout.CENTER);
		this.add(toolbar,BorderLayout.SOUTH);
	}
	public JList getList() {
		return list;
	}
}
