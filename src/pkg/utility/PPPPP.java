package pkg.utility;

import java.math.BigInteger;

public class PPPPP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String epc1 = "3035EBD2F83B8DEF6AA474C3";
		String epc2 = "3008499602D305C000002710";
		
		String header = "";
		String filter = "";
		String partition = "";
		String company_prefix = "";
		String extension = "";
		String RFU = "";
		
		int cp_length = 0;
		int cp_digits = 0;
		int ex_length = 0;
		int ex_digits = 0;
		
		String epcbin = "";
		
		int start = 0;
		for(int i=0;i<epc1.length();i++) {
			//System.out.println(i);
			String bin="0000"+hexToBin(epc1.substring(i,start+1));
			bin=bin.substring(bin.length()-4,bin.length()); 
			epcbin+=bin;
			start+=1;
		}
		
		if(epcbin.length()<96) {
			epcbin = "0000000000"+epcbin;
			epcbin = epcbin.substring(epcbin.length()-96,epcbin.length());
		}
		//System.out.println(hexToBin(toHexadecimal(t.getEpc().toHexString().replaceAll(" ", "").trim())));
		System.out.println(epcbin);
		
		String serial = epcbin.substring(epcbin.length()-38,epcbin.length());
		serial = ""+Integer.parseInt(serial, 2 );
		
		System.out.println(serial);
		
		
		
		
		header = epcbin.substring(0,8);
		filter = epcbin.substring(8,11);
		partition = epcbin.substring(11,14);
		
		
		partition = ""+Integer.parseInt(epcbin.substring(11,14), 2 );
		
		System.out.println(header);
		System.out.println(filter);
		System.out.println(partition);
		
		switch (partition) {
        case "0":  
        	cp_length = 40;
        	cp_digits = 12;
        	ex_length = 18;
        	ex_digits = 5;
            break;
        case "1":  
        	cp_length = 37;
        	cp_digits = 11;
        	ex_length = 21;
        	ex_digits = 6;
            break;
        case "2": 
        	cp_length = 34;
        	cp_digits = 10;
        	ex_length = 24;
        	ex_digits = 7;
            break;
        case "3":  
        	cp_length = 30;
        	cp_digits = 9;
        	ex_length = 28;
        	ex_digits = 8;
            break;
        case "4": 
        	cp_length = 27;
        	cp_digits = 8;
        	ex_length = 31;
        	ex_digits = 9;
            break;
        case "5": 
        	cp_length = 24;
        	cp_digits = 7;
        	ex_length = 34;
        	ex_digits = 10;
            break;
        case "6": 
        	cp_length = 20;
        	cp_digits = 6;
        	ex_length = 38;
        	ex_digits = 11;
            break;
		}
		
		
		company_prefix = ""+(int) Long.parseLong(epcbin.substring(14, (14+cp_length)), 2);
		extension = epcbin.substring((14+cp_length), (14+cp_length+ex_length));
		extension =  ""+new BigInteger(extension, 2);
		
		System.out.println(company_prefix);
		System.out.println(extension);
		
		
	}
	
	static String hexToBin(String s) {
		//System.out.println(s);
		return new BigInteger(s, 16).toString(2);
	}

}
