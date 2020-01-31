package pkg.utility;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import pkg.HR.Minnum_employeeDett;
import pkg.properties.ReadProperties;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Utility 
{
	
	public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static DateFormat dfNum = new SimpleDateFormat("yyyyMMdd");
	public static Date date = new Date();
	public static String ts = df.format(date);
	public static String dateNum = dfNum.format(date);
	
	/*
	 * ----------------------------------
	 * Arrotondamento
	 * ----------------------------------
	 */
	
	public static double arrotonda(double value, int numCifreDecimali) {
	      double temp = Math.pow(10, numCifreDecimali);
	      return Math.round(value * temp) / temp; 
	}
	
	/*
	 * ----------------------------------
	 * E' un numero?
	 * ----------------------------------
	 */
	public static boolean isnum(String sNum)
	{
		try   
	    {   
			Double.parseDouble(sNum);   
	    }   
	    catch(NumberFormatException ex)   
	    {   
	    	return false;   
	    }   
	    return true; 
	}
	
	/*
	 * ----------------------------------
	 * Nuovo Progressivo
	 * ----------------------------------
	 */
	public static String getNewProgr(Connection conn, String tab, String fld, String cond) throws IOException
	{
		String sNewProgr= "";
		int iNewProgr	= 0;
		
		String sSql		= "";
		
		Statement st = null;
		ResultSet rs = null;
		
		try
		{	
			st = conn.createStatement();
			
			sSql =  "SELECT MAX("+fld+"::integer) AS MAX FROM "+tab+" "+cond;
			//System.out.println(sSql);
			
			rs = st.executeQuery(sSql);
			if (rs.next())
			{	
				sNewProgr = ""+rs.getString("MAX");	
				if(sNewProgr.equals("null"))
				{
					iNewProgr = 1;
					sNewProgr = "" + iNewProgr;
				}	
				else
				{
					if(isnum(rs.getString("MAX")))
					{
						iNewProgr = rs.getInt("MAX") + 1;
						sNewProgr = "" + iNewProgr;
					}
					else
					{
						sNewProgr = "1";
					}
				}
			}
			else
			{		
				iNewProgr = 1;
				sNewProgr = "" + iNewProgr;
			}	
			rs.close();
			st.close();
			
		}
		catch(Exception Err)
		{	
			Err.printStackTrace();
			sNewProgr = "E";
		}
		return sNewProgr;
	}
	
	/*
	 * ----------------------------------
	 * Imposta la connessione al DATABASE
	 * ----------------------------------
	 */
	public static Connection getConnAnalytics() {
		Connection conn = null;
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = prop.get("usrAnalytics");
			String psw = prop.get("pswAnalytics");
			String url = prop.get("urlAnalytics");
			String drv = prop.get("drv");
			String ssl = prop.get("sslAnalytics");
			String database  = prop.get("databaseAnalytics");
			
			//System.out.println(url+database+","+usr+","+psw);
			if(Boolean.parseBoolean(ssl)) {
				Class.forName(drv);
				url = url+database+"?user="+usr+"&password="+psw+"&ssl=true";
				conn = DriverManager.getConnection(url);
			}
			else {
				Class.forName(drv);
				conn = DriverManager.getConnection(url+database, usr, psw);
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static Connection getConnCounter() {
		Connection conn = null;
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = prop.get("usrCounter");
			String psw = prop.get("pswCounter");
			String url = prop.get("urlCounter");
			String drv = prop.get("drv");
			String ssl = prop.get("sslCounter");
			String database  = prop.get("databaseCounter");
			
			//System.out.println(url+database+","+usr+","+psw);
			//System.out.println(url+database+","+usr+","+psw);
			if(Boolean.parseBoolean(ssl)) {
				Class.forName(drv);
				url = url+database+"?user="+usr+"&password="+psw+"&ssl=true";
				conn = DriverManager.getConnection(url);
			}
			else {
				Class.forName(drv);
				conn = DriverManager.getConnection(url+database, usr, psw);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static Connection getConnControl() {
		Connection conn = null;
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = prop.get("usrControl");
			String psw = prop.get("pswControl");
			String url = prop.get("urlControl");
			String drv = prop.get("drv");
			String ssl = prop.get("sslControl");
			String database  = prop.get("databaseControl");
			
			//System.out.println(url+database+","+usr+","+psw);
			
			//System.out.println(url+database+","+usr+","+psw);
			if(Boolean.parseBoolean(ssl)) {
				Class.forName(drv);
				url = url+database+"?user="+usr+"&password="+psw+"&ssl=true";
				conn = DriverManager.getConnection(url);
			}
			else {
				Class.forName(drv);
				conn = DriverManager.getConnection(url+database, usr, psw);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static Connection getConnDispatcher() {
		Connection conn = null;
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = prop.get("usrDispatcher");
			String psw = prop.get("pswDispatcher");
			String url = prop.get("urlDispatcher");
			String drv = prop.get("drv");
			String ssl = prop.get("sslDispatcher");
			String database  = prop.get("databaseDispatcher");
			
			//System.out.println(url+database+","+usr+","+psw);
			
			//System.out.println(url+database+","+usr+","+psw);
			if(Boolean.parseBoolean(ssl)) {
				Class.forName(drv);
				url = url+database+"?user="+usr+"&password="+psw+"&ssl=true";
				conn = DriverManager.getConnection(url);
			}
			else {
				Class.forName(drv);
				conn = DriverManager.getConnection(url+database, usr, psw);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static Connection getConnHR() {
		Connection conn = null;
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = prop.get("usrHR");
			String psw = prop.get("pswHR");
			String url = prop.get("urlHR");
			String drv = prop.get("drv");
			String ssl = prop.get("sslHR");
			String database  = prop.get("databaseHR");
			
			//System.out.println(url+database+","+usr+","+psw);
			
			//System.out.println(url+database+","+usr+","+psw);
			if(Boolean.parseBoolean(ssl)) {
				Class.forName(drv);
				url = url+database+"?user="+usr+"&password="+psw+"&ssl=true";
				conn = DriverManager.getConnection(url);
			}
			else {
				Class.forName(drv);
				conn = DriverManager.getConnection(url+database, usr, psw);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static Connection getConnVisit() {
		Connection conn = null;
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = prop.get("usrDispatcher");
			String psw = prop.get("pswDispatcher");
			String url = prop.get("urlDispatcher");
			String drv = prop.get("drv");
			String ssl = prop.get("sslDispatcher");
			String database  = prop.get("databaseVisit");
			
			//System.out.println(url+database+","+usr+","+psw);
			
			//System.out.println(url+database+","+usr+","+psw);
			if(Boolean.parseBoolean(ssl)) {
				Class.forName(drv);
				url = url+database+"?user="+usr+"&password="+psw+"&ssl=true";
				conn = DriverManager.getConnection(url);
			}
			else {
				Class.forName(drv);
				conn = DriverManager.getConnection(url+database, usr, psw);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static Connection getConnPostgres() {
		Connection conn = null;
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = prop.get("usrDispatcher");
			String psw = prop.get("pswDispatcher");
			String url = prop.get("urlDispatcher");
			String drv = prop.get("drv");
			String database  = "postgres";
			
			Class.forName(drv);
			conn = DriverManager.getConnection(url+database, usr, psw);
			
		}
		catch(Exception e) {
			//e.printStackTrace();
		}
		
		return conn;
	}
	
	public static Connection getConnAdmin() {
		Connection conn = null;
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = prop.get("usrAdmin");
			String psw = prop.get("pswAdmin");
			String url = prop.get("urlAdmin");
			String drv = prop.get("drv");
			String ssl = prop.get("sslAdmin");
			String database  = prop.get("databaseAdmin");
			
			//System.out.println(urlAdmin+databaseAdmin+","+usrAdmin+","+pswAdmin);
			
			//System.out.println(url+database+","+usr+","+psw);
			if(Boolean.parseBoolean(ssl)) {
				Class.forName(drv);
				url = url+database+"?user="+usr+"&password="+psw+"&ssl=true";
				conn = DriverManager.getConnection(url);
			}
			else {
				Class.forName(drv);
				conn = DriverManager.getConnection(url+database, usr, psw);
			}
		}
		catch(Exception e) {
			//e.printStackTrace();
		}
		
		return conn;
	}
	
	public static Connection getConnRfid() {
		Connection conn = null;
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = prop.get("usrRfid");
			String psw = prop.get("pswRfid");
			String url = prop.get("urlRfid");
			String drv = prop.get("drv");
			String ssl = prop.get("sslRfid");
			String database  = prop.get("databaseRfid");
			
			//System.out.println(url+database+","+usr+","+psw);
			if(Boolean.parseBoolean(ssl)) {
				Class.forName(drv);
				url = url+database+"?user="+usr+"&password="+psw+"&ssl=true";
				conn = DriverManager.getConnection(url);
			}
			else {
				Class.forName(drv);
				conn = DriverManager.getConnection(url+database, usr, psw);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static Connection getConnData() {
		Connection conn = null;
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = prop.get("usrData");
			String psw = prop.get("pswData");
			String url = prop.get("urlData");
			String drv = prop.get("drv");
			String ssl = prop.get("sslData");
			String database  = prop.get("databaseData");
			
			//System.out.println(url+database+","+usr+","+psw);
			
			//System.out.println(url+database+","+usr+","+psw);
			if(Boolean.parseBoolean(ssl)) {
				Class.forName(drv);
				//url = url+database+"?user="+usr+"&password="+psw+"&ssl=true";
				url = url+database+"?user="+usr+"&password="+psw+"&sslmode=require";
				conn = DriverManager.getConnection(url);
			}
			else {
				Class.forName(drv);
				conn = DriverManager.getConnection(url+database, usr, psw);
			};
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static Connection getConnPos() {
		Connection conn = null;
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = prop.get("usrPos");
			String psw = prop.get("pswPos");
			String url = prop.get("urlPos");
			String drv = prop.get("drv");
			String ssl = prop.get("sslPos");
			String database  = prop.get("databasePos");
			
			//System.out.println(url+database+","+usr+","+psw);
			
			//System.out.println(url+database+","+usr+","+psw);
			if(Boolean.parseBoolean(ssl)) {
				Class.forName(drv);
				url = url+database+"?user="+usr+"&password="+psw+"&ssl=true";
				conn = DriverManager.getConnection(url);
			}
			else {
				Class.forName(drv);
				conn = DriverManager.getConnection(url+database, usr, psw);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static Connection getConnPosext() {
		Connection conn = null;
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			
			String usr = prop.get("usrPos");
			String psw = prop.get("pswPos");
			String url = prop.get("urlPos");
			String drv = prop.get("drv");
			String ssl = prop.get("sslPos");
			String database  = prop.get("databasePosext");
			
			//System.out.println(urlext+databaseext+","+usrext+","+pswext);
			
			//System.out.println(url+database+","+usr+","+psw);
			if(Boolean.parseBoolean(ssl)) {
				Class.forName(drv);
				url = url+database+"?user="+usr+"&password="+psw+"&ssl=true";
				conn = DriverManager.getConnection(url);
			}
			else {
				Class.forName(drv);
				conn = DriverManager.getConnection(url+database, usr, psw);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	/*
	 * ----------------------------------
	 * null --> *blank
	 * ----------------------------------
	 */
	public static String noNull(String cmp) {
		
		cmp = ""+cmp;
		if(cmp.equals("null"))
			cmp = "";
		
		return cmp;
		
	}
	
	/*
	 * ----------------------------------
	 * Scrive Log
	 * ----------------------------------
	 */
	public static void writeLog(String msg) {
		
		try {
			
			ReadProperties prop = new ReadProperties("config.properties");
			String pathLog = prop.get("pathLog");
			
			String filename = pathLog+dateNum+".log";
			FileWriter fw = new FileWriter(filename,true); 
			
			fw.write(ts+" "+msg+"\n");
			fw.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static int getDateDiff(String dateStart, String dateStop) {
		
		// Custom date format
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy h:mm a");  
		//SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy h:mm a");
		
		Date d1 = null;
		Date d2 = null;
		try {
		    d1 = format.parse(dateStart);
		    d2 = format.parse(dateStop);
		} catch (ParseException e) {
		    e.printStackTrace();
		}    

		// Get msec from each, and subtract.
		long diff = d2.getTime() - d1.getTime();
		long diffSeconds = diff / 1000 % 60;         
		long diffMinutes = diff / (60 * 1000);         
		long diffHours = diff / (60 * 60 * 1000);                      
		System.out.println("Time in seconds: " + diffSeconds + " seconds.");         
		System.out.println("Time in minutes: " + diffMinutes + " minutes.");         
		System.out.println("Time in hours: " + diffHours + " hours."); 
		
		return (int) diffMinutes;

	}
	
	public static int getDateDiffDays(String dateStart, String dateStop) {
		
		// Custom date format
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		Date d1 = null;
		Date d2 = null;
		try {
		    d1 = format.parse(dateStart);
		    d2 = format.parse(dateStop);
		} catch (ParseException e) {
		    e.printStackTrace();
		}    

		System.out.println("dateStart: " + dateStart);
		System.out.println("dateStop: " + dateStop);
		
		// Get msec from each, and subtract.
		long diff = d2.getTime() - d1.getTime();
		long diffDays = diff / (1000 * 60 * 60 * 24);                  
		//System.out.println("Time in days: " + diffDays + " days.");         
		return (int) diffDays;
	}
	
	public static int getDateDiffDays2(String dateStart, String dateStop) {
		
		// Custom date format
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  

		Date d1 = null;
		Date d2 = null;
		try {
		    d1 = format.parse(dateStart);
		    d2 = format.parse(dateStop);
		} catch (ParseException e) {
		    e.printStackTrace();
		}    
		
		System.out.println(dateStart);
		System.out.println(dateStop);

		// Get msec from each, and subtract.
		long diff = d2.getTime() - d1.getTime();
		long diffDays = diff / (1000 * 60 * 60 * 24);                  
		
		System.out.println("Time in days: " + diffDays + " days.");         
		
		return (int) diffDays;

	}
	
	public static int getDateDiffDays3(String dateStart, String dateStop) {
		
		// Custom date format
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  

		Date d1 = null;
		Date d2 = null;
		try {
		    d1 = format.parse(dateStart);
		    d2 = format.parse(dateStop);
		} catch (ParseException e) {
		    e.printStackTrace();
		}    
		
		System.out.println(dateStart);
		System.out.println(dateStop);

		// Get msec from each, and subtract.
		long diff = d2.getTime() - d1.getTime();
		long diffDays = diff / (1000 * 60 * 60 * 24);                  
		
		System.out.println("Time in days: " + diffDays + " days.");         
		
		return (int) diffDays;

	}
	
	public static String noApex(String Str) {
		// apostrofo = &rsquo;
		Str = Str.replace("'", "&rsquo;");
		Str = Str.replace("''", "&rsquo;&rsquo;");
		///[\r\n]+/
		Str = Str.replaceAll("\\r?\\n", " ");
		return Str;
	}
	
	public static String yesApex(String Obj) {
		// Riduce il messaggio per anteprima
		//Obj = Obj.replace("&#8217;", "'");
		Obj = Obj.replace("&rsquo;", "'");
		Obj = Obj.replace("&rsquo;&rsquo;", "''");
		Obj = Obj.replaceAll("\\r?\\n", " ");
		return Obj;
	}	
	
	public static String SmsRedux(String Obj) {
		// Riduce il messaggio per anteprima
		Obj = Obj.replace("&#8217;", "'");
		Obj = Obj.replace("&rsquo;", "'");
		//System.out.println("Obj.lenght = " + Obj.length());
		if (Obj.length() >= 27){
			Obj = Obj.substring(0, 26);
			Obj = Obj + "...";
		}
		return Obj;
	}
	//SmsReduxslah
	public static String SmsReduxslah(String Obj) {
		// Rimette un apice "`" per visualizzare la stringa correttamente
		Obj = Obj.replace("&rsquo;", "`");
		return Obj;
	}
	
	public static boolean isValidMD5(String s) {		
	    return s.matches("[a-fA-F0-9]{32}");
	}
	
	public static boolean dbClose(ResultSet rs, Connection conn) {		

		boolean ret = true;
		
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { ret = false; /* ignored */}
		    }

		    if (conn != null) {
		        try {
		            conn.close();
		        } catch (SQLException e) { ret = false; /* ignored */}
		    }
		    
		    return ret; 
		
	}	
	
	public static Connection getConnMexall() {
		Connection conn = null;
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = prop.get("usrMexall");
			String pwd = prop.get("pwdMexall");
			String url = prop.get("urlMexall");
			String drv = prop.get("drvMexall");
			
			String connectionUrl = url+usr+pwd;
			Class.forName(drv);
            conn = DriverManager.getConnection(connectionUrl);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**/
	public static String getBarcode(String barcode) throws IOException {

        String pathBarcode = "";
        String imgpath = "";
        
        Code128Bean m_barcode = new Code128Bean();
        final int dpi = 150;
        
        if (m_barcode != null) {
              m_barcode.setModuleWidth(1.0);
              m_barcode.setBarHeight(40.0);
              m_barcode.setFontSize(10.0);
              m_barcode.setQuietZone(10.0);
              m_barcode.doQuietZone(true);
        }
        String AbsolutePath = new File(".").getAbsolutePath();
        AbsolutePath=AbsolutePath.substring(0, AbsolutePath.length()-1)+"WebContent\\barcodes\\";
        
        AbsolutePath = "c:\\sct\\Stampe\\";
        System.out.println("path = " + AbsolutePath);
        
        File outputFile = new File(AbsolutePath+barcode+".png");
        OutputStream out = new FileOutputStream(outputFile);
        
        
        
        
        
        try {
              //Set up the canvas provider for monochrome PNG output 
              BitmapCanvasProvider canvas = new BitmapCanvasProvider(
              out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
              
              // Generate the barcode
              m_barcode.generateBarcode(canvas, barcode);
              
              //Signal end of generation
              canvas.finish();
        } finally {
              out.close();
        }
        
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(AbsolutePath+barcode+".png"));
        } 
        catch (IOException e) {}
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imageString = "data:image/png;base64," +
            DatatypeConverter.printBase64Binary(baos.toByteArray());
        
        
        return imageString;
        
   }
	
	public static String getTurnDate(String dat) {		

		
		if (!"".equals(dat)){
			String[] app = null; 
			String yy = "", mm = "", dd = "";
			app = dat.split("-");
			yy = app[0];
			mm = app[1];
			dd = app[2];
			
			dat = dd+"-"+mm+"-"+yy;
		}
		return dat;
	}	
	

public String getPath() throws UnsupportedEncodingException {
	
	String path = this.getClass().getClassLoader().getResource("").getPath();
	String fullPath = URLDecoder.decode(path, "UTF-8");
	String pathArr[] = fullPath.split("/WEB-INF/classes/");
	System.out.println(fullPath);
	System.out.println(pathArr[0]);
	fullPath = pathArr[0];
	String reponsePath = "";
	// to read a file from webcontent
	reponsePath = new File(fullPath).getPath() + File.separatorChar + "newfile.txt";
	
	return reponsePath;
}
	
	/**/



	//HTTP POST request
	public static void sendPost(String url,String urlParameters) throws Exception {
	
	    String USER_AGENT = "Mozilla/5.0";
	
	    URL obj = new URL(url);
	    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
	    //add reuqest header
	    con.setRequestMethod("POST");
	    con.setRequestProperty("User-Agent", USER_AGENT);
	    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	
	    //String urlParameters = "idscan="+MainActivity.idscan+"&package_id="+MainActivity.id_package+"&epc="+epc;
	
	    // Send post request
	    con.setDoOutput(true);
	    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	    wr.writeBytes(urlParameters);
	    wr.flush();
	    wr.close();
	
	    int responseCode = con.getResponseCode();
	    System.out.println("\nSending 'POST' request to URL : " + url);
	    System.out.println("Post parameters : " + urlParameters);
	    System.out.println("Response Code : " + responseCode);
	
	    BufferedReader in = new BufferedReader(
	            new InputStreamReader(con.getInputStream()));
	    String inputLine;
	    StringBuffer response = new StringBuffer();
	
	    while ((inputLine = in.readLine()) != null) {
	        response.append(inputLine);
	    }
	    in.close();
	
	    //print result
	    System.out.println(response.toString());
	
	}
	
	public static String getCustomerDescription(Connection conn, String idcus) {
		
		String description = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql = "select description from customers where id = "+idcus+" ";
			rs = st.executeQuery(sql);
			if(rs.next()) {
				description = rs.getString("description");
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
		
		return description;
		
	}
	
	public static long getDateDiff( Date d1, Date d2, int calUnit ) {
	      if( d1.after(d2) ) {    // make sure d1 < d2, else swap them
	         Date temp = d1;
	         d1 = d2;
	         d2 = temp;
	      }
	      GregorianCalendar c1 = new GregorianCalendar();
	      c1.setTime(d1);
	      GregorianCalendar c2 = new GregorianCalendar();
	      c2.setTime(d2);
	      for( long i=1; ; i++ ) {         
	         c1.add( calUnit, 1 );   // add one day, week, year, etc.
	         if( c1.after(c2) )
	            return i-1;
	      }
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public static String getNationDescription(Connection conn, String idnaz) {
		
		String description = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql = "select nome_nazione from nazioni where id_naz = "+idnaz+" ";
			rs = st.executeQuery(sql);
			if(rs.next()) {
				description = rs.getString("nome_nazione");
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
		
		return description;
		
	}
	
	public static String getRegionDescription(Connection conn, String idreg) {
		
		String description = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql = "select nome_regione from regioni where id_reg = "+idreg+" ";
			rs = st.executeQuery(sql);
			if(rs.next()) {
				description = rs.getString("nome_regione");
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
		
		return description;
		
	}
	
	public static String getXdtab(Connection conn, String xcdtb, String xcdel) {
		
		String xdtab = "";
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql   = "select xdtab from ztables where xcdtb = '"+xcdtb+"' and xcdel = '"+xcdel+"' ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				xdtab = rs.getString("xdtab");
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
		
		return xdtab;
		
	}
	
	/**/
	public static String getTurnDatealt(String dat) {		
		
	       Date dt = null;
	       DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
	       DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		if (!"".equals(dat)){
			
			try {
				dt = df2.parse(dat);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
/*
			String[] app = null; 
			String yy = "", mm = "", dd = "";
			app = dat.split("-");
			yy = app[0];
			mm = app[1];
			dd = app[2];
			
			dat = dd+"-"+mm+"-"+yy;		
*/			
			dat = df.format(dt);
			
		}
		return dat;
	}		
	/**/
	
	
	public static boolean ePari(int numero) {
		if ((numero % 2) == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void setLog(Connection conn, String idusr, String msg) {
		
		Statement st = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql   = "insert into application_log "
					+ "(idusr, message) "
					+ "values "
					+ "('"+idusr.replaceAll("'","''")+"', '"+msg.replaceAll("'", "''")+"') ";
			st.executeUpdate(sql);
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
	
	/**/
	public static String getBadge(Connection conn, String id_employee, String dtini, String dtfin) throws SQLException {
		
		String badge = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql   = "select number "+
					"from rel_emp_badge a, hr_badge b "+
					"where "+
					"("+
					"a.id_badge = b.id and a.date_end is null and a.id_employ = '"+id_employee+"' "+
					") "+
					"or "+
					"("+
					"a.id_badge = b.id and a.date_end between "+dtini+" and "+dtfin+" and a.id_employ = '"+id_employee+"' "+
					") "+
					"fetch first 1 rows only ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				badge = rs.getString("number");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		
		return badge;
		
	}
	
	public static void reopenPlanner(Connection conn, String id_departments_from, String id_departments_to, String dtini) throws SQLException {
		
		Statement st = null;
		Statement up = null;
		ResultSet rs = null;
		String sql = "";
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis((Long.parseLong(dtini)*1000));
		String sDtini = formatter.format(calendar.getTime());
		
		try {
			st=conn.createStatement();
			up=conn.createStatement();
			
			String des_departments_from = Minnum_employeeDett.getDescription(conn, id_departments_from);
			String des_departments_to = Minnum_employeeDett.getDescription(conn, id_departments_to);
			
			int count = 0;
			String msg = "";
			if(id_departments_from.length()>0) {
				msg = "A seguito dello spostamento di una risorsa dallo store "+des_departments_from+" allo store "+des_departments_to+" a partire dal giorno "+sDtini+" &egrave; necessario ricontrollare le pianificazioni. Lo stato delle pianificazioni non è variato.";
			}
			else {
				msg = "A seguito dello spostamento di una risorsa allo store "+des_departments_to+" a partire dal giorno "+sDtini+" le settimane successive gi&agrave; approvate devono essere ricontrollate. Lo stato delle pianificazioni non è variato.";
			}
			
			sql   = "select id_departments, plan_year, plan_week from hr_planification "+
					"where datetime_start >= "+dtini+" "+ 
					"and id_departments in('"+id_departments_from+"' , '"+id_departments_to+"') and status not in('','Y') "+
					"group by id_departments, plan_year, plan_week ";
			System.out.println(sql);
			
			rs=st.executeQuery(sql);
			while(rs.next()) {
				count+=1;
				
				// PTF 20180807 ---> NON RIAPRE PIU' IL SETTIMANALE
				/*
				// Riapertura settimanali successivi allo spostamento
				sql   = "update hr_planification "+
						"set status = 'Y' "+
						"where id_departments in('"+id_departments_from+"' , '"+id_departments_to+"') "+
						"and datetime_start >= "+dtini+" and status not in('','Y') ";
				System.out.println(sql);
				up.executeUpdate(sql);
				*/
				
			}
			rs.close();
			
			// Se sono stati riaperti settimanali invia una mail
			if(count>0) {
				
				
				// ==========================
				// Seleziona Utente
				sql   = "select a.id_employee, b.name, b.surname, b.email, c.name as role, id_user "+
						"from rel_dep_employee a , hr_employee b "+
						"left join rel_emp_user u on u.id_employee = b.id, hr_roles c "+
						"where id_departments in('"+id_departments_from+"' , '"+id_departments_to+"') "+
						"and dateend is null and a.id_employee = b.id and a.id_roles = c.id "+
						"and id_roles in('a497f308-08e0-4ce0-8e1c-80f50c291186', '92b2a236-ba12-4b94-96ef-11bde7b85769') ";
				rs=st.executeQuery(sql);
				while(rs.next()) {
					
					try {
						JSendMail.msgMail(rs.getString("email"),"Spostamento Risorsa", msg,"","smarthr@smartretail.it");
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					
				}
				rs.close();
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(up!=null)
			up.close();
		if(st!=null)
			st.close();
		
	}	
	
	public static String getEmployeePlanningNew(Connection conn, String plan_year, 
			String plan_week, String plan_day, String id_employee, String trasf, String dep_orig) throws SQLException {
		
		String planning = "";
		String arrayPeriodi = "";
		String arrayCss = "";
		
		String inputs = "";
		String inputs2= "";
		
		Statement st = null;
		ResultSet rs = null;
		
		String sql   = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
		
		String ini = "";
		String fin = "";
		
		String cond = "";
		if(trasf.equals("S")) {
			cond = "and id_departments = '"+dep_orig+"' ";
		}
		
		try {
			st=conn.createStatement();
			
			sql   = "select datetime_start, datetime_end, id_departments, d.description ds, "+
					"to_timestamp(datetime_start) date_start, to_timestamp(datetime_end) date_end, "+
					"id_planification_type, color, t.description, status "+
					"from hr_planification p "+
					"left join hr_planification_type t on t.id = p.id_planification_type, hr_departments d "+
					"where plan_year = '"+plan_year+"' "+
					"and plan_week = '"+plan_week+"' "+
					"and plan_day = '"+plan_day+"' "+
					"and id_employee = '"+id_employee+"' "+
					
					cond+

					"and d.id = p.id_departments "+
					"group by datetime_start, datetime_end, id_planification_type, color, t.description, status, id_departments, d.description "+
					"order by datetime_start ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			int periodid = 0;
			
			String tp = "";
			String status = "";
			while(rs.next()) {
				
				status = ""+rs.getString("status");
				status = Utility.noNull(status);
				if(status.trim().length()>0) {
					//if(status.equals("A")||status.equals("X")||status.equals("L")) {
					if(status.equals("A")||status.equals("L")) {
						status = "disabled: 	true,  ";
					}
				}
				periodid+=1;
						
				// Se trasf=='S' --> Sempre disabilitato
				if(trasf.equals("S")) {
					status = "disabled: 	true,  ";
				}
				
				
				ini = sdf.format(rs.getTime("date_start"));
				fin = sdf.format(rs.getTime("date_end"));
				
				//System.out.println(plan_day+" "+ini+" - "+fin);
				
				ini = ini.replaceAll(":00", "");
				ini = ini.replaceAll(":30", "\\.5");
				
				fin = fin.replaceAll(":00", "");
				fin = fin.replaceAll(":30", "\\.5");
				
				//System.out.println(plan_day+" "+ini+", "+fin);
				
				// turno
				if(rs.getString("id_planification_type").equals("da24f14a-1436-4295-8f99-8b1a450b6277")) {
					tp = "turno";
					inputs+="$('<input type=\"hidden\" id=\"p_dati"+plan_day+id_employee+"_"+periodid+"\" name=\"p_dati"+plan_day+id_employee+"\" value=\""+Double.parseDouble(ini)+","+Double.parseDouble(fin)+"\" />').appendTo(scntDiv);";
					//inputs2+="$('<input type=\"text\" id=\"p_dati2"+plan_day+id_employee+"_"+periodid+"\" name=\"p_dati"+plan_day+id_employee+""+Double.parseDouble(ini)+","+Double.parseDouble(fin)+"\" value=\""+rs.getString("id_departments")+"\" />').appendTo(scntDiv);";
				
					if(!rs.getString("id_departments").equals(dep_orig)) {
						
						if(!trasf.equals("S")) {
							inputs2+="setTrasferta('"+plan_day+"', '"+id_employee+"', '"+ini+","+fin+"', '"+rs.getString("id_departments")+"', '"+rs.getString("ds")+"');";
						}
					}
					
					
				
				}
				else if(rs.getString("id_planification_type").equals("afc1e7c2-d84a-461b-a567-ce8771b8c6aa")) {
					tp = "malattia";
				}
				else if(rs.getString("id_planification_type").equals("575ed403-8924-4345-bf86-d2bf0534afb6")) {
					tp = "ferie";
				}
				else if(rs.getString("id_planification_type").equals("3694f2cb-1d27-4ec1-9cd2-dd2bc1bf73b4")) {
					tp = "rol";
				}
				else if(rs.getString("id_planification_type").equals("5d2cd2c5-1942-4138-863e-b5ca14e3fbf2")) {
					tp = "monte";
				}
				else if(rs.getString("id_planification_type").equals("33b31123-b8df-40dd-a4e5-f0458349e220")) {
					tp = "l104";
				}
				else if(rs.getString("id_planification_type").equals("54032bc1-7c3f-4175-934a-6e1fd30101ac")) {
					tp = "mat";
				}
				else if(rs.getString("id_planification_type").equals("199104ab-a536-4079-92f5-bbea69f469fe")) {
					tp = "all";
				}				
				else if(rs.getString("id_planification_type").equals("c08eba66-867c-4dff-970f-8332a3cc7785")) {
					tp = "asp";
				}
				else if(rs.getString("id_planification_type").equals("43d27024-37d7-4e25-ba05-ab36e41ec8dc")) {
					tp = "rc";
				}
				else if(rs.getString("id_planification_type").equals("37d58e57-26e9-4e94-938c-8d4097ae31a0")) {
					tp = "mal1";
				}
				else if(rs.getString("id_planification_type").equals("af51379a-858f-495b-a667-e17d27efabf2")) {
					tp = "mal2";
				}
				else if(rs.getString("id_planification_type").equals("efcd8c64-9355-43c2-a540-e096b366919c")) {
					tp = "com";
				}
				else if(rs.getString("id_planification_type").equals("05c266f5-1d5a-4fa9-bf83-ea6314303e59")) {
					tp = "pes";
				}
				
				arrayPeriodi+=""+Double.parseDouble(ini)+", "+Double.parseDouble(fin)+", ";
				arrayCss+= "false, { styleClass: '"+tp+"' },";
				
				
				//System.out.println("-----");
			}
			rs.close();
			
			// tolgo ultimo Byte
			if(arrayPeriodi.length()>0) {
				arrayPeriodi = arrayPeriodi.substring(0,arrayPeriodi.length()-2);
				
				planning = "$('."+plan_day+""+id_employee+"').empty();\n"
					+ "$(function() { $('."+plan_day+""+id_employee+"').limitslider({ "
					+ "values:		["+arrayPeriodi+"] , "
					+ "showRanges: true, "
					+ "step:		0.5, "
					+ "gap:		0, "
					+ "min:		6, "
					+ "max:		24, "
					+ "label:		false, "
					+ status
					+ "change: function( event, ui ) { "
		  			+ "    reloadInputs('"+plan_day+"' , '"+id_employee+"'); "
					+ "}, "
					+ "ranges:		["+arrayCss+" false, { styleClass: 'turno' }, false, { styleClass: 'turno' }, false] "
					+ "}); }); \n\n "+inputs+inputs2
					
					+ "";
				
				//planning = inputs;
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		
		return planning;
		
	}
	
	
	public static String getEmployeePlanningNewTot(Connection conn, String plan_year, 
			String plan_week, String plan_day, String id_employee, String trasf) throws SQLException {
		
		String planning = "";
		
		String inputs = "";
		
		Statement st = null;
		ResultSet rs = null;
		
		String sql   = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
		
		String ini = "";
		String fin = "";
		
		try {
			st=conn.createStatement();
			
			sql   = "select datetime_start, datetime_end, "+
					"to_timestamp(datetime_start) date_start, to_timestamp(datetime_end) date_end, "+
					"id_planification_type, color, description, status "+
					"from hr_planification p "+
					"left join hr_planification_type t on t.id = p.id_planification_type "+
					"where plan_year = '"+plan_year+"' "+
					"and plan_week = '"+plan_week+"' "+
					"and plan_day = '"+plan_day+"' "+
					"and id_employee = '"+id_employee+"' "+
					"group by datetime_start, datetime_end, id_planification_type, color, description, status "+
					"order by datetime_start ";
			rs=st.executeQuery(sql);
			
			int periodid = 0;
			
			while(rs.next()) {
				
				ini = sdf.format(rs.getTime("date_start"));
				fin = sdf.format(rs.getTime("date_end"));
				
				ini = ini.replaceAll(":00", "");
				ini = ini.replaceAll(":30", "\\.5");
				
				fin = fin.replaceAll(":00", "");
				fin = fin.replaceAll(":30", "\\.5");
				
				// turno
				if(rs.getString("id_planification_type").equals("da24f14a-1436-4295-8f99-8b1a450b6277")) {
					inputs+="<input type=\"hidden\" id=\"p_dati"+plan_day+id_employee+"_"+periodid+"\" name=\"p_dati"+plan_day+id_employee+"\" value=\""+Double.parseDouble(ini)+","+Double.parseDouble(fin)+"\" />";
				}
			}
			rs.close();
			
			planning = inputs;
					
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		
		return planning;
		
	}
	/**/
	
	public static String RandomString() {
		
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 11) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
  
/*		
		String saltStr = "";
        Random rand = new Random();
        
        // Generate random integers in range 0 to 999
        int rands = rand.nextInt(10000000);
        saltStr = new Integer(rands).toString();
 */
        return saltStr;
	}	
	
	/**/
	public static int CalcPunti(String punts, String flag) {

		double d1 = 0.00;
		double ret = 0;
		int ret_ = 0;
		
	   	 Connection conn = null;
	   	 Statement st = null;
	   	 ResultSet rs = null;
	   	 String sql = "";
	   	 String where = "";
	   	 String dat2 = "";
	   	 double perc_ = 0.00;

	   	 try{
			 conn = Utility.getConnPos();
			 st=conn.createStatement();
	   	 }catch(Exception e){
	   		e.printStackTrace();
	   	 }
		
		sql = "SELECT * from fidelity_param";
		try {
			 rs = st.executeQuery(sql);
			 if(rs.next()) {
				 perc_ = Double.parseDouble(rs.getString("percentuale"));
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}			
		
		
		
		if (!"".equals(Utility.noNull(punts))) {
			d1 = Double.parseDouble(punts);
			//ret = d1/100;
			if (flag.equals("C")) {
				ret = d1*perc_;	
			}else {
				ret = d1*perc_*100;
				//ret = Utility.arrotondaPerEccesso(ret, 0);
			}
			
		}
		
		//System.out.println("ret = " + ret);
		ret_ = (int)ret;
		
		return ret_;

	}	
	
	
	public static int CalcPuntiScarico(String punts, String flag) {

		double d1 = 0.00;
		double ret = 0;
		int ret_ = 0;
		
	   	 Connection conn = null;
	   	 Statement st = null;
	   	 ResultSet rs = null;
	   	 String sql = "";
	   	 String where = "";
	   	 String dat2 = "";
	   	 double perc_ = 0.00;

	   	 try{
			 conn = Utility.getConnPos();
			 st=conn.createStatement();
	   	 }catch(Exception e){
	   		e.printStackTrace();
	   	 }
		
		sql = "SELECT * from fidelity_param";
		try {
			 rs = st.executeQuery(sql);
			 if(rs.next()) {
				 perc_ = Double.parseDouble(rs.getString("percentuale"));
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}			
		
		
		
		if (!"".equals(Utility.noNull(punts))) {
			d1 = Double.parseDouble(punts);
			//ret = d1/100;
			if (flag.equals("C")) {
				ret = d1*perc_;	
			}else {
				//ret = d1*perc_*100;
				//ret = Utility.arrotondaPerEccesso(ret, 0);
				ret = d1*perc_;
			}
			
		}
		
		System.out.println("retscarico = " + ret);
		ret_ = (int)ret;
		
		return ret_;

	}		
	/**/
	
	public static String getEAN13CheckDigit(String ean) throws Exception {
		 
        if (ean.length() != 12) {
            throw new Exception("Please provide an input string of 12 chars. Current lenght: "+ean.length());
        }
        long tot = 0;
 
        for (int i = 0; i < 12; i++) {
            tot = tot + (Long.parseLong(String.valueOf(ean.charAt(i))) * (i % 2 == 0 ? 1 : 3));
        }
        return tot % 10 == 0 ? "0" : "" +(10-(tot % 10));
    }
	
	public static String getCharForNumber(int i) {
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(i, i+1);
	}
	
	public static String getXAxis(Connection conn, String idstores) {
		
		String xAxis = "";
		String min = "0";
		String max = "23";
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			
			/*
			sql="select " + 
				"min(extract(hour from fromhour)) m1, " + 
				"max(extract(hour from tohour)) m2 " + 
				"from dailyoperation_stores where id_stores in( "+idstores+" )";
			*/
			sql="select min(extract(hour from round_time(fromhour))) m1, "+
				"max(extract(hour from round_time(tohour))) m2 "+
				"from dailyoperation_stores where id_stores in( "+idstores+" )";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				int m1 = rs.getInt("m1");
				int m2 = rs.getInt("m2");
				/////////////
				if(m2==0)
					m2 = 24;
				/////////////
				
				String s1 = ""+rs.getString("m1");
				String s2 = ""+rs.getString("m2");
				s1=Utility.noNull(s1);
				s2=Utility.noNull(s2);
				
				
				if(s1.length()>0&&s2.length()>0) {
					
					
					
					min = rs.getString("m1");
					max = ""+m2;
					
					
				
					for(int i=m1;i<m2;i++) {
						String h1 = "00"+i;
						String h2 = "00"+(i+1);
						h1=h1.substring(h1.length()-2,h1.length());
						h2=h2.substring(h2.length()-2,h2.length());
						xAxis+=""+h1+"-"+h2+",";
					}
					
					if(xAxis.length()>0)
						xAxis = xAxis.substring(0, xAxis.length()-1);
				
				}
				else {
					xAxis = "00-01,01-02,02-03,03-04,04-05,05-06,06-07,07-08,08-09,09-10,10-11,11-12,12-13,13-14,14-15,15-16,16-17,17-18,18-19,19-20,20-21,21-22,22-23,23-24";
				}
			}
			else {
				xAxis = "00-01,01-02,02-03,03-04,04-05,05-06,06-07,07-08,08-09,09-10,10-11,11-12,12-13,13-14,14-15,15-16,16-17,17-18,18-19,19-20,20-21,21-22,22-23,23-24";
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
		
		return xAxis+"##@@"+min+"##@@"+max;
		
	}
	
	public static String cleanString(String text) 
    { 
        text = text.replace('\'', ' '); 
            return text; 
    } 
	
	public static String post(String urlEndpoint, String urlParameters) {
		
		String res = "";
		
		try {
			String USER_AGENT = "Mozilla/5.0";
	
	        String url = urlEndpoint;
	        URL obj = new URL(url);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
	        //add reuqest header
	        con.setRequestMethod("POST");
	        con.setRequestProperty("User-Agent", USER_AGENT);
	        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	
	        //String urlParameters = "iddoc=" + document_id + "&id_client=" + MainActivity.CLIENT_ID + "&val=" + values.replaceAll("null", "");
	
	        // Send post request
	        con.setDoOutput(true);
	        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	        wr.writeBytes(urlParameters);
	        wr.flush();
	        wr.close();
	
	        int responseCode = con.getResponseCode();
	        System.out.println("\nSending 'POST' request to URL : " + url);
	        System.out.println("Post parameters : " + urlParameters);
	        System.out.println("Response Code : " + responseCode);
	
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	
	        //print result
	        System.out.println(response.toString());
	        
	        res = response.toString();
	        
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return res;
		
	}
	
	
	public static String encrypt(String text, String key) {

		String s = "";
		try {
			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			// encrypt the text
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(text.getBytes());
			System.err.println(new String(encrypted));

			s = new String(encrypted, "UTF-8");
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return s;
	}
	
	
}