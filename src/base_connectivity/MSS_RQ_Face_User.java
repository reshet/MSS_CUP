/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

/**
 *
 * @author RX
 */
public interface MSS_RQ_Face_User {
    public String listTasksAviable();
    public String listTasksShared();
    public String subscribeTask(int n);
    public String unsubscribeTask(int n);
    public String sitTask(int n,String ASNWER);
}
