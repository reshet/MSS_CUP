package app_service_quiz;

import javax.swing.JList;

import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;
import base_gui.ToolMainWidget;
// For specific panel - groups
public class ListTasksServerDispatcher implements ServerDispatcher {
	private MSS_RQ_Admin reqHandlerAdmin;
	private JList list;
	private MSS_RQ_TableDescriptor QuizTDesc;
	private MSS_RQ_XMLtoTableDescriptor QuizT_XML_Desc;
	private MSS_RQ_CxListFiller QuizUpdater;
	public ListTasksServerDispatcher(JList lst)
	{
		list = lst;
		reqHandlerAdmin = new MSS_RQ_Admin("Tool", "1006959", ToolMainWidget.pswd);
	}
	@Override
	public void add(Object [] params) {
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"¹"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListTasksElementData.class);
		MSS_RQ_Request.http_request(reqHandlerAdmin.createTask((String)params[0],"Task description",30,0),ToolMainWidget.URL);
		//QuizUpdater.updateData(xml_arr_ans);
		//QuizUpdater.fillCxList(list);
	}

	@Override
	public void delete(Object [] params) {
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"¹"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListTasksElementData.class);
		MSS_RQ_Request.http_request(reqHandlerAdmin.deleteTask((Integer)params[0]),ToolMainWidget.URL);
	}

	@Override
	public void refresh(Object [] params) {
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"¹"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListTasksElementData.class);
		String xml_arr_ans = MSS_RQ_Request.http_request(reqHandlerAdmin.listTasksAviable(),ToolMainWidget.URL);
		QuizUpdater.updateData(xml_arr_ans);
		QuizUpdater.fillCxList(list);
	}
	
	@Override
	public void edit(Object [] params)
	{
		QuizTDesc = new MSS_RQ_TableDescriptor(new String[]{"¹"}, new Class[]{Integer.class});
		QuizT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"name","ID"});
		QuizUpdater = new MSS_RQ_CxListFiller(QuizTDesc,QuizT_XML_Desc, ListTasksElementData.class);
		MSS_RQ_Request.http_request(reqHandlerAdmin.updateTask((String)params[0],"New task decription",Integer.parseInt((String)params[2])),ToolMainWidget.URL);
		//System.out.println(xml_arr_ans);
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
