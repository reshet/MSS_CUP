/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

import java.util.Vector;

import javax.swing.JPanel;

import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author RX
 */
public class MSS_Chart_Cron {
private int items = 0;
private int [] autoCron;
private int [] IDs;
private Vector<MSS_Chart> MSS_Charts;
private Vector<JPanel> Panels;
@SuppressWarnings("unused")
private MSS_RQ_Admin admin;
private Thread cron;
public MSS_Chart_Cron(MSS_RQ_Admin admin)
{
    this.admin = admin;
    MSS_Charts = new Vector<MSS_Chart>(30);
    Panels = new Vector<JPanel>(30);
    autoCron = new int[30];
    IDs = new int [30];
    cron = new Thread(new Runnable() {
            public void run() {
            boolean run = true;
            while(!Thread.currentThread().isInterrupted()&&run)
            {
                int i = 0;
                while(i < items)
                {
                    if (autoCron[i]==1)
                   //here autoupdate
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
public void addItem(MSS_Chart chart,JPanel panel, boolean autoUpdating, int ID)
{
    MSS_Charts.add(items, chart);
    Panels.add(items, panel);
    if (autoUpdating) autoCron[items] = 1;
    IDs[items] = ID; 
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
public void updateItem(int it, DefaultCategoryDataset dataset)
{
    MSS_Charts.get(it).upDataset(dataset);
}
public boolean inItems(int ID)
{
    for (int  i = 0; i < items; i++)
        if (IDs[i] == ID) return true;
    return false;
}
public int getItemOverID(int ID)
{
    int item = -1;
    for (int i = 0; i < items; i++)
        if (IDs[i] == ID) {item = i;break;}
    return item;
}
public MSS_Chart getItem(int it)
{
    return MSS_Charts.get(it);
}
}
