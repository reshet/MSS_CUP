package app_service_quiz;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import app_service_SARS_Projects.SProject_InstrumentDesk;
import app_service_news.NewsServiceElementData;
import base_SP_Management.LocalInterviewElement;
import base_SP_Management.OSValidator;
import base_connectivity.MSS_Pair;
import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_XML;
import base_connectivity.MSS_RQ_XML_Pattern;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;

import com.explodingpixels.macwidgets.HudWidgetFactory;

public class CAPI_memory_grabber extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3472717812596817288L;
	@SuppressWarnings("unused")
	private JButton grab,send;
	private JTable attached_meta_table,attached_cases_table;
	public void setAttached_meta_table(JTable attachedMetaTable) {
		attached_meta_table = attachedMetaTable;
	}
	public void setAttached_cases_table(JTable attachedCasesTable) {
		attached_cases_table = attachedCasesTable;
	}
	private ArrayList<LocalInterviewElement> new_indecies;
	@SuppressWarnings("unused")
	private JPanel toolbar;
	private MSS_RQ_XML xmler;
	private ArrayList<String> column_names;
	@SuppressWarnings("unused")
	private String[] columnNames;
	//private DefaultTableModel model;
	/*
	public DefaultTableModel getModel() {
		return model;
	}
	*/
	public CAPI_memory_grabber(JTable cases_table,JTable meta_table)
	{
		this.attached_cases_table = cases_table;
		this.attached_meta_table = meta_table;
		//this.columnNames = Arrays.copyOf(col_names,col_names.length);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(500,400));
		setBackground(new Color(60,60,60));
		grab = HudWidgetFactory.createHudButton("Зібрати");
		send = HudWidgetFactory.createHudButton("Відіслати");
		grab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				grab(0);
			}
		});
		
		@SuppressWarnings("unused")
		Object [][] data = {
				{}
				};
		//ID="185" Description="Task description" Visability="0" Aviability="0" Accepted="0" Subscribed="0";
		//columnNames = new String[]{};
		//model = new DefaultTableModel(data, columnNames);
		//table = new JTable(model);
		//table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//table.setPreferredSize(new Dimension(400,300));
		//table.setSize(400, 300);
		//table.setHorizontalScrollEnabled(true);
		//JScrollPane scroller = new JScrollPane(table);
		//scroller.setPreferredSize(new Dimension(500,400));
		//scroller.setAutoscrolls(true);
		//IAppWidgetFactory.makeIAppScrollPane(scroller);
		
		//toolbar = new JPanel();
		//toolbar.setBackground(new Color(50,50,50));
		//toolbar.add(grab);
		//toolbar.add(send);
		//add(toolbar, BorderLayout.SOUTH);
		//add(scroller);
	}
	private boolean isInsideAttribute(String xmlstr,int pos)
	{
		boolean in_attr = false;
		String substr = xmlstr.substring(0,pos);
		int col_brances = 0;
		while (substr.contains("\""))
		{
			substr = substr.replaceFirst("\"", "");
			col_brances++;
		}
		in_attr = col_brances%2 == 1;
		return in_attr;
	}
	private ArrayList<Integer> countOccurences(String base, String seq)
	{
		String b = base;
		@SuppressWarnings("unused")
		int count = 0;
		ArrayList<Integer> positions = new ArrayList<Integer>();
		int shift = 0;
		while(b.contains(seq))
		{
			count++;
			positions.add(b.indexOf(seq)+shift);
			b = b.replaceFirst(seq, "");
			shift+=seq.length();
		}
		return positions;
	}
	private String preProcessUnvalidInterview(String inputInterview)
	{
		/*
		 * makes invalid xml correct.
		 */
		String outputInterview;
		outputInterview = new String(inputInterview);
		
		String[] ints = {" 0"," 1"," 2"," 3"," 4"," 5"," 6"," 7"," 8"," 9"};
		outputInterview=outputInterview.replaceAll(".2011 ", ".2011_");
		outputInterview=outputInterview.replaceAll("=\" ", "=\"\" ");
		outputInterview=outputInterview.replaceAll("`", "");
		outputInterview=outputInterview.replaceAll("  ", " ");
		
		
		outputInterview=outputInterview.replaceAll(" \" ", " ");
		
		System.out.println(outputInterview);
		
		for (String integ_seq:ints)
		{
			ArrayList<Integer> poss = countOccurences(outputInterview, integ_seq);
			//String outAfter = new String(outputInterview);
			int shift = 0;
			for(Integer pos:poss)
			{
				int cur_pos = pos + shift;
				if(!isInsideAttribute(outputInterview,cur_pos))
				{
					//outputInterview = outputInterview.replaceFirst(integ_seq, " DYN_"+integ_seq.substring(1, 2));
					String before = outputInterview.substring(0,cur_pos);
					String after = outputInterview.substring(cur_pos+2,outputInterview.length());
					outputInterview = new String(before +" DYN_"+integ_seq.substring(1, 2)+after);
					shift+=4;
				}
			}
			//outputInterview = outAfter;
			
		}
		outputInterview=outputInterview.replaceAll(".2011_", ".2011 ");
		System.out.println(outputInterview);
		return outputInterview;
	}
	private void addDynamicVarToMeta(String var_label,int metatable_prototype_id)
	{
		DefaultTableModel model = (DefaultTableModel)attached_meta_table.getModel();
		model.addRow(new Vector<String>(model.getColumnCount()));
		//add Label value
		model.setValueAt(var_label, model.getRowCount()-1,0);
		//copy of prototype
		 for(int i = 1; i < model.getColumnCount();i++)
		 {
			 model.setValueAt((String)model.getValueAt(metatable_prototype_id,i), model.getRowCount()-1, i);
		 }
		 attached_meta_table.setModel(model);
	}
	public void grab(int anketID)
	{
		// File [] roots = File.listRoots();
	
		
		File [] roots = {};
		if(OSValidator.isWindows())roots = File.listRoots();
		else if(OSValidator.isUnix())roots = new File("/media").listFiles();
		else if (OSValidator.isMac())roots = new File("/Volumes").listFiles();
		
		//File [] roots = new File("/media").listFiles();
		 File [] xml_files = new File[1000];
		 int f_count = 0;	
		 String userID="",userPSWD="";
		 for(File root:roots){
			//System.out.println(root.getAbsolutePath()); 
			if (new File(root.getAbsolutePath()+"/msmart.flag").exists())
			{
				StringBuilder strs = new StringBuilder();
				Scanner scns = null;
				try {
					scns = new Scanner(new File(root.getAbsolutePath()+"/msmart.flag"));
					while(scns.hasNextLine()) {
						strs.append(scns.nextLine());
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				String settings = strs.toString();
				
				MSS_RQ_TableDescriptor NewsTDesc = new MSS_RQ_TableDescriptor(new String[]{"USERID","PSWD"},
						new Class[]{String.class,String.class});
			    MSS_RQ_XMLtoTableDescriptor NewsT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"USERID","PSWD"});
			    MSS_RQ_CxListFiller NewsUpdater = new MSS_RQ_CxListFiller(NewsTDesc,NewsT_XML_Desc,NewsServiceElementData.class);		   
			    userID = NewsUpdater.requestLocalData_FromRoot(settings, 0);
			    userPSWD = NewsUpdater.requestLocalData_FromRoot(settings, 1);
			    
				
				
				File dir = new File(root.getAbsolutePath()+"/capital/"+String.valueOf(anketID)+"/");
				if (dir.isDirectory())
				{
					File [] files = dir.listFiles();
					for (File f: files)
					{	
						if (f.getAbsolutePath().endsWith(".xml"))
						{
							xml_files[f_count++] = f;
						}
					}
				}
			}
		 }
		 
		 if(attached_cases_table == null || attached_meta_table == null) return; 
		 
		 xmler = new MSS_RQ_XML();
		 column_names = new ArrayList<String>(100);
		 column_names.add("ID_FILE");
		 for(int i = 0; i < attached_meta_table.getModel().getRowCount();i++)
		 {
			 column_names.add((String)attached_meta_table.getModel().getValueAt(i,0));
		 }
		 
		 System.out.println(column_names.get(5));
		ArrayList<ArrayList<MSS_Pair>> DATA = new ArrayList<ArrayList<MSS_Pair>>(100);
		for (int i = 0; i < f_count;i++)
		{
			System.out.println(xml_files[i].getAbsolutePath());
			StringBuilder str = new StringBuilder();
	//		Scanner scn = null;
			try {
				//FileReader re = new FileReader(xml_files[i]);
				//BufferedInputStream stre = new BufferedInputStream(new FileReader(xml_files[i]));
				//re.
//			    String NL = System.getProperty("line.separator");
//				scn = new Scanner(xml_files[i]);
//				////////???
//				while(scn.hasNextLine()) {
//					str.append(scn.nextLine()+NL);
//				}
//				scn.close();
				
				BufferedReader input =  new BufferedReader(new FileReader(xml_files[i]));
			      try {
			        String line = null; //not declared within while loop
			        /*
			        * readLine is a bit quirky :
			        * it returns the content of a line MINUS the newline.
			        * it returns null only for the END of the stream.
			        * it returns an empty String if two newlines appear in a row.
			        */
			        while (( line = input.readLine()) != null){
			          str.append(line);
			          str.append(System.getProperty("line.separator"));
			        }
			      } 
			      finally {
				        input.close();
				      }
			}
		      catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<MSS_RQ_XML_Pattern> ptn;
			
			try {
				String interview = new String(str.toString().getBytes(),"UTF-8");
				//interview = preProcessUnvalidInterview(interview);
				ptn = xmler.parseXML(interview);
				
				for (MSS_RQ_XML_Pattern ptrn:ptn)
				{
					ArrayList<MSS_Pair> pairs = ptrn.getAttributes();
					ArrayList<MSS_Pair> pairs_res = new ArrayList<MSS_Pair>();
					for (MSS_Pair pair:pairs)
					{
						String pair_name = pair.getName();
						String tail = "";
						if(!column_names.contains(pair_name))
						{
							int antiloop = 0;
							while (!column_names.contains(pair_name)||pair_name.lastIndexOf("_")>= 0||antiloop<10)
							{
								int have_ = pair_name.lastIndexOf("_");
								if (have_ > -1)
								{
									String base_name = pair_name.substring(have_+1,pair_name.length())+tail;
									System.out.println(pair_name);
									System.out.println(base_name);
									if (!column_names.contains(base_name))
									{
										pair_name = pair_name.substring(0,have_);
										tail+="_"+base_name;
										antiloop++;
										//pairs_res.remove(pairs.indexOf(pair));
									}
									else
									{
										MSS_Pair pr = new MSS_Pair(pair.getName(), pair.getValue());
										pairs_res.add(pr);
										column_names.add(pair.getName());
										
										
										//add this var to metatable - temporary solution
										int proto_id = column_names.indexOf(base_name);
										addDynamicVarToMeta(pair.getName(), proto_id);
										//
										break;
									}
								} else
								{
									break;
								}
								//column_names.add(pair.getName());
							}
						}else
						{	
							MSS_Pair pr = new MSS_Pair(pair.getName(), pair.getValue());
							pairs_res.add(pr);
						}
					//	System.out.println(pair.getName()+ " : "+ pair.getValue());
					}
					pairs_res.add(new MSS_Pair("ID_USER", userID));
					pairs_res.add(new MSS_Pair("ID_FILE", xml_files[i].getName()));
					
					DATA.add(pairs_res);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@SuppressWarnings("unused")
		String [][] data = new String[DATA.size()][column_names.size()];
		//Object [] columnNames = column_names.toArray();
		
		//table.
		for(String col_name:this.column_names)
		{
			int found_col = -1;
			for(int k = 0; k < attached_cases_table.getModel().getColumnCount();k++)
			{
				if(attached_cases_table.getModel().getColumnName(k).equals(col_name))
				{
					found_col = k;
					break;
				}
			}
			if (found_col == -1)
			{
				((DefaultTableModel)attached_cases_table.getModel()).addColumn(col_name);
			}
		}
		
		int i = attached_cases_table.getModel().getRowCount();
		
		for (ArrayList<MSS_Pair> row: DATA)
		{
			int col_count = attached_cases_table.getModel().getColumnCount();
			((DefaultTableModel)attached_cases_table.getModel()).addRow(new String[col_count]);
			attached_cases_table.getModel().setValueAt(attached_cases_table.getModel().getRowCount(), attached_cases_table.getModel().getRowCount()-1, 0);
			new_indecies.add(new LocalInterviewElement(userID, userPSWD, attached_cases_table.getModel().getRowCount()-1));
			for(MSS_Pair elem:row)
			{
				int found_col = -1;
				for(int k = 0; k < attached_cases_table.getModel().getColumnCount();k++)
				{
					if(attached_cases_table.getModel().getColumnName(k).equals(elem.getName()))
					{
						found_col = k;
						break;
					}
				}
				if (found_col != -1)
					attached_cases_table.getModel().setValueAt(elem.getValue(), i, found_col);
			}
			i++;
		}
		
		SProject_InstrumentDesk.fillWithMissingValues(attached_cases_table,attached_meta_table);
		//TableModel model = new DefaultTableModel(data, columnNames);
		
		
		//table.setModel(model);
		//this.model = (DefaultTableModel) model;
		//TableColumnModel tcol= table.getColumnModel(); 
		//JTableHeader header = new JTableHeader(tcol);
		//table.setTableHeader(header);
		//table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//table.setModel(model);
		//this.add(scrl,BorderLayout.CENTER);
		//scrl.setVisible(true);	
	}
	public JTable getAttached_meta_table() {
		return attached_meta_table;
	}
	public JTable getAttached_cases_table() {
		return attached_cases_table;
	}
	public void setNew_indecies(ArrayList<LocalInterviewElement> new_indecies) {
		this.new_indecies = new_indecies;
	}
	public ArrayList<LocalInterviewElement> getNew_indecies() {
		return new_indecies;
	}
}
