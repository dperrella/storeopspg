package pkg.rfid;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Settings;

import pkg.rfid.TagReportListenerImplementation;

public class ImpinjClient {
	
	public static List tags  = new ArrayList();
	
	public static String getTags(String ip) {
		
		String arrayTags = "";
		
		try {
			
			ImpinjReader reader = new ImpinjReader();

            // Connect
            System.out.println("Connecting to " + ip);
            reader.connect(ip);

            // Get the default settings
            Settings settings = reader.queryDefaultSettings();

            // Apply the new settings
            reader.applySettings(settings);

            // connect a listener
            reader.setTagReportListener(new TagReportListenerImplementation());

            // Start the reader
            reader.start();

            System.out.println("Press Enter to exit.");
            
            
            //Scanner s = new Scanner(System.in);
            //s.nextLine();

            Thread.sleep(2000);
            
            System.out.println("Stopping  " + ip);
            reader.stop();
            
            for(int i=0;i<tags.size();i++) {
            	
            	arrayTags+=tags.get(i).toString()+",";
            	
            }
            if(arrayTags.length()>0)
            	arrayTags=arrayTags.substring(0,arrayTags.length()-1);
            
            
            System.out.println(arrayTags);
            
            System.out.println("Disconnecting from " + ip);
            reader.disconnect();

            System.out.println("Done");
            
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return arrayTags;
		
	}
	
}
