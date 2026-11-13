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
   			
   			//System.out.println(ip+" - "+port+" - "+zpl);
   			
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
String sku = request.getParameter("sku");
String qty = request.getParameter("qty");
String zplfile = request.getParameter("zplfile");  

String idcus = session.getAttribute("idcus").toString();

try {
	conn=Utility.getConnRfid();
	st=conn.createStatement();
	sql = "select replace(replace(replace(zpl, '##CODE##', p.code) , '##DESCR##', p.description) , '##SKU##' , p.sku) zpl, "+
			"x.ip, x.port "+
			"from documents_detail d "+
			"left join products p on p.sku = d.sku "+
			"left join printers x on x.id = "+idprinter+" "+
			"left join printers_templates t on t.id = "+idtemplate+" "+
			"where iddoc = "+iddoc+" and p.sku = '"+sku+"' ";
	
	sql = "select "+
		"replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace( "+
		"zpl, "+
		"'##art1##', p.art1) , "+
		"'##art2##', p.art2) , "+
		"'##art3##' , p.art3) , "+
		"'##codfor##' , p.codfor) , "+
		"'##artfor##' , p.artfor) , "+
		"'##name##' , p.name) , "+
		"'##size##' , p.size) , "+
		"'##colordesc##' , p.colordesc) , "+
		"'##nas##' , p.nas) , "+
		"'##nas1##' , p.nas1) , "+
		"'##sku##' , p.sku) , "+
		"'##bcode1##' , p.bcode1) , "+
		"'##bcode2##' , p.bcode2) "+
		"zpl, x.ip, x.port "+
		"from documents_detail d "+
		"left join products p on p.sku = d.sku "+
		"left join printers x on x.id = "+idprinter+" "+
		"left join printers_templates t on t.id = "+idtemplate+" "+
		"where iddoc = "+iddoc+" and p.sku = '"+sku+"' limit 1";
	
	
	/*
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
 			
			"zpl, x.ip, x.port "+
			"from documents_detail d "+
			"left join products p on p.sku = d.sku "+
			"left join printers x on x.id = "+idprinter+" "+
			"left join printers_templates t on t.id = "+idtemplate+" "+
			"where iddoc = "+iddoc+" and p.sku = '"+sku+"' limit 1";
	*/
	sql = "select "+
			"replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace( "+
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
		 	"'##docorigin##' ,  coalesce(z.doc_origin, null, '')) "+
		 			
 			
			"zpl, x.ip, x.port, p.itemcode, p.colourcode, p.sizecod, p.art1, p.art2, p.art3, p.codfor, p.artfor, p.name, p.colordesc "+
			"from documents_detail d "+
			"left join products p on p.sku = d.sku and p.id_customer = "+idcus+" "+
			"left join printers x on x.id = "+idprinter+" "+
			"left join printers_templates t on t.id = "+idtemplate+" "+
			"left join documents z on d.iddoc = z.id "+
			"where d.iddoc = "+iddoc+" and p.sku = '"+sku+"' limit 1";
	
	System.out.println(sql);
	rs=st.executeQuery(sql);
	
	String ip = "";
	int port = 0;
	while(rs.next()) {

		ip = rs.getString("ip");
		port = rs.getInt("port");
		
		
		for(int i=0;i<Integer.parseInt(qty);i++) {
			zpl+=rs.getString("zpl")+"\n";
		}
			
	}
	rs.close();
	
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