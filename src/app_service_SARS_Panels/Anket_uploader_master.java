package app_service_SARS_Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import app_service_news.NewsServiceElementData;
import base_connectivity.MSS_RQ_Admin;
import base_connectivity.MSS_RQ_CxListFiller;
import base_connectivity.MSS_RQ_Request;
import base_connectivity.MSS_RQ_TableDescriptor;
import base_connectivity.MSS_RQ_User;
import base_connectivity.MSS_RQ_XMLtoTableDescriptor;
import base_gui.ToolMainWidget;

import com.explodingpixels.macwidgets.HudWindow;
import com.lowagie.text.pdf.codec.Base64;

@SuppressWarnings("serial")
public class Anket_uploader_master extends JPanel{
	private JButton load,deploy;
	private JPanel toolbar;
	private JTextArea field;
	private MSS_RQ_Admin reqHandler;
	private JFileChooser chooser;
	public Anket_uploader_master()
	{
		setLayout(new BorderLayout(3,3));
		field = new JTextArea(10,50);
		field.setLineWrap(true);
		field.setTabSize(10);
		load = new JButton(new load_Action());
		deploy = new JButton(new deploy_Action());
		reqHandler = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
		chooser = new JFileChooser();
		chooser.setFileFilter(new AnketFileFilter());
		toolbar = new JPanel();
		toolbar.add(load);toolbar.add(deploy);
		add(new JScrollPane(field),BorderLayout.CENTER);
		add(toolbar,BorderLayout.SOUTH);
	}
	private class deploy_Action extends AbstractAction
	{
		//private int ID;
		public deploy_Action()
		{
			putValue(NAME, "В репозитарий!");
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					String prep_task_key = field.getText();
					String prep_coded = prep_task_key;
					/*
					String prep_coded = null;
					try 
					{
						prep_coded = new String(prep_task_key.getBytes(),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					*/
					if (prep_coded != null)
					{
						 prep_coded = new String(Base64.encodeBytes(prep_coded.getBytes()));
						String anket_name = JOptionPane.showInputDialog("Имя новой анкеты:");
						 String xmlans = MSS_RQ_Request.http_request(reqHandler.createTask(prep_coded,anket_name, 100030,2),ToolMainWidget.URL);
						 MSS_RQ_TableDescriptor NewsTDesc = new MSS_RQ_TableDescriptor(new String[]{"№"},
									new Class[]{Integer.class});
						    MSS_RQ_XMLtoTableDescriptor NewsT_XML_Desc = new MSS_RQ_XMLtoTableDescriptor(new String[]{"ID"});
						    MSS_RQ_CxListFiller NewsUpdater = new MSS_RQ_CxListFiller(NewsTDesc,NewsT_XML_Desc,NewsServiceElementData.class);		   
						String newID = NewsUpdater.requestLocalData_FromRoot(xmlans, 1);
						MSS_RQ_User reqHandler_usr = new MSS_RQ_User("Tool", "10000", "mysecret");
						@SuppressWarnings("unused")
						String ans1 = MSS_RQ_Request.http_request(reqHandler.shareTask(Integer.parseInt(newID)),ToolMainWidget.URL);
						@SuppressWarnings("unused")
						String ans2 = MSS_RQ_Request.http_request(reqHandler_usr.subscribeTask(Integer.parseInt(newID)), ToolMainWidget.URL);	
						//System.out.println();
					}
				}
			}).start();
		}
	}
	private class load_Action extends AbstractAction
	{
		private HudWindow dlg;
		public load_Action()
		{
			putValue(NAME, "Загрузить анкету...");
			dlg = new HudWindow("Выберите файл анкеты...");
			dlg.getJDialog().setPreferredSize(new Dimension(300,400));
			dlg.getJDialog().setBackground(new Color(80,80,80));
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			int returnVal = chooser.showOpenDialog(dlg.getJDialog());

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = chooser.getSelectedFile();
	            try {
	            	FileInputStream strm = new FileInputStream(file);
	            	byte[] arr;
					try {
						arr = new byte[1000000];
						int length = strm.read(arr);
						//strm.
						byte[] arr2 = new byte[length];
						for(int i = 0;i<length;i++)
						{
							arr2[i] = arr[i];
						}
						String ankk = new String(arr2,"UTF-8");
						System.out.println("ANK_BYTED:"+ankk);
						String anket = ankk;
						anket = anket.replaceAll("vertexNAME", "vertex NAME");
						anket = anket.replaceAll("filename", "file name");			
						anket = anket.replaceAll("\"DESCR", "\" DESCR");			
						anket = anket.replaceAll("\"ID", "\" ID");			
						anket = anket.replaceAll("\"COLOR", "\" COLOR");
						anket = anket.replaceAll("\"version", "\" version");
						anket = anket.replaceAll("\"content", "\" content");
						
						field.setText(anket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	//Scanner scn = new Scanner(file);
					//StringBuilder bld = new StringBuilder();
					/*
					while(scn.hasNextLine())
					{
						bld.append(scn.nextLine());
					}
					String anket = bld.toString();
					System.out.println("ANKET:"+anket);
					*/
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
	            //This is where a real application would open the file.
	            //log.append("Opening: " + file.getName() + "." + newline);
	        } else {
	            //log.append("Open command cancelled by user." + newline);
	        }
		}
	}
}

class Utils {
    public final static String TSX = "tsx";
    public final static String T = "t";
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
class AnketFileFilter extends FileFilter
{
	@Override
	public String getDescription() {
		return "Файлы анкет SARS";
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
}
