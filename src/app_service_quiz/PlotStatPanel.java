package app_service_quiz;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTable;

public class PlotStatPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private CardLayout switcher;
	private String[] switcher_names;
	@SuppressWarnings("unused")
	private JPanel[] plot_panels;
	@SuppressWarnings("unused")
	private int sw_load = 0;
	private int sw_capacity;
	private JTable linked_table;
	public JTable getLinked_table() {
		return linked_table;
	}
	public void setLinked_table(JTable linkedTable) {
		linked_table = linkedTable;
	}
	public PlotStatPanel(int capacity)
	{
		sw_capacity = capacity;
		switcher = new CardLayout();
		switcher_names = new String[sw_capacity];
		plot_panels = new JPanel[sw_capacity];
		for(int i = 0; i < sw_capacity;i++)
		{
			switcher_names[i] = new String("Plot"+i);
			sw_load++;
		}
		setLayout(new BorderLayout());
		setBackground(new Color(80,80,80));
	}
}
