package app_service_quiz;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class SidePalleteSparta extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5224643977122250466L;

	public SidePalleteSparta(JComponent MainW,JFrame owner){
		//super("",owner);
		//this.getJDialog().setLayout(new BorderLayout(5,5));
		//this.getJDialog().setLayout(new BorderLayout());
		this.setResizable(true);
		this.add(MainW);
		MainW.setOpaque(false);
		this.setSize(new Dimension(600,320));
		//this.setLocation((int)(MainW.getLocation().getX()+MainW.getWidth()), (int)(MainW.getLocation().getY()));
		//this.setLocation(MainW.getWidth(), MainW.getHeight());
		this.setAlwaysOnTop(false);
		this.setModal(false);
		this.setLocationRelativeTo(MainW);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	}

}
