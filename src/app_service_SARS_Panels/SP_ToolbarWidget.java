package app_service_SARS_Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
	private JButton addBtn,delBtn,refreshBtn;
	private SP_InstrumentDesk desk;
	private JPanel toolBtns;
	private JLabel infoLbl;
	public SP_InstrumentDesk getDesk() {
		return desk;
	}
	public void setDesk(SP_InstrumentDesk desk) {
		this.desk = desk;
		this.add(new JScrollPane(desk.getPanel_list()),BorderLayout.CENTER);
	}
	private JTextField editor;
	public SP_ToolbarWidget(SP_InstrumentDesk desk)
	{
		this.desk = desk;
		
		this.setBackground(new Color(30,30,30));
		addBtn = new JButton("+");
		delBtn = new JButton("-");
		refreshBtn = new JButton("<^>");
		//JButton testFES = new JButton("FES test!");
		/*
		testFES.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SP_ToolbarWidget.this.desk.test_FEScoder();
			}
		});
		*/
		addBtn.addActionListener(new NewSP_Action());
		delBtn.addActionListener(new Delete_SP_Action());
		refreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SP_ToolbarWidget.this.desk.refresh_PList();
			}
		});
		/*
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
		toolBtns = new JPanel(new GridLayout(4,1));
		toolBtns.setBackground(new Color(30,30,30));
		//loadBtn = new JButton("Загр. CSV");
		//saveBtn = new JButton("Сохр. CSV");
		//paramsBtn = new JButton("Параметры");
		//tagsBtn = new JButton("Теги");
		//analyticBtn = new JButton("Разбить");
		//deployBtn = new JButton("Oтправить!");
		//filterBtn = new JButton("Подвыборка");
		infoLbl = new JLabel("INFA:");
		//showTableBtn = new JButton("T-View");
		
		//toolEditor = new JPanel(new BorderLayout());
		//toolEditor.setBackground(new Color(30,30,30));
		//toolBtns.setLayout(new GridLayout(1,5));
		toolBtns.add(addBtn);
		toolBtns.add(delBtn);
		toolBtns.add(refreshBtn);
		//toolBtns.add(testFES);
		this.setLayout(new BorderLayout());
		this.add(infoLbl,BorderLayout.SOUTH);
		//toolEditor.add(editor);
		this.add(toolBtns,BorderLayout.EAST);
		this.add(new JScrollPane(desk.getPanel_list()),BorderLayout.CENTER);

		//this.add(toolEditor);
		
		/*
		DefaultListModel model = new DefaultListModel();
		ListModel mdl = desk.getPanel_list().getModel();
		for(int i = 0;i < mdl.getSize();i++)
		{
			model.addElement(mdl.getElementAt(i));
		}
		
		for(SocioPanel sp: desk.S_prj.getS_panels_all())
		{
			model.addElement(sp);
		}
		desk.getPanel_list().setModel(model);
		*/
		refreshBtn.doClick();
	}
	public JTextField getEditor() {
		return editor;
	}
	private class NewSP_Action extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4684379079563970183L;
		public NewSP_Action() {
			putValue(Action.NAME, "New panel create");
			putValue(Action.SHORT_DESCRIPTION, "Creates new SocioPanel");
			putValue(Action.MNEMONIC_KEY, new Integer('N'));
			putValue(Action.ACCELERATOR_KEY, "ctrl");
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			desk.createNewSP();
		}
	}
	private class Delete_SP_Action extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4684379079563970183L;
		public Delete_SP_Action() {
			putValue(Action.NAME, "Panel delete");
			putValue(Action.SHORT_DESCRIPTION, "Deletes SocioPanel");
			putValue(Action.MNEMONIC_KEY, new Integer('D'));
			putValue(Action.ACCELERATOR_KEY, "ctrl");
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			desk.delete_SP();
		}
	}
}
