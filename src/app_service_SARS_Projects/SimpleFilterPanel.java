package app_service_SARS_Projects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import base_data_exchanger.Internal_IO;

public class SimpleFilterPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6756287445309828142L;
	private JTable table;
	private JList var_list,filter;
	private JPanel var_panel,filter_pnl;
	private JButton addFilter_btn,doFilter_btn;
	private ArrayList<FilterElemData> filters;
	public SimpleFilterPanel(JTable tbl)
	{
		setLayout(new BorderLayout(2,2));
		var_list = new JList();
		var_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		var_list.setLayoutOrientation(JList.VERTICAL);
		var_list.setVisibleRowCount(-1);
		var_list.setSelectedIndex(0);
		DefaultListModel model = new DefaultListModel();
		final DefaultListModel s_model = new DefaultListModel();
		
		table = tbl;
		
		TableColumnModel col_m = table.getColumnModel();
		for(int i = 0;i < col_m.getColumnCount();i++)
		{
			model.add(i, table.getModel().getColumnName(i));
		}
		var_list.setModel(model);
		JScrollPane pane = new JScrollPane(var_list);
		pane.setPreferredSize(new Dimension(100,300));
		var_panel = new JPanel(new BorderLayout());
		filter_pnl = new JPanel(new BorderLayout());
		var_panel.setPreferredSize(new Dimension(100,300));
		var_panel.add(pane);
		addFilter_btn = new JButton("Добавить фильтр");
		var_panel.add(addFilter_btn,BorderLayout.SOUTH);
		add(var_panel,BorderLayout.WEST);
		
		filter = new JList();
		filter.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		filter.setLayoutOrientation(JList.VERTICAL);
		filter.setVisibleRowCount(-1);
		filter.setSelectedIndex(0);
		filter.setCellRenderer(new SimpleFilter_ListElementRenderer());
		filter.setEnabled(true);
		filter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2)
				{
					final JDialog dlgg = new JDialog();
					dlgg.setLayout(new BorderLayout(3,3));
					dlgg.setSize(300,110);
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					dlgg.setLocation((int)screenSize.getWidth()/2 - (int)dlgg.getSize().getWidth()/2,
							(int)screenSize.getHeight()/2 - (int)dlgg.getSize().getHeight()/2);
					DefaultListModel model = (DefaultListModel) filter.getModel();
					final Filter_pnl flt = new Filter_pnl((FilterElemData)model.getElementAt(filter.getSelectedIndex()));
					dlgg.add(flt);
					JButton done = new JButton("Изменить!");
					dlgg.add(done,BorderLayout.SOUTH);
					done.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							dlgg.setVisible(false);
							dlgg.dispose();
							FilterElemData data = new FilterElemData(flt.var.getText(), flt.val.getText());
							DefaultListModel model = (DefaultListModel) filter.getModel();
							model.setElementAt(data, filter.getSelectedIndex());
						}
					});
					dlgg.setModal(true);
					dlgg.setVisible(true);
				}
			}
		});
		filter_pnl.add(new JScrollPane(filter));
		doFilter_btn = new JButton("Фильтровать");
		filter_pnl.add(doFilter_btn,BorderLayout.SOUTH);
		add(filter_pnl);
		filters = new ArrayList<FilterElemData>(10);
		addFilter_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					
					final JDialog dlgg = new JDialog();
					dlgg.setLayout(new BorderLayout(3,3));
					dlgg.setSize(300,110);
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					dlgg.setLocation((int)screenSize.getWidth()/2 - (int)dlgg.getSize().getWidth()/2,
							(int)screenSize.getHeight()/2 - (int)dlgg.getSize().getHeight()/2);
					FilterElemData fltr = new FilterElemData((String)var_list.getModel().getElementAt(var_list.getSelectedIndex()), "0.0");
					final Filter_pnl flt = new Filter_pnl(fltr);
					dlgg.add(flt);
					JButton done = new JButton("Добавить");
					dlgg.add(done,BorderLayout.SOUTH);
					done.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							dlgg.setVisible(false);
							dlgg.dispose();
							FilterElemData data = new FilterElemData(flt.var.getText(), flt.val.getText());
							filters.add(data);
							s_model.addElement(data);
						}
					});
					dlgg.setModal(true);
					dlgg.setVisible(true);
			}
		});
		doFilter_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel newmodel = new DefaultTableModel();
				newmodel.setColumnCount(table.getColumnCount());
				int row_c = 0;
				for (int i = 0;i < table.getRowCount();i++)
				{
					int success = filters.size();
					int cur_suc = 0;
					for(FilterElemData elem:filters)
					{
						int col = Internal_IO.findColoumn((DefaultTableModel) table.getModel(), elem.getVar());
						if (table.getModel().getValueAt(i, col).equals(elem.getVal()))
						{
							System.out.println(elem.getVal());
							cur_suc++;
						}
					}
					if (success == cur_suc)
					{
						for (int ii = 0;ii < table.getColumnCount();ii++)
						{
							newmodel.setRowCount(row_c+1);
							newmodel.setValueAt(table.getModel().getValueAt(i, ii), row_c, ii);
							//System.out.println("i = "+i+": ii = "+ii);
						}
						row_c++;
						//newmodel.addRow(vect);
					}
					//newmodel.setColumnIdentifiers(((DefaultTableModel)table.getModel()
				}
				final JDialog dlgg = new JDialog();
				dlgg.setLayout(new BorderLayout(3,3));
				dlgg.setSize(500,510);
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				dlgg.setLocation((int)screenSize.getWidth()/2 - (int)dlgg.getSize().getWidth()/2,
						(int)screenSize.getHeight()/2 - (int)dlgg.getSize().getHeight()/2);
				
				JTable tbl = new JTable(newmodel.getRowCount(),newmodel.getColumnCount());
				tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				Vector<String> col_ident = new Vector<String>(table.getColumnCount());
				for (int i = 0;i < table.getColumnCount();i++)
				{
					col_ident.addElement(table.getColumnName(i));
				}
				newmodel.setColumnIdentifiers(col_ident);
				tbl.setModel(newmodel);
				/*
				TableColumnModel col_model = tbl.getColumnModel();
				for (int j =0;j<tbl.getColumnCount();j++)
				{
					col_model.getColumn(j).setWidth(30);
				}
				*/
				//tbl.setColumnModel(col_model);
				
				dlgg.add(new JScrollPane(tbl));
				dlgg.setModal(true);
				dlgg.setVisible(true);
				
			}
		});
		filter.setModel(s_model);
	}
}
class Filter_pnl extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8015530837419502834L;
	public JTextField val;
	public JLabel var;
	public Filter_pnl(FilterElemData data)
	{
		this.var = new JLabel(data.getVar());
		this.val = new JTextField(data.getVal());
		this.val.setEditable(true);
		this.val.setEnabled(true);
		add(this.var);
		add(this.val);
	}
}
class FilterElemData
{
	private String var,val;
	public FilterElemData(String var,String val) {
		this.setVar(var);
		this.setVal(val);
	}
	public void setVal(String val) {
		this.val = val;
	}
	public String getVal() {
		return val;
	}
	public void setVar(String var) {
		this.var = var;
	}
	public String getVar() {
		return var;
	}
	
}
class SimpleFilter_ListElementRenderer implements ListCellRenderer { 
	protected Component component = null;
	public Component getListCellRendererComponent(JList list, Object value, int index,
			  	boolean isSelected, boolean cellHasFocus) 
	{
	  //Map vars = new HashMap(((SocioPanelMember) value).getVars());  
	  //String name = new String(((SocioPanelMember) value).getName());
	  //int ID = (((SocioPanelMember) value).get());
	 //System.out.print(value.toString());
	  if (isSelected) {
		  	component = new Filter_pnl((FilterElemData) value);
	        component.setBackground(new Color(45,45,135));
	        component.setForeground(new Color(245,245,33));
	  } else {
		  	component = new Filter_pnl((FilterElemData) value);
	    	component.setBackground(list.getBackground());
	    	component.setForeground(list.getForeground());
	  }
	  return component;
	}
}

