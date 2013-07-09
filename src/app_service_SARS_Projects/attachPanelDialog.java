package app_service_SARS_Projects;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import app_service_quiz.ListGroupsElementData;
import app_service_quiz.ListGroupsPanel;
import app_service_quiz.ListUsersElementData;
import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;
import base_gui.ToolMainWidget;

public class attachPanelDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9040789661636225527L;

	public attachPanelDialog(int idRootGroup)
	{
		JPanel InnerPnl = new JPanel();
		setLayout(new BorderLayout());
		InnerPnl.setLayout(new BorderLayout(3,3));
		setSize(500,500);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int)screenSize.getWidth()/2 - (int)getSize().getWidth()/2,
				(int)screenSize.getHeight()/2 - (int)getSize().getHeight()/2);
		final ListGroupsPanel pnl = new ListGroupsPanel(100015);
		JButton sel = new JButton("Выбрать");
		sel.setPreferredSize(new Dimension(200,30));
		sel.setText("Выбрать");
		sel.setAction(new attachPanel_Action(pnl, this, idRootGroup));
		InnerPnl.add(pnl);
		InnerPnl.add(sel,BorderLayout.SOUTH);
		add(InnerPnl);
		setModal(true);
		setVisible(true);
	}
}

class attachPanel_Action extends AbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6899135803572320417L;
	private ListGroupsPanel pnl;
	private JDialog calledDialog;
	private int idRootGroup;
	public attachPanel_Action(ListGroupsPanel pnl,JDialog dlg,int idRootGroup ) {
		this.pnl = pnl;
		this.calledDialog = dlg;
		this.idRootGroup = idRootGroup;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		final MSS_RQ_TableDescriptor QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"№"}, new Class[]{Integer.class});
		final MSS_RQ_XMLtoTableDescriptor QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
		//final MSS_RQ_TableFiller QuizUpdater = new MSS_RQ_TableFiller(QuizTDesc,QuizT_XML_Desc);
		@SuppressWarnings("unused")
		final MSS_RQ_CxListFiller QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc,ListUsersElementData.class);
		
		final MSS_RQ_Admin reqHandler = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
		new Thread(new Runnable() {
			@Override
			public void run() {
			
				int newGroup = 0;
				int [] arr_indices = pnl.getList().getSelectedIndices();
				DefaultListModel model = (DefaultListModel)pnl.getList().getModel();
				for(Integer elem:arr_indices)
				{
					ListGroupsElementData data = (ListGroupsElementData)model.getElementAt(elem);
					newGroup = data.getID();
					//System.out.println("SAMA");
					MSS_RQ_Request.http_request(reqHandler.addHeirGroup(idRootGroup, newGroup),ToolMainWidget.URL);
				}
				
				/*
				TasksTablePanel pnl = new TasksTablePanel(idRootGroup);
				
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<Integer> task_ids = new ArrayList<Integer>();
				for (int i = 0;i < pnl.getTable().getRowCount();i++)
				{
					
					int cur_id = Integer.parseInt((String) pnl.getTable().getModel().getValueAt(i,1));
					//if( cur_id == idRootGroup)
					task_ids.add(cur_id);
				}
				ArrayList<Integer> user_ids = new ArrayList<Integer>();
				
				ListUsersPanel us_panel = new ListUsersPanel(newGroup);
				DefaultListModel model_users = (DefaultListModel)us_panel.getList().getModel();
				for(int i = 0; i< model_users.getSize();i++)
				{
					ListUsersElementData elem_user = (ListUsersElementData)model_users.getElementAt(i);
					int id_user = Integer.parseInt(elem_user.getName());
					user_ids.add(id_user);
				}
				System.out.println(user_ids.size());
				System.out.println(task_ids.size());
				for(Integer task_n:task_ids)
				{
					System.out.println("TASK_N: "+task_n);
					String xml_ans_3 = MSS_RQ_Request.http_request(reqHandler.extendScopeTask(task_n, newGroup),ToolMainWidget.URL);
					String xml_ans_4 = MSS_RQ_Request.http_request(reqHandler.openTask(newGroup),ToolMainWidget.URL);
					String xml_ans_5 = MSS_RQ_Request.http_request(reqHandler.shareTask(newGroup),ToolMainWidget.URL);
					for(Integer user_id:user_ids)
					{
						String xml_ans_6 = MSS_RQ_Request.http_request(reqHandler.force_subscribeTask(task_n, user_id),ToolMainWidget.URL);
					}
					/// TO DO
					//force subscribe for each user in panel;
				}
				
			
			*/
				
				//System.out.println(anketID);
				calledDialog.setVisible(false);
				/*
				
				*/
			}
		}).start();
		
	}

}
