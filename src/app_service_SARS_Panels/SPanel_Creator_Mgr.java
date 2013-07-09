package app_service_SARS_Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SPanel_Creator_Mgr extends JPanel{
	@SuppressWarnings("unused")
	private JLabel name,manager,orderer,
		dates,dates_1,dates_2,dates_3,dates_4,dates_5,dates_6,dates_7,methods;
	@SuppressWarnings("unused")
	private JTextField name_ed,manager_ed,orderer_ed,
	dates_ed,dates_1_ed,dates_2_ed,dates_3_ed,dates_4_ed,dates_5_ed,dates_6_ed,methods_ed;
	private JPanel meth;
	private JPanel main;
	@SuppressWarnings("unused")
	private JCheckBox meth_1,meth_2,meth_3,meth_4;
	public SPanel_Creator_Mgr() {
		super();
		setLayout(new BorderLayout());
		main = new JPanel();
		name = new JLabel("Название панели:");
		manager = new JLabel("Менеджер панели:");
		//orderer = new JLabel("Заказчик проекта:");
		/*
		dates = new JLabel("Даты проекта:");
		dates_1 = new JLabel("  получение брифа или задания от заказчика");
		dates_2 = new JLabel("  дата подготовка предложения");
		dates_3 = new JLabel("  дата подготовка опросника");
		dates_4 = new JLabel("  дата начала полевого этапа");
		dates_5 = new JLabel("  дата окончания полевого этапа");
		dates_6 = new JLabel("  дата передачи заказчику одномерных распределений");
		dates_7 = new JLabel("  дата передачи заказчику отчета");
		*/
		methods = new JLabel("Методы сбора информации:");
		main.setLayout(new GridLayout(12,2));
		name_ed = new JTextField();
		manager_ed = new JTextField();
		//orderer_ed = new JTextField();
		//dates_ed = new JTextField();
		//methods_ed = new JTextField();
		//JXDatePicker peaker = new JXDatePicker();
		///peaker.setDate(new Date());
		//JXDatePicker peaker2 = new JXDatePicker();
		//JXDatePicker peaker3 = new JXDatePicker();
		//JXDatePicker peaker4 = new JXDatePicker();
		//JXDatePicker peaker5 = new JXDatePicker();
		//JXDatePicker peaker6 = new JXDatePicker();
		//JXDatePicker peaker7 = new JXDatePicker();
		//peaker2.setDate(new Date());
		//peaker3.setDate(new Date());
		//peaker4.setDate(new Date());
		//peaker5.setDate(new Date());
		//peaker6.setDate(new Date());
		//peaker7.setDate(new Date());
		
		
		meth = new JPanel(new GridLayout(3, 1));
		meth_1 = new JCheckBox("CAPI");
		meth_2 = new JCheckBox("CATI");
		meth_3 = new JCheckBox("Бумажная анкета");
		meth.add(meth_1);
		meth.add(meth_2);
		meth.add(meth_3);
		
		main.add(name);main.add(name_ed);
		main.add(manager);main.add(manager_ed);
		//main.add(orderer);main.add(orderer_ed);
		//main.add(dates);main.add(dates_ed);
		//main.add(dates_1);main.add(peaker);
		//main.add(dates_2);main.add(peaker2);
		//main.add(dates_3);main.add(peaker3);
		//main.add(dates_4);main.add(peaker4);
		//main.add(dates_5);main.add(peaker5);
		//main.add(dates_6);main.add(peaker6);
		//main.add(dates_7);main.add(peaker7);
		
		main.add(methods);main.add(meth);
		add(main, BorderLayout.CENTER);
		JButton ok = new JButton("Создать!");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SPanel_Creator_Mgr.this.setDone(true);
			}
		});
		add(ok,BorderLayout.SOUTH);
		setPreferredSize(new Dimension(600,300));
	}
	private boolean done;
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
}
