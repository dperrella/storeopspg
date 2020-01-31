package pkg.rfidws;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.util.Iterator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pkg.beans.Rfid_documents;
import pkg.beans.Rfid_functions;
import pkg.beans.Rfid_scanner;
import pkg.beans.Rfid_scannerdetail;
import pkg.beans.Rfid_step;
import pkg.utility.MySSLSocketFactory;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/CheckDocsTransact")
public class CheckDocsTransact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckDocsTransact() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		String idflow = request.getParameter("idflow");	
		String idstep = request.getParameter("idstep");	
		String docnum = request.getParameter("docnum");
		
		JSONObject responseJson = new JSONObject();
		String output = "";
		
		try {
			
			/*
		     *  fix for
		     *    Exception in thread "main" javax.net.ssl.SSLHandshakeException:
		     *       sun.security.validator.ValidatorException:
		     *           PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
		     *               unable to find valid certification path to requested target
		     */
			
			TrustManager[] trustAllCerts = new TrustManager[] {
		       new X509TrustManager() {
		          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		            return null;
		          }

		          public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

		          public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

		       }
		    };

		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		    // Create all-trusting host name verifier
		    HostnameVerifier allHostsValid = new HostnameVerifier() {
		        public boolean verify(String hostname, SSLSession session) {
		          return true;
		        }
		    };
		    // Install the all-trusting host verifier
		    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		    
		    
		    /*
		     * end of the fix
		     */
			
			conn=Utility.getConnRfid();
			
			// Testata documento
			Rfid_documents doc = new Rfid_documents();
			doc.setId_flow(Integer.parseInt(idflow));
			doc.setId_step(Integer.parseInt(idstep));
			doc.setDocnum(Integer.parseInt(docnum));
			doc.selectRowFromDocnum(conn);
			
			// Se non ha un riferimento per Transact rifiuta
			if(!doc.checkDocRef_destination(conn)) {
				
				String docref_destination = doc.getDocref_destination();
				String iddoc = doc.getIddoc();
				
				// Legge il config dello step per sapere quale funzione richiamare
				Rfid_step step = new Rfid_step();
				step.setIdflow(Integer.parseInt(idflow));
				step.setIdstep(Integer.parseInt(idstep));
				step.selectRow(conn);
				// Leggo i parametri per il before
				String config_during = step.getConfig_during();
				
				// Lo trasformo in json
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(config_during);
				JSONObject jsonObject = (JSONObject) _obj;
				System.out.println(jsonObject);
		
				// loop array
				JSONArray msg = (JSONArray) jsonObject.get("config");
				Iterator<String> iterator = msg.iterator();
				
				JSONObject cfg_bef = new JSONObject();
				while (iterator.hasNext()) {
				    
					Object obj2 = iterator.next();
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    
				    String name = (String) jsonObject2.get("name");
				    String tp = (String) jsonObject2.get("type");
				    
				    if(tp.equalsIgnoreCase("Text"))
				    	cfg_bef.put(name, (String) jsonObject2.get("value"));
				    else
				    	cfg_bef.put(name, jsonObject2.get("value"));
				}
				// leggo i parametri per l'importazione dei documenti
				String fun_check_doc = (String) cfg_bef.get("fun_check_doc");
				
				// legge come costruire la funzione per leggere da TransAct
				Rfid_functions fun = new Rfid_functions();
				fun.setName(fun_check_doc);
				fun.selectFunctionByName(conn);
				
				String parm = fun.getParameters();
				System.out.println(parm);
				
				parser = new JSONParser();
				_obj = parser.parse(parm);
				jsonObject = (JSONObject) _obj;
				System.out.println(jsonObject);
		
				// loop array
				msg = (JSONArray) jsonObject.get("parameters");
				iterator = msg.iterator();
				
				cfg_bef = new JSONObject();
				while (iterator.hasNext()) {
				    
					Object obj2 = iterator.next();
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    
				    String name = (String) jsonObject2.get("name");
				    String tp = (String) jsonObject2.get("type");
				    
				    if(tp.equalsIgnoreCase("Text"))
				    	cfg_bef.put(name, (String) jsonObject2.get("value"));
				    else
				    	cfg_bef.put(name, jsonObject2.get("value"));
				}
				String fun_url = (String) cfg_bef.get("url");
				String fun_token = (String) cfg_bef.get("token");
				String fun_username = (String) cfg_bef.get("username");
				String fun_password = (String) cfg_bef.get("password");
				String fun_shop = (String) cfg_bef.get("shop");
				String fun_reporttype = (String) cfg_bef.get("reporttype");
				
				
				
				
				// Controlla TransAct
				//String res = sendGet(fun_url+"/"+docref_destination+"?token="+fun_token+"&username="+fun_username+"&password="+fun_password+"&shop="+fun_shop+"&reporttype="+fun_reporttype);
				//System.out.println(res);
				
				
				
				String url = fun_url+"/"+docref_destination+"?token="+fun_token+"&username="+fun_username+"&password="+fun_password+"&shop="+fun_shop+"&reporttype="+fun_reporttype;
				
				//url = "https://cloud.checkpointsystems.com//advancloud/import/stock/download/00984310015246205987?token=checkpointitalypro&username=lpiantelli&password=79sGmrdD&shop=shop01&reporttype=json";
				
				HttpClient client = getNewHttpClient();
				HttpGet req = new HttpGet(url);

				// add request header
				req.addHeader("User-Agent", USER_AGENT);
				HttpResponse res2 = client.execute(req);

				System.out.println("Response Code : " 
			                + res2.getStatusLine().getStatusCode());

				BufferedReader rd = new BufferedReader(
					new InputStreamReader(res2.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				
				
				parser = new JSONParser();
				_obj = parser.parse(result.toString());
				responseJson = (JSONObject) _obj;
				
				JSONArray data = (JSONArray) responseJson.get("data");
				if(data.size()==0) {
					System.out.println("lista vuota");
				}
				else {
					System.out.println("lista piena, scrivo scannerdetail...");
					
					Rfid_scanner scan = new Rfid_scanner();
					scan.setId_flow(Integer.parseInt(idflow));
					scan.setId_step(Integer.parseInt(idstep));
					
					System.out.println(iddoc);
					
					scan.setIddoc(iddoc);
					scan.selectRowFromIddoc(conn);
					
					int idscan = scan.getIdscan();
					System.out.println(idscan);
					
					iterator = data.iterator();
					
					Rfid_scannerdetail scandet = new Rfid_scannerdetail();
					scandet.setIdscan(idscan);
					
					while (iterator.hasNext()) {
					    
						Object obj2 = iterator.next();
					    JSONObject jsonObject2 = (JSONObject) obj2;
					    
					    scandet.setBarcode((String) jsonObject2.get("skuid"));
					    scandet.setEpc((String) jsonObject2.get("epc"));
					    scandet.setNrscan(1);
					    scandet.insert(conn);
					    
					}
					
					// passa lo stato del documento a 2
					doc.setStatus(2);
					doc.updateStatus(conn);
					
				}
				
				
			}
			else {
				
				output = "Document not present in TransAct";
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			output = "KO! "+e.getMessage();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
	
	private final String USER_AGENT = "Mozilla/5.0";

	// HTTP GET request
	private String sendGet(String url) throws Exception {
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setInstanceFollowRedirects(false);
		con.setRequestMethod("GET");
		
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();

	}
	
	public HttpClient getNewHttpClient() {
	    try {
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);

	        MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	        return new DefaultHttpClient(ccm, params);
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}
	
}
