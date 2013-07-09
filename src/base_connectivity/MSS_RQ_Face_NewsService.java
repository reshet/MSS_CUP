package base_connectivity;

public interface MSS_RQ_Face_NewsService {
	public String listFeeds(int IDCategory);
	public String getNewsCache(int IDCategory,int IDFeed);
	public String upNewsCache(int IDFeed);
	public String update_entity(int ID,String title,String desc,String fullT,String enc);
	public String add_entity(int IDfeed,String title,String desc,String fullT,String enc);
	public String delete_entity(int ID);
	public String add_feed(int IDCategory,String name);
	public String delete_feed(int ID);
}
