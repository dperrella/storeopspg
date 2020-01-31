package pkg.posws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;


@WebServlet("/GetStocks")
public class GetStocks extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetStocks() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String where = "";
		String Ids = Utility.noNull(request.getParameter("ids"));
		String cat = Utility.noNull(request.getParameter("cat"));
		String spr = Utility.noNull(request.getParameter("spr"));
		String art = Utility.noNull(request.getParameter("art"));
		String col = Utility.noNull(request.getParameter("col"));
		String war = Utility.noNull(request.getParameter("war"));
		String parts = Utility.noNull(request.getParameter("parts"));
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		String sql = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {

			conn = Utility.getConnPos();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			sql = "select distinct codart,parte,codcol,variante, "+
						"coalesce(p1,null,'-') p1, coalesce(p2,null,'-') p2, "+
						"coalesce(p3,null,'-') p3, coalesce(p4,null,'-') p4, coalesce(p5,null,'-') p5, coalesce(p6,null,'-') p6, "+ 
						"coalesce(p7,null,'-') p7, coalesce(p8,null,'-') p8, coalesce(p9,null,'-') p9 "+
						", coalesce(p10,null,'-') p10 "+
						", coalesce(p11,null,'-') p11 "+
						", coalesce(p12,null,'-') p12 "+
						", coalesce(p13,null,'-') p13 "+
						", coalesce(p14,null,'-') p14 "+
						", coalesce(p15,null,'-') p15 "+
						", coalesce(p16,null,'-') p16 "+
						", coalesce(p17,null,'-') p17 "+
						"from crosstab(' "+
						"select distinct product,posit,pezzi::text||''*''||taglia::text from get_stockgrid_web(''"+Ids+"'', ''"+war+"'', ''"+cat+"'', ''"+spr+"'', ''"+art+"'', ''"+col+"'', ''"+parts+"'') "+
						"order by 1,2'::text,'select distinct posit from size_position order by posit') "+ 
						"AS final_result(product text, p1 text,p2 text,p3 text,p4 text,p5 text,p6 text,p7 text,p8 text,p9 text,p10 text,p11 text,p12 text,p13 text,p14 text,p15 text,p16 text,p17 text) "+
						"join products on split_part(product,'/',1) = codart and split_part(product,'/',2) = parte and split_part(product,'/',3) = codcol and "+
						"coalesce(split_part(product,'/',4),null,'') = coalesce(variante,null,'') and products.idclient = '"+Ids+"' "+
						"order by codart,parte,codcol,variante;";
			
			System.out.println("SQL1 = " + sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("Codart", Utility.noNull(rs.getString("codart")));
				jsonObj.put("Parte", Utility.noNull(rs.getString("codart")));
				jsonObj.put("Codcol", Utility.noNull(rs.getString("codcol")));
				jsonObj.put("Variante", Utility.noNull(rs.getString("variante")));
				jsonObj.put("P1", Utility.noNull(rs.getString("p1")));
				jsonObj.put("P2", Utility.noNull(rs.getString("p2")));
				jsonObj.put("P3", Utility.noNull(rs.getString("p3")));
				jsonObj.put("P4", Utility.noNull(rs.getString("p4")));
				jsonObj.put("P5", Utility.noNull(rs.getString("p5")));
				jsonObj.put("P6", Utility.noNull(rs.getString("p6")));
				jsonObj.put("P7", Utility.noNull(rs.getString("p7")));
				jsonObj.put("P8", Utility.noNull(rs.getString("p8")));
				jsonObj.put("P9", Utility.noNull(rs.getString("p9")));
				jsonObj.put("P10", Utility.noNull(rs.getString("p10")));
				jsonObj.put("P11", Utility.noNull(rs.getString("p11")));
				jsonObj.put("P12", Utility.noNull(rs.getString("p12")));
				jsonObj.put("P13", Utility.noNull(rs.getString("p13")));
				jsonObj.put("P14", Utility.noNull(rs.getString("p14")));
				jsonObj.put("P15", Utility.noNull(rs.getString("p15")));
				jsonObj.put("P16", Utility.noNull(rs.getString("p16")));
				jsonObj.put("P17", Utility.noNull(rs.getString("p17")));
				jsonArray.add(jsonObj);
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
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		
		
		responseJson.put("listings", jsonArray);
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
