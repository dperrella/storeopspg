package pkg.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Pwd {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		String password = "nuvolari";
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		
		System.out.println(sb.toString());
		
	}

}
