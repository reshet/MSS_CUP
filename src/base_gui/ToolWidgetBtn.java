package base_gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolWidgetBtn extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4842839123970814638L;
	private final String desc;
	private Dimension size;
	private final CardLayout Tabber,TopTabber;
	private final JPanel ServiceWorkspace, TopServiceWorkspace;
	public ToolWidgetBtn(String service,CardLayout Tabber,CardLayout TopTabber,JPanel ServiceWorkspace, JPanel TopServiceWorkspace)
	{
		super();
		desc = service;
		this.Tabber = Tabber;
		this.TopTabber = TopTabber;
		this.ServiceWorkspace = ServiceWorkspace;
		this.TopServiceWorkspace = TopServiceWorkspace;
		this.size = new Dimension(200,(int)(200/2.66));
		this.setFont(new Font("Helvetika",Font.BOLD,16));
		//this.setForeground(new Color(240,240,240));
		//this.setBackground(new Color(120,120,120));
		this.setBorderPainted(false);
		this.setText(desc);
		this.setPreferredSize(size);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ToolWidgetBtn.this.setBackground(new Color(150,150,150));
				ToolWidgetBtn.this.Tabber.show(ToolWidgetBtn.this.ServiceWorkspace, desc);
				ToolWidgetBtn.this.TopTabber.show(ToolWidgetBtn.this.TopServiceWorkspace, desc);
				ToolWidgetBtn.this.setBackground(new Color(120,120,120));
			}
		});
	}
	
}
