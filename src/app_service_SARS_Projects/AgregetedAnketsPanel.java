package app_service_SARS_Projects;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;

public class AgregetedAnketsPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6308193864496493680L;
	@SuppressWarnings("unused")
	private JList list_of_agragates;
	public AgregetedAnketsPanel() {
		list_of_agragates = new JList();
		setLayout(new BorderLayout(3,3));
	}
}
