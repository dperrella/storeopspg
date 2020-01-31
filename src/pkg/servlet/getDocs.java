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
@WebServlet("/servlet/ws/GetDocs")
public class getDocs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getDocs() {
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
		
		String idflow = request.getParameter("idflow");
		String idstep = request.getParameter("idstep");
		String idsite = request.getParameter("idsite");
		String status = request.getParameter("status");
		
		String from = ""+request.getParameter("from");
		from = Utility.noNull(from);
		String to = ""+request.getParameter("to");
		to = Utility.noNull(to);
		
		String id_customer = request.getParameter("idcus");
		
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
			String cond3= "";
			String cond4= "";
			if(idflow.length()>0)
				cond2+= " and d.idflow = "+idflow+" ";
			if(idstep.length()>0)
				cond2+= " and d.idstep = "+idstep+" ";
			if(idsite.length()>0)
				cond2+= " and d.idsite = "+idsite+" ";
			if(status.equalsIgnoreCase("null")) {
				cond3 = " and d.status is null ";
			}
			else if(status.equalsIgnoreCase("X")) {
				cond3 = " and d.status = 'X' ";
			}
			else {
				cond3 = " ";
			}
			
			if(from.length()>0)
				cond4+=" and doc_date::date >= '"+from+"' ";
			if(to.length()>0)
				cond4+=" and doc_date::date <= '"+to+"' ";
			
			String cond = "";
			if(search.length()>0) {
				//cond+=" and upper(doc_ref) like '%"+search.toUpperCase().replaceAll("'", "''")+"%' ";
				cond+="and concat(upper(doc_ref), \n" + 
						"(select count(*) from documents_detail dd where dd.iddoc = d.id)::text , \n" + 
						"(select count(distinct(epc)) from scanner_detail sd where sd.iddoc = d.id)::text) "+
						"like '%"+search.toUpperCase().replaceAll("'", "''")+"%' ";
						
			}

			sql  =  "select count(*) num from documents d  "
					+ "where d.idflow in(select idflow from flow where id_customer = "+idcus+") "
					+ " "+cond+" "+cond2+ " "+cond3+" "+cond4;
			System.out.println(sql);
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
			
			
			
			sql = "select ROW_NUMBER () over() as row, coalesce(status, null, '') status, "
					+ "doc_number, doc_ref, doc_date, step.description dsstep, " + 
					"sites.description dssite, documents_type.description dstype, " + 
					"(select count(*) from documents_detail dd where dd.iddoc = d.id) nrtheo, "+
					"(select count(distinct(epc)) from scanner_detail sd where sd.iddoc = d.id and sd.epc is not null and sd.epc != '') nrread, d.id, d.doc_origin, d.idstep "+	
					"from documents d " + 
					"join step on step.idstep = d.idstep " + 
					"join sites on sites.id = d.idsite " + 
					"join documents_type on documents_type.id_doctype = d.id_document_type "+
					"where d.idflow in(select idflow from flow where id_customer = "+id_customer+") "+
					" "+cond+" "+cond2+" "+cond3+" "+cond4+" ";
			if(colIndex.length()>0) {
				sql+="order by "+(Integer.parseInt(colIndex)+1)+" "+dir+" ";
			}
			else {
				sql+="order by doc_date desc ";
			}
			sql+= "limit "+rowFin+" offset "+rowIni+" ";
			
			System.out.println("---"+sql);
			rs=st.executeQuery(sql);
			
			int $riga = 0;
			String id="";
			String doc_number="";
			String doc_date="";
			String doc_ref="";
			String doc_origin="";
			idflow="";
			idstep="";
			idsite="";
			String previous_iddoc="";
			status="";
			String id_document_type="";
			String dsstep="";
			String dssite="";
			String dstype="";
			
			String _idstep = "";
			int nrtheo=0;
			int nrread=0;
			
			String status2="";
			while(rs.next()) {
				
				$riga = rs.getRow()+rowIni;
				doc_number = rs.getString("doc_number");
				doc_ref = rs.getString("doc_ref");
				doc_date = rs.getString("doc_date");
				dsstep = rs.getString("dsstep");
				dssite = rs.getString("dssite");
				dstype = rs.getString("dstype");
				id = rs.getString("id");
				status = ""+rs.getString("status");
				status = Utility.noNull(status);
				status2=status;
				if(status.length()==0) {
					status = "<span class=\"label bg-success\">OPN</span>";
				}
				else {
					status = "<span class=\"label bg-light\">CLS</span>";
				}
				nrtheo=rs.getInt("nrtheo");
				nrread=rs.getInt("nrread");
				
				_idstep = rs.getString("idstep");
				
				doc_origin = rs.getString("doc_origin");
				
				JSONArray jsonRecord = new JSONArray();
				jsonRecord.add($riga);
				jsonRecord.add(status);
				jsonRecord.add(doc_number);
				jsonRecord.add(doc_ref);
				jsonRecord.add(doc_date);
				jsonRecord.add(dsstep);
				jsonRecord.add(dssite);
				jsonRecord.add(dstype);
				jsonRecord.add(nrtheo);
				jsonRecord.add(nrread);
				jsonRecord.add(id);
				jsonRecord.add(id);
				jsonRecord.add(id);
				jsonRecord.add(id);
				jsonRecord.add(status2);
				
				jsonRecord.add(doc_origin);
				jsonRecord.add(_idstep);
				
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
