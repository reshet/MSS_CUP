package base_SP_Management;

public class LocalInterviewElement {
	public String userID;
	public String userPSWD;
	public int index_in_cases_table;
	public LocalInterviewElement(String u_ID,String u_pswd,int index)
	{
		index_in_cases_table = index;
		userID = u_ID;
		userPSWD = u_pswd;
	}
}
