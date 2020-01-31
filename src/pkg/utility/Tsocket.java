package pkg.utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Tsocket {

	class myDevice {
		String id;
		String name;
		
		String parent_serial_no;
		int device_type_id;
		String address;
		String door_description;
		String serial_no;
		int door_type_id;
		
		public myDevice(String id, String name,
				String parent_serial_no, int device_type_id,
				String address, String door_description,
				String serial_no, int door_type_id) {
			this.id = id;
			this.name = name;
			
			this.parent_serial_no=parent_serial_no;
			this.device_type_id=device_type_id;
			this.address=address;
			this.door_description=door_description;
			this.serial_no=serial_no;
			this.door_type_id=door_type_id;
			
		}
	}

	public String SendReceiveData(int id, String ip, String port, int device_type) {
		
		String msg = "";
		
		List<String> listAnag = null;
		List<myDevice> listDeviceLetti = new ArrayList<myDevice>();
		String errorcode = "";
		String descr = "";
		String query = "";
		String query2= "";
		long ts = 0;
		try {
			listAnag = getListaAnagrafiche(id);
			
		} catch (SQLException e1) {
			System.out.println("Tsocket: " + e1.getMessage());
			System.out.println("Cannot read device list...");
			
			msg+="Tsocket: " + e1.getMessage()+"<br/>";
			msg+="Cannot read device list...<br/>";
			
		}
		System.out.println("");
		System.out.println("I'm analyzing " + ip + ":" + port + "(TSOCKET-device_type="+device_type+")");
		System.out.println("___________________________________________");
		InetAddress host;
		
		msg+="<p>I'm analyzing <b>" + ip + ":" + port + "</b> (TSOCKET-device_type="+device_type+")</p>";
		
		
		if(device_type==10)
			device_type = 2;
		
		
		List json = null;
		String SEPARATOR = "##@@";
		
		boolean res = false;
		
		try {
			
			updateDeviceSlavesAll(id, "");
			
			// ------------------
			// device_type=1
			if(device_type==1) {
				host = InetAddress.getByName(ip.trim());
				ts = MyTimeutils.getTimestamp();
				//Socket socket = new Socket(host.getHostName(), Integer.parseInt(port));
				
				Socket socket=null;
				
				int num = 5000;
				System.out.println("Byte Length:	" + num);
				byte[] bs = new byte[num];
				
				int nr_tentativi = 0;
				//int max_tentativi = MySqlHelper.readOption("numero_tentativi");
				int max_tentativi = 5;
				
				
				
				do {
					
					try {
						
						//if(isReachable(host.getHostName())) {
							socket = new Socket();
							
							System.out.println("Tentativo nr. "+nr_tentativi);
							msg+="<p>Attempt nr. "+nr_tentativi+"</p>";
							
							socket.connect(new InetSocketAddress(host.getHostName(), Integer.parseInt(port)), 60*1000);
					
							errorcode = "OK";
				
							System.out.println("Is Closed " + socket.isClosed());
							System.out.println("Is Connected " + socket.isConnected());
				
							DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
							DataInputStream dIn = new DataInputStream(socket.getInputStream());
				
							byte[] byteData = new byte[] { 0x7e, (byte) 0xff, 0x00, (byte) 0xff, 0x00, (byte) 0xe0, 0x00, 0x24, (byte) 0xf6, 0x1a, (byte) 0xee, 0x01, 0x00,
									0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
									0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x68 };
				
							dOut.write(byteData);
							dOut.flush(); // Send off the data
				
							dIn.read(bs);
							
							nr_tentativi = max_tentativi;
							
							res = true;
						/*	
						}
						else {
							nr_tentativi+=1;
						}
						*/
					}
					catch(Exception e) {
						System.out.println(nr_tentativi+" "+e.getMessage());
						msg+="<p>"+nr_tentativi+" "+e.getMessage()+"</p>";
						
						socket.close();
						
						Thread.sleep(5000);
						nr_tentativi+=1;
					}
					
				} while(nr_tentativi<max_tentativi);
			
				int num52trovati = 0;
				int numdopo52 = 0;
				boolean salvaLettere = false;
				String nome = "";
				String numero = "";
				boolean leggiNumero = true;
				int numdaleggere = 2;
				
				int ch = 0;
				int chOld = 0;
				int chOldOld = 0;
				int byteDaSaltare = 6;
				
				int nrDevice = 0;
				String nomeDevice = "";
				boolean find = false;
				boolean writeTitle = false;
				boolean stop = false;
				
				String lettere = "";
				String numeroOld = "";
				
				for (byte b : bs) {
					int c = b;
					
					System.out.print((char)c );
					
					lettere += c+"|";
					
					// valorizzo OldOld
					chOldOld = chOld;
					// valorizzo Old
					chOld = ch;
					// valorizzo new
					ch = c;
					
					if(chOld==52&&ch==4) {
						
						if(numero.trim().length()>0) {
							if(nome.length()>=2)
								nome = nome.substring(0, nome.length()-2);
							
							//System.out.println("NR: "+numero+" "+nome);
							if (listDeviceLetti.contains(nome))
								errorcode = "WRN_10002";
		
							listDeviceLetti.add(new myDevice(numero, nome, "", 0, "", "", "", 0));
						
							numeroOld = numero;
						}
						
						numero = "";
						nome = "";
						
						writeTitle = false;
						find=true;
						numero = ""+chOldOld;
						byteDaSaltare = 6;
						
						
					}
					
					if(find) {
						byteDaSaltare=byteDaSaltare-1;
					}
					if(byteDaSaltare==0) {
						find = false;
						writeTitle=true;
					}
					
					if(writeTitle) {
						if(c>=32&&c<=126) {
						
							char n = (char) c;
							nome += n;
							//System.out.print((char)c);
							
						}
					}
					
				}
				// ===============
				// Ultimo record
				//System.out.println("NR: "+numero+" "+nome);
				if (listDeviceLetti.contains(nome))
					errorcode = "WRN_10002";

				listDeviceLetti.add(new myDevice(numero, nome, "", 0, "", "", "", 0));
				// ===============
				
				socket.close();
				
			}
			
			// FINE device_type=1
			// ------------------
			
			// ------------------
			// device_type=2
			if(device_type==2) {
				host = InetAddress.getByName(ip.trim());
				ts = MyTimeutils.getTimestamp();
				errorcode = "OK";
				
				String numero = "";
				String nome = "";
				int num = 0;
				
				json = getJsonValues(ip.trim(), port);
				
				if (json != null)
				{
					Iterator i = json.iterator();
					
					while (i.hasNext())
					{
						
						
						String element = (String) i.next();
						String[] vet = element.split(SEPARATOR);
						num+=1;
						
						numero = ""+num;
						nome = vet[1];
						
						System.out.println(nome);
						if (listDeviceLetti.contains(nome))
							errorcode = "WRN_10002";
						
						listDeviceLetti.add(new myDevice(vet[5], nome, vet[4], Integer.parseInt(vet[7]), vet[0], vet[6],vet[5],Integer.parseInt(vet[3])));
						
						nome = "";
						numero = "";
					}
					
					res = true;
					
				}
				
			}
			
			if(res) {
				
				
				System.out.println("Devices found/expected	: " + listDeviceLetti.size() + "/" + listAnag.size());
				
				msg+="<p>Devices found/expected	: <b>" + listDeviceLetti.size() + "/" + listAnag.size()+"</b></p>";
				
				
				int i = 0;
				boolean newdevice = false;
				
				System.out.println(listAnag.size());
				
				
				
				System.out.println("");
				System.out.println("");
				
				for (myDevice string : listDeviceLetti) {
					
					if(device_type==2) {
						if (!listAnag.contains(string.id)) {
							
							System.out.println("**** "+string.id);
							// Ci sono nuovi device slave
							errorcode = "WRN_10001";
							
							newdevice = true;
							long nts = MyTimeutils.getTimestamp();
							if (i == 0) {
								query += " (" + id + ",'" + string.id + "','" + string.name + "',1," + nts + ", @idel, '"+errorcode+"')";
								query2+= id+","+string.id+","+errorcode+";";
								i++;
							} else {
								query += ",(" + id + ",'" + string.id + "','" + string.name + "',1," + nts + ", @idel, '"+errorcode+"')";
								query2+= id+","+string.id+","+errorcode+";";
							}
						}
						/*
						if(listDeviceLetti.size()!=listAnag.size()) {
							errorcode = "ERR_10009";
						}
						*/
					}
					else if(device_type==1) {
						//if (!listAnag.contains(string.id)) {
						if (!isPresenteDevice2(listAnag, string.id)) {
							// Ci sono nuovi device slave
							errorcode = "WRN_10001";
							
							
							newdevice = true;
							long nts = MyTimeutils.getTimestamp();
							if (i == 0) {
								query += " (" + id + ",'" + string.id + "','" + string.name + "',1," + nts + ", @idel, '"+errorcode+"')";
								query2+= id+","+string.id+","+errorcode+";";
								i++;
							} else {
								query += ",(" + id + ",'" + string.id + "','" + string.name + "',1," + nts + ", @idel, '"+errorcode+"')";
								query2+= id+","+string.id+","+errorcode+";";
							}
						}
					}
				}
				
				for (myDevice string : listDeviceLetti) {
					
					if(device_type==2) {
						if (newdevice) 
							errorcode = "ERR_10002";
						
						query2+= id+","+string.id+","+errorcode+";";
					}
					
				}
				
				for (String string : listAnag) {
					
					
					
					if(device_type==2) {
						
						
						/*
						if (listDeviceLetti.contains(string)) {
							
							//System.out.println("if contains");
							
							// Manca un device slave
							if (!newdevice) 
								errorcode = "ERR_10001";
							else
								errorcode = "ERR_10002";
						
							long nts = MyTimeutils.getTimestamp();
							if (i == 0) {
								query += "(" + id + ",'" + string + "',' '" + ",-1," + nts + ", @idel, '"+errorcode+"')";
								query2+= id+","+string+","+errorcode+";";
								i++;
							} else {
								query += ",(" + id + ",'" + string + "',' '" + ",-1," + nts + ", @idel, '"+errorcode+"')";
								query2+= id+","+string+","+errorcode+";";
							// MySqlHelper.addDeviceLog(id, string, -1, id_eventlog));
							}
						}
						else {
							
							
							//MySqlHelper.updateDeviceSlavesAll(id, errorcode);
							
							query2+= id+","+string+","+errorcode+";";
							
							// update slave readed as "OK";
						}
						
						*/
						
						String ids="";
						
						for(int d=0;d<listDeviceLetti.size();d++){
							ids+=listDeviceLetti.get(d).id+";";
						}
						
						if(ids.length()>0){
							ids=ids.substring(0, ids.length()-1);
						}
						
						String temp[]=ids.split(";");
						
						// Convert String Array to List
				        List<String> list = Arrays.asList(temp);
				        
				        if(list.contains(string)){
				            System.out.println("letto contiene");
				            
				            query2+= id+","+string+",OK;";
				        }
				        else {
				        	System.out.println("letto NON contiene");
				        	
				        	// Manca un device slave
							if (!newdevice) 
								errorcode = "ERR_10001";
							else
								errorcode = "ERR_10002";
							
							long nts = MyTimeutils.getTimestamp();
							if (i == 0) {
								query += "(" + id + ",'" + string + "',' '" + ",-1," + nts + ", @idel, '"+errorcode+"')";
								query2+= id+","+string+","+errorcode+";";
								i++;
							} else {
								query += ",(" + id + ",'" + string + "',' '" + ",-1," + nts + ", @idel, '"+errorcode+"')";
								query2+= id+","+string+","+errorcode+";";
							// MySqlHelper.addDeviceLog(id, string, -1, id_eventlog));
							}
				        }
					}
					else if(device_type==1) {
					
						//System.out.println(string);
						// if (!listDeviceLetti.contains(string))
						if (!isPresenteDevice(listDeviceLetti, string)) {
							System.out.println("***false "+string);
							
							
							// Manca un device slave
							if (!newdevice)
								errorcode = "ERR_10001";
							else
								errorcode = "ERR_10002";
						
							long nts = MyTimeutils.getTimestamp();
							if (i == 0) {
								query += "(" + id + ",'" + string + "',' '" + ",-1," + nts + ", @idel, '"+errorcode+"')";
								query2+= id+","+string+","+errorcode+";";
								i++;
							} else {
								query += ",(" + id + ",'" + string + "',' '" + ",-1," + nts + ", @idel, '"+errorcode+"')";
								query2+= id+","+string+","+errorcode+";";
								// MySqlHelper.addDeviceLog(id, string, -1, id_eventlog));
							}
						}
						else {
							//System.out.println("true");
						}
					}
				}
				
			}
			else {
				errorcode = "ERR_10008";
				
				updateDeviceSlavesAll(id, errorcode);
			}
			
			msg+="<p>ExitCode: <b>" + errorcode+"</b></p>";
			
			msg+="<hr/>";
			msg+="<p>Readed devices: <p><ul>";
			
			for (myDevice string : listDeviceLetti) {
					
				msg+="<li><b>" + string.id+"</b> - "+string.name+"</li>";
				
			}
			msg+="</ul>";
			System.out.println("___________________________________________");
			
		} catch (SocketTimeoutException e) {
			System.out.println("Tsocket SocketTimeoutException ("+ ip + "): " + e.getMessage());
			errorcode = "ERR_10005";
			descr = e.getMessage();
			System.out.println("SocketTimeoutException in 'Socket' Thread: " + e.getMessage());
		} catch (UnknownHostException e) {
			System.out.println("Tsocket UnknownHostException ("+ ip + "): " + e.getMessage());
			errorcode = "ERR_10003";
			descr = e.getMessage();
			System.out.println("UnknownHostException in 'Socket' Thread: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Tsocket IOException ("+ ip + "): " + e.getMessage());
			errorcode = "ERR_10004";
			descr = e.getMessage();
			System.out.println("IOException in 'Socket' Thread: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Tsocket ("+ ip + "): " + e.getMessage());
			errorcode = "ERR_10006";
			descr = e.getMessage();
			System.out.println("Exception error in 'Socket' Thread: " + e.getMessage());
		}

		try {
			System.out.println("Writing Events Log for id: " + id + ", code: " + errorcode + " and timestamp: " + ts);
			int id_el = addMasterLog(id, errorcode, descr, ts);
			query = query.replace("@idel", "" + id_el);
			if (!query.isEmpty()) {
				System.out.println(query);
				addDevicesLog(query);
				
				if(query2.length()>0)
					query2=query2.substring(0,query2.length()-1);
				
				String temp[]=query2.split(";");
				for(int i=0;i<temp.length;i++) {
					
					String temp2[]=temp[i].split(",");
					String id_device = temp2[0];
					String id_slave = temp2[1];
					String eventcode = temp2[2];
					
					updateDeviceSlaves(id_device, id_slave, eventcode);
					
				}
				
				
				System.out.println(query2);
			}
		} catch (SQLException e) {
			System.out.println("Tsocket ("+ ip + "): " + e.getMessage());
			System.out.println("SQLException in 'Socket' Thread: " + e.getMessage());
		}
    	
		
		return msg;
		
	}

	

	
	
	private boolean isPresenteDevice(List<myDevice> listDeviceLetti, String string) {
		int i = 0;
		for (myDevice md : listDeviceLetti) {
			i+=1;
			
			//System.out.println(i+" "+md.id.trim()+"|-|"+string.trim());
			if(isNumeric(md.id.trim())&&isNumeric(string.trim())) {
				if(Double.parseDouble(md.id.trim())==Double.parseDouble(string.trim()))
					return true;
			}
			else if (md.id.trim().equals(string.trim()))
				return true;
			
		}
		return false;
	}
	
	
	private boolean isPresenteDevice2(List<String> listAnag, String string) {
		int i = 0;
		
		
		for (String md : listAnag) {
			i+=1;
			
			
			if(isNumeric(md.trim())&&isNumeric(string.trim())) {
				if(Double.parseDouble(md.trim())==Double.parseDouble(string.trim())) {
					System.out.println(i+" "+md.trim()+"|-|"+string.trim());
					return true;
				}
			}
			else if (md.trim().equals(string.trim())) {
				System.out.println(i+" "+md.trim()+"|-|"+string.trim());
				return true;
			}
			
		}
		System.out.println("false "+string.trim());
		
		return false;
	}
	
	
	public static List<String> getListaAnagrafiche(int id) throws SQLException {
		ArrayList<String> al = new ArrayList<>();

		Connection c = Utility.getConnControl();
		Statement stmt = null;
		stmt = c.createStatement();
		String sql = "SELECT id_slave FROM deviceslaves WHERE id_device = " + id + " AND ENABLED = " + getBooleanValue(true);
		ResultSet dr = stmt.executeQuery(sql);
		while (dr.next()) {
			al.add(dr.getString(1));
		}
		stmt.close();
		c.close();

		return al;
	}
	
	public static String getBooleanValue(boolean value) {
		
		return value ? "true" : "false";
		
	}
	
	public static int addMasterLog(int id, String code, String descr, long ts) throws SQLException {
		//Connection c = getDB();
		Connection c = Utility.getConnControl();
		Statement stmt = null;
		int rid = -1;
		stmt = c.createStatement();
		
		String sql = "INSERT INTO eventslog (id_device, eventcode, description, tstamp) VALUES (" + id + ",'" + code + "','" + descr + "', " + ts + ")";
		
		System.out.println("***");
		System.out.println("eventslog: "+sql);
		
		stmt.executeUpdate(sql, new String[] { "id" });
		ResultSet rs = stmt.getGeneratedKeys();
		while (rs.next()) {
			rid = rs.getInt(1);
		}
		
		// in caso di OK...
		if(code.trim().toUpperCase().equals("OK")) {
			// update devices
			sql = "update devices set lasttime = "+ts+", checktime = "+ts+", lasteventcode = '"+code+"' where id = "+id+" ";
			stmt.executeUpdate(sql);
		}
		else {
			// update devices
			sql = "update devices set checktime = "+ts+", lasteventcode = '"+code+"' where id = "+id+" ";
			stmt.executeUpdate(sql);
		}
		
		stmt.close();
		c.close();
		return rid;
	}
	
	public static void addDevicesLog(String query) throws SQLException {
		//Connection c = getDB();
		Connection c = Utility.getConnControl();
		Statement stmt = null;
		stmt = c.createStatement();

		String sql = "INSERT INTO eventlog_details (id_device, id_deviceslave, slavename, status, tstamp, id_eventlog, eventcode) VALUES " + query;
		
		System.out.println("***");
		System.out.println("eventlog_details: "+sql);
		
		stmt.executeUpdate(sql);
		stmt.close();
		c.close();
	}
	
	public static void svuotaDeviceSlaveOf(int id) throws SQLException {
		//Connection c = getDB();
		Connection c = Utility.getConnControl();
		Statement stmt = null;
		stmt = c.createStatement();

		String sql = "DELETE FROM deviceslaves WHERE id_device = " + id;
		stmt.executeUpdate(sql);
		stmt.close();
		c.close();
	}
	
	public static void addDeviceSlaveJson(String query) throws SQLException {
		//Connection c = getDB();
		Connection c = Utility.getConnControl();
		Statement stmt = null;
		stmt = c.createStatement();

		String sql = "INSERT INTO deviceslaves(id_slave, enabled, id_device, slavename, "
				+ "parent_serial_no, device_type_id, address, door_description, serial_no, door_type_id) VALUES " + query;
		
		System.out.println(sql);
		
		stmt.executeUpdate(sql);
		stmt.close();
		c.close();
	}
	
	public static void rimuoviUpdating(int id) throws SQLException {
		//Connection c = getDB();
		Connection c = Utility.getConnControl();
		Statement stmt = null;
		stmt = c.createStatement();
		String sql = "UPDATE devices SET updating = " + getBooleanValue(false) + " WHERE id =" + id;
		stmt.executeUpdate(sql);
		stmt.close();
		c.close();
	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	public static boolean isReachable(String ip) {
		boolean reachable = false;
		try{
            InetAddress address = InetAddress.getByName(ip);
            reachable = address.isReachable(5000);
            System.out.println("IP "+ip+" raggiungibile: " + reachable);
        } catch (Exception e){
            e.printStackTrace();
        }
		return reachable;
	}
	
	public static void updateDeviceSlavesAll(int id_device, String eventcode) throws SQLException {
		
		try {
			Connection c = Utility.getConnControl();
			Statement stmt = null;
			stmt = c.createStatement();
	
			String sql = "update deviceslaves set eventcode = '"+eventcode+"' "
					+ "where id_device = "+id_device+" ";
			
			System.out.println("***");
			System.out.println("update_device_slaves: "+sql);
			
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> getJsonValues(String ip, String port) throws Exception
	{
		List<String> json;
		
		json = new ArrayList<String>();
		String SEPARATOR = "##@@";
		
	    // costruisce un URL
	    String s = "http://"+ip+":"+port+"/configutil/getdevicestatus";
	    //String s = "http://"+ip+":"+port+"/mobileutil/getdevicestatus";
		
	    //String s = "http://"+ip+":"+port+"/SmartRFID/dati.json";
	    System.out.println(s);
		URL url = new URL(s);
	 
	    // legge dall' URL
	    Scanner scan = new Scanner(url.openStream());
	    String str = new String();
	    while (scan.hasNext())
	        str += scan.nextLine();
	    scan.close();
	 
	    // costruisce un oggetto JSON
	    JSONObject obj = new JSONObject(str);
	    JSONArray arr = obj.getJSONArray("devices");
	    
	    String address = "";
	    String location = "";
	    int status_id = 0;
	    int door_type_id = 0;
	    String parent_serial_no = "";
	    String serial_no = "";
	    String door_description = "";
	    int device_type_id = 0;
	    String ip_address = "";
	    
	    String optValue = getOptionsValue("device_type_id");
	    //String optValue = "0,1,2,3,5,6,7,8,9,10,11,12,255";
	    
	    String opt = "";
	    String[] parts = optValue.split(",");
	    
	    for (int i = 0; i < arr.length(); i++)
	    {
	    	
	    	try {
		    	JSONObject os = new JSONObject(arr.getJSONObject(i).getString("other_settings"));
		    	ip_address = os.getString("ip_address");
	    	}
	    	catch(Exception e) {
	    		
	    	}
	    	address = ""+arr.getJSONObject(i).getString("address");
	    	location = ""+arr.getJSONObject(i).getString("location");
	    	status_id = arr.getJSONObject(i).getInt("status_id");
	    	door_type_id = arr.getJSONObject(i).getInt("door_type_id");
	    	parent_serial_no = ""+arr.getJSONObject(i).getString("parent_serial_no");
	    	serial_no = ""+arr.getJSONObject(i).getString("serial_no");
	    	try {
	    		door_description = ""+arr.getJSONObject(i).getString("door_description");
	    	}
	    	catch(Exception e) {
	    		door_description = "";
	    	}
	    	device_type_id = arr.getJSONObject(i).getInt("device_type_id");
	    	
	    	for(int o=0; o < parts.length ; o++) {
		    	opt = parts[o];
		    	// Se il device_type_id è presente in options...
		    	if(device_type_id==Integer.parseInt(opt)) {
		    		// Se status_id!=0
		    		if(status_id!=0) {
			    		// Aggiunge il device al json...
		    			
		    			
		    			//if(location.trim().length()>0) {
			    			System.out.println(location);
			    			
				    		json.add(address+SEPARATOR+
					    			 location+SEPARATOR+
					    			 status_id+SEPARATOR+
					    			 door_type_id+SEPARATOR+
					    			 parent_serial_no+SEPARATOR+
					    			 serial_no+SEPARATOR+
					    			 door_description+SEPARATOR+
					    			 device_type_id+SEPARATOR+
					    			 ip_address);
				    		
				    		//System.out.println(ip_address);
		    			//}
			    		
			    		
		    		}
			    }
		    }
	    	
	    	
			
	    }
	    
	    System.out.println("---");
	    System.out.println("");
	    
	    System.out.println(json);
	    return json;	    
	}
	
	public static void updateDeviceSlaves(String id_device, String id_slave, String eventcode) throws SQLException {
		
		try {
			Connection c = Utility.getConnControl();
			//Connection c = smartcontrol.c;
			Statement stmt = null;
			stmt = c.createStatement();
	
			String sql = "update deviceslaves set eventcode = '"+eventcode+"' "
					+ "where id_device = "+id_device+" and id_slave = '"+id_slave+"' ";
			
			System.out.println("***");
			System.out.println("update_device_slaves: "+sql);
			
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getOptionsValue(String optName) throws SQLException {
		String optValue = "";
		
		Connection c = null;
		Statement stmt = null;
		
		try {
			c = Utility.getConnControl();
			//Connection c = smartcontrol.c;
			
			
			stmt = c.createStatement();
	
			String sql = "SELECT value from options where name = '"+optName+"' ";
					
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				optValue = rs.getString("value");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		stmt.close();
		c.close();

		return optValue;
	}
}
