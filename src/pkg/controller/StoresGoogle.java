package pkg.controller;

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

import eu.mcsistemi.PlacesManager;
import eu.mcsistemi.Entity.PlaceEntry;
import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.utility.Utility;

public class StoresGoogle extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables> sto = new ArrayList<Tables>();
		sto.clear();
		
		String msg = "";
		
		String id = req.getParameter("id");
		String googleUid = req.getParameter("googleUid");
		String query = ""+req.getParameter("query");
		String customerid = req.getParameter("customerid");
		obj.put("customerid", customerid);
		query = Utility.noNull(query);
		
		Connection conn = null;
		try {
			conn = Utility.getConnAdmin();
			
			Stores s = new Stores();
			s.setId(id);
			s.selectRow(conn);
			
			
			String googleQuery = s.getCmp4()+" "+s.getDescription()+" "+s.getCity()+" "+s.getAddress();
			googleQuery = googleQuery.replaceAll("&", " ");
			
			if(query.length()>0)
				googleQuery = query;
			
			obj.put("googleQuery", googleQuery);
			obj.put("id", id);
			obj.put("googleUid", googleUid);
			
			
			PlacesManager placesManager = new PlacesManager();
			placesManager.searchPlaces(googleQuery);
			
			for (PlaceEntry placeEntry : placesManager.getPlacesEntry()) {
				/*
				System.out.println(placeEntry.name);
				System.out.println(placeEntry.address());
				System.out.println(placeEntry.latitude());
				System.out.println(placeEntry.longitude());
				System.out.println(placeEntry.uid());
				*/
				sto.add(new Tables("STO", placeEntry.uid().trim(), placeEntry.name().trim(), 
						placeEntry.address(),""+placeEntry.latitude(),""+placeEntry.longitude(),""));
				
			}
			obj.put("sto", sto);
			
			obj.put("result", placesManager.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("dsfun", "Lista Negozi Google");
		
		
		return new ModelAndView("/app/anag/stores-google", obj);
	}
	
}
