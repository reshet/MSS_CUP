package app_service_news;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_NewsService;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;

import com.explodingpixels.macwidgets.HudWidgetFactory;

public class feedToolBar extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton ServerToTool_btn;
	//private JButton NewEl_btn,DelEl_btn,NewCat_btn,DelCat_btn;
	private MSS_RQ_NewsService reqHandler = new MSS_RQ_NewsService();
	private String URL;
	protected MSS_RQ_TableDescriptor FeedTDesc;
	protected MSS_RQ_XMLtoTableDescriptor FeedT_XML_Desc;
	protected MSS_RQ_CxListFiller FeedUpdater; 
	protected JList feedList;
	private JButton Add_entity_btn;
	private JButton Delete_entity_btn;
	public feedToolBar(String tURL,JList list)
	{
		super();
		//this.setLayout(new GridLayout(1,3,5,5));
		this.setLayout(new FlowLayout(4));
		//this.setAlignmentX();
		this.URL = tURL;
		this.feedList = list;
		setBackground(new Color(50,60,70));
		ServerToTool_btn = HudWidgetFactory.createHudButton("Оновити");
		Add_entity_btn = HudWidgetFactory.createHudButton("+");
		Delete_entity_btn = HudWidgetFactory.createHudButton("-");
		add(Add_entity_btn);
		add(Delete_entity_btn);
		add(ServerToTool_btn);//add(ToolToServer_btn);
		
		ServerToTool_btn.addActionListener(new doUpdateAction());
		Add_entity_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//DefaultListModel model = new DefaultListModel();
				//JList lst = new JList(model);
				NewsFeedElementCreator editor = new NewsFeedElementCreator(feedList,"Нова стрічка новин");
				JDialog dlg = new JDialog();
				dlg.setSize(new Dimension(400,100));
				dlg.setTitle("Створення нової стрічки новин");
				dlg.setAlwaysOnTop(true);
				//dlg.setUndecorated(true);
				dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				dlg.setLocation((int)screenSize.getWidth()/2 - (int)dlg.getSize().getWidth()/2,
						(int)screenSize.getHeight()/2 - (int)dlg.getSize().getHeight()/2);
				dlg.setLayout(new BorderLayout());
				dlg.add(editor);
				dlg.setVisible(true);
			}
		});
		Delete_entity_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selected = feedList.getSelectedIndex();
				if (selected != -1)
				{
					if (JOptionPane.showConfirmDialog(getParent(), "Ви дісно бажаєте видалити стрічку новин?", "Підтвердження операції", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE) == 0)
					{
						NewsFeedElementData d =  (NewsFeedElementData) feedList.getModel().getElementAt(selected);
						MSS_RQ_Request.http_request(reqHandler.delete_feed(d.getID()),URL);
						DefaultListModel model =  (DefaultListModel)feedList.getModel();
						model.removeElementAt(selected);
						feedList.setSelectedIndex(selected-1);	
					}
					}
			}
		});
		new doUpdateAction().actionPerformed(null);
	}
	@SuppressWarnings("serial")
	private class doUpdateAction extends AbstractAction
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					FeedTDesc = new MSS_RQ_TableDescriptor(new String[]{"№","name","ID"},
							new Class[]{Integer.class,String.class});
				    FeedT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"ID","name"});
				    FeedUpdater = new MSS_RQ_CxListFiller(FeedTDesc,FeedT_XML_Desc,NewsFeedElementData.class);
				   // JBusyLabel busy_L= new JBusyLabel();
					JLabel busy_L = new JLabel();
				    JDialog dlg =new JDialog();
					dlg.setSize(new Dimension(240,90));
					dlg.setLocation(500,500);
					dlg.add(busy_L);
					//busy_L.setBusy(true);
					busy_L.setText("Виконання завантаження...");
					dlg.setVisible(true);
				    String xml_arr_ans2 = MSS_RQ_Request.http_request(reqHandler.listFeeds(App_Service_news.ID_category),URL);
		
				    //System.out.println(xml_arr_ans2);
					FeedUpdater.updateData(xml_arr_ans2);
					FeedUpdater.fillCxList(feedList);
					for (int i = 0;i< feedList.getModel().getSize();i++)
					{
						NewsFeedElementData data = (NewsFeedElementData) feedList.getModel().getElementAt(i);
						JPanel contain = new JPanel(new BorderLayout());
						NewsList list = new NewsList(data.getID());
						JScrollPane scroller = new JScrollPane(list);
						scroller.setFocusable(true);
						scroller.setPreferredSize(new Dimension(300, 500));
						contain.add(scroller);
						contain.add(new NewsToolBar(URL,list,data.getID()),BorderLayout.SOUTH);
						App_Service_news.newsL_bag.add(contain,String.valueOf(data.getID()));
					}
					dlg.setVisible(false);
				}
			}).start();

		}
		
	}
}
