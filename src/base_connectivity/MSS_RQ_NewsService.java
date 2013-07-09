package base_connectivity;


public class MSS_RQ_NewsService implements MSS_RQ_Face_NewsService,Service_Invokation_Handler{
    private String prepRQ;
    private String endofRQ;
    // Not yet have a sense
	private String ME = "MSSTool";
	private String userID = "10000";
	@SuppressWarnings("unused")
	private String pswd = "mysecret";
	//private int id_Category;
	//----------
	public MSS_RQ_NewsService()
	{
	//	this.id_Category = id_cat;
		 upprepRQ();
	}
	public void upprepRQ()
    {
           this.prepRQ = "<RQ SERVICE = \"news\" ME = \"" +
                this.ME+
                "\" SCREEN = \"400\" USERID = \""+
                this.userID+
                "\">";
          this.endofRQ = "/></RQ>";
    }
	@Override
	public String getNewsCache(int IDCategory, int IDFeed) {
		return prepRQ+
        "<vertex name = \""+String.valueOf(IDCategory)+"\""+" feed = \""+String.valueOf(IDFeed)+"\" "
        +endofRQ;
	}
	@Override
	public String listFeeds(int IDCategory) {
		return prepRQ+
        "<vertex name = \""+String.valueOf(IDCategory)+"\""+" feed = \"0\" "
        +endofRQ;
	}
	@Override
	public String upNewsCache(int IDFeed) {
		return prepRQ+
        "<vertex name = \"0\" feed = \""+String.valueOf(IDFeed)+"\" "
        +endofRQ;
	}
	@Override
	public String update_entity(int ID, String title, String desc,
			String fullT, String enc) {
		if (enc == null) enc = "No";
		return prepRQ+
        "<vertex method = \"update_entity\" ID = \""+String.valueOf(ID)+"\" "+
        "title = \""+title+"\" "+
        "description = \""+desc+"\" "+
        "fulltext = \""+fullT+"\" "+
        "enc = \""+enc+"\" "
        +endofRQ;
	}
	@Override
	public String add_entity(int IDfeed, String title, String desc,
			String fullT, String enc) {
		if (enc == null) enc = "No";
		return prepRQ+
        "<vertex name = \"10\" feed = \""+String.valueOf(IDfeed)+"\" method = \"add_entity\" "+
        "title = \""+title+"\" "+
        "description = \""+desc+"\" "+
        "fulltext = \""+fullT+"\" "+
        "enc = \""+enc+"\" "
        +endofRQ;
	}
	@Override
	public String delete_entity(int ID) {
		return prepRQ+
        "<vertex method = \"delete_entity\" ID = \""+String.valueOf(ID)+"\" "
        +endofRQ;
	}
	@Override
	public String add_feed(int ID_Cat,String name) {
		return prepRQ+
        "<vertex name = \""+String.valueOf(ID_Cat)+"\" method = \"add_newsfeed\" "+
        "title = \""+name+"\" "
        +endofRQ;	}
	@Override
	public String delete_feed(int ID) {
		return prepRQ+
        "<vertex feed = \""+String.valueOf(ID)+"\" method = \"delete_feed\" "
        +endofRQ;
	}
}
