/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RX
 */
public class MSS_RQ_Request {
    public static synchronized String http_request(String RQ_STR,String URL_str)
    {
        String ans = "";
            try {
            	System.out.println("RQ: "+RQ_STR);
                HttpRequestPoster ss = new HttpRequestPoster();
                //URL MainGate = new URL("http://localhost/services/SmartService/MainGate.php");
                //URL MainGateM = new URL("http://mplatforma.com/SmartService/MainGate.php");
                URL MainGateT = new URL(URL_str);
                String str = new String(RQ_STR.getBytes("UTF-8"));       
                String str2 = new String(str.getBytes("CP1251"));
                ans = ss.postData(str2, MainGateT);
                System.out.println("RQ-ANS: "+ans);
            } catch (Exception ex) {
                Logger.getLogger(MSS_RQ_Request.class.getName()).log(Level.SEVERE, null, ex);
                }
        return ans;
    }
    public static String http_request_async(String RQ_STR,String URL_str)
    {
        String ans = "";
            try {
            	System.out.println("RQ: "+RQ_STR);
                HttpRequestPoster ss = new HttpRequestPoster();
                //URL MainGate = new URL("http://localhost/services/SmartService/MainGate.php");
                //URL MainGateM = new URL("http://mplatforma.com/SmartService/MainGate.php");
                URL MainGateT = new URL(URL_str);
                String str = new String(RQ_STR.getBytes("UTF-8"));       
                String str2 = new String(str.getBytes("CP1251"));
                ans = ss.postData(str2, MainGateT);
                //System.out.println("RQ-ANS: "+ans);
            } catch (Exception ex) {
                Logger.getLogger(MSS_RQ_Request.class.getName()).log(Level.SEVERE, null, ex);
                }
        return ans;
    }
}


