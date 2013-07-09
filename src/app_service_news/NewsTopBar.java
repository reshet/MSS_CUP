package app_service_news;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NewsTopBar extends JPanel{
	private JTextField newsline_id;
	private JLabel lbl;
	public NewsTopBar()
	{
		super();
		lbl = new JLabel("Новостной канал:");
		newsline_id = new JTextField("42");
		add(lbl);
		add(newsline_id);
	}
	public int getNewslineID()
	{
		return Integer.parseInt(newsline_id.getText());
	}
}
