package base_SP_Management;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import app_service_SARS_Panels.UMS_Message_Poster;
import app_service_SARS_Panels.UserSettingsAskerWindow;
import app_service_SARS_Panels.UserSettingsDeployPanelMatcher;
import app_service_SARS_Projects.DataRepresenter;
import app_service_SARS_Projects.SProject_InstrumentDesk;
import app_service_SARS_Requests.FilterDialog;
import app_service_quiz.CAPI_memory_grabber;
import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_Request;
import base_data_exchanger.Internal_IO;
import base_gui.ToolMainWidget;

import com.lowagie.text.pdf.codec.Base64;

public class SocioInstrumentDesk {
	private Internal_IO format_io;
	private JList panel_list;
	public SocioPanel s_panel;
	private JTable tbl,meta_tbl;
	private JDialog dlgg,meta_dlgg;
	//private ArrayList<SocioPanel> panels;
	private SP_ServerDispatcher servDisp;
	private CAPI_memory_grabber grabber = new CAPI_memory_grabber(tbl,meta_tbl);
	public CAPI_memory_grabber getGrabber() {
		return grabber;
	}
	private static JTable metameta_tbl;
	static{
		metameta_tbl = new JTable();
		DefaultTableModel tmodel1 = new DefaultTableModel(50, 30);
		metameta_tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int i =0;i<50;i++)
		{
			for (int j =0;j<30;j++)
			{
				tmodel1.setValueAt("empty_cell", i, j);
			}
		}
		metameta_tbl.setModel(tmodel1);
		TableColumnModel col_model1 = metameta_tbl.getColumnModel();
		for (int j =0;j<30;j++)
		{
			col_model1.getColumn(j).setWidth(30);
		}
		SProject_InstrumentDesk sp_desk = new SProject_InstrumentDesk(-1);
		SProject_InstrumentDesk.unfold_anket_Action loadMeta = sp_desk.getUnfoldAction(ToolMainWidget.register_interview);
		loadMeta.unfoldTask();
		loadMeta.constructMetadata(new JTable(), metameta_tbl);
		loadMeta.w_dlg.getJDialog().setVisible(false);
		
	}
	public SocioInstrumentDesk(SocioPanel s_panel)
	{
		//this.panels = spanels;
		this.s_panel = s_panel;
		panel_list = s_panel.getList();
		tbl = new JTable();
		dlgg = new JDialog();
		dlgg.setSize(600,600);
		dlgg.setAlwaysOnTop(true);
		dlgg.setTitle("Таблица панелистов");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dlgg.setLocation((int)screenSize.getWidth()/2 - (int)dlgg.getSize().getWidth()/2,
				(int)screenSize.getHeight()/2 - (int)dlgg.getSize().getHeight()/2);
		DefaultTableModel tmodel = new DefaultTableModel(50, 30);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int i =0;i<50;i++)
		{
			for (int j =0;j<30;j++)
			{
				tmodel.setValueAt("empty_cell", i, j);
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
		
		
		meta_dlgg = new JDialog();
		meta_dlgg.setSize(700,600);
		meta_dlgg.setAlwaysOnTop(true);
		meta_dlgg.setTitle("Регистрационная анкета");
		meta_dlgg.setLocation((int)screenSize.getWidth()/2 - (int)meta_dlgg.getSize().getWidth()/2,
				(int)screenSize.getHeight()/2 - (int)meta_dlgg.getSize().getHeight()/2);
		DefaultTableModel metamodel = new DefaultTableModel(metameta_tbl.getModel().getRowCount(),metameta_tbl.getModel().getColumnCount());
		for (int i =0;i<metameta_tbl.getModel().getRowCount();i++)
		{
			for (int j =0;j<metameta_tbl.getModel().getColumnCount();j++)
			{
				metamodel.setValueAt(metameta_tbl.getModel().getValueAt(i,j), i, j);
			}
		}
		String [] colIdents = new String[metameta_tbl.getModel().getColumnCount()];
		for (int j =0;j<metameta_tbl.getModel().getColumnCount();j++)
		{
			colIdents[j] = metameta_tbl.getColumnName(j);
		}
	
		metamodel.setColumnIdentifiers(colIdents);
		meta_tbl = new JTable(metamodel);
		JScrollPane pane1 = new JScrollPane(meta_tbl);
		meta_dlgg.add(pane1);
		
		servDisp = new SP_ServerDispatcher(panel_list);
		// loadMeta = sp_desk.getUnfoldAction(381);	
		//loadMeta.
		//loadMeta.//dlgg.setVisible(true);
	}
	public SP_ServerDispatcher getServDisp() {
		return servDisp;
	}
	public SocioPanel getS_panel() {
		return s_panel;
	}
	public void upTagsListModel()
	{
		DefaultListModel model = s_panel.getListTagsModel();
		model.removeAllElements();
		for(SP_Tag tag:s_panel.getSp_tags())
		{
			model.addElement(tag.getName());
		}
		s_panel.setListTagsModel(model);
	}
	
	public void loadUsers()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				format_io.loadUsersCSV();
				//tbl.setModel(format_io.getTblModel());	
				s_panel.setAnalysysForm(format_io.getAnalForm());		
				s_panel.setModel(format_io.getTblModel());
				s_panel.setMetaModel(format_io.getTbl_M_Model());
				tbl.setModel(s_panel.getModel());
				meta_tbl.setModel(s_panel.getMetaModel());
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
		if(s_panel.getModel() != null)tbl.setModel(s_panel.getModel());
		dlgg.setVisible(true);
	}
	public void showTableOfMetadata()
	{
		//UMS_send();
		if (s_panel.getMetaModel()!= null)meta_tbl.setModel(s_panel.getMetaModel());
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
		for(SP_Tag tag:s_panel.getSp_tags())
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
	//private String deploy_settings="";
	private UserSettingsAskerWindow wind;
	public void deployPanel()
	{
		//String [] params = {"10000","Exit-Pol Demo","This is exitpoll demo group description"};
		//servDisp.addGroupID(params);
		//String [] params2 = {"10700","10000","project manager","default"};
		//servDisp.registerUser(params2);
		//String [] params_e = {"10700","10000","default"};
		//servDisp.enroll(params_e);
		//String [] params3 = {"10700","10700","project group","description"};
		//servDisp.addGroup(params3);
		new Thread(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						SocioInstrumentDesk.this.wind = new UserSettingsAskerWindow(new UserSettingsDeployPanelMatcher(tbl, meta_tbl));
					}
				});
				Thread askSett = new Thread(new Runnable() {
					@Override
					public void run() {
						while(SocioInstrumentDesk.this.wind==null
								||
								(SocioInstrumentDesk.this.wind!=null &&!SocioInstrumentDesk.this.wind.isSettingDone()))
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
			
				Map<Integer,String> map_sett = (Map<Integer, String>)wind.getAsker().getSetting();
				
				//System.out.println("Settings:"+deploy_settings);
				
				servDisp.registerUserBulkEnable();
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
				
				//!!!!!!!!!!!!!
				//get context and groupID of a panel
				int panel_groupID = SocioInstrumentDesk.this.s_panel.getID_GroupOwn();
				//ListUsersElementData el_data = (ListUsersElementData)SocioInstrumentDesk.this.s_panel.getList().getModel().getElementAt(selected_panel);
				//int groupID = Integer.parseInt(el_data.getName());
				//
				ArrayList<Integer> userIDs = new ArrayList<Integer>();
				ArrayList<String> personal_data = new ArrayList<String>();
				
				for(int i = 0; i < tbl.getRowCount();i++)
				{
					String [] params_t = {String.valueOf(panel_groupID),(String)tbl.getValueAt(i, 1),"default"};
					servDisp.registerUserBulkAdd(params_t);
					p_bar.setValue(i);
					//servDisp.registerUserAuto(params_t);
					if(i%100 == 0 && i !=0 )
						{
							servDisp.registerUserBulkProcess();
							servDisp.registerUserBulkEnable();
						}
					//
					// add statistics
					String dura = "<RQ><interview ";
						
				//$dura_ans = base64_encode($dura_ans);		
					for (int j = 1; j < tbl.getColumnCount(); j++)
					{
						dura +=map_sett.get(j)+"=\""+tbl.getValueAt(i,j)+"\" ";
					}
					dura+="/></RQ>";
					//
					//System.out.println(dura);
				
//					try {
//						 String str = new String(dura.getBytes("CP1251"));       
//			             String str2 = new String(str.getBytes("UTF-8"));
//			             dura = str2;
//					} catch (UnsupportedEncodingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				
					String ans = Base64.encodeBytes(dura.getBytes());
					personal_data.add(ans);
					System.out.println(dura);
					//servDisp.saveDATA(33,"default",360, ans);
				}
				String xml_ans = servDisp.registerUserBulkProcess();
				servDisp.registerUserBulkDisable();
				//parse user IDs
				try {
				 InputStream stream = new ByteArrayInputStream(xml_ans.getBytes("UTF-8"));//works good!!!!
	             DocumentBuilderFactory  D_Factory = DocumentBuilderFactory.newInstance();
		         DocumentBuilder D_Builder;
					D_Builder = D_Factory.newDocumentBuilder();
				
		            Document DOC_XML = D_Builder.parse(stream);
		            Element root = DOC_XML.getDocumentElement();
		            for (Node childNode = root.getFirstChild();childNode != null;
		                childNode = childNode.getNextSibling())
		            {
		                if (childNode instanceof Element)
		                {
		                	System.out.println(childNode.getNodeName());
		                	//System.out.println(childNode.getNodeValue());
		                	for (Node ch_ch_Node = childNode.getFirstChild();ch_ch_Node != null;
			                ch_ch_Node = ch_ch_Node.getNextSibling())
		                	{
		                		System.out.println(ch_ch_Node.getNodeName());
		                		NamedNodeMap attr = ch_ch_Node.getAttributes();
			                	if (attr != null)
			                	{
			                		for (int i = 0; i < attr.getLength();i++)
				                     {
				                        Node attr_node = attr.item(i);
				                        if (attr_node.getNodeName().equals("NEWID"))
				                        	userIDs.add(Integer.parseInt(attr_node.getNodeValue()));
				                     }	
			                	}
		                	}
		                }
		            } 
	   
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 
				
				//
				System.out.println(xml_ans);
				int counter = 0;
				for(String data:personal_data)
				{
					if (counter<=userIDs.size()-1)
					{
						servDisp.subscribe(String.valueOf(userIDs.get(counter)), "default", String.valueOf(ToolMainWidget.register_interview));
						servDisp.saveDATA(userIDs.get(counter),"default",ToolMainWidget.register_interview, data);
						counter++;
					}
				}
				//Dont know how estimate time?????
				p_bar.setValue(p_bar.getMaximum());
				dlgg.setVisible(false);
				servDisp.registerUserBulkDisable();
			}
		}).start();
		
	}
	public void initNewProject()
	{
		//???Here or not here to do/
	}
	public void filterRequest()
	{
		new FilterDialog(this.s_panel.getS_project().getS_panels_all(),this.s_panel);
	}
	public JList getPanel_list() {
		return panel_list;
	}
	public void setPanel_list(JList panelList) {
		panel_list = panelList;
	}
	public Internal_IO getFormat_io() {
		return format_io;
	}
	public void setFormat_io(Internal_IO formatIo) {
		format_io = formatIo;
	}
	public void UMS_send()
	{
		//String ID_acc = s_panel.getWidget()
		//	.getUsers_panel().getToolbar().getSelectedItem().getID_UMS_Accessor();
		//s_panel.getWidget()
		//	.getUsers_panel().getToolbar()
		//	.getUMSBtn().addActionListener(new UMS_send_Action(Integer.parseInt("268")));
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
