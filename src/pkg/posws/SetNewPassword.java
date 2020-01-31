package pkg.posws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pkg.properties.ReadProperties;
import pkg.utility.JSendMail;
import pkg.utility.Utility;

@WebServlet("/SetNewPassword")
public class SetNewPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SetNewPassword() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String idusr = (String) Utility.noNull(request.getParameter("usr"));
		String mail = (String) Utility.noNull(request.getParameter("ema"));
		
		String ids = "";

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		conn = Utility.getConnPos();
		String resp = "";
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/**/
		String flag = "";
		String Pwds = "";
		String lang = "IT";

		try{
			
			sql  =  "select * from zutenti where upper(idusr) = upper('"+idusr+"') and upper(mail) = upper('"+mail+"')";
			rs = st.executeQuery(sql);
			if(rs.next()) {
				ids = rs.getString("idusr");
				lang = rs.getString("language");
			}
			rs.close();
			
			Pwds = Utility.RandomString();
			
			if (!"".equals(Utility.noNull(ids))){
	    		sql = "update zutenti set pwd = md5('"+Pwds+"') WHERE upper(idusr) = upper('"+idusr+"')";
				st.executeUpdate(sql);
								
				String footer = "<br><br><small>This e-mail is strictly reserved to the specified users. Should the message be received by mistake, we kindly ask You to notify it to the sender and then to cancel the message.</small>";
				String contact = "<br><br>Mail: <a href='mailto:info@montoro.it'>info@montoro.it</a><br>Phone : 081-5366699";
				String sub = "Montoro Recovery Password";
				String mess = "La sua nuova password composta da 10 caratteri è:<br><br>"+Pwds+"<br><br>Per la sua sicurazza, La invitiamo a cambiarla quanto prima.<br><br>Montoro Team" +
							  contact +
							  footer;
				String att = "";
				String fr = "info@montoro.it";

				JSendMail.msgMail(mail, sub, mess, att, fr);

				flag = "OK";
				
			}else{
				
				flag = "KO";
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			flag = "KO";
		}
	
		resp = flag+"$"+lang;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
