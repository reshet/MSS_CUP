package app_service_SARS_Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class UserSettingsDeployPanelMatcher extends UserSettingsAbstractAsker<Map<Integer,String>>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4319930771111855630L;
	@SuppressWarnings("unused")
	private JTable tbl_1,tbl_2;
	private JPanel assoc_panel;
	private ArrayList<AssocPanel> associates = new ArrayList<AssocPanel>();
	ArrayList<String> left_names = new ArrayList<String>();
	Vector<String> right_names = new Vector<String>();
	ArrayList<String> right_codes = new ArrayList<String>();
	public UserSettingsDeployPanelMatcher(String help) {
		super(help);
	}
	public UserSettingsDeployPanelMatcher(JTable tbl_1,JTable tbl_2) {
		super("Сопоставьте колонки загруженной таблички пользователей с полями регистрации.");
		this.tbl_1 = tbl_1;
		this.tbl_2 = tbl_2;
		//ArrayList<String> left_names = new ArrayList<String>();
		
		
		for(int i = 0; i < tbl_1.getModel().getColumnCount();i++)
		{
			left_names.add(tbl_1.getModel().getColumnName(i));
		}
		right_names.add("Ничего");
		right_codes.add("");
		for(int i = 0; i < tbl_2.getModel().getRowCount();i++)
		{
			right_names.add((String)tbl_2.getModel().getValueAt(i, 1));
			right_codes.add((String)tbl_2.getModel().getValueAt(i, 0));
		}
		ScrollPane pane  = new ScrollPane();
		assoc_panel = new JPanel();
		assoc_panel.setLayout(new GridLayout(left_names.size(), 1));
		assoc_panel.setPreferredSize(new Dimension(400,left_names.size()*40));
		pane.add(assoc_panel);
		//pane.setLayout(new FlowLayout());
		for(String left_name:left_names)
		{
			AssocPanel pnl = new AssocPanel(left_name, right_names);
			associates.add(pnl);
			assoc_panel.add(pnl);
		}
		panel.setLayout(new BorderLayout(3,3));
		panel.add(pane);
		//pane.add();
	}
	@Override
	public void processSettings() {
		_setting = new HashMap<Integer, String>();
		int counter = 0;
		for(@SuppressWarnings("unused") String lft_name:left_names)
		{
			int val = associates.get(counter).cbox.getSelectedIndex();
			_setting.put(counter, right_codes.get(val));
			counter++;
		}	
	}
	class AssocPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1582551194804019125L;
		public JLabel label;
		public JComboBox cbox;
		public AssocPanel(String left,Vector<String> right) {
			label = new JLabel(left);
			cbox = new JComboBox(right);
			cbox.setSelectedIndex(makeSuggestion(left, right));
			setLayout(new GridLayout(1, 2));
			setPreferredSize(new Dimension(400,50));
			add(label);
			add(cbox);
		}
		private int makeSuggestion(String left,Vector<String> right)
		{
			int counter =0;
			for(String elem:right)
			{
				if(elem.indexOf(left)> -1)
					return counter;
				counter++;
			}
			return 0;
		}
	}
	

}
