package app_service_news;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

public class NewsFeedElement extends JPanel implements ListCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private ImageComponent icon;
	private JLabel Name;
	public NewsFeedElement (String name)
	{
		setPreferredSize(new Dimension(200,100));
		setLayout(new BorderLayout());
		Border border = BorderFactory.createEmptyBorder(3, 3, 3, 3);
		setBorder(border);
		Name = new JLabel(name);
		Name.setFont(new Font("Helvetica",Font.BOLD,15));
		Name.setForeground(new Color(255,255,255));
		Name.setBackground(new Color(70,80,90));
		Name.setOpaque(true);
		add(Name,BorderLayout.CENTER);
	}
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		return this;
	}
}

