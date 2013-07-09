package app_service_SARS_Projects;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app_service_SARS_Panels.Setting_AssocPair;
import app_service_SARS_Panels.UserSettingsAskerWindow;
import base_SP_Management.SocioProject;
import base_connectivity.MSS_Pair;
import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_XML;
import base_connectivity.MSS_RQ_XML_Pattern;
import base_connectivity.SARS_RQ_Admin;
import base_gui.ToolMainWidget;

import com.explodingpixels.macwidgets.HudWidgetFactory;
import com.lowagie.text.pdf.codec.Base64;

public class ListReportLinksToolbar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 401304332962688915L;
	private JButton addBtn,delBtn,refreshBtn,mailBtn;
	private JList list;
	private JPanel toolBtns;
	private JTextField editor;
	int groupID;
	private SocioProject sp_rj;
	public void setGroupID(int groupID) 
	{
		this.groupID = groupID;
	}
	public ListReportLinksToolbar(SocioProject sp,JList lst)
	{
		this.sp_rj = sp;
		this.list = lst;
		this.setBackground(new Color(30,30,30));
		addBtn = HudWidgetFactory.createHudButton("+");
		delBtn = HudWidgetFactory.createHudButton("-");
		refreshBtn = HudWidgetFactory.createHudButton("<^>");
		mailBtn = HudWidgetFactory.createHudButton("mail@report");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final ArrayList<Setting_AssocPair<String, Integer>> vars = new ArrayList<Setting_AssocPair<String,Integer>>();
				
				int anketID = sp_rj.getWidget().getSelectedAnketID();
				if (anketID == -1) return;
				/*
				DefaultTableModel meta_tbl = (DefaultTableModel)sp_rj.getDesk().getMetaTbl().getModel();
				for(int i = 0; i < meta_tbl.getRowCount();i++)
				{
					Setting_AssocPair<String, Integer> pair = new Setting_AssocPair<String, Integer>((String)meta_tbl.getValueAt(i,0), i);
					vars.add(pair);
				}
				*/
				SARS_RQ_Admin reqHandler = new SARS_RQ_Admin("TULA", "SUPERADMIN", "some");
				int curr_sars_task = sp_rj.getDesk().getCurrent_SARS_task();
				
				String xml_ans = MSS_RQ_Request.http_request(reqHandler.getTaskVars(curr_sars_task),ToolMainWidget.URL);
				
				MSS_RQ_XML xmler = new MSS_RQ_XML();
				ArrayList<MSS_RQ_XML_Pattern> ptn = xmler.parseXML(xml_ans);
				for (MSS_RQ_XML_Pattern ptrn:ptn)
				{
					ArrayList<MSS_Pair> pairs = ptrn.getAttributes();
					if (ptrn.getNodename().equals("var"))
					{
						int var_ID = 0;
						String lbl = "";
						for(MSS_Pair pair:pairs)
						{
							if(pair.getName().equals("ID"))var_ID = Integer.parseInt(pair.getValue());
							if(pair.getName().equals("Name"))lbl += pair.getValue();
							if(pair.getName().equals("Desc"))if(pair.getValue().length()>0)
								try {
									lbl += ": "+new String(Base64.decode(pair.getValue()),"CP1251");
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
						Setting_AssocPair<String, Integer> pair = new Setting_AssocPair<String, Integer>(lbl,var_ID);
						vars.add(pair);
					}
				}
				
				new Thread(new Runnable() {
					private UserSettingsAskerWindow wind;
					@Override
					public void run()
					{
						
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								wind = new UserSettingsAskerWindow(new UserSettingsReportLinkBuilder(vars));
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
					
						UserSettingsReportLink_Setting setting = (UserSettingsReportLink_Setting)wind.getAsker().getSetting();
						int[] arr = new int[setting.used_vars.size()];
						int counter = 0;
						String subVarStr ="";
						for(java.util.Map.Entry<Integer, String> entry:setting.used_vars.entrySet())
						{
							arr[counter++] = entry.getKey();
							subVarStr+="var"+String.valueOf(counter)+"="+arr[counter-1]+"&";
						}
						String type_str = "type=";
						if(setting.__type == UserSettingsReportLink_Setting._type.Table)
						{
							if(setting.__table_type == UserSettingsReportLink_Setting._table_type._1D)
								type_str+="tbl_1d";
							else type_str+="tbl_2d";						}
						else
						{
							if(setting.__chart_type == UserSettingsReportLink_Setting._chart_type._pie)
								type_str+="gr_pie";
							else type_str+="gr_bar";						
						}
						int curr_sars_task = sp_rj.getDesk().getCurrent_SARS_task();
						
						String link=ToolMainWidget.reportURL_begin+"?project=1&task="+curr_sars_task+"&";
						link+=subVarStr+type_str;
						ListReportLinksElementData elem = new ListReportLinksElementData(link,setting);
						DefaultListModel mdl = (DefaultListModel)list.getModel();
						mdl.addElement(elem);
						list.setModel(mdl);
						
						//HERE PERFORM TRIGGER ADD IN DATABASE IF SETTING ON
						//PLUMB FOR PRESENTATION
						if(setting.left_cond_type!= null)
						{
							MSS_RQ_Admin adm_mss = new MSS_RQ_Admin("CUP", "10000", "mysecret");
							SARS_RQ_Admin adm_sars = new SARS_RQ_Admin("TULA","SUPERADMIN", "some");
							String left_cmd = adm_mss.getTasksDoneCount(sp_rj.getDesk().getCurrent_MSS_task());
							String action = composeMailReportRequest(setting.email, setting);
							String right_cmd = setting.right_cond;
							String cond = setting.condition;
							if (cond.equals("каждые"))cond = "%";
							MSS_RQ_Request.http_request(adm_sars.createTrigger(
									Base64.encodeBytes(left_cmd.getBytes()),
									Base64.encodeBytes(right_cmd.getBytes()),
									cond,
									Base64.encodeBytes(action.getBytes()),
									"S:L"), ToolMainWidget.URL);
						}
						
					}
				}).start();
			}
		});
		delBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel model = (DefaultListModel) ListReportLinksToolbar.this.list.getModel();
				int index = list.getSelectedIndex();
				if (index == -1) return;
				int ID = ((ListReportLinksElementData)model.getElementAt(index)).getID();
				Integer [] param  = new Integer[1];
				param[0] = ID;
				//ListReportLinksToolbar.this.dispatcher.delete(param);
				refreshBtn.doClick();
			}
		});
		refreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = list.getSelectedIndex();
				//String [] params = {String.valueOf(groupID)};
				//ListReportLinksToolbar.this.dispatcher.refreshHeirGroups(params);
				DefaultListModel model = (DefaultListModel) ListReportLinksToolbar.this.list.getModel();
				int size = model.getSize();
			    delBtn.setEnabled(size > 0);
				if (size != 0 && size != index && size > index){list.setSelectedIndex(index);}
				if (size != 0 && size == index){list.setSelectedIndex(index-1);}
				if (size != 0 && size < index){list.setSelectedIndex(size-1);}
			}
		});
		mailBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String address = JOptionPane.showInputDialog("¬ведите e-mail:");
				DefaultListModel mdl = (DefaultListModel)list.getModel();
				
				ListReportLinksElementData elem = (ListReportLinksElementData)mdl.getElementAt(list.getSelectedIndex());
				UserSettingsReportLink_Setting setting = elem.getSetting();
				
//				int curr_sars_task = sp_rj.getDesk().getCurrent_SARS_task();
//				int[] arr = new int[setting.used_vars.size()];
//				int counter = 0;
//				String subVarStr ="";
//				for(java.util.Map.Entry<Integer, String> entry:setting.used_vars.entrySet())
//				{
//					arr[counter++] = entry.getKey();
//					subVarStr+="var"+String.valueOf(counter)+"="+arr[counter-1]+"&";
//				}
//				String type_str = "";
//				if(setting.__type == UserSettingsReportLink_Setting._type.Table)
//				{
//					if(setting.__table_type == UserSettingsReportLink_Setting._table_type._1D)
//						type_str+="tbl_1d";
//					else type_str+="tbl_2d";						}
//				else
//				{
//					if(setting.__chart_type == UserSettingsReportLink_Setting._chart_type._pie)
//						type_str+="gr_pie";
//					else type_str+="gr_bar";						
//				}
//				String link=ToolMainWidget.reportURL_begin+"?project=1&task="+curr_sars_task+"&";
//				link+=subVarStr+"type="+type_str;
//				
//				SARS_RQ_Admin req = new SARS_RQ_Admin("TULA","SUPERADMIN", "some");
//				MSS_RQ_Request.http_request(req.mailReport(1, curr_sars_task, arr[0], arr[1], type_str, address,link), ToolMainWidget.URL);
				MSS_RQ_Request.http_request(composeMailReportRequest(address, setting), ToolMainWidget.URL);
			}
		});
		this.setLayout(new GridLayout(2,1));
		toolBtns = new JPanel(new GridLayout(1,4));
		toolBtns.setBackground(new Color(30,30,30));
		//toolEditor = new JPanel(new BorderLayout());
		//toolEditor.setBackground(new Color(30,30,30));
		toolBtns.add(addBtn);
		toolBtns.add(delBtn);
		toolBtns.add(refreshBtn);
		toolBtns.add(mailBtn);
		
	//	toolEditor.add(editor);
		this.add(toolBtns);
		//this.add(toolEditor);
		refreshBtn.doClick();
	}
	private String composeMailReportRequest(String address,UserSettingsReportLink_Setting setting)
	{
		int curr_sars_task = sp_rj.getDesk().getCurrent_SARS_task();
		int[] arr = new int[setting.used_vars.size()];
		int counter = 0;
		String subVarStr ="";
		for(java.util.Map.Entry<Integer, String> entry:setting.used_vars.entrySet())
		{
			arr[counter++] = entry.getKey();
			subVarStr+="var"+String.valueOf(counter)+"="+arr[counter-1]+"&";
		}
		String type_str = "";
		if(setting.__type == UserSettingsReportLink_Setting._type.Table)
		{
			if(setting.__table_type == UserSettingsReportLink_Setting._table_type._1D)
				type_str+="tbl_1d";
			else type_str+="tbl_2d";						}
		else
		{
			if(setting.__chart_type == UserSettingsReportLink_Setting._chart_type._pie)
				type_str+="gr_pie";
			else type_str+="gr_bar";						
		}
		String link=ToolMainWidget.reportURL_begin+"?project=1&task="+curr_sars_task+"&";
		link+=subVarStr+"type="+type_str;
		
		SARS_RQ_Admin req = new SARS_RQ_Admin("TULA","SUPERADMIN", "some");
		return req.mailReport(1, curr_sars_task, arr[0], arr[1], type_str, address,link);
	}
	public JTextField getEditor() {
		return editor;
	}

}
