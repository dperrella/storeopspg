package pkg.disp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class FolderDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
				
		String id_file = req.getParameter("id_file");
		
		System.out.println("----"+id_file);
		
		obj.put("id_file", id_file);
		
		return new ModelAndView("/disp/main/folder-dett", obj);
	}
	
}
