package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pkg.beans.rfid.Site;

public class StoreTicket {

	private Integer id;
	private Integer idStore;
	private String callId;
	private String chargeCode;
	private String sapCode;
	private Date openDate;
	private Date closeDate;
	private String callDescriptionShort;
	private String callDescriptionLong;
	private Boolean exported;
	private Boolean open;
	
	public StoreTicket() {}
	
	public StoreTicket(Integer id, Integer idStore, String callId, String chargeCode,
			String sapCode, Date openDate, Date closeDate, String callDescriptionShort,
			String callDescriptionLong, Boolean exported, Boolean open) {
		this.id=id;
		this.idStore=idStore;
		this.callId=callId;
		this.chargeCode=chargeCode;
		this.sapCode=sapCode;
		this.openDate=openDate;
		this.closeDate=closeDate;
		this.callDescriptionShort=callDescriptionShort;
		this.callDescriptionLong=callDescriptionLong;
		this.exported=exported;
		this.open=open;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getIdStore() {
		return idStore;
	}

	public void setIdStore(Integer idStore) {
		this.idStore = idStore;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public String getSapCode() {
		return sapCode;
	}

	public void setSapCode(String sapCode) {
		this.sapCode = sapCode;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getCallDescriptionShort() {
		return callDescriptionShort;
	}

	public void setCallDescriptionShort(String callDescriptionShort) {
		this.callDescriptionShort = callDescriptionShort;
	}

	public String getCallDescriptionLong() {
		return callDescriptionLong;
	}

	public void setCallDescriptionLong(String callDescriptionLong) {
		this.callDescriptionLong = callDescriptionLong;
	}

	public Boolean getExported() {
		return exported;
	}

	public void setExported(Boolean exported) {
		this.exported = exported;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}
	
	public void newTicket(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from tickets where id_store = ? and open = true ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idStore);
			rs = st.executeQuery();
			int count = 0;
			if(rs.next()) {
				count++;
				setId(rs.getInt("id"));
				setIdStore(rs.getInt("id_store"));
				setCallId(rs.getString("callid"));
				setChargeCode(rs.getString("chargecode"));
				setSapCode(rs.getString("sapcode"));
				setOpenDate(rs.getDate("opendate"));
				setCloseDate(rs.getDate("closedate"));
				setCallDescriptionShort(rs.getString("calldescriptionshort"));
				setCallDescriptionLong(rs.getString("calldescriptionlong"));
			}
			rs.close();
			st.close();
			if(count==0) {
				// Open New Ticket
				setIdStore(idStore);
				sql = "select sapid from stores where id = ? ";
				st = conn.prepareStatement(sql);
				st.setInt(1, idStore);
				rs = st.executeQuery();
				if(rs.next()) {
					setSapCode(rs.getString("sapid"));
				}
				rs.close();
				st.close();
			}
			
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
	
	public String getLastCallId(Connection conn) {
		
		String callId = "";
		Integer iCallId = 0;
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select coalesce(max(substr(callid , 5, length(callid)-4)::int) , null , 0) num from tickets ";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if(rs.next()) {
				iCallId = rs.getInt("num");
			}
			rs.close();
			callId = String.format("%06d", (iCallId+1));
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
		
		return callId;
		
	}
	
	public String getChargeCode(Connection conn, Integer idStore) {
		
		String chargeCode = "";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select charge_code from customers where id = (select id_customer from stores where stores.id = ?) ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idStore);
			rs = st.executeQuery();
			if(rs.next()) {
				chargeCode = rs.getString("charge_code");
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
		
		return chargeCode;
		
	}
	public static List<StoreTicket> getTicketsByIdStore(Connection conn, int idStore){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		List<StoreTicket> stList = null;
		try {
			stList = new ArrayList<>();
			sql = "select * from tickets where id_store = ? and open = false order by opendate desc ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idStore);
			rs = st.executeQuery();
			while(rs.next()) {
				stList.add(new StoreTicket(rs.getInt("id"), rs.getInt("id_store"),
						rs.getString("callid"),rs.getString("chargecode"), rs.getString("sapcode"),
						rs.getDate("opendate"),rs.getDate("closedate"),rs.getString("calldescriptionshort"),
						rs.getString("calldescriptionlong"),rs.getBoolean("exported"),rs.getBoolean("open")));
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
		return stList;
	}
	
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update tickets set calldescriptionlong = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.callDescriptionLong);
	      st.setInt(2, this.id);
	      
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
	
	public void add(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = " insert into tickets (id_store, callid, chargecode, sapcode, opendate,"
					+ "calldescriptionshort, calldescriptionlong, open)" + 
					" values (?, ?, ?, ?, ?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idStore);
	        st.setString(2, this.callId);
	        st.setString(3, this.chargeCode);
	        st.setString(4, this.sapCode);
	        st.setTimestamp(5, new java.sql.Timestamp(new Date().getTime()));
	        st.setString(6, this.callDescriptionShort);
	        st.setString(7, this.callDescriptionLong);
	        st.setBoolean(8, this.open);
	        
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
