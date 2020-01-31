package pkg.beans.ccn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pkg.utility.Utility;

public class Devices {
	private int idDevice;
	private boolean inError;
	private String deviceName;
	private int accountId;
	private int storeId;
	private int doorId;
	private String ip;
	private String port;
	private int deviceType;
	private String devTypeDescription;
	private String status;
	private String stodesc;
	private String types;
	private String color;
	private String statusSince;
	private int idStore;
	
	
	
	
	public Devices() {	}

	public Devices(int idDevice, boolean inError, String deviceName, int accountId,
			int storeId, int doorId, String ip, String port, 
			int deviceType, String devTypeDescription, String status, String stodesc, 
			String types, String color, String statusSince, int idStore) {
		this.idDevice = idDevice;
		this.inError = inError;
		this.deviceName = deviceName;
		this.accountId = accountId;
		this.storeId = storeId;
		this.doorId = doorId;
		this.ip = ip;
		this.port = port;
		this.deviceType = deviceType;
		this.devTypeDescription = devTypeDescription;
		this.status = status;
		this.stodesc = stodesc;
		this.types = types;
		this.color = color;
		this.statusSince = statusSince;
		this.idStore = idStore;
		
	}
	
		public int getIdDevice() {
		return idDevice;
	}

	public void setIdDevice(int idDevice) {
		this.idDevice = idDevice;
	}

	public boolean isInError() {
		return inError;
	}

	public void setInError(boolean inError) {
		this.inError = inError;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getDoorId() {
		return doorId;
	}

	public void setDoorId(int doorId) {
		this.doorId = doorId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public String getDevTypeDescription() {
		return devTypeDescription;
	}

	public void setDevTypeDescription(String devTypeDescription) {
		this.devTypeDescription = devTypeDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStodesc() {
		return stodesc;
	}

	public void setStodesc(String stodesc) {
		this.stodesc = stodesc;
	}
	
	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	

	public String getStatusSince() {
		return statusSince;
	}

	public void setStatusSince(String statusSince) {
		this.statusSince = statusSince;
	}

	public int getIdStore() {
		return idStore;
	}

	public void setIdStore(int idStore) {
		this.idStore = idStore;
	}

	public static ArrayList<Devices> getDevicesByIdStore(Connection conn, int idStore){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Devices> devicesList = null;
		try {
			devicesList = new ArrayList<>();
			
			/*
			sql  =  "select a.id, coalesce(devicename , null, '-') devicename, "
					+ "accountid, storeid, doorid, ip, port, device_type, "
					+ "d.description ds_devicetype, coalesce(e.description , null , '') errdesc, "
					+ "lasteventcode, b.description as stodesc, b.google_places_types as types, control_color "
					+ "from devices a "
					+ "left join error_type e on e.errorcode = a.lasteventcode "
					+ "join vw_devices_control_color vw on a.id = vw.id, "
					+ "f_stores b, customers c, devicetype d "
					+ "where a.id_store = b.id and b.id_customer = c.id "
					+ "and a.device_type = d.id "
					//+ "and a.id_store in(select idstore from rel_user_stores "
					//+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
					+"and a.id_store = ? order by lasteventcode ";
			*/
			sql  = "select coalesce(a.id, null, -1) id, coalesce(devicename , null, '-') devicename, \n" + 
					"accountid, b.store_code storeid, doorid, ip, port, device_type, d.description ds_devicetype, \n" + 
					"coalesce(e.description , null , '') errdesc, \n" + 
					"lasteventcode, b.description as stodesc, b.google_places_types as types, \n" + 
					"control_color \n" + 
					"from f_stores b\n" + 
					"join customers c on c.id = b.id_customer\n" + 
					"left join devices a on a.id_store = b.id\n" + 
					"left join vw_devices_control_color vw on a.id = vw.id\n" + 
					"left join devicetype d on a.device_type = d.id \n" + 
					"left join error_type e on e.errorcode = a.lasteventcode \n" + 
					"where b.id = ? \n" + 
					"order by lasteventcode ";
			
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			st.setInt(1, idStore);
			rs = st.executeQuery();
			
			String evcode = "";
			boolean isErr = false;
			while(rs.next()) {
				
				
				
				evcode = ""+rs.getString("lasteventcode");
				evcode = Utility.noNull(evcode);
				
				if(evcode.trim().length()>0){
					if(evcode.equalsIgnoreCase("OK"))
						isErr = false;
					else 
						isErr = true;
				}
				
				String devicename = ""+rs.getString("devicename");
				devicename=Utility.noNull(devicename);
				if(devicename.length()==0)
					devicename = "-";
				
				devicesList.add(new Devices(rs.getInt("id"),  
						isErr, devicename, 
						rs.getInt("accountid"), rs.getInt("storeid"),
						rs.getInt("doorid"), rs.getString("ip"),
						rs.getString("port"), rs.getInt("device_type"), 
						rs.getString("ds_devicetype"),
						rs.getString("errdesc"), rs.getString("stodesc"), rs.getString("types"),
						rs.getString("control_color"), "", idStore));
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
		return devicesList;
	}
	
	public static ArrayList<Devices> getDevicesByIdStoreAndre(Connection conn, int idStore){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Devices> devicesList = null;
		try {
			devicesList = new ArrayList<>();
			
			
//			sql  = "select coalesce(a.id, null, -1) id, coalesce(devicename , null, '-') devicename, \n" + 
//					"accountid, b.store_code storeid, doorid, ip, port, device_type, d.description ds_devicetype, \n" + 
//					"coalesce(e.description , null , '') errdesc, \n" + 
//					"lasteventcode, b.description as stodesc, b.google_places_types as types, \n" + 
//					"control_color \n" + 
//					"from f_stores b\n" + 
//					"join customers c on c.id = b.id_customer\n" + 
//					"left join devices a on a.id_store = b.id\n" + 
//					"left join vw_devices_control_color vw on a.id = vw.id\n" + 
//					"left join devicetype d on a.device_type = d.id \n" + 
//					"left join error_type e on e.errorcode = a.lasteventcode \n" + 
//					"where b.id = ? \n" + 
//					"order by lasteventcode ";
			
			sql  = "select * from vw_control_device_customer where id_store = ? ";
			
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			st.setInt(1, idStore);
			rs = st.executeQuery();
			
			String evcode = "";
			boolean isErr = false;
			while(rs.next()) {
				
				
				
				evcode = ""+rs.getString("lasteventcode");
				evcode = Utility.noNull(evcode);
				
				if(evcode.trim().length()>0){
					if(evcode.equalsIgnoreCase("OK"))
						isErr = false;
					else 
						isErr = true;
				}
				
				String devicename = ""+rs.getString("devicename");
				devicename=Utility.noNull(devicename);
				if(devicename.length()==0)
					devicename = "-";
				
				devicesList.add(new Devices(rs.getInt("id"),  
						isErr, devicename, 
						rs.getInt("accountid"), rs.getInt("storeid"),
						rs.getInt("doorid"), rs.getString("ip"),
						rs.getString("port"), rs.getInt("device_type"), 
						rs.getString("ds_devicetype"),
						rs.getString("errdesc"), rs.getString("stodesc"), rs.getString("types"),
						rs.getString("control_color"), rs.getString("status_since"), idStore));
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
		return devicesList;
	}
	
}
