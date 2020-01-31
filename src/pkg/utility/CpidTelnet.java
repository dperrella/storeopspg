package pkg.utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;

public class CpidTelnet {

	public static void main(String[] args) {

		String ip = "192.168.51.239";	
		int port = 40012;	
		String cmd = "get taglist";
		
		String arrayTags = getTags(ip, port, cmd);
		
		System.out.println(arrayTags);
		
	}
	
	static TelnetClient tc = null;
    
	public static String getTags(String ip, int port, String cmd) {
		
		String tagList = "";
		
		try {
			FileOutputStream fout = null;
			
			String remoteip = ip;
	        int remoteport = port;
			
	        try
	        {
	            fout = new FileOutputStream ("spy.log", true);
	        }
	        catch (IOException e)
	        {
	            System.err.println(
	                "Exception while opening the spy file: "
	                + e.getMessage());
	        }

	        tc = new TelnetClient();

	        TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler("VT100", false, false, true, false);
	        EchoOptionHandler echoopt = new EchoOptionHandler(true, false, true, false);
	        SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);

	        try
	        {
	            tc.addOptionHandler(ttopt);
	            tc.addOptionHandler(echoopt);
	            tc.addOptionHandler(gaopt);
	        }
	        catch (InvalidTelnetOptionException e)
	        {
	            System.err.println("Error registering option handlers: " + e.getMessage());
	        }
	        
	        
	        
	        tc.connect(remoteip, remoteport);
	        
	        
	        OutputStream outstr = tc.getOutputStream();

	        byte[] buff = new byte[1024];
	        int ret_read = 0;

	        
	        outstr.write("\n".getBytes(), 0, 1);
	    	//outstr.write(buff, 0 , ret_read);
	        outstr.flush();
	        
	        outstr.write((cmd+"\n").getBytes(), 0, 12);
	    	//outstr.write(buff, 0 , ret_read);
	        outstr.flush();
	        
	        Thread.sleep(500);
	        
	        InputStream instr = tc.getInputStream();

	        String resp = "";
	        try
	        {
	            buff = new byte[1024];
	            ret_read = 0;

	            do
	            {
	                ret_read = instr.read(buff);
	                //System.out.println(ret_read);
	                if(ret_read > 0)
	                {
	                	
	                	resp+=new String(buff, 0, ret_read);
	                	//System.out.print(new String(buff, 0, ret_read));
	                	
	                	
	                }
	                
	                if(resp.substring(resp.length()-6,resp.length()).equals("mpos> ")) {
	                	ret_read = 0;
	                }
	            }
	            while (ret_read > 0);
	            
	            String temp[] = resp.split("mpos> ");
	            String temp2[] = temp[1].split("Total ");
	            
	            
	            String tags[] = temp2[0].split("\n");
	            for (int i=0;i<tags.length;i++) {
	            	//System.out.println("------");
	            	if(tags[i].trim().length()>0) {
	            		
	            		String record[] = tags[i].split(" ");
	            		
	            		tagList+=record[2]+",";
	            		
	            	}
	            }
	            
	            if(tagList.length()>0)
	            	tagList = tagList.substring(0,tagList.length()-1);
	            
	            //System.out.println(tagList);
	            
	        }
	        catch (IOException e)
	        {
	            System.err.println("Exception while reading socket:" + e.getMessage());
	        }
	        
	        
	        
	        
	        try
	        {
	            tc.disconnect();
	        }
	        catch (IOException e)
	        {
	            System.err.println("Exception while connecting:" + e.getMessage());
	        }
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return tagList;
	}

}
