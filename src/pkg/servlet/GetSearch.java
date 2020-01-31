package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pkg.properties.ReadProperties;
import pkg.utility.Utility;


public class GetSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetSearch() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		String str = request.getParameter("Str").replaceAll("'", "''");

		String idNew = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		conn=Utility.getConnHR();
		String resp = "";
		String query = "";
		String name = "";
		String surname = "";
		String employee = "";
		String date_end = "";
		String btn = "";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		/**/
		
		if (!"".equals(str)) {
			query = " where upper(e.name) like upper('%"+str+"%') or upper(e.surname) like upper('%"+str+"%') or a.number like '%"+str+"%' ";
		}

		try {
			st=conn.createStatement();
			sql="select a.id, a.number, max(To_timestamp(b.date_end))  dataFine, e.name, e.surname " + 
					"from hr_badge a " + 
					"left join rel_emp_badge b on a.id = b.id_badge " + 
					"left join hr_employee e on b.id_employ = e.id " + 
					query + 
					"group by a.id, a.number, e.name, e.surname " + 
					"order by a.number";
			//System.out.println("sql = " + sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				
				name = ""+rs.getString("name");
				surname = ""+rs.getString("surname");
				date_end = ""+rs.getString("dataFine");
				
				name = Utility.noNull(name);
				surname = Utility.noNull(surname);
				date_end = Utility.noNull(date_end);
				if(date_end.length()>0)
					date_end = df.format(rs.getDate("dataFine"));
				
				if(surname.length()==0)
					employee = "-";
				else
					employee = name+" "+surname;
				
				if(date_end.length()==0)
					date_end = "-";
				
				btn = "<a href=\"#\" data-dismiss=\"modal\" class=\"btn btn-default btn-xs\" "+
						  "onclick=\"document.getElementById('id_badge').value = '"+rs.getString("id")+"';document.getElementById('nr_badge').value = '"+rs.getString("number")+"'\">"+propLang.get("pop_ba_sel")+"</a>";

				
				 resp += "<tr><td>"+rs.getString("number")+"</td>" +
						"<td>"+employee+"</td>" +
						"<td>"+date_end+"</td>" +
						"<td>"+btn+"</td></tr>";
			}
			rs.close();		
			//conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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


		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
