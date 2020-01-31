package pkg.disp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Customers;
import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DoorsList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Customers> cus = new ArrayList<Customers>();
		List<Tables> tab = new ArrayList<Tables>();
		List<Tables> ais = new ArrayList<Tables>();
		cus.clear();
		tab.clear();
		ais.clear();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		Connection conn = null;
		PreparedStatement st = null;
		
		Connection connAdmin = null;
		PreparedStatement stAdmin = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		String idcus = ""+req.getParameter("idcus");
		idcus=Utility.noNull(idcus);
		obj.put("idcus", idcus);
		

		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		
		String msg = "";
		
		try {
			// Apre la connessione
			conn = Utility.getConnDispatcher();
			// Apre la connessione Admin
			connAdmin = Utility.getConnAdmin();
			
			// ==================
			// Lista Customers
			sql  =  "select * , '"+(String)session.getAttribute("username")+"' as user from customers a where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') "
					+ "union all "
					+ "select * from customerscountry b where idusr = '"+(String)session.getAttribute("username")+"' ";
			
			// Prepara il cursore
			stAdmin = connAdmin.prepareStatement(sql);
			
			// Esegue l'sql
			rs=stAdmin.executeQuery();
			
			// Campi database
			String $companyname;
			String $description;
			String $contact;
			String $brandname;
			String $brandlogo;
			String $account_code;
			String $id;
			String $phone;
			String $mail;
			String $mobile;
			String $id_optempl;
			String $customer_country_id;
			String $agreement;
			String $wskey;
			String $wsdecrypt_key;
			String $wscrypt;
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				$companyname=rs.getString("companyname");
				$description=rs.getString("description");
				$contact=rs.getString("contact");
				$brandname=rs.getString("brandname");
				$brandlogo=rs.getString("brandlogo");
				$account_code=rs.getString("account_code");
				$id=rs.getString("id");
				$phone=rs.getString("phone");
				$mail=rs.getString("mail");
				$mobile=rs.getString("mobile");
				$id_optempl=rs.getString("id_optempl");
				$customer_country_id=rs.getString("customer_country_id");
				$agreement=rs.getString("agreement");
				$wskey=rs.getString("wskey");
				$wsdecrypt_key=rs.getString("wsdecrypt_key");
				$wscrypt=rs.getString("wscrypt");
				
				cus.add(new Customers($companyname,$description,$contact,$brandname,
						$brandlogo,$account_code,$id,$phone,$mail,$mobile,$id_optempl,
						$customer_country_id,$agreement,$wskey,$wsdecrypt_key,$wscrypt,
						$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			// Fine Lista Customers
			// ==================
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("A")) {
					
					String door = req.getParameter("door");
					String aisle_type = req.getParameter("aisle_type");
					
					String temp[] = door.split(",");
					for(int i=0;i<temp.length;i++) {
						
						sql   = "update neo.doors set aisle_type = "+aisle_type+" "
								+ "where id_customer = "+idcus+" and door_type_id = "+temp[i]+" ";
						System.out.println(sql);
						st=conn.prepareStatement(sql);
						st.executeUpdate();
						st.close();
						
					}
					
				}
				
				if(tipoForm.equals("E")) {
					
					String door = req.getParameter("door");
					
					String temp[] = door.split(",");
					for(int i=0;i<temp.length;i++) {
						
						sql   = "update neo.doors set enabled = false "
								+ "where id_customer = "+idcus+" and door_type_id = "+temp[i]+" ";
						System.out.println(sql);
						st=conn.prepareStatement(sql);
						st.executeUpdate();
						st.close();
						
					}
					
				}
				
				if(tipoForm.equals("M")) {
					
					String door = req.getParameter("door");
					String desc = req.getParameter("desc");
					String enabled = req.getParameter("enabled");
						
					sql   = "update neo.doors set door_description = '"+desc.replaceAll("'", "''")+"' , enabled = "+enabled+" "
							+ "where id_customer = "+idcus+" and door_type_id = "+door+" ";
					System.out.println(sql);
					st=conn.prepareStatement(sql);
					st.executeUpdate();
					st.close();
				
				}
				
				if(tipoForm.equals("I")) {
					
					String door = req.getParameter("door");
					String desc = req.getParameter("desc");
					String account_id = getAccountID(conn, idcus);
					
					// check description
					String chkErr="";
					sql = "select count(*) num from neo.doors where id_customer = "+idcus+" "
							+ "and upper(door_description) = '"+desc.toUpperCase().replaceAll("'", "''")+"' ";
					st=conn.prepareStatement(sql);
					rs=st.executeQuery();
					int num = 0;
					if(rs.next()) {
						num = rs.getInt("num");
					}
					rs.close();
					
					if(num==0) {
						sql = "insert into neo.doors "+
								"(id_customer, account_id, door_description, door_type_id, enabled, imported) "+
								"values "+
								"("+idcus+" , '"+account_id+"', '"+desc.replaceAll("'", "''")+"', "+door+", true, false) ";
						System.out.println(sql);
						st=conn.prepareStatement(sql);
						st.executeUpdate();
						st.close();
					}
					else {
						msg = "<div class=\"wrapper\"><div class=\"alert alert-danger\"><strong>Door Already Exist</strong></div></div>";
					}
				
				}
				
				
			}
			
			
			
			if(idcus.length()==0)
				idcus = "0";
			
			sql   = "select *, a.description ds_aisle "
					+ "from neo.doors d "
					+ "left join aisle_type a on d.aisle_type = a.id "
					+ "where id_customer = "+idcus+" ";
			st=conn.prepareStatement(sql);
			rs=st.executeQuery();
			
			String enabled = "";
			String imported = "";
			while(rs.next()) {
				
				if(rs.getBoolean("enabled")) {
					enabled = "<i class=\"fa fa-circle text-success\"></i>";
				}
				else {
					enabled = "<i class=\"fa fa-circle text-danger\"></i>";
				}
				
				if(rs.getBoolean("imported")) {
					imported = "<i class=\"icon-cloud-download\"></i> Imported";
				}
				else {
					imported = "<i class=\"icon-note\"></i> Manual";
				}
				String aisle_type = ""+rs.getString("ds_aisle");
				aisle_type = Utility.noNull(aisle_type);
				tab.add(new Tables("DOO",rs.getString("door_type_id"),rs.getString("door_description"),
						rs.getString("account_id"),aisle_type,enabled,imported));
			}
			rs.close();
			st.close();
			
			// aisle_type
			sql   = "select * "
					+ "from public.aisle_type ";
			st=conn.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				ais.add(new Tables("AIS",rs.getString("id"),rs.getString("description"),"","","",""));
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			msg = "<div class=\"wrapper\"><div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div></div>";
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("cus", cus);
		obj.put("tab", tab);
		obj.put("ais", ais);
		obj.put("msg", msg);
		
		return new ModelAndView("/disp/config/doors-list", obj);
	}
	
	private static String getAccountID(Connection conn, String idcus) {
		
		String accountId = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql = "select account_code from neo.customers where id = "+idcus+" ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				accountId = rs.getString("account_code");
			}
			rs.close();
		}
		catch(Exception e) {
			
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		return accountId;
		
	}
}
