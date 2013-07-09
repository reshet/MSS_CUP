package app_service_SARS_Projects;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


import app_service_SARS_Panels.Setting_AssocPair;
import app_service_SARS_Panels.UserSettingsAbstractAsker;

public class UserSettingsReportLinkBuilder extends UserSettingsAbstractAsker<UserSettingsReportLink_Setting>{
	private ArrayList<Setting_AssocPair<String, Integer>> vars;
	private JComboBox var_ch_1,var_ch_2;
	@SuppressWarnings("unused")
	private JLabel lbl=new JLabel("Имя агрегата:"),lbl_email = new JLabel("e-mail:");
	private JPanel type_pnl,dim_pnl,gr_type_pnl,vars_pnl,conditions_pnl;
	private JRadioButton table_opt,graphic_opt,t_1d,t_2d,gr_bar,gr_pie;
	private JCheckBox conditional_opt;
	private JComboBox av_conditions_left,av_operations;
	private JTextField av_cond_right,email;
	private ArrayList<Integer> var_ids;
	public UserSettingsReportLinkBuilder(ArrayList<Setting_AssocPair<String, Integer>> vars) {
		super("Введите параметры отчета.");
		this.vars = vars;
		var_ids = new ArrayList<Integer>();
		table_opt = new JRadioButton("Таблица");
		graphic_opt = new JRadioButton("График");
		t_1d = new JRadioButton("Одномерное распределение");
		t_2d = new JRadioButton("Двумерное распределение");
		gr_bar = new JRadioButton("Столбцовая диаграмма");
		gr_pie = new JRadioButton("Круговая диаграмма");
		conditions_pnl = new JPanel(new GridLayout(1,5));
		conditions_pnl.setPreferredSize(new Dimension(300, 20));
		conditions_pnl.setVisible(false);
		conditional_opt = new JCheckBox("Отчет по условию", false);
		av_conditions_left = new JComboBox();
		av_conditions_left.addItem("Кол-во интервью");
		
		av_operations = new JComboBox();
		av_operations.addItem("=");
		av_operations.addItem("каждые");
		av_operations.addItem(">");
		av_operations.addItem("<");
		av_cond_right = new JTextField();
		av_cond_right.setText("0");
		email = new JTextField();
		conditions_pnl.add(av_conditions_left);
		conditions_pnl.add(av_operations);
		conditions_pnl.add(av_cond_right);
		conditions_pnl.add(lbl_email);
		conditions_pnl.add(email);
		conditions_pnl.setEnabled(false);
		
		String[] varrs = new String[vars.size()];
		int counter = 0;
		for(Setting_AssocPair<String, Integer> pair:this.vars)
		{
			varrs[counter++] = pair.getLeft();
			var_ids.add(pair.getRight());
		}
		var_ch_1 = new JComboBox(varrs);
		var_ch_2 = new JComboBox(varrs);
		
		vars_pnl = new JPanel(new GridLayout(2,1));
		type_pnl = new JPanel(new GridLayout(1,2));
		dim_pnl = new JPanel(new GridLayout(2,1));
		gr_type_pnl = new JPanel(new GridLayout(2,1));
		
		vars_pnl.add(var_ch_1);vars_pnl.add(var_ch_2);
		type_pnl.add(table_opt);type_pnl.add(graphic_opt);
		dim_pnl.add(t_1d);dim_pnl.add(t_2d);
		gr_type_pnl.add(gr_bar);gr_type_pnl.add(gr_pie);
		
		
		panel.setLayout(new GridLayout(5,1));
		panel.add(vars_pnl);
		panel.add(type_pnl);
		JPanel opt_pnl = new JPanel(new GridLayout(1,2));
		opt_pnl.add(dim_pnl);
		opt_pnl.add(gr_type_pnl);
		panel.add(opt_pnl);
		panel.add(conditional_opt);
		panel.add(conditions_pnl);
		setGUIActions();
		//panel.add(dim_pnl);
		//panel.add(gr_type_pnl);
		
		//panel.add(name_pnl, BorderLayout.SOUTH);
		//panel.add(new JLabel("This is DEMO SETTINGS LABEL!"));
	}
	/**
	 * 
	 */
	private void setGUIActions()
	{
		table_opt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				graphic_opt.setSelected(false);
				t_1d.setEnabled(true);
				t_2d.setEnabled(true);
				gr_bar.setEnabled(false);
				gr_pie.setEnabled(false);
			}
		});
		graphic_opt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				table_opt.setSelected(false);
				t_1d.setEnabled(false);
				t_2d.setEnabled(false);
				gr_bar.setEnabled(true);
				gr_pie.setEnabled(true);
			
			}
		});
		t_1d.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				t_2d.setSelected(false);
			}
		});
		t_2d.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				t_1d.setSelected(false);
			}
		});
		gr_bar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gr_pie.setSelected(false);
			}
		});
		gr_pie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gr_bar.setSelected(false);
			}
		});
		conditional_opt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (conditional_opt.isSelected()) conditions_pnl.setVisible(true);
				else conditions_pnl.setVisible(false);
			}
		});
	}
	private static final long serialVersionUID = 4036252704300393935L;

	@Override
	public void processSettings() {
		Map<Integer,String> map = new HashMap<Integer, String>();
		map.put(var_ids.get(var_ch_1.getSelectedIndex()), (String)var_ch_1.getSelectedItem());
		map.put(var_ids.get(var_ch_2.getSelectedIndex()), (String)var_ch_2.getSelectedItem());
		int type = 0;
		int subtype = 0;
		String name = "Report link:[";
		name+="]";
		if(table_opt.isSelected()){
			type = 0;
			if(t_1d.isSelected())subtype = 0;else subtype = 1;
		}
		else{
			type = 1;
			if(gr_bar.isSelected())subtype = 0;else subtype = 1;
		}
		String cond_left = null,cond = null,cond_right = null,emailer = null;
		if (conditional_opt.isSelected())
		{
			cond_left = av_conditions_left.getSelectedItem().toString();
			cond = av_operations.getSelectedItem().toString();
			cond_right = av_cond_right.getText();
			emailer = email.getText();
		}
		_setting = new UserSettingsReportLink_Setting(name,type,subtype,map,cond,cond_left,cond_right,emailer);
	}
}
