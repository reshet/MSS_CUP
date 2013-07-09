package app_service_quiz;

import java.awt.Font;

import javax.swing.JLabel;

public class ListGroupsElement extends JLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String name, ID;
	private ListGroupsElementData data;
	public ListGroupsElement(String name, String ID, ListGroupsElementData data) {
		this.setText(name);
		this.setOpaque(true);
		this.data = data;
		this.name = name;
		this.ID = ID;
		this.setFont(new Font("Helvetika",Font.PLAIN,11));
	}
	public ListGroupsElementData getData()
	{
		return data;
	}
}
