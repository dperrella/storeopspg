package pkg.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.zip.GZIPInputStream;


public class GetPing extends HttpServlet {
	
	
	private static String getWebPabeSource(String sURL) throws IOException {
	    URL url = new URL(sURL);
	    URLConnection urlCon = url.openConnection();
	    BufferedReader in = null;

	    if (urlCon.getHeaderField("Content-Encoding") != null
	            && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
	        in = new BufferedReader(new InputStreamReader(new GZIPInputStream(
	                urlCon.getInputStream())));
	    } else {
	        in = new BufferedReader(new InputStreamReader(
	                urlCon.getInputStream()));
	    }

	    String inputLine;
	    StringBuilder sb = new StringBuilder();

	    while ((inputLine = in.readLine()) != null)
	        sb.append(inputLine);
	    in.close();

	    return sb.toString();
	}
	
	private static final long serialVersionUID = 1L;
       

    public GetPing() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		  String host = request.getParameter("command");
          String cmd = "";
         
        if(System.getProperty("os.name").startsWith("Windows")) {   
                // For Windows
                cmd = "ping -n 2 " + host;
        } else {
                // For Linux and OSX
                cmd = "ping -c 2 " + host;
        }
         
          String resp = "";
          String command = cmd;
          String s = "";
          int k = 0;
          String flag = "KO";

		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			// reading output stream of the command
			while ((s = inputStream.readLine()) != null) {
				
				System.out.println(s);
//				flag = "OK";
				
			}
			p.waitFor();
			k = p.exitValue();
			
		} catch (Exception e) {
			e.printStackTrace();
		}


		resp = Integer.toString(k);

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
