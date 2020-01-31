package eu.mcsistemi;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.cj.pager.nextPagesTag;

import eu.mcsistemi.Entity.Place;

public class Places extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		// contenuto del controller
		// ...
		
		// singolo uid
		Place place = new Place("ChIJ_USvLZQEOxMRbJop4zaV8vU");
		System.out.print(place.toString());
		
		
		// ricerca...
		PlacesManager placesManager = new PlacesManager();
		placesManager.searchPlaces("conbipel provincia di napoli");
		System.out.print(placesManager.toString());
		// restituzione della logica alla View
		return new ModelAndView("/places", obj);
	}
	
}
