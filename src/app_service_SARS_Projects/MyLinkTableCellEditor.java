package app_service_SARS_Projects;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.AbstractCellEditor;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.jdesktop.swingx.JXHyperlink;

public class MyLinkTableCellEditor extends AbstractCellEditor implements TableCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3642414324068332819L;
	public MyLinkTableCellEditor()
	{
		//cbox = new JCheckBox();
		//cbox.setBackground(new Color(90,90,90));
		//cbox.setAlignmentX(Component.CENTER_ALIGNMENT);
		//reqHandlerAdmin = new MSS_RQ_Admin("Tool", ToolMainWidget.login, ToolMainWidget.pswd);
		link.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String browserPath = "C:/Program Files (x86)/Mozilla Firefox/firefox.exe"; //Use your browser path
				try {
					String[] b = {browserPath, link.getText()};
					Runtime.getRuntime().exec(b);
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
	}
	private JXHyperlink link = new JXHyperlink();
	private String param;
	private void prepareLink()
	{
		String [] p_arr = param.split(":");
		String lo = p_arr[0];
		String la = p_arr[1];
		
		String link = "http://mplatforma.com:82/MSS/GEO_locator?lo="+lo+"&la="+la;
		this.link.setText(link);
	}
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int col) {
		param = (String)value;
		prepareLink();
		//link.setText((String)value);
		
		//JOptionPane.showInputDialog("YES!");
		return link;
	}
	@Override
	public Object getCellEditorValue() {
		return param;
	}
}