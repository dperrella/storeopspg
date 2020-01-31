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
import eu.mcsistemi.Entity.Place;
import eu.mcsistemi.Entity.PlaceEntry;
import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.utility.Utility;

public class StoresGoogleDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		
		String msg = "";
		
		String id = req.getParameter("id");
		id=id.replaceAll("\\?cd=undefined", "");
		String googleUid = req.getParameter("googleUid");
		googleUid=googleUid.replaceAll("\\?cd=undefined", "");
		
		Connection conn = null;
		try {
			conn = Utility.getConnAdmin();
			
			obj.put("id", id);
			obj.put("googleUid", googleUid);
			
			Place place = new Place(googleUid);
			//System.out.println(place.toString());
			
			obj.put("name", place.name());
			obj.put("address", place.formattedAddress());
			obj.put("latitude", place.latitude());
			obj.put("longitude", place.longitude());
			obj.put("icon", place.icon());
			//obj.put("rating", place.rating());
			obj.put("country", place.longCountry());
			obj.put("region", place.region());
			obj.put("longProvince", place.longProvince());
			obj.put("shortProvince", place.shortProvince());
			obj.put("zipCode", place.zipCode());
			obj.put("postalCode", place.postalCode());
			obj.put("phone", place.phone());
			obj.put("url", place.url());
			
			
			String types = "";
			
			for(int i=0;i<place.tipi().size();i++) {
				types+="\""+place.tipi().get(i)+"\",";
				System.out.println(" - "+place.tipi().get(i));
			}
			
			if(types.length()>0)
				types=types.substring(0, types.length()-1);
			
			types="["+types+"]";
			obj.put("types", types);
			
			
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
		
		obj.put("dsfun", "Dettaglio Negozio Google");
		
		
		return new ModelAndView("/app/anag/stores-google-dett", obj);
	}
	
}
