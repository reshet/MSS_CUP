/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

import java.util.ArrayList;

/**
 *
 * @author RX
 */

public class MSS_RQ_XML_Pattern {
    private ArrayList<MSS_Pair> attributes;
    private String nodename;
    /**
     * @return the attributes
     */
    public MSS_RQ_XML_Pattern()
    {
        attributes = new ArrayList<MSS_Pair>(20);
    }
    public ArrayList<MSS_Pair> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(ArrayList<MSS_Pair> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the nodename
     */
    public String getNodename() {
        return nodename;
    }

    /**
     * @param nodename the nodename to set
     */
    public void setNodename(String nodename) {
        this.nodename = nodename;
    }
}
