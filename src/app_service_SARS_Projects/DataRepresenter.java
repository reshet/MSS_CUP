package app_service_SARS_Projects;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DataRepresenter extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8398856246162608072L;
	private JTable table;
	private JButton filter_btn;
	private JButton report_btn;
	private JButton graph_btn;
	private JPanel bar;
	public DataRepresenter(JTable tabl)
	{
		table = tabl;
		setLayout(new BorderLayout(3,3));
		bar = new JPanel(new GridLayout(1, 3));
		filter_btn = new JButton("Фильтр");
		report_btn = new JButton("Отчет");
		graph_btn = new JButton("График");
		bar.add(filter_btn);
		bar.add(report_btn);
		bar.add(graph_btn);
		//add(bar,BorderLayout.SOUTH);
		JScrollPane pane = new JScrollPane(table);
		add(pane,BorderLayout.CENTER);
		filter_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final JDialog dlgg = new JDialog();
				dlgg.setLayout(new BorderLayout(3,3));
				dlgg.setSize(500,500);
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				dlgg.setLocation((int)screenSize.getWidth()/2 - (int)dlgg.getSize().getWidth()/2,
						(int)screenSize.getHeight()/2 - (int)dlgg.getSize().getHeight()/2);
				SimpleFilterPanel filter = new SimpleFilterPanel(table);
				dlgg.add(filter);
				dlgg.setModal(true);
				dlgg.setVisible(true);
			}
		});
		report_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		graph_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	}
}
