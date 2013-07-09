package base_SP_Management;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import app_service_SARS_Panels.Setting_AssocPair;


public class SocioProjectItself {
	//should be above that hierarchy
	//private int rootGroup = 100000;
	//private int ProjectsGroup = 100000;
	private int projectRootGroupID = 100000;
	@SuppressWarnings("unused")
	private int projectsGroupID = 100005;
	private int tagsGroupID = 100010;
	public int getTagsGroupID() {
		return tagsGroupID;
	}

	public int getRepositoryGroupID() {
		return repositoryGroupID;
	}
	@SuppressWarnings("unused")
	private int panelsGroupID = 100020;
	private int repositoryGroupID = 100030;
	
	/**
	 * SocioPanel containes users of terminal group
	 */
	private ArrayList<SocioProject> s_projects_all;
	private Map<Integer,Setting_AssocPair<Integer, String>> ankets_content;
	private Map<Integer,Setting_AssocPair<DefaultTableModel, DefaultTableModel>> ankets_meta_cases;
	
	/**
	 * SP_Tags containes hierarchical tags, which is generally non-terminal groups,
	 * but can be used to generate a terminal group over tags criteria.
	 */
	private ArrayList<SP_Tag> sp_tags;
	private SP_Tag tag_for_tags;
	public SP_Tag getTag_for_tags() {
		if (tag_for_tags == null)
		{
			for(SP_Tag tag:sp_tags)
			{
				if (tag.getID() == getTagsGroupID())
				{
					tag_for_tags = tag;
					break;
				}
			}
		}
		return tag_for_tags;
	}
	//private ListTasksPanel tasksPanel;
	private SP_PManager projectManager;
	
	public SocioProjectItself(int sID)
	{
		this.projectsGroupID = sID;
		this.sp_tags = new ArrayList<SP_Tag>();
		this.s_projects_all = new ArrayList<SocioProject>();
	}
	
	public int getProjectRootGroupID() {
		return projectRootGroupID;
	}

	public SP_PManager getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(SP_PManager projectManager) {
		this.projectManager = projectManager;
	}
	public void addSP_Tag(SP_Tag spTag) {
		sp_tags.add(spTag);
	}
	public ArrayList<SP_Tag> getSp_tags() {
		return sp_tags;
	}
	public ArrayList<SocioProject> getS_projects_all() {
		return s_projects_all;
	}
	/*
	public void updateTagsInAllPnls()
	{
		for(SocioPanel pnl:s_panels_all)
		{
			pnl.setSp_tags(sp_tags);
		}
	}
	*/
	
	public void addSocioProject(SocioProject pnl)
	{
		this.s_projects_all.add(pnl);
		pnl.setID_in_prj(s_projects_all.size()-1);
		//pnl.setS_project(this);
	}

	public void setAnkets_content(Map<Integer,Setting_AssocPair<Integer, String>> ankets_content) {
		this.ankets_content = ankets_content;
	}

	public Map<Integer,Setting_AssocPair<Integer, String>> getAnkets_content() {
		return ankets_content;
	}

	public void setAnkets_meta_cases(Map<Integer,Setting_AssocPair<DefaultTableModel, DefaultTableModel>> ankets_meta_cases) {
		this.ankets_meta_cases = ankets_meta_cases;
	}

	public Map<Integer,Setting_AssocPair<DefaultTableModel, DefaultTableModel>> getAnkets_meta_cases() {
		return ankets_meta_cases;
	}
	
}
