package pkg.occupancy.model2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RelDoorArea {

	private int idCustomer;
	private int idDoor;
	private String doorDescription;
	private int _in;
	private int _out;
	private int idArea;
	private boolean reverse;
	
	
	public RelDoorArea() {}
	
	public RelDoorArea(int idDoor, String doorDescription, int _in, int _out) {
		this.idDoor=idDoor;
		this.doorDescription=doorDescription;
		this._in=_in;
		this._out=_out;
	}
	
	
	
	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public int getIdDoor() {
		return idDoor;
	}

	public void setIdDoor(int idDoor) {
		this.idDoor = idDoor;
	}

	public String getDoorDescription() {
		return doorDescription;
	}

	public void setDoorDescription(String doorDescription) {
		this.doorDescription = doorDescription;
	}

	public int get_in() {
		return _in;
	}

	public void set_in(int _in) {
		this._in = _in;
	}

	public int get_out() {
		return _out;
	}

	public void set_out(int _out) {
		this._out = _out;
	}
	
	public int getIdArea() {
		return idArea;
	}

	public void setIdArea(int idArea) {
		this.idArea = idArea;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public static ArrayList<RelDoorArea> getRel(Connection conn, int idCustomer) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<RelDoorArea> list = null;
		try {
			list = new ArrayList<>();
			sql = "select door_type_id id_door, door_description, \n" + 
					"coalesce((select id_area from occ2_rel_door_area where id_door = door_type_id and reverse = false and id_customer = "+idCustomer+") , null, -1) _in,\n" + 
					"coalesce((select id_area from occ2_rel_door_area where id_door = door_type_id and reverse = true and id_customer = "+idCustomer+") , null, -1) _out\n" + 
					"from occ2_doors where enabled = true and id_customer = "+idCustomer+" ";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				list.add(new RelDoorArea(rs.getInt("id_door"), rs.getString("door_description"),
						rs.getInt("_in"), rs.getInt("_out")));
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
		return list;
		
	}
	
	public void add(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = " insert into occ2_rel_door_area (id_area, id_door, reverse, id_customer)" + 
					" values (?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idArea);
		    st.setInt(2, this.idDoor);
		    st.setBoolean(3, this.reverse);
		    st.setInt(4, this.idCustomer);
			// execute the java preparedstatement
		    
		    System.out.println(st.toString());
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
		  String sql = "delete from occ2_rel_door_area where id_area = ? and id_door = ? and id_customer = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idDoor);
	      st.setInt(2, this.idArea);
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
	
	public void deleteAll(Connection conn, int idCustomer) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from occ2_rel_door_area where id_customer = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, idCustomer);
	      
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
