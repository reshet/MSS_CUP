package app_service_SARS_Panels;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import base_SP_Management.SocioPanel;


public class SP_CellRenderer implements ListCellRenderer {
	static int yet = 0;  
	protected Component component;
	  public Component getListCellRendererComponent(JList list, Object value, int index,
			  	boolean isSelected, boolean cellHasFocus) {
		  //int selectedIndex = ((Integer)value).intValue();

	      String name = new String(((SocioPanel) value).getName());  
	      int ID = new Integer(((SocioPanel) value).getID_GroupOwn());  
	      int size = new Integer(((SocioPanel) value).getCol_panelsists());  
		  component = new SP_ListElement_Widget(name,ID,size);
		  if (yet == 0)
		  {
			  yet = 1;
		  }
			  if (isSelected) {
	            component.setBackground(list.getSelectionBackground());
	            component.setForeground(list.getSelectionForeground());
	        } else {
	        	component.setBackground(list.getBackground());
	        	component.setForeground(list.getForeground());
	        }

		  return component;
	  }
	}