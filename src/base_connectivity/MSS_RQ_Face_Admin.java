/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

/**
 *
 * @author RX
 */
public interface MSS_RQ_Face_Admin {
    public String createTask(String task,String desc,int scope,int pattern);
    public String deleteTask(int n);
    public String updateTask(String task,String desc,int n);
    public String openTask(int n);
    public String closeTask(int n);
    public String shareTask(int n);
    public String hideTask(int n);
    public String unfoldTask(int n);
    public String makeGroup(String name,String desc);
    public String makeGroup(int id,String name,String desc);
    public String makeGroup(int id,int id_foruser,String name,String desc);
    public String editGroup(int n, String newName);
    public String deleteGroup(int n);
    public String listOwnGroups();
    public String listTasksOwn();
    public String listTasksOwnW(int group);
    public String listGroupMembers(int n);
    public String listGroupMembersDetailed(int group, int id_reg_iv);
    public String geatherStatistics(int n);
    public String representStatistics(int n);
    public String representAllStatistics();
    public String registerUser(int id,int group,String PIB, String pswd);
    public String registerUser(int group,String PIB, String pswd);
    public String enrollToGroup(int idUser, int idGroup,String pswd);
    public String kickUserFromGroup(int idUser,int idGroup);
    public String listHeirGroups(int groupID);
    public String addHeirGroup(int superID,int heirID);
    public String sitTask(int taskID, String answer);
    public String extendScopeTask(int taskID, int groupID);
    public String descendScopeTask(int taskID, int groupID);
    public String listTasksAviable();
    public String listTasksAviable2(int gr);
    public String force_subscribeTask(int idTask,int idUser);
    public String force_unsubscribeTask(int idTask,int idUser);
    public String getTasksDoneCount(int task);
}
