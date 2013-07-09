package base_SP_Management;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import com.explodingpixels.macwidgets.IAppWidgetFactory;

public class SP_Tags_Panel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6802370059846197245L;
	private JList listOflabels;
	//private ArrayList<SP_Tag> attachedTags;
	private DefaultListModel model;
	public SP_Tags_Panel(DefaultListModel model)
	{
		this.model = model;
		//attachedTags = tags;
		//DefaultListModel model = new DefaultListModel();
		//for(SP_Tag tag:attachedTags)
		//{
	//		model.addElement(tag.getName());
	//	}
		listOflabels = new JList(this.model);
		listOflabels.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listOflabels.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listOflabels.setVisibleRowCount(-1);
		listOflabels.setSelectedIndex(0);
		
		Border border = BorderFactory.createEmptyBorder(5,5,5,5);
		listOflabels.setBorder(border);
		listOflabels.setBackground(new Color(50,50,50));
		listOflabels.setForeground(new Color(210,210,220));
		JScrollPane scroller = new JScrollPane(listOflabels);
		scroller.setPreferredSize(new Dimension(200,500));
		IAppWidgetFactory.makeIAppScrollPane(scroller);
		scroller.setFocusable(true);	
		this.add(scroller);
	}
	public void setModel(DefaultListModel model)
	{
		this.model = model;
		listOflabels.setModel(this.model);
	}
	/*
	public void setTags(ArrayList<SP_Tag> tags)
	{
		attachedTags = tags;
	}
	public void upModel()
	{
		DefaultListModel model = new DefaultListModel();
		for(SP_Tag tag:attachedTags)
		{
			model.addElement(tag.getName());
		}
		listOflabels.setModel(model);
	}
	*/
	public DefaultListModel getModel()
	{
		return model;
	}
	
}
