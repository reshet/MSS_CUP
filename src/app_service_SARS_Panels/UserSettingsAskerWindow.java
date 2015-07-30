package app_service_SARS_Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

public class UserSettingsAskerWindow extends JDialog{

	/**
	 * 
	 */
	private UserSettingsAbstractAsker<?> asker;
	private static final long serialVersionUID = -4250405233433264646L;
	private JButton set_done;
	private boolean settingDone = false;
	public boolean isSettingDone() {
		return settingDone;
	}
	public <Setting> UserSettingsAskerWindow(UserSettingsAbstractAsker<Setting> asker)
	{
		this.asker = asker;
		setTitle(asker.getText_help().getText());
		set_done = new JButton("Подтвердить!");
		set_done.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserSettingsAskerWindow.this.asker.processSettings();
				settingDone = true;
				setVisible(false);
			}
		});
		setLayout(new BorderLayout(3,3));
		add(asker,BorderLayout.CENTER);
		add(set_done,BorderLayout.SOUTH);
		setSize(500,500);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int)screenSize.getWidth()/2 - (int)getSize().getWidth()/2,
				(int)screenSize.getHeight()/2 - (int)getSize().getHeight()/2);
		setVisible(true);
		setAlwaysOnTop(true);	
	}
	public UserSettingsAbstractAsker<?> getAsker(){return asker;}
}
