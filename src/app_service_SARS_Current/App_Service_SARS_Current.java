package app_service_SARS_Current;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import base_SP_Management.SocioPanel;
import base_SP_Management.SocioProjectMain;
import base_connectivity.MSS_CxList_Cron;
import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_NewsService;
import base_connectivity.Service_Invokation_Handler;
import base_gui.App_Service;
import base_gui.App_Service_Dependent;
import base_gui.ToolMainWidget;

public class App_Service_SARS_Current implements App_Service, App_Service_Dependent
{
	private static String service_name = "Текущие события";
	private JPanel WorkPane, TopWorkspace;
	private JLabel Header_lbl;
	private MSS_RQ_CxListFiller Updater;
	//private MSS_RQ_TableDescriptor TDesc;
	//private MSS_RQ_XMLtoTableDescriptor T_XML_Desc;
    private MSS_CxList_Cron cron;
    private Service_Invokation_Handler this_service = new MSS_RQ_NewsService();
    private JList NewsList;
    private String URL;
	private JFrame MainWindow;
	//private RequestFilterPanel_T RQ_panel;
	private SocioProjectMain s_prj;
	private ArrayList<SocioPanel> s_panels;
	private JMenu menu;
	public App_Service_SARS_Current()
	{
		//Connectivity settings
		// TDesc = new MSS_RQ_TableDescriptor(new String[]{"в„–","Image","Header","Text","Fulltext"},
	//			 								new Class[]{Integer.class,String.class,String.class,String.class});
	  //T_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"title","description","fulltext","enclosure"});
	  // Updater = new MSS_RQ_CxListFiller(TDesc,T_XML_Desc,(Class<? extends ListElementData>) NewsServiceElementData.class);
	     this.URL = ToolMainWidget.URL;
		//-----------
		Header_lbl = new JLabel(); 
		Header_lbl.setForeground(new Color(200,200,200));
		Header_lbl.setText("Рабочая область сервиса текущих событий.");	
		WorkPane = new JPanel(new BorderLayout());
		WorkPane.setBackground(new Color(40,60,80));
		TopWorkspace = new JPanel(new BorderLayout());
		TopWorkspace.setBackground(new Color(70,70,70));
		TopWorkspace.add(Header_lbl);
	}
	public void initialize(JPanel work, JPanel top, JPanel MainTool)
	{
		if (cron != null) cron.stopCron();
        cron = new MSS_CxList_Cron(this_service,this.URL);
            try {
				Object [] args1 = new Object[]{(int)5,(int)38};
				cron.addItem(Updater, NewsList, MSS_RQ_NewsService.class.getMethod("getNewsCache",int.class,int.class), args1, true);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //cron.updateItem(0);
            //cron.startCron();
			work.add(WorkPane,service_name);
			top.add(TopWorkspace,service_name);
	}
	public String getServiceWidgetName()
	{
		return service_name;
	}
	public JPanel getViewer() {
		return WorkPane;
	}
	@Override
	public void setMainFrameLink(JFrame frame) {
		MainWindow = frame;
	}

	@Override
	public void requireInitialization(Object[] params) {
		if (params[0] instanceof SocioProjectMain)
		{
			SocioProjectMain S_project = (SocioProjectMain)params[0];
			s_prj = S_project;
			s_panels = s_prj.getS_panels_all();
			//Wait for initialization of required service
			if (s_panels.size()==0)
			{
				Thread thr = new Thread(new Runnable() {
					@Override
					public void run() {
						//ArrayList<SocioPanel> temp_panels = s_prj.getS_panels_all();
						/*
						while(s_prj.getS_panels_all().size()==0)
						{
							try {
								Thread.currentThread().sleep(5000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//temp_panels = s_prj.getS_panels_all();
						}
						*/
						s_panels = s_prj.getS_panels_all();
					}
				});
				thr.start();
				try {
					thr.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			JLabel lbl = new JLabel("CURRENT");
			menu = new JMenu("Текущие события");
			JMenuItem watch = new JMenuItem("Просмотр");
			JMenuItem state = new JMenuItem("Состояние проектов");
			JMenuItem actions = new JMenuItem("Необходимые действия");
			
			menu.add(watch);
			menu.add(state);
			menu.add(actions);
			MainWindow.getJMenuBar().add(menu);
		
			WorkPane.add(lbl);
		}
	}	
}
