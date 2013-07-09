package app_service_SARS_Panels;

import javax.swing.JLabel;

public class UserSettingsDemoAsker extends UserSettingsAbstractAsker<String>{
	
	public UserSettingsDemoAsker(String help) {
		super(help);
		panel.add(new JLabel("This is DEMO SETTINGS LABEL!"));
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 4036252704300393935L;

	@Override
	public void processSettings() {
		_setting = "Setting done!";
	}
}
