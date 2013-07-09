package base_SP_Management;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import app_service_quiz.ListUsersPanel;


public class SocioProjectWidget extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8163733543632425864L;
	private SocioProjectMain _project;
	private JTree tree;
	private DefaultTreeModel tree_model;
	private DefaultMutableTreeNode root_node;
	private CardLayout CardListSwither;
	private JPanel SwitcherPanel;
	private Map<String,ListUsersPanel> ListsOfUsers;
	private void doBuildTree(DefaultMutableTreeNode root_node,SP_Tag addTag)
	{
		if(addTag!= null)
		{
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(addTag);
			tree_model.insertNodeInto(newNode, root_node, root_node.getChildCount());	
			//root_node.add(newNode);
			if(addTag.getInternalTags().size()!=0)
			{
				for(String tag_name:addTag.getInternalTags().keySet())
				{
					doBuildTree(newNode,addTag.getInternalTags().get(tag_name));
				}
			}
		}
	}
	private void doUpdateTree(DefaultMutableTreeNode root_node,SP_Tag addTag)
	{
		if(addTag!= null)
		{
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(addTag);
			boolean found = false;
			for(int i = 0; i < root_node.getChildCount();i++)
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) root_node.getChildAt(i);
				if ((SP_Tag)node.getUserObject()==addTag)
				{
					found = true;
					newNode = node;
					break;
				}
			}
			if (!found)
			{
				tree_model.insertNodeInto(newNode, root_node, root_node.getChildCount());	
			}
			//root_node.add(newNode);
			if(addTag.getInternalTags().size()!=0)
			{
				for(String tag_name:addTag.getInternalTags().keySet())
				{
					doUpdateTree(newNode,addTag.getInternalTags().get(tag_name));
				}
			}
		}
	}
	public void showUsersForTag(SP_Tag tag)
	{
		if (ListsOfUsers.containsKey(tag.getName()))
		{
			CardListSwither.show(SwitcherPanel, tag.getName());
		}
		else
		{
			ListUsersPanel panel = new ListUsersPanel(tag.getID());
			panel.setModel((DefaultListModel) tag.getList().getModel());
			ListsOfUsers.put(tag.getName(), panel);
			SwitcherPanel.add(panel,tag.getName());
			CardListSwither.show(SwitcherPanel, tag.getName());
		}
	}
	public SocioProjectWidget(SocioProjectMain prj)
	{
		
		this.CardListSwither = new CardLayout();
		this.SwitcherPanel = new JPanel(CardListSwither);
		JLabel lbl = new JLabel("Empty switcher");
		SwitcherPanel.add(lbl,"empty");
		ListsOfUsers = new TreeMap<String, ListUsersPanel>();
		SwingUtilities.updateComponentTreeUI(this);
		this._project = prj;
		
		SP_Tag root_tag = new SP_Tag("SARS Projects","Root group");
		root_tag.setID(_project.getProjectRootGroupID());
		
		
		//root_tag.updateTagFromServer();
		
		
		for(String tag_name:root_tag.getInternalTags().keySet())
		{
			_project.addSP_Tag(root_tag.getInternalTags().get(tag_name));
		}
		root_node = new DefaultMutableTreeNode(root_tag);
		tree_model  = new DefaultTreeModel(root_node);
		tree = new JTree(tree_model);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent ev) {
				DefaultMutableTreeNode selected_node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				SP_Tag tag = (SP_Tag)selected_node.getUserObject();
				if (tag!=null&&tag.getInternalTags().size()==0)
				{
					String [] params = {String.valueOf(tag.getID())};
					tag.getServDisp().refreshGroupMembers(params);
					showUsersForTag(tag);
				}	
			}
		});
		//?????
		for(SP_Tag tag:_project.getSp_tags())
		{
			doBuildTree(root_node, tag);
		}
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (!Thread.currentThread().isInterrupted())
					{
						//SP_Tag tag = (SP_Tag)root_node.getUserObject();
						//tag.updateTagFromServer();
						//Thread.currentThread();
						
						//doBuildTree(root_node, tag);
						/*
						for(int i = 0; i < root_node.getChildCount();i++)
						{
							tree_model.removeNodeFromParent((MutableTreeNode) root_node.getChildAt(i));
						}
						*/
						Thread.sleep(2000);
						for(SP_Tag tagg:_project.getSp_tags())
						{
							if (!tagg.isUpdateStatusLocal())
								{
									tagg.updateTagFromServer();
								}
							if (!tagg.isUpdateStatusServer())
							{
								tagg.updateTagToServer();
							}
							
							doUpdateTree(root_node, tagg);
						}
						
						/*
						for(SP_Tag tag:((SP_Tag)root_node.getUserObject()).getInternalTags())
						{
							tag.updateTagFromServer();
						}
						*/
						Thread.sleep(10000);
					}	
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		tree.setCellRenderer(new SP_Tag_Widget(new SP_Tag("plumb", "plumb")));
		this.setLayout(new BorderLayout());
		JScrollPane scr = new JScrollPane(tree);
		scr.setPreferredSize(new Dimension(200,400));
		this.add(scr,BorderLayout.WEST);
		this.add(SwitcherPanel,BorderLayout.CENTER);
		this.setBackground(new Color(50,50,50));
		/*
		SourceListModel model = new SourceListModel();
		SourceListCategory category = new SourceListCategory("Category");
		SourceListCategory category2 = new SourceListCategory("Category2");
		SourceListCategory category3 = new SourceListCategory("Category3");
		model.addCategory(category);
		model.addCategory(category2);
		model.addCategory(category3);
		
		model.addItemToCategory(new SourceListItem("Item1"), category);
		model.addItemToCategory(new SourceListItem("Item2"), category);
		model.addItemToCategory(new SourceListItem("Item3"), category);
		model.addItemToCategory(new SourceListItem("Item1"), category2);
		model.addItemToCategory(new SourceListItem("Item2"), category2);
		model.addItemToCategory(new SourceListItem("Item3"), category2);
		model.addItemToCategory(new SourceListItem("Item1"), category3);
		model.addItemToCategory(new SourceListItem("Item2"), category3);
		model.addItemToCategory(new SourceListItem("Item3"), category3);
		//SourceListItem item = new SourceListItem("MegaItem");
		//SourceListItem c_item = new SourceListItem("C_MegaItem");
		//model.addItemToItem(c_item, item);
		//model.addItemToItem(c_item, item);
		//model.addItemToItem(c_item, item);
		//model.addItemToCategory(item,category3);
		SourceList sourceList = new SourceList(model);
		sourceList.getComponent().setPreferredSize(new Dimension(400,400));
		JScrollPane scr = new JScrollPane(sourceList.getComponent());
		scr.setPreferredSize(new Dimension(500,500));
		this.add(scr);
		*/
		
	}
}
