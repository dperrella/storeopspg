package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

@WebServlet("/smcheckdevices")
public class smcheckdevices extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public smcheckdevices() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/**/
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		int Hh = cal.get(Calendar.HOUR_OF_DAY);
		
		month = (month + 1);

		String idcustomer = "";
		String idStore = "";
		String flags = "";
		String tabname = "";
		String tab = "";
		int flag = 0;
		int flag2 = 0;
		String tabint = "";
		String int2="";
		String tablasttime = "";
		String tabunintime = "";
		String tabtimezone = "";
		String oggiIso = "";
		String tabdescr = "";
		
		Connection conn = null;
		Connection conn2 = null;
		Connection connData = null;
		Statement st = null;
		Statement st2 = null;
		Statement stData = null;
		
		ResultSet rs = null;
		String sql = "";
		String sql_ = "";
		conn=Utility.getConnCounter();
		conn2=Utility.getConnControl();
		connData=Utility.getConnData();
		String resp = "";
		try {
			st=conn.createStatement();
			st2=conn2.createStatement();
			stData=connData.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		
		try {
				
				
				sql = "select * from devices where accountid = '"+request.getParameter("account")+"' and storeid = '"+request.getParameter("store")+"' and doorid = '"+request.getParameter("door")+"'";
				System.out.println("sql = " + sql);
				rs = st.executeQuery(sql);
				while(rs.next()) {
					flag++;
					idStore = rs.getString("id_store");
				}
				rs.close();
				
				sql = "select id_customer from stores where id = "+idStore;
				rs = st.executeQuery(sql);
				if(rs.next()) {
					idcustomer=rs.getString("id_customer");
				}
				rs.close();
				
				//System.out.println("flag = " + flag);
				
				if (flag > 0){

					/*
					sql = "select c.id_store,yeard as year,monthd as month ,dayd as day,hourd as hour,coalesce(sum(pin),null,0) inc,description,timest,unitts,timezone from "
							+"(select distinct a.id,a.id_store,yeard,monthd,dayd,hourd,a.timest,a.unitts,a.timezone from "
							+"(select devices.id,devices.id_store, to_timestamp(devices.lasttime) as timest, to_timestamp(devices.unit_time) as unitts, devices.timezone from devices left join rel_loginc_stores "
							+"on devices.id_store = rel_loginc_stores.id_store "
							+"where rel_loginc_stores.id_store = "+idStore+" and (devices.device_type = 3 or devices.device_type = 5 or devices.device_type = 99) "
							+"and rel_loginc_stores.visible = 't' ) a,datedef "
							+"where monthd = '"+month+"' and dayd = '"+day+"' and yeard = '"+year+"' and hourd >= '7' and hourd <= '"+Hh+"' "
							+"order by a.id,a.id_store,yeard,monthd,dayd,hourd) c "
							+"left join (select * from v_people_counter where month = '"+month+"' and day = '"+day+"' and year = '"+year+"' and hour >= '7' and hour <= '"+Hh+"') vpc "
							+"on vpc.id_devices = c.id and monthd = month and dayd = day and yeard = year and hourd = hour "
							+"left join stores s on c.id_store = s.id "
							+"group by yeard,monthd,dayd,hourd,c.id_store,description,c.timest,c.unitts,c.timezone order by yeard,monthd,dayd,hourd,c.id_store ";
					*/

					oggiIso = year+"-"+month+"-"+day;
					
					sql   = "select p.id_store,year,month,day,hour,coalesce(sum(ingresso),null,0) inc,stores.description,to_timestamp(devices.lasttime) timest, " + 
							"to_timestamp(devices.unit_time) as unitts, devices.timezone ,devicetype.description descr " + 
							"from mv_current_month p join devices on id_devices = devices.id join stores on p.id_store = stores.id  join devicetype on device_type = devicetype.id " + 
							"where devices.accountid = '"+request.getParameter("account")+"' and devices.storeid = '"+request.getParameter("store")+"' and devices.doorid = '"+request.getParameter("door")+"' " + 
							"and month = '"+month+"' and day = '"+day+"' and year = '"+year+"' " + 
							"group by p.id_store,year,month,day,hour,stores.description,to_timestamp(devices.lasttime), " + 
							"to_timestamp(devices.unit_time) , devices.timezone,devicetype.description";
					
					
					System.out.println("sql = " + sql);
					rs = st.executeQuery(sql);
					
					int count = 0;
					while(rs.next()) {
						count++;
						tabname = rs.getString("description");
						tablasttime = rs.getString("timest");
						tabunintime = rs.getString("unitts");
						tabtimezone = rs.getString("timezone");
						tabdescr = rs.getString("descr");
						
						String hh = "00"+rs.getString("hour");
						hh=hh.substring(hh.length()-2,hh.length())+":00";
						
						tab = tab + "<tr><td>"+hh+"</td><td>"+rs.getString("inc")+"</td></tr>";
					}
					rs.close();
					
					if(count==0) {
						
						sql   = "select p.id_store,stores.description,to_timestamp(p.lasttime) timest, " + 
								"to_timestamp(p.unit_time) as unitts, p.timezone ,devicetype.description descr " + 
								"from devices p join stores on p.id_store = stores.id  join devicetype on device_type = devicetype.id " + 
								"where p.accountid = '"+request.getParameter("account")+"' and p.storeid = '"+request.getParameter("store")+"' and p.doorid = '"+request.getParameter("door")+"' " + 
								"group by p.id_store,stores.description,to_timestamp(p.lasttime), " + 
								"to_timestamp(p.unit_time) , p.timezone,devicetype.description";
						System.out.println(sql);
						rs = st.executeQuery(sql);
						if(rs.next()) {
							tabname = rs.getString("description");
							tablasttime = rs.getString("timest");
							tabunintime = rs.getString("unitts");
							tabtimezone = rs.getString("timezone");
							tabdescr = rs.getString("descr");
						}
						rs.close();
						
						String uid = "";
						sql   = "select * from uuid_generate_v1()";
						System.out.println(sql);
						rs = stData.executeQuery(sql);
						if(rs.next()) {
							uid = rs.getString(1);
						}
						rs.close();
						
						// read AN2
						sql = "select * from counter.report_grid('"+oggiIso+"','"+oggiIso+"','"+idStore+"','VI2','D',false, 5 ,'C01', '"+uid+"') where codkpi = 'VI2' and tot > 0 and idcat = "+request.getParameter("door");
						System.out.println(sql);
						rs=stData.executeQuery(sql);
						String th = "";
						String td = "";
						String d = "";
						if(rs.next()) {
							
							int2="<tr><th width='50%'>Hour</th><th>"+rs.getString("idcat")+" - "+rs.getString("category")+"</th></tr> ";
							
							for(int i=0;i<55;i++) {
								if(rs.getString((i+6))!=null) {
									
									d="00"+(i);
					        		d=d.substring(d.length()-2,d.length())+":00";
					        		
									//System.out.print(rs.getString((i+6))+" ");
									tab+="<tr><td>"+d+"</td><td class=\"b-r text-right\">"+Utility.round(rs.getDouble((i+6)) , 2)+"</td></tr>";
									
								}
							}
							
						}
						rs.close();
						
					}
					else {
						int2="<tr><th width='50%'>Hour</th><th width='50%'>Counts</th></tr> ";
					}
					
					tabint = "<tr><td colspan='2' id='tabdevname' align='center'><strong>"+tabname+"</strong></td></tr> ";
					tabint = tabint + "<tr><th colspan='2'>&nbsp;</th></tr> ";
					tabint = tabint + "<tr><td colspan='2' id='tabdevname' align='center'><strong>"+tabdescr+"</strong></td></tr> ";
					tabint = tabint + "<tr><th colspan='2'>&nbsp;</th></tr> ";
					tabint = tabint + "<tr><th width='50%'>LastTime</th><th width='50%'>"+tablasttime+"</th></tr> ";
					tabint = tabint + "<tr><th width='50%'>UnitTime</th><th width='50%'>"+tabunintime+"</th></tr> ";
					tabint = tabint + "<tr><th width='50%'>TimeZone</th><th width='50%'>"+tabtimezone+"</th></tr> ";
					tabint = tabint + "<tr><th colspan='2'>&nbsp;</th></tr> ";
					//tabint = tabint + "<tr><th>"+tablasttime+"</th><th>"+tabunintime+"</th><th>"+tabtimezone+"</th></tr> ";
					tabint = tabint+int2;
					
					flags = "SI";
					
				}else{
					
					tabint = "<tr><th width='100%' align='center' style='text-align: center;' colspan='4'>Device not Configured but present</th></tr><tr><th colspan='4'>&nbsp;</th></tr><tr><th width=\"25%\">Account Id</th> "
							+ "<th width=\"22%\">Store Id</th> "
							+"<th width=\"8%\">Door</th> "
							+"<th width=\"45%\">Last Data</th> ";
					
					sql = "select *,to_timestamp(tstamp) as tstamp_ from devicenotpaired where userstr = '"+request.getParameter("account")+"' and siteid = '"+request.getParameter("store")+"' and deviceid = '"+request.getParameter("door")+"' order by tstamp desc";
					System.out.println("sql = " + sql);
					rs = st2.executeQuery(sql);
					
					while(rs.next()) {
						flag2++;
						tab = tab + "<tr><td>"+request.getParameter("account")+"</td><td>"+request.getParameter("store")+"</td><td>"+request.getParameter("door")+"</td><td>"+rs.getString("tstamp_")+"</td></tr>";		
					}
					rs.close();
					tabname = "";
					flags = "NO";
					
					//System.out.println("flag2 = " + flag2);
					
					if (flag2 == 0){
						tabint = "";
						/*
						tabint = "<tr><th width='50%'>Store Name</th><th width='50%'>Store Notes</th></tr>"
								+ "<tr><th width='50%'><input type='text' id='storename' name='storename' class='form-control'  placeholder='Store Name' /></th><th width='50%'><input type='text' id='storenotes' name='storenotes' class='form-control' placeholder='Store Notes' /></th></tr>"
								+ "<tr><th width='100%' colspan='2'><button id='pre' class='form-control' onclick='PreReg()'>Pre-Registration</button></th></tr>";
						*/		
						tabint = "<tr><th width='100%' align='center' style='text-align: center;'>Device not Configured</th></tr>";
					}else{
						tab = tab + "<tr><td colspan='4'>&nbsp;</td></tr><tr><th width='100%' colspan='4' style='text-align: center'>PRE-REGISTRATION</th></tr><tr><th width='50%' colspan='2'>Store Name</th><th width='50%' colspan='2'>Store Notes</th></tr>"
								+ "<tr><th width='50%' colspan='2'><input type='text' id='storename' name='storename' class='form-control'  placeholder='Store Name' /></th><th width='50%' colspan='2'><input type='text' id='storenotes' name='storenotes' class='form-control' placeholder='Store Notes' /></th></tr>"
								+ "<tr><th width='50%' colspan='2'>Account ID</th><th width='50%' colspan='2'>Store ID</th></tr>"
								+ "<tr><th width='50%' colspan='2'><input type='text' id='accountid' name='accountid' class='form-control'  placeholder='Account ID' value='"+request.getParameter("account")+"' readonly /></th><th width='50%' colspan='2'><input type='text' id='storeid' name='storeid' class='form-control' placeholder='Store ID'  value='"+request.getParameter("store")+"' readonly /></th></tr>"								
								+ "<tr><th width='100%' colspan='4'>&nbsp;</th></tr><tr><th width='100%' colspan='4'><button id='pre' class='form-control btn-primary' onclick='PreReg()'>SAVE</button></th></tr>";						
					}
					
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			
		resp = flags + "$" + tabname + "$" + tab + "$" + tabint;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
			
	/**/	
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
