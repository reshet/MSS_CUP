package app_service_quiz;

import java.awt.Font;

import javax.swing.JLabel;

public class ListUsersElement extends JLabel{
	/**
	 * visualization in JList
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String name, ID;
	@SuppressWarnings("unused")
	private ListUsersElementData data;
	public ListUsersElement(String name, String ID, ListUsersElementData data) {
		this.setText(name);
		this.setOpaque(true);
		this.data = data;
		this.name = name;
		this.ID = ID;
		this.setFont(new Font("Helvetika",Font.PLAIN,11));
	}
}
