/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

import com.lowagie.text.pdf.codec.Base64;

/**
 *
 * @author RX
 */
public class SARS_RQ_Admin implements SARS_RQ_Face_Admin, Service_Invokation_Handler {

    private String ME;
    private String userID;
    private String pswd;
    private String prepRQ;
    @SuppressWarnings("unused")
	private String endofRQ,endofRQ_base,endofRQ2;
    private StringBuilder bulk_of_RQs;
    private int bulkSize = 0;
    //private int loading = 0;
    private boolean bulk_mode=false;
    public SARS_RQ_Admin(String me, String user, String pswd)
    {
        this.ME = me;
        this.userID = user;
        this.pswd = pswd;
        upprepRQ();
    }
    public void upprepRQ()
    {
         this.prepRQ = "<RQ SERVICE = \"SARS\" ME = \"" +
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
    private String SingleWrap(String method)
    {
    	if (bulk_mode) 
		{
			bulk_of_RQs.append(method);
			bulkSize++;
		}
	    return prepRQ+method+endofRQ2;
    }
    public String processBulk()
    {
    	String preporRQ = "<RQ SERVICE = \"SARS\" ME = \"" +
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
	public String addTag(int idProject, int idTag) {
		String method =     "<method name = \"addTag\" ID_PROJECT=\""+
		String.valueOf(idProject)+"\" ID_TAG=\""+
		String.valueOf(idTag)+"\""+
		endofRQ_base;    
		return SingleWrap(method); 
	}
	@Override
	public String agragateTask(int idTask, int idProject, String actualityDate) {
		String method =     "<method name = \"agregateTask\" ID_TASK=\""+
		String.valueOf(idTask)+"\" ID_PROJECT=\""+
		String.valueOf(idProject)+"\" ACT_DATE=\""+
		actualityDate+"\""+
		endofRQ_base;    
		return SingleWrap(method); 

	}
	@Override
	public String createProject(String name, String desc) {
		String method =     "<method name = \"createProject\" NAME=\""+
		name+"\" DESC=\""+
		desc+"\""+
		endofRQ_base;    
		return SingleWrap(method); 
	}
	@Override
	public String createTag(int idTagCategory, String tagDesc) {
		String method =     "<method name = \"createTag\" TAG_CAT=\""+
		String.valueOf(idTagCategory)+"\" DESC=\""+
		tagDesc+"\""+
		endofRQ_base;    
		return SingleWrap(method); 
	}
	@Override
	public String createTask(String taskDesc) {
		String method =     "<method name = \"createTask\" TASK_DESC=\""+
		taskDesc+"\""+
		endofRQ_base;    
		return SingleWrap(method); 
	}
	@Override
	public String getTaskVars(int idTask) {
		String method =     "<method name = \"getTaskVars\" ID_TASK=\""+
		String.valueOf(idTask)+"\""+
		endofRQ_base;    
		return SingleWrap(method); 
	}
	@Override
	public String mailReport(int idProject, int idTask, int var1, int var2,
			String type,String email,String link) {
		String method =     "<method name = \"mailReport\" ID_PROJECT=\""+
		String.valueOf(idProject)+"\" ID_TASK=\""+
		String.valueOf(idTask)+"\" VAR=\""+
		String.valueOf(var1)+"\" VAR2=\""+
		String.valueOf(var2)+"\" TYPE=\""+
		type+"\" EMAIL=\""+
		email+"\" LINK=\""+
		Base64.encodeBytes(link.getBytes()).toString()+"\""+
		endofRQ_base;    
		return SingleWrap(method); 
	}
	@Override
	public String inviteUsersOnline(int idGroup, int idTask) {
		String method =     "<method name = \"onlineInviteUsers\" ID_GROUP=\""+
		String.valueOf(idGroup)+"\" ID_TASK=\""+
		String.valueOf(idTask)+"\"  TYPE=\"e-mail\""+
		endofRQ_base;    
		return SingleWrap(method); 
	}
	@Override
	public String createTrigger(String LEFT_CMD, String RIGHT_CMD,
			String CONDITION, String ACTION, String TYPE) {
		String method =     "<method name = \"createTrigger\" LEFT_CMD=\""+
		LEFT_CMD+"\" RIGHT_CMD=\""+
		RIGHT_CMD+"\" CONDITION=\""+
		CONDITION+"\" ACTION=\""+
		ACTION + "\" TYPE=\""+TYPE+"\" AUTO=\"true\" "+
		endofRQ_base;    
		return SingleWrap(method); 
	}
	
	

}
