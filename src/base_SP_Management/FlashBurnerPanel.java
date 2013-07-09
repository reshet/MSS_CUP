package base_SP_Management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import app_service_SARS_Projects.MyBTNTableCellEditor;
import app_service_SARS_Projects.MyBTNTableCellRenderer;
import app_service_SARS_Projects.MyCBOXTableCellEditor;
import app_service_SARS_Projects.MyCBOXTableCellRenderer;
import app_service_quiz.ListGroupsElementData;
import app_service_quiz.ListUsersElementData;
import app_service_quiz.ListUsersPanel;

import com.explodingpixels.macwidgets.IAppWidgetFactory;
import com.lowagie.text.pdf.codec.Base64;

public class FlashBurnerPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4283776631486357926L;
	private SocioProject sp;
	private JTable stateTable;
	private HeadStatePanel header;
	private DriveLookup lookuper;
	public FlashBurnerPanel(SocioProject prj) {
		sp = prj;
		//resetBurner();
	}
	private int anket_sel_ID = 0;
	@SuppressWarnings("unused")
	private int anket_real_ID = 0;
	
	private String ank_content;
	public void resetBurner()
	{
		//if(sp.getWidget() == null||sp.getWidget() == null) return;
		removeAll();
		setLayout(new BorderLayout(3,3));
		header = new HeadStatePanel("panel", "anket");
		add(header,BorderLayout.NORTH);
		
		int panelSelected = sp.getWidget().getPanelss_panel().getList().getSelectedIndex();
		anket_sel_ID = sp.getWidget().getSelectedAnketID();
		ank_content = sp.getS_project().getAnkets_content().get(anket_sel_ID).getRight();
		anket_real_ID = sp.getS_project().getAnkets_content().get(anket_sel_ID).getLeft();
		
			ank_content = new String(Base64.decode(ank_content));
		
		//JOptionPane.showInputDialog(ank_content);
		System.out.println(ank_content);
		if(panelSelected < 0 || anket_sel_ID < 0) return;
		
		ListGroupsElementData elem = (ListGroupsElementData)sp.getWidget().getPanelss_panel().getList().getModel().getElementAt(panelSelected);
		int panelID = elem.getID();
		String panel_name = elem.getName();
		String anket_name = sp.getWidget().getSelectedAnketName();
		header.setAnkName(anket_name);
		header.setPnlName(panel_name);
		
		ListUsersPanel users_pnl = new ListUsersPanel(panelID);
		users_pnl.getToolbar().refresh();
		ArrayList<Integer> panelists = new ArrayList<Integer>();
		ArrayList<String> panelist_names = new ArrayList<String>();
		DefaultListModel mdl = (DefaultListModel)users_pnl.getList().getModel();
		for(int i = 0; i < mdl.getSize();i++)
		{
			ListUsersElementData elemm = (ListUsersElementData)mdl.getElementAt(i);
			panelists.add(Integer.parseInt(elemm.getName()));
			panelist_names.add(elemm.getPIB());
		}
		
		lookuper = new DriveLookup();
		String[] colIdents = new String[]{"#","ID_user","PIB","PSWD","Status","Drive","Action"};
		Object[][] data = new Object[panelists.size()][7];
		Integer counter = 0;
		for(Integer panelist:panelists)
		{
			Object[] row = new Object[]{counter,panelist,panelist_names.get(counter),"default","Не выгружен",new DriveBox(lookuper),new JButton(new burn_flash_Action())};
			data[counter++] = row;
		}
		DefaultTableModel model = new DefaultTableModel(data,colIdents);
		stateTable = new JTable(model);
		TableColumnModel tcol= stateTable.getColumnModel();
		MyCBOXTableCellRenderer cbox_rend = new MyCBOXTableCellRenderer();
		MyCBOXTableCellEditor cbox_edit = new MyCBOXTableCellEditor();
		MyBTNTableCellRenderer btn_rend = new MyBTNTableCellRenderer();
		MyBTNTableCellEditor btn_edit = new MyBTNTableCellEditor();
			tcol.getColumn(5).setCellRenderer(cbox_rend);
			tcol.getColumn(5).setCellEditor(cbox_edit);
			tcol.getColumn(6).setCellRenderer(btn_rend);
			tcol.getColumn(6).setCellEditor(btn_edit);
		JTableHeader header = new JTableHeader(tcol);
		stateTable.setTableHeader(header);
		stateTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroller = new JScrollPane(stateTable);
		stateTable.setFillsViewportHeight(true);
		stateTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		stateTable.setRowHeight(27);
		stateTable.setBackground(new Color(90,90,90));
		stateTable.setForeground(new Color(190,190,255));
		stateTable.setSelectionBackground(new Color(90,90,200));
		stateTable.setSelectionForeground(new Color(200,200,90));
		scroller.setPreferredSize(new Dimension(300,300));
		scroller.setBackground(new Color(70,70,70));
		scroller.setForeground(new Color(60,60,60));
		IAppWidgetFactory.makeIAppScrollPane(scroller);
		add(scroller,BorderLayout.CENTER);
	}
	class burn_flash_Action extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4908853434470771961L;
		public burn_flash_Action() {
			putValue(NAME, "Выгрузить!");
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int sel = stateTable.getSelectedRow();
			String userID = String.valueOf(stateTable.getModel().getValueAt(sel, 1));
			String userPSWD = (String)stateTable.getModel().getValueAt(sel, 3);
			String drive = (String)((JComboBox)stateTable.getModel().getValueAt(sel, 5)).getSelectedItem();
			System.out.println("Flash burned! "+userID+":"+userPSWD+":"+drive);

			 File x = new File(drive+"msmart.flag");
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
				 	writer.println("<FLAG USERID=\""+userID+"\" PSWD=\""+userPSWD+"\"/>");
				 	writer.close();
				 }
				 
			 File dir = new File(drive+"/capital/"+String.valueOf(anket_sel_ID)+"/");
			 dir.mkdirs();
			 
			 dir = new File(drive+"/capitan/"+String.valueOf(anket_sel_ID)+"/");
			 dir.mkdirs();
			 
			 File xx = new File(drive+"/capitan/"+String.valueOf(anket_sel_ID)+"/"+"e.t");
			 try {
				xx.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			PrintWriter writerx = null;
			try {
				writerx = new PrintWriter(xx);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 if (writerx != null)
			 {
			 	writerx.println(ank_content);
				
			 	writerx.close();
			 }
			stateTable.getModel().setValueAt("Выгружено", sel, 4);
		}
		
	}
	class DriveLookup
	{
		public Thread drive_lookup_thr = new Thread(new Runnable() {
			@Override
			public void run() {
			
				while(!Thread.currentThread().isInterrupted())
				{
					try {
						// For Windows File [] roots = File.listRoots();
						File [] roots = new File("/media").listFiles();
						boolean fire = false;
						ArrayList<String> newdrives = new ArrayList<String>();
						for(File root:roots)
						{
							newdrives.add(root.getAbsolutePath());
							System.out.println(root.getAbsolutePath());
							if(!drives.contains(root.getAbsolutePath())||roots.length<drives.size())
							{
								fire = true;
								//drives.add(root.getAbsolutePath());
							}
						}
						if(fire||first)
						{
							drives = newdrives;
							fireDriveChanges();
							first = false;
						}
						
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		private ArrayList<String> drives = new ArrayList<String>();
		private ArrayList<DriveBox> boxes = new ArrayList<DriveBox>();
		private boolean first = true;
		public DriveLookup(){drive_lookup_thr.start();}
		public void addBox(DriveBox cbox)
		{
			boxes.add(cbox);
			first = true;
		}
		private void fireDriveChanges()
		{
			for(DriveBox box:boxes)
			{
				box.removeAllItems();
				for(String drive:drives)
				{
					box.addItem(drive);
				}
			}
		}
	}
	class DriveBox extends JComboBox
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -5012488785787870616L;

		public DriveBox(DriveLookup lookuper) {
			lookuper.addBox(this);
		}
	}
	class HeadStatePanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3645979201726682806L;
		private JLabel pnl,pnl_name,anket, anket_name;
		public HeadStatePanel(String pnl_nm,String ank_nm) {
			pnl = new JLabel("Панель: ");
			anket = new JLabel("Анкета: ");
			pnl_name = new JLabel(pnl_nm);
			anket_name = new JLabel(ank_nm);
			setLayout(new GridLayout(1,4));
			add(pnl);add(pnl_name);add(anket);add(anket_name);
		}
		public void setAnkName(String newName)
		{
			anket_name.setText(newName);
		}
		public void setPnlName(String newName)
		{
			pnl_name.setText(newName);
		}
	}
}
