package pkg.servlet.pos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;	
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/pos/GetSalesSituation")
public class GetSalesSituation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSalesSituation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			conn=Utility.getConnPos();
			
			String store = request.getParameter("store");
			String from = request.getParameter("from");
			String to = request.getParameter("to");
			
			String draw = request.getParameter("draw");
			String start = request.getParameter("start");
			String search = ""+request.getParameter("search[value]");
			search=Utility.noNull(search);
			
			// ...
			System.out.println("search: "+search);
			
			String colIndex = ""+request.getParameter("order[0][column]");
			colIndex = Utility.noNull(colIndex);
			String dir = ""+request.getParameter("order[0][dir]");
			dir = Utility.noNull(dir);
			
			System.out.println("order: "+colIndex);
			System.out.println("dir: "+dir);
			
			String recordPerPage = ""+request.getParameter("length");
			recordPerPage = Utility.noNull(recordPerPage);
			if(recordPerPage.length()==0)
				recordPerPage = "10";
			
			System.out.println("length: "+recordPerPage);
			
			int sta = (Integer.parseInt(start)/Integer.parseInt(recordPerPage));
			
			System.out.println(start);
			
			
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			int rowIni = 0;
			int rowFin = 0;
			int nrPag = 0;

			int numRow = 0;
			String prev = "";
			String next = "";
			String nrRecord = recordPerPage;
			
			String cond = "";
			if(search.length()>0) {
				cond+="where concat(upper(ticket), \n" + 
						"upper(name)) \n" + 
						"like '%"+search.toUpperCase().replaceAll("'", "''")+"%' ";
			}
			
			sql  =  "select count(*) num from st_wsticket('"+store+"','"+from+"', 0) "+cond+" ";
			System.out.println("1. "+sql);
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			int count = 0;
			if(rs.next()) {
				numRow=rs.getInt("num");
			}
			rs.close();
			st.close();
			
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

			
			sql = "select * from st_wsticket('"+store+"','"+from+"', 0) "+cond+" ";
			if(colIndex.length()>0) {
				sql+="order by "+(Integer.parseInt(colIndex))+" "+dir+" ";
			}
			else {
				sql+="order by datenew desc ";
			}
			sql+= "limit "+rowFin+" offset "+rowIni+" ";
			
			System.out.println("2. "+sql);
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			
			int $riga = Integer.parseInt(start);
			
			while(rs.next()) {
				$riga+=1;
				
				JSONArray jsonRecord = new JSONArray();
				jsonRecord.add($riga);
				//jsonRecord.add(rs.getString("ticket"));
				jsonRecord.add(rs.getString("datenew"));
				jsonRecord.add(df.format(rs.getDouble("total")));
				//jsonRecord.add(rs.getString("name"));
				jsonRecord.add("");
				jsonRecord.add("");
				jsonRecord.add(rs.getString("ticket"));
				
				jsonArray.add(jsonRecord);
				
			}
			rs.close();
			st.close();
			
			
			responseJson.put("draw", draw);
			responseJson.put("recordsTotal", numRow);
			responseJson.put("recordsFiltered", numRow);
			responseJson.put("data", jsonArray);
			
			
			
			
			
			
			
			
			sql = "select sum(incasso) incasso,"
					+ "sum(pezzi) pezzi,"
					+ "sum(pezzi_medi) pezzi_medi,"
					+ "sum(scontrini)scontrini,"
					+ "sum(scontrino_medio)scontrino_medio "
					+ "from totali_giornalieri where idclient = ? "
					+ "and datenew::date >= ?::date and datenew::date <= ?::date ";
			st = conn.prepareStatement(sql);
			st.setString(1, store);
			st.setDate(2, java.sql.Date.valueOf(from));
			st.setDate(3, java.sql.Date.valueOf(to));
			
			System.out.println(st.toString());
			
			rs=st.executeQuery();
			
			if(rs.next()) {
				responseJson.put("incasso", df.format(rs.getDouble("incasso")));
				responseJson.put("pezzi", rs.getInt("pezzi"));
				responseJson.put("pezzi_medi", df.format(rs.getDouble("pezzi_medi")));
				responseJson.put("scontrini", rs.getString("scontrini"));
				responseJson.put("scontrino_medio", df.format(rs.getDouble("scontrino_medio")));
			}
			rs.close();
			st.close();
			
			
			// datediff
			int datediff = Utility.getDateDiffDays3(from, to);
			System.out.println(datediff);
			int dFrom = Integer.parseInt(from.substring(8,10));
			
			
			if(datediff==0) {
				
				// add day 
				Date _to = iso.parse(to);
				Calendar c = Calendar.getInstance();
		        c.setTime(_to);
		        c.add(Calendar.DATE, 1);
		        
		        to = iso.format(c.getTime());
				
				// day
				sql = "SELECT date_trunc('hour',h)::time, coalesce(sum(total), null, 0) total, coalesce(sum(units) , null, 0) units, count(ticket) ticket\n" + 
						"from generate_series\n" + 
						"        ( '"+from+"'::date \n" + 
						"        , '"+to+"'::date\n" + 
						"        , '1 hour'::interval) h\n" + 
						"left join st_wsticket2('"+store+"', '"+from+"', 0)  on date_trunc('hour',datenew)::time = h::time\n" + 
						"group by h\n" + 
						"order by date_trunc('hour',h)::time";
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				
				String tot="";
				String pcs="";
				String tic="";
				while(rs.next()) {
					tot+=rs.getDouble("total")+",";
					pcs+=rs.getInt("units")+",";
					tic+=rs.getInt("ticket")+",";
				}
				rs.close();
				st.close();
				
				if(tot.length()>0)
					tot = tot.substring(0,tot.length()-1);
				if(pcs.length()>0)
					pcs = pcs.substring(0,pcs.length()-1);
				if(tot.length()>0)
					tic = tic.substring(0,tic.length()-1);
				
				responseJson.put("tot", tot);
				responseJson.put("pcs", pcs);
				responseJson.put("tic", tic);
				
				responseJson.put("xAxis", "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23");
				
				
			}
			else {
				
				sql = "SELECT d::date, coalesce(sum(total), null, 0) total, coalesce(sum(units) , null, 0) units, count(ticket) ticket\n" + 
						"from generate_series\n" + 
						"        ( '"+from+"'::date \n" + 
						"        , '"+to+"'::date\n" + 
						"        , '1 day'::interval) d\n" + 
						"left join st_wsticket3('"+store+"', '"+from+"', '"+to+"', 0)  on datenew::date = d::date\n" + 
						"group by d\n" + 
						"order by d::date";
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				
				String tot="";
				String pcs="";
				String tic="";
				while(rs.next()) {
					tot+=rs.getDouble("total")+",";
					pcs+=rs.getInt("units")+",";
					tic+=rs.getInt("ticket")+",";
				}
				rs.close();
				st.close();
				
				if(tot.length()>0)
					tot = tot.substring(0,tot.length()-1);
				if(pcs.length()>0)
					pcs = pcs.substring(0,pcs.length()-1);
				if(tot.length()>0)
					tic = tic.substring(0,tic.length()-1);
				
				responseJson.put("tot", tot);
				responseJson.put("pcs", pcs);
				responseJson.put("tic", tic);
				
				String xAxis = "";
				for(int i=dFrom; i<=(dFrom+datediff); i++) {
					xAxis+=i+",";
				}
				
				if(xAxis.length()>0)
					xAxis = xAxis.substring(0,xAxis.length()-1);
			
				responseJson.put("xAxis", xAxis);
				
			}
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
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
		doGet(request,response);
	}

}
