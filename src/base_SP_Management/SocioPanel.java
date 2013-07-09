package base_SP_Management;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;

import app_service_quiz.ServerDispatcher;
import base_data_exchanger.Internal_IO;

public class SocioPanel {
	private int ID_GroupOwn;
	private String name = "Panel_1";
	private int ID_in_prj;
	private ArrayList<SocioPanelMember> members;
	private SocioAnalysisForm analysysForm;
	private SocioInstrumentDesk desk;
	@SuppressWarnings("unused")
	private ServerDispatcher servDisp;
	private Internal_IO format_io;
	private JList list;
	private DefaultTableModel model,meta_model;
	private DefaultListModel listMembersModel,listTagsModel;
	private int col_panelsists = 0;
	private ArrayList<SP_Tag> sp_tags;
	private SocioProjectMain s_project;
	private SocioPanelWidget widget;
	public void setWidget(SocioPanelWidget widget) {
		this.widget = widget;
	}
	public SocioPanelWidget getWidget() {
		return widget;
	}
	public SocioPanel(int s_prj_plumb){}
	public SocioPanel(SocioProjectMain s_prj)
	{
		this.s_project = s_prj;
		s_project.addSocioPanel(this);
		sp_tags = new ArrayList<SP_Tag>();
		members = new ArrayList<SocioPanelMember>(50);
		list = new JList();
		servDisp = new SP_ServerDispatcher(list);
		listMembersModel = new DefaultListModel();
		listTagsModel = new DefaultListModel();
		//analysysForm = new SocioAnalysisForm(null);
		desk = new SocioInstrumentDesk(this);
		format_io = new Internal_IO(desk);
		//format_io.setAnalForm(analysysForm);
		format_io.setMemb_list(members);
		desk.setFormat_io(format_io);
		//
		//Loading users data from file
		//loadUsersCSV();
		//saveUsersCSV();
		//
	}
	public DefaultListModel getListMembersModel() {
		return listMembersModel;
	}
	public DefaultListModel getListTagsModel() {
		return listTagsModel;
	}
	public void updateTags()
	{
		sp_tags = s_project.getSp_tags();
	}
	public int getID_GroupOwn() {
		return ID_GroupOwn;
	}
	public void setID_GroupOwn(int iDGroupOwn) {
		ID_GroupOwn = iDGroupOwn;
	}
	public ArrayList<SP_Tag> getSp_tags() {
		return sp_tags;
	}
	public void setSp_tags(ArrayList<SP_Tag> spTags) {
		sp_tags = spTags;
	}
	public int getCol_panelsists() {
		return col_panelsists;
	}
	public DefaultTableModel getModel() {
		return model;
	}
	public void setModel(DefaultTableModel model) {
		this.model = model;
		this.col_panelsists = model.getRowCount();
	}
	public DefaultTableModel getMetaModel() {
		return meta_model;
	}
	public void setMetaModel(DefaultTableModel model) {
		this.meta_model = model;
	}
	public JList getList() {
		return list;
	}
	public void setListTagsModel(DefaultListModel listTagsModel) {
		this.listTagsModel = listTagsModel;
	}
	public void setList(JList list) {
		this.list = list;
	}
	//private SocioViewDecorator decorator;
	
	public SocioProjectMain getS_project() {
		return s_project;
	}
	/*
	public void setS_project(SocioProjectMain sProject) {
		s_project = sProject;
	}
	*/
	public ArrayList<SocioPanelMember> getMembers() {
		return members;
	}
	public void setMembers(ArrayList<SocioPanelMember> members) {
		this.members = members;
	}
	public SocioAnalysisForm getAnalysysForm() {
		return analysysForm;
	}
	public void setAnalysysForm(SocioAnalysisForm analysysForm) {
		this.analysysForm = analysysForm;
	}
	public SocioInstrumentDesk getDesk() {
		return desk;
	}
	public void setDesk(SocioInstrumentDesk desk) {
		this.desk = desk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getID_in_prj() {
		return ID_in_prj;
	}
	public void setID_in_prj(int iDInPrj) {
		ID_in_prj = iDInPrj;
	}
	
	
}

