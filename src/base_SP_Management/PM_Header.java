package base_SP_Management;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class PM_Header extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7306440190445604866L;
	private JLabel Name_lbl,Desc_lbl;
	private JTextField name_TXF;
	private JTextArea desc_TXA;
	private JPanel left, right;
	public PM_Header() {
		setPreferredSize(new Dimension(600,70));
		Name_lbl = new JLabel("Имя проекта: ");
		Desc_lbl = new JLabel("Описание проекта: ");
		name_TXF = new JTextField("Имя проекта...");
		desc_TXA = new JTextArea("Описание проекта здесь: новый проект...");
		setLayout(new BorderLayout());
		left = new JPanel();
		right = new JPanel();
		left.setLayout(new GridLayout(2,1,4,4));
		right.setLayout(new GridLayout(2,1,4,4));
		left.setPreferredSize(new Dimension(150,50));
		right.setPreferredSize(new Dimension(150,50));
		left.add(Name_lbl);
		left.add(Desc_lbl);
		right.add(name_TXF);
		right.add(desc_TXA);
		add(left,BorderLayout.WEST);
		add(right,BorderLayout.CENTER);
	}
	@Override
	public String getName()
	{
		return name_TXF.getText();
	}
	public String getDesc()
	{
		return desc_TXA.getText();
	}
	
}
