package app_service_SARS_Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class SP_ListElement_Widget extends JPanel implements ListCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private ImageComponent icon;
	private JLabel Name, Descr, Size;
	private JPanel TextPanel;
	private double IconWidth = 0.2, HeaderHeight = 0.25,DescrHeight = 0.3,SizeHeight = 0.45;
	@SuppressWarnings("unused")
	private int LeftWidth; 
	public SP_ListElement_Widget (String name,int IDgroup,int size)
	{
		setPreferredSize(new Dimension(200,100));
		setLayout(new BorderLayout());
		TextPanel = new JPanel(new GridLayout(3,1));
		TextPanel.setBackground(new Color(70,80,90));
		Border border = BorderFactory.createEmptyBorder(3, 3, 3, 3);
		setBorder(border);
		TextPanel.setPreferredSize(new Dimension(150,70));
		LeftWidth = (int)(getPreferredSize().getWidth()*IconWidth);
		Name = new JLabel(name); Name.setPreferredSize(new Dimension((int)TextPanel.getPreferredSize().getWidth(),(int)(TextPanel.getPreferredSize().getHeight()*HeaderHeight)));
		Name.setFont(new Font("Helvetica",Font.BOLD,13));
		Name.setForeground(new Color(255,255,255));
		Name.setBackground(new Color(70,80,90));
		Descr = new JLabel(String.valueOf(IDgroup));Descr.setPreferredSize(new Dimension((int)TextPanel.getPreferredSize().getWidth(),(int)(TextPanel.getPreferredSize().getHeight()*DescrHeight)));
		Descr.setFont(new Font("Helvetica",Font.PLAIN,12));
		Descr.setForeground(new Color(180,180,195));
		Descr.setBackground(new Color(200,200,50));
		Descr.setHorizontalAlignment(SwingConstants.LEFT);
		//Descr.setLineWrap(true);
		Size = new JLabel(String.valueOf(size));Size.setPreferredSize(new Dimension((int)TextPanel.getPreferredSize().getWidth(),(int)(TextPanel.getPreferredSize().getHeight()*SizeHeight)));
		Size.setFont(new Font("Helvetica",Font.PLAIN,12));
		Size.setForeground(new Color(20,20,45));
		Size.setBackground(new Color(200,200,50));
		Size.setHorizontalAlignment(SwingConstants.LEFT);
		//Size.setLineWrap(true);
		
		Name.setBackground(new Color(50,90,50));
		Name.setOpaque(true);
		Descr.setBackground(new Color(50,90,50));
		Descr.setOpaque(true);
		Size.setBackground(new Color(50,90,50));
		Size.setOpaque(true);
		//JScrollPane pane1 = new JScrollPane(Header);
		//JScrollPane pane2 = new JScrollPane(Descr);
		//JScrollPane pane3 = new JScrollPane(Size);
		
		TextPanel.add(Name);TextPanel.add(Descr);TextPanel.add(Size);
		//TextPanel.add(Header,BorderLayout.NORTH);TextPanel.add(Descr,BorderLayout.CENTER);TextPanel.add(Size,BorderLayout.SOUTH);
		//TextPanel.add(pane1,BorderLayout.NORTH);TextPanel.add(pane2,BorderLayout.WEST);TextPanel.add(pane3,BorderLayout.SOUTH);
		
		//add(icon,BorderLayout.WEST);
		add(TextPanel,BorderLayout.CENTER);
		
		//Editor mode on clicking dblclick
		
	}
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		return this;
	}
}


