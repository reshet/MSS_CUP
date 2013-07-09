package app_service_news;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class NewsServiceElement extends JPanel implements ListCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageComponent icon;
	private JLabel Header, Preview, Contence;
	private JPanel TextPanel;
	private double IconWidth = 0.2, HeaderHeight = 0.25,PreviewHeight = 0.3,ContenceHeight = 0.45;
	@SuppressWarnings("unused")
	private int LeftWidth; 
	public NewsServiceElement (Image ic,String header,String preview,String fulltext)
	{
		setPreferredSize(new Dimension(200,100));
		setLayout(new BorderLayout());
		icon = new ImageComponent(ic);
		TextPanel = new JPanel(new GridLayout(3,1));
		TextPanel.setBackground(new Color(70,80,90));
		Border border = BorderFactory.createEmptyBorder(3, 3, 3, 3);
		setBorder(border);
		TextPanel.setPreferredSize(new Dimension(150,70));
		LeftWidth = (int)(getPreferredSize().getWidth()*IconWidth);
		Header = new JLabel(header); Header.setPreferredSize(new Dimension((int)TextPanel.getPreferredSize().getWidth(),(int)(TextPanel.getPreferredSize().getHeight()*HeaderHeight)));
		Header.setFont(new Font("Helvetica",Font.BOLD,13));
		Header.setForeground(new Color(255,255,255));
		Header.setBackground(new Color(70,80,90));
		Preview = new JLabel(preview);Preview.setPreferredSize(new Dimension((int)TextPanel.getPreferredSize().getWidth(),(int)(TextPanel.getPreferredSize().getHeight()*PreviewHeight)));
		Preview.setFont(new Font("Helvetica",Font.PLAIN,12));
		Preview.setForeground(new Color(180,180,195));
		Preview.setBackground(new Color(70,80,90));
		Preview.setHorizontalAlignment(SwingConstants.LEFT);
		//Preview.setLineWrap(true);
		Contence = new JLabel(fulltext);Contence.setPreferredSize(new Dimension((int)TextPanel.getPreferredSize().getWidth(),(int)(TextPanel.getPreferredSize().getHeight()*ContenceHeight)));
		Contence.setFont(new Font("Helvetica",Font.PLAIN,12));
		Contence.setForeground(new Color(20,20,45));
		Contence.setBackground(new Color(70,80,90));
		Contence.setHorizontalAlignment(SwingConstants.LEFT);
		//Contence.setLineWrap(true);
		
		//Header.setBackground(Color.pink);
		Header.setOpaque(true);
		//Preview.setBackground(Color.pink);
		Preview.setOpaque(true);
		//Contence.setBackground(Color.pink);
		Contence.setOpaque(true);
		//JScrollPane pane1 = new JScrollPane(Header);
		//JScrollPane pane2 = new JScrollPane(Preview);
		//JScrollPane pane3 = new JScrollPane(Contence);
		
		TextPanel.add(Header);TextPanel.add(Preview);TextPanel.add(Contence);
		//TextPanel.add(Header,BorderLayout.NORTH);TextPanel.add(Preview,BorderLayout.CENTER);TextPanel.add(Contence,BorderLayout.SOUTH);
		//TextPanel.add(pane1,BorderLayout.NORTH);TextPanel.add(pane2,BorderLayout.WEST);TextPanel.add(pane3,BorderLayout.SOUTH);
		
		add(icon,BorderLayout.WEST);
		add(TextPanel,BorderLayout.CENTER);
		
		//Editor mode on clicking dblclick
		
	}
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		return this;
	}
}
class ImageComponent extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	public ImageComponent(Image im)
	{
		setPreferredSize(new Dimension(70,50));
		image = im;
		if (image == null)
		try
		{
			String path = System.getProperty("user.dir");
			path += "\\src\\app_service_news\\DefaultNewsImage.png";
			image = ImageIO.read(new File(path));	
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void paintComponent(Graphics g)
	{
		if (image ==null) return;
		g.drawImage(image, 0, 0,70,70,null);
	}
}

