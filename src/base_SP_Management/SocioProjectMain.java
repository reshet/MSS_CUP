package base_SP_Management;

import java.util.ArrayList;


public class SocioProjectMain {
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
	private ArrayList<SocioPanel> s_panels_all;
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
	
	public SocioProjectMain(int sID)
	{
		this.projectsGroupID = sID;
		this.sp_tags = new ArrayList<SP_Tag>();
		this.s_panels_all = new ArrayList<SocioPanel>();
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
	public ArrayList<SocioPanel> getS_panels_all() {
		return s_panels_all;
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
	
	public void addSocioPanel(SocioPanel pnl)
	{
		this.s_panels_all.add(pnl);
		pnl.setID_in_prj(s_panels_all.size()-1);
		//pnl.setS_project(this);
	}
	
}
