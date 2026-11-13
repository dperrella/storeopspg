<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="java.util.Calendar"
import="java.util.Locale"
import="java.io.*"
import="java.net.*"
import="pkg.beans.rfid.DocumentDetail"
import="java.util.ArrayList"
import="java.util.HashMap"
import="java.util.List"
import="java.util.Map"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%!
public class PrintZpl implements Runnable {

	private String ip;
	private int port;
	private String zpl;
	
	public PrintZpl(String ip, int port, String zpl) {
    	this.ip = ip;
    	this.port = port;
    	this.zpl = zpl;
   	}

   	public void run() {
   		
   		try {
   			Socket clientSocket=new Socket(ip, port);
       	 	DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream() );
       	 	outToServer.writeBytes(zpl);
    		clientSocket.close();
   		}
   		catch(Exception e) {
   			e.printStackTrace();
   		}
   	}
}
%>
<%
Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;
String sql 		= "";
String zpl		= "";

String iddoc = request.getParameter("iddoc");
String idprinter = request.getParameter("idprinter");
String idtemplate = request.getParameter("idtemplate");  
String zplfile = request.getParameter("zplfile");  
String perc = ""+request.getParameter("perc");  
perc = Utility.noNull(perc);

String etichettaRott = ""+request.getParameter("etichettaRott");  
etichettaRott = Utility.noNull(etichettaRott);

System.out.println("***** "+etichettaRott+" *****");

String idcus = session.getAttribute("idcus").toString();



List<DocumentDetail> doc = new ArrayList<DocumentDetail>();
doc.clear();

try {
	conn=Utility.getConnRfid();
	st=conn.createStatement();
	
	
	//breakpont
	String brk = "";
	sql = "select zpl from printers_templates where id = "+(etichettaRott.length()==0?"3":etichettaRott);
	rs=st.executeQuery(sql);
	if(rs.next()) {
		brk = rs.getString("zpl");
	}
	rs.close();
	
	sql = "select "+
			"replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace( "+
			"zpl, "+
			"'##art1##', coalesce(p.art1, null, '')) , "+
			"'##art2##',  coalesce(p.art2, null, '')) , "+
			"'##art3##' ,  coalesce(p.art3, null, '')) , "+
			"'##codfor##' ,  coalesce(p.codfor, null, '')) , "+
			"'##artfor##' ,  coalesce(p.artfor, null, '')) , "+
			"'##name##' ,  coalesce(p.name, null, '')) , "+
			"'##size##' ,  coalesce(p.size, null, '')) , "+
			"'##colordesc##' ,  coalesce(p.colordesc, null, '')) , "+
			"'##nas##' ,  coalesce(p.nas, null, '')) , "+
			"'##nas1##' ,  coalesce(p.nas1, null, '')) , "+
			"'##sku##' ,  coalesce(p.sku, null, '')) , "+
			"'##bcode1##' ,  coalesce(p.bcode1, null, '')) , "+
			"'##bcode2##' ,  coalesce(p.bcode2, null, '')) , "+
			
			"'##code##' ,  coalesce(p.code, null, '')) , "+
			"'##itemcode##' ,  coalesce(p.itemcode, null, '')) , "+
			"'##colourcode##' ,  coalesce(p.colourcode, null, '')) , "+
 			"'##variant##' ,  coalesce(p.variant, null, '')) , "+
		 	"'##part##' ,  coalesce(p.part, null, '')) ,  "+
 			"'##docref##' ,  coalesce(z.doc_ref, null, '')) ,  "+
		 	"'##docorigin##' ,  coalesce(z.doc_origin, null, '')) , "+
		 	"'##packid##' ,  coalesce(d.package_id, null, ''))  "+
		 			
 			
			"zpl, x.ip, x.port, p.itemcode, p.colourcode, p.sizecod, p.art1, p.art2, p.art3, p.codfor, p.artfor, p.name, p.colordesc "+
			"from documents_detail d "+
			"left join products p on p.sku = d.sku and p.id_customer = "+idcus+" "+
			"left join printers x on x.id = "+idprinter+" "+
			"left join printers_templates t on t.id = "+idtemplate+" "+
			"left join documents z on d.iddoc = z.id "+
			"where iddoc = "+iddoc+" order by package_id, itemcode, colourcode, sizecod ";
	
	
	System.out.println("2******* "+sql);
	rs=st.executeQuery(sql);
	
	String ip = "";
	int port = 0;
	
	String rott = "";
	int x = 0;
	while(rs.next()) {
	
		x++;
		//System.out.println(x);
		if(!rott.equals(rs.getString("itemcode")+rs.getString("colourcode")+rs.getString("art1"))) {
			
			System.out.println(x+" "+rs.getString("itemcode")+rs.getString("colourcode"));
			
			String brk2 = brk.replaceAll("##art1##", Utility.noNull(rs.getString("art1")))
					.replaceAll("##art1##", Utility.noNull(rs.getString("art1")))
					.replaceAll("##art2##", Utility.noNull(rs.getString("art2")))
					.replaceAll("##art3##", Utility.noNull(rs.getString("art3")))
					.replaceAll("##codfor##", Utility.noNull(rs.getString("codfor")))
					.replaceAll("##artfor##", Utility.noNull(rs.getString("artfor")))
					.replaceAll("##name##", Utility.noNull(rs.getString("name")))
					.replaceAll("##colordesc##", Utility.noNull(rs.getString("colordesc")))
					;
			zpl+=brk2;
			/*
			zpl+="^XA\n"+
				"^WVY^RS8^RU^FO540,320^XGE:RLOGO90A.GRF^FS\n"+
				"^RB96,8,3,3,24,20,38^FS\n"+
				"^FT110,440^ARB,70,70^FH\\^FD------^FS\n"+
				"^FT210,440^ARB,70,70^FH\\^FD"+rs.getString("itemcode")+"^FS\n"+
				"^FN1^RFR,E^FS\n"+
				"^RFR,H,2,12,1^FS\n"+
				"^PQ1,0,1,Y^XZ";
			*/
			
			
		}
		ip = rs.getString("ip");
		port = rs.getInt("port");
		zpl+=rs.getString("zpl")+"\n";
		
		rott=rs.getString("itemcode")+rs.getString("colourcode")+rs.getString("art1");
			
	}
	rs.close();
	
	//System.out.println(zpl);
	
	
	///////////////////////////////////////
	// gestione perc
	
	if(perc.length()>0) {
		doc = DocumentDetail.getDocumentsDetailByIdDocument(conn, Integer.parseInt(iddoc), false, "SKU");
		for (DocumentDetail row : doc) {
			System.out.println(row.getQty_theo()+" "+Math.floor((row.getQty_theo()*Double.parseDouble(perc))/100));
			
			if(Math.floor((row.getQty_theo()*Double.parseDouble(perc))/100)>0) {
				sql = "select "+
						"replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace( "+
						"zpl, "+
						"'##art1##', coalesce(p.art1, null, '')) , "+
						"'##art2##',  coalesce(p.art2, null, '')) , "+
						"'##art3##' ,  coalesce(p.art3, null, '')) , "+
						"'##codfor##' ,  coalesce(p.codfor, null, '')) , "+
						"'##artfor##' ,  coalesce(p.artfor, null, '')) , "+
						"'##name##' ,  coalesce(p.name, null, '')) , "+
						"'##size##' ,  coalesce(p.size, null, '')) , "+
						"'##colordesc##' ,  coalesce(p.colordesc, null, '')) , "+
						"'##nas##' ,  coalesce(p.nas, null, '')) , "+
						"'##nas1##' ,  coalesce(p.nas1, null, '')) , "+
						"'##sku##' ,  coalesce(p.sku, null, '')) , "+
						"'##bcode1##' ,  coalesce(p.bcode1, null, '')) , "+
						"'##bcode2##' ,  coalesce(p.bcode2, null, '')) , "+
						
						"'##code##' ,  coalesce(p.code, null, '')) , "+
						"'##itemcode##' ,  coalesce(p.itemcode, null, '')) , "+
						"'##colourcode##' ,  coalesce(p.colourcode, null, '')) , "+
			 			"'##variant##' ,  coalesce(p.variant, null, '')) , "+
					 	"'##part##' ,  coalesce(p.part, null, ''))  "+
			 			
						"zpl, x.ip, x.port, p.itemcode, p.colourcode, p.sizecod, p.art1, p.art2, p.art3, p.codfor, p.artfor, p.name, p.colordesc "+
						"from documents_detail d "+
						"left join products p on p.sku = d.sku and p.id_customer = "+idcus+" "+
						"left join printers x on x.id = "+idprinter+" "+
						"left join printers_templates t on t.id = "+idtemplate+" "+
						"where iddoc = "+iddoc+" and p.sku = '"+row.getSku()+"' order by itemcode, colourcode, sizecod limit 1 ";
				System.out.println("3******* "+sql);
				
				rs=st.executeQuery(sql);
				if(rs.next()) {
					
					for(int i=0;i<Math.floor((row.getQty_theo()*Double.parseDouble(perc))/100);i++) {
						
						zpl+=rs.getString("zpl")+"\n";
						
					}
					
				}
				rs.close();
			
			}
			
		}

	}
	///////////////////////////////////////
	
	if(!Boolean.parseBoolean(zplfile)) {
		Runnable r = new PrintZpl(ip, port, zpl);
		new Thread(r).start();
	}
	
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
<%=zpl%>