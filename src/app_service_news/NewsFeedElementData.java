package app_service_news;

import java.awt.Component;

public class NewsFeedElementData extends Component implements ListElementData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Image image;
	private String name;
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	private int ID;
	public NewsFeedElementData(){}
	/*
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}*/
	@Override
	public void initialize(Object[] params) {
		int ID = Integer.parseInt((String)params[1]);		
		String name = (String)params[2];
		this.ID = ID;
		this.name = name;
	}
}
