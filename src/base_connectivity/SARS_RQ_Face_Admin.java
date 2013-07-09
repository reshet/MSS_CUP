/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

/**
 *
 * @author RX
 */
public interface SARS_RQ_Face_Admin {
	public String createProject(String name,String desc);
	public String createTask(String task_desc);
	public String agragateTask(int idTask,int idProject,String actualityDate);
	public String addTag(int idProject,int idTag);
	public String createTag(int idTagCategory,String tag_desc);
	public String getTaskVars(int idTask);
	public String mailReport(int idProject,int idTask,int var1,int var2, String type,String email,String link);
	public String inviteUsersOnline(int idGroup,int idTask);
	public String createTrigger(String LEFT_CMD, String RIGHT_CMD, String CONDITION,String ACTION,String TYPE);
}
