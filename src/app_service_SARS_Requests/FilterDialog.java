package app_service_SARS_Requests;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

import base_SP_Management.RequestFilterPanel_T;
import base_SP_Management.SocioPanel;

public class FilterDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6090309595891534658L;
	private RequestFilterPanel_T panel;
	public FilterDialog(ArrayList<SocioPanel> spanels,SocioPanel sp) {
			panel = new RequestFilterPanel_T(spanels,sp);
			this.setResizable(true);
			this.setSize(new Dimension(1000,500));
			this.setAlwaysOnTop(false);
			this.setModal(false);
			this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
			this.add(panel);
			this.setVisible(true);
		}
}