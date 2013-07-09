package base_data_exchanger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import base_SP_Management.SocioAnalysisForm;
import base_SP_Management.SocioInstrumentDesk;
import base_SP_Management.SocioPanelMember;
import base_SP_Management.bindDataDescriptor;

import com.explodingpixels.macwidgets.HudWindow;
import com.pmstation.spss.MissingValue;
import com.pmstation.spss.SPSSWriter;
import com.pmstation.spss.ValueLabels;

public class Internal_IO {
	private SocioAnalysisForm analForm;
	private ArrayList<SocioPanelMember> memb_list;
	private JFileChooser chooser;
	@SuppressWarnings("unused")
	private JTable tbl;
	private DefaultTableModel _Model,_M_Model;
	//@SuppressWarnings("unused")
	//private SocioInstrumentDesk desk;
	private JProgressBar p_bar;
	private HudWindow dlg;
	private int ret_val;
	public void set_Model(DefaultTableModel model) {
		_Model = model;
	}
	public DefaultTableModel getTblModel() {
		return _Model;
	}
	public void set_M_Model(DefaultTableModel model) {
		_M_Model = model;
	}
	public DefaultTableModel getTbl_M_Model() {
		return _M_Model;
	}
	public Internal_IO(SocioInstrumentDesk des)
	{
		//this.desk = des;
	}
	public bindDataDescriptor bindHeaderCSV()
	{
		chooser = new JFileChooser();

		chooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "Файли баз данных CSV";
			}
			
			@Override
			public boolean accept(File f) {
				String extension = Utils.getExtension(f);
			    if (extension != null) {
				if (extension.equals(Utils.CSV)
						||
						extension.equals(Utils.SAV)
						||
						extension.equals(Utils.XML)
						||
						extension.equals(Utils.TSX)
						) {
				        return true;
				} else {
				    return false;
				}
			    }

			    return false;
			}
		});
		dlg = new HudWindow("Оберіть файл CSV...");
		dlg.getJDialog().setPreferredSize(new Dimension(300,400));
		dlg.getJDialog().setBackground(new Color(80,80,80));
		ret_val = chooser.showOpenDialog(dlg.getJDialog());
		Set<String> set = new HashSet<String>();	
		String fname = null;
		        if (ret_val == JFileChooser.APPROVE_OPTION) {
								File file = chooser.getSelectedFile();
								try
								{
									Scanner scn = new Scanner(file);
									String varnames = null;
									fname = file.getPath();
									varnames = scn.nextLine();
									varnames.replaceAll("; ", ";");
									String [] values = varnames.split(";");		
									for(String str:values)
									{
										set.add(str);			
									}
									scn.close();
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
		        } else {
		        }	
	/////////////	    
		return new bindDataDescriptor(fname, set);
	}
	
	public void loadUsersCSV()
	{
		
		chooser = new JFileChooser();

		chooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "Файли баз данных CSV";
			}
			
			@Override
			public boolean accept(File f) {
				String extension = Utils.getExtension(f);
			    if (extension != null) {
				if (extension.equals(Utils.CSV)
						||
					extension.equals(Utils.SAV)
					||
					extension.equals(Utils.XML)
					||
					extension.equals(Utils.TSX)) {
				        return true;
				} else {
				    return false;
				}
			    }
			    return false;
			}
		});
		dlg = new HudWindow("Оберіть файл CSV...");
		dlg.getJDialog().setPreferredSize(new Dimension(300,400));
		dlg.getJDialog().setBackground(new Color(80,80,80));
				ret_val = chooser.showOpenDialog(dlg.getJDialog());
		        if (ret_val == JFileChooser.APPROVE_OPTION) {
		        	Thread thr = new Thread(new Runnable() {
						@Override
						public void run() {
						
								JDialog dlgg = new JDialog();
								dlgg.setTitle("Загрузка данных.Подождите, пожалуйста!");
								dlgg.setSize(new Dimension (400,80));
								Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
								dlgg.setLocation((int)screenSize.getWidth()/2 - (int)dlgg.getSize().getWidth()/2,
										(int)screenSize.getHeight()/2 - (int)dlgg.getSize().getHeight()/2);
								File file = chooser.getSelectedFile();
								if (Utils.getExtension(file).equals(Utils.SAV))
								{
									System.out.println("SAV FOUND!!!");
									SPSS_JDBC_Connector connector = new SPSS_JDBC_Connector(file.getAbsolutePath());
									//ArrayList<ArrayList<String>> arr = connector.getDBManeger().DB_Reader("SELECT * FROM Cases;");
									//ArrayList<ArrayList<String>> meta_arr = connector.getDBManeger().DB_Reader("SELECT * FROM CasesView;");
									ArrayList<ArrayList<String>> arr = connector.getDBManeger().DB_Reader("SELECT * FROM Cases;");
									ArrayList<ArrayList<String>> meta_arr = connector.getDBManeger().DB_Reader("SELECT * FROM Variables;");
									
									DefaultTableModel T_model = new DefaultTableModel();
									DefaultTableModel TM_model = new DefaultTableModel();
									int counter = 0;
									for (ArrayList<String> arr_str: arr)
									{
										if (counter == 0)
										{
											for (String c_name : arr_str)
											{
												T_model.addColumn(c_name);
											}
										}
										else 
										{
											T_model.addRow(arr_str.toArray());
										}
										counter++;
									}
									counter = 0;
									for (ArrayList<String> arr_str: meta_arr)
									{
										if (counter == 0)
										{
											for (String c_name : arr_str)
											{
												TM_model.addColumn(c_name);
											}
										}
										else 
										{
											//Additionally Load valueLabels
											int label_col = findColoumn(TM_model, "ValueLabelTableName");
											if (label_col != -1)
											{
												String table_name = arr_str.get(label_col);
												ArrayList<ArrayList<String>> labels_arr = connector.getDBManeger().DB_Reader("SELECT * FROM "+table_name+";");
												String labels = "";
												boolean first = true;
												for(ArrayList<String> labs_str:labels_arr)
												{
													if (!first)
													{
														labels+="\""+labs_str.get(0)+"\"=\""+labs_str.get(1)+"\";";	
													}else first = false;
												}
												arr_str.set(label_col, labels);
											}
											TM_model.addRow(arr_str.toArray());
				
										}
										counter++;
									}
									_Model = T_model;
									_M_Model = TM_model;
								} else
									
								try
								{
									Scanner scn = new Scanner(file,"CP1251");
									p_bar = new JProgressBar(0, (int)file.length());
									p_bar.setPreferredSize(new Dimension (400,20));
									dlgg.add(p_bar);
									dlgg.setVisible(true);
									
									//StringBuilder bld = new StringBuilder();
									String varnames = null;
									int i = 0;
									DefaultTableModel T_model = new DefaultTableModel();
								
									while(scn.hasNextLine())
									{
										if (varnames == null)
										{
											varnames = scn.nextLine();
											p_bar.setValue(p_bar.getValue()+varnames.length());
											varnames = varnames.replaceAll("; ", ";");
											String [] values = varnames.split(";");
											Set<String> set = new HashSet<String>();
											//For table view
											//
											for(String str:values)
											{
												String str_utf = null;
												String str_rus = null;
												try {
													//str_utf = new String(str.getBytes("UTF-8"));
													str_rus = new String(str.getBytes("CP1251"));
												} catch (UnsupportedEncodingException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}          
												T_model.addColumn(str_rus);
												System.out.println(str+" "+str_utf+" "+str_rus);
												set.add(str_rus);			
											}
											analForm = new SocioAnalysisForm(set);
											/*
											Thread askForSettings = new Thread(new Runnable() {
												//boolean approved = false;
												//JDialog Settings_dlg;
												SP_PManager mgr;
												SP_PManager_Widget mgr_w;
												@SuppressWarnings("static-access")
												@Override
												public void run() {
													//Settings_dlg = new JDialog();
													//Settings_dlg.setTitle("Настройка загрузки данных.");
													//Settings_dlg.setSize(new Dimension(400,100));
													//Settings_dlg.setLocation(400, 400);
													//JButton btn = new JButton("Подтвердить");
													
													mgr = desk.getS_panel().getS_project().getProjectManager();
													mgr_w = new SP_PManager_Widget(mgr);
													Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
													mgr_w.setLocation((int)screenSize.getWidth()/2 - (int)mgr_w.getSize().getWidth()/2,
															(int)screenSize.getHeight()/2 - (int)mgr_w.getSize().getHeight()/2);
													mgr_w.setVisible(true);
													
													
													//Settings_dlg.add(btn);
													//Settings_dlg.setVisible(true);
													//mgr_w.add(btn);
													while(!mgr.isApproved())
													{
														if (!Thread.currentThread().isInterrupted())
															try {
																Thread.currentThread().sleep(500);
															} catch (InterruptedException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															}
													}
													
												}
											});
											askForSettings.start();
											try {
												askForSettings.join();
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											*/
											//bar_updater = new BarUpdater(p_bar, 1,varnames.length());
											//EventQueue.invokeLater(new BarUpdater(p_bar, varnames.length()));
											
										}
										else
										{
											String var_values_row = scn.nextLine();
											p_bar.setValue(p_bar.getValue()+var_values_row.length());
											var_values_row = var_values_row.replaceAll("; ", ";");
											String [] values = var_values_row.split(";");
											String [] rus_values = new String[values.length];
											int counter = 0;
											for (String str:values)
											{
												String str_utf = null;
												String str_rus = null;
												try {
													//str_utf = new String(str.getBytes("UTF-8"));
													str_rus = new String(str.getBytes("CP1251"),"CP1251");
												} catch (UnsupportedEncodingException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} 
												rus_values[counter++] = str_rus;
												System.out.println(str+" "+str_utf+" "+str_rus);
											}
											//For table
											T_model.addRow(rus_values);
											
											//
											
											@SuppressWarnings("unused")
											SocioPanelMember member = new SocioPanelMember(i++,"panelist");
											Map<String,String> vars = new HashMap<String, String>();
											int col = 0;
											for(String key_str:analForm.getVar_names())
											{
												if (col < values.length&&values[col]!=null)
													{
														vars.put(key_str, values[col++]);
													}
											}
											//member.setVars(vars);
											//memb_list.add(member);
											//EventQueue.invokeLater(new BarUpdater(p_bar, values.length));
											
										}
										//tbl.setModel(T_model);	
									}
									_Model = T_model;
									scn.close();
									dlgg.setVisible(false);
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
						}
					});
		        	thr.start();
		        	try {
						thr.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
				
						//key.setText(Base64.encodeBytes(bld.toString().getBytes()).toString());
					
		            //This is where a real application would open the file.
		            //log.append("Opening: " + file.getName() + "." + newline);
		        } else {
		            //log.append("Open command cancelled by user." + newline);
		        }	
	/////////////	        
	}
	static public int findColoumn(DefaultTableModel model,String col_name)
	{
		for(int i = 0;i <model.getColumnCount();i++)
		{
			if (model.getColumnName(i).equals(col_name))return i;
		}
		return -1;
	}
	public void saveUsersCSV()
	{
		chooser = new JFileChooser();

		chooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "Файли баз данных CSV,SAV";
			}
			
			@Override
			public boolean accept(File f) {
				String extension = Utils.getExtension(f);
			    if (extension != null) {
				if (extension.equals(Utils.CSV)
						||
						extension.equals(Utils.SAV)
					) {
				        return true;
				} else {
				    return false;
				}
			    }

			    return false;
			}
		});
		dlg = new HudWindow("Збережіть файл...");
		dlg.getJDialog().setPreferredSize(new Dimension(300,400));
		dlg.getJDialog().setBackground(new Color(80,80,80));
		ret_val = chooser.showSaveDialog(dlg.getJDialog());
		
        if (ret_val == JFileChooser.APPROVE_OPTION) {
        	File file = chooser.getSelectedFile();
        	if (Utils.getExtension(file).equals(Utils.SAV))
        	{
        		try {
        			/*
        			OutputStream str = new FileOutputStream(file);
        			SPSSWriter writer = new SPSSWriter(str, "windows-1251");
        			//OutputStream reader = writer.getOut();
        			writer.setCalculateNumberOfCases(false);
        			writer.addDictionarySection(-1);
        			writer.addStringVar("Continents", 32, "Cont of the world");
        		      
        			writer.addDataSection();
        			writer.addData("Asia");
        		    writer.addFinishSection();
					*/
        			OutputStream str = new FileOutputStream(file);
        			SPSSWriter writer = new SPSSWriter(str, "windows-1251");
        			//OutputStream reader = writer.getOut();
        			writer.setCalculateNumberOfCases(false);
        			writer.addDictionarySection(-1);
        			int col_type = findColoumn(_M_Model, "Type");
        			int col_name = findColoumn(_M_Model, "VarName");
        			int col_width = findColoumn(_M_Model, "Width");
        			int col_decimals = findColoumn(_M_Model, "Decimals");
        			int col_label = findColoumn(_M_Model, "Label");
        			int col_mv = findColoumn(_M_Model, "MvCode");
        			@SuppressWarnings("unused")
					int col_columns = findColoumn(_M_Model, "WriteWidth");
        			@SuppressWarnings("unused")
					int col_align = findColoumn(_M_Model, "Alignment");
        			@SuppressWarnings("unused")
					int col_measure = findColoumn(_M_Model, "MeasLevel");
        			
        			int col_vlabel = findColoumn(_M_Model, "ValueLabelTableName");
        			//In map consider K = var number, V = types (0-integer,other = string length)
        			Map<Integer,Integer> map_types = new HashMap<Integer, Integer>();
        			for(int i = 0; i < _M_Model.getRowCount();i++)
        			{
        				MissingValue mv = new MissingValue();
    					mv.setOneDescreteMissingValue(Double.parseDouble((String)_M_Model.getValueAt(i, col_mv)));
    					
    					int type_c = Integer.parseInt((String)_M_Model.getValueAt(i, col_type));
    					map_types.put(i, type_c);
    					
    					if (type_c == 0)
        				{
        					writer.addNumericVar(
        							(String)_M_Model.getValueAt(i, col_name),
        							Integer.parseInt((String)_M_Model.getValueAt(i, col_width)),
        							Integer.parseInt((String)_M_Model.getValueAt(i, col_decimals)), 
        							(String)_M_Model.getValueAt(i, col_label)//,
        							//Integer.parseInt((String)_M_Model.getValueAt(i, col_columns)),
        							//Integer.parseInt((String)_M_Model.getValueAt(i, col_align)),
        							//Integer.parseInt((String)_M_Model.getValueAt(i, col_measure)),
        							//mv
        							);
        				}
        				else
        				{
        					writer.addStringVar(
        							(String)_M_Model.getValueAt(i, col_name),
        							Integer.parseInt((String)_M_Model.getValueAt(i, col_type)),
        							(String)_M_Model.getValueAt(i, col_label)//,
        							//Integer.parseInt((String)_M_Model.getValueAt(i, col_columns)),
        							//Integer.parseInt((String)_M_Model.getValueAt(i, col_align)),
        							//Integer.parseInt((String)_M_Model.getValueAt(i, col_measure))
        							);
        				}
        			}
        			//writer.addNumericVar("myvar",8,1,"some");
					//writer.addNumericVar("myvar2",8,1,"some");
					//writer.
					//writer.addStringVar("cont", 32, "continents of the world");
        			
        			//ValueLabels labs = new ValueLabels();
					//labs.putLabel(232, "LabelMy");
					//writer.addValueLabels(1, labs);
        			
        			for(int i = 0; i < _M_Model.getRowCount();i++)
        			{
        				ValueLabels labs = new ValueLabels();
    					String str_label = (String)_M_Model.getValueAt(i, col_vlabel);
    					String [] strs = str_label.split(";");
    					for (String st: strs)
    					{
    						String [] s = st.split("=");
    						s[0] = s[0].substring(1, s[0].length()-1);
    						s[1] = s[1].substring(1, s[1].length()-1);
    						labs.putLabel(Double.parseDouble(s[0]), s[1]);
    						System.out.print(Double.parseDouble(s[0]));
    						System.out.println(" : "+s[1]);
        				}
    					if (map_types.get(i)==0)
    					{
    						
    						writer.addValueLabels(i+2, labs);
    					}
        			}
        			
					writer.addDataSection();
					
					for(int i = 0; i < _Model.getRowCount();i++)
        			{
						for (int j = 0; j < _M_Model.getRowCount();j++)
						{
							if (map_types.get(j) == 0)
							{
								writer.addData(Double.parseDouble((String)_Model.getValueAt(i, j+1)));
								System.out.println(i+" : "+j+" : "+(String)_Model.getValueAt(i, j+1));
							}
							else
							{
								writer.addData((String)_Model.getValueAt(i, j+1));
								System.out.println(i+" : "+j+" : "+(String)_Model.getValueAt(i, j+1));
							}
						}
					}
					writer.addFinishSection();
					
					str.close();
					} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				JDialog dlgg = new JDialog();
				dlgg.setTitle("Сохранение данных. Подождите, пожалуйста!");
				dlgg.setSize(new Dimension (400,80));
				dlgg.setLocation(400, 400);
				p_bar = new JProgressBar(0, _Model.getRowCount());
				p_bar.setPreferredSize(new Dimension (400,20));
				dlgg.add(p_bar);
				dlgg.setVisible(true);
				*/
				/*
				for (int j = 0; j< _Model.getColumnCount()-1;j++)
				{
					writer.print(_Model.getColumnName(j)+";");
				}
				writer.println();
				for (int i = 0; i< _Model.getRowCount();i++)
				{
					for (int j = 0; j< _Model.getColumnCount()-1;j++)
					{
						writer.print(_Model.getValueAt(i, j)+";");
					}
					writer.print(_Model.getValueAt(i, _Model.getColumnCount()-1));
					writer.println();
					p_bar.setValue(i);
				}
				//dlgg.setVisible(false);
				writer.close();
				*/
        	}
        	else
        	{
        		try {
        			PrintWriter writer = new PrintWriter(file);
        			/*
        			for(SocioPanelMember memb:memb_list)
        			{
        				Map<String,String> map = memb.getVars();
        				for(String str:analForm.getVar_names())
        				{
        					writer.print(map.get(str)+"|");
        				}
        				writer.println();
        			}
        			*/
        			JDialog dlgg = new JDialog();
    				dlgg.setTitle("Сохранение данных. Подождите, пожалуйста!");
    				dlgg.setSize(new Dimension (400,80));
    				dlgg.setLocation(400, 400);
    				p_bar = new JProgressBar(0, _Model.getRowCount());
    				p_bar.setPreferredSize(new Dimension (400,20));
    				dlgg.add(p_bar);
    				dlgg.setVisible(true);
    				
    				for (int j = 0; j< _Model.getColumnCount()-1;j++)
        			{
    					writer.print(_Model.getColumnName(j)+";");
        			}
    				writer.println();
        			for (int i = 0; i< _Model.getRowCount();i++)
        			{
        				for (int j = 0; j< _Model.getColumnCount()-1;j++)
            			{
        					writer.print(_Model.getValueAt(i, j)+";");
            			}
        				writer.print(_Model.getValueAt(i, _Model.getColumnCount()-1));
        				writer.println();
        				p_bar.setValue(i);
        			}
        			dlgg.setVisible(false);
        			writer.close();
        		} catch (FileNotFoundException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	}
        }
		
	}
	
	public ArrayList<SocioPanelMember> getMemb_list() {
		return memb_list;
	}

	public void setMemb_list(ArrayList<SocioPanelMember> membList) {
		memb_list = membList;
	}

	public SocioAnalysisForm getAnalForm() {
		return analForm;
	}

	public void setAnalForm(SocioAnalysisForm analForm) {
		this.analForm = analForm;
	}
}

class Utils {

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";
    public final static String CSV = "csv";
    public final static String SAV = "sav";
    public final static String TSX = "tsx";
    public final static String XML = "xml";
    
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
class BarUpdater implements Runnable
{
	private JProgressBar bar;
	private int inc;
	public BarUpdater(JProgressBar bar,int increment,int total)
	{
		this.bar = bar;
		this.bar.setMaximum(total);
		this.bar.setValue(0);
		this.inc = increment;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		bar.setValue(bar.getValue()+inc);
	}
}
