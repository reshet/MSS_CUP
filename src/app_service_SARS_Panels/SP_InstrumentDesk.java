package app_service_SARS_Panels;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import app_service_news.NewsServiceElementData;
import app_service_quiz.ListGroupsElementData;
import app_service_quiz.ListGroupsServerDispatcher;
import app_service_quiz.ServerDispatcher;
import base_SP_Management.SP_ServerDispatcher;
import base_SP_Management.SocioPanel;
import base_SP_Management.SocioPanelWidget;
import base_SP_Management.SocioProjectMain;
import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;
import base_gui.ToolMainWidget;

public class SP_InstrumentDesk {
	private JList panel_list;
	public SocioProjectMain S_prj;
	private ArrayList<SocioPanel> panels;
	private SP_ServerDispatcher servDisp;
	private JTabbedPane tabber;
	public SP_InstrumentDesk(SocioProjectMain s_prj)
	{
		this.S_prj = s_prj;
		panels = S_prj.getS_panels_all();
		panel_list = new JList();
		
		panel_list.setBackground(new Color(50,60,70));
		panel_list.setSelectionBackground(new Color(69,149,38));
		panel_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		panel_list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		panel_list.setVisibleRowCount(-1);
		SP_CellRenderer renderer = new SP_CellRenderer();
		panel_list.setCellRenderer(renderer);
		
		servDisp = new SP_ServerDispatcher(panel_list);
	}
	public void setTabber(JTabbedPane tabb)
	{
		tabber = tabb;
		tabber.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panel_list.setSelectedIndex(tabber.getSelectedIndex());
			}
		});
		tabber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				panel_list.setSelectedIndex(tabber.getSelectedIndex());				
			}
		});
		panel_list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				tabber.setSelectedIndex(panel_list.getSelectedIndex());				
			}
		});
		panel_list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tabber.setSelectedIndex(panel_list.getSelectedIndex());
			}
		});
	}
	public SP_ServerDispatcher getServDisp() {
		return servDisp;
	}
	public JList getPanel_list() {
		return panel_list;
	}
	public void setPanel_list(JList panelList) {
		panel_list = panelList;
	}
	public void createNewSP()
	{
		new create_panel_Action().actionPerformed(null);
	}
	public void delete_SP()
	{
		new delete_panel_Action().actionPerformed(null);
	}
	public void refresh_PList()
	{
		new list_panels_Action().actionPerformed(null);
	}
	public void test_FEScoder()
	{
		//new Thread(new MyRun(1,100)).start();	
		//new Thread(new MyRun(2,100)).start();
		//new Thread(new MyRun(3,100)).start();
		//new Thread(new MyRun(4,100)).start();
	}
	private void actualizeGUI(SocioPanel sp)
	{
		panels.add(sp);
		DefaultListModel model = new DefaultListModel();
		ListModel mdl = panel_list.getModel();
		for(int i = 0;i < mdl.getSize();i++)
		{
			model.addElement(mdl.getElementAt(i));
		}
		
		model.addElement(sp);
		panel_list.setModel(model);
			SocioPanelWidget wdg = new SocioPanelWidget();
			wdg.adjustSocioPanel(sp);
			tabber.addTab("Панель: "+sp.getName(), wdg);
	}
	private boolean checkGUI_notchanged(SocioPanel sp)
	{
		for (SocioPanel s: panels)
		{
			if (s.getID_GroupOwn() == sp.getID_GroupOwn()) return true;
		}
		return false;
	}
	@SuppressWarnings("serial")
	private class create_panel_Action extends AbstractAction
	{
		//private int ID;
		private MSS_RQ_Admin reqHandler;
		private SocioPanel sp;
		public create_panel_Action()
		{
			reqHandler = new MSS_RQ_Admin("Tool", "10000", "mysecret");
			putValue(NAME, "Создать панель (на сервере)!");
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			sp = new SocioPanel(S_prj);	
			String panelName = JOptionPane.showInputDialog("Имя новой панели:");
			sp.setName(panelName);
			new Thread(new Runnable() {
				@Override
				public void run()
				{
						 String xmlans = MSS_RQ_Request.http_request(reqHandler.makeGroup(sp.getName(), "Panel"),ToolMainWidget.URL);
						 MSS_RQ_TableDescriptor NewsTDesc = new MSS_RQ_TableDescriptor(new String[]{"№"},
									new Class[]{Integer.class});
						    MSS_RQ_XMLtoTableDescriptor NewsT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"ID"});
						    MSS_RQ_CxListFiller NewsUpdater = new MSS_RQ_CxListFiller(NewsTDesc,NewsT_XML_Desc,NewsServiceElementData.class);		   
						String newID = NewsUpdater.requestLocalData_FromRoot(xmlans, 1);
						sp.setID_GroupOwn(Integer.parseInt(newID));
						MSS_RQ_Request.http_request(reqHandler.addHeirGroup(100015, sp.getID_GroupOwn()),ToolMainWidget.URL);
						MSS_RQ_Request.http_request(reqHandler.extendScopeTask(ToolMainWidget.register_interview,sp.getID_GroupOwn()),ToolMainWidget.URL);
						
						//MSS_RQ_User reqHandler_usr = new MSS_RQ_User("Tool", "10000", "mysecret");
						//MSS_RQ_Request.http_request(reqHandler.shareTask(Integer.parseInt(newID)),ToolMainWidget.URL);
						//MSS_RQ_Request.http_request(reqHandler_usr.subscribeTask(Integer.parseInt(newID)), ToolMainWidget.URL);
				}
			}).start();
			actualizeGUI(sp);
		}
	}
	@SuppressWarnings("serial")
	private class delete_panel_Action extends AbstractAction
	{
		//private int ID;
		private MSS_RQ_Admin reqHandler;
		public delete_panel_Action()
		{
			reqHandler = new MSS_RQ_Admin("Tool", "10000", "mysecret");
			putValue(NAME, "Удалить панель (на сервере)!");
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			DefaultListModel model = (DefaultListModel)panel_list.getModel();
			int selected = panel_list.getSelectedIndex();
			SocioPanel pan = (SocioPanel) panel_list.getModel().getElementAt(selected);
			final int id_to_delete = pan.getID_GroupOwn();
			if (selected != -1)
			{
				model.removeElementAt(selected);
				tabber.remove(selected);
			}
			new Thread(new Runnable() {
				@Override
				public void run()
				{
					MSS_RQ_Request.http_request(reqHandler.deleteGroup(id_to_delete),ToolMainWidget.URL);
				}
			}).start();
		}
	}
	@SuppressWarnings("serial")
	private class list_panels_Action extends AbstractAction
	{
		//private int ID;
		@SuppressWarnings("unused")
		private MSS_RQ_Admin reqHandler;
		private ServerDispatcher disp;
		private JList list;
		public list_panels_Action()
		{
			reqHandler = new MSS_RQ_Admin("Tool", "10000", "mysecret");
			list = new JList();
			disp = new ListGroupsServerDispatcher(list);
			putValue(NAME, "Список панелей (с сервере)!");
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			String [] params = {String.valueOf(100015)};
			WaitDialog w_dlg = new WaitDialog("Инициализация состояния панелей...");
			w_dlg.getJDialog().setVisible(true);
			disp.refreshHeirGroups(params);
			w_dlg.setProgress(50);
			//SocioProjectMain prj_plumb = new SocioProjectMain(10000); 
			for (int i = 0; i < list.getModel().getSize();i++)
			{
				ListGroupsElementData elem = (ListGroupsElementData)list.getModel().getElementAt(i);
				SocioPanel sp = new SocioPanel(-1);
				sp.setName(elem.getName());
				sp.setID_GroupOwn(elem.getID());
				if (!checkGUI_notchanged(sp))
				{
					SocioPanel spp = new SocioPanel(S_prj);
					spp.setName(sp.getName());
					spp.setID_GroupOwn(sp.getID_GroupOwn());
					actualizeGUI(spp);
				}
			}
			w_dlg.setProgress(100);
			w_dlg.getJDialog().setVisible(false);
		}
	}
}

class MyRun implements Runnable
{
	private int ID;
	private int iters;
	MyRun(int threadID,int iter_numb)
	{
		super();
		this.ID = threadID;
		this.iters = iter_numb;
	}
	@Override
	public void run() {
		File f = new File("FES test thread "+String.valueOf(ID)+" log.txt");
		try {
			PrintWriter writer = new PrintWriter(f);
			for (int i = 0; i < iters;i++)
			{
				String url = "http://194.44.143.27:82/MSS/MainGate.php";
				long first,second;
				String request = "<RQ SERVICE = \"sms\" ME = \"SMSGate\" SCREEN = \"240\" USERID = \"0\">"+
				"<vertex name = \"income\" smsText = \"mrts sit "+ ID +" 176\" phone = \"sdf\" serviceNumber = \"777\" country = \"ua\"" +
				" abonentId = \"3333\" now = \"20082323435342\" md5key = \"46c1fe3e6c93959c4b0275ee7cdc61be\"/></RQ>";
				
				first = System.currentTimeMillis();
				writer.println("BEGIN Thread "+String.valueOf(ID)+": iteration "+String.valueOf(i)+" START_TIME = " + String.valueOf(first));
				writer.println("REQUEST:"+request);
				String ans = MSS_RQ_Request.http_request(request,url);
				writer.println("REQUEST-ANS:"+ans);
				second = System.currentTimeMillis();
				writer.println("END Thread "+String.valueOf(ID)+": iteration "+String.valueOf(i)
							+" END_TIME = " + String.valueOf(second)+
							" DURATION = "+String.valueOf(second-first)+"ms.");
				writer.println();
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			}
}


