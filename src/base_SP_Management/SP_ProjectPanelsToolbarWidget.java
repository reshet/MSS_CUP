package base_SP_Management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app_service_quiz.TasksTablePanel;
import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_TableFiller;
import base_connectivity.MSS_RQ_User;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;
import base_gui.ToolMainWidget;

public class SP_ProjectPanelsToolbarWidget extends JPanel {
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
	@SuppressWarnings("unused")
	private JButton saveBtn,loadBtn,paramsBtn,params2Btn,tagsBtn,analyticBtn,deployBtn,filterBtn,grabFlashBtn,attachAnketBtn;
	private JButton ankets_panelsBtn,reportsBtn,flashExporterBtn,inviteUsersOnline;
	private SocioProjectInstrumentDesk desk;
	private JPanel toolBtns;
	private JLabel infoLbl;
	public SocioProjectInstrumentDesk getDesk() {
		return desk;
	}
	public void setDesk(SocioProjectInstrumentDesk desk) {
		this.desk = desk;
	}
	private JTextField editor;
	public SP_ProjectPanelsToolbarWidget()
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
		toolBtns = new JPanel();
		toolBtns.setBackground(new Color(30,30,30));
		loadBtn = new JButton("Загр. CSV");
		saveBtn = new JButton("Сохр. CSV");
		paramsBtn = new JButton("Кейсы");
		params2Btn = new JButton("Мета");
		reportsBtn = new JButton("Отчеты");
		ankets_panelsBtn = new JButton("Соответствия");
		inviteUsersOnline= new JButton("Разослать онлайн");
		//tagsBtn = new JButton("Теги");
		//analyticBtn = new JButton("Разбить");
		deployBtn = new JButton("Oтправить!");
		filterBtn = new JButton("Подвыборка");
		grabFlashBtn = new JButton("Собрать с карточек");
		flashExporterBtn = new JButton("Прошивка флешек");
		attachAnketBtn = new JButton("Добавить анкету");
		infoLbl = new JLabel("Всего елементов в панели:0");
		//showTableBtn = new JButton("T-View");
		loadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ProjectPanelsToolbarWidget.this.desk.loadUsers();
				infoLbl.setText("Всего елементов в панели:"+String.valueOf(desk.getS_panel().getCol_panelsists()));
				//paramsBtn.doClick();
			}
		});
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ProjectPanelsToolbarWidget.this.desk.saveUsers();
			}
		});
		ankets_panelsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ProjectPanelsToolbarWidget.this.desk.panels_subscription();
			}
		});
		inviteUsersOnline.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SP_ProjectPanelsToolbarWidget.this.desk.inviteOnlineDemo();
			}
		});
		reportsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//SP_ProjectPanelsToolbarWidget.this.desk.reportsPanel();
				SP_ProjectPanelsToolbarWidget.this.desk.showReportsPanel();
				
			}
		});
		flashExporterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//SP_ProjectPanelsToolbarWidget.this.desk.reportsPanel();
				SP_ProjectPanelsToolbarWidget.this.desk.showFlashBurner();
				
			}
		});
		/*
		analyticBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ProjectPanelsToolbarWidget.this.desk.SubdividePanelOnGroups();
			}
		});
		*/
		/*
		tagsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ProjectPanelsToolbarWidget.this.desk.upTagsListModel();
			}
		});
		*/
		deployBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ProjectPanelsToolbarWidget.this.desk.deployPanel();
			}
		});
		filterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ProjectPanelsToolbarWidget.this.desk.filterRequest();
				infoLbl.setText("Всего елементов в панели:"+String.valueOf(desk.getS_panel().getCol_panelsists()));
			}
		});
		paramsBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ProjectPanelsToolbarWidget.this.desk.showTableOfCases();
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
				SP_ProjectPanelsToolbarWidget.this.desk.getS_panel().getWidget().setTmodel(defmodel);
				infoLbl.setText("Всего елементов в панели:"+String.valueOf(desk.getS_panel().getCol_panelsists()));
			}
		});
		params2Btn.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ProjectPanelsToolbarWidget.this.desk.showTableOfMetadata();
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
				SP_ProjectPanelsToolbarWidget.this.desk.getS_panel().getWidget().setTmodel(defmodel);
				infoLbl.setText("Всего елементов в панели:"+String.valueOf(desk.getS_panel().getCol_panelsists()));
			}
		});
		grabFlashBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				desk.getGrabber().setAttached_cases_table(desk.getTbl());
				desk.getGrabber().setAttached_meta_table(desk.getMetaTbl());
				desk.getGrabber().setNew_indecies(SP_ProjectPanelsToolbarWidget.this.desk.s_prj.getLocal_interviews_stack());
				int ank_selID = SP_ProjectPanelsToolbarWidget.this.desk.s_prj.getWidget().getSelectedAnketID();
				
				
				//desk.getGrabber().grab(SP_ProjectPanelsToolbarWidget.this.desk.s_prj.getS_project().getAnkets_content().get(ank_selID).getLeft());
				desk.getGrabber().grab(ank_selID);
				
				
				//desk.getTbl().setModel(desk.getGrabber().getModel());
				
				//anket_real_ID = sp.getS_project().getAnkets_content().get(sp.getS_project().).getLeft();
				
				//desk.getS_panel().setModel(desk.getGrabber().getModel());
				paramsBtn.doClick();
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
				JButton sel = new JButton("Выбрать");
				
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
						@SuppressWarnings("unused")
						final MSS_RQ_TableFiller QuizUpdater = new MSS_RQ_TableFiller(QuizTDesc,QuizT_XML_Desc);
						final MSS_RQ_Admin reqHandler = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
						final MSS_RQ_User reqHandlerU = new MSS_RQ_User("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
										
						new Thread(new Runnable() {
							@Override
							public void run() {
								
								int projectID = desk.getS_panel().getID_GroupOwn();
								MSS_RQ_Request.http_request(reqHandler.extendScopeTask(anketID, projectID),ToolMainWidget.URL);
								MSS_RQ_Request.http_request(reqHandlerU.subscribeTask(anketID),ToolMainWidget.URL);				
								desk.getS_panel().getWidget().getAnket_table().update();
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
		toolBtns.setLayout(new GridLayout(3,5));
		toolBtns.add(loadBtn);
		toolBtns.add(grabFlashBtn);
		
		toolBtns.add(saveBtn);
		toolBtns.add(paramsBtn);
		toolBtns.add(params2Btn);
		toolBtns.add(ankets_panelsBtn);
		toolBtns.add(reportsBtn);
		toolBtns.add(flashExporterBtn);
		//toolBtns.add(analyticBtn);
		toolBtns.add(deployBtn);
		toolBtns.add(filterBtn);
		toolBtns.add(attachAnketBtn);
		toolBtns.add(inviteUsersOnline);
		
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
