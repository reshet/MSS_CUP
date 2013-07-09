package app_service_SARS_Projects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import base_SP_Management.SocioProjectItself;
import base_connectivity.MSS_CxList_Cron;
import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_NewsService;
import base_connectivity.Service_Invokation_Handler;
import base_gui.App_Service;
import base_gui.App_Service_Dependent;
import base_gui.App_Service_Provider;
import base_gui.ToolMainWidget;

public class App_Service_SARS_Projects implements App_Service, App_Service_Provider,App_Service_Dependent/*temporary decision*/{
	private static String service_name = "Проекты";
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
	//@SuppressWarnings("unchecked")
	private SocioProjectItself s_prj;
	private JMenu menu;
	public App_Service_SARS_Projects()
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
		Header_lbl.setText("Рабочая область сервиса проектов.");	
		WorkPane = new JPanel(new BorderLayout());
		WorkPane.setBackground(new Color(40,60,80));
		TopWorkspace = new JPanel(new BorderLayout());
		TopWorkspace.setBackground(new Color(70,70,70));
		TopWorkspace.add(Header_lbl);
		
		//s_prj = new SocioProjectMain(10000);
		
		/*
		final DefaultListModel listModel = new DefaultListModel();
		NewsList = new JList(listModel);
		NewsList.setBackground(new Color(50,60,70));
		NewsList.setSelectionBackground(new Color(69,149,38));
		NewsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		NewsList.setLayoutOrientation(JList.VERTICAL);
		NewsList.setVisibleRowCount(-1);
		ComplexCellRenderer renderer = new ComplexCellRenderer();
		NewsList.setCellRenderer(renderer);
		JScrollPane scroller = new JScrollPane(NewsList);
		scroller.setFocusable(true);
		scroller.setPreferredSize(new Dimension(300, 500));
		NewsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int count = e.getClickCount();
				if (count != 2) return;
				JDialog dlg = new JDialog();
				dlg.setSize(new Dimension(500,180));
				JPanel pnl = new JPanel(new BorderLayout());
				NewsServiceElementData data = (NewsServiceElementData)NewsList.getSelectedValue();
				final NewsServiceElementEditor editor = new NewsServiceElementEditor(NewsList,data.getImage(), data.getHeader(), data.getPreview(), data.getFulltext());
				pnl.add(editor,BorderLayout.CENTER);
				pnl.setBackground(new Color(89,89,83));
				dlg.setLocation(e.getXOnScreen(),e.getYOnScreen());
				dlg.setTitle("News Element Editing window");
				dlg.setAlwaysOnTop(true);
				dlg.add(pnl);
				dlg.setVisible(true);
				dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dlg.addWindowStateListener(new WindowStateListener() {
					
					@Override
					public void windowStateChanged(WindowEvent event) {
						// not working as it supposed to!!!
						int state = event.getNewState();
						System.out.print("State changed!!!!");
						if (state == WindowEvent.WINDOW_LOST_FOCUS)
						{
							final int index = NewsList.getSelectedIndex();
							NewsServiceElementData d = (NewsServiceElementData)listModel.getElementAt(index);
							d.setHeader(editor.getHeader().getText());
							d.setPreview(editor.getPreview().getText());
							d.setFulltext(editor.getContence().getText());
							listModel.setElementAt(d, index);
							NewsList.setModel(listModel);
						}
					}
				});
			}
			
		});
		toolBar = new NewsToolBar(URL,NewsList);
		WorkPane.add(toolBar,BorderLayout.NORTH);		
		
		WorkPane.add(scroller);
		*/
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
	public Object[] provideParameters() {
		Object [] params = {s_prj};
		return params;
	}
	@Override
	public void requireInitialization(Object[] params) {
		if (params[1] instanceof SocioProjectItself)
		{
			SocioProjectItself S_project = (SocioProjectItself)params[1];
			s_prj = S_project;
				Thread thr = new Thread(new Runnable() {
					@Override
					public void run() {
						//ArrayList<SocioPanel> temp_panels = s_prj.getS_panels_all();
						/*
						while(s_prj.getS_panels_all().size()==0)
						{
							try {
								Thread.currentThread();
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//temp_panels = s_prj.getS_panels_all();
						}
						*/
					}
				});
				thr.start();
				try {
					thr.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//SocioProjectWidget PRJ_W = new SocioProjectWidget(s_prj);
				menu = new JMenu("Проекты");
				MainWindow.getJMenuBar().add(menu);
			JMenuItem newPrj = new JMenuItem("Начать новый проект");
			Action act = new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 6098167974464301349L;

				@Override
				public void actionPerformed(ActionEvent arg0) {
					SProject_Creator_Mgr mgr = new SProject_Creator_Mgr();
					JDialog dlg = new JDialog();
					dlg.setTitle("Создание нового проекта...");
					dlg.setSize(new Dimension(600,600));
					dlg.add(mgr);
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					dlg.setLocation((int)screenSize.getWidth()/2 - (int)dlg.getSize().getWidth()/2,
							(int)screenSize.getHeight()/2 - (int)dlg.getSize().getHeight()/2);
				
					dlg.setVisible(true);
				}
			};
			
			act.putValue(Action.NAME,"Начать новый проект" );
			newPrj.setAction(act);
					
			JMenuItem listPrj = new JMenuItem("Текущие проекты");
			menu.add(newPrj);
			menu.add(listPrj);
			SProjects_TabWidget tabWidget = new SProjects_TabWidget(s_prj);
			SProject_InstrumentDesk i_desk = new SProject_InstrumentDesk(s_prj);
			i_desk.setTabber(tabWidget.getTabber());
			SProjects_ToolbarWidget toolBar = new SProjects_ToolbarWidget(i_desk);
			
			WorkPane.add(tabWidget);
			TopWorkspace.add(toolBar);
		}
	}
}
