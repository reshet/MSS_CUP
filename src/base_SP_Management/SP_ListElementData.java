package base_SP_Management;

import app_service_news.ListElementData;

public class SP_ListElementData  implements ListElementData{
	private String name;
	private String ID;
	public SP_ListElementData(){}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		
		this.name = nm;
		this.ID = ID;
	}
}
