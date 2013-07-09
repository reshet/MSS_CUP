package app_service_SARS_Projects;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app_service_SARS_Panels.Setting_AssocPair;
import app_service_SARS_Panels.UserSettingsAskerWindow;
import base_SP_Management.SocioProject;

import com.explodingpixels.macwidgets.HudWidgetFactory;

public class ListAgrAnketsToolbar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 401304332962688915L;
	private JButton addBtn,delBtn,refreshBtn;
	private JList list;
	private JPanel toolBtns;
	private JTextField editor;
	private SocioProject s_prj;
	//private ListGr
	public void setSocioProject(SocioProject sprj){this.s_prj = sprj;}
	public ListAgrAnketsToolbar(JList lst,SocioProject sprj)
	{
		this.s_prj = sprj;
		this.list = lst;
		this.setBackground(new Color(30,30,30));
		addBtn = HudWidgetFactory.createHudButton("+");
		delBtn = HudWidgetFactory.createHudButton("-");
		refreshBtn = HudWidgetFactory.createHudButton("<^>");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//String name = "Новая панель в проекте";
			    //String[] param = new String[2];
			    //param[0] = name;
			    //param[1] = String.valueOf(groupID);
				//ListGroupsToolbar.this.dispatcher.add(param);
				//attachPanelDialog dlg = new attachPanelDialog(groupID);
				//ArrayList<Integer> arr = new ArrayList<Integer>();
				//arr.add(404);
				//arr.add(402);
				
				new Thread(new Runnable() {
					private UserSettingsAskerWindow wind;
					private ArrayList<Setting_AssocPair<String, Integer>> ankets;
					
					@SuppressWarnings("unchecked")
					@Override
					public void run() {
						ankets = new ArrayList<Setting_AssocPair<String,Integer>>();
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
								wind = new UserSettingsAskerWindow(new UserSettingsAnketAgregateSelector(ankets));
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
					
						Setting_AssocPair<String, ArrayList<Integer>> selected_arr = (Setting_AssocPair<String, ArrayList<Integer>>)wind.getAsker().getSetting();
						String name = selected_arr.getLeft();
						ArrayList<Integer> arr = selected_arr.getRight();
						ListAgregatedAnketsElementData elem = new ListAgregatedAnketsElementData(name,arr);
						DefaultListModel model = (DefaultListModel) ListAgrAnketsToolbar.this.list.getModel();
						model.addElement(elem);
						//refreshBtn.doClick();
						//WaitDialog dlg = new WaitDialog("Применение заданных настроек...");
					}		
				}).start();
				
				
			}
		});
		delBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel model = (DefaultListModel) ListAgrAnketsToolbar.this.list.getModel();
				int index = list.getSelectedIndex();
				if (index == -1) return;
				model.remove(index);
				int size = model.getSize();
			    delBtn.setEnabled(size > 0);
				if (size != 0 && size != index && size > index){list.setSelectedIndex(index);}
				if (size != 0 && size == index){list.setSelectedIndex(index-1);}
				if (size != 0 && size < index){list.setSelectedIndex(size-1);}
				//refreshBtn.doClick();
			}
		});
		refreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = list.getSelectedIndex();
				if (index == -1) return;
				//String [] params = {String.valueOf(groupID)};
				//ListGroupsToolbar.this.dispatcher.refreshHeirGroups(params);
				DefaultListModel model = (DefaultListModel) ListAgrAnketsToolbar.this.list.getModel();
				ListAgregatedAnketsElementData elem = (ListAgregatedAnketsElementData)model.getElementAt(index);
				elem.refreshCasesTable();
			}
		});
		this.setLayout(new GridLayout(2,1));
		toolBtns = new JPanel(new GridLayout(1,3));
		toolBtns.setBackground(new Color(30,30,30));
		//toolEditor = new JPanel(new BorderLayout());
		//toolEditor.setBackground(new Color(30,30,30));
		toolBtns.add(addBtn);
		toolBtns.add(delBtn);
		toolBtns.add(refreshBtn);
		
	//	toolEditor.add(editor);
		this.add(toolBtns);
		//this.add(toolEditor);

		//refreshBtn.doClick();
	}
	public JTextField getEditor() {
		return editor;
	}

}
