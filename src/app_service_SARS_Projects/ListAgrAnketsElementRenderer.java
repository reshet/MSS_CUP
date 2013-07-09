package app_service_SARS_Projects;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ListAgrAnketsElementRenderer implements ListCellRenderer { 
	protected Component component = null;
	public Component getListCellRendererComponent(JList list, Object value, int index,
			  	boolean isSelected, boolean cellHasFocus) 
	{  
	  String name = new String(((ListAgregatedAnketsElementData) value).getName());  
	 
	  if (isSelected) {
		  	component = new ListAgrAnketsElement(name,(ListAgregatedAnketsElementData) value);
	        component.setBackground(new Color(45,45,135));
	        component.setForeground(new Color(245,245,33));
	  } else {
		  	component = new ListAgrAnketsElement(name,(ListAgregatedAnketsElementData) value);
	    	component.setBackground(list.getBackground());
	    	component.setForeground(list.getForeground());
	  }
	  return component;
	}
}
