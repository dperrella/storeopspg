package com.example.sdksamples;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Tag;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import pkg.rfidws.ImpinjRead;

public class TagReportListenerImplementation implements TagReportListener {

    @Override
    public void onTagReported(ImpinjReader reader, TagReport report) {
        List<Tag> tags = report.getTags();
        
        for (Tag t : tags) {
        	
        	try {
        		String epcbin = hexToBin(t.getEpc().toHexString().replaceAll(" ", "").trim());
        		if(epcbin.length()<96) {
        			epcbin = "0000000000"+epcbin;
        			epcbin = epcbin.substring(epcbin.length()-96,epcbin.length());
        		}
        		//System.out.println(hexToBin(toHexadecimal(t.getEpc().toHexString().replaceAll(" ", "").trim())));
        		System.out.println(epcbin);
        		//System.out.println(hexToBin("3129D5A49BD46F4D16000000".toLowerCase()));
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
        	
        	//System.out.println(" EPC: " + t.getEpc().toString());
        	if(ImpinjRead.tags.contains(t.getEpc().toString().trim())) {
        		//System.out.println(" EPC GIA' ESISTENTE: " + t.getEpc().toString());
        		
        		
        		
        	}
        	else {
        		//System.out.println(" EPC NUOVO: " + t.getEpc().toString());
        		

            	
            	
        		ImpinjRead.tags.add(t.getEpc().toString().trim());
        	}
        }
        
    }
    
    static String hexToBin(String s) {
	  return new BigInteger(s, 16).toString(2);
	}
}
