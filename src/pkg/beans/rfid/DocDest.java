package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DocDest {
	private int idCustomer;
	private int idStep;
	private String origin;
	private String destination;
	
	private String stepDesc;
	
	public DocDest() {	}

	public DocDest(int idCustomer, int idStep, String origin, String destination, String stepDesc) {
		this.idCustomer = idCustomer;
		this.idStep = idStep;
		this.origin = origin;
		this.destination = destination;
		this.stepDesc = stepDesc;
		
	}
	
	
	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public int getIdStep() {
		return idStep;
	}

	public void setIdStep(int idStep) {
		this.idStep = idStep;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getStepDesc() {
		return stepDesc;
	}

	public void setStepDesc(String stepDesc) {
		this.stepDesc = stepDesc;
	}

	public static ArrayList<DocDest> getDestByIdCustomer(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<DocDest> siteList = null;
		try {
			siteList = new ArrayList<>();
			sql = "select dd.*, s.description from documents_destination dd "
					+ "join step s on s.idstep = dd.idstep where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				siteList.add(new DocDest(rs.getInt("id_customer"), rs.getInt("idstep"), rs.getString("origin"), 
						rs.getString("destination"), rs.getString("description")));
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
		return siteList;
	}
	
	public void add(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = " insert into documents_destination (id_customer, idstep, origin, destination)" + 
					" values (?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idCustomer);
			st.setInt(2, this.idStep);
			st.setString(3, this.origin);
	        st.setString(4, this.destination);
			// execute the java preparedstatement
			st.executeUpdate();
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
	}

	
	public void delete(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from documents_destination where id_customer = ? and idstep = ? and origin = ? and destination = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idCustomer);
	      st.setInt(2, this.idStep);
	      st.setString(3, this.origin);
	      st.setString(4, this.destination);
	      
	      // execute the java preparedstatement
	      st.executeUpdate();
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
	}

	
}
