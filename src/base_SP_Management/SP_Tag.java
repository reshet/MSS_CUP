package base_SP_Management;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import app_service_quiz.ListGroupsElementData;
import app_service_quiz.ListGroupsServerDispatcher;
import app_service_quiz.ServerDispatcher;

public class SP_Tag {
	private int ID;
	private String name;
	private String description;
	private boolean toAutoSubdivide = false;
	private Map<String,SP_Tag> internalTags;
	private Color color;
	private ServerDispatcher servDisp;
	private boolean updateStatusServer = false;
	private boolean updateStatusLocal = false;	
	public ServerDispatcher getServDisp() {
		return servDisp;
	}
	private JList list;
	public boolean isToAutoSubdivide() {
		return toAutoSubdivide;
	}
	public void setToAutoSubdivide(boolean toAutoSubdivide) {
		this.toAutoSubdivide = toAutoSubdivide;
	}
	public int getID() {
		return ID;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Color getColor() {
		return color;
	}
	public void addInternalTag(SP_Tag tag)
	{
		internalTags.put(tag.getName(),tag);
		updateStatusServer = false;
	}
	public Map<String,SP_Tag> getInternalTags() {
		return internalTags;
	}
	public SP_Tag(String name,String desc)
	{
		list = new JList();
		this.servDisp = new ListGroupsServerDispatcher(list);
		this.internalTags = new HashMap<String,SP_Tag>();
		this.name = name;
		this.description = desc;
		color = new Color(new Random().nextInt(255),100+new Random().nextInt(155),100+new Random().nextInt(155));
	}
	public void setID(int iD) {
		ID = iD;
	}
	public synchronized void updateTagFromServer()
	{
		String [] params = {String.valueOf(ID)};
		servDisp.refreshHeirGroups(params);
		//Something intelligent needed here
		Set<SP_Tag> set = new HashSet<SP_Tag>();
		for (int i = 0; i < list.getModel().getSize();i++)
		{
			ListGroupsElementData elem = (ListGroupsElementData)list.getModel().getElementAt(i);
			SP_Tag tag = new SP_Tag(elem.getName(),elem.getDesc());
			tag.setID(elem.getID());
			set.add(tag);
			internalTags.put(tag.getName(),tag);
		}
		this.updateStatusLocal = true;
		this.updateStatusServer = true;
		Set<String> inter_set = internalTags.keySet();
		/*
		for(String key_name:inter_set)
		{
			if (!set.contains(internalTags.get(key_name)))
			{
				internalTags.remove(key_name);
			}
		}
		*/
		for(String key_name:inter_set)
		{
			SP_Tag tag = internalTags.get(key_name);
			tag.updateTagFromServer();
		}
	}
	public synchronized void updateTagToServer()
	{
		//only add now
		String [] params = {name,description};
		servDisp.add(params);
		
		
		//to return newID!
		
		//servDisp.refreshHeirGroups(params);
		//Something intelligent needed here
		this.updateStatusServer = true;
		this.updateStatusLocal = true;
		
		Set<String> inter_set = internalTags.keySet();
		for(String key_name:inter_set)
		{
			SP_Tag tag = internalTags.get(key_name);
			tag.updateTagToServer();
		}
	}
	public boolean isUpdateStatusServer() {
		return updateStatusServer;
	}
	public boolean isUpdateStatusLocal() {
		return updateStatusLocal;
	}
	public JList getList() {
		return list;
	}
}
class SP_Tag_Widget extends JLabel implements TreeCellRenderer
{
	/**
	 * Description of SP_Tag class? Great!
	 */
	private SP_Tag tag_D;
	private static final long serialVersionUID = 689594981243912225L;
	public SP_Tag_Widget (SP_Tag tag_data)
	{
		super();
		tag_D = tag_data;
		setText(tag_D.getName());
		setBackground(tag_D.getColor());
		setToolTipText(tag_D.getDescription());
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				String [] params = {String.valueOf(tag_D.getID())};
				SP_Tag_Widget.this.tag_D.getServDisp().refreshGroupMembers(params);
			}
		});
	}
	@Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
		      boolean selected, boolean expanded, boolean leaf, int row,
		      boolean hasFocus) 
	{
		    Component returnValue = null;
		    if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
		      Object userObject = ((DefaultMutableTreeNode) value)
		          .getUserObject();
		      if (userObject instanceof SP_Tag) {
		    	SP_Tag tag_elem = (SP_Tag) userObject;
		        SP_Tag_Widget tag_W = new SP_Tag_Widget(tag_elem);
		        tag_W.setEnabled(true);
		    	if (selected) {
		    		tag_W.setBackground(new Color(60,60,60));
			        } else {
			        tag_W.setBackground(new Color(60,60,60));
			        }
		        returnValue = tag_W;
		      }
		    }
		    if (returnValue == null) {
		      returnValue = new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree,
		          value, selected, expanded, leaf, row, hasFocus);
		    }
		    return returnValue;
		  }
}
