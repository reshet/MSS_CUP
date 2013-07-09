package app_service_news;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

public class NewsList extends JList{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2318526667809351407L;
	private final DefaultListModel listModel = new DefaultListModel();
	private int id_feed;
	public int getIDfeed()
	{
		return id_feed;
	}
	public NewsList(int id_feed)
	{
		super();
		this.id_feed = id_feed;
		this.setModel(listModel);
		this.setBackground(new Color(50,60,70));
		this.setSelectionBackground(new Color(69,149,38));
		this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.setLayoutOrientation(JList.VERTICAL);
		this.setVisibleRowCount(-1);
		ComplexCellRenderer renderer = new ComplexCellRenderer();
		this.setCellRenderer(renderer);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int count = e.getClickCount();
				if (count != 2) return;
				JDialog dlg = new JDialog();
				dlg.setSize(new Dimension(500,180));
				JPanel pnl = new JPanel(new BorderLayout());
				NewsServiceElementData data = (NewsServiceElementData)NewsList.this.getSelectedValue();
				final NewsServiceElementEditor editor = new NewsServiceElementEditor(NewsList.this,data.getImage(), data.getHeader(), data.getPreview(), data.getFulltext());
				pnl.add(editor,BorderLayout.CENTER);
				pnl.setBackground(new Color(89,89,83));
				dlg.setLocation(e.getXOnScreen(),e.getYOnScreen());
				dlg.setTitle("News Element Editing window");
				dlg.setAlwaysOnTop(true);
				dlg.add(pnl);
				//dlg.setUndecorated(true);
				dlg.setVisible(true);
				dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dlg.addWindowStateListener(new WindowStateListener() {
					
					@Override
					public void windowStateChanged(WindowEvent event) {
						// not working as it supposed to!!!
						int state = event.getNewState();
						System.out.print("State changed!!!!");
						if (state == WindowEvent.WINDOW_LOST_FOCUS)
						{
							final int index = NewsList.this.getSelectedIndex();
							NewsServiceElementData d = (NewsServiceElementData)listModel.getElementAt(index);
							d.setHeader(editor.getHeader().getText());
							d.setPreview(editor.getPreview().getText());
							d.setFulltext(editor.getContence().getText());
							listModel.setElementAt(d, index);
							NewsList.this.setModel(listModel);
						}
					}
				});
			}
			
		});
		
	}
}
