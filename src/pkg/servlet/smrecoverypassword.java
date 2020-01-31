package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pkg.properties.ReadProperties;
import pkg.utility.JSendMail;
import pkg.utility.JSendMailSSL;
import pkg.utility.Utility;

import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet("/smrecoverypassword")
public class smrecoverypassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public smrecoverypassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		ReadProperties propLang = new ReadProperties("connection.properties");
		
		//String idusr = request.getParameter("Username");
		String idusr = "";
		String mail = request.getParameter("Mail");
		String pwdm5 = "";
		String link_ = "";
		String Link_ = "";
		String footer = "";
		String contact = "";
		String flag = "OK";
		int exist = 0;
		
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		conn=Utility.getConnAdmin();
		String resp = "";
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		// pinko pwd = 463f6784f7e2ab722fd3263b13630f18
		
		try {
				
			//sql = "select count(*) from zutenti where upper(idusr) = '"+idusr.toUpperCase()+"' and upper(mail) = '"+mail.toUpperCase()+"'";
			sql = "select count(*) from zutenti where upper(mail) = '"+mail.toUpperCase()+"'";
			//st.executeUpdate(sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				exist = Integer.parseInt(rs.getString("count"));
			}
			rs.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//System.out.println("Exist = " + exist);
		if (exist > 0){
			
			char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890_-()".toCharArray();
			StringBuilder sb = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < 25; i++) {
			    char c = chars[random.nextInt(chars.length)];
			    sb.append(c);
			}
			String output = sb.toString();
			
			try {
				//sql = "update zutenti set pwd = md5('"+output+"') where upper(idusr) = '"+idusr.toUpperCase()+"' and upper(mail) = '"+mail.toUpperCase()+"'";
				sql = "update zutenti set pwd = md5('"+output+"') where upper(mail) = '"+mail.toUpperCase()+"'";
				st.executeUpdate(sql);
				
				/**/
				//sql = "select pwd from zutenti where upper(idusr) = '"+idusr.toUpperCase()+"' and upper(mail) = '"+mail.toUpperCase()+"'";
				sql = "select idusr,pwd from zutenti where upper(mail) = '"+mail.toUpperCase()+"'";
				//st.executeUpdate(sql);
				rs = st.executeQuery(sql);
				while(rs.next()) {
					pwdm5 = rs.getString("pwd");
					idusr = rs.getString("idusr");
				}
				rs.close();

				/**/
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			//link_ = "<a href='http://localhost:8080/Counter/login-esegui.htm?username="+idusr+"&password="+output+"&link=app/main/profilo.htm'>Effettua il login direttamente</a>";
			link_ = "<a href='"+propLang.get("httpweb")+"login-esegui.htm?username="+idusr+"&password="+output+"'>Effettua il login direttamente</a>";
			
			footer = "<br><br><small>This e-mail is strictly reserved to the specified users. Should the message be received by mistake, we kindly ask You to notify it to the sender and then to cancel the message.</small>";
			//contact = "<br><br>Mail: <a href='mailto:smart-suite@checkpt.com'>smart-suite@checkpt.com</a><br>Phone : "+propLang.get("chkTel")+"<br>Web: http://cloud.smart-control.it/Counter";
			contact = "<br><br>Mail: <a href='mailto:"+propLang.get("chkMail")+"'>"+propLang.get("chkMail")+"</a><br>Phone : "+propLang.get("chkTel")+"<br>Web: http://cloud.smart-control.it/Counter";
			String sub = "Smart-Suite Recovery Password";
			String mess = "La sua nuova password composta da 25 caratteri e':<br><br>"+output+"<br><br>"+link_+"<br><br>Per la sua sicurazza, La invitiamo a cambiarla quanto prima.<br><br>Smart-Suite Team" +
						  contact +
						  footer;
			
			
			mess = "Dear customer, you will find your new password in this email. You can login again by clicking on the link below.<br/>" + 
					"Best regards,<br/>" + 
					"<br/>" + 
					"Gentile cliente, trover&agrave; in questa mail la sua nuova password. Pu&ograve; effettuare nuovamente il login cliccando sul link sottostante.<br/>" + 
					"Cordiali Saluti,<br/>" + 
					"<br/>" + 
					"Estimado cliente, encontrar&agrave; su nueva contrase&ntilde;a en este correo electr&ograve;nico. Puede iniciar sesi&ograve;n de nuevo haciendo clic en el enlace de abajo.<br/>" + 
					"Saludos cordiales,<br/>" + 
					"<br/>" + 
					"Sehr geehrter Kunde, in dieser E-Mail finden Sie Ihr neues Passwort. Sie k&ouml;nnen sich erneut anmelden, indem Sie auf den untenstehenden Link klicken.<br/>" + 
					"Mit freundlichen Gr&uuml;&szlig;en<br/><br/><hr/><b>New Password:<br/>"+output+"</b><br/><br/><a href=\"https://www.smart-control.it\">www.smart-control.it</a>";
			String att = "";
			String fr = propLang.get("mailfrom2");
			
			try {

				//JSendMail.msgMail(mail, sub, mess, att, fr);
				JSendMailSSL.sendMail(mail, sub, mess, att, fr);
	
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			flag = "OK";
			
			resp = propLang.get("rec_pwd_ok")+"$"+flag;
			
		}else{
			
			flag = "KO";
			
			resp = propLang.get("rec_pwd_ko")+"$"+flag;
			
		}
		
	
		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	

}
