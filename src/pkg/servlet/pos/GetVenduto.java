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
@WebServlet("/pos/GetVenduto")
public class GetVenduto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetVenduto() {
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
				cond+="and concat(upper(codart), \n" + 
						"upper(codartalt), upper(parte), upper(code), upper(variante) ) \n" + 
						"like '%"+search.toUpperCase().replaceAll("'", "''")+"%' ";
			}
			
			sql  =  "select count(*) num from (select * from venduto_giornaliero) a where idclient = '"+store+"' and giorno >= '"+from+"' and giorno <= '"+to+"' "+cond+" ";
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

			
			sql = "select * from (select * from venduto_giornaliero) a where idclient = '"+store+"' and giorno >= '"+from+"' and giorno <= '"+to+"' "+cond+" ";
			if(colIndex.length()>0) {
				sql+="order by "+(Integer.parseInt(colIndex))+" "+dir+" ";
			}
			else {
				sql+="order by giorno desc,codart,parte,codcol,variante ";
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
				jsonRecord.add(Utility.noNull(rs.getString("giorno")));
				jsonRecord.add(Utility.noNull(rs.getString("codart")));
				jsonRecord.add(Utility.noNull(rs.getString("codartalt")));
				jsonRecord.add(Utility.noNull(rs.getString("taglia")));
				jsonRecord.add(Utility.noNull(rs.getString("parte")));
				jsonRecord.add(Utility.noNull(rs.getString("codcol")));
				jsonRecord.add(Utility.noNull(rs.getString("variante")));
				jsonRecord.add(Utility.noNull(rs.getString("pezzi")));
				jsonRecord.add(Utility.noNull(df.format(rs.getDouble("valore"))));
				jsonRecord.add(Utility.noNull(rs.getString("code")));
				jsonRecord.add(Utility.noNull(rs.getString("idclient")));
				jsonRecord.add("");
				jsonRecord.add("");
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
