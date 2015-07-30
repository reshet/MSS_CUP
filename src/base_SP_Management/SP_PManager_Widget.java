package base_SP_Management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import base_data_exchanger.Internal_IO;

import com.explodingpixels.macwidgets.HudWidgetFactory;

public class SP_PManager_Widget extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8972305142234904981L;
	private JPanel MainPanel,Tags_Panel,ActionPanel;
	private PM_Header H_Panel;
	private SP_PManager _MGR;
	private GR_Panel Gr_Panel;
	private SP_ServerDispatcher servDisp;
	public SP_PManager_Widget(SP_PManager PMGR) {
		servDisp = new SP_ServerDispatcher(null);
		_MGR = PMGR;
		setSize(new Dimension(600,600));
		setLayout(new BorderLayout());
		MainPanel = new JPanel();
		MainPanel.setBackground(new Color(50,60,70));
		MainPanel.setLayout(new BorderLayout());
		H_Panel = new PM_Header();
		H_Panel.setBackground(new Color(50,60,70));
		Gr_Panel = new GR_Panel(servDisp,_MGR);
		Gr_Panel.setBackground(new Color(50,60,70));
		Gr_Panel.setPreferredSize(new Dimension(600,200));
		Tags_Panel = new JPanel();
		Tags_Panel.setPreferredSize(new Dimension(600,200));
		ActionPanel = new JPanel();
		ActionPanel.setPreferredSize(new Dimension(600,30));
		ActionPanel.setBackground(new Color(50,60,70));
		JButton newB = HudWidgetFactory.createHudButton("Создать");
		newB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//here creates projects on server.
				String [] params = {H_Panel.getName(),H_Panel.getDesc()};
				servDisp.add(params);		
				_MGR.setHeader(H_Panel.getName());
				_MGR.setDescr(H_Panel.getDesc());
				_MGR.loadTagsToProject();
				//_MGR.updateTagsInProjectPanels();
				SP_PManager_Widget.this.setVisible(false);
			}
		});
		JButton saveB = HudWidgetFactory.createHudButton("Сохранить");
		ActionPanel.setLayout(new GridLayout(1,2,3,3));
		ActionPanel.add(newB);
		ActionPanel.add(saveB);
		MainPanel.add(H_Panel,BorderLayout.NORTH);
		MainPanel.add(Gr_Panel,BorderLayout.CENTER);
		//MainPanel.add(Tags_Panel,BorderLayout.SOUTH);
		MainPanel.add(ActionPanel,BorderLayout.SOUTH);
		
		JScrollPane scr = new JScrollPane(MainPanel);
		add(scr);
	}
}

class GR_Panel extends JPanel
{
	private static final long serialVersionUID = -598306773581164587L;
	private GR_Item_T[] req_I_arr;
	private int cols =0;
	private JPanel pnl,toolbar;
	//ivate SocioPanel span;
	//private ArrayList<SocioPanelMember> members;
	@SuppressWarnings("unused")
	private SP_ServerDispatcher servDisp;
	private SP_PManager _MGR;
	public GR_Panel(SP_ServerDispatcher servDisp,SP_PManager MGR)
	{
		this.servDisp = servDisp;
		this._MGR = MGR;
		pnl = new JPanel();
		pnl.setLayout(new FlowLayout());
		pnl.setPreferredSize(new Dimension(600,300));
		pnl.setBackground(new Color(50,50,50));	
		req_I_arr = new GR_Item_T[20];
		JScrollPane scroller = new JScrollPane(pnl);
		this.setLayout(new BorderLayout());
		toolbar = new JPanel();
		toolbar.setLayout(new GridLayout(1,3));
		JButton addbtn = new JButton("+");
		JButton delbtn = new JButton("-");
		JButton dobtn = new JButton("Filter!");
		req_I_arr[cols] = new GR_Item_T(_MGR.getProjectPanels());
		pnl.add(req_I_arr[cols]);
		addbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cols < 20)
				{
					req_I_arr[++cols] = new GR_Item_T(_MGR.getProjectPanels());
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
			@Override
			public void actionPerformed(ActionEvent e) {
				//req_I_arr[0].adjustSP(_Model);
				for (int i = 0; i <= cols;i++)
				{	
					req_I_arr[i].processSP();
					_MGR.addTag(req_I_arr[i].getTag());
				}
				//DefaultTableModel ResultMaps = req_I_arr[cols].processSP();
				
				//SocioPanel sp = new SocioPanel();
				//sp.setModel(ResultMaps);
		
				//SocioPanelWidget SPanel = new SocioPanelWidget();
				///SPanel.adjustSocioPanel(sp);
				//SidePalleteSparta spWindow  = new SidePalleteSparta(SPanel,null);
				//spWindow.setTitle("SocioLogic2");
				//spWindow.setVisible(true);
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
class GR_Item_T extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8291657683487581926L;
	private JCheckBox use_CB;
	private JLabel desc_L;
	@SuppressWarnings("unused")
	private JComboBox oper_CBX,panel_CBX,var_filter_CBX, logic_CBX,analytic_CBX;
	private JTextField name_Group,desc_Group;
	private JButton bindData;
	private DefaultTableModel procSPP;
	@SuppressWarnings("unused")
	private Map<String,String> varsToProc;
	//private String compareStr;
	//private String compareValue;
	private bindDataDescriptor binder;
	private ArrayList<String> bindedToDataParameter;
	private ArrayList<JLabel> bindedLabels;
	private ArrayList<SocioPanel> panels;
	private JPanel line1,line2;
	@SuppressWarnings("unused")
	private String gr_name;
	@SuppressWarnings("unused")
	private SP_ServerDispatcher servDisp;
	private SP_Tag tag;
	public SP_Tag getTag() {
		return tag;
	}
	public bindDataDescriptor getBinder() {
		return binder;
	}
	//private SocioPanel s_Panel;
	public GR_Item_T()
	{
		gr_name = "";
	}
	private int FindPanelByName(String p_name)
	{
		int i = 0;
		for(SocioPanel span:panels)
		{
			if (span.getName().equals(p_name)) return i;
			i++;
		}
		return -1;
	}
	public GR_Item_T(ArrayList<SocioPanel> spanels)
	{
		//this.s_Panel = span;
		//
		//this.servDisp = servDisp;
		this.panels = spanels;
		line1 = new JPanel();
		line2 = new JPanel();
		
		line1.setPreferredSize(new Dimension(500,20));
		line2.setPreferredSize(new Dimension(500,30));
		setMinimumSize(new Dimension(500,20));
		setMaximumSize(new Dimension(500,60));
		//setPreferredSize(new Dimension(500,60));
		line1.setLayout(new GridLayout(1,5));
		line2.setLayout(new FlowLayout());
		setLayout(new BorderLayout());
		use_CB = new JCheckBox("Вкл.");
		use_CB.setEnabled(true);
		desc_L = new JLabel("МультиТег");
		oper_CBX = new JComboBox();
		oper_CBX.addItem("Группа");
		oper_CBX.addItem("Тег");
		analytic_CBX = new JComboBox();
		analytic_CBX.addItem("Обычный");
		analytic_CBX.addItem("Авторазбивка");
		oper_CBX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (oper_CBX.getSelectedItem().toString().equals("Тег"))
					line2.setVisible(false);
				else
					line2.setVisible(true);
			}
		});
		panel_CBX = new JComboBox();
		for (SocioPanel panel:panels)
		{
			panel_CBX.addItem(panel.getName());
		}
		
		//oper_CBX.setPreferredSize(new Dimension(60,20));
		bindData = HudWidgetFactory.createHudButton("Связать");
		var_filter_CBX = new JComboBox();
		var_filter_CBX.setMaximumRowCount(30);
		line1.add(panel_CBX);
		line1.add(desc_L);
		line1.add(use_CB);
		//use_CB.setBackground(new Color(30,30,30));
		line1.add(oper_CBX);
		line1.add(analytic_CBX);
		//add(var_filter_CBX);
		//add(logic_CBX);
		line1.add(bindData);
		bindedLabels = new ArrayList<JLabel>();
		bindedToDataParameter = new ArrayList<String>();
		bindData.addActionListener(new ActionListener() {
			private JComboBox cbx;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Internal_IO format = new Internal_IO(null);
				binder = format.bindHeaderCSV();
				System.out.println("fname = "+binder.getFileName());
				for(String st:binder.getSet())
				{
					System.out.println("param = "+st);
				}
				JDialog toBindDlg = new JDialog();
				toBindDlg.setSize(400,100);
				cbx = new JComboBox();
				cbx.addItem("Не связывать.");
				for(String st:binder.getSet())
				{
					cbx.addItem(st);
				}
				cbx.setMaximumRowCount(30);
				cbx.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						bindedToDataParameter.add(cbx.getSelectedItem().toString());
						JLabel lbl = new JLabel(cbx.getSelectedItem().toString());
						bindedLabels.add(lbl);
						line2.add(lbl);
					}
				});
				toBindDlg.add(cbx);
				toBindDlg.setVisible(true);
			}
		});
		name_Group = new JTextField("Имя...");
		desc_Group = new JTextField("Описание...");
		line1.add(name_Group);line1.add(desc_Group);
		add(line1,BorderLayout.NORTH);
		add(line2);
		setVisible(true);
	}

	public void adjustSP(DefaultTableModel model)
	{
		this.procSPP = model;	
	}
	@SuppressWarnings("unused")
	private int findColoumn()
	{
		String compareKey = var_filter_CBX.getSelectedItem().toString();
		for(int i = 0;i < procSPP.getColumnCount();i++)
		{
			if (procSPP.getColumnName(i).equals(compareKey))return i;
		}
		return -1;
	}
	public DefaultTableModel processSP()
	{
		String gr_name = name_Group.getText();
		String gr_desc = desc_Group.getText();
		tag = new SP_Tag(gr_name, gr_desc);
		SocioPanel pnl = panels.get(FindPanelByName(panel_CBX.getSelectedItem().toString()));
		if(analytic_CBX.getSelectedItem().toString().equals("Авторазбивка"))
		{
			tag.setToAutoSubdivide(true);
		}
		pnl.getSp_tags().add(tag);
		//String [] params = {gr_name,gr_desc};
		//servDisp.addGroupLazy(params);
		//Garbage
		DefaultTableModel newModel = new DefaultTableModel();
		if (!use_CB.isSelected())
		{
			return procSPP;			
		}
		//--Garbage
		return newModel;
	}
	public void setVarProcess(Map<String, String> map)
	{
		this.varsToProc = map;
	}
}
