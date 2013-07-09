package app_service_quiz;

import app_service_news.ListElementData;

public class ListGroupsElementData  implements ListElementData{
	private String name,desc;
	private int ID;
	public ListGroupsElementData(){}
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public void initialize(Object[] params) {
		int ID = Integer.parseInt((String)params[1]);
		String nm = (String)params[2];
		String desc = (String)params[3];
		this.name = nm;
		this.ID = ID;
		this.desc = desc;
	}
}
