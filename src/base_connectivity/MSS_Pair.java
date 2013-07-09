/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

/**
 *
 * @author RX
 */
public class MSS_Pair {
    private String name;
    private String value;

    public MSS_Pair(String name,String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
