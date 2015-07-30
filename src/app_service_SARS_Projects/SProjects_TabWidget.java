package app_service_SARS_Projects;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import base_SP_Management.SocioProject;
import base_SP_Management.SocioProjectItself;

public class SProjects_TabWidget extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649886208231036200L;
	private JTabbedPane tabber;
	private SocioProjectItself S_prj;
	public SProjects_TabWidget(SocioProjectItself s_prj)
	{
		S_prj = s_prj;
		tabber = new JTabbedPane();
		for(SocioProject sp: S_prj.getS_projects_all())
		{
			SocioProject_Widget wdg = new SocioProject_Widget();
			wdg.adjustSocioProject(sp);
			tabber.addTab("Проект: "+sp.getName(), wdg);
		}
		setLayout(new BorderLayout());
		this.add(tabber);
	}
	public JTabbedPane getTabber()
	{
		return tabber;
	}
}
