package base_SP_Management;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class SP_ListElementRenderer implements ListCellRenderer { 
	protected Component component = null;
	public Component getListCellRendererComponent(JList list, Object value, int index,
			  	boolean isSelected, boolean cellHasFocus) 
	{
	  //Map vars = new HashMap(((SocioPanelMember) value).getVars());  
	  //String name = new String(((SocioPanelMember) value).getName());
	  //int ID = (((SocioPanelMember) value).get());
	 System.out.print(value.toString());
	  if (isSelected) {
		  	component = new SP_MemberWidget((SocioPanelMember) value);
	        component.setBackground(new Color(45,45,135));
	        component.setForeground(new Color(245,245,33));
	  } else {
		  	component = new SP_MemberWidget((SocioPanelMember) value);
	    	component.setBackground(list.getBackground());
	    	component.setForeground(list.getForeground());
	  }
	  return component;
	}
}
