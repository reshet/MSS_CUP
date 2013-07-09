package app_service_quiz;

import javax.swing.JList;

import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;
import base_gui.ToolMainWidget;
// For specific panel - groups
public class ListUsersServerDispatcher implements ServerDispatcher {
	private MSS_RQ_Admin reqHandlerAdmin;
	private JList list;
	//private String URL;
	private MSS_RQ_TableDescriptor QuizTDesc;
	private MSS_RQ_XMLtoTableDescriptor QuizT_XML_Desc;
	private MSS_RQ_CxListFiller QuizUpdater;
	public ListUsersServerDispatcher(JList lst)
	{
		list = lst;
		//this.URL = ToolMainWidget.URL;	
		reqHandlerAdmin = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
	}
	@Override
	public void add(Object [] params) {
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"¹"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListUsersElementData.class);
		@SuppressWarnings("unused")
		String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.registerUser(Integer.parseInt((String)params[0]),Integer.parseInt((String)params[2]),(String)params[1],"default"),ToolMainWidget.URL);
		@SuppressWarnings("unused")
		String arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.enrollToGroup(Integer.parseInt((String)params[0]), Integer.parseInt((String)params[2]), "default"),ToolMainWidget.URL);
		
		//QuizUpdater.updateData(xml_arr_ans);
		//QuizUpdater.fillCxList(list);
	}

	@Override
	public void delete(Object [] params) {
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"¹"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListUsersElementData.class);
		@SuppressWarnings("unused")
		String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.kickUserFromGroup((Integer)params[0],(Integer)params[1]),ToolMainWidget.URL);
	}

	@Override
	public void refresh(Object [] params) {
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"user","username"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"user","username"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListUsersElementData.class);
		String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.listGroupMembers((Integer)params[0]),ToolMainWidget.URL);
		QuizUpdater.updateData(xml_arr_ans);
		QuizUpdater.fillCxList(list);
		
		//int groupID = (Integer)params[0];
		for (int i = 0; i < list.getModel().getSize();i++)
		{
			@SuppressWarnings("unused")
			ListUsersElementData data = (ListUsersElementData) list.getModel().getElementAt(i);
			//@SuppressWarnings("unused")
			//String xml_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.listGroupMembers(groupID),ToolMainWidget.URL);
			//TO DO here!!	
		}
	}
	
	@Override
	public void edit(Object [] params)
	{
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"¹"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListUsersElementData.class);
		//@SuppressWarnings("unused")
		//String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.editGroup(Integer.parseInt((String)params[0]),(String)params[1] ),ToolMainWidget.URL);
		//QuizUpdater.updateData(xml_arr_ans);
		//QuizUpdater.fillCxList(list);
	}
	@Override
	public void refreshHeirGroups(Object[] params) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void refreshGroupMembers(Object[] params) {
		// TODO Auto-generated method stub
		
	}
}
