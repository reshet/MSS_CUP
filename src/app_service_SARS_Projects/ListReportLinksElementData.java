package app_service_SARS_Projects;

public class ListReportLinksElementData{
	private String name;
	private int ID;
	private UserSettingsReportLink_Setting setting;
	public ListReportLinksElementData(String link,UserSettingsReportLink_Setting setting){
		name = link;
		this.setSetting(setting);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public void setSetting(UserSettingsReportLink_Setting setting) {
		this.setting = setting;
	}
	public UserSettingsReportLink_Setting getSetting() {
		return setting;
	}
}
