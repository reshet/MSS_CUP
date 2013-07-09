package app_service_SARS_Panels;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JProgressBar;

import com.explodingpixels.macwidgets.HudWindow;

public class WaitDialog extends HudWindow{
	private JProgressBar bar;
 public WaitDialog(String text)
 {
	 	//JLabel busy_L= new JLabel();
		getJDialog().setSize(new Dimension(300,40));
		//getJDialog().add(busy_L);
		getJDialog().setTitle(text);
		bar = new JProgressBar();
		bar.setValue(100);
		getJDialog().add(bar);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		getJDialog().setLocation((int)screenSize.getWidth()/2 - (int)getJDialog().getSize().getWidth()/2,
				(int)screenSize.getHeight()/2 - (int)getJDialog().getSize().getHeight()/2);
		getJDialog().setAlwaysOnTop(true);
		getJDialog().setVisible(true);
		//getJDialog().setModal(true);
		/*
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (i < 20)
				{
					WaitDialog.this.bar.setValue(6*i);
					try {
						Thread.sleep(70);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i++;
				}
			}
		});
		*/
 }
 public void setProgress(int val)
 {
	 bar.setValue(val);
 }
 public int getProgress()
 {
	 return bar.getValue();
 }
}
