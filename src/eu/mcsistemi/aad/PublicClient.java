package eu.mcsistemi.aad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.naming.ServiceUnavailableException;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.OutputStream;
import java.io.OutputStreamWriter; 

public class PublicClient {

    private final static String AUTHORITY = "https://login.microsoftonline.com/057b692d-19f3-4f35-be28-764d78ae6997";
    private final static String CLIENT_ID = "d9efda67-ef82-4d80-b97d-8623b027417f";

    public static void main(String args[]) throws Exception {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                System.in))) {
//            System.out.print("Enter username: ");
//            String username = br.readLine();
//            System.out.print("Enter password: ");
//            String password = br.readLine();
        	
        	String username = "suite@smartcontrolcheckpt.onmicrosoft.com";
        	String password = "Aprtssm$";

//        	String username = "dperrella@mcsistemi.net";
//        	String password = "Rodamayo02";

            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {

                        public java.security.cert.X509Certificate[] getAcceptedIssuers()
                        {
                            return null;
                        }
                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                        {
                            //No need to implement.
                        }
                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                        {
                            //No need to implement.
                        }
                    }
            };

            // Install the all-trusting trust manager
            try 
            {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } 
            catch (Exception e) 
            {
                System.out.println(e);
            }
            
            // Request access token from AAD
            AuthenticationResult result = getAccessTokenFromUserCredentials(
                    username, password);
            // Get user info from Microsoft Graph
            //String userInfo = getUserInfoFromGraph(result.getAccessToken());
            //System.out.println(userInfo);
            
            // Create User
            //String writeUser = writeUserFromGraph(result.getAccessToken());
            //System.out.println(writeUser);
            
            // Invite User
            String inviteUser = inviteUserFromGraph(result.getAccessToken());
            System.out.println(inviteUser);
            
            // Read All User
            //String users = getAllUserInfoFromGraph(result.getAccessToken());
            //System.out.println(users);
            
        }
   }

    private static AuthenticationResult getAccessTokenFromUserCredentials(
            String username, String password) throws Exception {
        AuthenticationContext context;
        AuthenticationResult result;
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(AUTHORITY, false, service);
            Future<AuthenticationResult> future = context.acquireToken(
                    "https://graph.microsoft.com", CLIENT_ID, username, password,
                    null);
            result = future.get();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException(
                    "authentication result was null");
        }
        return result;
    }

    private static String getUserInfoFromGraph(String accessToken) throws IOException {

        URL url = new URL("https://graph.microsoft.com/v1.0/me");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Accept","application/json");

        int httpResponseCode = conn.getResponseCode();
        if(httpResponseCode == 200) {
            BufferedReader in = null;
            StringBuilder response;
            try{
                in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            } finally {
                in.close();
            }
            return response.toString();
        } else {
            return String.format("Connection returned HTTP code: %s with message: %s",
                    httpResponseCode, conn.getResponseMessage());
        }
    }
    
    private static String inviteUserFromGraph(String accessToken) throws IOException {
    	
    	String invitedUserEmailAddress = "info@1dayapp.it";
    	String inviteRedirectUrl = "https://suitetest.azurewebsitest.net";
    	
    	String jsonInputData = "{\n" + 
    			"  \"invitedUserEmailAddress\": \""+invitedUserEmailAddress+"\",\n" + 
    			"  \"inviteRedirectUrl\": \""+inviteRedirectUrl+"\"\n" + 
    			"}";

	    HttpPost post = null;
	    HttpResponse response = null;
        try {
            String url = "https://graph.microsoft.com/v1.0/invitations";
            StringEntity s = new StringEntity(jsonInputData);
            HttpClient client = HttpClientBuilder.create().build();
            post = new HttpPost(url);

            // add header
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Authorization", "Bearer " + accessToken);
            post.setEntity(s);
            

            response = client.execute(post);
            
            HttpEntity httpEntity = response.getEntity();
            InputStream is = httpEntity.getContent();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            String json="";
            while ((line = rd.readLine()) != null) {
                json += line;
            }
            rd.close();
            
            
            
            System.out.println("Response Code : " 
                        + response.getStatusLine().getStatusCode());
            System.out.println(json);
        } finally {
            post.releaseConnection();
        }
                
        return response.toString();
        
	
    }
    
    private static String writeUserFromGraph(String accessToken) throws IOException {
    	
    	String displayName = "";
    	String mailNickname = "";
    	String givenName = "";
    	String surname = "";
    	String password = "";
    	
    	int count = 2;
    	String jsonInputData = "{ "+
	                "  \"accountEnabled\": true," + 
	                "  \"displayName\": \"pippo" +count+"\"," + 
	                "  \"mailNickname\": \"pippo" +count+"\"," +
	                "  \"givenName\": \"pippo\"," +
	                "  \"surname\": \"franco\"," +
	                
	                //"  \"country\": \"IT\"," +
	                "  \"userPrincipalName\": \"pippo"+count+"@smartcontrolcheckpt.onmicrosoft.com\"," + 
	                "  \"passwordProfile\" : {" + 
	                "    \"forceChangePasswordNextSignIn\": false," + 
	                "    \"password\": \"Test@123\"" + 
	                "  }" + 
	                "}";

	    HttpPost post = null;
	    HttpResponse response = null;
        try {
            String url = "https://graph.microsoft.com/v1.0/users";
            StringEntity s = new StringEntity(jsonInputData);
            HttpClient client = HttpClientBuilder.create().build();
            post = new HttpPost(url);

            // add header
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Authorization", "Bearer " + accessToken);
            post.setEntity(s);

            response = client.execute(post);
            System.out.println("Response Code : " 
                        + response.getStatusLine().getStatusCode());
        } finally {
            post.releaseConnection();
        }
                
        return response.toString();
        
	
    }
    
    private static String getAllUserInfoFromGraph(String accessToken) throws IOException {

        URL url = new URL("https://graph.microsoft.com/v1.0/users");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Accept","application/json");

        int httpResponseCode = conn.getResponseCode();
        if(httpResponseCode == 200) {
            BufferedReader in = null;
            StringBuilder response;
            try{
                in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            } finally {
                in.close();
            }
            return response.toString();
        } else {
            return String.format("Connection returned HTTP code: %s with message: %s",
                    httpResponseCode, conn.getResponseMessage());
        }
    }
    
}

