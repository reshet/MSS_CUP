package base_gui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;


public class RootBackgroundFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -386995027392643044L;

	@Override
	public void paint(Graphics g) {
		// currently BULLSHIT(((((
		Graphics2D g2d = (Graphics2D)g;
		AlphaComposite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.5f);
		g2d.setComposite(newComposite);
		super.paint(g2d);
	};
}
