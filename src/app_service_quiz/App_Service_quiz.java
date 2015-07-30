package app_service_quiz;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;

import base_SP_Management.SP_PManager;
import base_SP_Management.SP_PManager_Widget;
import base_SP_Management.SocioPanelWidget;
import base_SP_Management.SocioProjectItself;
import base_SP_Management.SocioProjectMain;
import base_connectivity.MSS_Pair;
import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_TableFiller;
import base_connectivity.MSS_RQ_XML;
import base_connectivity.MSS_RQ_XML_Pattern;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;
import base_gui.App_Service;
import base_gui.App_Service_Dependent;
import base_gui.App_Service_Provider;
import base_gui.SidePalleteUser;
import base_gui.ToolMainWidget;

import com.explodingpixels.macwidgets.HudWidgetFactory;
import com.lowagie.text.pdf.codec.Base64;

public class App_Service_quiz implements App_Service,SidePalleteUser, App_Service_Dependent,App_Service_Provider {
	private static String service_name = "Остатки старого";
	private JPanel WorkPane,TopWorkspace,WorkPaneSub1,WorkPaneSub2,TopWorkspaceSub1,TopWorkspaceSub2, ToolMainW;
	@SuppressWarnings("unused")private JPanel leftListPanel,rightListPanel;
	@SuppressWarnings("unused")
	private SocioPanelWidget SPanel;
	private JLabel Header_lbl;
	private JFrame MainWindow;
	private CardLayout WorkPaneTabber,TopPaneTabber;
	private static String TOP_Perspect1 = "TOP_Perspect1";
	private static String TOP_Perspect2 = "TOP_Perspect2";
	private static String WORK_Perspect1 = "WORK_Perspect1";
	private static String WORK_Perspect2 = "WORK_Perspect2";
	private TasksTablePanel tasksPanel;
	private TaskEditingPanel taskEditor;
	private StatisticsTablePanel statisticsPanel;
	private PlotStatPanel plotPanel;
	protected SidePallete groupsWindow,tasksWindow;
	protected SidePallete [] userWindows;
	SidePalleteSparta spWindow;
	private MSS_RQ_TableDescriptor QuizTDesc;
	private MSS_RQ_XMLtoTableDescriptor QuizT_XML_Desc;
	private MSS_RQ_TableFiller QuizUpdater;
	private MSS_RQ_Admin reqHandler;
	@SuppressWarnings("unused")
	private SidePallete UsersWindow;
	@SuppressWarnings("unused")
	private ListUsersPanel leftUsersPanel;
	@SuppressWarnings("unused")
	private ListUsersPanel[] UsersPanels; 
	@SuppressWarnings("unused")
	private int [] UserPanelsStatus;
	private SocioProjectMain s_project;
	//private SocioPanelWidget SPanel2;
	//private SidePalleteSparta spWindow2;
	private SP_PManager_Widget PrjM;
	public App_Service_quiz() {	
		Header_lbl = new JLabel();
		Header_lbl.setText("Робоча область сервісу опитувань");
		WorkPaneTabber = new CardLayout();
		WorkPane = new JPanel(WorkPaneTabber);
		WorkPaneSub1 = new JPanel();
		WorkPaneSub2 = new JPanel();
		
		tasksPanel = new TasksTablePanel(31);
		WorkPaneSub1.setLayout(new BorderLayout());
		WorkPaneSub1.add(tasksPanel);
		
		TopWorkspaceSub2 = new JPanel(new BorderLayout());
		plotPanel = new PlotStatPanel(40);
		TopWorkspaceSub2.add(plotPanel);
		
		statisticsPanel = new StatisticsTablePanel();
		(statisticsPanel).setLinked_plotter(plotPanel);
		WorkPaneSub2.setLayout(new BorderLayout());
		WorkPaneSub2.add(statisticsPanel);
		
		WorkPane.add(WorkPaneSub1,WORK_Perspect1);
		WorkPane.add(WorkPaneSub2,WORK_Perspect2);
		
		
		TopWorkspaceSub1 = new JPanel(new BorderLayout());
		TopWorkspaceSub1.setBackground(new Color(50,50,50));
		
		
		
		taskEditor = new TaskEditingPanel();
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"№"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
		QuizUpdater = new MSS_RQ_TableFiller(QuizTDesc,QuizT_XML_Desc);
		reqHandler = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
		tasksPanel.getTable().addMouseListener(new MouseAdapter() {
			private int ID;
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int selected = tasksPanel.getTable().getSelectedRow();
				int N = Integer.parseInt((String)tasksPanel.getTable().getValueAt(selected, 0))-1;
				ID = Integer.parseInt((String)tasksPanel.getTable().getValueAt(selected, 1));
				String desc = (String)tasksPanel.getTable().getValueAt(selected, 2);
				
				if (tasksPanel.getLinked_list()!=null)
					tasksPanel.getLinked_list().setSelectedIndex(N);
				taskEditor.setContence(desc);
				new Thread(new Runnable() {
					@Override
					public void run() {
						String xml_arr_ans = MSS_RQ_Request.http_request(reqHandler.unfoldTask(ID),ToolMainWidget.URL);
						//QuizUpdater.updateData(xml_arr_ans);
						String ans = QuizUpdater.requestLocalData(xml_arr_ans, 0);
						if (ans!= null)
						{
							String ans_decoded = new String(Base64.decode(ans));
							taskEditor.setKey(ans_decoded);
						}
						//METADATA READ
						
						MSS_RQ_XML xmler = new MSS_RQ_XML();
						ArrayList<MSS_RQ_XML_Pattern> ptn = xmler.parseXML(xml_arr_ans);
						for (MSS_RQ_XML_Pattern ptrn:ptn)
						{
							ArrayList<MSS_Pair> pairs = ptrn.getAttributes();
							if (ptrn.getNodename().equals("metadata"))
							{
								String xml_meta = pairs.get(0).getValue();
								xml_meta = new String (Base64.decode(xml_meta));
								System.out.println(xml_meta);
								ArrayList<MSS_RQ_XML_Pattern> pt = xmler.parseXML(xml_meta);
								DefaultTableModel tmodel = new DefaultTableModel(pt.size(), 4);
								int counter = 0;
								
								for (MSS_RQ_XML_Pattern prn:pt)
								{
									ArrayList<MSS_Pair> pairss = prn.getAttributes();
									String quest = new String(Base64.decode(pairss.get(0).getValue()));
									String alts = pairss.get(1).getValue();
									ArrayList<MSS_RQ_XML_Pattern> alters = xmler.parseXML(new String(Base64.decode(alts)));
									String altern = "";
									String missingV = "0.0";
									for (MSS_RQ_XML_Pattern alter:alters)
									{
										ArrayList<MSS_Pair> alter_item_atrs = alter.getAttributes();
										String text = new String(Base64.decode(alter_item_atrs.get(0).getValue()));;
										String ID = new String(Base64.decode(alter_item_atrs.get(1).getValue()));
										//String mark = new String(Base64.decode(alter_item_atrs.get(2).getValue()));
										//System.out.println(text);
										//System.out.println(ID);
										//System.out.println(mark);
										altern+="\""+ID+"\"=\""+text+"\";";
										if (ID.equals("MV")) missingV = text;
									}
									altern = altern.substring(0, altern.length()-1);
									tmodel.setValueAt(prn.getNodename(), counter, 0);
									tmodel.setValueAt(quest, counter, 1);
									tmodel.setValueAt(missingV, counter, 2);
									tmodel.setValueAt(altern, counter, 3);
									++counter;
								}
								tmodel.setColumnIdentifiers(new String[]{"VarName","Label","NMissingValue1","ValueLabelTableName"});
								taskEditor.getMetaTable().setModel(tmodel);
								int col_labels = 3;
								TableColumnModel tcol = taskEditor.getMetaTable().getColumnModel();
						    	TableCellEditor TCEditor =  new base_SP_Management.MyValueLabelCellEditor();
						    	tcol.getColumn(col_labels).setCellEditor(TCEditor);
						    	tcol.getColumn(0).setPreferredWidth(25);
						    	tcol.getColumn(1).setPreferredWidth(400);
						    	tcol.getColumn(2).setPreferredWidth(25);
						    	tcol.getColumn(col_labels).setPreferredWidth(400);
						    	taskEditor.getMetaTable().setColumnModel(tcol);
						    	taskEditor.getMetaTable();
						    }
						}
					}
				}).start();
			}
		});
		tasksPanel.getTable().addKeyListener(new KeyListener() {	
			private int ID;
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// 	
			}	
			@Override
			public void keyPressed(KeyEvent e) {
				int selected = tasksPanel.getTable().getSelectedRow();
				int N = Integer.parseInt((String)tasksPanel.getTable().getValueAt(selected, 0))-1;	
				ID = Integer.parseInt((String)tasksPanel.getTable().getValueAt(selected, 1));
				String desc = (String)tasksPanel.getTable().getValueAt(selected, 2);
				tasksPanel.getLinked_list().setSelectedIndex(N);
				taskEditor.setContence(desc);
				new Thread(new Runnable() {
					@Override
					public void run() {
						String xml_arr_ans = MSS_RQ_Request.http_request(reqHandler.unfoldTask(ID),ToolMainWidget.URL);
						//QuizUpdater.updateData(xml_arr_ans);
						String ans = QuizUpdater.requestLocalData(xml_arr_ans, 0);
						taskEditor.setKey(ans);
					}
				}).start();}
		});
		taskEditor.setLinked_table(tasksPanel.getTable());
		TopWorkspaceSub1.add(taskEditor);
		
		
		
		TopPaneTabber = new CardLayout();
		TopWorkspace = new JPanel(TopPaneTabber);
		TopWorkspace.add(TopWorkspaceSub1,TOP_Perspect1);
		TopWorkspace.add(TopWorkspaceSub2,TOP_Perspect2);
		
		ToolMainW = new JPanel();
		WorkPaneTabber.show(WorkPane, WORK_Perspect1);
		TopPaneTabber.show(TopWorkspace, TOP_Perspect1);
		
	}

	public void initialize(JPanel work, JPanel top, JPanel maintool) {
		//leftListPanel = new ListGroupsPanel();
		
		/////////////////////////////////////////////////
		//B E G I N Currently locked section!!!
		////////////////////////////////////////////////
		
		/*
		
		groupsWindow  = new SidePallete(leftListPanel,MainWindow);
		groupsWindow.getJDialog().setTitle("Управління групами");
		
		//Aniumation
		 Point from = new Point((int)MainWindow.getLocation().getX(),(int)MainWindow.getLocation().getY());
	     //Point to= new Point(this.getLeftSideLoc());
	     
	     
	     PropertySetter ps = new PropertySetter(groupsWindow.getJDialog(),"location",from,to);
         Animator mover = new Animator(1400,1,Animator.RepeatBehavior.LOOP,ps);
         mover.setDeceleration(.5f);
         mover.setAcceleration(.2f);
        
		 
		 
		 
		groupsWindow.getJDialog().setLocation(from);
		groupsWindow.getJDialog().setVisible(true);
		JButton groupsWindow_btn = HudWidgetFactory.createHudButton("Групи");
		groupsWindow_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				groupsWindow.getJDialog().setVisible(true);
			}
		});
		
		UsersPanels = new ListUsersPanel[50];
		userWindows = new SidePallete[50];
		UserPanelsStatus = new int[50];
		((ListGroupsPanel)leftListPanel).getList().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2)
				{
					@SuppressWarnings("unused")
					String name = ((ListGroupsElementData)
							((ListGroupsPanel)leftListPanel).getList().getModel().getElementAt(((ListGroupsPanel)leftListPanel).getList().getSelectedIndex())).getName();
					int id = ((ListGroupsElementData)
							((ListGroupsPanel)leftListPanel).getList().getModel().getElementAt(((ListGroupsPanel)leftListPanel).getList().getSelectedIndex())).getID();
					int selected_index =  ((ListGroupsPanel)leftListPanel).getList().getSelectedIndex();
					int groupID = id;
					if (UserPanelsStatus[selected_index] == groupID)
					{
						userWindows[selected_index].getJDialog().setVisible(true);
					} else
					{
						UserPanelsStatus[selected_index] = groupID;
						UsersPanels[selected_index] = new ListUsersPanel(groupID);
						//UsersPanels[selected_index];
						userWindows[selected_index]  = new SidePallete(UsersPanels[selected_index],MainWindow);
						userWindows[selected_index].getJDialog().setTitle("Панель "+String.valueOf(selected_index)+" група "+String.valueOf(groupID));
						Point from11 = new Point((int)MainWindow.getLocation().getX(),(int)MainWindow.getLocation().getY());
				        userWindows[selected_index].getJDialog().setLocation(from11);
				        userWindows[selected_index].getJDialog().setVisible(true);	
					}
				}
			}
		});
		
		/////////////////
		leftUsersPanel = new ListUsersPanel(31);
		
		UsersWindow  = new SidePallete(leftUsersPanel,MainWindow);
		UsersWindow.getJDialog().setTitle("Панель 1");
		
		//Aniumation
		 Point from11 = new Point((int)MainWindow.getLocation().getX(),(int)MainWindow.getLocation().getY());
	    // Point to11= new Point(this.getLeftSideLoc());
	     /*
	     PropertySetter ps11 = new PropertySetter(UsersWindow.getJDialog(),"location",from11,to11);
         Animator mover11 = new Animator(1400,1,Animator.RepeatBehavior.LOOP,ps11);
         mover11.setDeceleration(.5f);
         mover11.setAcceleration(.2f);
 
		UsersWindow.getJDialog().setLocation(from11);
		UsersWindow.getJDialog().setVisible(true);
		JButton UsersWindow_btn = HudWidgetFactory.createHudButton("Панель 1");
		UsersWindow_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UsersWindow.getJDialog().setVisible(true);
			}
		});
		
		/////////////////////////////////
		*/
		/////////////////////////////////////////////////
		// E N D of Currently locked section!!!
		////////////////////////////////////////////////
		s_project = new SocioProjectMain(100031);
		//SocioPanel sp = new SocioPanel(s_project);
		//String panelName = JOptionPane.showInputDialog("Имя новой панели:");
		//sp.setName(panelName);
		//SPanel = new SocioPanelWidget();
		//SPanel.adjustSocioPanel(sp);
		//spWindow  = new SidePalleteSparta(SPanel,MainWindow);
		//spWindow.setTitle(sp.getName());
		//SProject.addSocioPanel(sp);
		
		//Animation
		// Point cfrom = new Point((int)MainWindow.getLocation().getX(),(int)MainWindow.getLocation().getY());
	     //Point cto= new Point(this.getLeftSideLoc());
	     /*
	     PropertySetter cps = new PropertySetter(spWindow,"location",cfrom,cto);
         Animator cmover = new Animator(1400,1,Animator.RepeatBehavior.LOOP,cps);
         cmover.setDeceleration(.5f);
         cmover.setAcceleration(.2f);
        */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// spWindow.setLocation((int)screenSize.getWidth()/2 - (int)spWindow.getSize().getWidth()/2,
		//			(int)screenSize.getHeight()/2 - (int)spWindow.getSize().getHeight()/2);
		//spWindow.setVisible(true);
		
		/*
		JButton spWindow_btn = HudWidgetFactory.createHudButton("SocioPanel");
		spWindow_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				spWindow.setVisible(true);
			}
		});
		*/
		//Project manager prototype
		
		//SocioPanel sp2 = new SocioPanel(SProject);
		//SPanel2 = new SocioPanelWidget();
		//SPanel2.adjustSocioPanel(sp2);
		//spWindow2  = new SidePalleteSparta(SPanel2,MainWindow);
		//spWindow2.setTitle("SocioLogic2");
		
		SP_PManager _Mgr = new SP_PManager(s_project);
		PrjM = new SP_PManager_Widget(_Mgr);
		PrjM.setLocation((int)screenSize.getWidth()/2 - (int)PrjM.getSize().getWidth()/2,
				(int)screenSize.getHeight()/2 - (int)PrjM.getSize().getHeight()/2);
		JButton Manager_btn = HudWidgetFactory.createHudButton("Prj Manager");
		Manager_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//spWindow2.setVisible(true);
				PrjM.setVisible(true);
			}
		});
		/////////////
		/*
		rightListPanel = new ListTasksPanel();
		tasksPanel.setLinked_list(((ListTasksPanel) rightListPanel).getList());
		((ListTasksPanel)rightListPanel).setLinked_table(tasksPanel.getTable());
		
		tasksWindow  = new SidePallete(rightListPanel,MainWindow);
		tasksWindow.getJDialog().setTitle("Управління завданнями");
		
		 Point from2 = new Point((int)MainWindow.getLocation().getX()+MainWindow.getWidth()-170,(int)MainWindow.getLocation().getY());
	     //Point to2= new Point(this.getRightSideLoc());
	     /*
	     PropertySetter ps2 = new PropertySetter(tasksWindow.getJDialog(),"location",from2,to2);
	     mover.addTarget(ps2);
	     mover.setStartDelay(3000);
         mover.start();
        */
		 /*
        MainWindow. addComponentListener(new ComponentAdapter(){
            public void componentMoved(ComponentEvent ce){
                //System.out.println("x = "+MainWindow.getX()+", y = "+MainWindow.getY());
            	groupsWindow.getJDialog().setLocation(getLeftSideLoc());
            	tasksWindow.getJDialog().setLocation(getRightSideLoc());
            	UsersWindow.getJDialog().setLocation(getRightSideLoc());
            	
            	spWindow.setLocation(getLeftSideLoc());
              }
            });
         */
		/*
		tasksWindow.getJDialog().setLocation(from2);
		tasksWindow.getJDialog().setVisible(true);
		*/
		JButton tasksWindow_btn = HudWidgetFactory.createHudButton("Завдання");
		tasksWindow_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//tasksWindow.getJDialog().setVisible(true);
				WorkPaneTabber.show(WorkPane, WORK_Perspect1);
				TopPaneTabber.show(TopWorkspace, TOP_Perspect1);
			}
		});
		
		JButton statPanel_btn = HudWidgetFactory.createHudButton("Статистика");
		statPanel_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				WorkPaneTabber.show(WorkPane, WORK_Perspect2);
				TopPaneTabber.show(TopWorkspace, TOP_Perspect2);
			}
		});
		
		ToolMainW.setLayout(new GridLayout(6,1,2,2));
		//ToolMainW.add(Manager_btn);
		//ToolMainW.add(groupsWindow_btn);
		
		//**********USEFULL!
		//ToolMainW.add(tasksWindow_btn);
		//ToolMainW.add(statPanel_btn);
		
		//ToolMainW.add(spWindow_btn);
		//ToolMainW.add(UsersWindow_btn);
		
		
		maintool.add(ToolMainW, service_name);
		ToolMainW.setBackground(ToolMainW.getParent().getBackground());
		
		//************ CURRENTLY SWITCHED OFF
		//work.add(WorkPane, service_name);
		//top.add(TopWorkspace,service_name);
	}

	public String getServiceWidgetName() {
		return service_name;
	}
	public JPanel getViewer() {
		return WorkPane;
	}

	@Override
	public Point getLeftSideLoc() {
		Point p = MainWindow.getLocation();
		p.setLocation(p.getX()-170, p.getY());
		return p;
	}

	@Override
	public Point getNextAvaiblePlace() {
		// 
		return null;
	}

	@Override
	public Point getRightSideLoc() {
		Point p = MainWindow.getLocation();
		p.setLocation(p.getX()+MainWindow.getWidth(), p.getY());
		return p;
	}

	@Override
	public void setMainFrameLink(JFrame frame) {
		MainWindow = frame;
	}
	@Override
	public void requireInitialization(Object[] params) {
		//Should be in normal version, currently inside-out
	}

	@Override
	public Object[] provideParameters() {
		SocioProjectItself spr = new SocioProjectItself(31);
		Object [] params = {s_project,spr};
		return params;
	}
	
}
