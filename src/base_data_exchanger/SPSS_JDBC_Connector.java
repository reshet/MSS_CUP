package base_data_exchanger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;

public class SPSS_JDBC_Connector {
	private SOAD dbMgr;
	public SPSS_JDBC_Connector(String sav_file) {
		String conn_str = "jdbc:spssstatistics://localhost:18886;ServerDatasource=SAVDB;" +
				"CustomProperties=(CONNECT_STRING="+sav_file+";UserMissingIsNull=1;MissingDoubleValueAsNAN=1)";
		dbMgr = new SOAD(conn_str, "admin", "pswd");
	}
	public SOAD getDBManeger()
	{
		return dbMgr;
	}
	//private DefaultTableModel Properties,Variables,
	public void prepare_new()
	{
		String[] queries = new String[]
		{
				"CREATE TABLE Properties (Encoding VARCHAR NOT NULL);",
				"CREATE TABLE Variables ("+
						"VarName VARCHAR NOT NULL,"+
						"Label VARCHAR,"+
						"IsWeightVar BIT ( 1 ) NOT NULL,"+
						"Format SMALLINT NOT NULL,"+
						"Width INTEGER NOT NULL,"+
						"Decimals INTEGER NOT NULL,"+
						"WriteFormat SMALLINT NOT NULL,"+
						"WriteWidth SMALLINT NOT NULL,"+
						"WriteDecimals SMALLINT NOT NULL,"+
						"Alignment SMALLINT NOT NULL,"+
						"MeasLevel SMALLINT,"+
						"MvCode SMALLINT,"+
						"Role SMALLINT,"+
						"NMissingValue1 DOUBLE PRECISION,"+
						"NMissingValue2 DOUBLE PRECISION,"+
						"NMissingValue3 DOUBLE PRECISION,"+
						"SMissingValue1 VARCHAR ( 8 ),"+
						"SMissingValue2 VARCHAR ( 8 ),"+
						"SMissingValue3 VARCHAR ( 8 ),"+
						"DMissingValue1 DATE,"+
						"DMissingValue2 DATE,"+
						"DMissingValue3 DATE NOT NULL,"+
						"Position INTEGER,"+
						"Type INTEGER,"+
						"ValueLabelTableName VARCHAR,"+
						"CONSTRAINT TC_MeasLevel CHECK (MeasLevel >= 0 && MeasLevel <= 3),"+
						"CONSTRAINT TC_MvCode CHECK (MvCode == -3 || MvCode ==-2 || MvCode ==0 || MvCode == 1 ||"+
						"MvCode ==2 || MvCode ==3 ),"+
						"CONSTRAINT TC_Type CHECK (Type>= 0 && Type <= 32767),"+
						"CONSTRAINT TC_Alignment CHECK (Alignment >=0 && Alignment <= 2)"+
						");",
						
						"CREATE TABLE VLVAR<var_name>* ("+
						"<var_name> VARCHAR NOT NULL,"+
						"<var_name>_label VARCHAR NOT NULL"+
						");",
						
						"CREATE TABLE Attributes ("+
						"AttributeName VARCHAR NOT NULL,"+
						"AttributeTableId INTEGER NOT NULL"+
						");",
						
						"CREATE TABLE VarAttributes ("+
						"VarName VARCHAR NOT NULL,"+
						"AttibuteName VARCHAR NOT NULL,"+
						"AttributeTableId INTEGER NOT NULL"+
						");",
						
						"CREATE TABLE AttributeValues ("+
						"AttributeTableId INTEGER NOT NULL,"+
						"Number SMALLINT NOT NULL,"+
						"Value VARCHAR ( 128 ) NOT NULL,"+
						"CONSTRAINT PK_Id_Number PRIMARY KEY (AttributeTableId, Number)"+
						");",
						
						"CREATE TABLE MrSets ("+
						"Name VARCHAR NOT NULL,"+
						"Label VARCHAR NOT NULL,"+
						"Type SMALLINT NOT NULL,"+
						"TableId INTEGER NOT NULL,"+
						"NConstant DOUBLE PRECISION NOT NULL,"+
						"Sconstant VARCHAR ( 128 ) NOT NULL"+
						");",
						
						"CREATE TABLE MrSetVariables ("+
						"TableId SMALLINT NOT NULL,"+
						"VarName VARCHAR NOT NULL"+
						");",
						
						"CREATE TABLE Cases ("+
						"RECORD_NUM INTEGER NOT NULL,"+
						"<var_name> VARCHAR NOT NULL"+
						");",
						
						"CREATE TABLE CasesView ("+
						"RECORD_NUM INTEGER NOT NULL,"+
						"<var_name> VARCHAR NOT NULL"+
						");",
						
						"CREATE TABLE VarSets ("+
						"Name VARCHAR NOT NULL,"+
						"Label VARCHAR,"+
						"TableName VARCHAR NOT NULL,"+
						"ViewTableName VARCHAR NOT NULL"+
						");",
						
						"CREATE TABLE VARSETCASES<set_name>* ("+
						"RECORD_NUM INTEGER NOT NULL,"+
						"<var_name> VARCHAR NOT NULL"+
						");",
						
						"CREATE TABLE VARSETCASESVIEW<set_name>* ("+
						"RECORD_NUM INTEGER NOT NULL,"+
						"<var_name> VARCHAR NOT NULL"+
						");",
						
						"CREATE TABLE Extensions ("+
						"Number SMALLINT NOT NULL,"+
						"Content VARCHAR NOT NULL"+
						");",
						
						"CREATE TABLE TrendsInfo ("+
						"Position SMALLINT NOT NULL,"+
						"Name VARCHAR NOT NULL,"+
						"Type SMALLINT NOT NULL,"+
						"Period INTEGER NOT NULL"+
						");"
		};
		for (int i = 0;i < queries.length;i++)
		{
			System.out.println(queries[i]);
			dbMgr.DB_Writer(queries[i]);
		}
	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author RX
 */
class SOAD {
	//jdbc:spssstatistics://localhost:18886;ServerDatasource=SAVDB;
	//CustomProperties=(CONNECT_STRING=/home/user/data/Employee data.sav;UserMissingIsNull=1;
	//MissingDoubleValueAsNAN=1)
    private String dbName = "jdbc:mysql://localhost:3306/javaee";
    private String dbQuery;
    //
    //private String dbDriver = "com.mysql.jdbc.Driver";
    private String dbDriver = "com.spss.statistics.datafile.jdbc.openaccess.OpenAccessDriver";
    String userid = "root";
    String password = "mypassword";
    Connection connection;
    Statement statement;
    public SOAD(String db_Name,String db_user,String db_pswd)
    {
        userid = db_user;
        password = db_pswd;
        dbName = db_Name;
        try
        {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(dbName);
            statement = connection.createStatement();
        }
        catch (SQLException ex) {
            Logger.getLogger(SOAD.class.getName()).log(Level.SEVERE, null, ex);
        }        catch (ClassNotFoundException ex) {
            Logger.getLogger(SOAD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @PreDestroy
    public void release()
    {
        try {
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SOAD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean DB_Writer(String query)
    {
        try {
            if (query != null){
            	statement.executeUpdate(query);
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(SOAD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public String DB_Query(String query)
    {
        try {
            if (query != null)statement.executeUpdate(query);
                else{
                	@SuppressWarnings("unused")
					 ResultSet set = statement.executeQuery(dbQuery);
                    return "OK!";
                }
        }
        catch (SQLException ex) {
            Logger.getLogger(SOAD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error on ezecuting (my comment)";
    }
    public ArrayList<ArrayList<String>> DB_Reader(String query)
    {
        ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>(200);
        try {
            ResultSet rs = statement.executeQuery(query);
            ResultSetMetaData meta = (ResultSetMetaData) rs.getMetaData();
            int col_c = meta.getColumnCount();
            ArrayList<String> arr_colms = new ArrayList<String>(10);
        	for (int i = 0;i < col_c;i++)
        	{
        		arr_colms.add(meta.getColumnName(i+1));
        	}
        	arr.add(arr_colms);
            while(rs.next())
            {
            	ArrayList<String> arr_s = new ArrayList<String>(10);
            	for (int i = 0;i < col_c;i++){arr_s.add(rs.getString(meta.getColumnName(i+1)));}
                arr.add(arr_s);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(SOAD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
}
