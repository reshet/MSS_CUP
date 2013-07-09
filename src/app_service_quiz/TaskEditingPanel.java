package app_service_quiz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.EventObject;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.CellEditorListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;

import base_SP_Management.MyValueLabelCellEditor;
import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_Request;
import base_gui.ToolMainWidget;

import com.explodingpixels.macwidgets.HudWidgetFactory;
import com.explodingpixels.macwidgets.HudWindow;
import com.explodingpixels.macwidgets.IAppWidgetFactory;
import com.lowagie.text.pdf.codec.Base64;

public class TaskEditingPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6864988350745968879L;
	private JTextArea contence,key;
	private JTable linked_table,tbl;
	private MSS_RQ_Admin reqHandler;
	private JFileChooser chooser;
	private HudWindow dlg;
	private JDialog dlgg;
	public JTable getLinked_table() {
		return linked_table;
	}
	public JTable getMetaTable() {
		return tbl;
	}
	public void setLinked_table(JTable linkedTable) {
		linked_table = linkedTable;
	}
	public void setContence(String val)
	{
		contence.setText(val);
	}
	public String getContence()
	{
		return contence.getText();
	}
	public void setKey(String val)
	{
		key.setText(val);
	}
	public String getKey()
	{
		return key.getText();
	}
	class MyMetaCellEditor implements TableCellEditor
	{
		public MyMetaCellEditor()
		{
			panel = new JPanel(new BorderLayout(2,2));
			panel.setPreferredSize(new Dimension(400,300));
			panel.setBackground(new Color(90,90,90));
			list = new JList(new DefaultListModel());
			list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			list.setLayoutOrientation(JList.VERTICAL);
			list.setVisibleRowCount(-1);
			list.setSelectedIndex(0);
			Border border = BorderFactory.createEmptyBorder(0,0,1,1);
			list.setBorder(border);
			list.setBackground(new Color(50,50,50));
			list.setForeground(new Color(210,210,220));
			
			JScrollPane scroller = new JScrollPane(list);
			scroller.setBackground(new Color(70,70,70));
			scroller.setAutoscrolls(true);
			IAppWidgetFactory.makeIAppScrollPane(scroller);
			scroller.setFocusable(true);
			scroller.setPreferredSize(new Dimension(370, 320));
			panel.add(scroller);
		}
		 private JPanel panel;
		 private JList list;
		 private String str;
			@Override
			public Component getTableCellEditorComponent(JTable table,
					Object value, boolean isSelected, int row, int column) {
			str = (String)value;
			String [] strs = str.split(";");
			DefaultListModel model = (DefaultListModel) list.getModel();
			for(String st:strs)
			{
				model.addElement(st);
			}
			list.setModel(model);
			JDialog dlg = new JDialog();
			dlg.setTitle("ValueLabels");
			dlg.setModal(true);
			//dlg.setUndecorated(true);
			dlg.setSize(new Dimension(500,500));
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			dlg.setLocation((int)screenSize.getWidth()/2 - (int)dlg.getSize().getWidth()/2,
					(int)screenSize.getHeight()/2 - (int)dlg.getSize().getHeight()/2);
			dlg.add(panel);
			dlg.setVisible(true);
			return new JLabel(str);
		}
			@Override
			public void addCellEditorListener(CellEditorListener arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void cancelCellEditing() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public Object getCellEditorValue() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public boolean isCellEditable(EventObject arg0) {
				// TODO Auto-generated method stub
				return true;
			}
			@Override
			public void removeCellEditorListener(CellEditorListener arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public boolean shouldSelectCell(EventObject arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean stopCellEditing() {
				// TODO Auto-generated method stub
				return false;
			}
	}
	private void prepare_meta_dlg()
	{
		tbl = new JTable();
		dlgg = new JDialog();
		dlgg.setTitle("Метаданные анкеты...");
		dlgg.setSize(400,400);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dlgg.setLocation((int)screenSize.getWidth()/2 - (int)dlgg.getSize().getWidth()/2,
				(int)screenSize.getHeight()/2 - (int)dlgg.getSize().getHeight()/2);
		DefaultTableModel tmodel = new DefaultTableModel(10, 3);
		//tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbl.setAutoscrolls(true);
		for (int i =0;i<10;i++)
		{
			for (int j =0;j<3;j++)
			{
				tmodel.setValueAt("empty_cell", i, j);
			}
		}
		tbl.setModel(tmodel);
		tbl.setEnabled(true);
		TableColumnModel col_model = tbl.getColumnModel();
		tmodel.setColumnIdentifiers(new String[]{"Метка","Описание","Альтернативы"});
		col_model.getColumn(0).setWidth(30);
		col_model.getColumn(0).setMaxWidth(30);
		
		col_model.getColumn(1).setPreferredWidth(400);
		
		TableCellEditor TCEditor =  new MyValueLabelCellEditor();
		col_model.getColumn(2).setCellEditor(TCEditor);
    	tbl.setColumnModel(col_model);
		
		JScrollPane pane = new JScrollPane(tbl);
		dlgg.add(pane);
		dlgg.setModal(true);
	}
	public TaskEditingPanel() {
		//JLabel header = HudWidgetFactory.createHudLabel("Редагування завдання");
		//header.setFont(new Font("helvetika",Font.BOLD,14));
		//JPanel superbar = new JPanel(new GridLayout(2,1));
		//JPanel[] emptypanels = new JPanel[30];
		prepare_meta_dlg();
		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		this.setBorder(border);
		Border border2 = BorderFactory.createEmptyBorder(0, 0, 5, 5);
		JPanel bar1 = new JPanel(new GridLayout(5,1,5,5));
		JPanel bar2 = new JPanel(new GridLayout(2,1,5,5));
		bar2.setBorder(border2);	
		JPanel bar3 = new JPanel(new GridLayout(4,1));
		//superbar.setPreferredSize(new Dimension(400,90));
		bar1.setPreferredSize(new Dimension(70,90));
		JLabel text1 = HudWidgetFactory.createHudLabel("Опис: ");
		JLabel text2 = HudWidgetFactory.createHudLabel("Ключ: ");
		contence = new JTextArea("Складний тест");
		key = new JTextArea("11010010101010...");
		key.setLineWrap(true);
		JButton load = HudWidgetFactory.createHudButton("Завантажити TSX...");
		JButton accept = HudWidgetFactory.createHudButton("Підтвердити!");
		JButton meta = HudWidgetFactory.createHudButton("Метадані!");
		JButton save = HudWidgetFactory.createHudButton("Экспорт на Flash!");
		
		bar1.add(new JLabel(""));bar1.add(text1);bar1.add(new JLabel(""));bar1.add(text2);bar1.add(new JLabel(""));
		bar2.add(contence);bar2.add(new JScrollPane(key));
		bar3.add(save);bar3.add(load);bar3.add(meta);bar3.add(accept);
		//superbar.add(bar1);superbar.add(bar2);
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(80,80,80));
		//add(header,BorderLayout.NORTH);
		add(bar1,BorderLayout.WEST);
		add(bar2,BorderLayout.CENTER);
		add(bar3,BorderLayout.EAST);
		bar1.setBackground(bar1.getParent().getBackground());
		bar2.setBackground(bar2.getParent().getBackground());
		bar3.setBackground(bar3.getParent().getBackground());
		meta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dlgg.setVisible(true);
			}
		});
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog dlg = new JDialog();
				//dlg.setModal(true);
				dlg.setTitle("Сохранение на карту памяти...");
				dlg.setSize(400,400);
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				dlg.setLocation((int)screenSize.getWidth()/2 - (int)dlgg.getSize().getWidth()/2,
						(int)screenSize.getHeight()/2 - (int)dlgg.getSize().getHeight()/2);
				JLabel lbl = new JLabel("Вставьте карту памяти.");
				dlg.add(lbl);
				if (seek_flash(Integer.parseInt((String)linked_table.getModel().getValueAt(linked_table.getSelectedRow(),1)), getKey()))
				{
					lbl.setText("Дані успішно збережені");
				}
				else
				{
					lbl.setText("Проблеми при збереженні...");	
				}
				
				dlg.setVisible(true);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dlg.setVisible(false);
				dlg.dispose();
			}
		});
		
		reqHandler = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
		accept.addActionListener(new ActionListener() {	
			private int ID;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selected = linked_table.getSelectedRow();
				//int N = Integer.parseInt((String)tasksPanel.getTable().getValueAt(selected, 0))-1;
				ID = Integer.parseInt((String)linked_table.getValueAt(selected, 1));
				new Thread(new Runnable() {
					@Override
					public void run() {
						String prep_task_key = key.getText();
						String prep_coded = null;
						try 
						{
							prep_coded = new String(prep_task_key.getBytes(),"CP1251");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (prep_coded != null)
						{
							 prep_coded = new String(Base64.encodeBytes(prep_coded.getBytes()));
							MSS_RQ_Request.http_request(reqHandler.updateTask(prep_coded,contence.getText(), ID),ToolMainWidget.URL);	
						}
					}
				}).start();
			}
		});
		chooser = new JFileChooser();
		chooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "Файли завдань MSS-Rada TSX";
			}
			
			@Override
			public boolean accept(File f) {
				String extension = Utils.getExtension(f);
			    if (extension != null) {
				if (extension.equals(Utils.TSX)
						||
					extension.equals(Utils.T)) {
				        return true;
				} else {
				    return false;
				}
			    }

			    return false;
			}
		});
		dlg = new HudWindow("Оберіть файл TSX...");
		dlg.getJDialog().setPreferredSize(new Dimension(300,400));
		dlg.getJDialog().setBackground(new Color(80,80,80));
		load.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int returnVal = chooser.showOpenDialog(dlg.getJDialog());

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = chooser.getSelectedFile();
		            try {
						Scanner scn = new Scanner(file);
						StringBuilder bld = new StringBuilder();
						while(scn.hasNextLine())
						{
							bld.append(scn.next());
						}
						key.setText(Base64.encodeBytes(bld.toString().getBytes()).toString());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
		            //This is where a real application would open the file.
		            //log.append("Opening: " + file.getName() + "." + newline);
		        } else {
		            //log.append("Open command cancelled by user." + newline);
		        }
			}
		});
		//add(superbar);
	}
	public boolean seek_flash(int anket_id, String content)
	{
		 boolean found = false;
		 File flash = null;
		 int trys = 0;
		 while(!found && trys < 20)
		 {
			 File [] roots = File.listRoots();
			 //File [] xml_files = new File[100];
			// int f_count = 0;	
			 for(File root:roots)
			 {
				//System.out.println(root.getAbsolutePath()); 
				if (new File(root.getAbsolutePath()+"msmart.flag").exists())
				{
					found = true;
					flash = root;
				}
			 }	
			 try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			trys++;
		 }
		 if (flash != null)
		 {
			
			 File dir = new File(flash.getAbsolutePath()+"/capitan/"+String.valueOf(anket_id)+"/");
			 dir.mkdirs();
			 File x = new File(dir.getAbsolutePath()+"/"+"e.t");
			 try {
				x.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 PrintWriter writer = null;
			try {
				writer = new PrintWriter(x);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 if (writer != null)
			 {
			 	writer.println(content);
			 	writer.close();
			 	return true;
			 }
			 return false;
		 }
		 return false;
	 }
}

class Utils {

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";
    public final static String TSX = "tsx";
    public final static String CSV = "csv";
    public final static String T = "t";
    
    
    /*
     * Get the extension of a file.
     */  
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}