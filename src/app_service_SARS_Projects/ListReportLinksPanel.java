package app_service_SARS_Projects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

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


public class ListReportLinksPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3168455789998983274L;
	private JList list;
	@SuppressWarnings("unused")
	private DefaultListModel model;
	private ListReportLinksToolbar toolbar;
	int idRoot;
	int groupID;
	
	public void setGroupID(int groupID) {
		this.groupID = groupID;
		toolbar.setGroupID(groupID);
	}
	
	public ListReportLinksPanel(SocioProject sp)
	{	
		
		list = new JList(new DefaultListModel());
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setSelectedIndex(0);
		list.setCellRenderer(new ListReportLinksElementRenderer());
		
		Border border = BorderFactory.createEmptyBorder(5,5,5,5);
		list.setBorder(border);
		list.setBackground(new Color(50,50,50));
		list.setForeground(new Color(210,210,220));
		list.setEnabled(true);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultListModel mdl = (DefaultListModel)list.getModel();
				ListReportLinksElementData elem = (ListReportLinksElementData)mdl.getElementAt(list.getSelectedIndex());
				String link = elem.getName();
				//String ask = JOptionPane.showInputDialog("Link opened!");
				String browserPath = "C:/Program Files (x86)/Mozilla Firefox/firefox.exe"; //Use your browser path
				try {
					String[] b = {browserPath, link};
					Runtime.getRuntime().exec(b);
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		JScrollPane scroller = new JScrollPane(list);
		IAppWidgetFactory.makeIAppScrollPane(scroller);
		scroller.setFocusable(true);
		scroller.setPreferredSize(new Dimension(170, 220));
		
		toolbar = new ListReportLinksToolbar(sp,list);
		toolbar.setGroupID(idRoot);
		toolbar.setPreferredSize(new Dimension(170,40));
		JLabel text = new JLabel("Список ccылок на отчеты:");
		Font f = new Font(Font.SANS_SERIF,Font.BOLD,15);
		text.setFont(f);
		text.setText("Список груп:");
		text.setBackground(new Color(55,95,95));
		text.setForeground(new Color(80,80,80));
	
		this.setLayout(new BorderLayout());
		this.add(text,BorderLayout.NORTH);
		this.add(scroller,BorderLayout.CENTER);
		this.add(toolbar,BorderLayout.SOUTH);
	}
	public JList getList() {
		return list;
	}
	
}
