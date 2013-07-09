/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author RX
 */
public class MSS_RQ_TableFiller {
private MSS_RQ_XML xmlparser;
@SuppressWarnings("unused")
private JTable table;
private String RQ_STR;
private String [] descriptor, xml_descr;
@SuppressWarnings("rawtypes")
private Class [] class_desc;
private ArrayList<MSS_RQ_XML_Pattern> DATA;
private DefaultTableModel model;
    public MSS_RQ_TableFiller(MSS_RQ_TableDescriptor desc,MSS_RQ_XMLtoTableDescriptor desc_xml) {
        xmlparser = new MSS_RQ_XML();
        descriptor = desc.getDescription();
        xml_descr = desc_xml.getDescription();
        class_desc = desc.getClassDescription();
        model = new DefaultTableModel(descriptor, 0);   
    }

    MSS_RQ_TableFiller(MSS_RQ_TableDescriptor desc) {
        xmlparser = new MSS_RQ_XML();
        descriptor = desc.getDescription();
        xml_descr = desc.getDescription();
        model = new DefaultTableModel(descriptor, 0);
    }
    public void updateData(String RQ)
    {
        model = new DefaultTableModel(descriptor, 0);
        RQ_STR = RQ;
        DATA = xmlparser.parseXML(RQ_STR);
        int i = 1;
        for (MSS_RQ_XML_Pattern cortage:DATA)
        {
            Vector<String> row = new Vector<String>(cortage.getAttributes().size()+1);
            ArrayList<MSS_Pair> rower = new ArrayList<MSS_Pair>(cortage.getAttributes().size());
            row.add(String.valueOf(i++));
            for (MSS_Pair str: cortage.getAttributes())
            {
                int pos = 0;//Arrays.binarySearch(xml_descr, 0, xml_descr.length, str.getName());
                for (int j = 0; j < xml_descr.length;++j)
                {
                    if (xml_descr[j].compareTo(str.getName()) == 0 
                    //for CAPI flex attribute Q || 
                    //(xml_descr[j].startsWith("Q") && xml_descr[j].compareTo(str.getName().substring(0, 1)) == 0)
                    )
                    {
                    	pos = j;break;
                    }
                }
                String val = str.getValue();
                @SuppressWarnings("unused")
				String name = str.getName();
                rower.add(new MSS_Pair(String.valueOf(pos),val));
            }
            int posit = 1;
            int ower = 0;
            @SuppressWarnings("unused")
			int skipped = 0;
            while (posit < xml_descr.length 
            		//&& skipped <= rower.size()
            		)
            {
                for(MSS_Pair elem: rower)
                {
                    if (elem.getName().compareTo(String.valueOf(posit)) == 0)
                    	{
                    		row.add(elem.getValue());posit++;ower = 0;
                    	}
                    else ower++;
                }
                if (ower + 1 >= rower.size())
                	{
                		ower = 0;
                		row.add("");
                		skipped++;
                		posit++;
                	}
            } 
            model.addRow(CastRowElements(row));
        }
    }
    private Vector<Object> CastRowElements(Vector<String> row)
    {
    	int posit = 0;
    	Vector<Object> raw = new Vector<Object>(row.size());
        while (posit < row.size() && posit < class_desc.length)
        {
        	String element = row.get(posit);
        	Object outer = element;
        	if (class_desc[posit].equals(Boolean.class))
        	{
        		if (element.equals("0"))outer = false;else outer = true;
          	}
        	raw.add(outer);
        	posit++;
        }
		return raw;
    }
    public String requestLocalData(String RQ, int N)
    {
        RQ_STR = RQ;
        DATA = xmlparser.parseXML(RQ_STR);
        if (DATA.size()!=0)
        {
        	MSS_RQ_XML_Pattern cortage = DATA.get(0);
        	Vector<String> row = new Vector<String>(cortage.getAttributes().size());
        	for (MSS_Pair str: cortage.getAttributes())row.add(str.getValue());
        	return row.get(N);
        }
        return null;
    }
    public void setLocalData(String data,int row, int col)
    {
        model.setValueAt(data, row, col);
    }
    public void fillTable(JTable tbl)
    {   
    	@SuppressWarnings("unused")
		DefaultTableModel prevModel = (DefaultTableModel) tbl.getModel();
    	boolean eqls = true;
    	int r1,r2,c1,c2;
    	r1  = tbl.getRowCount();
    	r2 = model.getRowCount();
    	c1 = tbl.getColumnCount();
    	c2 = model.getColumnCount();
    	if (c1 !=c2 || r1 != r2) eqls = false;
    	
    	if (eqls)
	    	for (int i = 0; i < r1;i++)
			{ 
	    		if (!eqls) break;
				for(int j = 0; j < c1;j++)
				{
					//System.out.println(eqls);
					if ( model.getValueAt(i, j) != null &&
							!model.getValueAt(i, j).equals(tbl.getValueAt(i, j))) 
						{
							//System.out.println(i+"+"+j);
							eqls = false;
							break;
						}
				}
			}
    	if (eqls)
    	{
    		
    	} else
    	{
    		tbl.setModel(model);
            prepareTableRenderers(tbl);
    	}
        //table.repaint();
    }
    private void prepareTableRenderers(JTable tbl)
    {
    	TableColumnModel tcol = tbl.getColumnModel();
    	TableCellEditor TCEditor =  new MyBooleanTableCellEditor();
		TableCellRenderer TCRenderer = new MyBooleanTableCellRenderer();
    	int class_count = class_desc.length;
    	//int col_count = tbl.getColumnCount();
        for (int i = 0; i < tcol.getColumnCount() && i < class_count;i++)
		{
			if(class_desc[i].equals(Boolean.class))
			{
				tcol.getColumn(i).setCellEditor(TCEditor);
				tcol.getColumn(i).setCellRenderer(TCRenderer);
			}
			tcol.getColumn(i).setPreferredWidth(descriptor[i].length());
		}
        tbl.setColumnModel(tcol);
    }

}




