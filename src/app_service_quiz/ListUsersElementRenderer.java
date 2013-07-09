package app_service_quiz;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ListUsersElementRenderer implements ListCellRenderer { 
	protected Component component = null;
	public Component getListCellRendererComponent(JList list, Object value, int index,
			  	boolean isSelected, boolean cellHasFocus) 
	{
	  String ID = new String(((ListUsersElementData) value).getID());  
	  String name = new String(((ListUsersElementData) value).getPIB());  
	 
	  if (isSelected) {
		  	component = new ListUsersElement(name,ID,(ListUsersElementData) value);
	        component.setBackground(new Color(45,45,135));
	        component.setForeground(new Color(245,245,33));
	  } else {
		  	component = new ListUsersElement(name,ID,(ListUsersElementData) value);
	    	component.setBackground(list.getBackground());
	    	component.setForeground(list.getForeground());
	  }
	  return component;
	}
}
