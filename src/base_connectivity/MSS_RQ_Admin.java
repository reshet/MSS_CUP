/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

/**
 *
 * @author RX
 */
public class MSS_RQ_Admin implements MSS_RQ_Face_Admin, Service_Invokation_Handler {

    private String ME;
    private String userID;
    private String pswd;
    private String prepRQ;
    private String endofRQ,endofRQ_base,endofRQ2;
    private StringBuilder bulk_of_RQs;
    private int bulkSize = 0;
    //private int loading = 0;
    private boolean bulk_mode=false;
    public MSS_RQ_Admin(String me, String user, String pswd)
    {
        this.ME = me;
        this.userID = user;
        this.pswd = pswd;
        upprepRQ();
    }
    public void upprepRQ()
    {
         this.prepRQ = "<RQ SERVICE = \"MSS\" ME = \"" +
                this.ME+
                "\" SCREEN = \"240\" USERID = \""+
                this.userID+
                "\">";
         this.endofRQ = " USERID = \"" + this.userID +
                "\" PSWD = \""+
                this.getPswd()+
                "\"/></RQ>";
         this.endofRQ_base = " USERID = \"" + this.userID + "\" PSWD = \""+this.getPswd()+"\"/>";
         this.endofRQ2 = "</RQ>";

    }
    public String processBulk()
    {
    	String preporRQ = "<RQ SERVICE = \"MSS\" ME = \"" +
        this.ME+
        "\" SCREEN = \"240\" USERID = \""+
        this.userID+
        "\" M_MODE=\"ENABLED\">";
    	if (bulk_of_RQs!=null || bulk_mode == true) 
    		{
    			bulk_mode = false;
    			return preporRQ+bulk_of_RQs.toString()+endofRQ2;
    		}
		return "";
    }
    public void enableBulk()
    {
    	bulk_of_RQs = new StringBuilder();
    	bulkSize = 0;
    	bulk_mode = true;
    }
    public int bulkSize()
    {
    	return bulkSize;
    }
    public void disableBulk()
    {
    	bulk_mode = false;
    	bulkSize = 0;
    }
    private String compactWrapper(String method)
    {
    	if (bulk_mode) 
		{
			bulk_of_RQs.append(method);
			bulkSize++;
		}
	    return prepRQ+method+endofRQ2; 
    }
    public String createTask(String task,String desc, int scope, int pattern) {

        return prepRQ+
                "<method name = \"createTask\" TASK=\""+
                task+"\" DESC=\""+desc+"\" SCOPE=\""+String.valueOf(scope)+"\" PATTERN=\""+String.valueOf(pattern)+"\""
                +endofRQ;
    }

    public String deleteTask(int n) {
        return prepRQ+
                "<method name = \"deleteTask\" TASK=\""+
                String.valueOf(n)+"\""
                +endofRQ;
    }

    public String updateTask(String task,String desc,int n) {
       return prepRQ+
                "<method name = \"updateTask\" TASK=\""+
                task+"\" DESC=\""+desc+"\" N=\""+String.valueOf(n)+"\""
                +endofRQ;
    }

    public String openTask(int n) {
    	String method =     "<method name = \"openTask\" TASK=\""+
    		String.valueOf(n)+"\""+endofRQ_base;    
	    if (bulk_mode) 
		{
			bulk_of_RQs.append(method);
			bulkSize++;
		}
	    return prepRQ+method+endofRQ2; 
    }

    public String closeTask(int n) {
        return prepRQ+
                "<method name = \"closeTask\" TASK=\""+
                String.valueOf(n)+"\""
                +endofRQ;
    }

    public String shareTask(int n) {
    	String method =      "<method name = \"shareTask\" TASK=\""+
        String.valueOf(n)+"\""+endofRQ_base;    
	    if (bulk_mode) 
		{
			bulk_of_RQs.append(method);bulkSize++;
		}
	    return prepRQ+method+endofRQ2; 
    }

    public String hideTask(int n) {
        return prepRQ+
                "<method name = \"hideTask\" TASK=\""+
                String.valueOf(n)+"\""
                +endofRQ;
    }
    public String unfoldTask(int n) {
    return prepRQ+
            "<method name = \"unfoldTask\" TASK=\""+
            String.valueOf(n)+"\""
            +endofRQ;
    }

    public String makeGroup(String NAME,String Descr) {
        return prepRQ+
                "<method name = \"makeGroup\" NAME=\""+
                NAME+"\" DESC=\""+
                Descr+"\""
                +endofRQ;
    }
    public String makeGroup(int id,String NAME,String Descr) {
        return prepRQ+
                "<method name = \"makeGroup\" NAME=\""+
                NAME+"\" DESC=\""+
                Descr+"\" ID=\""+
                String.valueOf(id)+"\""
                +endofRQ;
    }
    public String makeGroup(int id, int for_user,String NAME,String Descr) {
        return prepRQ+
        "<method name = \"makeGroup\" NAME=\""+
        NAME+"\" DESC=\""+
        Descr+"\" ID=\""+
        String.valueOf(id)+"\" FORUSER=\""+
        String.valueOf(for_user)+"\""
        +endofRQ;
    }
   
    public String editGroup(int n,String NAME) {
        return prepRQ+
                "<method name = \"editGroup\" ID=\""+String.valueOf(n)+"\" NAME=\""+
                NAME+"\""
                +endofRQ;
    }
    public String deleteGroup(int n) {
        return prepRQ+
                "<method name = \"deleteGroup\" N=\""+
                String.valueOf(n)+"\""
                +endofRQ;
    }
    public String listOwnGroups() {

        return prepRQ+
                "<method name = \"listOwnGroups\""
                +endofRQ;
    }
    public String listGroupMembers(int n)
    {
        return prepRQ+
                "<method name = \"listGroupMembers\" GROUP = \""+ String.valueOf(n)+"\""
                +endofRQ;
    }

    public String listTasksOwn() {
       
        return prepRQ+
                "<method name = \"listTasksOwn\""
                +endofRQ;
    }
    public String listTasksOwnW(int group) {

        return prepRQ+
                "<method name = \"listTasksOwnW\" group=\""
        		+String.valueOf(group)+"\""
                +endofRQ;
    }
    public String geatherStatistics(int n) {
    	String method = "<method name = \"geatherStatistics\" TASK=\""+
                String.valueOf(n)+"\"/>";
		if (bulk_mode) 
		{
			bulk_of_RQs.append(method);bulkSize++;
		}
        return prepRQ+method +endofRQ2;
    }

    public String representStatistics(int n) {
                return prepRQ+
                "<method name = \"representStatistics\" TASK=\""+
                String.valueOf(n)+"\""
                +endofRQ;
    }
    public String representStatistics(int n,int by_user_id) {
        return prepRQ+
        "<method name = \"representStatistics\" TASK=\""+
        String.valueOf(n)+"\" BY_USER_ID=\""+
        String.valueOf(by_user_id)
        +endofRQ;
}
    public String representAllStatistics() {
                return prepRQ+
                "<method name = \"representAllStatistics\""
                +endofRQ;
    }
    @Override
	public String registerUser(int id, int group, String LOGIN, String pswd) {
    	String method = "<method name = \"registerUser\" CORP=\"2\" GROUP=\""+String.valueOf(group)
		+"\" USERID=\""+String.valueOf(id)
		+"\" LOGIN=\""+String.valueOf(LOGIN)+"\" PSWD=\""+String.valueOf(pswd)+"\"/>";
    	
    	if (bulk_mode) 
    		{
    			bulk_of_RQs.append(method);bulkSize++;
    		//    			if (bulk_of_RQs == null) bulk_of_RQs = method;
//    			else bulk_of_RQs += method;
    		}
        return prepRQ+method+endofRQ2;
	}

	public String registerUser(int group, String LOGIN, String pswd) {
		String method = "<method name = \"registerUser\" CORP=\"2\" GROUP=\""+String.valueOf(group)
		+"\" LOGIN=\""+String.valueOf(LOGIN)+"\" PSWD=\""+String.valueOf(pswd)+"\"/>";
		if (bulk_mode) 
		{
			bulk_of_RQs.append(method);bulkSize++;
		}
        return prepRQ+method +endofRQ2;
	}
    @Override
	public String enrollToGroup(int idUser, int idGroup, String pswd) {
        return prepRQ+
		"<method name = \"enrollToGroup\"  GROUP=\""+String.valueOf(idGroup)
		+"\" USERID=\""+String.valueOf(idUser)
		+"\" PSWD=\""+String.valueOf(pswd)+"\"/>"
	    +"</RQ>";
	}
	@Override
	public String kickUserFromGroup(int idUser, int idGroup) {
		return prepRQ+
		"<method name = \"kickUserFromGroup\"  GROUP=\""+String.valueOf(idGroup)
		+"\" USER=\""+String.valueOf(idUser)
		+"\" "
	    +endofRQ;
	}
	@Override
	public String listHeirGroups(int n)
    {
        return prepRQ+
                "<method name = \"listHeirGroups\" GROUP = \""+ String.valueOf(n)+"\""
                +endofRQ;
    }
	@Override
	public String addHeirGroup(int superID, int heirID)
    {
        return prepRQ+
                "<method name = \"bindHeirGroup\" ID = \""+ String.valueOf(superID)+"\""+
                " ID_HEIR = \""+ String.valueOf(heirID)+"\""
                +endofRQ;
    }
	@Override
	public String sitTask(int taskID, String answer) {
        return prepRQ+
        "<method name = \"sitTask\" TASK=\""+String.valueOf(taskID)+"\" ANSWER=\""+
        answer+"\""
        +endofRQ;
	}
	@Override
	public String extendScopeTask(int taskID, int groupID) {
		String method =  "<method name = \"extendScopeTask\" TASK=\""+String.valueOf(taskID)+"\" GROUP=\""+
        			String.valueOf(groupID)+"\" "+endofRQ_base;    
		if (bulk_mode) 
		{
			bulk_of_RQs.append(method);bulkSize++;
		}
        return prepRQ+method+endofRQ2; 
	}
	@Override
	public String descendScopeTask(int taskID, int groupID) {
		String method =  "<method name = \"descendScopeTask\" TASK=\""+String.valueOf(taskID)+"\" GROUP=\""+
        String.valueOf(groupID)+"\" "+endofRQ_base;    
		if (bulk_mode) 
		{
			bulk_of_RQs.append(method);bulkSize++;
		}
		return prepRQ+method+endofRQ2; 
	}
	@Override
	public String listTasksAviable() {
        return prepRQ+
        	"<method name = \"listTasksAviable\""
        +endofRQ;
	}
	@Override
	public String listTasksAviable2(int gr) {
        return prepRQ+
        	"<method name = \"listTasksAviable\""
        +endofRQ;
	}
	@Override
	 public String force_subscribeTask(int task,int user) {
		String method =   "<method name = \"force_subscribeTask\" TASK=\""+
        	String.valueOf(task)+"\" SUBSCRIBER_USER=\""+
        	String.valueOf(user)+"\" "+endofRQ_base;    
		if (bulk_mode) 
		{
			bulk_of_RQs.append(method);bulkSize++;
		}
		return prepRQ+method+endofRQ2; 
    }
	@Override
	 public String force_unsubscribeTask(int task,int user) {
		String method =    "<method name = \"force_unsubscribeTask\" TASK=\""+
			String.valueOf(task)+"\" UNSUBSCRIBER_USER=\""+
			String.valueOf(user)+"\" "+endofRQ_base;    
		if (bulk_mode) 
		{
			bulk_of_RQs.append(method);bulkSize++;
		}
		return prepRQ+method+endofRQ2; 
   }
	/**
     * @return the ME
     */
    public String getME() {
        return ME;
    }

    /**
     * @param ME the ME to set
     */
    public void setME(String ME) {
        this.ME = ME;
        upprepRQ();
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
        upprepRQ();
    }

    /**
     * @return the pswd
     */
    public String getPswd() {
        return pswd;
    }

    /**
     * @param pswd the pswd to set
     */
    public void setPswd(String pswd) {
        this.pswd = pswd;
        upprepRQ();
    }
    
	@Override
	public String listGroupMembersDetailed(int group, int idRegIv) {
		String method =     "<method name = \"listGroupMembersDetailed\" GROUP=\""+
		String.valueOf(group)+"\" ID_REG_IV=\""+String.valueOf(idRegIv)+"\""+endofRQ_base;    
	    return compactWrapper(method); 
	}
	@Override
	public String getTasksDoneCount(int task) {
		String method =     "<method name = \"getTasksDoneCount\" TASK=\""+
		String.valueOf(task)+"\" "+endofRQ_base;    
	    return compactWrapper(method); 
	}
	
	

}
