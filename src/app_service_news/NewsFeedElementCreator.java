package app_service_news;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.text.JTextComponent;

import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_NewsService;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;

public class NewsFeedElementCreator extends JPanel implements ListCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JEditorPane NameEdit;
	private JButton Confirm;
	private MSS_RQ_NewsService reqHandler = new MSS_RQ_NewsService();
	private String URL = "http://localhost/services/SmartService/MainGate.php";
	//public static String URL = "http://194.44.143.27:82/MSS/MainGate.php";
	public JEditorPane getNameEdit() {
		return NameEdit;
	}
	private JList NewsListEditing;
	public NewsFeedElementCreator (JList newsLE,String name)
	{
		NewsListEditing = newsLE;
		setPreferredSize(new Dimension(200,40));
		setLayout(new BorderLayout());
		NameEdit = new JEditorPane(JTextComponent.DEFAULT_KEYMAP,name);
		NameEdit.setFont(new Font("Helvetica",Font.BOLD,15));
		NameEdit.setForeground(Color.WHITE);
		Confirm = new JButton("Створити!");
		Confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					private MSS_RQ_TableDescriptor NewsTDesc;
					private MSS_RQ_XMLtoTableDescriptor NewsT_XML_Desc;
					private MSS_RQ_CxListFiller NewsUpdater;

					@Override
					public void run() {
						//NewsServiceElementData d = (NewsServiceElementData)model.getElementAt(selected_index);
						NewsFeedElementData d = new NewsFeedElementData();
						d.setName(getNameEdit().getText());
						
						
						 JLabel busy_L= new JLabel();
							JDialog dlg =new JDialog();
							dlg.setSize(new Dimension(200,90));
							dlg.setLocation(500,500);
							dlg.add(busy_L);
							busy_L.setText("Застосування змін...");
							dlg.setVisible(true);
							
						String xml_arr_ans2 = MSS_RQ_Request.http_request
							(reqHandler.add_feed(App_Service_news.ID_category,getNameEdit().getText()),URL);
						//Here add ID to newsfeed object!
						NewsTDesc = new MSS_RQ_TableDescriptor(new String[]{"№"},
								new Class[]{Integer.class});
					    NewsT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"ID"});
					    NewsUpdater = new MSS_RQ_CxListFiller(NewsTDesc,NewsT_XML_Desc,NewsServiceElementData.class);
					   
						String newID = NewsUpdater.requestLocalData_FromRoot(xml_arr_ans2, 1);
						d.setID(Integer.parseInt(newID));
						DefaultListModel model = (DefaultListModel) NewsListEditing.getModel();
						model.add(model.getSize(), d);
						dlg.setVisible(false);
						dlg.dispose();
						//System.out.println(xml_arr_ans2);
						//setVisible(false);
					}
				}).start();
			}
		});
		NameEdit.setBackground(new Color(50,60,70));
		//feedID = new JTextField("42");
		add(NameEdit,BorderLayout.CENTER);
		add(Confirm, BorderLayout.EAST);
	}
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		return this;
	}
}

