package app_service_SARS_Projects;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;

import app_service_SARS_Panels.Setting_AssocPair;
import app_service_SARS_Panels.UserSettingsAskerWindow;
import app_service_SARS_Panels.WaitDialog;
import app_service_news.NewsServiceElementData;
import app_service_quiz.ListGroupsElementData;
import app_service_quiz.ListGroupsServerDispatcher;
import app_service_quiz.ServerDispatcher;
import base_SP_Management.SP_ServerDispatcher;
import base_SP_Management.SocioProject;
import base_SP_Management.SocioProjectInstrumentDesk;
import base_SP_Management.SocioProjectItself;
import base_connectivity.MSS_Pair;
import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_TableFiller;
import base_connectivity.MSS_RQ_XML;
import base_connectivity.MSS_RQ_XML_Pattern;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;
import base_gui.ToolMainWidget;

import com.lowagie.text.pdf.codec.Base64;

public class SProject_InstrumentDesk {
	private JList prj_list;
	public SocioProjectItself S_prj;
	private ArrayList<SocioProject> projects;
	private SP_ServerDispatcher servDisp;
	private JTabbedPane tabber;
	private Map<Integer,Setting_AssocPair<Integer, String>> ankets_content;
	private Map<Integer,Setting_AssocPair<DefaultTableModel, DefaultTableModel>> ankets_meta_cases;
	public static void fillWithMissingValues(JTable cases_table,JTable meta_table)
	{
		//Fill with proper missing values;
		//
		
		
		for(int i = 0; i < cases_table.getRowCount();i++)
		{
			for(int j = 0; j < cases_table.getColumnCount();j++)
			{
				String cur_val = "";
				if (cases_table.getModel().getValueAt(i, j) instanceof String)
					cur_val = (String)cases_table.getModel().getValueAt(i, j);
				if (cases_table.getModel().getValueAt(i, j) instanceof Integer)
					cur_val = String.valueOf(cases_table.getModel().getValueAt(i, j));
				if (cases_table.getModel().getValueAt(i, j) instanceof Double)
					cur_val = String.valueOf(cases_table.getModel().getValueAt(i, j));
				
					
				if (cur_val == null || cur_val.equals("")||cur_val.equals("null"))
				{
					//System.out.println("FOUND CUR_VAL!!!+"+cur_val);
					String col_name = cases_table.getColumnName(j);
					int row_in_meta = -1;
					//System.out.println(col_name);
					
					for(int k = 0;k < meta_table.getRowCount();k++)
					{	
						String cur_var_name = (String)meta_table.getModel().getValueAt(k, 0);
						//System.out.println("Curr - "+ cur_var_name);
						if(cur_var_name.equals(col_name))
						{
							row_in_meta = k;
							break;
						}
					}
					int col_in_meta = -1;
					for(int k = 0;k < meta_table.getColumnCount();k++)
					{
						if(meta_table.getModel().getColumnName(k).equals("NMissingValue1"))
						{
							col_in_meta = k;
							break;
						}
					}
					 System.out.println();
					 System.out.println(row_in_meta+"   "+col_in_meta);
					
					 //Cycled missing
					 if(row_in_meta == -1)
					 {
						 	String name = col_name;
							int have_ = name.lastIndexOf("_");
							if (have_ > -1)
							{
								String base_name = name.substring(have_+1,name.length());
								for(int k = 0;k < meta_table.getRowCount();k++)
								{	
									String cur_var_name = (String)meta_table.getModel().getValueAt(k, 0);
									System.out.println("Curr - "+ cur_var_name);
									if(cur_var_name.equals(base_name))
									{
										row_in_meta = k;
										System.out.println("FOUND: "+k);
										break;
									}
								}
							}
					 }
					 
					 if (row_in_meta != -1 && col_in_meta != -1)
					{
						String mis_str = (String)meta_table.getModel().getValueAt(row_in_meta,col_in_meta);
						cases_table.getModel().setValueAt(mis_str, i, j);
					}
					
				}
			}
		}
		
	}
	public SProject_InstrumentDesk(SocioProjectItself s_prj)
	{
		this.S_prj = s_prj;
		projects = S_prj.getS_projects_all();
		prj_list = new JList();
		
		prj_list.setBackground(new Color(50,60,70));
		prj_list.setSelectionBackground(new Color(69,149,38));
		prj_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		prj_list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		prj_list.setVisibleRowCount(-1);
		SPrj_CellRenderer renderer = new SPrj_CellRenderer();
		prj_list.setCellRenderer(renderer);
		
		servDisp = new SP_ServerDispatcher(prj_list);
		ankets_content = new HashMap<Integer, Setting_AssocPair<Integer,String>>();
		ankets_meta_cases = new HashMap<Integer, Setting_AssocPair<DefaultTableModel,DefaultTableModel>>();
		S_prj.setAnkets_content(ankets_content);
		S_prj.setAnkets_meta_cases(ankets_meta_cases);
		addTaskSelectionListeners();	
		//refresh_PList();
	}
	public SProject_InstrumentDesk(int plumb){}
	private void addTaskSelectionListeners()
	{
		// ADD UNFOLD ON USER CLICK FUNCTIONALITY
		
		//System.out.println("here! "+projects.size());
		for(final SocioProject spr:projects)
		{
			//System.out.println(" add Selection! ");
			spr.getWidget().getAnketTable().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					int sel = spr.getWidget().getSelectedAnketID();
					if(!ankets_meta_cases.containsKey(sel)||e.getClickCount() == 2)unfold_anket(sel);
					if(ankets_meta_cases.containsKey(sel))
					{
						Setting_AssocPair<DefaultTableModel,DefaultTableModel> pair_models = ankets_meta_cases.get(sel);
						spr.setModel(pair_models.getLeft());
						spr.setMetaModel(pair_models.getRight());
					}
				}
			});
			spr.getWidget().getAgregList().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					int sel = spr.getWidget().getAgregList().getSelectedIndex();
					ListAgregatedAnketsElementData elem = (ListAgregatedAnketsElementData)spr.getWidget().getAgregList().getModel().getElementAt(sel);
					//System.out.println("Selected! "+sel);
					SProject_InstrumentDesk.this.getSelectedSocioProject().setMetaModel((DefaultTableModel)elem.getMeta_agr_tbl().getModel());
					SProject_InstrumentDesk.this.getSelectedSocioProject().setModel((DefaultTableModel)elem.getCases_agr_tbl().getModel());
				}
			});
		}
	}
	public void setTabber(JTabbedPane tabb)
	{
		tabber = tabb;
		tabber.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				prj_list.setSelectedIndex(tabber.getSelectedIndex());
			}
		});
		tabber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				prj_list.setSelectedIndex(tabber.getSelectedIndex());				
			}
		});
		prj_list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				tabber.setSelectedIndex(prj_list.getSelectedIndex());				
			}
		});
		prj_list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tabber.setSelectedIndex(prj_list.getSelectedIndex());
			}
		});
	}
	public SocioProject getSelectedSocioProject()
	{
		if (tabber!= null && tabber.getSelectedComponent()!= null &&tabber.getSelectedComponent() instanceof SocioProject_Widget)
		{
			SocioProject sp = ((SocioProject_Widget)tabber.getSelectedComponent()).getSocioProject();
			System.out.println("Yahho!"+sp.getID_GroupOwn());
			return sp;
		} else
		return null;
	}
	public SP_ServerDispatcher getServDisp() {
		return servDisp;
	}
	public JList getprj_list() {
		return prj_list;
	}
	public void setprj_list(JList panelList) {
		prj_list = panelList;
	}
	public void createNewSP()
	{
		new create_project_Action().actionPerformed(null);
	}
	public void unfold_anket(int anketID)
	{
		new unfold_anket_Action(anketID).actionPerformed(null);
	}
	public void unfold_agreg_anket(int index_in_list)
	{
		
	}
	public void delete_SP()
	{
		new delete_project_Action().actionPerformed(null);
	}
	public void refresh_PList()
	{
		new list_projects_Action().actionPerformed(null);
		// ADD UNFOLD ON USER CLICK FUNCTIONALITY
		addTaskSelectionListeners();
	}
	public void test_FEScoder()
	{
		//new Thread(new MyRun(1,100)).start();	
		//new Thread(new MyRun(2,100)).start();
		//new Thread(new MyRun(3,100)).start();
		//new Thread(new MyRun(4,100)).start();
	}
	private void actualizeGUI(SocioProject sp)
	{
		projects.add(sp);
		DefaultListModel model = new DefaultListModel();
		ListModel mdl = prj_list.getModel();
		for(int i = 0;i < mdl.getSize();i++)
		{
			model.addElement(mdl.getElementAt(i));
		}
		
		model.addElement(sp);
		prj_list.setModel(model);
			SocioProject_Widget wdg = new SocioProject_Widget();
			wdg.adjustSocioProject(sp);
			tabber.addTab("Проект: "+sp.getName(), wdg);
	}
	private boolean checkGUI_notchanged(SocioProject sp)
	{
		for (SocioProject s: projects)
		{
			if (s.getID_GroupOwn() == sp.getID_GroupOwn()) return true;
		}
		return false;
	}
	public SProject_InstrumentDesk.unfold_anket_Action getUnfoldAction(int anketID)
	{
		SProject_InstrumentDesk.unfold_anket_Action act = new unfold_anket_Action(anketID);
		return act;
	}
	private class create_project_Action extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6663011277138149044L;
		//private int ID;
		private MSS_RQ_Admin reqHandler;
		private SocioProject sp;
		private UserSettingsAskerWindow wind;
		private Map<Integer,String> tag_categories;
		private ArrayList<String> predefined_tasks;
		public create_project_Action()
		{
			reqHandler = new MSS_RQ_Admin("Tool", "10000", "mysecret");
			putValue(NAME, "Создать проект (на сервере)!");
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			//String panelName = JOptionPane.showInputDialog("Имя нового проекта:");
			new Thread(new Runnable() {
				@SuppressWarnings("static-access")
				@Override
				public void run()
				{
					
					tag_categories = new HashMap<Integer, String>();
					tag_categories.put(100, "Заказчик");
					predefined_tasks = new ArrayList<String>();
					predefined_tasks.add(" получение брифа или задания от заказчика");
					predefined_tasks.add(" дата подготовки предложения");
					predefined_tasks.add(" дата подготовка опросника");
					predefined_tasks.add(" дата начала полевого этапа");
					predefined_tasks.add(" дата окончания полевого этапа");
					predefined_tasks.add(" дата передачи заказчику одномерных распределений");
					predefined_tasks.add(" дата передачи заказчику отчета");
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							wind = new UserSettingsAskerWindow(new UserSettingsProjectCreator(tag_categories,predefined_tasks));
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
				
					UserSettingsProjectCreator_Setting setting = (UserSettingsProjectCreator_Setting)wind.getAsker().getSetting();
					sp = new SocioProject(S_prj);	
					sp.setName(setting._projectName);
					sp.setDescr(setting._projectDescription);
					
						 String xmlans = MSS_RQ_Request.http_request(reqHandler.makeGroup(setting._projectName, setting._projectDescription),ToolMainWidget.URL);
						 MSS_RQ_TableDescriptor NewsTDesc = new MSS_RQ_TableDescriptor(new String[]{"№"},
									new Class[]{Integer.class});
						    MSS_RQ_XMLtoTableDescriptor NewsT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"ID"});
						    MSS_RQ_CxListFiller NewsUpdater = new MSS_RQ_CxListFiller(NewsTDesc,NewsT_XML_Desc,NewsServiceElementData.class);		   
						String newID = NewsUpdater.requestLocalData_FromRoot(xmlans, 1);
						sp.setID_GroupOwn(Integer.parseInt(newID));
						MSS_RQ_Request.http_request(reqHandler.addHeirGroup(100005, sp.getID_GroupOwn()),ToolMainWidget.URL);
						//MSS_RQ_User reqHandler_usr = new MSS_RQ_User("Tool", "10000", "mysecret");
						//MSS_RQ_Request.http_request(reqHandler.shareTask(Integer.parseInt(newID)),ToolMainWidget.URL);
						//MSS_RQ_Request.http_request(reqHandler_usr.subscribeTask(Integer.parseInt(newID)), ToolMainWidget.URL);
						try {
							Thread.currentThread().sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						actualizeGUI(sp);
				}
			}).start();
				
		}
	}
	public class unfold_anket_Action extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6663011277138149044L;
		//private int ID;
		private MSS_RQ_Admin reqHandler = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
		private MSS_RQ_TableDescriptor QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"№"}, new Class[]{Integer.class});
		private MSS_RQ_XMLtoTableDescriptor QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
		private MSS_RQ_TableFiller QuizUpdater = new MSS_RQ_TableFiller(QuizTDesc,QuizT_XML_Desc);;
		
		private int anketID;
		//private String task_data_decoded;
		private String task_data_unfolded;
		private JTable cases_tbl,meta_tbl;
		private String [] col_idents;
		public WaitDialog w_dlg = new WaitDialog("Загрузка анкеты с сервера...");
		public unfold_anket_Action(int anketID)
		{
			this.anketID = anketID;
			putValue(NAME, "Создать панель (на сервере)!");
		}
		public void unfoldTask()
		{
			
			task_data_unfolded = MSS_RQ_Request.http_request(reqHandler.unfoldTask(anketID),ToolMainWidget.URL);
			String ans = QuizUpdater.requestLocalData(task_data_unfolded, 0);
			if (ans!= null)
			{
				//task_data_decoded = new String(Base64.decode(ans));
			}
			//getSelectedSocioProject();
		}
		public void constructMetadata(JTable cases_tbl, JTable meta_tbl)
		{
			this.cases_tbl = cases_tbl;
			this.meta_tbl = meta_tbl;
			//METADATA READ
			
			MSS_RQ_XML xmler = new MSS_RQ_XML();
			System.out.println("METADATA DEBUG:");
			System.out.println(task_data_unfolded);
			
			ArrayList<MSS_RQ_XML_Pattern> ptn = xmler.parseXML(task_data_unfolded);
			for (MSS_RQ_XML_Pattern ptrn:ptn)
			{
				ArrayList<MSS_Pair> pairs = ptrn.getAttributes();
				if (ptrn.getNodename().equals("content"))
				{
					String sars_id = pairs.get(1).getValue();
					String sars_content = pairs.get(0).getValue();
					if(sars_id != null && sars_content!=null && ankets_content != null)
						ankets_content.put(anketID, new Setting_AssocPair<Integer, String>(Integer.parseInt(sars_id), sars_content));
					if(getSelectedSocioProject()!= null)
					{
						final SocioProjectInstrumentDesk desk = getSelectedSocioProject().getDesk();
						if(desk != null)desk.setCurrent_SARS_task(Integer.parseInt(sars_id));
						System.out.println(sars_id);
					}
				}
				if (ptrn.getNodename().equals("metadata"))
				{
					String xml_meta = pairs.get(0).getValue();
					xml_meta = new String (Base64.decode(xml_meta));
					System.out.println(xml_meta);
					ArrayList<MSS_RQ_XML_Pattern> pt = xmler.parseXML(xml_meta);
					int col_type = 2;
					int col_format = 3;
					int col_width = 4;
        			int col_decimals = 5;
        			int col_mv = 6;
        			
					DefaultTableModel tmodel = new DefaultTableModel(pt.size(), 9);
					int counter = 0;
					DefaultTableModel cases_model = new DefaultTableModel(0,pt.size());
					col_idents = new String[pt.size()]; 
					//col_idents[0] = "ID_IV";
					for (MSS_RQ_XML_Pattern prn:pt)
					{
						col_idents[counter] = prn.getNodename();
						ArrayList<MSS_Pair> pairss = prn.getAttributes();
						String quest = new String(Base64.decode(pairss.get(0).getValue()));
						String missingV = "0.0";
						String vartype = "";
						
						int found_type = -1;
						int found_alts = -1;
						int iter = 0;
						for(MSS_Pair pair:pairss)
						{
							if (pair.getName().equals("vartype")) found_type = iter;
							if (pair.getName().equals("alternatives")) found_alts = iter;
							iter++;
						}
						if(found_type > -1)
						{
							vartype = pairss.get(found_type).getValue();
						}
						if(found_alts > -1)
						{
							String alts = pairss.get(found_alts).getValue();
							ArrayList<MSS_RQ_XML_Pattern> alters = xmler.parseXML(new String(Base64.decode(alts)));
							String altern = "";
							for (MSS_RQ_XML_Pattern alter:alters)
							{
								ArrayList<MSS_Pair> alter_item_atrs = alter.getAttributes();
								String text = new String(Base64.decode(alter_item_atrs.get(0).getValue()));;
								String ID = new String(Base64.decode(alter_item_atrs.get(1).getValue()));
								//String mark = new String(Base64.decode(alter_item_atrs.get(2).getValue()));
								//System.out.println(text);
								//System.out.println(ID);
								//System.out.println(mark);
								if (ID.equals("MV")) missingV = text;
									else
								altern+="\""+ID+"\"=\""+text+"\";";
							}
							altern = altern.substring(0, altern.length()-1);
							tmodel.setValueAt(altern, counter, 8);
						}	
							tmodel.setValueAt(prn.getNodename(), counter, 0);
							tmodel.setValueAt(quest, counter, 1);
							if(vartype.equals("double")||vartype.equals("integer")||vartype.equals("defined"))
							{
								tmodel.setValueAt("0", counter, col_type);
								tmodel.setValueAt("5", counter, col_format);
							}	
							else
							if(vartype.equals("datetime"))
							{
								tmodel.setValueAt("20", counter, col_type);
								tmodel.setValueAt("22", counter, col_format);
							}else
							{
								tmodel.setValueAt("500", counter, col_type);
								tmodel.setValueAt("1", counter, col_format);
							}	
							//tmodel.setValueAt("0", counter, col_type);
							tmodel.setValueAt("8", counter, col_width);
							tmodel.setValueAt("5", counter, col_decimals);
							tmodel.setValueAt("1", counter, col_mv);
							tmodel.setValueAt(missingV, counter, 7);
							++counter;
					}
					tmodel.setColumnIdentifiers(new String[]{"VarName","Label","Type","Format","Width","Decimals","MvCode","NMissingValue1","ValueLabelTableName"});
					//final SocioProjectInstrumentDesk desk = getSelectedSocioProject().getDesk();
					
					
					//desk.getMetaTbl().setModel(tmodel);
					meta_tbl.setModel(tmodel);
					int col_labels = 8;
					//TableColumnModel tcol = desk.getMetaTbl().getColumnModel();
					TableColumnModel tcol = meta_tbl.getColumnModel();
			    	
					TableCellEditor TCEditor =  new base_SP_Management.MyValueLabelCellEditor();
			    	tcol.getColumn(col_labels).setCellEditor(TCEditor);
			    	tcol.getColumn(0).setPreferredWidth(25);
			    	tcol.getColumn(1).setPreferredWidth(400);
			    	tcol.getColumn(2).setPreferredWidth(25);
			    	tcol.getColumn(col_labels).setPreferredWidth(400);
			    	
			    	//desk.getMetaTbl().setColumnModel(tcol);
			    	meta_tbl.setColumnModel(tcol);
			    	//construct cases table model
			    	cases_model.setColumnIdentifiers(col_idents);
			    	
			    	//desk.getTbl().setModel(cases_model);
			    	cases_tbl.setModel(cases_model);
					//return col_idents;
					//here panel not a panel but a project! needed renaming, but it works without!!!
					//int projectID = desk.getS_panel().getID_GroupOwn();
					//System.out.println(projectID);
				}
			}	
		}
		public JTable getCases_tbl() {
			return cases_tbl;
		}
		public void constructCases()
		{
			@SuppressWarnings("rawtypes")
			Class [] classes = new Class[col_idents.length+1];
			classes[0] = Integer.class;
			
			for(int i = 1; i < classes.length;i++)
			{
				classes[i] = String.class;
			}
			String [] col_idents_2 = new String[col_idents.length+1];
			col_idents_2[0] ="№";
			
			for(int i = 1; i < col_idents_2.length;i++)
			{
				col_idents_2[i] = new String(col_idents[i-1]);
			}
			MSS_RQ_TableDescriptor QuizTDesc2 = new MSS_RQ_TableDescriptor(col_idents_2,classes);
			
			MSS_RQ_XMLtoTableDescriptor QuizT_XML_Desc2 = new MSS_RQ_XMLtoTableDescriptor(col_idents_2);
			MSS_RQ_TableFiller QuizUpdater2 = new MSS_RQ_TableFiller(QuizTDesc2,QuizT_XML_Desc2);
			MSS_RQ_Admin reqHandlerAdmin = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
			String xml_ans_2 = MSS_RQ_Request.http_request(reqHandlerAdmin.representStatistics(anketID),ToolMainWidget.URL);
			QuizUpdater2.updateData(xml_ans_2);
			//QuizUpdater2.fillTable(desk.getTbl());
			QuizUpdater2.fillTable(cases_tbl);
			
			//TableColumnModel cm = desk.getTbl().getColumnModel();
			TableColumnModel cm = cases_tbl.getColumnModel();
	    	
			//SProject_InstrumentDesk.fillWithMissingValues(desk.getTbl(),desk.getMetaTbl());
			SProject_InstrumentDesk.fillWithMissingValues(cases_tbl,meta_tbl);
			
			//desk.getTbl().setColumnModel(cm);
			cases_tbl.setColumnModel(cm);
		}
		public void constructCases(int by_user_id)
		{
			@SuppressWarnings("rawtypes")
			Class [] classes = new Class[col_idents.length+1];
			classes[0] = Integer.class;
			
			for(int i = 1; i < classes.length;i++)
			{
				classes[i] = String.class;
			}
			String [] col_idents_2 = new String[col_idents.length+1];
			col_idents_2[0] ="№";
			
			for(int i = 1; i < col_idents_2.length;i++)
			{
				col_idents_2[i] = new String(col_idents[i-1]);
			}
			MSS_RQ_TableDescriptor QuizTDesc2 = new MSS_RQ_TableDescriptor(col_idents_2,classes);
			
			MSS_RQ_XMLtoTableDescriptor QuizT_XML_Desc2 = new MSS_RQ_XMLtoTableDescriptor(col_idents_2);
			MSS_RQ_TableFiller QuizUpdater2 = new MSS_RQ_TableFiller(QuizTDesc2,QuizT_XML_Desc2);
			MSS_RQ_Admin reqHandlerAdmin = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
			String xml_ans_2 = MSS_RQ_Request.http_request(reqHandlerAdmin.representStatistics(anketID),ToolMainWidget.URL);
			QuizUpdater2.updateData(xml_ans_2);
			//QuizUpdater2.fillTable(desk.getTbl());
			QuizUpdater2.fillTable(cases_tbl);
			
			//TableColumnModel cm = desk.getTbl().getColumnModel();
			TableColumnModel cm = cases_tbl.getColumnModel();
	    	
			//SProject_InstrumentDesk.fillWithMissingValues(desk.getTbl(),desk.getMetaTbl());
			SProject_InstrumentDesk.fillWithMissingValues(cases_tbl,meta_tbl);
			for(int i = 0; i < col_idents.length;i++)
			{
				cm.getColumn(i).setPreferredWidth(100);
				cm.getColumn(i).setWidth(100);
			}
			TableCellEditor TLEditor =  new MyLinkTableCellEditor();
	    	cm.getColumn(2).setCellEditor(TLEditor);
	    	
			//desk.getTbl().setColumnModel(cm);
			cases_tbl.setColumnModel(cm);
		}
		
		@Override
		public void actionPerformed(ActionEvent ev)
		{
			w_dlg.setProgress(0);
			w_dlg.getJDialog().setVisible(true);
			System.out.println(anketID);
			@SuppressWarnings("unused")
			final int selected_row =  getSelectedSocioProject().getDesk().getTbl().getSelectedRow();
			unfoldTask();
			w_dlg.setProgress(30);
			//System.out.println(task_data_decoded);
			//System.out.println(task_data_unfolded);
			//final SocioProjectInstrumentDesk desk = getSelectedSocioProject().getDesk();
			JTable cases_tbl = new JTable();
			JTable meta_tbl = new JTable();
			
			//constructMetadata(desk.getTbl(),desk.getMetaTbl());
			constructMetadata(cases_tbl,meta_tbl);
			w_dlg.setProgress(60);
			constructCases();
			w_dlg.setProgress(90);
			ankets_meta_cases.put(anketID,new Setting_AssocPair<DefaultTableModel, DefaultTableModel>((DefaultTableModel)cases_tbl.getModel(), (DefaultTableModel)meta_tbl.getModel()));
			w_dlg.getJDialog().setVisible(false);
			/*
			new Thread(new Runnable() {
				@Override
				public void run()
				{
					while(getSelectedSocioProject().getDesk().getTbl().getSelectedRow()==selected_row)
					{
						//unfoldTask();
						//System.out.println(task_data_decoded);
						//System.out.println(task_data_unfolded);
						//constructMetadata();
						try {
							Thread.currentThread().sleep(60000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			*/
		}
	}
	private class delete_project_Action extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4996416636315377093L;
		//private int ID;
		private MSS_RQ_Admin reqHandler;
		public delete_project_Action()
		{
			reqHandler = new MSS_RQ_Admin("Tool", "10000", "mysecret");
			putValue(NAME, "Удалить проект (на сервере)!");
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			DefaultListModel model = (DefaultListModel)prj_list.getModel();
			int selected = prj_list.getSelectedIndex();
			SocioProject pan = (SocioProject) prj_list.getModel().getElementAt(selected);
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
	private class list_projects_Action extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8327207691365197932L;
		//private int ID;
		@SuppressWarnings("unused")
		private MSS_RQ_Admin reqHandler;
		private ServerDispatcher disp;
		private JList list;
		public list_projects_Action()
		{
			reqHandler = new MSS_RQ_Admin("Tool", "10000", "mysecret");
			list = new JList();
			disp = new ListGroupsServerDispatcher(list);
			putValue(NAME, "Список проектов (с сервера)!");
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			String [] params = {String.valueOf(100005)};
			WaitDialog w_dlg = new WaitDialog("Инициализация состояния проектов...");
			w_dlg.getJDialog().setVisible(true);
			disp.refreshHeirGroups(params);
			w_dlg.setProgress(50);
			SocioProjectItself prj_plumb = new SocioProjectItself(10000); 
			for (int i = 0; i < list.getModel().getSize();i++)
			{
				ListGroupsElementData elem = (ListGroupsElementData)list.getModel().getElementAt(i);
				SocioProject sp = new SocioProject(prj_plumb);
				sp.setName(elem.getName());
				sp.setDescr(elem.getDesc());
				sp.setID_GroupOwn(elem.getID());
				if (!checkGUI_notchanged(sp))
				{
					SocioProject spp = new SocioProject(S_prj);
					spp.setName(sp.getName());
					spp.setDescr(elem.getDesc());
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


