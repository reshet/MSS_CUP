package app_service_SARS_Projects;

import java.awt.Font;

import org.jdesktop.swingx.JXHyperlink;

public class ListReportLinksElement extends JXHyperlink{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String name, ID;
	private ListReportLinksElementData data;
	public ListReportLinksElement(String name, String ID, ListReportLinksElementData data) {
		this.setText(data.getName());
		this.setEnabled(true);
		this.setOpaque(true);
		this.data = data;
		this.name = data.getName();
		this.ID = ID;
		this.setFont(new Font("Helvetika",Font.PLAIN,11));
		
	}
	public ListReportLinksElementData getData()
	{
		return data;
	}
}
