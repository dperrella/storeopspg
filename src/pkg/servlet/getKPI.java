package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.beans.Tables2;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetActiveAlarms
 */
@WebServlet("/servlet/ws/GetKpi")
public class getKPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getKPI() {
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
		ResultSet rs = null;
		String sql = "";
		
		String kpi = request.getParameter("kpi");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		
		from = from.substring(6,10)+"-"+from.substring(3,5)+"-"+from.substring(0,2);
		to = to.substring(6,10)+"-"+to.substring(3,5)+"-"+to.substring(0,2);
		
		String account_id = request.getParameter("account_id");
		String id_customer = request.getParameter("id_customer");
		
		
		String idstore = ""+request.getParameter("idstore");
		idstore=Utility.noNull(idstore);
		if(idstore.length()==0)
			idstore = "null";
		else 
			idstore = "'"+idstore+"'";
		
		String hour = ""+request.getParameter("hour");
		hour=Utility.noNull(hour);
		if(hour.length()==0)
			hour = "false";
		
		String draw = request.getParameter("draw");
		String start = request.getParameter("start");
		String search = ""+request.getParameter("search[value]");
		search=Utility.noNull(search);
		
		// ...
		System.out.println("search: "+search);
		
		int sta = (Integer.parseInt(start)/10);
		
		System.out.println(start);
		
		
		HttpSession session = request.getSession();
		// Formato numero
		DateFormat timeFormat = new SimpleDateFormat("kk:mm");
		DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			conn=Utility.getConnData();
			st=conn.createStatement();
		
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			int rowIni = 0;
			int rowFin = 0;
			int nrPag = 0;

			int numRow = 0;
			String prev = "";
			String next = "";
			String nrRecord = "10";
			
			String cond = "";
			if(search.length()>0)
				cond+=" where upper(description) like '%"+search.toUpperCase().replaceAll("'", "''")+"%' ";

			sql  =  "select count(*) num from counter.ws_analytics( "+
					"	'"+from+"', "+
					"	'"+to+"', "+
					"   '"+kpi.replaceAll(",", "*")+"', "+
					"	"+idstore+", "+
					"	'"+account_id+"', "+
					"	null, "+
					"	null, "+
					"	null, "+
					"	null, "+
					"	15, "+
					"   "+hour+", "+
					"   false, "+
					"   "+id_customer+"  "+
					") "+cond+" ";
			System.out.print(sql);
			rs=st.executeQuery(sql);
			int count = 0;
			if(rs.next()) {
				numRow=rs.getInt("num");
			}
			rs.close();
			
			nrPag = (int) Math.ceil(numRow/Double.parseDouble(nrRecord));
			
			int max = 0;
			
			for (int i=1; i<=nrPag; i++) {
				
				max+=Integer.parseInt(nrRecord);
				
				if(i==(sta+1)) {
					
					prev = ""+(i-1);
					next = ""+(i+1);
					
					rowIni = (max-Integer.parseInt(nrRecord));
					rowFin = Integer.parseInt(nrRecord);
					
				}
			}
			
			sql  =  "select * from counter.ws_analytics( "+
					"	'"+from+"', "+
					"	'"+to+"', "+
					"   '"+kpi.replaceAll(",", "*")+"', "+
					"	"+idstore+", "+
					"	'"+account_id+"', "+
					"	null, "+
					"	null, "+
					"	null, "+
					"	null, "+
					"	15, "+
					"   false, "+
					"   "+hour+", "+
					"   "+id_customer+"  "+
					") "+cond+" "+
					"limit "+rowFin+" offset "+rowIni+" ";
			
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			int $riga = 0;
			String $storeid="";
			String $doorid="";
			String $accountid="";
			String $description="";
			String $_year="";
			String $_month="";
			String $_day="";
			String $_hour="";
			String $_minute="";
			String $kpi="";
			String $kpi_description="";
			String $val="";
			
			while(rs.next()) {
				
				$riga = rs.getRow()+rowIni;
				$storeid = rs.getString("storeid");
				$doorid = rs.getString("doorid");
				$accountid = rs.getString("accountid");
				$description = rs.getString("description");
				$_year = rs.getString("year");
				$_month = rs.getString("month");
				$_day = rs.getString("day");
				$_hour = rs.getString("hour");
				$_minute = rs.getString("minute");
				$kpi = rs.getString("kpi");
				$kpi_description = rs.getString("kpi_description");
				$val = rs.getString("val");
				
				
				JSONArray jsonRecord = new JSONArray();
				jsonRecord.add($riga);
				jsonRecord.add($accountid);
				jsonRecord.add($storeid);
				jsonRecord.add($doorid);
				jsonRecord.add($description);
				jsonRecord.add($_year);
				jsonRecord.add($_month);
				jsonRecord.add($_day);
				jsonRecord.add($_hour);
				jsonRecord.add($_minute);
				jsonRecord.add($kpi);
				jsonRecord.add($kpi_description);
				jsonRecord.add($val);
				jsonArray.add(jsonRecord);
			
			}
			
			rs.close();
			
			responseJson.put("draw", draw);
			responseJson.put("recordsTotal", numRow);
			responseJson.put("recordsFiltered", numRow);
			responseJson.put("data", jsonArray);
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
			
		}
		catch(Exception e) {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
