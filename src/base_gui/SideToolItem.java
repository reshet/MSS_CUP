package base_gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SideToolItem extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5275928916787501256L;
	private JPanel WorkPane;
	public JPanel getWorkPane(){return WorkPane;}
	public SideToolItem(JFrame owner) {
		super(owner,"Noname dialog",true);
		setSize(300,300);
		WorkPane = new JPanel();
		WorkPane.setSize(this.getWidth(), this.getHeight());
		this.add(WorkPane);
	}
}
