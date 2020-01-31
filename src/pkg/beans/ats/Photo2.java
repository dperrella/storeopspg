package pkg.beans.ats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Photo2 {
	private String title;
	private String caption;
	private String url;
	
	public Photo2() {	}

	public Photo2(String title, String caption, String url) {
		this.title = title;
		this.caption = caption;
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	public static ArrayList<Photo2> getPhotoByItem(Connection conn, int idItem){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Photo2> photoList = null;
		try {
			photoList = new ArrayList<>();
			sql = "select * from ats.photo where iditem = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idItem);
			rs = st.executeQuery();
			while(rs.next()) {
				photoList.add(new Photo2("", "",rs.getString("url")));
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}	
		return photoList;
	}
	
	
}
