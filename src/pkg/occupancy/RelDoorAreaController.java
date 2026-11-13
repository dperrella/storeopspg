package pkg.occupancy;

import java.sql.Connection;
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

import pkg.occupancy.model2.Aree;
import pkg.occupancy.model2.RelDoorArea;
import pkg.utility.Utility;

public class RelDoorAreaController extends AbstractController {
	@Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		long ts = new Date().getTime();
        obj.put("ts", ts);
        
        HttpSession session = req.getSession();
		String idcus = ""+session.getAttribute("idcus");
		idcus = Utility.noNull(idcus);
        
        
        List<RelDoorArea> rel = new ArrayList<RelDoorArea>();
		rel.clear();
		
		List<Aree> aree = new ArrayList<Aree>();
		aree.clear();
		
		Connection conn = null;
		
		// Per operazioni di CRUD
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		String msg = "";
		
		try {
			// Apertura connessione
			conn = Utility.getConnData();
			
			// Inizializzazione Bean
			RelDoorArea bean = new RelDoorArea();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				bean.deleteAll(conn, Integer.parseInt(idcus));
				
				String[] _idDoors=req.getParameterValues("idDoor");
				String[] _ins=req.getParameterValues("_in");
				String[] _outs=req.getParameterValues("_out");
				
				for(int i=0; i<_idDoors.length; i++)
				{
					
					System.out.println(_idDoors[i]+" "+_ins[i]+" "+_outs[i]);
					
					
					
					// in
					if(Integer.parseInt(_ins[i])!=-1) {
						bean.setIdDoor(Integer.parseInt(_idDoors[i]));
						bean.setIdArea(Integer.parseInt(_ins[i]));
						bean.setReverse(false);
						bean.setIdCustomer(Integer.parseInt(idcus));
						bean.add(conn);
					}
					
					if(Integer.parseInt(_outs[i])!=-1) {
						//bean.set_in(Integer.parseInt(_ins[i]));
						//bean.set_out(Integer.parseInt(_outs[i]));
						bean.setIdDoor(Integer.parseInt(_idDoors[i]));
						bean.setIdArea(Integer.parseInt(_outs[i]));
						bean.setReverse(true);
						bean.setIdCustomer(Integer.parseInt(idcus));
						bean.add(conn);
					}
						
				}
				
				
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>Operation Successful.</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			rel = RelDoorArea.getRel(conn, Integer.parseInt(idcus));
			
			// aree (per combo)
			aree = Aree.getAreeNoZone(conn, Integer.parseInt(idcus));
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = "<div class=\"wrapper\"><div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div></div>";
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("rel", rel);
		obj.put("aree", aree);
		
		obj.put("msg", msg);
		
        
		return new ModelAndView("/occupancy/rel/rel-door_aree", obj);
	}
}
