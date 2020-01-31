package pkg.rfidws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pkg.properties.ReadProperties;
import pkg.utility.Utility;


public class GetFlowStep extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetFlowStep() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("IN GETFLOWSTEP");
		HttpSession session = request.getSession();
		//ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		conn=Utility.getConnRfid();
		String resp = "";
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		/**/
		String option = "<option value=\"\">-</option>";

		try{
			
			if (request.getParameter("action").equals("FLOW")) {
				
				/*
				sql = "select rel_dev_step.idflow idparam, description from rel_dev_step "
						+ "left join flow on rel_dev_step.idflow = flow.idflow where id_device = "+request.getParameter("param")+ " "
						+ "group by rel_dev_step.idflow, description ";
				*/
				sql = "select idflow idparam, description from flow ";
			}else {
				String[] par = request.getParameter("param").split("-");
				
				int flow = Integer.parseInt(par[0]);
				int dev = Integer.parseInt(par[1]);
				
				//sql = "select rel_dev_step.idstep idparam, description from rel_dev_step left join step on rel_dev_step.idstep = step.idstep where rel_dev_step.idflow = "+flow+" and id_device = "+dev;
				sql = "select idstep idparam, description from step where idflow = "+flow+" ";
				
			}
    		
    		System.out.println("sql = " + sql);
    		rs = st.executeQuery(sql);
    		
    		while(rs.next()) {
    			option += "<option value='"+rs.getString("idparam")+"'>"+rs.getString("idparam")+" - "+Utility.noNull(rs.getString("description"))+"</option>";
    		}
    		
    		rs.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		

				
		resp = option;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
	
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
