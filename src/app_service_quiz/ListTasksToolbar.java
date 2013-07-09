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

public class ListTasksToolbar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4141840483479525774L;
	private JButton addBtn,delBtn,refreshBtn;
	private ServerDispatcher dispatcher;
	private JList list;
	private JPanel toolBtns,toolEditor;
	private JTextField editor;
	public ListTasksToolbar(ServerDispatcher disp,JList lst)
	{
		this.dispatcher = disp;
		this.list = lst;
		this.setOpaque(true);
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
				ListTasksToolbar.this.dispatcher.add(param);
				refreshBtn.doClick();
			}
		});
		delBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel model = (DefaultListModel) ListTasksToolbar.this.list.getModel();
				int index = list.getSelectedIndex();
				if (index == -1) return;
				String ID = ((ListTasksElementData)model.getElementAt(index)).getID();
				Integer [] param  = new Integer[1];
				param[0] = Integer.parseInt(ID);
				ListTasksToolbar.this.dispatcher.delete(param);
				refreshBtn.doClick();
			}
		});
		refreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = list.getSelectedIndex();
				ListTasksToolbar.this.dispatcher.refresh(new String[2]);
				DefaultListModel model = (DefaultListModel) ListTasksToolbar.this.list.getModel();
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
					editor.setText(((ListTasksElementData)list.getModel().getElementAt(list.getSelectedIndex())).getDescription());
					((ListTasksPanel)ListTasksToolbar.this.getParent()).getLinked_table().getSelectionModel()
						.setLeadSelectionIndex(list.getSelectedIndex());
					((ListTasksPanel)ListTasksToolbar.this.getParent()).getLinked_table().getSelectionModel()
					.setAnchorSelectionIndex(list.getSelectedIndex());
				}
			}
		});
		editor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//((ListGroupsElementData)list.getModel().getElementAt(list.getSelectedIndex())).setName(editor.getText());
				String ID = ((ListTasksElementData)list.getModel().getElementAt(list.getSelectedIndex())).getID();
				String Task = editor.getText();
				String [] param = new String[3];
				param[0] = Task;
				param[1] = "descr";
				param[2] = ID;
				ListTasksToolbar.this.dispatcher.edit(param);
				refreshBtn.doClick();
			}
		});
		editor.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				//((ListGroupsElementData)list.getModel().getElementAt(list.getSelectedIndex())).setName(editor.getText());
			}
		});
		this.setLayout(new GridLayout(2,1));
		toolBtns = new JPanel(new GridLayout(1,3));
		toolBtns.setBackground(new Color(30,30,30));
		toolEditor = new JPanel(new BorderLayout());
		toolEditor.setBackground(new Color(30,30,30));
		toolBtns.add(addBtn);
		toolBtns.add(delBtn);
		toolBtns.add(refreshBtn);
		
		toolEditor.add(editor);
		this.add(toolBtns);
		this.add(toolEditor);
		
		refreshBtn.doClick();
	}
	public JTextField getEditor() {
		return editor;
	}

}
