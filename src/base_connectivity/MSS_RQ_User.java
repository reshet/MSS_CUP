/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

/**
 *
 * @author RX
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RX
 */
public class MSS_RQ_User implements MSS_RQ_Face_User {

    private String ME;
    private String userID;
    private String prepRQ;
    private String endofRQ;
    private String pswd;
    public MSS_RQ_User(String me, String user, String pswd)
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
                "\" P=\"1\">";
          this.endofRQ = " USERID = \"" + this.userID +
                "\" PSWD = \""+
                this.getPswd()+
                "\"/></RQ>";
    }
    public String listTasksAviable() {
        return prepRQ+
                "<method name = \"listTasksAviable\""
                +endofRQ;
    }

    public String listTasksShared() {
         return prepRQ+
                "<method name = \"listTasksShared\""
                +endofRQ;    }

    public String subscribeTask(int n) {
         return prepRQ+
                "<method name = \"subscribeTask\" TASK=\""+
                String.valueOf(n)+"\""
                +endofRQ;
    }

    public String unsubscribeTask(int n) {
         return prepRQ+
                "<method name = \"unsubscribeTask\" TASK=\""+
                String.valueOf(n)+"\""
                +endofRQ;
    }

    public String sitTask(int n,String ANSWER) {
         return prepRQ+
                "<method name = \"sitTask\" TASK=\""+
                String.valueOf(n)+"\" ANSWER=\""+ANSWER+"\""
                +endofRQ;
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
    }



}
