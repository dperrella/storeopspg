package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Flow implements CRUD {

	private int idflow;
	private String description;
	private int idCustomer;
	private ArrayList<Step> steps;
	
	public Flow() {}
	
	public Flow(int idflow, String description, int idCustomer, ArrayList<Step> selfSteps) {
		this.idflow=idflow;
		this.description=description;
		this.idCustomer=idCustomer;
		this.steps = selfSteps;
	}
	
	public int getIdflow() {
		return idflow;
	}
	
	public void setIdflow(int idflow) {
		this.idflow = idflow;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getIdCustomer() {
		return idCustomer;
	}
	
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	
	public ArrayList<Step> getSteps() {
		return steps;
	}

	public void setSteps(ArrayList<Step> selfSteps) {
		this.steps = selfSteps;
	}

	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from flow where idflow = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idflow);
			rs = st.executeQuery();
			if(rs.next()) {
				
				setDescription(rs.getString("description"));
				setIdCustomer(rs.getInt("id_customer"));
				setSteps(Step.getStepByIdFlow(conn, this.idflow));
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
	
	public static ArrayList<Flow> getFlowByIdCustomer(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Flow> flowList = null;
		try {
			flowList = new ArrayList<>();
			sql = "select * from flow where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				flowList.add(new Flow(rs.getInt("idflow"), rs.getString("description"),
						             rs.getInt("id_customer"), Step.getStepByIdFlow(conn, rs.getInt("idflow"))));
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
		return flowList;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update flow set description = ? where idflow = ?";
	      st = conn.prepareStatement(sql);
	      st.setString(1, description);
	      st.setInt(2, idflow);
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
	public void add(Connection conn){
		PreparedStatement st = null;
		try{
		  String sql = " insert into flow (description, id_customer)" + 
		  		       " values (?,?)";
	      st = conn.prepareStatement(sql);
	      st.setString(2, this.description);
	      st.setInt(3, this.idCustomer);
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
	public void delete(Connection conn){
		PreparedStatement st = null;
		try{
		  String sql = "delete from flow where idflow = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, idflow);
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


	public int insertFlow(Connection conn){
		PreparedStatement st = null;
		ResultSet rs = null;
		int idflow = 0;
		try{
		  String sql = " insert into flow (description, id_customer)" + 
		  		       " values (?,?) returning idflow ";
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.description);
	      st.setInt(2, this.idCustomer);
	      // execute the java preparedstatement
	      rs = st.executeQuery();
	      
	      if(rs.next()) {
	    	  idflow = rs.getInt("idflow");
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
		return idflow;
	}
}
