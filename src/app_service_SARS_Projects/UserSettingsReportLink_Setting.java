package app_service_SARS_Projects;

import java.util.Map;

public class UserSettingsReportLink_Setting {
	public String _Name;
	public int task_ID;
	public static enum _type{Table,Graphic};
	public static enum _table_type{_1D,_2D};
	public static enum _chart_type{_bar,_pie};
	public _type __type;
	public _table_type __table_type;
	public _chart_type __chart_type;
	public Map<Integer,String> used_vars;
	public String condition,left_cond_type,right_cond,email;
	public UserSettingsReportLink_Setting(String name,int type,int subtype,Map<Integer,String> used_vars,String condition,String left_cond_type,String right_cond,String email) {
		this.used_vars = used_vars;
		this._Name = name;
		this.condition = condition;
		this.left_cond_type = left_cond_type;
		this.right_cond = right_cond;
		this.email = email;
		if (type == _type.Table.ordinal())
		{
			__type = _type.Table;
			__table_type = _table_type.values()[subtype];
		}else
		{
			__type = _type.Graphic;
			__chart_type = _chart_type.values()[subtype];
		}
	}
}
