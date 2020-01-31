package pkg.rfidws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pkg.beans.Rfid_config;
import pkg.beans.Rfid_devices;
import pkg.beans.Rfid_documents;
import pkg.beans.Rfid_flow;
import pkg.beans.Rfid_scanner;
import pkg.beans.Rfid_scannerdetail;
import pkg.utility.SGTIN96;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/ScannerDetail")
public class ScannerDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScannerDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement st = null;
		
		int idscan = Integer.parseInt(request.getParameter("idscan"));
		String package_id = ""+request.getParameter("package_id");
		package_id = Utility.noNull(package_id);
		
		String epc = request.getParameter("epc");
		String code = ""+request.getParameter("code");
		code = Utility.noNull(code);
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
		
			Rfid_scannerdetail bean = new Rfid_scannerdetail();
			bean.setIdscan(idscan);
			
			String temp[] = epc.split(",");
			int count = 0;
			String barcode = "";
			int nrscan = Integer.parseInt(Utility.getNewProgr(conn, "scannerdetail", "nrscan", "where idscan = "+idscan+" "));
			for(int i=0;i<temp.length;i++) {
				
				count++;
				
				if(code.length()==0) {
					try {
		            	System.out.println("|"+temp[i]+"|");
		            	barcode = SGTIN96.decodeEpc(temp[i]);
		            }
		            catch(Exception e) {
		            	barcode = "?????????????";
		            	e.printStackTrace();
		            }
					bean.setBarcode(barcode);
					bean.setEpc(temp[i]);
					bean.setNrscan(nrscan);
				}
				else {
					
					
					
					String temp2[]=temp[i].split(";");
					
					System.out.println(temp2[1]+" "+temp2[0]);
					
					bean.setBarcode(temp2[1]);
					bean.setEpc(temp2[0]);
					bean.setNrscan(nrscan);
				}
					
				bean.insert(conn);
				
			}
			
			// Passa anche il package id per scrivere documents_details
			ResultSet rs=st.executeQuery("select * from scan_clean("+idscan+", '"+package_id+"')");
			rs.close();
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("epc_count", count);
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jsonObj.toString());
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
