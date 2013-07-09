package app_service_SARS_Panels;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class UserSettingsAbstractAsker<Setting> extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6410528033057904553L;
	protected JPanel panel;
	private JLabel text_help;
	protected Setting _setting;
	public UserSettingsAbstractAsker(String help)
	{
		setLayout(new BorderLayout(3,3));
		panel = new JPanel();
		text_help = new JLabel(help);
		add(panel,BorderLayout.CENTER);
		//add(text_help,BorderLayout.NORTH);
	}
	public JLabel getText_help() {
		return text_help;
	}
	//public JPanel getPanel(){return panel;}
	public Setting getSetting(){return _setting;}
	public abstract void processSettings();
}
