/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author RX
 */

public class MSS_RQ_XML {
    private DocumentBuilderFactory D_Factory;
    private DocumentBuilder D_Builder;
    private Document DOC_XML;
    @SuppressWarnings("unused")
	private String XML_STR;
    public MSS_RQ_XML()
    {
        try {
            D_Factory = DocumentBuilderFactory.newInstance();
            D_Builder = D_Factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MSS_RQ_XML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList<MSS_RQ_XML_Pattern> parseXML(String XML_str)
    {
        ArrayList<MSS_RQ_XML_Pattern> Arr = new ArrayList<MSS_RQ_XML_Pattern>(20);
        try {
        	if (XML_str != null && XML_str.length() !=0)
        	{
        		InputStream stream = new ByteArrayInputStream(XML_str.getBytes("UTF-8"));//works good!!!!
	            //FileWriter flr = new FileWriter(new File("some.somer"));
	            //flr.write(XML_str);
	            //flr.close();
	           
	            /*
	            Scanner scn = new Scanner(new File("some.somer"));
	            StringBuilder newStr = new StringBuilder();
	            while (scn.hasNextLine())
	            {
	            	newStr.append(scn.nextLine());
	            }
	            String str = newStr.toString();
	            char ch = 0x31;
	           // System.out.println(ch);
	            InputStream stream2 = new ByteArrayInputStream(str.getBytes());
	            */
        		//////////training
        		//DOC_XML.createElement("elem").appendChild(DOC_XML.)
        		//D_Builder.
        		//DOC_XML = 
        		///////////
	            DOC_XML = D_Builder.parse(stream);
	            Element root = DOC_XML.getDocumentElement();
	            for (Node childNode = root.getFirstChild();childNode != null;
	                childNode = childNode.getNextSibling())
	            {
	                if (childNode instanceof Element)
	                {
	                     MSS_RQ_XML_Pattern Elem = new MSS_RQ_XML_Pattern();
	                     Elem.setNodename(childNode.getNodeName());
	                     NamedNodeMap attr = childNode.getAttributes();
	                     for (int i = 0; i < attr.getLength();i++)
	                     {
	                        Node attr_node = attr.item(i);
	                        Elem.getAttributes().add(new MSS_Pair(attr_node.getNodeName(), attr_node.getNodeValue()));
	                     }
	                     Arr.add(Elem);
	                }
	            } 
        
        	}
           // Arr.add(XML_str);
            //.getChannel().map(MapMode.READ_WRITE, 0, lengthOfFile);
        } catch (SAXException ex) {
            Logger.getLogger(MSS_RQ_XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MSS_RQ_XML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Arr;
    }
    public ArrayList<MSS_RQ_XML_Pattern> parseXML_Root(String XML_str)
    {
        ArrayList<MSS_RQ_XML_Pattern> Arr = new ArrayList<MSS_RQ_XML_Pattern>(20);
        try {
        	if (XML_str != null && XML_str.length() !=0)
        	{
        		InputStream stream = new ByteArrayInputStream(XML_str.getBytes("UTF-8"));//works good!!!!
	            DOC_XML = D_Builder.parse(stream);
	            Element root = DOC_XML.getDocumentElement(); 
                if (root instanceof Element)
                {
                     MSS_RQ_XML_Pattern Elem = new MSS_RQ_XML_Pattern();
                     Elem.setNodename(root.getNodeName());
                     NamedNodeMap attr = root.getAttributes();
                     for (int i = 0; i < attr.getLength();i++)
                     {
                        Node attr_node = attr.item(i);
                        Elem.getAttributes().add(new MSS_Pair(attr_node.getNodeName(), attr_node.getNodeValue()));
                     }
                     Arr.add(Elem);
                }
        
        	}  
        } catch (SAXException ex) {
            Logger.getLogger(MSS_RQ_XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MSS_RQ_XML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Arr;
    }
}
