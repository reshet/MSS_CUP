package base_SP_Management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;

import app_service_quiz.TasksTablePanel;
import base_connectivity.MSS_Pair;
import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_TableFiller;
import base_connectivity.MSS_RQ_XML;
import base_connectivity.MSS_RQ_XML_Pattern;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;
import base_gui.ToolMainWidget;

import com.lowagie.text.pdf.codec.Base64;

public class SP_ToolbarWidget extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6420541884401407970L;
	/**
	 * 
	 */
/*	private ServerDispatcher dispatcher;
	private JList list;
	private static final long serialVersionUID = 401304332962688915L;*/
	//private JButton addBtn,delBtn,refreshBtn;
	private JButton saveBtn,loadBtn,paramsBtn,params2Btn,tagsBtn,analyticBtn,deployBtn,filterBtn,grabFlashBtn,attachAnketBtn;
	private SocioInstrumentDesk desk;
	private JPanel toolBtns;
	private JLabel infoLbl;
	public SocioInstrumentDesk getDesk() {
		return desk;
	}
	public void setDesk(SocioInstrumentDesk desk) {
		this.desk = desk;
	}
	private JTextField editor;
	public SP_ToolbarWidget()
	{
		
		/*this.dispatcher = disp;
		this.list = lst;
		this.setBackground(new Color(30,30,30));
		addBtn = HudWidgetFactory.createHudButton("+");
		delBtn = HudWidgetFactory.createHudButton("-");
		refreshBtn = HudWidgetFactory.createHudButton("<^>");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = editor.getText();
			    String[] param = new String[1];
			    param[0] = name;
			    SP_ToolbarWidget.this.dispatcher.add(param);
				refreshBtn.doClick();
			}
		});
		delBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel model = (DefaultListModel) SP_ToolbarWidget.this.list.getModel();
				int index = list.getSelectedIndex();
				if (index == -1) return;
				String ID = ((ListGroupsElementData)model.getElementAt(index)).getID();
				Integer [] param  = new Integer[1];
				param[0] = Integer.parseInt(ID);
				SP_ToolbarWidget.this.dispatcher.delete(param);
				refreshBtn.doClick();
			}
		});
		refreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = list.getSelectedIndex();
				SP_ToolbarWidget.this.dispatcher.refresh(new String[2]);
				DefaultListModel model = (DefaultListModel) SP_ToolbarWidget.this.list.getModel();
				int size = model.getSize();
			    delBtn.setEnabled(size > 0);
				if (size != 0 && size != index && size > index){list.setSelectedIndex(index);}
				if (size != 0 && size == index){list.setSelectedIndex(index-1);}
				if (size != 0 && size < index){list.setSelectedIndex(size-1);}
			}
		});
		editor = HudWidgetFactory.createHudTextField("Новий елемент...");
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2)
				{
					editor.setText(((ListGroupsElementData)list.getModel().getElementAt(list.getSelectedIndex())).getName());
				}
			}
		});
		editor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//((ListGroupsElementData)list.getModel().getElementAt(list.getSelectedIndex())).setName(editor.getText());
				String ID = ((ListGroupsElementData)list.getModel().getElementAt(list.getSelectedIndex())).getID();
				String Name = editor.getText();
				String [] param = new String[2];
				param[0] = ID;
				param[1] = Name;
				SP_ToolbarWidget.this.dispatcher.edit(param);
				refreshBtn.doClick();
			}
		});
		editor.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				//((ListGroupsElementData)list.getModel().getElementAt(list.getSelectedIndex())).setName(editor.getText());
			}
		});
		*/
		//this.setLayout(new GridLayout(2,1));
		//this.desk = desk;
		toolBtns = new JPanel(new GridLayout(1,4));
		toolBtns.setBackground(new Color(30,30,30));
		loadBtn = new JButton("Загрузить панель");
		saveBtn = new JButton("Сохр. CSV");
		paramsBtn = new JButton("Таблица панелистов");
		params2Btn = new JButton("Регистрационная анкета");
		tagsBtn = new JButton("Теги");
		analyticBtn = new JButton("Разбить");
		deployBtn = new JButton("Зарегистрировать панелистов!");
		filterBtn = new JButton("Подвыборка");
		grabFlashBtn = new JButton("Собрать с карточек");
		attachAnketBtn = new JButton("Добавить анкету");
		infoLbl = new JLabel("Всего елементов в панели:0");
		//showTableBtn = new JButton("T-View");
		loadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ToolbarWidget.this.desk.loadUsers();
				infoLbl.setText("Всего елементов в панели:"+String.valueOf(desk.getS_panel().getCol_panelsists()));
				//paramsBtn.doClick();
			}
		});
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ToolbarWidget.this.desk.saveUsers();
			}
		});
		analyticBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ToolbarWidget.this.desk.SubdividePanelOnGroups();
			}
		});
		tagsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ToolbarWidget.this.desk.upTagsListModel();
			}
		});
		deployBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//JXDatePicker dp = new JXDatePicker();
				//JDialog dlg = new JDialog();
				//dlg.setSize(new Dimension(400,400));
				//dlg.add(new JScrollPane(dp));
				//dlg.setVisible(true);
				/*
				Thread thr = new Thread(new Runnable() {
						@Override
						public void run() {
							try
							{
								//JFrame frame = new JFrame();
							//	frame.getContentPane().set
							//	frame.setTransferHandler(new TransferHandler(arg0));
								
								
					//			Runtime rt = Runtime.getRuntime();
					//			Process p = rt.exec("c:\\Program Files (x86)\\Download Master\\dmaster.exe");
							
								
								//	System.out.println(p.getInputStream().read());
							  //  InputStream in = p.getInputStream();
							   // OutputStream out = p.getOutputStream();
							   // InputStream err = p.getErrorStream();
							 //works for one minute
					//			Thread.sleep(10000);
								
					//			p.destroy() ;
							}
							catch(Exception exc){/*handle exception}		 
						}
					});
				thr.start();
				try {
					thr.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				
			
				SP_ToolbarWidget.this.desk.deployPanel();
			}
		});
		filterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ToolbarWidget.this.desk.filterRequest();
				infoLbl.setText("Всего елементов в панели:"+String.valueOf(desk.getS_panel().getCol_panelsists()));
			}
		});
		paramsBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ToolbarWidget.this.desk.showTableOfCases();
				@SuppressWarnings("unused")
				DefaultTableModel model = new DefaultTableModel();
				DefaultTableModel _Model = (DefaultTableModel) desk.getTbl().getModel();
				
				Vector<Vector<String>> allData = _Model.getDataVector();
				DefaultTableModel defmodel = new DefaultTableModel(0,_Model.getColumnCount());
				@SuppressWarnings("rawtypes")
				Vector col_ident = new Vector(_Model.getColumnCount());
				for (int i = 0;i < _Model.getColumnCount();i++)
				{
					col_ident.addElement(_Model.getColumnName(i));
				}
				defmodel.setColumnIdentifiers(col_ident);
				defmodel.setDataVector(allData, col_ident);
				SP_ToolbarWidget.this.desk.getS_panel().getWidget().setTmodel(defmodel);
				infoLbl.setText("Всего елементов в панели:"+String.valueOf(desk.getS_panel().getCol_panelsists()));
			}
		});
		params2Btn.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ToolbarWidget.this.desk.showTableOfMetadata();
				@SuppressWarnings("unused")
				DefaultTableModel model = new DefaultTableModel();
				DefaultTableModel _Model = (DefaultTableModel) desk.getMetaTbl().getModel();
				Vector<Vector<String>> allData = _Model.getDataVector();
				DefaultTableModel defmodel = new DefaultTableModel(0,_Model.getColumnCount());
				@SuppressWarnings("rawtypes")
				Vector col_ident = new Vector(_Model.getColumnCount());
				for (int i = 0;i < _Model.getColumnCount();i++)
				{
					col_ident.addElement(_Model.getColumnName(i));
				}
				defmodel.setColumnIdentifiers(col_ident);
				defmodel.setDataVector(allData, col_ident);
				SP_ToolbarWidget.this.desk.getS_panel().getWidget().setTmodel(defmodel);
				infoLbl.setText("Всего елементов в панели:"+String.valueOf(desk.getS_panel().getCol_panelsists()));
			}
		});
		grabFlashBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog dlg = new JDialog();
				dlg.setLayout(new BorderLayout());
				dlg.setSize(new Dimension(300,400));
				//getJDialog().add(busy_L);
				dlg.setTitle("Сбор данных исследования с флеш-карт");
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				dlg.setLocation((int)screenSize.getWidth()/2 - (int)dlg.getSize().getWidth()/2,
						(int)screenSize.getHeight()/2 - (int)dlg.getSize().getHeight()/2);
				dlg.setAlwaysOnTop(true);
				dlg.setVisible(true);
				dlg.add(desk.getGrabber());
				JButton ok = new JButton();
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						DefaultTableModel mdl_new = (DefaultTableModel)desk.getGrabber().getAttached_cases_table().getModel();
						DefaultTableModel mdl_src = (DefaultTableModel)desk.getGrabber().getAttached_cases_table().getModel();
						
						for (int i = 0; i < mdl_new.getRowCount();i++)
						{
							mdl_src.addRow(new Vector<String>(mdl_src.getColumnCount()));
							for(int j = 0; j < mdl_src.getColumnCount();j++)
							{
								mdl_src.setValueAt(mdl_new.getValueAt(i, j), mdl_src.getRowCount()-1, j);
							}
						}
						
						desk.getTbl().setModel(mdl_src);
						
						//desk.getS_panel().setModel(desk.getGrabber().getModel());
						//paramsBtn.doClick();	
					}
				});
				dlg.add(ok);
				
			}
		});
		attachAnketBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final JDialog dlgg = new JDialog();
				dlgg.setLayout(new BorderLayout(3,3));
				dlgg.setSize(500,500);
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				dlgg.setLocation((int)screenSize.getWidth()/2 - (int)dlgg.getSize().getWidth()/2,
						(int)screenSize.getHeight()/2 - (int)dlgg.getSize().getHeight()/2);
				final TasksTablePanel pnl = new TasksTablePanel(100030);
				JButton sel = new JButton("Выбрать!!!!");
				
				dlgg.add(pnl);
				dlgg.add(sel,BorderLayout.SOUTH);
				sel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						final int anketID = Integer.parseInt((String)pnl.getTable().getValueAt(pnl.getTable().getSelectedRow(), 1));
						System.out.println(anketID);
						dlgg.setVisible(false);
						final MSS_RQ_TableDescriptor QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"№"}, new Class[]{Integer.class});
						final MSS_RQ_XMLtoTableDescriptor QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
						final MSS_RQ_TableFiller QuizUpdater = new MSS_RQ_TableFiller(QuizTDesc,QuizT_XML_Desc);
						final MSS_RQ_Admin reqHandler = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
						
						new Thread(new Runnable() {
							@Override
							public void run() {
								String xml_arr_ans = MSS_RQ_Request.http_request(reqHandler.unfoldTask(anketID),ToolMainWidget.URL);
								//QuizUpdater.updateData(xml_arr_ans);
								String ans = QuizUpdater.requestLocalData(xml_arr_ans, 0);
								if (ans!= null)
								{
									@SuppressWarnings("unused")
									String ans_decoded = new String(Base64.decode(ans));
									//taskEditor.setKey(ans_decoded);
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
										int col_type = 2;
										int col_width = 3;
					        			int col_decimals = 4;
					        			int col_mv = 5;
										DefaultTableModel tmodel = new DefaultTableModel(pt.size(), 8);
										int counter = 0;
										DefaultTableModel cases_model = new DefaultTableModel(0,pt.size());
										String [] col_idents = new String[pt.size()+1]; 
										col_idents[0] = "ID_IV";
										for (MSS_RQ_XML_Pattern prn:pt)
										{
											col_idents[counter+1] = prn.getNodename();
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
												if (ID.equals("MV")) missingV = text;
													else
												altern+="\""+ID+"\"=\""+text+"\";";
											}
											altern = altern.substring(0, altern.length()-1);
											tmodel.setValueAt(prn.getNodename(), counter, 0);
											tmodel.setValueAt(quest, counter, 1);
											tmodel.setValueAt("0", counter, col_type);
											tmodel.setValueAt("8", counter, col_width);
											tmodel.setValueAt("5", counter, col_decimals);
											tmodel.setValueAt("1", counter, col_mv);
											tmodel.setValueAt(missingV, counter, 6);
											tmodel.setValueAt(altern, counter, 7);
											++counter;
										}
										tmodel.setColumnIdentifiers(new String[]{"VarName","Label","Type","Width","Decimals","MvCode","NMissingValue1","ValueLabelTableName"});
										desk.getMetaTbl().setModel(tmodel);
										int col_labels = 7;
										TableColumnModel tcol = desk.getMetaTbl().getColumnModel();
								    	TableCellEditor TCEditor =  new base_SP_Management.MyValueLabelCellEditor();
								    	tcol.getColumn(col_labels).setCellEditor(TCEditor);
								    	tcol.getColumn(0).setPreferredWidth(25);
								    	tcol.getColumn(1).setPreferredWidth(400);
								    	tcol.getColumn(2).setPreferredWidth(25);
								    	tcol.getColumn(col_labels).setPreferredWidth(400);
								    	desk.getMetaTbl().setColumnModel(tcol);
								    	desk.getMetaTbl();
								    	
								    	//construct cases table model
								    	cases_model.setColumnIdentifiers(col_idents);
								    	TableColumnModel cm = desk.getTbl().getColumnModel();
								    	//String xml_ans = MSS_RQ_Request.http_request(reqHandler.representStatistics(anketID),ToolMainWidget.URL);
										desk.getTbl().setModel(cases_model);
										
										@SuppressWarnings("rawtypes")
										Class [] classes = new Class[col_idents.length];
										classes[0] = Integer.class;
										//classes[1] = Integer.class;
										
										for(int i = 1; i < classes.length;i++)
										{
											classes[i] = String.class;
										}
										String [] col_idents_2 = new String[col_idents.length+1];
										col_idents_2[0] = col_idents[0];
										col_idents_2[1] = "ID_IV";
										for(int i = 2; i < col_idents_2.length;i++)
										{
											col_idents_2[i] = new String(col_idents[i-1]);
										}
										MSS_RQ_TableDescriptor QuizTDesc2 = new MSS_RQ_TableDescriptor(col_idents,classes);
										
										MSS_RQ_XMLtoTableDescriptor QuizT_XML_Desc2 = new MSS_RQ_XMLtoTableDescriptor(col_idents);
										MSS_RQ_TableFiller QuizUpdater2 = new MSS_RQ_TableFiller(QuizTDesc2,QuizT_XML_Desc2);
										MSS_RQ_Admin reqHandlerAdmin = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
										String xml_ans_2 = MSS_RQ_Request.http_request(reqHandlerAdmin.representStatistics(12),ToolMainWidget.URL);
										QuizUpdater2.updateData(xml_ans_2);
										QuizUpdater2.fillTable(desk.getTbl());
										
										for(int i = 0; i < col_idents.length;i++)
										{
											cm.getColumn(i).setPreferredWidth(30);
										}
										desk.getTbl().setColumnModel(cm);
										//here panel not a panel but a project! needed renaming, but it works without!!!
										int projectID = desk.getS_panel().getID_GroupOwn();
										System.out.println(projectID);
										MSS_RQ_Request.http_request(reqHandlerAdmin.extendScopeTask(anketID, projectID),ToolMainWidget.URL);
									}
								}
							}
						}).start();
					}
				});
				dlgg.setModal(true);
				dlgg.setVisible(true);
			}
		});
		//toolEditor = new JPanel(new BorderLayout());
		//toolEditor.setBackground(new Color(30,30,30));
		//toolBtns.add(addBtn);
		//toolBtns.add(delBtn);
		//toolBtns.add(refreshBtn);
		toolBtns.setLayout(new GridLayout(1,5));
		toolBtns.add(loadBtn);
		//toolBtns.add(grabFlashBtn);
		
		//toolBtns.add(saveBtn);
		toolBtns.add(paramsBtn);
		toolBtns.add(params2Btn);
		//toolBtns.add(tagsBtn);
		//toolBtns.add(analyticBtn);
		toolBtns.add(deployBtn);
		toolBtns.add(filterBtn);
		//toolBtns.add(attachAnketBtn);
		
		this.setLayout(new GridLayout(2,1));
		this.add(infoLbl);
		//toolEditor.add(editor);
		this.add(toolBtns);
		//this.add(toolEditor);

		//refreshBtn.doClick();
	}
	public JTextField getEditor() {
		return editor;
	}
}
