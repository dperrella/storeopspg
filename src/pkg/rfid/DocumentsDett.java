package pkg.rfid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.DocumentsDetail3;
import pkg.utility.SGTIN96;
import pkg.utility.Utility;

public class DocumentsDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		List<DocumentsDetail3> doc = new ArrayList<DocumentsDetail3>();
		
		// Pulisce la lista
		doc.clear();
		
		String username = session.getAttribute("username").toString().toUpperCase();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String iddocuments = req.getParameter("iddoc");
		String idflow = req.getParameter("idflow");
		String idstep = req.getParameter("idstep");
		
		obj.put("iddocuments",iddocuments);
		obj.put("idflow",idflow);
		obj.put("idstep",idstep);
		
		
		
		String barcode = ""+req.getParameter("barcode");
		barcode = Utility.noNull(barcode);
		obj.put("barcode", barcode);
		
		if(barcode.length()==0)
			obj.put("labelBarcode", "Barcode Detail");
		else
			obj.put("labelBarcode", "Barcode Detail");
		
		String solodiff = ""+req.getParameter("solodiff");
		solodiff = Utility.noNull(solodiff);
		obj.put("solodiff", solodiff);
		
		try {
			// Apre la connessione
			conn = Utility.getConnRfid();
			st = conn.createStatement();
			DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
			
			if(barcode.length()==0) {
	    		
	    		sql   = "select tags , count(distinct(tags)) qty_scan, qty_theoretical qty_theo, ds1pro, a.id_package, codpro "
			    		+ "from scannerdetail a left join documents_details b on a.tags = epc "
			    		+ "and b.id_documents = '"+iddocuments+"' "
			    		+ "left join products p on p.edipro = a.barcode "
			    		+ "where a.idflow = '"+idflow+"' "
			    		+ "and a.idstep = "+idstep+" "
			    		+ "and a.iddocuments = '"+iddocuments+"' "
			    		+ "group by tags, qty_theoretical,ds1pro, a.id_package, codpro "
			    		+ "union "
			    		+ "select epc tags , count(distinct(tags)) qty_scan , qty_theoretical qty_theo,ds1pro, c.id_package, codpro "
			    		+ "from documents_details c left join scannerdetail d on d.tags = c.epc "
			    		+ "and d.idflow = '"+idflow+"' "
			    		+ "and d.idstep = "+idstep+" "
			    		+ "and d.iddocuments = '"+iddocuments+"' "
			    		+ "left join products p on p.edipro = c.barcode " 
			    		+ "where c.idflow = '"+idflow+"' "
			    		+ "and c.id_step = "+idstep+" "
			    		+ "and c.id_documents = '"+iddocuments+"' "
			    		+ "group by epc, qty_theoretical,ds1pro, c.id_package, codpro "
			    		+ "order by codpro ";
	    		
	    	}
	    	else {
	    		
	    		
	    		
	    		// Conta barcode senza considerare gli EPC
	    		if(idstep.equals("0")) {
	    			
		    		sql   = "select a.barcode as tags,ds1pro, a.id_package, codpro "
		    				+ "from scannerdetail a "
		    				+ "left join products on a.barcode = products.edipro "
				    		+ "where a.idflow = '"+idflow+"' "
				    		+ "and a.idstep = "+idstep+" "
				    		+ "and a.iddocuments = '"+iddocuments+"' "
				    		+ "group by a.barcode,ds1pro, a.id_package, codpro "
				    		+ "union "
				    		+ "select c.barcode as tags,ds1pro, c.id_package, codpro "
				    		+ "from documents_details c "
				    		+ "left join products on c.barcode = products.edipro "
				    		+ "where c.idflow = '"+idflow+"' "
				    		+ "and c.id_step = "+idstep+" "
				    		+ "and c.id_documents = '"+iddocuments+"' "
				    		+ "group by c.barcode,ds1pro, c.id_package, codpro "
				    		+ "order by codpro ";
	    		}
	    		// Conta barcode considerando gli EPC di docuemnts_details
	    		else {
	    			
	    			sql   = "select a.barcode as tags,ds1pro, a.id_package, codpro "+
		    				"from scannerdetail a "+
		    				"left join products "+
		    				"on a.barcode = products.edipro "+
		    				"where a.idflow = '"+idflow+"' "+
		    				"and a.idstep = "+idstep+" "+
		    				"and a.iddocuments = '"+iddocuments+"' "+
		    				"and a.tags in(select epc from documents_details x "+
		    				"where a.iddocuments = x.id_documents "+
		    				"and a.idflow = x.idflow and a.idstep = x.id_step) "+
		    				"group by a.barcode,ds1pro, a.id_package, codpro "+
		    				"union "+
		    				"select c.barcode as tags,ds1pro, c.id_package, codpro "+
		    				"from documents_details c left join products "+
		    				"on c.barcode = products.edipro "+
		    				"where c.idflow = '"+idflow+"' "+
		    				"and c.id_step = "+idstep+" "+
		    				"and c.id_documents = '"+iddocuments+"' "+
		    				"group by c.barcode,ds1pro, c.id_package, codpro "+
		    				"order by codpro ";
	    			
	    		}
	    	}
		    
		    System.out.println("*** "+sql);
		    rs=st.executeQuery(sql);
		    
		    String $epc = "";
		    int $qty_theo = 0;
		    int $qty_scan = 0;
		    String $status = "";
		    String $barcode = "";
		    String $check = "";
		    String $pkg = "";
		    String $codpro = "";
		    
		    int t_theo = 0;
		    int t_scan = 0;
		    
		    int summgt = 0;
			int summgr = 0;
			String Desc = "";
			String idscan_scan = "";
			
			while(rs.next()) {
		    	
		    	$epc = rs.getString("tags");
		    	$codpro = rs.getString("codpro");
		    	
		    	if(barcode.length()==0) {
			    	$qty_theo = rs.getInt("qty_theo");
			    	$qty_scan = rs.getInt("qty_scan");
			    }
		    	else {
		    		// legge le quantita con una funzione
		    		$qty_theo = getSkuQtyTheo(conn, $epc, idflow, idstep, iddocuments);
		    		$qty_scan = getSkuQtyScan2(conn, $epc, idflow, idstep, iddocuments, true);
		    	}
		    	
		    	summgt = summgt + $qty_theo;
		    	summgr = summgr + $qty_scan;
		    	
		    	t_theo+=$qty_theo;
		    	t_scan+=$qty_scan;
		    	
		    	try {
		    		$barcode = SGTIN96.decodeEpc($epc);
		    	}
		    	catch(Exception e) {
		    		$barcode = "?????????????";
		    	}

		    	
		    	//$check = getSkuCheck(conn, $epc, idflow, idstep, iddocuments, barcode, $qty_scan);
		    	
		    	if($qty_theo!=$qty_scan) {
		    		$status = "<i class=\"icon-ban text-danger\"></i>";
		    	}
		    	else {
		    		if($qty_theo == 0){
		    			$status = "<i class=\"icon-ban text-danger\"></i>";
		    		}else{
		    			$status = "<i class=\"icon-check text-success\"></i>";
		    		}
		    	}
		    	
		    	Desc = rs.getString("ds1pro");
		    	$pkg = rs.getString("id_package"); 
		    	$pkg = $codpro;
		    	
		    	if(solodiff.equals("S")) {
			    	
		    		count+=1;
		    		
		    		if($qty_theo!=$qty_scan) {
				    	doc.add(new DocumentsDetail3(iddocuments,idflow,idstep,
				    			$epc,""+$qty_theo,""+$qty_scan,"","","","",$barcode,$status,$barcode,$check,Desc,$pkg, ""+count,"S"));
		    		}
		    	
		    	}
		    	else {
		    		
		    		count+=1;
		    		
		    		doc.add(new DocumentsDetail3(iddocuments,idflow,idstep,
			    			$epc,""+$qty_theo,""+$qty_scan,"","","","",$barcode,$status,$barcode,$check,Desc,$pkg, ""+count,"S"));
		    		
		    	}
		    }
		    rs.close();
		    
		    
		    
		    obj.put("summgt", summgt);
		    obj.put("summgr", summgr);
		    
		    if(t_theo==t_scan) {
		    	obj.put("statusGenerale", "<i class=\"icon-check text-success\"></i>");
		    }
		    else {
		    	obj.put("statusGenerale", "<i class=\"icon-ban text-danger\"></i>");
		    }
		   
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Lista Documenti");
		obj.put("doc", doc);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid/docs/docs-dett", obj);
	}
	
public static int getSkuQtyTheo(Connection conn, String sku, String idflow, String idstep, String iddoc) {
		
		int skuQty = 0;
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			
			sql   = "select count(distinct(epc)) qty "+
					"from documents_details "+
					"where idflow = '"+idflow+"' and id_step = "+idstep+" "+
					"and id_documents = '"+iddoc+"' "+
					"and barcode = '"+sku+"' ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				skuQty = rs.getInt("qty");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		
		return skuQty;
	}
	
	
	public static int getSkuQtyScan(Connection conn, String sku, String idflow, String idstep, String iddoc) {
		
		int skuQty = 0;
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			
			sql   = "select count(distinct(tags)) qty "+
					"from scannerdetail "+
					"where idflow = '"+idflow+"' and idstep = "+idstep+" "+
					"and iddocuments = '"+iddoc+"' "+
					"and barcode = '"+sku+"' ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				skuQty = rs.getInt("qty");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		
		return skuQty;
	}
	
	public static int getSkuQtyScan2(Connection conn, String sku, String idflow, String idstep, String iddoc, boolean inDoc) {
		
		int skuQty = 0;
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			
			if(idstep.equals("0")) {
				sql   = "select count(distinct(tags)) qty "+
						"from scannerdetail "+
						"where idflow = '"+idflow+"' and idstep = "+idstep+" "+
						"and iddocuments = '"+iddoc+"' "+
						"and barcode = '"+sku+"' ";
			}
			else {
				if(inDoc) {
					sql   = "select count(distinct(tags)) qty "+
							"from scannerdetail a "+
							"where a.idflow = '"+idflow+"' and a.idstep = "+idstep+" "+
							"and a.iddocuments = '"+iddoc+"' "+
							"and a.barcode = '"+sku+"' "+
							"and a.tags in(select x.epc from documents_details x "+
		    				"where a.iddocuments = x.id_documents "+
		    				"and a.idflow = x.idflow and a.idstep = x.id_step) ";
				}
				else {
					sql   = "select count(distinct(tags)) qty "+
							"from scannerdetail a "+
							"where a.idflow = '"+idflow+"' and a.idstep = "+idstep+" "+
							"and a.iddocuments = '"+iddoc+"' "+
							"and a.barcode = '"+sku+"' "+
							"and a.tags not in(select x.epc from documents_details x "+
		    				"where a.iddocuments = x.id_documents "+
		    				"and a.idflow = x.idflow and a.idstep = x.id_step) ";
				}
			}
			
			rs=st.executeQuery(sql);
			if(rs.next()) {
				skuQty = rs.getInt("qty");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		
		return skuQty;
	}
	
	
	
	public static String getSkuCheck(Connection conn, String code, String idflow, String idstep, String iddoc, String barcode, int qty) {
		
		String check = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			
			if(barcode.equals("S")) {
				sql   = "select status "+
						"from scannerdetail "+
						"where idflow = '"+idflow+"' and idstep = "+idstep+" "+
						"and iddocuments = '"+iddoc+"' "+
						"and barcode = '"+code+"' ";
			}
			else {
				sql   = "select status "+
						"from scannerdetail "+
						"where idflow = '"+idflow+"' and idstep = "+idstep+" "+
						"and iddocuments = '"+iddoc+"' "+
						"and tags = '"+code+"' ";
			}
			rs=st.executeQuery(sql);
			if(rs.next()) {
				check = ""+rs.getString("status");
				check = Utility.noNull(check);
			}
			rs.close();
			
			if(idstep.equals("0")) {
				
				System.out.println("qty scan: "+qty);
				
				sql   = "select nrscan from scanner "+
						"where idflow = '"+idflow+"' "+
						"and idstep = '"+idstep+"' "+
						"and iddocuments = '"+iddoc+"' order by nrscan desc fetch first 1 rows only ";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				int nrscan = 0;
				if(rs.next()) {
					nrscan = rs.getInt("nrscan");
				}
				rs.close();
				
				// status ultima lettura
				sql   = "select status "+
						"from scannerdetail a, scanner b "+
						"where a.idscan = b.idscan and a.idflow = '"+idflow+"' and a.idstep = "+idstep+" "+
						"and a.iddocuments = '"+iddoc+"' "+
						"and barcode = '"+code+"' "+
						"and nrscan = "+nrscan+" ";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				if(rs.next()) {
					check = ""+rs.getString("status");
					check = Utility.noNull(check);
				}
				rs.close();
				
				if(nrscan>1) {
					nrscan = nrscan-1;
					
					sql   = "select count(distinct(tags)) qty "+
							"from scannerdetail a, scanner b "+
							"where a.idscan = b.idscan and a.idflow = '"+idflow+"' and a.idstep = "+idstep+" "+
							"and a.iddocuments = '"+iddoc+"' "+
							"and barcode = '"+code+"' "+
							"and nrscan <= "+nrscan+" ";
					System.out.println(sql);
					rs=st.executeQuery(sql);
					int skuQty = 0;
					if(rs.next()) {
						skuQty = rs.getInt("qty");
					}
					rs.close();
					
					System.out.println("qty OLD : "+skuQty);
					
					
					if(qty>skuQty) {
						if(check.length()==0) {
							check = " ";
						}
					}
				}
			}
			
			if(check.equals("X"))
				check = "checked";
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		
		return check;
	}
}
