package pkg.analytics2;

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

import pkg.beans.Kpi;
import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class KpiList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Kpi> kpi = new ArrayList<Kpi>();
		
		// Pulisce la lista
		kpi.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		try {
			// Apre la connessione
			conn = Utility.getConnData();
			
			// ==================
			// Lista Utenti
			sql   = "select xcdtb, ata32, trim(xcdel) xcdel, xdtab, xdesc, calculated, "+
					"color, kpi_symbol, filtered "+
					"from ztables a "+
					"where xcdtb = 'KPI' ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi datebase
			String $xcdtb="";
			String $xcdel="";
			String $xdtab="";
			String $ata32="";
			String $xdesc="";
			boolean $calculated=false;
			String $color="";
			String $kpi_symbol="";
			boolean $filtered=false;
			
			String cmp1 = "";
			String cmp2 = "";
			while(rs.next()) {
				
				count+=1;
				
				$xcdtb=rs.getString("xcdtb");
				$xcdel=rs.getString("xcdel");
				$xdtab=rs.getString("xdtab");
				$ata32=rs.getString("ata32");
				$xdesc=rs.getString("xdesc");
				$calculated=rs.getBoolean("calculated");
				if($calculated)
					cmp1 = "<i class=\"icon-check text-success\"></i>";
				else
					cmp1 = "<i class=\"icon-ban text-danger\"></i>";
				$color=rs.getString("color");
				$kpi_symbol=rs.getString("kpi_symbol");
				
				$filtered=rs.getBoolean("filtered");
				if($filtered)
					cmp2 = "<i class=\"icon-check text-success\"></i>";
				else
					cmp2 = "<i class=\"icon-ban text-danger\"></i>";
				
				kpi.add(new Kpi($xcdtb,$xcdel,$xdtab,$ata32,$xdesc,$calculated,$color,$kpi_symbol,
						cmp1,cmp2,"","","","","",""));
				
			}
			rs.close();
			// Fine Lista Utenti
			// ==================
			
			
			if(st!=null)
				st.close();
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Lista Kpi");
		obj.put("kpi", kpi);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/analytics2/anag/kpi-list", obj);
	}
	
}
