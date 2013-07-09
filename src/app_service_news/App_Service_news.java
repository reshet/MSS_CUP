package app_service_news;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import base_connectivity.MSS_CxList_Cron;
import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_NewsService;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;
import base_connectivity.Service_Invokation_Handler;
import base_gui.App_Service;
import base_gui.ToolMainWidget;

public class App_Service_news implements App_Service{
	private static String service_name = "Новини";
	private JPanel WorkPane, TopWorkspace,NewsPane,FeedsPane;
	private JLabel Header_lbl;
	@SuppressWarnings("unused")
	private NewsToolBar toolBar;
	private NewsTopBar topBar;
	@SuppressWarnings("unused")
	private MSS_RQ_CxListFiller FeedUpdater;
    private MSS_RQ_TableDescriptor FeedTDesc;
    private MSS_RQ_XMLtoTableDescriptor FeedT_XML_Desc;
    @SuppressWarnings("unused")
    private MSS_CxList_Cron cron;
    @SuppressWarnings("unused")
    private Service_Invokation_Handler this_service = new MSS_RQ_NewsService();
    @SuppressWarnings("unused")
    private JList NewsList,FeedList;
    public static CardLayout Tabber = new CardLayout();
    @SuppressWarnings("unused")
    private ArrayList<JList> newslists;
    private String URL;
	@SuppressWarnings("unused")
	private JFrame MainWindow;
	private feedToolBar feedtoolBar;
	public static int ID_category = 10;
	public static int current_feed = 1;
	public static JPanel newsL_bag = new JPanel(Tabber);
	public App_Service_news()
	{
		//Connectivity settings
		 FeedTDesc = new MSS_RQ_TableDescriptor(new String[]{"№","Image","Header","Text","Fulltext"},
				 								new Class[]{Integer.class,String.class,String.class,String.class});
	     FeedT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"title","description","fulltext","enclosure"});
	     FeedUpdater = new MSS_RQ_CxListFiller(FeedTDesc,FeedT_XML_Desc,NewsFeedElementData.class);
	     this.URL = ToolMainWidget.URL;
		//-----------
		Header_lbl = new JLabel(); 
		Header_lbl.setForeground(new Color(200,200,200));
		Header_lbl.setText("Робоча область сервісу новин");
		//GridLt = new GridLayout(2,1,5,5);
		WorkPane = new JPanel(new BorderLayout());
		WorkPane.setBackground(new Color(40,60,80));
		
		NewsPane = new JPanel(new BorderLayout());
		NewsPane.setBackground(new Color(40,60,80));
		
		FeedsPane = new JPanel(new BorderLayout());
		FeedsPane.setBackground(new Color(40,60,80));
		FeedsPane.setPreferredSize(new Dimension(300,500));
		
		TopWorkspace = new JPanel(new BorderLayout());
		TopWorkspace.setBackground(new Color(70,70,70));
		TopWorkspace.add(Header_lbl);
		topBar = new NewsTopBar();
		TopWorkspace.add(topBar);
		
		//WorkPane.add(Header_lbl);
		final DefaultListModel feedlistModel = new DefaultListModel();
		FeedList = new JList(feedlistModel);
		FeedList.setBackground(new Color(50,60,70));
		FeedList.setSelectionBackground(new Color(69,149,38));
		FeedList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		FeedList.setLayoutOrientation(JList.VERTICAL);
		FeedList.setVisibleRowCount(-1);
		FeedCellRenderer feedrenderer = new FeedCellRenderer();
		FeedList.setCellRenderer(feedrenderer);
		FeedList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//super.mouseClicked(e);
				NewsFeedElementData data = (NewsFeedElementData)FeedList.getSelectedValue();
				App_Service_news.current_feed = data.getID();
				Tabber.show(App_Service_news.newsL_bag, String.valueOf(data.getID()));
				//dlg.setLocation(e.getXOnScreen(),e.getYOnScreen());
				//dlg.setAlwaysOnTop(true);
				//dlg.setUndecorated(true);
				//dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				/*
				dlg.addWindowStateListener(new WindowStateListener() {
					
					@Override
					public void windowStateChanged(WindowEvent event) {
						// not working as it supposed to!!!
						int state = event.getNewState();
						if (state == WindowEvent.WINDOW_LOST_FOCUS)
						{
		
						}
					}
				});
				*/
			}
			
		});

		FeedList.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				NewsFeedElementData data = (NewsFeedElementData)FeedList.getSelectedValue();
				App_Service_news.current_feed = data.getID();
				Tabber.show(App_Service_news.newsL_bag, String.valueOf(data.getID()));
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		//JScrollPane scroller = new JScrollPane(NewsList);
		//scroller.setFocusable(true);
		//scroller.setPreferredSize(new Dimension(300, 500));
		
		JScrollPane feedscroller = new JScrollPane(FeedList);
		feedscroller.setFocusable(true);
		feedscroller.setPreferredSize(new Dimension(300, 500));
		feedtoolBar = new feedToolBar(URL,FeedList);
		FeedsPane.add(feedtoolBar,BorderLayout.SOUTH);
		FeedsPane.add(feedscroller);
		
		//NewsPane.add(toolBar,BorderLayout.NORTH);		
		NewsPane.add(newsL_bag);
		
		WorkPane.add(NewsPane);
		WorkPane.add(FeedsPane,BorderLayout.WEST);
		
		//String[] columnNames = new String[]{"One", "Two", "Three"};
		//TableModel model = new DefaultTableModel(data, columnNames);
		
		//MacServiceTable = MacWidgetFactory.createITunesTable(model);
		//JScrollPane scrollPane = new JScrollPane(MacServiceTable);
		//MacServiceTable.setFillsViewportHeight(true);
		//IAppWidgetFactory.makeIAppScrollPane(scrollPane);
		//TableColumnModel tcol= MacServiceTable.getColumnModel(); 
		//JTableHeader header = new JTableHeader(tcol);
		//MacServiceTable.setTableHeader(header);
		//int vColIndex = 0; 
		//TableColumn col = MacServiceTable.getColumnModel().getColumn(vColIndex); 
		//col.setCellEditor(new ListTableEditor());
		//MacServiceTable.setPreferredSize(new Dimension(500,500));
		//ServiceTable = new JTable(model);
		/*
		SourceListModel model2 = new SourceListModel();
		SourceListCategory category = new SourceListCategory("Category");
		model2.addCategory(category);
		model2.addItemToCategory(new SourceListItem("Item"), category);
		SourceList sourceList = new SourceList(model2);
		*/
		//WorkPane.add(MacServiceTable);
		
		//WorkPane.add(sourceList.getComponent());
	}

	public void initialize(JPanel work, JPanel top, JPanel MainTool)
	{
		/*
		if (cron != null) cron.stopCron();
        cron = new MSS_CxList_Cron(this_service,this.URL);
            try {
            	Object [] args1 = new Object[]{(int)5,(int)38};
				cron.addItem(FeedUpdater, FeedList, MSS_RQ_NewsService.class.getMethod("getNewsCache",int.class,int.class), args1, true);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            cron.updateItem(0);
            //cron.startCron();
		*/
		work.add(WorkPane,service_name);
		top.add(TopWorkspace,service_name);
	}
	public String getServiceWidgetName()
	{
		return service_name;
	}
	public JPanel getViewer() {
		return WorkPane;
	}
	@Override
	public void setMainFrameLink(JFrame frame) {
		MainWindow = frame;
	}
}
