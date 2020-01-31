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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

/**
 * Servlet implementation class GetNazioni
 */
@WebServlet("/servlet/ws/SearchDocuments")
public class SearchDocuments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchDocuments() {
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
		
		String warehouse = ""+request.getParameter("warehouse");
		warehouse = Utility.noNull(warehouse);
		
		String type = ""+request.getParameter("type");
		type = Utility.noNull(type);
		
		String q = ""+request.getParameter("q");
		q = Utility.noNull(q);
		
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
		
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			String cond = "";
			if(type.equals("B")) {
				cond+=" and  e.barcode = '"+q+"' ";
			}
			else {
				cond+=" and  e.epc = '"+q+"' ";
			}
			
			DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
			
			sql   = "select a.id, a.docnumber, a.doctype, a.docdate, "
					+ "b.description as origin_descr, c.description as dest_descr, "
					+ "d.description as doc_descr, 1 as verso "
					+ "from documents a, warehouse b, warehouse c, doctype d , documents_details e "
					+ "where destination_warehouse = '"+warehouse+"' "
					+ "and idstep = 0 and a.origin_warehouse = b.id and a.destination_warehouse = c.id "
					+ "and a.doctype::integer = d.id and a.status = 'X' "
					+ "and a.id = e.id_documents "+cond+" "
					+ "union "
					+ "select a.id, a.docnumber, a.doctype, a.docdate , "
					+ "b.description as origin_descr, c.description as dest_descr, "
					+ "d.description as doc_descr, -1 as verso "
					+ "from documents a, warehouse b, warehouse c, doctype d, documents_details e "
					+ "where origin_warehouse = '"+warehouse+"' "
					+ "and idstep = 1 and a.origin_warehouse = b.id and a.destination_warehouse = c.id "
					+ "and a.doctype::integer = d.id and a.status = 'X' "
					+ "and a.id = e.id_documents "+cond+" "
					+ "order by docdate ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", rs.getString("id"));
				jsonObj.put("docnumber", rs.getString("docnumber"));
				jsonObj.put("doctype", rs.getString("doctype"));
				jsonObj.put("docdate", dateIta.format(rs.getDate("docdate")));
				jsonObj.put("origin_descr", rs.getString("origin_descr"));
				jsonObj.put("dest_descr", rs.getString("dest_descr"));
				jsonObj.put("doc_descr", rs.getString("doc_descr"));
				jsonObj.put("verso", rs.getString("verso"));
				jsonArray.add(jsonObj);
			}
			rs.close();
			
			responseJson.put("listings", jsonArray);
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
