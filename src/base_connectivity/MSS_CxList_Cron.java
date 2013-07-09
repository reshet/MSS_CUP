package base_connectivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JList;


/**
 *
 * @author RX
 */
public class MSS_CxList_Cron {
private int items = 0;
private int [] autoCron;
private Vector<MSS_RQ_CxListFiller> Lupdaters;
private Vector<JList> CxLists;
private Vector<Method> Methods;
private Vector<Object []> MethodArgs;
private Service_Invokation_Handler serv_handler;
private Thread cron;
private String tURL;
public MSS_CxList_Cron(Service_Invokation_Handler serv,String URL)
{
    this.serv_handler = serv;
    this.tURL = URL;
    Lupdaters = new Vector<MSS_RQ_CxListFiller>(30);
    CxLists = new Vector<JList>(30);
    Methods = new Vector<Method>(30);
    MethodArgs = new Vector<Object []>(30);
    autoCron = new int[30];
    cron = new Thread(new Runnable() {
            public void run() {
            boolean run = true;    
            while(!Thread.currentThread().isInterrupted()&&run)
            {
                int i = 0;
                while(i < items)
                {
                    if (autoCron[i]==1)
                        try {
                            Object [] args = null;
                            try{
                                args = MethodArgs.get(i);
                            }
                            catch (ArrayIndexOutOfBoundsException ex)
                            {
                            }
                            String xml_arr_ans = MSS_RQ_Request.http_request((String) Methods.get(i).invoke(MSS_CxList_Cron.this.serv_handler,args),tURL);
                            Lupdaters.get(i).updateData(xml_arr_ans);
                            Lupdaters.get(i).fillCxList(CxLists.get(i));
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(MSS_CxList_Cron.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(MSS_CxList_Cron.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(MSS_CxList_Cron.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    i++;
                }
                try
                {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    run = false;
                    //Logger.getLogger(MSS_Table_Cron.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
           
            
            }
        });
}
public void addItem(MSS_RQ_CxListFiller filler,JList list, Method method, Object args[], boolean autoUpdating)
{
    Lupdaters.add(items, filler);
    CxLists.add(items, list);
    Methods.add(items, method);
    if (args != null) MethodArgs.add(items,args.clone());
    if (autoUpdating) autoCron[items] = 1;
    ++items;
}
public void startCron()
{
    cron.start();
}
public void stopCron()
{
    cron.interrupt();
}
public void updateItem(int it)
{
        String xml_arr_ans = null;
        try {
            Object [] args = null;
            try{
                args = MethodArgs.get(it);
            }
            catch (ArrayIndexOutOfBoundsException ex)
            {
            }
            xml_arr_ans = MSS_RQ_Request.http_request((String) Methods.get(it).invoke(MSS_CxList_Cron.this.serv_handler,args),tURL);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MSS_CxList_Cron.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MSS_CxList_Cron.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MSS_CxList_Cron.class.getName()).log(Level.SEVERE, null, ex);
        }
        Lupdaters.get(it).updateData(xml_arr_ans);
        Lupdaters.get(it).fillCxList(CxLists.get(it));
}
}
