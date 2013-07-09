package base_SP_Management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import app_service_quiz.SidePalleteSparta;



public class RequestFilterPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -598306773581164587L;
	private RequestFilterItem[] req_I_arr;
	private int cols =0;
	private JPanel pnl,toolbar;
	private SocioPanel span;
	
	private ArrayList<SocioPanelMember> members;
	public RequestFilterPanel(SocioPanel spanal) 
	{
		this.span = spanal;
		
		members = span.getMembers();
		pnl = new JPanel();
		pnl.setLayout(new FlowLayout());
		pnl.setPreferredSize(new Dimension(300,600));
		pnl.setBackground(new Color(50,50,50));
		//JScrollPane spane = new JScrollPane();
		req_I_arr = new RequestFilterItem[20];
		//final DefaultListModel listModel = new DefaultListModel();
		//JList BarList = new JList(listModel);
		//BarList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		//BarList.setLayoutOrientation(JList.VERTICAL);
		//BarList.setVisibleRowCount(-1);
		//ComplexCellRenderer renderer = new ComplexCellRenderer();
		//BarList.setCellRenderer(renderer);
		JScrollPane scroller = new JScrollPane(pnl);
		this.setLayout(new BorderLayout());
		toolbar = new JPanel();
		toolbar.setLayout(new GridLayout(1,3));
		JButton addbtn = new JButton("+");
		JButton delbtn = new JButton("-");
		JButton dobtn = new JButton("Filter!");
		req_I_arr[cols] = new RequestFilterItem(span);
		//pnl.add(new JScrollPane(tree));
		pnl.add(req_I_arr[cols]);
		addbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cols < 20)
				{
					req_I_arr[++cols] = new RequestFilterItem(span);
					//req_I_arr[cols].setS_Panel(span);
					pnl.add(req_I_arr[cols]);
					pnl.repaint();
					pnl.validate();
				}
			}
		});
		delbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cols > 0 )
				{
					pnl.remove(req_I_arr[cols--]);
					//req_I_arr[cols--] = null;
					pnl.repaint();
					pnl.validate();
				}
			}
		});
		dobtn.addActionListener(new ActionListener() {
			@SuppressWarnings({ "unchecked" })
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Map<String,String> [] allMaps = new HashMap[members.size()];
				for (int i = 0; i < allMaps.length;i++)
				{
					allMaps[i] = members.get(i).getVars();
					allMaps[i].put("RESTORE_KEY_ID", String.valueOf(i));
				}
				req_I_arr[0].adjustSP(allMaps);
				for (int i = 1; i <= cols;i++)
				{
					req_I_arr[i].adjustSP(req_I_arr[i-1].processSP());
				}
				Map<String,String> [] ResultMaps = req_I_arr[cols].processSP();
				Set<String> totalSet = new HashSet<String>(ResultMaps.length);
				for (int i = 0; i < ResultMaps.length;i++)
				{
					String restore_id = ResultMaps[i].get("RESTORE_KEY_ID");
					totalSet.add(restore_id);
				}
				ArrayList<SocioPanelMember> members_new = new ArrayList<SocioPanelMember>();
				for (int i = 0; i < members.size();i++)
				{
					if (totalSet.contains(String.valueOf(i))){members_new.add(members.get(i));}
				}
				members.size();
				members_new.size();
				//
				//make new panel
				//SocioPanel sp = new SocioPanel();
				//sp.setAnalysysForm(span.getAnalysysForm());
				//sp.setMembers(members_new);
				DefaultListModel model = new DefaultListModel();
				for(SocioPanelMember member:members_new)
				{
					model.addElement(member);
				}
				//sp.getList().setModel(model);
				SocioPanelWidget SPanel = new SocioPanelWidget();
				//sp.getDesk().getFormat_io().set_Model(model);
				//SPanel.adjustSocioPanel(sp);
				
				SidePalleteSparta spWindow  = new SidePalleteSparta(SPanel,null);
				spWindow.setTitle("SocioLogic2");
				spWindow.setVisible(true);
				//
			}
		});
		toolbar.add(addbtn);
		toolbar.add(delbtn);
		toolbar.add(dobtn);
		toolbar.setPreferredSize(new Dimension(400,30));
		this.add(toolbar, BorderLayout.SOUTH);
		this.add(scroller, BorderLayout.CENTER);
		
	}
}
class RequestFilterItem extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8291657683487581926L;
	private JCheckBox use_CB;
	@SuppressWarnings("unused")	private JLabel desc_L;
	private JComboBox oper_CBX,var_filter_CBX, logic_CBX;
	private JTextField value_EP;
	private Map<String,String> [] procSPP;
	@SuppressWarnings("unused")
	private Map<String,String> varsToProc;
	private String compareStr;
	private String compareValue;
	private SocioPanel s_Panel;
	public RequestFilterItem()
	{
		compareValue = "";
	}
	public RequestFilterItem(SocioPanel span)
	{
		this.s_Panel = span;
		setPreferredSize(new Dimension(500,20));
		setLayout(new GridLayout(1,5));
		use_CB = new JCheckBox("Фильтр");
		use_CB.setEnabled(true);
		desc_L = new JLabel("Фильтр");
		oper_CBX = new JComboBox();
		oper_CBX.addItem("Содержит");
		oper_CBX.addItem("Исключая");
		//oper_CBX.setPreferredSize(new Dimension(60,20));
		logic_CBX = new JComboBox();
		logic_CBX.addItem("=");
		logic_CBX.addItem(">");
		logic_CBX.addItem(">=");
		logic_CBX.addItem("<");
		logic_CBX.addItem("<=");
		logic_CBX.addItem("<>");
		compareStr = logic_CBX.getSelectedItem().toString();
		logic_CBX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				compareStr = logic_CBX.getSelectedItem().toString();
			}
		});
		var_filter_CBX = new JComboBox();
		
		if (s_Panel.getAnalysysForm().getVar_names() != null)
		for (String str:s_Panel.getAnalysysForm().getVar_names())
		{
			var_filter_CBX.addItem(str);
		}
		//add(desc_L);
		add(use_CB);
		//use_CB.setBackground(new Color(30,30,30));
		//add(oper_CBX);
		add(var_filter_CBX);
		add(logic_CBX);
		value_EP = new JTextField("");
		add(value_EP);

		setVisible(true);
	}
	public SocioPanel getS_Panel() {
		return s_Panel;
	}
	public void setS_Panel(SocioPanel sPanel) {
		s_Panel = sPanel;
	}
	@SuppressWarnings("unchecked")
	public void adjustSP(@SuppressWarnings("rawtypes") Map [] map)
	{
		this.procSPP = map;	
	}

	@SuppressWarnings("unchecked")
	public Map<String,String> [] processSP()
	{
		compareValue = value_EP.getText();
		String compareKey = var_filter_CBX.getSelectedItem().toString();
		ArrayList<Map<String,String>> mp = new ArrayList<Map<String, String>>(2);
		if (!use_CB.isSelected())
		{
			return procSPP;			
		}
		if (compareStr.equals(">"))
		{
			for(Map<String,String> aMap:procSPP)
			{
				if(aMap.containsKey(compareKey))
				if(aMap.get(compareKey).toString().compareTo(compareValue)<0)
				{
					mp.add(aMap);
				}
			}
		} else
		if (compareStr.equals(">="))
		{
			for(Map<String,String> aMap:procSPP)
			{
				if(aMap.containsKey(compareKey))
				if(aMap.get(compareKey).toString().compareTo(compareValue)>0
						||
						aMap.get(compareKey).toString().compareTo(compareValue)==0)
				{
					mp.add(aMap);
				}
			}
		}else	
		if (compareStr.equals("<"))
		{
			for(Map<String,String> aMap:procSPP)
			{
				if(aMap.containsKey(compareKey))
				if(aMap.get(compareKey).toString().compareTo(compareValue)>0)
				{
					mp.add(aMap);
				}
			}
		} else
		if (compareStr.equals("<="))
		{
			for(Map<String,String> aMap:procSPP)
			{
				if(aMap.containsKey(compareKey))
				if(aMap.get(compareKey).toString().compareTo(compareValue)<0
						||
						aMap.get(compareKey).toString().compareTo(compareValue)==0)
				{
					mp.add(aMap);
				}
			}
		} else
		if (compareStr.equals("="))
		{
			for(Map<String,String> aMap:procSPP)
			{
				if(aMap.containsKey(compareKey))
				if(aMap.get(compareKey).toString().compareTo(compareValue)==0)
				{
					System.out.println(aMap.toString());
					mp.add(aMap);
				}
			}
		} else
		if (compareStr.equals("<>"))
		{
			
			for(Map<String,String> aMap:procSPP)
			{
				if(aMap.containsKey(compareKey))
				if(aMap.get(compareKey).toString().compareTo(compareValue)!=0)
				{
					mp.add(aMap);
				}
			}
		}
		@SuppressWarnings("rawtypes")
		Map [] mpp = new Map[mp.size()];
		for (int i = 0;i < mpp.length;i++)mpp[i]=mp.get(i);
		return mpp;
	}
	public void setVarProcess(Map<String, String> map)
	{
		this.varsToProc = map;
	}
}
