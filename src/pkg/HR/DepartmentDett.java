package pkg.HR;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import pkg.beans.HR.Employee;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DepartmentDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Employee> emp = new ArrayList<Employee>();
		List<Tables> deptype = new ArrayList<Tables>();
		List<Tables> sto = new ArrayList<Tables>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		emp.clear();
		deptype.clear();
		sto.clear();
		
		Connection conn = null;
		Statement st = null;
		Connection connAdmin = null;
		Statement stAdmin = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		int count = 0;
		String msg = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		
		
		try {
			
			// Campi database
			String $id="";
			String $description="";
			String $parentid="";
			String $address="";
			String $locality="";
			String $province="";
			String $zipcode="";
			String $state="";
			String $phone="";
			String $email="";
			String $id_manager="";
			String $department_code="";
			String $emp_description="";
			String $minnum_intervals="";
			String $tollerance="";
			String $nocturne="";
			
			String $department_type="";
			String $id_store="";
			
			String $company_code="";
			String $agency_code="";
			
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			$id = ""+req.getParameter("id");
			$id=Utility.noNull($id);
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			connAdmin = Utility.getConnAdmin();
			stAdmin = connAdmin.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					System.out.println("Inserimento...");
					
					$description=req.getParameter("description");
					$parentid=req.getParameter("parentid");
					$address=req.getParameter("address");
					$locality=req.getParameter("locality");
					$province=req.getParameter("province");
					$zipcode=req.getParameter("zipcode");
					$phone=req.getParameter("phone");
					$email=req.getParameter("email");
					$id_manager=req.getParameter("id_employee");
					$department_code=req.getParameter("department_code");
					$minnum_intervals=req.getParameter("minnum_intervals");
					$tollerance=req.getParameter("tollerance");
					$nocturne=req.getParameter("nocturne");
					if($minnum_intervals.trim().length()==0)
						$minnum_intervals = "0";
					if($tollerance.trim().length()==0)
						$tollerance = "0";
					if($nocturne.trim().length()==0)
						$nocturne = "0";
					
					$department_type=""+req.getParameter("department_type");
					$department_type=Utility.noNull($department_type);
					$id_store=""+req.getParameter("id_store");
					$id_store=Utility.noNull($id_store);
					
					$company_code=""+req.getParameter("company_code");
					$company_code=Utility.noNull($company_code);
					$agency_code=""+req.getParameter("agency_code");
					$agency_code=Utility.noNull($agency_code);
					
					if($department_type.trim().length()==0)
						$department_type = "0";
					if($id_store.trim().length()==0)
						$id_store = "0";
					
					sql   = "insert into hr_departments "+
							"(description, parent_id, address, locality, "+
							"province, zipcode, phone, email, id_manager, department_code,"+
							"minnum_intervals, tollerance, nocturne, id_customer, department_type, "+
							"id_store, company_code, agency_code) values "+
							"('"+$description.replaceAll("'", "''")+"', '"+$parentid+"', "+
							"'"+$address.replaceAll("'", "''")+"', '"+$locality.replaceAll("'", "''")+"', "+
							"'"+$province+"', '"+$zipcode.replaceAll("'", "''")+"', "+
							"'"+$phone.replaceAll("'", "''")+"', '"+$email.replaceAll("'", "''")+"', "+
							"'"+$id_manager+"', '"+$department_code+"', "+
							$minnum_intervals+" , "+$tollerance+" , "+$nocturne+" , "+session.getAttribute("idcus")+" , "+
							$department_type+" , "+$id_store+" , '"+$company_code.replaceAll("'", "''")+"' , '"+$agency_code.replaceAll("'", "''")+"' ) RETURNING id ";
					System.out.println(sql);
					
					rs = st.executeQuery(sql);
					if(rs.next()) {
						$id = rs.getString(1);
						System.out.println("ID: "+rs.getString(1));
					}
					else {
						$id = "-1";
						System.out.println("ID: -1");
					}
					rs.close();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "M";
					
				}
				
				if(tipoForm.equals("M")) {
					
					System.out.println("Modifica...");
					
					$description=req.getParameter("description");
					$parentid=req.getParameter("parentid");
					$address=req.getParameter("address");
					$locality=req.getParameter("locality");
					$province=req.getParameter("province");
					$zipcode=req.getParameter("zipcode");
					$phone=req.getParameter("phone");
					$email=req.getParameter("email");
					$id_manager=req.getParameter("id_employee");
					$department_code=req.getParameter("department_code");
					$minnum_intervals=req.getParameter("minnum_intervals");
					$tollerance=req.getParameter("tollerance");
					$nocturne=req.getParameter("nocturne");
					if($minnum_intervals.trim().length()==0)
						$minnum_intervals = "0";
					if($tollerance.trim().length()==0)
						$tollerance = "0";
					if($nocturne.trim().length()==0)
						$nocturne = "0";
					
					$department_type=""+req.getParameter("department_type");
					$department_type=Utility.noNull($department_type);
					$id_store=""+req.getParameter("id_store");
					$id_store=Utility.noNull($id_store);
					
					$company_code=""+req.getParameter("company_code");
					$company_code=Utility.noNull($company_code);
					$agency_code=""+req.getParameter("agency_code");
					$agency_code=Utility.noNull($agency_code);
					
					if($department_type.trim().length()==0)
						$department_type = "0";
					if($id_store.trim().length()==0)
						$id_store = "0";
					
					sql   = "update hr_departments "+
							"set description = '"+$description.replaceAll("'", "''")+"', "+
							"parent_id = '"+$parentid+"', address='"+$address.replaceAll("'", "''")+"', "+
							"locality = '"+$locality.replaceAll("'", "''")+"', province = '"+$province+"', "+
							"zipcode = '"+$zipcode.replaceAll("'", "''")+"', phone = '"+$phone.replaceAll("'", "''")+"', "+
							"email = '"+$email+"', id_manager = '"+$id_manager+"', department_code = '"+$department_code+"', "+
							"minnum_intervals = "+$minnum_intervals+" , tollerance = "+$tollerance+" , nocturne = "+$nocturne+" , "+
							"department_type = "+$department_type+" , id_store = "+$id_store+" , "+
							"company_code = '"+$company_code.replaceAll("'", "''")+"' , agency_code = '"+$agency_code.replaceAll("'", "''")+"' "+
							"where id = '"+$id+"' ";
					st.executeUpdate(sql);
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				if(tipoForm.equals("E")) {
					
					System.out.println("Cancellazione...");
					sql   = "delete from hr_departments where id = '"+$id+"' ";
					st.executeUpdate(sql);
					
					// *blank id
					$id = "";
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "I";
					
				}
				
			}
			
			if(tipoForm.equals("V")||tipoForm.equals("M")||tipoForm.equals("E")) {
				
				// Messaggio conferma cancellazione record
				if(tipoForm.equals("E")) {
					msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";	
				}
				// Bottone non attivo in visualizzazione
				if(tipoForm.equals("V")) {
					btnActive = "disabled";	
				}
				
				// ===================
				// Dettaglio / Modifica / Elimina Dipartimento
				sql  =  "select a.id, a.description, a.parent_id, a.address, a.locality, "+
						"a.province, a.zipcode, a.state, a.phone, a.email, a.id_manager, "+
						"a.department_code, b.name, b.surname, minnum_intervals, tollerance, "+
						"nocturne, department_type, id_store, company_code, agency_code "+
						"from hr_departments a "+
						"left join hr_employee b on a.id_manager = b.id "+
						"where a.id = '"+$id+"' ";
				System.out.println(sql);
				
				rs=st.executeQuery(sql);
				String name = "";
				String surname = "";
				
				if(rs.next()) {
					$id=rs.getString("id");
					$description=rs.getString("description");
					$parentid=rs.getString("parent_id");
					$address=rs.getString("address");
					$locality=rs.getString("locality");
					$province=rs.getString("province");
					$zipcode=rs.getString("zipcode");
					$state=rs.getString("state");
					$phone=rs.getString("phone");
					$email=rs.getString("email");
					$id_manager=rs.getString("id_manager");
					$department_code=rs.getString("department_code");
					name = ""+rs.getString("name");
					surname = ""+rs.getString("surname");
					name = Utility.noNull(name);
					surname = Utility.noNull(surname);
					
					$emp_description=name+" "+surname;
					
					$minnum_intervals = rs.getString("minnum_intervals");
					$tollerance = rs.getString("tollerance");
					$nocturne = rs.getString("nocturne");
					
					$department_type = ""+rs.getString("department_type");
					$department_type = Utility.noNull($department_type);
					$id_store = ""+rs.getString("id_store");
					$id_store = Utility.noNull($id_store);
					
					$company_code = ""+rs.getString("company_code");
					$company_code = Utility.noNull($company_code);
					$agency_code = ""+rs.getString("agency_code");
					$agency_code = Utility.noNull($agency_code);
					
				}
				rs.close();
				
				// ====================
				// Lista dipendenti del dipartimento
				sql  =  "select c.id, c.name, c.surname, d.description from "+
						"rel_dep_employee b, hr_employee c, hr_departments d "+
						"where b.id_employee = c.id "+ 
						"and b.id_departments = d.id "+
						"and d.id = '"+$id+"' ";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				while(rs.next()) {
					
					emp.add(new Employee(rs.getString("id"),rs.getString("name"),rs.getString("surname"),"","","","","","",
							"","","","","","",rs.getString("description"),"","","","","","",""));
					
				}
				rs.close();
				
				
				
				
			}
			
			
			// Lista department type
			sql   = "select * from hr_department_type ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			while(rs.next()) {
				deptype.add(new Tables("DEPTYPE", rs.getString(1), rs.getString(2),"","","",""));
			}
			rs.close();
			
			
			// lista Stores Suite (Per customer)
			// Lista department type
			sql   = "select * from stores where id_customer = "+session.getAttribute("idcus")+" ";
			System.out.println(sql);
			rs=stAdmin.executeQuery(sql);
			while(rs.next()) {
				sto.add(new Tables("STORES", rs.getString("id"), rs.getString("description"),"","","",""));
			}
			rs.close();
			
			st.close();
			conn.close();
			
			stAdmin.close();
			connAdmin.close();
			
			obj.put("id", $id);
			obj.put("description", $description);
			obj.put("parentid", $parentid);
			obj.put("address", $address);
			obj.put("locality", $locality);
			obj.put("province", $province);
			obj.put("zipcode", $zipcode);
			obj.put("state", $state);
			obj.put("phone", $phone);
			obj.put("email", $email);
			obj.put("id_employee", $id_manager);
			obj.put("department_code", $department_code);
			obj.put("emp_description", $emp_description);
			obj.put("minnum_intervals", $minnum_intervals);
			obj.put("tollerance", $tollerance);
			obj.put("nocturne", $nocturne);
			
			obj.put("department_type", $department_type);
			obj.put("id_store", $id_store);
			
			obj.put("company_code", $company_code);
			obj.put("agency_code", $agency_code);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Dipartimento");
		
		
		
		obj.put("emp", emp);
		obj.put("deptype", deptype);
		obj.put("sto", sto);
		
		obj.put("tipoForm", tipoForm);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/hr/department/dep-dett", obj);
	}
	
}
