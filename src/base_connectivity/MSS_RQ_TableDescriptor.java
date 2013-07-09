/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

/**
 * Class for descripting data coloumns alias to XML fields
 * @author RX
 */
public class MSS_RQ_TableDescriptor {
private String[] descriptor;
@SuppressWarnings("rawtypes")
private Class[] class_descriptor;
public MSS_RQ_TableDescriptor(String [] desc, @SuppressWarnings("rawtypes") Class[] class_desc)
{
    descriptor = desc;
    class_descriptor = class_desc;
}
public String [] getDescription()
{
    return descriptor;
}
@SuppressWarnings("rawtypes")
public Class [] getClassDescription()
{
    return class_descriptor;
}
}
