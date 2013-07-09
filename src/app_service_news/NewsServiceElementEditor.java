package app_service_news;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;

import base_connectivity.MSS_RQ_NewsService;
import base_connectivity.MSS_RQ_Request;

import com.explodingpixels.macwidgets.HudWindow;
import com.lowagie.text.pdf.codec.Base64;

public class NewsServiceElementEditor extends JPanel implements ListCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageComponentEditing icon;
	private Image img;
	private JEditorPane Header, Preview, Contence;
	private JButton Confirm;
	private MSS_RQ_NewsService reqHandler = new MSS_RQ_NewsService();
	private String URL = "http://localhost/services/SmartService/MainGate.php";
	//public static String URL = "http://194.44.143.27:82/MSS/MainGate.php";
	private int selected_index;
	public ImageComponentEditing getIcon() {
		return icon;
	}
	public void setIcon(ImageComponentEditing icon) {
		this.icon = icon;
	}
	public JEditorPane getHeader() {
		return Header;
	}
	public void setHeader(JEditorPane header) {
		Header = header;
	}
	public JEditorPane getPreview() {
		return Preview;
	}
	public void setPreview(JEditorPane preview) {
		Preview = preview;
	}
	public JEditorPane getContence() {
		return Contence;
	}
	public void setContence(JEditorPane contence) {
		Contence = contence;
	}
	private JPanel TextPanel;
	private double IconWidth = 0.2, HeaderHeight = 0.25,PreviewHeight = 0.3,ContenceHeight = 0.45;
	@SuppressWarnings("unused")
	private int LeftWidth; 
	private JList NewsListEditing;
	public NewsServiceElementEditor (NewsList newsLE,Image ic,String header,String preview,String fulltext)
	{
		NewsListEditing = newsLE;
		setPreferredSize(new Dimension(200,100));
		setLayout(new BorderLayout());
		img = ic;
		icon = new ImageComponentEditing(img);
		
		icon.setPreferredSize(new Dimension(128,128));
		setBackground(new Color(50,60,70));
		//icon.setOpaque(true);
		TextPanel = new JPanel(new GridLayout(3,1));
		TextPanel.setBackground(new Color(50,60,70));
		TextPanel.setPreferredSize(new Dimension(150,70));
		LeftWidth = (int)(getPreferredSize().getWidth()*IconWidth);
		Header = new JEditorPane(JTextComponent.DEFAULT_KEYMAP,header); Header.setPreferredSize(new Dimension((int)TextPanel.getPreferredSize().getWidth(),(int)(TextPanel.getPreferredSize().getHeight()*HeaderHeight)));
		Header.setFont(new Font("Helvetica",Font.BOLD,13));
		Header.setForeground(Color.WHITE);
		Preview = new JEditorPane(JTextComponent.DEFAULT_KEYMAP,preview);Preview.setPreferredSize(new Dimension((int)TextPanel.getPreferredSize().getWidth(),(int)(TextPanel.getPreferredSize().getHeight()*PreviewHeight)));
		Preview.setFont(new Font("Helvetica",Font.PLAIN,12));
		Preview.setForeground(new Color(205,205,225));
		Preview.setBackground(new Color(200,200,50));
		Preview.setAlignmentX(Component.LEFT_ALIGNMENT);
		//Preview.setLineWrap(true);
		Contence = new JEditorPane(JTextComponent.DEFAULT_KEYMAP,fulltext);Contence.setPreferredSize(new Dimension((int)TextPanel.getPreferredSize().getWidth(),(int)(TextPanel.getPreferredSize().getHeight()*ContenceHeight)));
		Contence.setFont(new Font("Helvetica",Font.PLAIN,12));
		Contence.setForeground(new Color(180,180,195));
		Contence.setBackground(new Color(200,200,50));
		Contence.setAlignmentX(Component.LEFT_ALIGNMENT);
		//Contence.setLineWrap(true);
		Confirm = new JButton("Змінити");
		selected_index = NewsListEditing.getSelectedIndex();
		Confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						DefaultListModel model = (DefaultListModel) NewsListEditing.getModel();
						//NewsServiceElementData d = (NewsServiceElementData)model.getElementAt(selected_index);
						NewsServiceElementData d = new NewsServiceElementData();
						d.setHeader(getHeader().getText());
						d.setPreview(getPreview().getText());
						d.setFulltext(getContence().getText());
						if (selected_index != -1)
							model.setElementAt(d, selected_index);
						NewsListEditing.setModel(model);
						
						
						Image imgs = NewsServiceElementEditor.this.img;
						String img_coded = null;
						if (imgs != null)
						{
							ByteArrayOutputStream outer = new ByteArrayOutputStream();
							try {
								
								ImageIO.write((RenderedImage) imgs, "png", outer);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ByteArrayInputStream inner = new ByteArrayInputStream(outer.toByteArray());
							byte [] arr = new byte[20000]; 
							int read_done = 0;
							try {
								read_done = inner.read(arr);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							img_coded = Base64.encodeBytes(arr,0,read_done);
							d.setImage(imgs);
						}	
						/*
					//	File f = new File("summer.png64","w");
						try {
							FileWriter wr = new FileWriter("summer.png64");
							wr.write(img_coded);
							wr.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
						 //JBusyLabel busy_L= new JBusyLabel();
							JDialog dlg =new JDialog();
							dlg.setSize(new Dimension(240,90));
							dlg.setLocation(500,500);
							//dlg.add(busy_L);
							//busy_L.setBusy(true);
							//busy_L.setText("Застосування змін...");
							dlg.setVisible(true);
						System.out.println(img_coded);
						String xml_arr_ans2 = MSS_RQ_Request.http_request
							(reqHandler.update_entity
									(d.getID(), 
									getHeader().getText(), 
									getPreview().getText(), 
									getContence().getText(), 
									img_coded),URL);
						dlg.setVisible(false);
						System.out.println(xml_arr_ans2);
						//setVisible(false);
					}
				}).start();
			}
		});
		icon.addMouseListener(new MouseAdapter() {
			private JFileChooser chooser;

			@Override
			public void mouseClicked(MouseEvent arg0) {
				chooser = new JFileChooser();
				chooser.setFileFilter(new FileFilter() {
					@Override
					public String getDescription() {
						return "зображення";
					}
					
					@Override
					public boolean accept(File f) {
						String extension = Utils.getExtension(f);
					    if (extension != null) {
						   return true;
						} else {
						    return false;
						}
					}
					
				});
				HudWindow dlg = new HudWindow("Оберіть файл зображення...");
				dlg.getJDialog().setPreferredSize(new Dimension(300,400));
				dlg.getJDialog().setBackground(new Color(80,80,80));
				int ret_val = chooser.showOpenDialog(dlg.getJDialog());
				//String fname = null;
		        if (ret_val == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					try {
						img = ImageIO.read(file);
						//img = img.getScaledInstance(128, 128, 0);
						icon.setImage(img);
						icon.repaint();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }	
			}
		});

		Header.setBackground(new Color(50,60,70));
		Header.setOpaque(true);
		Preview.setBackground(new Color(50,60,70));
		Preview.setOpaque(true);
		Contence.setBackground(new Color(50,60,70));
		Contence.setOpaque(true);
		//JScrollPane pane1 = new JScrollPane(Header);
		//JScrollPane pane2 = new JScrollPane(Preview);
		//JScrollPane pane3 = new JScrollPane(Contence);
		
		TextPanel.add(Header);TextPanel.add(Preview);TextPanel.add(Contence);
		//TextPanel.add(pane1);TextPanel.add(pane2);TextPanel.add(pane3);
		//TextPanel.add(Header,BorderLayout.NORTH);TextPanel.add(Preview,BorderLayout.CENTER);TextPanel.add(Contence,BorderLayout.SOUTH);
		//TextPanel.add(pane1,BorderLayout.NORTH);TextPanel.add(pane2,BorderLayout.WEST);TextPanel.add(pane3,BorderLayout.SOUTH);
		
		add(icon,BorderLayout.WEST);
		add(TextPanel,BorderLayout.CENTER);
		add(Confirm, BorderLayout.EAST);
		
		//Editor mode on clicking dblclick
		
	}
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		return this;
	}
}

class ImageComponentEditing extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	public ImageComponentEditing(Image im)
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
	public void setImage(Image im)
	{
		image = im;
	}
	@Override
	public void paintComponent(Graphics g)
	{
		if (image ==null) return;
		g.drawImage(image, 0, 0,128,128,null);
	}
}

