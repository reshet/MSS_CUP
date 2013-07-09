package base_SP_Management;

import java.util.ArrayList;

public class SP_PManager {
	private String header,descr;
//	private ArrayList<SP_Tag> tags_for_AD;
	private ArrayList<SP_Tag> tags_for_prj;
	private SocioProjectMain s_project;
	private boolean approved = false;
	public boolean isApproved() {
		return approved;
	}
	public ArrayList<SP_Tag> getTags_for_prj() {
		return tags_for_prj;
	}
	public SP_PManager(SocioProjectMain s_project)
	{
		this.s_project = s_project;
		s_project.setProjectManager(this);
		tags_for_prj = new ArrayList<SP_Tag>();
	}
	public ArrayList<SocioPanel> getProjectPanels()
	{
		return s_project.getS_panels_all();
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	void addTag(SP_Tag tag)
	{
		this.tags_for_prj.add(tag);
	}
	void loadTagsToProject()
	{
		SP_Tag tags_root = s_project.getTag_for_tags();
		if (tags_root != null)
		for(SP_Tag tag:tags_for_prj)
		{
			tags_root.addInternalTag(tag);			
		}
		approved = true;
	}
	/*
	void updateTagsInProjectPanels()
	{
		s_project.updateTagsInAllPnls();
	}
	*/
}
