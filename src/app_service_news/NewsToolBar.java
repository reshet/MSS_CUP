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
import javax.swing.JPanel;

import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_NewsService;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;

import com.explodingpixels.macwidgets.HudWidgetFactory;

public class NewsToolBar extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton SiteToServer_btn,ServerToTool_btn;
	//private JButton NewEl_btn,DelEl_btn,NewCat_btn,DelCat_btn;
	private MSS_RQ_NewsService reqHandler = new MSS_RQ_NewsService();
	private String URL;
	protected MSS_RQ_TableDescriptor NewsTDesc;
	protected MSS_RQ_XMLtoTableDescriptor NewsT_XML_Desc;
	protected MSS_RQ_CxListFiller NewsUpdater; 
	protected JList newsList;
	private JButton Add_entity_btn;
	private JButton Delete_entity_btn;
	private int id_feed; 
	public NewsToolBar(String tURL,JList list,int id_feed)
	{
		super();
		this.id_feed = id_feed;
		//this.setLayout(new GridLayout(1,4,5,5));
		this.setLayout(new FlowLayout(4));
		this.URL = tURL;
		this.newsList = list;
		setBackground(new Color(50,60,70));
		SiteToServer_btn = HudWidgetFactory.createHudButton("Оновити на сервері");
		ServerToTool_btn = HudWidgetFactory.createHudButton("Завантажити з сервера");
		Add_entity_btn = HudWidgetFactory.createHudButton("+");
		Delete_entity_btn = HudWidgetFactory.createHudButton("-");
		//ToolToServer_btn = new JButton("ToolToServer");
		add(Add_entity_btn);
		add(Delete_entity_btn);
		add(ServerToTool_btn);//add(ToolToServer_btn);
		add(SiteToServer_btn);
		ServerToTool_btn.addActionListener(new doUpdateAction());
		SiteToServer_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						//JBusyLabel busy_L= new JBusyLabel();
						JLabel busy_L= new JLabel();
						JDialog dlg =new JDialog();
						dlg.setSize(new Dimension(270,90));
						dlg.setLocation(500,500);
						dlg.add(busy_L);
						//busy_L.setBusy(true);
						busy_L.setText("Виконання оновлення на сервері...");
						dlg.setVisible(true);
					    String xml_arr_ans = MSS_RQ_Request.http_request(reqHandler.upNewsCache(10),URL);
						System.out.print(xml_arr_ans);
						dlg.setVisible(false);
				}
				}).start();
			}
		});
		
		Add_entity_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//DefaultListModel model = new DefaultListModel();
				//JList lst = new JList(model);
				NewsServiceElementCreator editor = new NewsServiceElementCreator(newsList, "Header", "Preview", "Fulltext");
				JDialog dlg = new JDialog();
				dlg.setSize(new Dimension(500,200));
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
				int selected = newsList.getSelectedIndex();
				if (selected != -1)
				{
					NewsServiceElementData d =  (NewsServiceElementData) newsList.getModel().getElementAt(selected);
					MSS_RQ_Request.http_request(reqHandler.delete_entity(d.getID()),URL);
					DefaultListModel model =  (DefaultListModel)newsList.getModel();
					model.removeElementAt(selected);
					newsList.setSelectedIndex(selected-1);
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
					NewsTDesc = new MSS_RQ_TableDescriptor(new String[]{"№","Image","Header","Text","Fulltext"},
							new Class[]{Integer.class,Integer.class,String.class,String.class,String.class});
				    NewsT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"ID","title","description","fulltext","enclosure"});
				    NewsUpdater = new MSS_RQ_CxListFiller(NewsTDesc,NewsT_XML_Desc,NewsServiceElementData.class);
				   // JBusyLabel busy_L= new JBusyLabel();
					JLabel busy_L = new JLabel();
				    JDialog dlg =new JDialog();
					dlg.setSize(new Dimension(240,90));
					dlg.setLocation(500,500);
					dlg.add(busy_L);
					//busy_L.setBusy(true);
					busy_L.setText("Виконання завантаження...");
					dlg.setVisible(true);
				    String xml_arr_ans2 = MSS_RQ_Request.http_request(reqHandler.getNewsCache(App_Service_news.ID_category,id_feed),URL);
					//System.out.println(xml_arr_ans2);
					NewsUpdater.updateData(xml_arr_ans2);
					NewsUpdater.fillCxList(newsList);
					dlg.setVisible(false);
					
				}
			}).start();
		}
	}
}
