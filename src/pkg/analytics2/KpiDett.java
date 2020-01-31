package pkg.analytics2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class KpiDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		PreparedStatement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		String key = "";
		
		DateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		
		
		String xcdel="";
		String xctab="";
		String xdesc="";
		boolean cal = false;
		String col="";
		String sym="";
		String sign="";
		String kpi1="";
		String kpi2="";
		String kpiopt = "";
		String kpiout = "";
		String dooropt = "";
		String doorout = "";
		
		
		/**/
		
		// Campi database
		String $code="";
		String $codeold="";
		String $name="";
		String $desc="";
		String $symb="";
		String $calc="";
		boolean $calc_=false;
		String $colo="";
		String $pare="";
		String $ata="";
		String $tabs="KPI";
		String kpiSel = "";
		String $sign = "";
		String $kpi1 = "";
		String $kpi2 = "";
		String $ctype = "";
		String $kpig = "";
		String $door_type = "";
		String $cctype = "";
		
		
		String $order = "";
		
		try {
			/**/

			
			conn = Utility.getConnData();
			st2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			sql  =  "select xcdel from public.ztables ";
			rs = st2.executeQuery(sql);
			while(rs.next()) {
				kpiSel+= "<option value='"+rs.getString("xcdel")+"'>"+rs.getString("xcdel")+"</option>";
				
			}
			System.out.println("kpiSel..." + kpiSel);
			rs.close();			
			/**/
			
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					$code = req.getParameter("code");
					$name = req.getParameter("name");
					$desc = req.getParameter("description");
					$calc = req.getParameter("calculate");
					//$calc = req.getParameter("HidCal");
					$ctype = Utility.noNull(""+req.getParameter("calculate_type"));
					int $ctype_ = Integer.parseInt($ctype);
					$door_type = Utility.noNull(""+req.getParameter("hid_door_type"));
					
					boolean filtered = false;
					if($door_type.length()>0)
						filtered = true;
					else
						filtered = false;
					/*
					if ($calc.equals("t") || $calc.equals(true)) {
						$calc_ = true;
					}else {
						$calc_ = false;
					}
					*/
					
					$calc_ = true;
					
					$symb = req.getParameter("symbol");
					$colo = "#"+req.getParameter("color");
					
					$order = ""+req.getParameter("order");
					$order = Utility.noNull($order);
					
					sql   = "insert into public.ztables "+
							"(xcdtb, xcdel, xdtab, xdesc, ata32, parent, calculated, color, kpi_symbol, calculated_type, filtered, order_position) "+
							"values "+
							"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
					
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					
					st.setString(1, $tabs);
					st.setString(2, $code.toUpperCase());
					st.setString(3, $name);
					st.setString(4, $desc);
					st.setString(5, $ata);
					st.setString(6, $pare);
					st.setBoolean(7, $calc_);
					st.setString(8, $colo);
					st.setString(9, $symb);
					st.setInt(10, $ctype_);
					st.setBoolean(11, filtered);
					if($order.length()==0)
						st.setNull(12, java.sql.Types.INTEGER);
					else 
						st.setInt(12, Integer.parseInt($order));
					
					// Esegue l'sql
					System.out.println("sql_kpi_ins = " + sql);
					st.executeUpdate();
					//st.executeQuery();
					/*	
					if(rs.next()) {
						$code = rs.getString(1);
						System.out.println("year: "+rs.getString(1));
					}
					else {
						$code= "-1";
						System.out.println("year: -1");
					}
					*/
					//rs.close();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					//tipoForm = "M";
					tipoForm = "I";
					
					$sign = Utility.noNull(""+req.getParameter("kpisymb"));
					$kpi1 = Utility.noNull(""+req.getParameter("kpi1"));
					$kpi2 = Utility.noNull(""+req.getParameter("kpi2"));
					$ctype = Utility.noNull(""+req.getParameter("calculate_type"));
					$kpig = Utility.noNull(""+req.getParameter("hid_grouped"));
					
							
					if ($calc.equals("t") || $calc.equals(true)) {

						if ($ctype.equals("1")) {
						sql   = "INSERT INTO public.calculatedkpi(kpi, sign, kpi1, kpi2)VALUES (?, ?, ?, ?)";
						System.out.println("sql_kpi_true = " + sql);
						st = conn.prepareStatement(sql);
						st.setString(1, $code.toUpperCase());
						st.setString(2, $sign);
						st.setString(3, $kpi1);
						st.setString(4, $kpi2);
						st.executeUpdate();
						
						} else {

							String[] appk = $kpig.split(",");
							
							for (int r=0; r < appk.length; r++) {

								sql   = "INSERT INTO public.kpi_grouped(grouped_kpi, kpi)VALUES (?, ?)";
								System.out.println("sql_kpi_true = " + sql);
								st = conn.prepareStatement(sql);
								st.setString(1, $code.toUpperCase());
								st.setString(2, appk[r]);
								st.executeUpdate();
							}
							
						}
						
						
						String[] doors = $door_type.split(",");
						
						if($door_type.length()>0) {
							
							for (int r=0; r < doors.length; r++) {
	
								sql   = "INSERT INTO public.rel_kpi_doors(idkpi, iddoor)VALUES (?, ?)";
								System.out.println("sql_kpi_doors = " + sql);
								st = conn.prepareStatement(sql);
								st.setString(1, $code.toUpperCase());
								st.setInt(2, Integer.parseInt(doors[r]));
								st.executeUpdate();
							}
							
						}
						
					}
				
				}
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MOD...");
					
					$code = req.getParameter("code");
					$codeold = req.getParameter("code");
					$name = req.getParameter("name");
					$desc = req.getParameter("description");
					$calc = req.getParameter("HidCal");
					
					$sign = req.getParameter("kpisymb");
					$kpi1 = req.getParameter("kpi1");
					$kpi2 = req.getParameter("kpi2");
					$ctype = Utility.noNull(""+req.getParameter("calculate_type"));
					$kpig = Utility.noNull(""+req.getParameter("hid_grouped"));
					$door_type = Utility.noNull(""+req.getParameter("hid_door_type"));
					
					boolean filtered = false;
					if($door_type.length()>0)
						filtered = true;
					else
						filtered = false;
					
					
					if ($calc.equals("t") || $calc.equals("true")) {
						$calc_ = true;
					}else {
						$calc_ = false;
					}		
					System.out.println("$calc_ = "+$calc_);
				
					
					$symb = req.getParameter("symbol");
					$colo = "#"+req.getParameter("color");
					
					$order = ""+req.getParameter("order");
					$order = Utility.noNull($order);
					
					/*
					sql  =  "UPDATE public.ztables " + 
							"   SET xcdel=?, xdtab=?, xdesc=?, calculated=?, " + 
							"   color=?, kpi_symbol=?, calculated_type=? where xcdel = ?";
					*/
					sql  =  "UPDATE public.ztables " + 
							"   SET xcdel=?, xdtab=?, xdesc=?, " + 
							"   color=?, kpi_symbol=?, calculated_type=?, filtered = ?, order_position = ? where xcdel = ?";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri			
					st.setString(1, $code.toUpperCase());
					st.setString(2, $name);
					st.setString(3, $desc);
					//st.setBoolean(4, $calc_);
					st.setString(4, $colo);
					st.setString(5, $symb);
					
					System.out.println($ctype);
					if($ctype.length()>0) {
						st.setInt(6, Integer.parseInt($ctype));
					}
					else {
						st.setNull(6, java.sql.Types.INTEGER);
					}
					st.setBoolean(7, filtered);
					
					if($order.length()>0) {
						st.setInt(8, Integer.parseInt($order));
					}
					else {
						st.setNull(8, java.sql.Types.INTEGER);
					}
					st.setString(9, $codeold);
					
					
					// Esegue l'sql
					st.executeUpdate();
					
					
					if ($calc.equals("t") || $calc.equals("true")) {
						
						if ($ctype.equals("1")) {
							String _xcdel = ""; 
							/**/
							sql  =  "select * from public.calculatedkpi where upper(kpi) = ?";
							st = conn.prepareStatement(sql);
							st.setString(1, $code.toUpperCase());
							rs=st.executeQuery();
							
							if(rs.next()) {
								_xcdel=rs.getString("kpi");
							}
							rs.close();
							/**/
							
							sql   = "delete from public.kpi_grouped where upper(grouped_kpi) = ?";
							st = conn.prepareStatement(sql);
							st.setString(1, $code.toUpperCase());
							st.executeUpdate();
							
							if ("".equals(_xcdel)) {
								sql   = "INSERT INTO public.calculatedkpi(sign, kpi1, kpi2, kpi)VALUES (?, ?, ?, ?)";
							}else {
								sql   = "UPDATE public.calculatedkpi set sign=?, kpi1=?, kpi2=? where upper(kpi) = ?";
							}
						
							st = conn.prepareStatement(sql);
							st.setString(1, $sign);
							st.setString(2, $kpi1);
							st.setString(3, $kpi2);
							st.setString(4, $code.toUpperCase());
							st.executeUpdate();
							st.close();
							
						} else {
							
							sql   = "delete from public.kpi_grouped where upper(grouped_kpi) = ?";
							st = conn.prepareStatement(sql);
							st.setString(1, $code.toUpperCase());
							st.executeUpdate();
							st.close();
							
							sql   = "delete from public.calculatedkpi where upper(kpi) = ?";
							st = conn.prepareStatement(sql);
							st.setString(1, $code.toUpperCase());
							st.executeUpdate();
							st.close();
							
							String[] appk = $kpig.split(",");
							
							
							
							for (int r=0; r < appk.length; r++) {

								sql   = "INSERT INTO public.kpi_grouped(grouped_kpi, kpi)VALUES (?, ?)";
								System.out.println("sql_kpi_true = " + sql);
								st = conn.prepareStatement(sql);
								st.setString(1, $code.toUpperCase());
								st.setString(2, appk[r]);
								st.executeUpdate();	
								st.close();
							}
							
						}
						
						sql   = "delete from public.rel_kpi_doors where upper(idkpi) = ?";
						st = conn.prepareStatement(sql);
						st.setString(1, $code.toUpperCase());
						st.executeUpdate();
						st.close();
						
						
						System.out.println("doors: "+$door_type);
						
						if($door_type.length()>0) {
							String[] doors = $door_type.split(",");
							for (int r=0; r < doors.length; r++) {
	
								sql   = "INSERT INTO public.rel_kpi_doors(idkpi, iddoor)VALUES (?, ?)";
								System.out.println("sql_kpi_doors = " + sql);
								st = conn.prepareStatement(sql);
								st.setString(1, $code.toUpperCase());
								st.setInt(2, Integer.parseInt(doors[r]));
								st.executeUpdate();
							}
						}
						
						
						
					}else {
						sql   = "delete from public.calculatedkpi where kpi = ?";
						st = conn.prepareStatement(sql);
						st.setString(1, $codeold);
						st.executeUpdate();
						st.close();
						
					}
					
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					
					
						
				}
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					$codeold = req.getParameter("code");
					
					sql   = "delete from public.ztables where xcdel = ?";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1, $codeold);
					
					// Esegue l'sql
					st.executeUpdate();
					
					// *blank id
					
					sql   = "delete from public.calculatedkpi where kpi = ?";
					st = conn.prepareStatement(sql);
					st.setString(1, $codeold);
					st.executeUpdate();
					
					sql   = "delete from public.kpi_grouped where grouped_kpi = ?";
					st = conn.prepareStatement(sql);
					st.setString(1, $codeold);
					st.executeUpdate();
					
					sql   = "delete from public.rel_kpi_doors where idkpi = ?";
					st = conn.prepareStatement(sql);
					st.setString(1, $codeold);
					st.executeUpdate();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "I";
					
				}
				
			}
			
			if(tipoForm.equals("V")||tipoForm.equals("M")||tipoForm.equals("E")) {
				
				key = "readonly";
				
				$codeold = req.getParameter("code");
				
				// Messaggio conferma cancellazione record
				if(tipoForm.equals("E")) {
					msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";	
				}
				// Bottone non attivo in visualizzazione
				if(tipoForm.equals("V")) {
					btnActive = "disabled";	
				}
				
				
				// ===================
				// Dettaglio / Modifica / Elimina ruolo
				sql  =  "select * from public.ztables left join calculatedkpi on xcdel = kpi where xcdel = ?";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				st.setString(1, $codeold);
				System.out.println("sql+ = " + sql);	
				rs=st.executeQuery();
				
				if(rs.next()) {
					xcdel=rs.getString("xcdel");
					xctab=rs.getString("xdtab");
					xdesc=rs.getString("xdesc");
					cal=rs.getBoolean("calculated");
					col=Utility.noNull(""+rs.getString("color"));
					if (!"".equals(col)) {
						col = col.replace("#", "");
					}
					sym=rs.getString("kpi_symbol");
					sign=rs.getString("sign");
					kpi1=rs.getString("kpi1");
					kpi2=rs.getString("kpi2");
					$cctype=rs.getString("calculated_type");
					$order=""+rs.getString("order_position");
					$order=Utility.noNull($order);
				}
				rs.close();
		
				
				sql  =  "select * from public.ztables left join kpi_grouped on xcdel = grouped_kpi where xcdel = ?";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				st.setString(1, $codeold);
				// Esegue l'sql
				rs=st.executeQuery();				
				while(rs.next()) {
					if ("".equals(kpiout)) {
						kpiout = "'"+Utility.noNull(rs.getString("kpi"))+"'";
					}else {
						kpiout = kpiout + "," + "'"+Utility.noNull(rs.getString("kpi"))+"'";
					}
				}
				rs.close();
				
				
				sql  =  "select * from public.ztables left join rel_kpi_doors on trim(xcdel) = trim(idkpi) where xcdel = ?";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				st.setString(1, $codeold);
				// Esegue l'sql
				rs=st.executeQuery();				
				while(rs.next()) {
					if ("".equals(doorout)) {
						doorout = "'"+Utility.noNull(rs.getString("iddoor"))+"'";
					}else {
						doorout = doorout + "," + "'"+Utility.noNull(rs.getString("iddoor"))+"'";
					}
				}
				rs.close();
				
				System.out.println("doorout: "+doorout);
				
				
				
				// ===================
				// Dettaglio / Modifica / Elimina
				sql  = "select * from ztables where xcdtb = 'KPI' ";
				//System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs=st.executeQuery();				
				while(rs.next()) {
					kpiopt += "<option value='"+rs.getString("xcdel")+"'>"+rs.getString("xdtab")+"</option>";
				}
				rs.close();
				
			}
			
			sql  =  "select * from aisle_type ";
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();				
			while(rs.next()) {
				dooropt += "<option value='"+rs.getString("id")+"'>"+rs.getString("description")+"</option>";
			}
			rs.close();
			
			obj.put("Xcdel", xcdel);
			obj.put("Xctab", xctab);
			obj.put("Xdesc", xdesc);
			obj.put("Cal", cal);
			obj.put("Col", col);
			obj.put("Sym", sym);
			obj.put("order", $order);
			
			obj.put("Sign", sign);
			obj.put("Kpi1", kpi1);
			obj.put("Kpi2", kpi2);
			obj.put("Kpiopt", kpiopt);
			obj.put("Kpiout", kpiout);
			obj.put("dooropt", dooropt);
			obj.put("doorout", doorout);
			
			if(st!=null)
				st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Finacial Calendar");
		
		obj.put("tipoForm", tipoForm);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		obj.put("key", key);
		obj.put("Codeold", $codeold);
		obj.put("KpiSel", kpiSel);
		obj.put("Ctype", $cctype);
		

		
		return new ModelAndView("/analytics2/anag/kpi-dett", obj);
	}
	
}
