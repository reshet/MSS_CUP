package app_service_SARS_Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JFrame;
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

public class App_Service_SARS_Panels implements App_Service, App_Service_Dependent
{
	private static String service_name = "Панели";
	private JPanel WorkPane, TopWorkspace;
	//private JLabel Header_lbl;
	private MSS_RQ_CxListFiller Updater;
	//private MSS_RQ_TableDescriptor TDesc;
	//private MSS_RQ_XMLtoTableDescriptor T_XML_Desc;
    private MSS_CxList_Cron cron;
    private Service_Invokation_Handler this_service = new MSS_RQ_NewsService();
    private JList NewsList;
    private String URL;

	private JFrame MainWindow;
	private JMenu menu;
	//private RequestFilterPanel_T RQ_panel;
	private SocioProjectMain s_prj;
	private ArrayList<SocioPanel> s_panels;
	public App_Service_SARS_Panels()
	{
		//Connectivity settings
		// TDesc = new MSS_RQ_TableDescriptor(new String[]{"в„–","Image","Header","Text","Fulltext"},
	//			 								new Class[]{Integer.class,String.class,String.class,String.class});
	  //T_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"title","description","fulltext","enclosure"});
	  // Updater = new MSS_RQ_CxListFiller(TDesc,T_XML_Desc,(Class<? extends ListElementData>) NewsServiceElementData.class);
	     this.URL = ToolMainWidget.URL;
		//-----------
		//Header_lbl = new JLabel(); 
		//Header_lbl.setForeground(new Color(200,200,200));
		//Header_lbl.setText("Рабочая область сервиса панелей.");	
		WorkPane = new JPanel(new BorderLayout());
		WorkPane.setBackground(new Color(40,60,80));
		TopWorkspace = new JPanel(new BorderLayout());
		TopWorkspace.setBackground(new Color(70,70,70));
		//TopWorkspace.add(Header_lbl);
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
			//RQ_panel = new RequestFilterPanel_T(s_panels);
			//JLabel lbl = new JLabel("LABEL");
			SPanels_TabWidget tabWidget = new SPanels_TabWidget(s_prj);
			SP_InstrumentDesk i_desk = new SP_InstrumentDesk(s_prj);
			i_desk.setTabber(tabWidget.getTabber());
			SP_ToolbarWidget toolBar = new SP_ToolbarWidget(i_desk);
			menu = new JMenu("Панели");
			JMenuItem cr_new = new JMenuItem("Создать новую панель.");
			JMenuItem p_list = new JMenuItem("Список существующих панелей");
			JMenu edit = new JMenu("Действия...");
			edit.add(new JMenuItem("Сгенерировать статистику"));
			edit.add(new JMenuItem("Необходимые действия"));
			edit.add(new JMenuItem("Объединить"));
			edit.add(new JMenuItem("Сделать подвыборку"));
			edit.add(new JMenuItem("Отправить сообщение"));
			edit.add(new JMenuItem("Составить расписание"));
			edit.add(new JMenuItem("Список проектов участия"));
			edit.add(new JMenuItem("Редактировать фильтрами"));
			menu.add(cr_new);
			menu.add(p_list);
			menu.addSeparator();
			menu.add(edit);
			
			JMenu ankets_menu = new JMenu("Репозитарий");
			JMenuItem anket_list = new JMenuItem(new Ankets_list_Action());
			JMenuItem anket_add = new JMenuItem(new Anket_add_Action());
			ankets_menu.add(anket_list);
			ankets_menu.add(anket_add);
			
			
			MainWindow.getJMenuBar().add(menu);
			MainWindow.getJMenuBar().add(ankets_menu);
			WorkPane.add(tabWidget);
			TopWorkspace.add(toolBar);
		}
	}
	private class Ankets_list_Action extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4684379079563970183L;
		public Ankets_list_Action() {
			putValue(Action.NAME, "Список анкет.");
			putValue(Action.SHORT_DESCRIPTION, "Views a list of ankets from repository");
			putValue(Action.MNEMONIC_KEY, new Integer('L'));
			//putValue(Action.ACCELERATOR_KEY, "ctrl");
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			///desk.delete_SP();
		}
	}
	private class Anket_add_Action extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4684379079563970183L;
		private JDialog dlg;
		public Anket_add_Action() {
			dlg = new JDialog();
			dlg.setTitle("Мастер добавления анкеты");
			dlg.setSize(new Dimension (400,400));
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			dlg.setLocation((int)screenSize.getWidth()/2 - (int)dlg.getSize().getWidth()/2,
					(int)screenSize.getHeight()/2 - (int)dlg.getSize().getHeight()/2);
			dlg.add(new Anket_uploader_master());
			putValue(Action.NAME, "Добвить анкету.");
			putValue(Action.SHORT_DESCRIPTION, "Adds an ankets to repository");
			putValue(Action.MNEMONIC_KEY, new Integer('A'));
			//putValue(Action.ACCELERATOR_KEY, "ctrl");
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			dlg.setVisible(true);
		}
	}
}



