package app_service_quiz;

import app_service_news.ListElementData;

public class ListTasksElementData  implements ListElementData{
	private String description;
	private String ID;
	public ListTasksElementData(){}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	@Override
	public void initialize(Object[] params) {
		String nm = (String)params[1];
		String ID = (String)params[2];
		this.description = nm;
		this.ID = ID;
	}
}
