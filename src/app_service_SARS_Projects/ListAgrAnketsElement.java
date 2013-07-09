package app_service_SARS_Projects;

import java.awt.Font;

import javax.swing.JLabel;

public class ListAgrAnketsElement extends JLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String name;
	private ListAgregatedAnketsElementData data;
	public ListAgrAnketsElement(String name,ListAgregatedAnketsElementData data) {
		this.setText(name);
		this.setOpaque(true);
		this.data = data;
		this.name = name;
		this.setFont(new Font("Helvetika",Font.PLAIN,11));
	}
	public ListAgregatedAnketsElementData getData()
	{
		return data;
	}
}
