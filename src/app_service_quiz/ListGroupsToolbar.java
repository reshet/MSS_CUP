package app_service_quiz;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app_service_SARS_Projects.attachPanelDialog;

import com.explodingpixels.macwidgets.HudWidgetFactory;

public class ListGroupsToolbar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 401304332962688915L;
	private JButton addBtn,delBtn,refreshBtn;
	private ServerDispatcher dispatcher;
	private JList list;
	private JPanel toolBtns;
	private JTextField editor;
	int groupID;
	//private ListGr
	public void setGroupID(int groupID) {
		this.groupID = groupID;
		this.refresh();
	}
	public ListGroupsToolbar(ServerDispatcher disp,JList lst)
	{
		this.dispatcher = disp;
		this.list = lst;
		this.setBackground(new Color(30,30,30));
		addBtn = HudWidgetFactory.createHudButton("+");
		delBtn = HudWidgetFactory.createHudButton("-");
		refreshBtn = HudWidgetFactory.createHudButton("Обновить");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//String name = "Новая панель в проекте";
			    //String[] param = new String[2];
			    //param[0] = name;
			    //param[1] = String.valueOf(groupID);
				//ListGroupsToolbar.this.dispatcher.add(param);
				new attachPanelDialog(groupID);
				refreshBtn.doClick();
			}
		});
		delBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel model = (DefaultListModel) ListGroupsToolbar.this.list.getModel();
				int index = list.getSelectedIndex();
				if (index == -1) return;
				int ID = ((ListGroupsElementData)model.getElementAt(index)).getID();
				Integer [] param  = new Integer[1];
				param[0] = ID;
				ListGroupsToolbar.this.dispatcher.delete(param);
				refreshBtn.doClick();
			}
		});
		refreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		//editor = HudWidgetFactory.createHudTextField("Новf елемент...");
		/*
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
		*/
		/*
		editor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//((ListGroupsElementData)list.getModel().getElementAt(list.getSelectedIndex())).setName(editor.getText());
				int ID = ((ListGroupsElementData)list.getModel().getElementAt(list.getSelectedIndex())).getID();
				String Name = editor.getText();
				String [] param = new String[2];
				param[0] = String.valueOf(ID);
				param[1] = Name;
				ListGroupsToolbar.this.dispatcher.edit(param);
				refreshBtn.doClick();
			}
		});
		*/
		/*
		editor.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				//((ListGroupsElementData)list.getModel().getElementAt(list.getSelectedIndex())).setName(editor.getText());
			}
		});
		*/
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

		refreshBtn.doClick();
	}
	public void refresh()
	{
		int index = list.getSelectedIndex();
		String [] params = {String.valueOf(groupID)};
		dispatcher.refreshHeirGroups(params);
		DefaultListModel model = (DefaultListModel) ListGroupsToolbar.this.list.getModel();
		int size = model.getSize();
	    delBtn.setEnabled(size > 0);
		if (size != 0 && size != index && size > index){list.setSelectedIndex(index);}
		if (size != 0 && size == index){list.setSelectedIndex(index-1);}
		if (size != 0 && size < index){list.setSelectedIndex(size-1);}
	}
	public JTextField getEditor() {
		return editor;
	}

}
