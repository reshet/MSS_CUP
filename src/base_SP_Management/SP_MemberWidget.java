package base_SP_Management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

public class SP_MemberWidget extends JPanel implements ListCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String name, ID;
	private SocioPanelMember data;
	private JPanel params_pnl;
	private JPanel id_name_pnl;
	public SP_MemberWidget(SocioPanelMember data) {
		
		this.setData(data);		
		this.setLayout(new BorderLayout());
		var_names = data.getVar_names();
		vars = data.getVars();
		setPreferredSize(new Dimension(25,40));
		setMaximumSize(new Dimension(100,150));
		setMinimumSize(new Dimension(100,15));		
		//setScrollableTracksViewportHeight(true);
		params_pnl = new JPanel();
		id_name_pnl = new JPanel();
		id_name_pnl.setPreferredSize(new Dimension(100,20));
		JScrollPane scr = new JScrollPane(params_pnl);
		JLabel id_lbl = new JLabel(String.valueOf(data.getID()));
		//id_lbl.setPreferredSize(new Dimension(90,15));
		id_lbl.setBackground(new Color(30,40,70));
		id_lbl.setForeground(new Color(240,240,255));
		JLabel name_lbl = new JLabel(data.getName());
		//name_lbl.setPreferredSize(new Dimension(90,15));
		name_lbl.setBackground(new Color(30,40,70));
		name_lbl.setForeground(new Color(240,240,255));
		//name_lbl.setOpaque(true);
		id_name_pnl.setOpaque(false);
		id_name_pnl.add(id_lbl);
		id_name_pnl.add(name_lbl);
		this.add(id_name_pnl,BorderLayout.WEST);
		lbs = new ArrayList<JLabel>(10);
		//TO-DO
		//Simplyfied - only strigns
		for(String str:var_names)
		{
			JLabel lbl = new JLabel();
			lbs.add(lbl);
			lbl.setFont(new Font("Helvetika",Font.PLAIN,11));
			lbl.setText(vars.get(str));
			lbl.setForeground(new Color(100,244,244));
			lbl.setBackground(new Color(200,50,200));
			lbl.setAlignmentX(LEFT_ALIGNMENT);
			lbl.setOpaque(true);
			params_pnl.add(lbl);
		}
		params_pnl.setOpaque(false);
		//params_pnl.setAlignmentX(LEFT_ALIGNMENT);
		scr.setOpaque(false);
		scr.setBackground(new Color(30,40,40));
		scr.setForeground(new Color(30,40,40));
		this.add(params_pnl,BorderLayout.CENTER);
	}
	private void setData(SocioPanelMember data) {
		this.data = data;
	}
	@SuppressWarnings("unused")
	private SocioPanelMember getData() {
		return data;
	}
	private ArrayList<JLabel> lbs;
	private Set<String> var_names;
	private Map<String,String> vars;
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		return this;
	}
}
