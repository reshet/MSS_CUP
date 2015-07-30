package app_service_quiz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.jfree.data.category.DefaultCategoryDataset;

import base_connectivity.MSS_Chart;
import base_connectivity.MSS_Chart_Cron;
import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_TableFiller;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;
import base_connectivity.MSS_Table_Cron;
import base_gui.ToolMainWidget;

import com.explodingpixels.macwidgets.HudWidgetFactory;
import com.explodingpixels.macwidgets.IAppWidgetFactory;

public class StatisticsTablePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8539043994169299423L;
	private JTable table;
	private DefaultTableModel model;
	private MSS_Chart_Cron G_Cron;
	@SuppressWarnings("unused")
	private MSS_Table_Cron T_Cron;
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}
	private PlotStatPanel linked_plotter;
	private MSS_RQ_TableDescriptor QuizTDesc;
	private MSS_RQ_XMLtoTableDescriptor QuizT_XML_Desc;
	private MSS_RQ_TableFiller QuizUpdater;
	private MSS_RQ_Admin reqHandlerAdmin;
	private String[] columnNames;
	private JPanel toolbar;
	private Thread RealTimeUpdater;
	private Runnable run_task;
	//private String URL;
	//private TableColumnModel col_model;
	public StatisticsTablePanel()
	{
	//	this.URL = ToolMainWidget.URL;
		this.setLayout(new BorderLayout());
		G_Cron = new MSS_Chart_Cron(reqHandlerAdmin);
		//T_Cron = new MSS_Table_Cron(reqHandlerAdmin, ToolMainWidget.URL);
		//T_Cron.addItem(QuizUpdater,table,reqHandlerAdmin.geatherStatistics(n), args, autoUpdating)
		Object [][] data = {
				{"2342342","32534534","242353rwerfsdf",new Boolean(false),"dgdg,drgert34"}
				};
		//ID="185" Description="Task description" Visability="0" Aviability="0" Accepted="0" Subscribed="0";
		columnNames = new String[]{"№", "ID", "Пройдено","Відповіді"};
		model = new DefaultTableModel(data, columnNames);
		table = new JTable(model);
		TableColumnModel tcol= table.getColumnModel(); 
		JTableHeader header = new JTableHeader(tcol);
		table.setTableHeader(header);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroller = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setBackground(new Color(90,90,90));
		table.setForeground(new Color(190,190,255));
		table.setSelectionBackground(new Color(90,90,200));
		table.setSelectionForeground(new Color(200,200,90));
		scroller.setPreferredSize(new Dimension(300,300));
		scroller.setBackground(new Color(70,70,70));
		scroller.setForeground(new Color(60,60,60));
		IAppWidgetFactory.makeIAppScrollPane(scroller);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 updatePlot("s");
			}
		});
		run_task = new Runnable() {
			@Override
			public void run() {
				while(!Thread.currentThread().isInterrupted())
				{
					geatherStat();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					update();
				}
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				geatherStat();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//update();	
			}
		}).start();
		//JLabel lbl = new JLabel("text");
		toolbar = new JPanel();
		JButton upd = HudWidgetFactory.createHudButton("Оновити!");
		JButton gupd = HudWidgetFactory.createHudButton("Зібрати статистику");
		JCheckBox real = HudWidgetFactory.createHudCheckBox("Реального часу");
		upd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			new Thread(new Runnable() {
				@Override
				public void run() {
					int selected = table.getSelectedRow();
					update();
					if (selected != -1) table.getSelectionModel().setAnchorSelectionIndex(selected);
				}
			}).start();	
			}
		});
		gupd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			new Thread(new Runnable() {
				@Override
				public void run() {
					geatherStat();	
				}
			}).start();	
			}
		});
		real.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox)e.getSource()).isSelected())
				{
					RealTimeUpdater = new Thread(run_task);
					RealTimeUpdater.start();
				} else
				{
					RealTimeUpdater.interrupt();
				}
			}				
		});
		toolbar.setPreferredSize(new Dimension(400,40));
		toolbar.setBackground(new Color(60,60,60));
		toolbar.add(upd);
		toolbar.add(gupd);	
		toolbar.add(real);
		this.add(scroller);
		this.add(toolbar, BorderLayout.SOUTH);
	}
	public PlotStatPanel getLinked_plotter() {
		return linked_plotter;
	}
	public void setLinked_plotter(PlotStatPanel linkedPlotter) {
		linked_plotter = linkedPlotter;
	}
	void update()
	{
		int select = table.getSelectedRow();
		String answers = "2";
		if (select != -1)
		{
			answers = table.getValueAt(select,3).toString();	
		}
		QuizTDesc = new MSS_RQ_TableDescriptor(columnNames, 
				new Class[]{Integer.class, Integer.class,Integer.class,String.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"task","col_accepted","Q"});
		QuizUpdater = new MSS_RQ_TableFiller(QuizTDesc,QuizT_XML_Desc);
		reqHandlerAdmin = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
		String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.representAllStatistics(),ToolMainWidget.URL);
		System.out.println(xml_arr_ans);
		QuizUpdater.updateData(xml_arr_ans);
		int selected = table.getSelectedRow();
		QuizUpdater.fillTable(table);
		if (selected != -1)
		{
			table.getSelectionModel().setAnchorSelectionIndex(selected);
			table.getSelectionModel().setLeadSelectionIndex(selected);
			updatePlot(answers);
		}
	}
	private void updatePlot(String ans)
	{
		String answers = table.getValueAt(table.getSelectedRow(),3).toString();
		if (answers.equals(ans)) return;
		answers = answers.split(" : ")[1];
        Vector<String> variants = new Vector<String>(10);
        Vector<Integer> ans_col = new Vector<Integer>(10);
       int prev = 0;
       for(int i = 0; i < answers.length();i++)
       {
           if (answers.charAt(i) == '-')
           {
               String elem =answers.substring(prev, i);
               if (elem.compareTo("-") != 0 && elem.compareTo("|") != 0) variants.add(elem);
               prev = i+1;
           }
           if (answers.charAt(i) == '|')
           {
               ans_col.add(Integer.parseInt(answers.substring(prev, i)));
               prev = i+1;
           }
           if (i == answers.length() - 1)
           {
               ans_col.add(Integer.parseInt(answers.substring(prev, i+1)));
               prev = i+1;
           }
       }
       DefaultCategoryDataset dataset = new DefaultCategoryDataset();
       for(int i = 0 ; i < variants.size()&&i<ans_col.size();i++)
       {
            dataset.setValue(ans_col.get(i), "Відповіді", variants.get(i));
       }
       for (int i = 0; i < table.getRowCount(); i++)
       {
            int t = Integer.parseInt(table.getValueAt(i,1).toString());
            try
            {
                int tid = G_Cron.getItemOverID(t);
                if (tid > -1) G_Cron.getItem(tid).HideChartPanel();
            } catch (Exception ex)
            {
                System.out.println(ex);
            }
       }
       int taskID = Integer.parseInt(table.getValueAt(table.getSelectedRow(),1).toString());
       if (!G_Cron.inItems(taskID))
       {
           MSS_Chart chart = new MSS_Chart(dataset,"Завдання №" + table.getValueAt(table.getSelectedRow(),1).toString(),linked_plotter);
           G_Cron.addItem(chart, linked_plotter, false, taskID);
           chart.ShowChartPanel();
       } else
       {
           G_Cron.updateItem(G_Cron.getItemOverID(taskID), dataset);
           G_Cron.getItem(G_Cron.getItemOverID(taskID)).ShowChartPanel();
       }
	}
	void geatherStat()
	{
		int selected = -1;
		try 
		{
			selected = table.getSelectedRow();
		} catch (Exception e){}
		if (selected != -1)
		{
			MSS_RQ_Request.http_request(reqHandlerAdmin.geatherStatistics(Integer.parseInt((String)table.getValueAt(selected,1))),ToolMainWidget.URL);
		}
	}
}