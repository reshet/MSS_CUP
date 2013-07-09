package app_service_quiz;

import javax.swing.JList;

import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;
import base_gui.ToolMainWidget;
// For specific panel - groups
public class ListGroupsServerDispatcher implements ServerDispatcher {
	private MSS_RQ_Admin reqHandlerAdmin;
	private JList list;
	//private String URL;
	private MSS_RQ_TableDescriptor QuizTDesc;
	private MSS_RQ_XMLtoTableDescriptor QuizT_XML_Desc;
	private MSS_RQ_CxListFiller QuizUpdater;
	public ListGroupsServerDispatcher(JList lst)
	{
		list = lst;
		//this.URL = ToolMainWidget.URL;	
		reqHandlerAdmin = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
	}
	@Override
	public void add(Object [] params) {
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"¹"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListGroupsElementData.class);
		String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.makeGroup((String)params[0],"Group description"),ToolMainWidget.URL);
		String st = QuizUpdater.requestLocalData_FromRoot(xml_arr_ans, 1);
		int NewGrID = Integer.parseInt(st);
		@SuppressWarnings("unused")
		String xml_arr_ans2 = MSS_RQ_Request.http_request(reqHandlerAdmin.addHeirGroup(Integer.parseInt((String)params[1]), NewGrID),ToolMainWidget.URL);
		
		//QuizUpdater.updateData(xml_arr_ans);
		//QuizUpdater.fillCxList(list);
	}

	@Override
	public void delete(Object [] params) {
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"¹"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListGroupsElementData.class);
		@SuppressWarnings("unused")
		String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.deleteGroup((Integer)params[0]),ToolMainWidget.URL);
	}

	@Override
	public void refresh(Object [] params) {
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"¹"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"ID","name","desc"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListGroupsElementData.class);
		String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.listOwnGroups(),ToolMainWidget.URL);
		QuizUpdater.updateData(xml_arr_ans);
		QuizUpdater.fillCxList(list);
	}
	@Override
	public void refreshGroupMembers(Object [] params) {
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"¹"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"user","username"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListUsersElementData.class);
		String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.listGroupMembers(Integer.parseInt((String)params[0])),ToolMainWidget.URL);
		QuizUpdater.updateData(xml_arr_ans);
		QuizUpdater.fillCxList(list);
	}
	
	@Override
	public void refreshHeirGroups(Object [] params) {
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"¹"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"ID","name","desc"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListGroupsElementData.class);
		String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.listHeirGroups(Integer.parseInt((String)params[0])),ToolMainWidget.URL);
		QuizUpdater.updateData(xml_arr_ans);
		QuizUpdater.fillCxList(list);
	}
	
	@Override
	public void edit(Object [] params)
	{
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"¹"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListGroupsElementData.class);
		@SuppressWarnings("unused")
		String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.editGroup(Integer.parseInt((String)params[0]),(String)params[1] ),ToolMainWidget.URL);
		//QuizUpdater.updateData(xml_arr_ans);
		//QuizUpdater.fillCxList(list);
	}
}
