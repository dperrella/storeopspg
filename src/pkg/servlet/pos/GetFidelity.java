package pkg.servlet.pos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
@WebServlet("/pos/GetFidelity")
public class GetFidelity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFidelity() {
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
		
		try {
			conn=Utility.getConnPosext();
			
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
				cond+="where concat(upper(Fidelitycode), \n" + 
						"upper(fidelityname), upper(fidelitysurname), upper(fidelityaddress), " +
						"upper(Fidelitytel), upper(Fidelitymail), upper(Fidelitycodfiscale) ) \n" + 
						"like '%"+search.toUpperCase().replaceAll("'", "''")+"%' ";
			}
			
			sql  =  "select count(*) num from fidelitycard left join categoryfid on fidelitycategory = idcategoryfid "+cond+" ";
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

			
			sql = "select fidelityname, fidelitysurname, fidelitysex, fidelityaddress,"
					+ "fidelitytel, fidelitymail, fidelitycodfiscale, descriptioncategoryfid, "
					+ "fidelitystatus, fidelitycode, fidelitydate, idfidelity  from fidelitycard left join categoryfid on fidelitycategory = idcategoryfid "+cond+" ";
			if(colIndex.length()>0) {
				sql+="order by "+(Integer.parseInt(colIndex))+" "+dir+" ";
			}
			else {
				sql+="order by fidelitydate desc ";
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
				jsonRecord.add(Utility.noNull(rs.getString("fidelityname")));
				jsonRecord.add(Utility.noNull(rs.getString("fidelitysurname")));
				jsonRecord.add(Utility.noNull(rs.getString("fidelitysex")));
				jsonRecord.add(Utility.noNull(rs.getString("fidelityaddress")));
				jsonRecord.add(Utility.noNull(rs.getString("fidelitytel")));
				jsonRecord.add(Utility.noNull(rs.getString("fidelitymail")));
				jsonRecord.add(Utility.noNull(rs.getString("fidelitycodfiscale")));
				jsonRecord.add(Utility.noNull(rs.getString("descriptioncategoryfid")));
				jsonRecord.add(rs.getBoolean("fidelitystatus"));
				jsonRecord.add(Utility.noNull(rs.getString("fidelitycode")));
				jsonRecord.add(Utility.noNull(rs.getString("fidelitydate")));
				jsonRecord.add("");
				jsonRecord.add("");
				jsonRecord.add(Utility.noNull(rs.getString("idfidelity")));
				jsonArray.add(jsonRecord);
				
			}
			rs.close();
			st.close();
			
			
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
