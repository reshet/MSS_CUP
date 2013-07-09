package app_service_SARS_Projects;

import java.util.Map;

public class UserSettingsProjectCreator_Setting {
	public String _projectName;
	public String _projectDescription;
	public Map<Integer,String> filled_tags;
	public Map<String,String> filled_tasks;
	public UserSettingsProjectCreator_Setting(String prj_name,String prj_desc,Map<Integer,String> filled_tags,Map<String,String> filled_tasks) {
		this.filled_tags = filled_tags;
		this.filled_tasks = filled_tasks;
		this._projectName = prj_name;
		this._projectDescription = prj_desc;
	}
}
