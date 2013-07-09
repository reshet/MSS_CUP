package base_SP_Management;

import java.util.Map;
import java.util.Set;

public class SocioPanelMember {
	private String name;
	private int ID;
	//@SuppressWarnings("unchecked")
	private Map<String,String> vars;
	private Set<String> var_names;
	public SocioPanelMember(int ID,String name)
	{
		this.ID = ID;
		this.name = name;
		//setVar_names(vars.keySet());
	}
	public Map<String,String> getVars() {
		return vars;
	}
	public void setVars(Map<String,String> vars) {
		this.vars = vars;
		setVar_names(vars.keySet());
	}
	public SocioPanelMember()
	{
		setName("panelist");
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	@SuppressWarnings("unused")
	private void setID(int iD) {
		ID = iD;
	}
	public int getID() {
		return ID;
	}
	private void setVar_names(Set<String> var_names) {
		this.var_names = var_names;
	}
	public Set<String> getVar_names() {
		return var_names;
	}
}
