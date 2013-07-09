package app_service_news;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;


public class FeedCellRenderer implements ListCellRenderer {
	static int yet = 0;  
	protected Component component;
	  public Component getListCellRendererComponent(JList list, Object value, int index,
			  	boolean isSelected, boolean cellHasFocus) {
		  //int selectedIndex = ((Integer)value).intValue();

	      String name = new String(((NewsFeedElementData) value).getName());  
	      component = new NewsFeedElement(name);
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