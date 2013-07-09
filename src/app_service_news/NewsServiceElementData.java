package app_service_news;

import java.awt.Component;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.lowagie.text.pdf.codec.Base64;

public class NewsServiceElementData extends Component implements ListElementData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	private String header,preview,fulltext;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	private int ID;
	public NewsServiceElementData(){}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getPreview() {
		return preview;
	}
	public void setPreview(String preview) {
		this.preview = preview;
	}
	public String getFulltext() {
		return fulltext;
	}
	public void setFulltext(String fulltext) {
		this.fulltext = fulltext;
	}
	@Override
	public void initialize(Object[] params) {
		 String img_coded = (params.length>=6)?(String)params[5]:null;
         Image img = null;
         if (img_coded != null)
				try {
					img = ImageIO.read(new ByteArrayInputStream(Base64.decode(img_coded)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		int ID = Integer.parseInt((String)params[1]);		
		String header = (String)params[2];
		String preview = (String)params[3];
		String fulltext = (String)params[4];
		this.image = img;
		this.ID = ID;
		this.header = header;
		this.preview = preview;
		this.fulltext = fulltext;
	}
}
