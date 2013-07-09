package app_service_quiz;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ListGroupsElementRenderer implements ListCellRenderer { 
	protected Component component = null;
	public Component getListCellRendererComponent(JList list, Object value, int index,
			  	boolean isSelected, boolean cellHasFocus) 
	{
	  int ID = ((ListGroupsElementData) value).getID();  
	  String name = new String(((ListGroupsElementData) value).getName());  
	 
	  if (isSelected) {
		  	component = new ListGroupsElement(name,String.valueOf(ID),(ListGroupsElementData) value);
	        component.setBackground(new Color(45,45,135));
	        component.setForeground(new Color(245,245,33));
	  } else {
		  	component = new ListGroupsElement(name,String.valueOf(ID),(ListGroupsElementData) value);
	    	component.setBackground(list.getBackground());
	    	component.setForeground(list.getForeground());
	  }
	  return component;
	}
}
