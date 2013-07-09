package app_service_quiz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.explodingpixels.macwidgets.HudWidgetFactory;

public class ListUsersToolbar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 401304332962688915L;
	private JButton addBtn,delBtn,refreshBtn,UMSBtn;
	private ServerDispatcher dispatcher;
	public ServerDispatcher getDispatcher() {
		return dispatcher;
	}
	private JList list;
	private JPanel toolBtns,toolEditor;
	private JTextField editor,editor2;
	private JPanel toolEditor2;
	private int groupID;
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	public ListUsersElementData getSelectedItem()
	{
		ListUsersElementData elem = (ListUsersElementData)list.getModel().getElementAt(list.getSelectedIndex());
		return elem;
	}
	public void refresh()
	{
		int index = list.getSelectedIndex();
		Integer [] params = new Integer[1];
		params[0] = ListUsersToolbar.this.groupID;

		ListUsersToolbar.this.dispatcher.refresh(params);
		DefaultListModel model = (DefaultListModel) ListUsersToolbar.this.list.getModel();
		int size = model.getSize();
	    //delBtn.setEnabled(size > 0);
		if (size != 0 && size != index && size > index){list.setSelectedIndex(index);}
		if (size != 0 && size == index){list.setSelectedIndex(index-1);}
		if (size != 0 && size < index){list.setSelectedIndex(size-1);}

	}
	public ListUsersToolbar(ServerDispatcher disp,JList lst)
	{
		this.dispatcher = disp;
		this.list = lst;
		this.setBackground(new Color(30,30,30));
		addBtn = HudWidgetFactory.createHudButton("+");
		delBtn = HudWidgetFactory.createHudButton("-");
		refreshBtn = HudWidgetFactory.createHudButton("<^>");
		UMSBtn = HudWidgetFactory.createHudButton("UMS->>");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String IDuser = editor.getText(); 
			    String name = editor2.getText();
				String[] param = new String[3];
			    param[0] = IDuser;
			    param[1] = name;
			    param[2] = String.valueOf(ListUsersToolbar.this.groupID);
			    ListUsersToolbar.this.dispatcher.add(param);
				refreshBtn.doClick();
			}
		});
		
		delBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel model = (DefaultListModel) ListUsersToolbar.this.list.getModel();
				int index = list.getSelectedIndex();
				if (index == -1) return;
				String ID = ((ListUsersElementData)model.getElementAt(index)).getName();
				//String ID = ((ListUsersElementData)model.getElementAt(index)).getID();
				
				Integer [] param  = new Integer[2];
				param[0] = Integer.parseInt(ID);
			    param[1] = ListUsersToolbar.this.groupID;
				ListUsersToolbar.this.dispatcher.delete(param);
				refreshBtn.doClick();
			}
		});
		
		refreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		editor = HudWidgetFactory.createHudTextField("100000");
		editor2 = HudWidgetFactory.createHudTextField("ПІБ:Новий панеліст...");
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2)
				{
					editor.setText(((ListUsersElementData)list.getModel().getElementAt(list.getSelectedIndex())).getName());
					editor2.setText(((ListUsersElementData)list.getModel().getElementAt(list.getSelectedIndex())).getPIB());
				}
			}
		});
		editor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//((ListUsersElementData)list.getModel().getElementAt(list.getSelectedIndex())).setName(editor.getText());
				String ID = ((ListUsersElementData)list.getModel().getElementAt(list.getSelectedIndex())).getID();
				String Name = editor.getText();
				String [] param = new String[2];
				param[0] = ID;
				param[1] = Name;
				ListUsersToolbar.this.dispatcher.edit(param);
				refreshBtn.doClick();
			}
		});
		editor.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				//((ListUsersElementData)list.getModel().getElementAt(list.getSelectedIndex())).setName(editor.getText());
			}
		});
		
		this.setLayout(new GridLayout(3,1));
		toolBtns = new JPanel(new GridLayout(1,4));
		toolBtns.setBackground(new Color(30,30,30));
		toolEditor = new JPanel(new BorderLayout());
		toolEditor.setBackground(new Color(30,30,30));
		toolEditor2 = new JPanel(new BorderLayout());
		toolEditor2.setBackground(new Color(30,30,30));
		toolBtns.add(addBtn);
		toolBtns.add(delBtn);
		toolBtns.add(refreshBtn);
		//toolBtns.add(UMSBtn);
		toolEditor.add(editor);
		toolEditor2.add(editor2);
		
		this.add(toolBtns);
		this.add(toolEditor);
		this.add(toolEditor2);

		//refreshBtn.doClick();
	}
	public JButton getUMSBtn() {
		return UMSBtn;
	}
	public JTextField getEditor() {
		return editor;
	}

}
