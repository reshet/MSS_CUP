package base_gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public interface App_Service {
	public String getServiceWidgetName();
	public void initialize(JPanel work, JPanel top,JPanel mainTool);
	public JPanel getViewer();
	public void setMainFrameLink(JFrame frame);
}
