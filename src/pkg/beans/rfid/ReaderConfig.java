package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReaderConfig implements CRUD {
	private int idreader;
	private String dsreader;
	private int idsite;
	private String dssite;
	private int idstep;
	private String dsstep;
	
	public ReaderConfig() {	}

	public ReaderConfig(int idreader, String dsreader, int idsite, String dssite, int idstep, String dsstep) {
		this.idreader = idreader;
		this.dsreader = dsreader;
		this.idsite = idsite;
		this.dssite = dssite;
		this.idstep = idstep;
		this.dsstep = dsstep;
		
	}
	
	public int getIdreader() {
		return idreader;
	}
	public void setIdreader(int idreader) {
		this.idreader = idreader;
	}
	
	public String getDsreader() {
		return dsreader;
	}
	public void setDsreader(String dsreader) {
		this.dsreader = dsreader;
	}
	
	public int getIdsite() {
		return idsite;
	}
	public void setIdsite(int idsite) {
		this.idsite = idsite;
	}
	
	public String getDssite() {
		return dssite;
	}
	public void setDssite(String dssite) {
		this.dssite = dssite;
	}
	
	public int getIdstep() {
		return idstep;
	}
	public void setIdstep(int idstep) {
		this.idstep = idstep;
	}
	
	public String getDsstep() {
		return dsstep;
	}
	public void setDsstep(String dsstep) {
		this.dsstep = dsstep;
	}
	
	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select r.* , p.description dsreader, s.description dssite, t.description dsstep from rel_reader_site r "
					+ "join readers p on p.id = r.idreader "
					+ "join sites s on s.id = r.idsite "
					+ "join step t on t.idstep = r.idstep "
					+ "where r.idreader = ? and r.idsite = ? and r.idstep = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idreader);
			st.setInt(2, idsite);
			st.setInt(3, idstep);
			
			rs = st.executeQuery();
			if(rs.next()) {
				setDsreader(rs.getString("dsreader"));
				setDssite(rs.getString("dssite"));
				setDsstep(rs.getString("dsstep"));				
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
	}
	
	
	public static ArrayList<ReaderConfig> getConfigByIdreader(Connection conn, int idreader){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<ReaderConfig> configList = null;
		try {
			configList = new ArrayList<>();
			sql = "select r.* , p.description dsreader, s.description dssite, t.description dsstep "
					+ "from rel_reader_site r "
					+ "join readers p on p.id = r.idreader "
					+ "join sites s on s.id = r.idsite "
					+ "join step t on t.idstep = r.idstep "
					+ "where r.idreader = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idreader);
			rs = st.executeQuery();
			while(rs.next()) {
				configList.add(new ReaderConfig(rs.getInt("idreader"), rs.getString("dsreader"), 
						rs.getInt("idsite"), rs.getString("dssite"), rs.getInt("idstep"), rs.getString("dsstep")));
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
		return configList;
	}
	
	
	@Override
	public void add(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = " insert into rel_reader_site (idreader, idsite, idstep)" + 
					" values (?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idreader);
			st.setInt(2, this.idsite);
			st.setInt(3, this.idstep);
			
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

	
	@Override
	public void delete(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from rel_reader_site where idreader = ? and idsite = ? and idstep = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idreader);
	      st.setInt(2, this.idsite);
	      st.setInt(3, this.idstep);
	      
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

	@Override
	public void update(Connection conn) {
		// TODO Auto-generated method stub
		
	}

}
