package app_service_news;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;


public class ComplexCellRenderer implements ListCellRenderer {
	static int yet = 0;  
	protected Component component;
	  public Component getListCellRendererComponent(JList list, Object value, int index,
			  	boolean isSelected, boolean cellHasFocus) {
		  //int selectedIndex = ((Integer)value).intValue();

	      String pre = new String(((NewsServiceElementData) value).getPreview());  
	      String full = new String(((NewsServiceElementData) value).getFulltext());  
	      String head = new String(((NewsServiceElementData) value).getHeader());  
		  component = new NewsServiceElement(((NewsServiceElementData) value).getImage(),head,pre,full);
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