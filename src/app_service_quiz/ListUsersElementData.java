package app_service_quiz;

import app_service_news.ListElementData;

public class ListUsersElementData  implements ListElementData{
	private String name;
	private String ID;
	private String PIB;
	private String ID_UMS_Accessor;
	public ListUsersElementData(){}
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
		String ID = (String)params[0];
		String nm = (String)params[1];
		String pib = (String)params[2];
		if (params.length > 3)
		{
			this.ID_UMS_Accessor = (String)params[3];
		}
		this.name = nm;
		this.ID = ID;
		this.PIB = pib;
	}
	public String getPIB() {
		return PIB;
	}
	public void setPIB(String pIB) {
		PIB = pIB;
	}
	public void setID_UMS_Accessor(String iD_UMS_Accessor) {
		ID_UMS_Accessor = iD_UMS_Accessor;
	}
	public String getID_UMS_Accessor() {
		return ID_UMS_Accessor;
	}
}
