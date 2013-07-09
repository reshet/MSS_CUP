package base_SP_Management;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;

import app_service_SARS_Panels.Setting_AssocPair;
import app_service_SARS_Panels.UMS_Message_Poster;
import app_service_SARS_Panels.UserSettingsAskerWindow;
import app_service_SARS_Panels.WaitDialog;
import app_service_SARS_Projects.DataRepresenter;
import app_service_SARS_Projects.ListReportLinksPanel;
import app_service_SARS_Projects.MyLinkTableCellEditor;
import app_service_SARS_Projects.UserSettingsAnketPanelMatcher;
import app_service_quiz.CAPI_memory_grabber;
import app_service_quiz.ListGroupsElementData;
import app_service_quiz.ListUsersElementData;
import app_service_quiz.ListUsersPanel;
import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.SARS_RQ_Admin;
import base_data_exchanger.Internal_IO_prj;
import base_gui.ToolMainWidget;

import com.lowagie.text.pdf.codec.Base64;

public class SocioProjectInstrumentDesk {
	private Internal_IO_prj format_io;
	private JList panel_list;
	public SocioProject s_prj;
	private JTable tbl,meta_tbl;
	private ListReportLinksPanel reportsPanel;
	private FlashBurnerPanel flash_burner_pnl;
	private JDialog dlgg,meta_dlgg,reports_dlg,flash_burner_dlg;
	//private ArrayList<SocioPanel> panels;
	private SP_ServerDispatcher servDisp;
	private CAPI_memory_grabber grabber;
	private int current_SARS_task;
	public CAPI_memory_grabber getGrabber() {
		return grabber;
	}
	public SocioProjectInstrumentDesk(SocioProject s_prj)
	{
		//this.panels = spanels;
		this.s_prj = s_prj;
		panel_list = s_prj.getList();
		tbl = new JTable();
		dlgg = new JDialog();
		dlgg.setSize(600,600);
		dlgg.setModal(true);
		dlgg.setAlwaysOnTop(true);
		dlgg.setTitle("Собранные данные исследования");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dlgg.setLocation((int)screenSize.getWidth()/2 - (int)dlgg.getSize().getWidth()/2,
				(int)screenSize.getHeight()/2 - (int)dlgg.getSize().getHeight()/2);
		DefaultTableModel tmodel = new DefaultTableModel(50, 30);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int i =0;i<50;i++)
		{
			for (int j =0;j<30;j++)
			{
				tmodel.setValueAt("    ", i, j);
			}
		}
		tbl.setModel(tmodel);
		TableColumnModel col_model = tbl.getColumnModel();
		for (int j =0;j<30;j++)
			
		{
			col_model.getColumn(j).setWidth(30);
		}
		DataRepresenter repres = new DataRepresenter(tbl);
		dlgg.add(repres);
		
		meta_tbl = new JTable();
		meta_dlgg = new JDialog();
		meta_dlgg.setSize(700,600);
		meta_dlgg.setModal(true);
		meta_dlgg.setAlwaysOnTop(true);
		meta_dlgg.setTitle("Метаданные анкеты");
		meta_dlgg.setLocation((int)screenSize.getWidth()/2 - (int)meta_dlgg.getSize().getWidth()/2,
				(int)screenSize.getHeight()/2 - (int)meta_dlgg.getSize().getHeight()/2);
		DefaultTableModel tmodel1 = new DefaultTableModel(50, 30);
		meta_tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int i =0;i<50;i++)
		{
			for (int j =0;j<30;j++)
			{
				tmodel1.setValueAt("empty_cell", i, j);
			}
		}
		meta_tbl.setModel(tmodel1);
		TableColumnModel col_model1 = meta_tbl.getColumnModel();
		for (int j =0;j<30;j++)
		{
			col_model1.getColumn(j).setWidth(30);
		}
		JScrollPane pane1 = new JScrollPane(meta_tbl);
		meta_dlgg.add(pane1);
		
		reportsPanel = new ListReportLinksPanel(this.s_prj);
		//reportsPanel.add(new ListReportLinksElement("link", "Link_id", new ListReportLinksElementData("http://link.php")));
		reports_dlg = new JDialog();
		reports_dlg.setSize(600,600);
		reports_dlg.setTitle("Отчеты");
		reports_dlg.setLocation((int)screenSize.getWidth()/2 - (int)reports_dlg.getSize().getWidth()/2,
				(int)screenSize.getHeight()/2 - (int)reports_dlg.getSize().getHeight()/2);
		
		JScrollPane pane2 = new JScrollPane(reportsPanel);
		reports_dlg.add(pane2);
		
		flash_burner_pnl = new FlashBurnerPanel(this.s_prj);
		//reportsPanel.add(new ListReportLinksElement("link", "Link_id", new ListReportLinksElementData("http://link.php")));
		flash_burner_dlg = new JDialog();
		flash_burner_dlg.setTitle("Прошивка флеш-карт интервьюверов для проекта");
		flash_burner_dlg.setSize(600,600);
		flash_burner_dlg.setLocation((int)screenSize.getWidth()/2 - (int)flash_burner_dlg.getSize().getWidth()/2,
				(int)screenSize.getHeight()/2 - (int)flash_burner_dlg.getSize().getHeight()/2);
		JScrollPane pane3 = new JScrollPane(flash_burner_pnl);
		flash_burner_dlg.add(pane3);
		
		grabber = new CAPI_memory_grabber(tbl, meta_tbl);
		servDisp = new SP_ServerDispatcher(panel_list);
		format_io = new Internal_IO_prj(this);
		
		//dlgg.setVisible(true);
	}
	public SP_ServerDispatcher getServDisp() {
		return servDisp;
	}
	public SocioProject getS_panel() {
		return s_prj;
	}
	public void upTagsListModel()
	{
		DefaultListModel model = s_prj.getListTagsModel();
		model.removeAllElements();
		for(SP_Tag tag:s_prj.getSp_tags())
		{
			model.addElement(tag.getName());
		}
		s_prj.setListTagsModel(model);
	}
	
	public void loadUsers()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				format_io.loadUsersCSV();
				//tbl.setModel(format_io.getTblModel());	
				s_prj.setAnalysysForm(format_io.getAnalForm());		
				s_prj.setModel(format_io.getTblModel());
				s_prj.setMetaModel(format_io.getTbl_M_Model());
				tbl.setModel(s_prj.getModel());
				meta_tbl.setModel(s_prj.getMetaModel());
			}
		}).start();
		//thr.start();
		
		//Thread.yield();
		
		//Not effective - now table representation
		/*
		DefaultListModel model = new DefaultListModel();
		for(SocioPanelMember member:format_io.getMemb_list())
		{
			model.addElement(member);
		}
		
		panel_list.setModel(model);
		*/
		//
		
	}
	public void saveUsers()
	{
		format_io.set_Model((DefaultTableModel) tbl.getModel());
		format_io.set_M_Model((DefaultTableModel) meta_tbl.getModel());
		format_io.saveUsersCSV();
	}
	public void showTableOfCases()
	{
		//UMS_send();
		if(s_prj.getModel() != null)tbl.setModel(s_prj.getModel());
		int col_labels = findColoumn("GEOCODE",tbl);
		if(col_labels != -1)
		{
			TableCellEditor TLEditor =  new MyLinkTableCellEditor();
	    	TableColumnModel tcol = tbl.getColumnModel();
	    	tcol.getColumn(col_labels).setCellEditor(TLEditor);
	    	tcol.getColumn(col_labels).setPreferredWidth(400);
	    	tbl.setColumnModel(tcol);
		}
	 	dlgg.setVisible(true);
	}
	public void showTableOfMetadata()
	{
		//UMS_send();
		if (s_prj.getMetaModel()!= null)meta_tbl.setModel(s_prj.getMetaModel());
		meta_tbl.setEnabled(true);
		int col_labels = findColoumn("ValueLabelTableName",meta_tbl);
		if(col_labels != -1)
		{
			TableColumnModel tcol = meta_tbl.getColumnModel();
	    	TableCellEditor TCEditor =  new MyValueLabelCellEditor();
	    	tcol.getColumn(col_labels).setCellEditor(TCEditor);
	    	tcol.getColumn(col_labels).setPreferredWidth(400);
	    	meta_tbl.setColumnModel(tcol);
		}
		
		meta_dlgg.setVisible(true);
	}
	
	public JTable getTbl() {
		return tbl;
	}
	public void setTbl(JTable tbl) {
		this.tbl = tbl;
	}
	public JTable getMetaTbl() {
		return meta_tbl;
	}
	public void setMetaTbl(JTable tbl) {
		this.meta_tbl = tbl;
	}
	/////
	public void SubdividePanelOnGroups()
	{
		ArrayList<SP_Tag> arr = new ArrayList<SP_Tag>();
		for(SP_Tag tag:s_prj.getSp_tags())
		{
			if(tag.isToAutoSubdivide())arr.add(tag);
		}
		for(SP_Tag tag:arr)
		{
			Set<String> keyset = new HashSet<String>();
			int found = findColoumn(tag.getName(),tbl);
			if (found!=-1)
			{
				for(int i = 1; i<tbl.getRowCount();i++)
				{
				//	TableColumnModel cmodel = (TableColumnModel) tbl.getColorModel();
				//	cmodel.
					String cell = (String) tbl.getValueAt(i, found);
					if (!keyset.contains(cell))
					{
						keyset.add(cell);
					}
				}
				for(String key:keyset)
				{
					tag.addInternalTag(new SP_Tag(key,tag.getName()+" Element"));
				}
			}
		}
		System.out.println(arr);
	}
	public static int findColoumn(String byName,JTable tabel)
	{
		for(int i = 0;i < tabel.getColumnCount();i++)
		{
			if (tabel.getColumnName(i).equals(byName))return i;
		}
		return -1;
	}
	public void deployPanel()
	{
		//servDisp.registerUserBulkEnable();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int anketID = s_prj.getWidget().getSelectedAnketID();
				JDialog dlgg = new JDialog();
				dlgg.setTitle("Подготовка к сохранению на сервере...");
				dlgg.setSize(new Dimension (400,80));
				dlgg.setLocation(400, 400);
				JProgressBar p_bar = new JProgressBar(0, tbl.getRowCount());
				p_bar.setPreferredSize(new Dimension (400,20));
				dlgg.add(p_bar);
				dlgg.setVisible(true);
				dlgg.setTitle("Сохранение на сервере во внутреннем формате MSS...");
				p_bar.setValue(0);
				int counter=0;
				for(LocalInterviewElement elem:s_prj.getLocal_interviews_stack())
				{
					int index = elem.index_in_cases_table;
					p_bar.setValue(counter/s_prj.getLocal_interviews_stack().size()*100);
					//servDisp.registerUserAuto(params_t);
					
					// add statistics
					String dura = "<RQ><interview ";
						
				//$dura_ans = base64_encode($dura_ans);		
				
					for (int j = 1; j < tbl.getColumnCount(); j++)
					{
						if(index-1 > tbl.getRowCount()) break;
						String value = (String)tbl.getValueAt(index,j);
						if(value != null && value != "")
							dura += tbl.getColumnName(j)+"=\""+value+"\" ";
					}
					dura+="/></RQ>";
					//
					System.out.println(dura);
					String ans = Base64.encodeBytes(dura.getBytes());
					servDisp.saveDATA(Integer.parseInt(elem.userID),elem.userPSWD,anketID,ans);
				}
				//servDisp.registerUserBulkProcess();
				s_prj.setLocal_interviews_stack(new ArrayList<LocalInterviewElement>());
				//Dont know how estinate time?????
				p_bar.setValue(p_bar.getMaximum());
				dlgg.setVisible(false);
				//servDisp.registerUserBulkDisable();
			}
		}).start();
		
	}
	public void showReportsPanel()
	{
		reports_dlg.setVisible(true);
	}
	public void showFlashBurner()
	{
		flash_burner_pnl.resetBurner();
		flash_burner_dlg.setVisible(true);
	}
	public void panels_subscription()
	{
		new Thread(new Runnable() {
			private UserSettingsAskerWindow wind;
			private ArrayList<Setting_AssocPair<String, Integer>> panels,ankets;
			
			@Override
			public void run() {
				panels = new ArrayList<Setting_AssocPair<String,Integer>>();
				ankets = new ArrayList<Setting_AssocPair<String,Integer>>();
				DefaultListModel _mdl = (DefaultListModel)s_prj.getWidget().getPanelss_panel().getList().getModel();
				int list_panels_size = _mdl.getSize();
				for (int i = 0; i <list_panels_size;i++)
				{
					ListGroupsElementData elem = (ListGroupsElementData)_mdl.getElementAt(i);
					panels.add(new Setting_AssocPair<String, Integer>(elem.getName(), elem.getID()));
				}
				/*
				for(Setting_AssocPair<String, Integer> pair :panels)
				{
					System.out.println(pair.getLeft()+":"+pair.getRight());
				}
				*/
				DefaultTableModel t_mdl = (DefaultTableModel)s_prj.getWidget().getAnketTable().getModel();
				int table_ankets_size = t_mdl.getRowCount();
				for (int i = 0; i <table_ankets_size;i++)
				{
					Integer ID = Integer.parseInt((String)t_mdl.getValueAt(i, 1));
					String desc = (String)t_mdl.getValueAt(i, 2);
					ankets.add(new Setting_AssocPair<String, Integer>(desc, ID));
				}
				/*
				System.out.println("ANKETS:");
				for(Setting_AssocPair<String, Integer> pair :ankets)
				{
					System.out.println(pair.getLeft()+":"+pair.getRight());
				}
				*/
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						wind = new UserSettingsAskerWindow(new UserSettingsAnketPanelMatcher(panels, ankets));
					}
				});
				Thread askSett = new Thread(new Runnable() {
					@Override
					public void run() {
						while(wind==null
								||
								(wind!=null &&!wind.isSettingDone()))
						{
							if (!Thread.currentThread().isInterrupted())
								try {
									Thread.currentThread();
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
					}
				});
				askSett.start();
				
					try {
						askSett.join();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			
				DefaultTableModel model = (DefaultTableModel)wind.getAsker().getSetting();
				final MSS_RQ_Admin reqHandler = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
				reqHandler.enableBulk();
				Map<Integer,ArrayList<Integer>> gr_users= new HashMap<Integer,ArrayList<Integer>>();
				WaitDialog dlg = new WaitDialog("Применение заданных настроек...");
				for(int j = 1; j < model.getColumnCount();j++)
				{
					int groupID = Integer.parseInt(model.getColumnName(j));
					//get user ids for group
					ArrayList<Integer> user_ids = new ArrayList<Integer>();
					ListUsersPanel us_panel = new ListUsersPanel(groupID);
					DefaultListModel model_users = (DefaultListModel)us_panel.getList().getModel();
					for(int k = 0; k< model_users.getSize();k++)
					{
						ListUsersElementData elem_user = (ListUsersElementData)model_users.getElementAt(k);
						int id_user = Integer.parseInt(elem_user.getName());
						user_ids.add(id_user);
					}
					gr_users.put(groupID, user_ids);
				}	
				for(int i = 0; i <model.getRowCount();i++)
				{
					dlg.setProgress(i/model.getRowCount()*100);
					if(reqHandler.bulkSize()>100){MSS_RQ_Request.http_request(reqHandler.processBulk(),ToolMainWidget.URL);reqHandler.enableBulk();}
					int taskID = Integer.parseInt((String)model.getValueAt(i, 0));
					for(int j = 1; j < model.getColumnCount();j++)
					{
						int groupID = Integer.parseInt(model.getColumnName(j));
						//get user ids for group
						if ((Boolean)model.getValueAt(i,j))
						{
							reqHandler.extendScopeTask(taskID, groupID);
							reqHandler.openTask(taskID);
							reqHandler.shareTask(taskID);
							ArrayList<Integer> user_ids = gr_users.get(groupID);
							for(Integer user_id:user_ids)
							{
								reqHandler.force_subscribeTask(taskID, user_id);
								if(reqHandler.bulkSize()>100){MSS_RQ_Request.http_request(reqHandler.processBulk(),ToolMainWidget.URL);reqHandler.enableBulk();}
							}
							//subscribe whole panel
						}else
						{
							reqHandler.descendScopeTask(taskID, groupID);
							ArrayList<Integer> user_ids = gr_users.get(groupID);
							for(Integer user_id:user_ids)
							{
								reqHandler.force_unsubscribeTask(taskID, user_id);
								if(reqHandler.bulkSize()>100){MSS_RQ_Request.http_request(reqHandler.processBulk(),ToolMainWidget.URL);reqHandler.enableBulk();}
							}
							//unsubscribe whole panel
						}	
					}
				}
				MSS_RQ_Request.http_request(reqHandler.processBulk(),ToolMainWidget.URL);
				reqHandler.disableBulk();
				dlg.setProgress(100);
				dlg.getJDialog().setVisible(false);
			}
		}).start();
		System.out.println("Subscription done!");
	}
	public void inviteOnlineDemo()
	{
		int id_panel = 0,id_task=0;
		DefaultListModel _mdl = (DefaultListModel)s_prj.getWidget().getPanelss_panel().getList().getModel();
		int panel_sel = s_prj.getWidget().getPanelss_panel().getList().getSelectedIndex();
		ListGroupsElementData elem = (ListGroupsElementData)_mdl.getElementAt(panel_sel);
		id_panel = elem.getID();
		
		DefaultTableModel t_mdl = (DefaultTableModel)s_prj.getWidget().getAnketTable().getModel();
		int anket_sel = s_prj.getWidget().getAnketTable().getSelectedRow();
		id_task = Integer.parseInt((String)t_mdl.getValueAt(anket_sel, 1));
		
		final SARS_RQ_Admin reqHandler = new SARS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
		MSS_RQ_Request.http_request(reqHandler.inviteUsersOnline(id_panel, id_task), ToolMainWidget.URL);
	}
	public void initNewProject()
	{
		//???Here or not here to do/
	}
	public void filterRequest()
	{
		//new FilterDialog(null);
	}
	public JList getPanel_list() {
		return panel_list;
	}
	public void setPanel_list(JList panelList) {
		panel_list = panelList;
	}
	public Internal_IO_prj getFormat_io() {
		return format_io;
	}
	public void setFormat_io(Internal_IO_prj formatIo) {
		format_io = formatIo;
	}
	public void UMS_send()
	{
		//String ID_acc = s_prj.getWidget()
		//	.getUsers_prj().getToolbar().getSelectedItem().getID_UMS_Accessor();
		//s_prj.getWidget()
		//	.getUsers_prj().getToolbar()
		//	.getUMSBtn().addActionListener(new UMS_send_Action(Integer.parseInt("268")));
	}
	public void setCurrent_SARS_task(int current_SARS_task) {
		this.current_SARS_task = current_SARS_task;
	}
		public int getCurrent_SARS_task() {
		return current_SARS_task;
	}
	public int getCurrent_MSS_task() {
		DefaultTableModel t_mdl = (DefaultTableModel)s_prj.getWidget().getAnketTable().getModel();
		int anket_sel = s_prj.getWidget().getAnketTable().getSelectedRow();
		int id_task = Integer.parseInt((String)t_mdl.getValueAt(anket_sel, 1));
		return id_task;
	}
	
	@SuppressWarnings("unused")
	private class UMS_send_Action extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -6255275367138386540L;
		private int ID;
		private String answer = "";
		private MSS_RQ_Admin reqHandler;
		private UMS_Message_Poster poster;
		public UMS_send_Action(int ID_UMS_Accesor)
		{
			reqHandler = new MSS_RQ_Admin("Tool", "10000", "mysecret");
			putValue(NAME, "Отправить сообщение!");
			ID = ID_UMS_Accesor;
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			JDialog dlgg = new JDialog();
			dlgg.setTitle("Сообщение UMS!");
			dlgg.setSize(new Dimension (400,180));
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			dlgg.setLocation((int)screenSize.getWidth()/2 - (int)dlgg.getSize().getWidth()/2,
					(int)screenSize.getHeight()/2 - (int)dlgg.getSize().getHeight()/2);
			poster = new UMS_Message_Poster(null,"","",""); 
			dlgg.add(poster);
			dlgg.setVisible(true);
			//here compose "answer" as command to send post in newsfeed!
			new Thread(new Runnable() {
				@Override
				public void run()
				{
					while (answer.equals(""))
					{
						try {
							Thread.sleep(1000);
							answer = poster.getQueryStr();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					MSS_RQ_Request.http_request(reqHandler.sitTask(ID, answer),ToolMainWidget.URL);
				}
			}).start();
		}
	}
}
