package base_SP_Management;

import java.util.Set;

public class SocioAnalysisForm {
	private Set<String> var_names;
	public SocioAnalysisForm(Set<String> names)
	{
		var_names = names;
	}
	public Set<String> getVar_names() {
		return var_names;
	}

	public void setVar_names(Set<String> varNames) {
		var_names = varNames;
	}
}
