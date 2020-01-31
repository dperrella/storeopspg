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
import pkg.beans.rfid.DateManager;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetActiveAlarms
 */
@WebServlet("/servlet/ws/GetStock")
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
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String idsite = request.getParameter("idsite");
		String code = ""+request.getParameter("code");
		code=Utility.noNull(code);
		String category = ""+request.getParameter("category");
		category = Utility.noNull(category);
		String season = ""+request.getParameter("season");
		season = Utility.noNull(season);
		String itemcode = ""+request.getParameter("itemcode");
		itemcode = Utility.noNull(itemcode);
		String colourcode = ""+request.getParameter("colourcode");
		colourcode = Utility.noNull(colourcode);
		String size = ""+request.getParameter("size");
		size = Utility.noNull(size);
		String location = ""+request.getParameter("location");
		location = Utility.noNull(location);
		
		
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
		
		
		HttpSession session = request.getSession();
		String idcus = session.getAttribute("idcus").toString();
		
		// Formato numero
		DateFormat timeFormat = new SimpleDateFormat("kk:mm");
		DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
		
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			int rowIni = 0;
			int rowFin = 0;
			int nrPag = 0;

			int numRow = 0;
			String prev = "";
			String next = "";
			String nrRecord = recordPerPage;
			
			String cond2= "";
			
			if(code.length()>0)
				cond2+= " and p.code = '"+code+"' ";
			if(idsite.length()>0)
				cond2+= " and s.id_site = "+idsite+" ";
			
			if(category.length()>0)
				cond2+=" and category = '"+category+"' ";
			if(season.length()>0)
				cond2+=" and season = '"+season+"' ";
			if(itemcode.length()>0)
				cond2+=" and itemcode = '"+itemcode+"' ";
			if(colourcode.length()>0)
				cond2+=" and colourcode = '"+colourcode+"' ";
			if(size.length()>0)
				cond2+=" and size = '"+size+"' ";
			if(location.length()>0)
				cond2+=" and l.id = "+location+" ";
			
			String cond = "";
			if(search.length()>0) {
				cond+="and concat(upper(code), \n" + 
						"upper(p.description), upper(colordesc), upper(l.description)) \n" + 
						"like '%"+search.toUpperCase().replaceAll("'", "''")+"%' ";
						
			}

			sql  =  "select count(distinct(code||colordesc||sizecod)) num from products p \n" + 
					"left join stockdiary s on p.sku = s.barcode\n" + 
					"left join location l on l.id = s.id_location\n" + 
					"where p.id_customer = "+idcus+" and id_site = "+idsite+"\n" + 
					" "+cond+" "+cond2+" ";
			System.out.println("1. "+sql);
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
			
			
			
			sql = "select code, p.description as name, colordesc, size, sizecod, l.description dsloc, sum(units) qty, l.id idloc\n" + 
					"from products p \n" + 
					"left join stockdiary s on p.sku = s.barcode\n" + 
					"left join location l on l.id = s.id_location\n" + 
					"where p.id_customer = "+idcus+" and id_site = "+idsite+"\n" + 
					" "+cond+" "+cond2+" "+
					"group by code, p.description, colordesc, size, sizecod, l.id, l.description\n" + 
					" ";
			if(colIndex.length()>0) {
				sql+="order by "+(Integer.parseInt(colIndex)+1)+" "+dir+" ";
			}
			else {
				sql+="order by code, colordesc, sizecod ";
			}
			sql+= "limit "+rowFin+" offset "+rowIni+" ";
			
			System.out.println("2. "+sql);
			rs=st.executeQuery(sql);
			
			int $riga = 0;
			code="";
			String name="";
			String color="";
			size="";
			
			int qty=0;
			
			
			while(rs.next()) {
				
				$riga = rs.getRow()+rowIni;
				code = rs.getString("code");
				name = rs.getString("name");
				color = rs.getString("colordesc");
				size = rs.getString("size");
				
				qty = rs.getInt("qty");
				
				
				JSONArray jsonRecord = new JSONArray();
				jsonRecord.add($riga);
				jsonRecord.add(code);
				jsonRecord.add(name);
				jsonRecord.add(color);
				jsonRecord.add(size);
				jsonRecord.add(rs.getString("dsloc"));
				jsonRecord.add(qty);
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
