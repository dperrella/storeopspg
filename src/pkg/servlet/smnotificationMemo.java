package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pkg.properties.ReadProperties;
import pkg.utility.JSendMail;
import pkg.utility.Utility;

@WebServlet("/smnotificationMemo")
public class smnotificationMemo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public smnotificationMemo() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		/**/
		HttpSession session = request.getSession();
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		ReadProperties propLang_ = new ReadProperties("connection.properties");
		String monthapp = "";
		/*date*/
		/*
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		int Hh = cal.get(Calendar.HOUR_OF_DAY);
		month = (month + 1);
		if (month < 10){
			monthapp = "0"+month;
		}
		
		String dates = year + "-" + monthapp + "-" + day;
		*/
		/**/
		String username = (String) session.getAttribute("username");
		String mail = (String) session.getAttribute("mail");
		String analyt = "";

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String sql_ = "";
		String sql2 = "";
		conn=Utility.getConnAdmin();
		String resp = "";
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// -------------------------------
		// Legge le notifiche
		int numNotif = 0;
		String rigaNotif = "";
		String alertNotif = "";
		String notif = "";
		String reads = "";
		String dayb = "";
		String dayc = "";
		String viam = "";
		String contact = "";
		String footer = "";
		
			try {

				sql   = "select * from zmemsettings where upper(idusr) = upper('"+username+"') ";
				rs=st.executeQuery(sql);
				while(rs.next()) {
					dayb = rs.getString("daybefore");
					dayc = rs.getString("daycurrent");
					viam = rs.getString("viamail");
				}
				//System.out.println("numNotif = " + numNotif);
				rs.close();
				
				/*memo giorno -1*/
				if (dayb.equals("t") || dayb == "t"){
					sql_ = "";
					sql2 = "";
					
					sql   = "select * from zmemorandum "
							+ "where upper(idusr) = upper('"+username+"') "
							+ "and datestart::DATE = now()::DATE+1 and daybef = false ";
					rs=st.executeQuery(sql);
					
					while(rs.next()) {

						/*
						String datestart = rs.getString("datestart").toString();
						
						int diffdays = Utility.getDateDiffDays2(dates, datestart);
						*/
						
						sql_ = sql_ + "INSERT INTO znotif(link, img, titolo, nota, stato, idusr)"
								+ "VALUES ('notification-list-dett.htm', '<i class=\"fa fa-envelope-o\"></i>', 'Notification Memorandum day before', "
								+ "'"+rs.getString("name")+ "\n" +rs.getString("location")+"', '', '"+username.toUpperCase()+"'); ";
							
							//System.out.println("SQL insert = "+sql_);
							//st.executeUpdate(sql_);
							
						sql2 = sql2 + "update zmemorandum set daybef = 'true' where idmem = "+rs.getString("idmem")+"; ";
						//System.out.println("SQL update = "+sql2);
						//st.executeUpdate(sql2);	
						
						
						if (viam.equals("t") || viam == "t"){
							try {
								footer = "<br><br><small>This e-mail is strictly reserved to the specified users. Should the message be received by mistake, we kindly ask You to notify it to the sender and then to cancel the message.</small>";
								//contact = "<br><br>Mail: <a href='mailto:smart-suite@checkpt.com'>smart-suite@checkpt.com</a><br>Phone : "+propLang.get("chkTel")+"<br>Web: http://cloud.smart-control.it/Counter";
								contact = "<br><br>Mail: <a href='mailto:"+propLang_.get("chkMail")+"'>"+propLang_.get("chkMail")+"</a><br>Phone : "+propLang_.get("chkTel")+"<br>Web: http://cloud.smart-control.it/Counter";
								String sub = "Smart-Suite Notification";
								String mess = "E-mail notification of the day before:<br>Task: "+rs.getString("name")+"<br>Location: "+rs.getString("location")+
											  contact +
											  footer;
								String att = "";
								String fr = propLang_.get("mailfrom");
								JSendMail.msgMail(mail, sub, mess, att, fr);
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}					
					//rs.close();
				}
/*				
				st.executeUpdate(sql_);
				st.executeUpdate(sql2);
*/
				if (sql_ != "" || !"".equals(sql_)){
					st.executeUpdate(sql_);
					st.executeUpdate(sql2);
				}
				rs.close();
				
				/*memo giorno corrente*/
				if (dayc.equals("t") || dayc == "t"){
					
					sql_ = "";
					sql2 = "";
					
					sql   = "select * from zmemorandum "
							+ "where upper(idusr) = upper('"+username+"') "
							+ "and datestart::DATE between now()::DATE and now()::DATE and daycur = false ";
					rs=st.executeQuery(sql);
					
					while(rs.next()) {

						sql_ = sql_ + "INSERT INTO znotif(link, img, titolo, nota, stato, idusr)"
								+ "VALUES ('notification-list-dett.htm', '<i class=\"fa fa-envelope-o\"></i>', 'Notification Memorandum current day', "
								+ "'"+rs.getString("name")+ "\n" +rs.getString("location")+"', '', '"+username.toUpperCase()+"'); ";
							
							//System.out.println("SQL insert = "+sql_);
							//st.executeUpdate(sql_);
							
						sql2 = sql2 + "update zmemorandum set daycur = 'true' where idmem = "+rs.getString("idmem")+"; ";
						//System.out.println("SQL update = "+sql2);
						//st.executeUpdate(sql2);	

	
						if (viam.equals("t") || viam == "t"){
							try {
								
								footer = "<br><br><small>This e-mail is strictly reserved to the specified users. Should the message be received by mistake, we kindly ask You to notify it to the sender and then to cancel the message.</small>";
								//contact = "<br><br>Mail: <a href='mailto:smart-suite@checkpt.com'>smart-suite@checkpt.com</a><br>Phone : "+propLang.get("chkTel")+"<br>Web: http://cloud.smart-control.it/Counter";
								contact = "<br><br>Mail: <a href='mailto:"+propLang_.get("chkMail")+"'>"+propLang_.get("chkMail")+"</a><br>Phone : "+propLang_.get("chkTel")+"<br>Web: http://cloud.smart-control.it/Counter";
								String sub = "Smart-Suite Notification";
								String mess = "E-mail notification of the current day:<br>Task: "+rs.getString("name")+"<br>Location: "+rs.getString("location")+
											  contact +
											  footer;
								String att = "";
								String fr = propLang_.get("mailfrom");
								
								JSendMail.msgMail(mail, sub, mess, att, fr);
							} catch (MessagingException e) {
								e.printStackTrace();
							}
						}
						
					}					
					//rs.close();
				}
				if (sql_ != "" || !"".equals(sql_)){
					st.executeUpdate(sql_);
					st.executeUpdate(sql2);
				}
				rs.close();
				
				
				sql   = "select count(*) from znotif where upper(idusr) = upper('"+username+"') and stato <> 'A'";
				rs=st.executeQuery(sql);
				while(rs.next()) {
					numNotif = Integer.parseInt(rs.getString("count"));
				}
				//System.out.println("numNotif = " + numNotif);
				rs.close();
				
				sql   = "select * from znotif where idusr = '"+username+"' and stato <> 'A' order by idnota desc limit 5 ";
				rs=st.executeQuery(sql);
				while(rs.next()) {
					
					if(!rs.getString("stato").equals("A")){
						//numNotif+=1;
					
						rigaNotif+= "<a href=\"../config/"+rs.getString("link")+"?id="+rs.getString("idnota")+"\" class=\"media list-group-item\">"+
					                " 	<span class=\"pull-left thumb-sm text-center\">"+rs.getString("img")+"</span>"+
					                "   <span class=\"media-body block m-b-none\">"+rs.getString("titolo")+"<br>"+
					                "      <small class=\"text-muted\">"+rs.getString("nota")+"</small>"+
					                "    </span>"+
					                "</a>";	
					}
					
				}
				rs.close();
				
				if(numNotif > 0){
					alertNotif = "<span id=\"spanCount\" class=\"badge badge-sm up bg-danger count\" style='font-size: 10px;'>"+numNotif+"</span>";
				}
				
				notif = "  <a href=\"#\" class=\"dropdown-toggle lt\" data-toggle=\"dropdown\">"+
				        "    <i class=\"icon-bell\"></i>"+alertNotif+
				        "  </a>"+
				        "  <section class=\"dropdown-menu aside-xl animated fadeInUp\">"+
				        "    <section class=\"panel bg-white\">"+
				        "      <div class=\"panel-heading b-light bg-light\">"+
				        "        <strong>"+propLang.get("hai")+" <span id=\"spanCount4\" class=\"count\">"+numNotif+"</span> "+propLang.get("notifiche")+"</strong>"+
				        "      </div>"+
				        "        <div class=\"list-group list-group-alt\">"+
				        rigaNotif+
				        "      </div>"+
				        "      <div class=\"panel-footer text-sm\">"+
				        "        <a href=\"../../app/config/notification-list.htm\" class=\"pull-right\"><i class=\"fa fa-cog fa-spin fa-fw\"></i></a>"+
				        "        <a href=\"../../app/config/notification-list.htm\">"+propLang.get("visnotif")+"</a>"+
				        "      </div>"+
				        "    </section>"+
				        "  </section>";
				
				st.close();
				conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		response.setContentType("text/plain");
			

		
		resp = notif+"$"+numNotif;
		response.getWriter().write(resp);
			
	/**/	
	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
