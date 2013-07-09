package base_gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.explodingpixels.macwidgets.MacUtils;


public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3369783360913525345L;
	public MainFrame()
	{
		super();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		defaultAppSize = new Dimension((int)(screenSize.getWidth()/1.66), ((int)(screenSize.getHeight()/1.33)));
		this.setSize(defaultAppSize);
		this.setLocation((int)(screenSize.getWidth()/5), (int)(screenSize.getHeight()/5));
		this.setTitle("SARS: ÷”œ");
		this.setLayout(new BorderLayout(5,5));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(new Color(10,10,10));
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		try {
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			//com.sun.java.swing.plaf.windows.
			//"com.sun.java.swing.plaf.windows.WindowsLookAndFeel"
			//UIManager.installLookAndFeel(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeelClassName());
			//ch.randelshofer.quaqua.leopard.Quaqua16LeopardLookAndFeel;
			//UIManager.setLookAndFeel("ch.randelshofer.quaqua.leopard.Quaqua16LeopardLookAndFeel");
			//UIManager.setLookAndFeel(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
		MacUtils.makeWindowLeopardStyle(this.getRootPane());
		
			String path = new String(System.getProperty("user.dir")+"/src/base_gui/resources/Icon.png");
			System.out.println(path);
			//char cd = 63;
			//System.out.println(cd);
			//icon = ImageIO.read(new File(path));
			//this.setIconImage(icon);
		
		addWindowStateListener(new WindowStateListener() {
			
			@Override
			public void windowStateChanged(WindowEvent e) {
			}
		});
		//this.setUndecorated(true);
	}
	private Dimension screenSize;
	private Dimension defaultAppSize;
	private JMenuBar menuBar;
	//private Image icon;
}
