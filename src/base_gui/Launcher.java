package base_gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class Launcher {

	/**
	 * @param args
	 */
	public static MainFrame MainWindow;
	public static JPanel ToolMainWidget,ToolServicesWidget,TopWidget,WorkSpaceWidget,LeftArea,CenterArea;
	//public MainFrame getMainWindow(){return MainWindow;}
	private static double MSRatio = 0.18;
	public static CardLayout TabServices, TopWidgetTabsLayout;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {		
			@Override
			public void run() {
				//RootBackgroundFrame RBFrame = new RootBackgroundFrame();
				//RBFrame.setUndecorated(true);
				//RBFrame.setPreferredSize(new Dimension(1024,896));
				MainWindow = new MainFrame();
				DivideZones();
				/*
				String [] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
				for (String font:fonts)
				{
					System.out.println(font);			
				}
				*/
				SeekServices();
				//RBFrame.add(MainWindow);
				MainWindow.setVisible(true);
				
				//RBFrame.setVisible(true);
			}
		});
		
	}
	protected static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = MainWindow.getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		}
		else 
		{
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	 
	private static void SeekServices()
	{
		//here reflectionally search for services and launches them
		
		
		
		//temporary section
			new Thread(new Runnable() {
				@Override
				public void run() {
					
					/*
					try {
						String class_name = "app_service_news.App_Service_news";
						Class toLoad = Class.forName(class_name);
						App_Service service = (App_Service) toLoad.newInstance();
						service.setMainFrameLink(MainWindow);
						service.initialize(WorkSpaceWidget,TopWidget,ToolMainWidget);
						ToolWidgetBtn tool_btn = new ToolWidgetBtn(service.getServiceWidgetName(),TabServices,TopWidgetTabsLayout,WorkSpaceWidget,TopWidget);
						ToolServicesWidget.add(tool_btn);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					*/
					try
					{
						String class_name_quiz = "app_service_quiz.App_Service_quiz";
						@SuppressWarnings("rawtypes")
						Class toLoad_quiz = Class.forName(class_name_quiz);
						App_Service service_quiz = (App_Service) toLoad_quiz.newInstance();
						service_quiz.setMainFrameLink(MainWindow);
						service_quiz.initialize(WorkSpaceWidget,TopWidget,ToolMainWidget);
						//ToolWidgetBtn tool_btn_quiz = new ToolWidgetBtn(service_quiz.getServiceWidgetName(),TabServices,TopWidgetTabsLayout,WorkSpaceWidget,TopWidget);
						//ToolServicesWidget.add(tool_btn_quiz);
						
						String class_name = "app_service_SARS_TagsTree.App_Service_SARS_TagsTree";
						@SuppressWarnings("rawtypes")
						Class toLoad = Class.forName(class_name);
						App_Service service = (App_Service) toLoad.newInstance();
						service.setMainFrameLink(MainWindow);
						service.initialize(WorkSpaceWidget,TopWidget,ToolMainWidget);
						
						//ToolWidgetBtn tool_btn = new ToolWidgetBtn(service.getServiceWidgetName(),TabServices,TopWidgetTabsLayout,WorkSpaceWidget,TopWidget);
						//ToolServicesWidget.add(tool_btn);
						
						if (service_quiz instanceof App_Service_Provider
								&&
								service instanceof	App_Service_Dependent)
							{
								((App_Service_Dependent)service).requireInitialization(((App_Service_Provider)service_quiz).provideParameters());
							}
						
						String class_name_prj = "app_service_SARS_Projects.App_Service_SARS_Projects";
						@SuppressWarnings("rawtypes")
						Class toLoad_prj = Class.forName(class_name_prj);
						App_Service service_prj = (App_Service) toLoad_prj.newInstance();
						service_prj.setMainFrameLink(MainWindow);
						
						service_prj.initialize(WorkSpaceWidget,TopWidget,ToolMainWidget);
						ToolWidgetBtn tool_btn_prj = new ToolWidgetBtn(service_prj.getServiceWidgetName(),TabServices,TopWidgetTabsLayout,WorkSpaceWidget,TopWidget);
						ToolServicesWidget.add(tool_btn_prj);
						//ToolServicesWidget.repaint();
						
						if (service instanceof App_Service_Provider
								&&
								service_prj instanceof	App_Service_Dependent)
							{
								((App_Service_Dependent)service_prj).requireInitialization(((App_Service_Provider)service).provideParameters());
							}
						/*
						String class_name_req = "app_service_SARS_Requests.App_Service_SARS_Requests";
						Class toLoad_req = Class.forName(class_name_req);
						App_Service service_req = (App_Service) toLoad_req.newInstance();
						service_req.setMainFrameLink(MainWindow);
						
						service_req.initialize(WorkSpaceWidget,TopWidget,ToolMainWidget);
						ToolWidgetBtn tool_btn_req = new ToolWidgetBtn(service_req.getServiceWidgetName(),TabServices,TopWidgetTabsLayout,WorkSpaceWidget,TopWidget);
						ToolServicesWidget.add(tool_btn_req);
						
						if (service instanceof App_Service_Provider
								&&
								service_req instanceof	App_Service_Dependent)
							{
								((App_Service_Dependent)service_req).requireInitialization(((App_Service_Provider)service).provideParameters());
							}
						*/
						String class_name_pnl = "app_service_SARS_Panels.App_Service_SARS_Panels";
						@SuppressWarnings("rawtypes")
						Class toLoad_pnl = Class.forName(class_name_pnl);
						App_Service service_pnl = (App_Service) toLoad_pnl.newInstance();
						service_pnl.setMainFrameLink(MainWindow);
						
						service_pnl.initialize(WorkSpaceWidget,TopWidget,ToolMainWidget);
						ToolWidgetBtn tool_btn_pnl = new ToolWidgetBtn(service_pnl.getServiceWidgetName(),TabServices,TopWidgetTabsLayout,WorkSpaceWidget,TopWidget);
						ToolServicesWidget.add(tool_btn_pnl);
						
						if (service instanceof App_Service_Provider
								&&
								service_pnl instanceof	App_Service_Dependent)
							{
								((App_Service_Dependent)service_pnl).requireInitialization(((App_Service_Provider)service).provideParameters());
							}
						
						//MainWindow.resize(MainWindow.getWidth(),MainWindow.getHeight());
						/*
						String class_name_cldr = "app_service_SARS_Calendar.App_Service_SARS_Calendar";
						Class toLoad_cldr = Class.forName(class_name_cldr);
						App_Service service_cldr = (App_Service) toLoad_cldr.newInstance();
						service_cldr.setMainFrameLink(MainWindow);
						
						service_cldr.initialize(WorkSpaceWidget,TopWidget,ToolMainWidget);
						ToolWidgetBtn tool_btn_cldr = new ToolWidgetBtn(service_cldr.getServiceWidgetName(),TabServices,TopWidgetTabsLayout,WorkSpaceWidget,TopWidget);
						ToolServicesWidget.add(tool_btn_cldr);
						
						if (service instanceof App_Service_Provider
								&&
								service_cldr instanceof	App_Service_Dependent)
							{
								((App_Service_Dependent)service_cldr).requireInitialization(((App_Service_Provider)service).provideParameters());
							}
						*/
						/*
						String class_name_curr = "app_service_SARS_Current.App_Service_SARS_Current";
						Class toLoad_curr = Class.forName(class_name_curr);
						App_Service service_curr = (App_Service) toLoad_curr.newInstance();
						service_curr.setMainFrameLink(MainWindow);
						
						service_curr.initialize(WorkSpaceWidget,TopWidget,ToolMainWidget);
						ToolWidgetBtn tool_btn_curr = new ToolWidgetBtn(service_curr.getServiceWidgetName(),TabServices,TopWidgetTabsLayout,WorkSpaceWidget,TopWidget);
						ToolServicesWidget.add(tool_btn_curr);
						
						if (service instanceof App_Service_Provider
								&&
								service_curr instanceof	App_Service_Dependent)
							{
								((App_Service_Dependent)service_curr).requireInitialization(((App_Service_Provider)service).provideParameters());
							}
						*/
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		return;
		// end of it
		
		/*
		String path = System.getProperty("user.dir");
		System.out.println(path);
		Wheremi where = new Wheremi();
		path += "\\src";
		System.out.println(path);
		File [] file_list = new File(path).listFiles();
		for (File fl:file_list)
		{
			System.out.println(fl.getName());
			if (fl.isDirectory()&& fl.getName().contains("app_service"))
			{
				String sub_path = path+"\\"+fl.getName();
				File [] file_sub_list = new File(sub_path).listFiles();
				for (File sb_fl:file_sub_list)
				{
					System.out.println("   "+sb_fl.getName());
					if (sb_fl.isFile()&&sb_fl.getName().startsWith("App_"))
					{
						try {
							String class_name = sb_fl.getName().substring(0,sb_fl.getName().lastIndexOf(".java"));
							Class toLoad = Class.forName(fl.getName()+"."+class_name);
							App_Service service = (App_Service) toLoad.newInstance();
							service.setMainFrameLink(MainWindow);
							service.initialize(WorkSpaceWidget,TopWidget,ToolMainWidget);
							ToolWidgetBtn tool_btn = new ToolWidgetBtn(service.getServiceWidgetName(),TabServices,TopWidgetTabsLayout,WorkSpaceWidget,TopWidget);
							ToolServicesWidget.add(tool_btn);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	*/	
	}
	private static void DivideZones()
	{
		
		LeftArea = new JPanel(new BorderLayout(3,3));
		LeftArea.setBackground(new Color(20,20,20));
		LeftArea.setPreferredSize(new Dimension((int) (MainWindow.getSize().getWidth()*MSRatio),(int)(MainWindow.getSize().getHeight())));
		
		CenterArea = new JPanel(new BorderLayout(3,3));
		CenterArea.setBackground(new Color(10,10,10));
			
		ToolServicesWidget = new JPanel();
		LeftArea.add(ToolServicesWidget,BorderLayout.CENTER);
		ToolServicesWidget.setBackground(new Color(80,80,80));
		
		ToolMainWidget = new ToolMainWidget();
		ToolMainWidget.setPreferredSize(new Dimension((int)LeftArea.getPreferredSize().getWidth(), (int)(LeftArea.getPreferredSize().getHeight()*MSRatio)));
		LeftArea.add(ToolMainWidget,BorderLayout.NORTH);
		ToolMainWidget.setBackground(new Color(80,80,80));
		
		TopWidgetTabsLayout = new CardLayout();
		TopWidget = new JPanel(TopWidgetTabsLayout);
		TopWidget.setPreferredSize(new Dimension(0,(int)(LeftArea.getPreferredSize().getHeight()*MSRatio)));
		
		//CenterArea.add(TopWidget,BorderLayout.NORTH);
		TopWidget.setBackground(Color.RED);
		
		TabServices = new CardLayout();
		WorkSpaceWidget = new JPanel(TabServices);
		//CenterArea.add(WorkSpaceWidget,BorderLayout.CENTER);
		WorkSpaceWidget.setBackground(Color.MAGENTA);
		
		JSplitPane wspaceSplitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT,TopWidget,WorkSpaceWidget);
		wspaceSplitter.setDividerLocation(200);
		wspaceSplitter.setDividerSize(3);
		wspaceSplitter.setOneTouchExpandable(true);
		CenterArea.add(wspaceSplitter);
		
		JSplitPane mainSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new JScrollPane(LeftArea),CenterArea);
		mainSplitter.setDividerLocation(200);
		mainSplitter.setDividerSize(2);
		mainSplitter.setOneTouchExpandable(true);
		MainWindow.add(mainSplitter);
		//MainWindow.add(CenterArea,BorderLayout.CENTER);
		//MainWindow.add(LeftArea,BorderLayout.WEST);
	}
}

class Wheremi
{
	public Wheremi(){}
}
