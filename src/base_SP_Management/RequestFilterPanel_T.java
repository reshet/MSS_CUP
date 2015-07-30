package base_SP_Management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;



public class RequestFilterPanel_T extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -598306773581164587L;
	//private RequestFilterItem_T[] req_I_arr;
	//private int cols =0;
	private JPanel pnl,toolbar;
	private SocioPanel span;
	private ArrayList<SocioPanel> panels;
	private JTree tree;
	private DefaultTreeModel tree_model;
	private DefaultMutableTreeNode root_node;
	//private ArrayList<SocioPanelMember> members;
	private DefaultTableModel _Model;
	@SuppressWarnings("unchecked")
	private DefaultTableModel doSelectSubgroupMembers(String grouping_tag_name, SP_Tag subgrouping_tag)
	{
		Vector<Vector<String>> allData = _Model.getDataVector();
		DefaultTableModel model = new DefaultTableModel(0,_Model.getColumnCount());
		@SuppressWarnings("rawtypes")
		Vector col_ident = new Vector(_Model.getColumnCount());
		for (int i = 0;i < _Model.getColumnCount();i++)
		{
			col_ident.addElement(_Model.getColumnName(i));
		}
		model.setColumnIdentifiers(col_ident);
		int col_filter = RequestFilterItem_T.findColoumn(_Model, grouping_tag_name);
		if (col_filter != -1)
			for(int i = 0; i < _Model.getRowCount();i++)
			{
				if(i<_Model.getRowCount()
						&&
						col_filter<_Model.getColumnCount()
						&&
						_Model.getValueAt(i,col_filter)!=null
						&&
						_Model.getValueAt(i,col_filter).equals(subgrouping_tag.getName()))
				{
					Vector<String> row = new Vector<String>(allData.elementAt(i));
					model.addRow(row);
				}	
			}
		return model;
	}
	@SuppressWarnings("unchecked")
	public DefaultTableModel doProcessTreeOfQueries(DefaultMutableTreeNode node,DefaultTableModel modelPassed)
	{
		if (!((RequestFilterItem_T)node.getUserObject()).isUsed()) return modelPassed;		
		if(!node.isLeaf())
		{	
			if(((RequestFilterItem_T)node.getUserObject()).getMode()==11)
				//COMPLEX QUERY AND
			{
				DefaultTableModel modelToPass = doProcessTreeOfQueries((DefaultMutableTreeNode) node.getChildAt(0), modelPassed);
				for(int i = 1;i < node.getChildCount();i++)
				{
					modelToPass = doProcessTreeOfQueries((DefaultMutableTreeNode)node.getChildAt(i), modelToPass);
				}
				return modelToPass;
			}
			else if(((RequestFilterItem_T)node.getUserObject()).getMode()==12)
				//COMPLEX QUERY OR
			{
				DefaultTableModel modelToPass = doProcessTreeOfQueries((DefaultMutableTreeNode) node.getChildAt(0), modelPassed);
				for(int i = 1;i < node.getChildCount();i++)
				{
					DefaultTableModel modelToAdd = doProcessTreeOfQueries((DefaultMutableTreeNode)node.getChildAt(i), modelPassed);
					Vector<Vector<String>> allData = modelToAdd.getDataVector();
					for (int j = 0; j < allData.size();j++)
					{
						modelToPass.addRow(allData.elementAt(j));
					}
				}
				return modelToPass;
			}
			else if(((RequestFilterItem_T)node.getUserObject()).getMode()==13)
				//COMPLEX QUERY Iterator (works like OR for all subtagged members for choosen tag)
			{
				String grouping_tag_name = ((RequestFilterItem_T)node.getUserObject()).getCompareValue();
				DefaultTableModel modelToPass = new DefaultTableModel(0,_Model.getColumnCount());
				@SuppressWarnings("rawtypes")
				Vector col_ident = new Vector(_Model.getColumnCount());
				for (int i = 0;i < _Model.getColumnCount();i++)
				{
					col_ident.addElement(_Model.getColumnName(i));
				}
				modelToPass.setColumnIdentifiers(col_ident);
				if (((RequestFilterItem_T)node.getUserObject()).getTagsToIterate()!= null)
				for(SP_Tag tag:((RequestFilterItem_T)node.getUserObject()).getTagsToIterate())
				{
					DefaultTableModel model_iter = doSelectSubgroupMembers(grouping_tag_name, tag);
					for(int i = 0;i < node.getChildCount();i++)
					{
						DefaultTableModel modelToAdd = doProcessTreeOfQueries((DefaultMutableTreeNode)node.getChildAt(i), model_iter);
						Vector<Vector<String>> allData = modelToAdd.getDataVector();
						for (int j = 0; j < allData.size();j++)
						{
							@SuppressWarnings("rawtypes")
							Vector<String> row = new Vector(allData.elementAt(j));
							modelToPass.addRow(row);
						}
					}
				}
				return modelToPass;
			}
		} 
		RequestFilterItem_T item = (RequestFilterItem_T) node.getUserObject();
		item.adjustSP(modelPassed);
		DefaultTableModel modelToReturn = item.processSP();
		return modelToReturn;
	}
	public RequestFilterPanel_T(ArrayList<SocioPanel> spanels,SocioPanel base_pnl) 
	{
		panels = spanels;
		this.span = base_pnl;
		_Model = span.getModel();
		//members = span.getMembers();
		pnl = new JPanel();
		pnl.setLayout(new BorderLayout());
		pnl.setPreferredSize(new Dimension(300,600));
		pnl.setBackground(new Color(50,50,50));
		//req_I_arr = new RequestFilterItem_T[20];
		JScrollPane scroller = new JScrollPane(pnl);
		this.setLayout(new BorderLayout());
		toolbar = new JPanel();
		toolbar.setLayout(new GridLayout(1,3));
		JButton addbtn = new JButton("+");
		JButton delbtn = new JButton("-");
		JButton dobtn = new JButton("Filter!");

		RequestFilterItem_T root_item = new RequestFilterItem_T(panels);
		root_item.doComplexFilterPrepare();
		root_node = new DefaultMutableTreeNode(root_item);

		RequestFilterItem_T root_child = new RequestFilterItem_T(panels);
		root_child.doSimpleFilterPrepare();
		root_node.add(new DefaultMutableTreeNode(root_child));
		tree_model  = new DefaultTreeModel(root_node);
		tree = new JTree(tree_model);
		tree.setCellRenderer(new RequestFilterItem_T());
		tree.setCellEditor(new RequestFilterItem_T());
		tree.setEditable(true);
		tree.setBackground(new Color(20,20,20));
		//tree.setRootVisible(false);
		pnl.add(new JScrollPane(tree));
		addbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//if (cols < 20)
				{
					//req_I_arr[++cols] = new RequestFilterItem_T(panels);
					//req_I_arr[cols].setS_Panel(span);
					//pnl.add(req_I_arr[cols]);
					DefaultMutableTreeNode selected_node_as_parent = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					RequestFilterItem_T item =new RequestFilterItem_T(panels);
					item.doSimpleFilterPrepare();
					if (selected_node_as_parent != null)
					{
						RequestFilterItem_T parent_node_item = (RequestFilterItem_T) selected_node_as_parent.getUserObject();
						parent_node_item.doComplexFilterPrepare();
						item.setPrimaryPanelToProcess(parent_node_item.getPrimaryPanelToProcess());
						tree_model.insertNodeInto(new DefaultMutableTreeNode(item) , selected_node_as_parent, selected_node_as_parent.getChildCount());
						//parent_node_item.getOper_CBX().actionPerformed(null);
						//deliverEvent(new Event(parent_node_item.getOper_CBX(), Event.ACTION_EVENT, null));					
					}
						//root_node.add((MutableTreeNode) req_I_arr[cols]);
					//pnl.repaint();
					//pnl.validate();
					
				}
			}
		});
		delbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//if (cols > 0 )
				{
					//pnl.remove(req_I_arr[cols--]);
					
					//req_I_arr[cols--] = null;
					DefaultMutableTreeNode selected_node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					DefaultMutableTreeNode parent_node= (DefaultMutableTreeNode) selected_node.getParent();
					if (selected_node != null && !(selected_node.getLevel()==1 && selected_node.getParent().getChildCount()<=1))
					{
						if (parent_node.getChildCount()==1)
						{
							RequestFilterItem_T parent_node_item = (RequestFilterItem_T) parent_node.getUserObject();			
							parent_node_item.doSimpleFilterPrepare();
						}
						tree_model.removeNodeFromParent(selected_node);
					}
				//	pnl.repaint();
				//	pnl.valifddate();
				}
			}
		});
		dobtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Map<String,String> [] allMaps = new HashMap[_Model.getRowCount()];
				
				
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						JDialog dlgg = new JDialog();
						dlgg.setTitle("Обработка данных. Подождите, пожалуйста!");
						dlgg.setSize(new Dimension (400,80));
						dlgg.setLocation(400, 400);
						//JProgressBar p_bar = new JProgressBar(0, (int)_Model.getRowCount());
						//p_bar.setPreferredSize(new Dimension (400,20));
						//dlgg.add(p_bar);
						dlgg.setVisible(true);
						
						/*
						req_I_arr[0].adjustSP(_Model);
						for (int i = 1; i <= cols;i++)
						{
							req_I_arr[i].adjustSP(req_I_arr[i-1].processSP());
							p_bar.setValue(p_bar.getMaximum()/cols*i);
						}
						DefaultTableModel ResultMaps = req_I_arr[cols].processSP();
						*/
						//int colls = root_node.getLeafCount();
						
						RequestFilterItem_T root_obj_item = (RequestFilterItem_T)root_node.getUserObject();
						_Model = root_obj_item.getS_Panel().getModel();
						//root_obj_item.setPrimaryPanelToProcess(root_obj_item.get)
						//root_obj_item.adjustSP(model)
						DefaultTableModel ResultMaps = doProcessTreeOfQueries(root_node, _Model);
						
						//p_bar.setValue(p_bar.getMaximum());
						dlgg.setVisible(false);
						//Set<String> totalSet = new HashSet<String>(ResultMaps.length);
						
						span.setModel(ResultMaps);
						//
						//make new panel
						/*
						SocioPanel sp = new SocioPanel(root_obj_item.getS_Panel().getS_project());
						//span.getS_project().addSocioPanel(sp);
						//sp.setS_project(span.getS_project());
						
						String newSpanelName = JOptionPane.showInputDialog("Имя новой панели:");
						sp.setName(newSpanelName);
						sp.setModel(ResultMaps);
						sp.setAnalysysForm(root_obj_item.getS_Panel().getAnalysysForm());
						SocioPanelWidget SPanel = new SocioPanelWidget();
						///!!!!!Bad style
						sp.getDesk().getFormat_io().set_Model(sp.getModel());
						SPanel.adjustSocioPanel(sp);
						
						SidePalleteSparta spWindow  = new SidePalleteSparta(SPanel,null);
						spWindow.setTitle(sp.getName());
						spWindow.setVisible(true);
						*/
					}
				}).start();
				
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
class RequestFilterItem_T extends JPanel  implements TreeCellRenderer, TreeCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8291657683487581926L;
	private JCheckBox use_CB;
	private boolean isUsed = false;
	@SuppressWarnings("unused")	private JLabel desc_L;
	private JComboBox oper_CBX,complex_CBX,var_filter_1_CBX,var_filter_2_CBX, logic_CBX,panel_1_CBX,panel_2_CBX,var_filter_LJoin_CBX;
	private JTextField value_EP_1;
	private DefaultTableModel procSPP;
	@SuppressWarnings("unused")
	private Map<String,String> varsToProc;
	private ArrayList<SP_Tag> tagsToIterate;
	public ArrayList<SP_Tag> getTagsToIterate() {
		return tagsToIterate;
	}
	public void setTagsToIterate(ArrayList<SP_Tag> tagsToIterate) {
		this.tagsToIterate = tagsToIterate;
	}
	private String compareStr;
	public String getCompareValue() {
		return compareValue;
	}
	public boolean isUsed() {
		return isUsed;
	}
	private String compareValue;
	private SocioPanel s_Panel;
	private ArrayList<SocioPanel> panels;
	private int mode = 0;
	public int getMode() {
		return mode;
	}
	//mode = 1 - linking 2 panels
	//mode = 11 - brances mode AND
	//mode = 12 - brances mode OR
	public RequestFilterItem_T()
	{
		compareValue = "";
	}
	public JComboBox getOper_CBX() {
		return oper_CBX;
	}
	public void setPrimaryPanelToProcess(String p_name)
	{
		s_Panel = panels.get(FindPanelByName(p_name));
		procSPP = s_Panel.getModel();
		panel_1_CBX.removeAllItems();
		panel_1_CBX.addItem(p_name);
	}
	public String getPrimaryPanelToProcess()
	{
		return panel_1_CBX.getSelectedItem().toString();
	}
	public void doComplexFilterPrepare()
	{
		getOper_CBX().removeAllItems();
		getOper_CBX().addItem("Составной");
		complex_CBX.setVisible(true);
		logic_CBX.setVisible(false);
		value_EP_1.setVisible(false);
		var_filter_1_CBX.setVisible(false);
		var_filter_2_CBX.setVisible(false);
		var_filter_LJoin_CBX.setVisible(false);
		panel_2_CBX.setVisible(false);
		if (complex_CBX.getSelectedItem().toString().equals("И")) mode = 11;else
		if (complex_CBX.getSelectedItem().toString().equals("ИЛИ")) mode = 12;else mode = 13;
	}
	public void doSimpleFilterPrepare()
	{
		getOper_CBX().removeAllItems();
		value_EP_1.setVisible(true);
		logic_CBX.setVisible(true);
		var_filter_1_CBX.setVisible(true);
		var_filter_2_CBX.setVisible(false);
		var_filter_LJoin_CBX.setVisible(false);
		panel_2_CBX.setVisible(false);
		complex_CBX.setVisible(false);
		mode = 0;	
		getOper_CBX().addItem("Содержит");
		getOper_CBX().addItem("Исключая");
		getOper_CBX().addItem("Связать");
		//getOper_CBX().addItem("Итератор");
	}
	public RequestFilterItem_T(ArrayList<SocioPanel> spanels)
	{
		this.panels = spanels;
		if (spanels.size() != 0)
			this.s_Panel = spanels.get(0);
		setPreferredSize(new Dimension(900,40));
		setSize(new Dimension(900,40));
		setAlignmentX(LEFT_ALIGNMENT);
		setBorder(BorderFactory.createLineBorder(new Color(20,200,20)));
		//setBackground(new Color(200,100,30));
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		
		use_CB = new JCheckBox("Фильтр");
		use_CB.setEnabled(true);
		desc_L = new JLabel("Фильтр");
		oper_CBX = new JComboBox();
		//oper_CBX.addItem("Составной");
		//oper_CBX.addItem("Содержит");
		//oper_CBX.addItem("Исключая");
		//oper_CBX.addItem("Связать");
		//oper_CBX.setPreferredSize(new Dimension(60,20));
		logic_CBX = new JComboBox();
		complex_CBX = new JComboBox();
		complex_CBX.addItem("И");
		complex_CBX.addItem("ИЛИ");
		complex_CBX.addItem("Итератор");
		complex_CBX.setSelectedIndex(0);
		complex_CBX.setVisible(false);
		logic_CBX.setPreferredSize(new Dimension(55,25));
		logic_CBX.addItem("=");
		logic_CBX.addItem(">");
		logic_CBX.addItem(">=");
		logic_CBX.addItem("<");
		logic_CBX.addItem("<=");
		logic_CBX.addItem("<>");
		logic_CBX.addItem("R_S");
		logic_CBX.addItem("R_Pr");
		panel_1_CBX = new JComboBox();
		for(SocioPanel panel:panels)
		{
			panel_1_CBX.addItem(panel.getName());
		}
		panel_2_CBX = new JComboBox();
		for(SocioPanel panel:panels)
		{
			panel_2_CBX.addItem(panel.getName());
		}
		compareStr = logic_CBX.getSelectedItem().toString();
		logic_CBX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				compareStr = logic_CBX.getSelectedItem().toString();
			}
		});
		var_filter_1_CBX = new JComboBox();
		var_filter_1_CBX.setMaximumRowCount(30);
		if (s_Panel != null)
		if (s_Panel.getAnalysysForm()!=null && s_Panel.getAnalysysForm().getVar_names() != null)
		for (String str:s_Panel.getAnalysysForm().getVar_names())
		{
			var_filter_1_CBX.addItem(str);
		}
		
		var_filter_2_CBX = new JComboBox();
		var_filter_2_CBX.setMaximumRowCount(30);
		
		if (s_Panel != null)
		if (s_Panel.getAnalysysForm()!= null&&s_Panel.getAnalysysForm().getVar_names() != null)
		for (String str:s_Panel.getAnalysysForm().getVar_names())
		{
			var_filter_2_CBX.addItem(str);
		}
		var_filter_LJoin_CBX = new JComboBox();
		var_filter_LJoin_CBX.setMaximumRowCount(30);
		
		//add(desc_L);
		add(use_CB);
		//use_CB.setBackground(new Color(30,30,30));
		add(oper_CBX);
		add(complex_CBX);
		add(panel_1_CBX);
		add(var_filter_1_CBX);
		add(logic_CBX);
		add(panel_2_CBX);
		add(var_filter_2_CBX);
		add(var_filter_LJoin_CBX);
		var_filter_1_CBX.setVisible(false);
		var_filter_2_CBX.setVisible(false);
		var_filter_LJoin_CBX.setVisible(false);
		panel_2_CBX.setVisible(false);
		logic_CBX.setVisible(false);
		value_EP_1 = new JTextField("");
		value_EP_1.setVisible(false);
		value_EP_1.setPreferredSize(new Dimension(200,25));
		add(value_EP_1);
		use_CB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel_1_CBX.removeAllItems();
				panel_2_CBX.removeAllItems();
				for(SocioPanel panel:panels)
				{
					panel_1_CBX.addItem(panel.getName());
					panel_2_CBX.addItem(panel.getName());
				}
			}
		});
		//value_EP_2 = new JTextField("");
		//add(value_EP_2);
		oper_CBX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(oper_CBX.getSelectedItem()!= null)
				if (oper_CBX.getSelectedItem().toString().equals("Связать"))
				{
					value_EP_1.setVisible(false);
					var_filter_1_CBX.setVisible(true);
					var_filter_2_CBX.setVisible(true);
					var_filter_LJoin_CBX.setVisible(true);
					logic_CBX.setVisible(true);
					panel_2_CBX.setVisible(true);
					mode = 1;//Linking 2 panels = LeftJoin SQL
				}else
				if(oper_CBX.getSelectedItem().toString().equals("Составной"))
				{
					complex_CBX.setVisible(true);
					logic_CBX.setVisible(false);
					value_EP_1.setVisible(false);
					var_filter_1_CBX.setVisible(false);
					var_filter_2_CBX.setVisible(false);
					var_filter_LJoin_CBX.setVisible(false);
					panel_2_CBX.setVisible(false);
					if (complex_CBX.getSelectedItem().toString().equals("И")) mode = 11;
					if (complex_CBX.getSelectedItem().toString().equals("ИЛИ")) mode = 12;
					else mode = 13;
				}
				else
				{
					value_EP_1.setVisible(true);
					logic_CBX.setVisible(true);
					var_filter_1_CBX.setVisible(true);
					var_filter_2_CBX.setVisible(false);
					var_filter_LJoin_CBX.setVisible(false);
					panel_2_CBX.setVisible(false);
					complex_CBX.setVisible(false);
					mode = 0;	
				}
			}
		});
		complex_CBX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (oper_CBX.getSelectedItem().toString().equals("Составной"))
				{
					if (complex_CBX.getSelectedItem().toString().equals("Итератор"))
					{
						mode = 13;
						var_filter_1_CBX.removeAllItems();
						for(SP_Tag tag:panels.get(FindPanelByName(panel_1_CBX.getSelectedItem().toString())).getSp_tags())
						{
							var_filter_1_CBX.addItem(tag.getName());
						}
						if(var_filter_1_CBX.getSelectedItem()!=null)
							compareValue = var_filter_1_CBX.getSelectedItem().toString();
						var_filter_1_CBX.setVisible(true);
					}else
					{
						var_filter_1_CBX.setVisible(false);
					}
					if (complex_CBX.getSelectedItem().toString().equals("И")) mode = 11;
					if (complex_CBX.getSelectedItem().toString().equals("ИЛИ")) mode = 12;		
				}
			}
		});
		
		panel_1_CBX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (panel_1_CBX.getSelectedItem()!= null)
				{
					String p_name = panel_1_CBX.getSelectedItem().toString();
					SocioPanel panel = panels.get(FindPanelByName(p_name));
					//ATTENTION!
					s_Panel = panel;
					adjustSP(s_Panel.getModel());
					var_filter_1_CBX.removeAllItems();
					if (complex_CBX.getSelectedItem().toString().equals("Итератор"))
					{
						if (panel.getSp_tags()!= null)
							for (SP_Tag tag:panel.getSp_tags())
							{
								var_filter_1_CBX.addItem(tag.getName());
							}
						if(var_filter_1_CBX.getSelectedItem()!=null)
							compareValue = var_filter_1_CBX.getSelectedItem().toString();
					}
					else
					{
						if (panel.getAnalysysForm()!= null&&panel.getAnalysysForm().getVar_names() != null)
							for (String str:panel.getAnalysysForm().getVar_names())
							{
								var_filter_1_CBX.addItem(str);
							}
					}
				}
			}
		});
		panel_2_CBX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(panel_2_CBX.getSelectedItem()!= null)
				{
					String p_name = panel_2_CBX.getSelectedItem().toString();
					SocioPanel panel = panels.get(FindPanelByName(p_name));
					var_filter_2_CBX.removeAllItems();
					var_filter_LJoin_CBX.removeAllItems();
					if (panel.getAnalysysForm()!= null&&panel.getAnalysysForm().getVar_names() != null)
					for (String str:panel.getAnalysysForm().getVar_names())
					{
						var_filter_2_CBX.addItem(str);
						var_filter_LJoin_CBX.addItem(str);
					}
				}
			}
		});
		use_CB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isUsed = use_CB.isEnabled();
			}
		});
		var_filter_1_CBX.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (mode == 13)
				{
					String p_name = panel_1_CBX.getSelectedItem().toString();
					SocioPanel panel = panels.get(FindPanelByName(p_name));
					for (SP_Tag tag:panel.getSp_tags())
					{
						if (var_filter_1_CBX.getSelectedItem()!=null
								&&
								tag.getName().equals(var_filter_1_CBX.getSelectedItem().toString()))
						{
							tagsToIterate = new ArrayList<SP_Tag>();
							for(SP_Tag tagg:tag.getInternalTags().values())
							{
								tagsToIterate.add(tagg);
							}
						}
					}
					if(var_filter_1_CBX.getSelectedItem()!=null)
						compareValue = var_filter_1_CBX.getSelectedItem().toString();
				}
			}
		});
		setAlignmentY(LEFT_ALIGNMENT);
		setVisible(true);
	}
	public SocioPanel getS_Panel() {
		return s_Panel;
	}
	public void setS_Panel(SocioPanel sPanel) {
		s_Panel = sPanel;
	}
	public void adjustSP(DefaultTableModel model)
	{
		this.procSPP = model;	
	}
	private int findColoumn()
	{
		String compareKey = var_filter_1_CBX.getSelectedItem().toString();
		for(int i = 0;i < procSPP.getColumnCount();i++)
		{
			if (procSPP.getColumnName(i).equals(compareKey))return i;
		}
		/*
		int found = 1;
		for(String str:s_Panel.getAnalysysForm().getVar_names())
		{
			if (str.equals(compareKey)) return s_Panel.getAnalysysForm().getVar_names().size()-found-1;
			found++;
		}
		*/
		return -1;
	}
	static public int findColoumn(DefaultTableModel model,String col_name)
	{
		for(int i = 0;i <model.getColumnCount();i++)
		{
			if (model.getColumnName(i).equals(col_name))return i;
		}
		return -1;
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
	@SuppressWarnings("unchecked")
	public DefaultTableModel processSP()
	{
		JDialog dlgg = new JDialog();
		dlgg.setTitle("Загрузка данных.Подождите, пожалуйста!");
		dlgg.setSize(new Dimension (400,80));
		dlgg.setLocation(400, 400);
		JProgressBar p_bar = new JProgressBar(0, procSPP.getColumnCount());
		p_bar.setPreferredSize(new Dimension (400,20));
		dlgg.add(p_bar);
		dlgg.setVisible(true);
		
		compareValue = value_EP_1.getText();
		DefaultTableModel newModel = new DefaultTableModel();
		@SuppressWarnings("rawtypes")
		Vector col_ident = new Vector(procSPP.getColumnCount());
		for (int i = 0;i < procSPP.getColumnCount();i++)
		{
			col_ident.addElement(procSPP.getColumnName(i));
		}
		newModel.setColumnIdentifiers(col_ident);
		newModel.setColumnCount(procSPP.getColumnCount());
		newModel.setRowCount(0);
		//Vector arr = procSPP.getDataVector();
		int column = findColoumn();
		if (!use_CB.isSelected()||column==-1)
		{
			return procSPP;			
		}
		if (mode == 1)
		{
			if (compareStr.equals("="))
			{
				DefaultTableModel model = panels.get(FindPanelByName(panel_1_CBX.getSelectedItem().toString())).getModel();
				DefaultTableModel model2 = panels.get(FindPanelByName(panel_2_CBX.getSelectedItem().toString())).getModel();
				String joinOverVar = var_filter_LJoin_CBX.getSelectedItem().toString();
				col_ident.add(joinOverVar);
				int joinOverColID = findColoumn(model2,joinOverVar);
				newModel.setColumnIdentifiers(col_ident);
				//newModel.setColumnCount(newModel.getColumnCount()+1);
				for(int i = 0; i < model.getRowCount();i++)
				{
					p_bar.setValue(i);
					if(model.getValueAt(i, column)!= null)
					for(int j = 0; j < model2.getRowCount();j++)
					{
						String compVal = (String) model.getValueAt(i, column);
						if(model2.getValueAt(j, column)!= null)
						if(model2.getValueAt(j, column).toString().compareTo(compVal)==0)
						{
							newModel.setRowCount(newModel.getRowCount()+1);
							for(int ir = 0;ir< model.getColumnCount();ir++)
							{
								newModel.setValueAt(model.getValueAt(i, ir), newModel.getRowCount()-1, ir);
							}
							newModel.setValueAt(model2.getValueAt(j, joinOverColID), newModel.getRowCount()-1, newModel.getColumnCount()-1);
						}
					}
				}
			}
			dlgg.setVisible(false);
			return newModel;
		}
		if (mode == 13)
		{
			if (compareStr.equals("R_S"))
			{
				int how_many_select = Integer.parseInt(value_EP_1.getText());
				Random randomizator = new Random(procSPP.getRowCount());
				//randomizator
				for(int i = 0; i < how_many_select;i++)
				{
					p_bar.setValue(i);
					int random_index = randomizator.nextInt(procSPP.getRowCount());
					newModel.setRowCount(newModel.getRowCount()+1);
					for(int ir = 0;ir< procSPP.getColumnCount();ir++)
					{
						if(procSPP.getValueAt(random_index, column)!= null)
						{
							newModel.setValueAt(procSPP.getValueAt(random_index, ir), newModel.getRowCount()-1, ir);
						}
					}
				}
			}
		}	
		if (compareStr.equals(">"))
		{
			for(int i = 0; i < procSPP.getRowCount();i++)
			{
				//if(aMap.containsKey(compareKey))
				p_bar.setValue(i);
				if(procSPP.getValueAt(i, column)!= null)
				if(procSPP.getValueAt(i, column).toString().compareTo(compareValue)>0)
				{
					newModel.setRowCount(newModel.getRowCount()+1);
					for(int ir = 0;ir< procSPP.getColumnCount();ir++)
					{
						newModel.setValueAt(procSPP.getValueAt(i, ir), newModel.getRowCount()-1, ir);
					}
				}
			}
		} else
		if (compareStr.equals(">="))
		{
			for(int i = 0; i < procSPP.getRowCount();i++)
			{
				p_bar.setValue(i);
				if(procSPP.getValueAt(i, column)!= null)
				if(procSPP.getValueAt(i, column).toString().compareTo(compareValue)>0
						||
						procSPP.getValueAt(i, column).toString().compareTo(compareValue)==0)
				{
					newModel.setRowCount(newModel.getRowCount()+1);
					for(int ir = 0;ir< procSPP.getColumnCount();ir++)
					{
						newModel.setValueAt(procSPP.getValueAt(i, ir), newModel.getRowCount()-1, ir);
					}
				}
			}
		}else	
		if (compareStr.equals("<"))
		{
			for(int i = 0; i < procSPP.getRowCount();i++)
			{
				p_bar.setValue(i);
				if(procSPP.getValueAt(i, column)!= null)
				if(procSPP.getValueAt(i, column).toString().compareTo(compareValue)<0)
				{
					newModel.setRowCount(newModel.getRowCount()+1);
					for(int ir = 0;ir< procSPP.getColumnCount();ir++)
					{
						newModel.setValueAt(procSPP.getValueAt(i, ir), newModel.getRowCount()-1, ir);
					}
				}
			}
		} else
		if (compareStr.equals("<="))
		{
			for(int i = 0; i < procSPP.getRowCount();i++)
			{
				p_bar.setValue(i);
				if(procSPP.getValueAt(i, column)!= null)
				if(procSPP.getValueAt(i, column).toString().compareTo(compareValue)<0
						||
						procSPP.getValueAt(i, column).toString().compareTo(compareValue)==0)
				{
					newModel.setRowCount(newModel.getRowCount()+1);
					for(int ir = 0;ir< procSPP.getColumnCount();ir++)
					{
						newModel.setValueAt(procSPP.getValueAt(i, ir), newModel.getRowCount()-1, ir);
					}
				}
			}
		} else
		if (compareStr.equals("="))
		{
			for(int i = 0; i < procSPP.getRowCount();i++)
			{
				p_bar.setValue(i);
				if(procSPP.getValueAt(i, column)!= null)
				if(procSPP.getValueAt(i, column).toString().compareTo(compareValue)==0)
				{
					newModel.setRowCount(newModel.getRowCount()+1);
					for(int ir = 0;ir< procSPP.getColumnCount();ir++)
					{
						newModel.setValueAt(procSPP.getValueAt(i, ir), newModel.getRowCount()-1, ir);
					}
				}
			}
		} else
		if (compareStr.equals("<>"))
		{
			for(int i = 0; i < procSPP.getRowCount();i++)
			{
				p_bar.setValue(i);
				if(procSPP.getValueAt(i, column)!= null)
				if(procSPP.getValueAt(i, column).toString().compareTo(compareValue)!=0)
				{
					newModel.setRowCount(newModel.getRowCount()+1);
					for(int ir = 0;ir< procSPP.getColumnCount();ir++)
					{
						newModel.setValueAt(procSPP.getValueAt(i, ir), newModel.getRowCount()-1, ir);
					}
				}
			}
		}
		if (compareStr.equals("R_S"))
		{
			Set<Integer> set= new HashSet<Integer>();
			int how_many_select = Integer.parseInt(value_EP_1.getText());
			Random randomizator = new Random(procSPP.getRowCount());
			while(set.size() < how_many_select)
			{
				int random_index = randomizator.nextInt(procSPP.getRowCount());
				if(!set.contains(random_index))set.add(random_index);
			}
			int [] arr = new int[how_many_select];
			int counter = 0;
			for(Integer in: set)
			{
				arr[counter++] = in;
			}
			//randomizator
			for(int i = 0; i < how_many_select;i++)
			{
				p_bar.setValue(i);
				//int random_index = randomizator.nextInt(procSPP.getRowCount());
				newModel.setRowCount(newModel.getRowCount()+1);
				for(int ir = 0;ir< procSPP.getColumnCount();ir++)
				{
					if(procSPP.getValueAt(arr[i], column)!= null)
					{
						newModel.setValueAt(procSPP.getValueAt(arr[i], ir), newModel.getRowCount()-1, ir);
					}
				}
			}
		}
		if (compareStr.equals("R_Pr"))
		{ 
			//Here we use percent of proportianlity: 100% means equally proportianal
			int how_many_select = 
				(int) Math.round((Integer.parseInt(value_EP_1.getText())*0.01*procSPP.getRowCount()));
			Random randomizator = new Random(procSPP.getRowCount());
			for(int i = 0; i < how_many_select;i++)
			{
				p_bar.setValue(i);
				int random_index = randomizator.nextInt(procSPP.getRowCount());
				newModel.setRowCount(newModel.getRowCount()+1);
				for(int ir = 0;ir< procSPP.getColumnCount();ir++)
				{
					if(procSPP.getValueAt(random_index, column)!= null)
					{
					newModel.setValueAt(procSPP.getValueAt(random_index, ir), newModel.getRowCount()-1, ir);
					}
				}
			}
		}
		dlgg.setVisible(false);
		return newModel;
		
	}
	public void setVarProcess(Map<String, String> map)
	{
		this.varsToProc = map;
	}
	@Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
		      boolean selected, boolean expanded, boolean leaf, int row,
		      boolean hasFocus) 
	{
		    Component returnValue = null;
		    if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
		      Object userObject = ((DefaultMutableTreeNode) value)
		          .getUserObject();
		      if (userObject instanceof RequestFilterItem_T) {
		    	RequestFilterItem_T query_elem = (RequestFilterItem_T) userObject;
		       
		    	if (selected) {
		    		query_elem.setBackground(new Color(60,60,60));
			        } else {
			        query_elem.setBackground(new Color(60,60,60));
			        }
		        this.setEnabled(true);
		        query_elem.setEnabled(true);
		        returnValue = query_elem;
		      }
		    }
		    if (returnValue == null) {
		      returnValue = new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree,
		          value, selected, expanded, leaf, row, hasFocus);
		    }
		    return returnValue;
		  }
	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value,
		      boolean selected, boolean expanded, boolean leaf, int row) 
	{
		    Component returnValue = null;
		    if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
		      Object userObject = ((DefaultMutableTreeNode) value)
		          .getUserObject();
		      if (userObject instanceof RequestFilterItem_T) {
		    	RequestFilterItem_T query_elem = (RequestFilterItem_T) userObject;
		       
		    	if (selected) {
		    		query_elem.setBackground(new Color(49,140,26));
			        } else {
			        query_elem.setBackground(new Color(49,140,26));
			        }
		        this.setEnabled(true);
		        query_elem.setEnabled(true);
		        returnValue = query_elem;
		      }
		    }
		    if (returnValue == null) {
		      returnValue = new DefaultTreeCellEditor(tree, new DefaultTreeCellRenderer()).getTreeCellEditorComponent(tree,
		          value, selected, expanded, leaf, row);
		    }
		    return returnValue;
		  }
	@Override
	public void addCellEditorListener(CellEditorListener arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void cancelCellEditing() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isCellEditable(EventObject arg0) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void removeCellEditorListener(CellEditorListener arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean shouldSelectCell(EventObject arg0) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean stopCellEditing() {
		// TODO Auto-generated method stub
		return true;
	}
}
