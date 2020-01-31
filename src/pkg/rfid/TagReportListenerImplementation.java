package pkg.rfid;

import java.util.List;

import pkg.rfid.ImpinjClient;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Tag;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;

public class TagReportListenerImplementation implements TagReportListener {
	
	public void onTagReported(ImpinjReader reader, TagReport report) {
		
		try {	
			
			List<Tag> tags = report.getTags();
			
			
			for (Tag t : tags) {							
				
				if(ImpinjClient.tags.contains(t.getEpc().toString().trim().replaceAll(" ", ""))) {
					// EPC gia esistente
	        	}
	        	else {
	        		
	        		// Aggiunge sempre a scanner / scannerdetail
	        		System.out.println(" EPC NUOVO: " + t.getEpc().toString().trim().replaceAll(" ", ""));
	    			
	        		// ***Multi
	        		ImpinjClient.tags.add(t.getEpc().toString().trim().replaceAll(" ", ""));
	        		
	        	}
				
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(ex.getMessage());
		}
	}
		

}
