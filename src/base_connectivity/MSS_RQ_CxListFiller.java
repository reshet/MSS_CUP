package base_connectivity;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import app_service_news.ListElementData;

/**
 *
 * @author RX
 */
public class MSS_RQ_CxListFiller {
private MSS_RQ_XML xmlparser;
@SuppressWarnings("unused")
private JList list;
private String RQ_STR;
@SuppressWarnings("unused")
private String [] descriptor, xml_descr;
private ArrayList<MSS_RQ_XML_Pattern> DATA;
private DefaultListModel model;
private Class<? extends ListElementData> modelElement;
    public MSS_RQ_CxListFiller(MSS_RQ_TableDescriptor desc,MSS_RQ_XMLtoTableDescriptor desc_xml, Class<? extends ListElementData> class1) {
        xmlparser = new MSS_RQ_XML();
        descriptor = desc.getDescription();
        xml_descr = desc_xml.getDescription();
        model = new DefaultListModel();   
        modelElement = class1;
    }

    MSS_RQ_CxListFiller(MSS_RQ_TableDescriptor desc) {
        xmlparser = new MSS_RQ_XML();
        descriptor = desc.getDescription();
        xml_descr = desc.getDescription();
        model = new DefaultListModel();
    }
	public void updateData(String RQ)
    {
        model = new DefaultListModel();
        RQ_STR = RQ;
        DATA = xmlparser.parseXML(RQ_STR);
        int i = 1;
        for (MSS_RQ_XML_Pattern cortage:DATA)
        {
            Vector<String> row = new Vector<String>(cortage.getAttributes().size());
            ArrayList<MSS_Pair> rower = new ArrayList<MSS_Pair>(cortage.getAttributes().size());
            row.add(String.valueOf(i++));
            for (MSS_Pair str: cortage.getAttributes())
            {
                int pos = 0;//Arrays.binarySearch(xml_descr, 0, xml_descr.length, str.getName());
                for (int j = 0; j < xml_descr.length;++j)
                {
                    if (xml_descr[j].compareTo(str.getName()) == 0){pos = j;break;}
                }
                rower.add(new MSS_Pair(String.valueOf(pos),str.getValue()));
            }
            int posit = 0;
            int ower = 0;
            while (posit < xml_descr.length && ower <= rower.size())
                for(MSS_Pair elem: rower)
                {
                    if (elem.getName().compareTo(String.valueOf(posit)) == 0) {row.add(elem.getValue());posit++;ower = 0;}
                    else ower++;
                }
			ListElementData elemData;
			try {
				elemData = modelElement.newInstance();
				Object [] param = row.toArray();
				elemData.initialize(param);
	            model.addElement(elemData);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
        }
    }
    public String requestLocalData(String RQ, int N)
    {
        RQ_STR = RQ;
        DATA = xmlparser.parseXML(RQ_STR);
        MSS_RQ_XML_Pattern cortage = DATA.get(0);
        Vector<String> row = new Vector<String>(cortage.getAttributes().size());
        for (MSS_Pair str: cortage.getAttributes())row.add(str.getValue());
        return row.get(N);
    }
    public String requestLocalData_FromRoot(String RQ, int N)
    {
        RQ_STR = RQ;
        DATA = xmlparser.parseXML_Root(RQ_STR);
        MSS_RQ_XML_Pattern cortage = DATA.get(0);
        Vector<String> row = new Vector<String>(cortage.getAttributes().size());
        for (MSS_Pair str: cortage.getAttributes())row.add(str.getValue());
        return row.get(N);
    }
    public void setLocalData(String data,int index)
    {
        model.set(index, data);
    }
    public void fillCxList(JList list)
    {   
        list.setModel(model);
        //table.repaint();
    }
}

