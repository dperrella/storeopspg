package pkg.analytics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Stores;
import pkg.utility.Utility;

public class AreeStoresList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Stores> sto = new ArrayList<Stores>();
		
		// Pulisce la lista
		sto.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		HttpSession session = req.getSession();
		String arrayStores = (String) session.getAttribute("arrayStores");
		if(arrayStores.length()==0)
			arrayStores = "0";
		
		try {
			// Apre la connessione
			conn = Utility.getConnAnalytics();
			
			// sql da eseguire
			sql  =  "select a.*, c.brandname custbrand, "
					+ "(select count(b.id) from devices b where b.id_store=a.id) nrdevices "
					+ "from stores a, customers c "
					+ "where a.id_customer = c.id "
					+ "and a.id in(select idstore::integer from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			
			System.out.println(sql);
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" and a.description || c.brandname like ? ";
			}
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Se ha parametri li passa all'sql
			if(hasCond==true) {
				st.setString(1, "%" + q + "%");
			}
			
			System.out.println(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $description="";
			String $address="";
			String $country="";
			String $city="";
			String $postalcode="";
			String $region="";
			String $contact="";
			String $cphone="";
			String $sphone="";
			String $cmail="";
			String $smail="";
			String $coordinates="";
			String $store_code="";
			String $brandname="";
			String $brandlogo="";
			String $id_customer="";
			String $id="";
			String $cmobile="";
			String $province="";
			String $id_optempl="";
			String $customeridstore="";
			String $sapid="";
			String $latitude="";
			String $longitude="";
			String $notes="";
			String $openhh="";
			String $closehh="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$description=rs.getString("description");
				$address=rs.getString("address");
				$country=rs.getString("country");
				$city=rs.getString("city");
				$postalcode=rs.getString("postalcode");
				$region=rs.getString("region");
				$contact=rs.getString("contact");
				$cphone=rs.getString("cphone");
				$sphone=rs.getString("sphone");
				$cmail=rs.getString("cmail");
				$smail=rs.getString("smail");
				$coordinates=rs.getString("coordinates");
				$store_code=rs.getString("store_code");
				$brandname=rs.getString("brandname");
				$brandlogo=rs.getString("brandlogo");
				$id_customer=rs.getString("id_customer");
				$id=rs.getString("id");
				$cmobile=rs.getString("cmobile");
				$province=rs.getString("province");
				$id_optempl=rs.getString("id_optempl");
				$customeridstore=rs.getString("customeridstore");
				$sapid=rs.getString("sapid");
				$latitude=rs.getString("latitude");
				$longitude=rs.getString("longitude");
				$notes=rs.getString("notes");
				$openhh=rs.getString("openhh");
				$closehh=rs.getString("closehh");
				
				$cmp1=rs.getString("custbrand");
				$cmp2=rs.getString("nrdevices");
				
				sto.add(new Stores($description,$address,$country,$city,
						$postalcode,$region,$contact,$cphone,$sphone,$cmail,$smail,
						$coordinates,$store_code,$brandname,$brandlogo,$id_customer,
						$id,$cmobile,$province,$id_optempl,$customeridstore,$sapid,
						$latitude,$longitude,$notes,$openhh,$closehh,
						"","","",
						$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Lista Aree Negozi");
		obj.put("sto", sto);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/app/anag/aree-stores-list", obj);
	}
	
}
