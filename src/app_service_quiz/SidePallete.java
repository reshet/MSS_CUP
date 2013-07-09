package app_service_quiz;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.explodingpixels.macwidgets.HudWindow;

public class SidePallete extends HudWindow{
	public SidePallete(){}//only to enable use of arrays ! danger!
	public SidePallete(JComponent MainW,JFrame owner){
		//super("",owner);
		//this.getJDialog().setLayout(new BorderLayout(5,5));
		//this.getJDialog().setLayout(new BorderLayout());
		this.getJDialog().setResizable(true);
		this.getJDialog().add(MainW);
		MainW.setOpaque(false);
		this.getJDialog().setSize(new Dimension(170,320));
		//this.setLocation((int)(MainW.getLocation().getX()+MainW.getWidth()), (int)(MainW.getLocation().getY()));
		//this.setLocation(MainW.getWidth(), MainW.getHeight());
		this.getJDialog().setAlwaysOnTop(false);
		this.getJDialog().setModal(false);
		this.getJDialog().setLocationRelativeTo(MainW);
		this.getJDialog().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	}

}
