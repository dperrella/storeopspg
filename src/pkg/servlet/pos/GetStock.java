package pkg.servlet.pos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
@WebServlet("/pos/GetStock")
public class GetStock extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStock() {
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
		
		try {
			conn=Utility.getConnPos();
			
			String store = ""+request.getParameter("store");
			store = Utility.noNull(store);
			
			// filtri
			String location = ""+request.getParameter("location");
			location = Utility.noNull(location);
			String variant = ""+request.getParameter("variant");
			variant = Utility.noNull(variant);
			String supplier = ""+request.getParameter("supplier");
			supplier = Utility.noNull(supplier);
			String size = ""+request.getParameter("size");
			size = Utility.noNull(size);
			
			String cond2 = "";
			if(location.length()>0)
				cond2+= " and location = '"+location+"' ";
			if(variant.length()>0)
				cond2+= " and variante = '"+variant+"' ";
			if(supplier.length()>0)
				cond2+= " and fornitore = '"+supplier+"' ";
			if(size.length()>0)
				cond2+= " and taglia = '"+size+"' ";
			
			if(store.length()>0)
				cond2+= " and g.idclient = '"+store+"' ";
			
			
			String q = ""+request.getParameter("q");
			q = Utility.noNull(q);
			
			
			// parametri in griglia
			
			String draw = request.getParameter("draw");
			String start = request.getParameter("start");
			String search = ""+request.getParameter("search[value]");
			search=Utility.noNull(search);
			
			
			if(q.length()>0)
				search = search	+q;
			
			
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
				cond+="and concat(upper(location), \n" + 
						"upper(codice_alternativo), upper(g.descrizione), upper(fornitore), upper(code), upper(codart)) \n" + 
						"like '%"+search.toUpperCase().replaceAll("'", "''")+"%' ";
			}
			
			sql  =  "select count(*) num from giacenze g "
					+ "left join app_stores s on s.idclient = g.idclient "
					+ "where 1=1 "+cond+" "+cond2;
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

			
			sql = "select s.descrizione as stodesc, g.* from giacenze g "
					+ "left  join app_stores s on s.idclient = g.idclient "
					+ "where 1=1 "+cond+" "+cond2;
			if(colIndex.length()>0) {
				sql+="order by "+(Integer.parseInt(colIndex))+" "+dir+" ";
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
				jsonRecord.add(rs.getString("stodesc"));
				jsonRecord.add(rs.getString("code"));
				jsonRecord.add(rs.getString("codart"));
				jsonRecord.add(rs.getString("codice_alternativo").trim());
				jsonRecord.add(rs.getString("descrizione"));
				jsonRecord.add(rs.getString("fornitore"));
				jsonRecord.add(rs.getString("tessuto"));
				jsonRecord.add(rs.getString("variante"));
				jsonRecord.add(rs.getString("taglia"));
				jsonRecord.add(rs.getString("location"));
				jsonRecord.add(rs.getLong("units"));
				jsonRecord.add("");
				jsonRecord.add(rs.getString("idclient"));
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
