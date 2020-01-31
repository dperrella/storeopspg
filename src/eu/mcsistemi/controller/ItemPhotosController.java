package eu.mcsistemi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import eu.mcsistemi.properties.ReadProperties;
import pkg.utility.Utility;

public class ItemPhotosController extends AbstractController {
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();

		HttpSession session = req.getSession();
		String id = ""+req.getParameter("id");
		id = Utility.noNull(id);
		id=id.replaceAll("\\?cd=undefined", "");
		
		//Legge il file di properties associato alla lingua sul profilo
		//ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		obj.put("id", id);
		return new ModelAndView("/source/anag/item-photos", obj);
	}
}
