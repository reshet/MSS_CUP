package app_service_SARS_Panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import base_SP_Management.SocioPanel;
import base_SP_Management.SocioPanelWidget;
import base_SP_Management.SocioProjectMain;

public class SPanels_TabWidget extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649886208231036200L;
	private JTabbedPane tabber;
	private SocioProjectMain S_prj;
	public SPanels_TabWidget(SocioProjectMain s_prj)
	{
		S_prj = s_prj;
		tabber = new JTabbedPane();
		for(SocioPanel sp: S_prj.getS_panels_all())
		{
			SocioPanelWidget wdg = new SocioPanelWidget();
			wdg.adjustSocioPanel(sp);
			tabber.addTab("Панель: "+sp.getName(), wdg);
		}
		setLayout(new BorderLayout());
		this.add(tabber);
	}
	public JTabbedPane getTabber()
	{
		return tabber;
	}
}
