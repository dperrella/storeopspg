package pkg.posws;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.utility.Utility;


@WebServlet("/giftprint")
public class giftprint extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public giftprint() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int num = Integer.parseInt(request.getParameter("Numero"));

		String idNew = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "", sql1 = "";
		conn=Utility.getConnPos();
		String resp = "";
		int j = 0;
		
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		/**/
		int seqs = 0, seqn = 0;
  		String clockss = "";
		try {
			sql = "select to_char(now(), 'dd-MM-yyyy HH24-MI-SS') as clock";
			rs = st.executeQuery(sql);
				while(rs.next()) {
					clockss = rs.getString("clock");
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
		

		sql = "SELECT last_value from package_session_gift ";
		try {
			rs = st.executeQuery(sql);
			while(rs.next()) {
				seqs = Integer.parseInt(rs.getString("last_value"));
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

		
			


		          for(int i=0; i < num ;i++)
		          {
		        	  
		      		
		    		try {
		    			sql = "SELECT nextval('package_number_gift') ";
						rs = st.executeQuery(sql);
						while(rs.next()) {
							seqn = Integer.parseInt(rs.getString("nextval"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    
						sql1 = sql1 + "insert into buonigiftcard (datetime,package_session,package_number,percorso,percorsodown) values ("+
							      "'"+clockss+"', "+seqs+","+seqn+", '', '');"; 
		                
		          }
		          
		          
					try {
						//sql = "insert into buonigiftcard (datetime,package_session,package_number,percorso,percorsodown) values ("+
						//      "'"+clockss+"', "+seqs+","+seq+", '"+Files+"', '"+Filesdown+"');"; 
						
						System.out.println("sql_insert = " + sql1);
						st.executeUpdate(sql1);
						
					}catch(Exception e) {
						e.printStackTrace();
					}
		          
		      		sql = "SELECT nextval('package_session_gift') ";
		    		try {
						rs = st.executeQuery(sql);
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		


		
		
		/**/

		
		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
