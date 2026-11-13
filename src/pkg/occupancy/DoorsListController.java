package pkg.occupancy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables;
import pkg.utility.Utility;

public class DoorsListController extends AbstractController {
	@Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		long ts = new Date().getTime();
        obj.put("ts", ts);
        
        HttpSession session = req.getSession();
		String idcus = ""+session.getAttribute("idcus");
		idcus = Utility.noNull(idcus);
        
        List<Tables> tab = new ArrayList<Tables>();
		List<Tables> ais = new ArrayList<Tables>();
		tab.clear();
		ais.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		

		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		
		String msg = "";
		
		try {
			// Apre la connessione
			conn = Utility.getConnData();
			
			String account_id = Utility.getAccountId(conn, idcus);
			obj.put("account_id", account_id);
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("A")) {
					
					String door = req.getParameter("door");
					String aisle_type = req.getParameter("aisle_type");
					
					String temp[] = door.split(",");
					for(int i=0;i<temp.length;i++) {
						
						sql   = "update occ2_doors set aisle_type = "+aisle_type+" "
								+ "where door_type_id = "+temp[i]+" and id_customer = "+idcus+" ";
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
						
						sql   = "update occ2_doors set enabled = false "
								+ "where door_type_id = "+temp[i]+" and id_customer = "+idcus+" ";
						System.out.println(sql);
						st=conn.prepareStatement(sql);
						st.executeUpdate();
						st.close();
						
					}
					
				}
				
				if(tipoForm.equals("M")) {
					
					String door = req.getParameter("door");
					String desc = req.getParameter("desc");
					String enabled = ""+req.getParameter("enabled");
					enabled = Utility.noNull(enabled);
					if(enabled.length()==0)
						enabled = "true";
					sql   = "update occ2_doors set door_description = '"+desc.replaceAll("'", "''")+"' , enabled = "+enabled+" "
							+ "where door_type_id = "+door+" and id_customer = "+idcus+" ";
					System.out.println(sql);
					st=conn.prepareStatement(sql);
					st.executeUpdate();
					st.close();
				
				}
				
				if(tipoForm.equals("I")) {
					
					String door = req.getParameter("door");
					String desc = req.getParameter("desc");
					
					
					// check description
					String chkErr="";
					sql = "select count(*) num from occ2_doors "
							+ "where upper(door_description) = '"+desc.toUpperCase().replaceAll("'", "''")+"' and id_customer = "+idcus+" ";
					st=conn.prepareStatement(sql);
					rs=st.executeQuery();
					int num = 0;
					if(rs.next()) {
						num = rs.getInt("num");
					}
					rs.close();
					
					if(num==0) {
						sql = "insert into occ2_doors "+
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
			
			
			sql   = "select *, a.description ds_aisle "
					+ "from occ2_doors d "
					+ "left join occ2_aisle_type a on d.aisle_type = a.id "
					+ "where id_customer = "+idcus;
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
					+ "from public.occ_aisle_type ";
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
		
		obj.put("tab", tab);
		obj.put("ais", ais);
		obj.put("msg", msg);
		
		return new ModelAndView("/occupancy/anag/doors-list", obj);
	}
	
	
}
