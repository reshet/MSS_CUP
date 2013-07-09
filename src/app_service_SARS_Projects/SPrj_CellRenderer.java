package app_service_SARS_Projects;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import base_SP_Management.SocioProject;


public class SPrj_CellRenderer implements ListCellRenderer {
	static int yet = 0;  
	protected Component component;
	  public Component getListCellRendererComponent(JList list, Object value, int index,
			  	boolean isSelected, boolean cellHasFocus) {
		  //int selectedIndex = ((Integer)value).intValue();

	      String name = new String(((SocioProject) value).getName());  
	      String descr = new String(((SocioProject) value).getDescr());  
	      int ID = new Integer(((SocioProject) value).getID_GroupOwn());  
	      int size = new Integer(((SocioProject) value).getCol_panelsists());  
		  component = new SPrj_ListElement_Widget(name,descr,ID,size);
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