package QlikTestAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.*;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class Base {

	static {
	    disableSslVerification();
	}

	private static void disableSslVerification() {
	    try
	    {
	        // Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        }
	        };

	        // Install the all-trusting trust manager
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
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (KeyManagementException e) {
	        e.printStackTrace();
	    }
	}

	
	public void TicketRequest(String method, String server, String user, String userdirectory) throws IOException
	{
		//Create URL to REST endpoint for tickets
		String targeturl = "https://" + server + ":4243/qps/ticket";	
		HttpsURLConnection request = null; 
        
		//Create the HTTP Request and add required headers and content in Xrfkey
		String Xrfkey = "0123456789abcdef";
		URL url = new URL(targeturl + "?Xrfkey=" + Xrfkey);
		request = (HttpsURLConnection)url.openConnection();

		// Add the method to authentication the user
		request.setRequestMethod(method);
        request.setRequestProperty("Content-Type", "application/json");
        request.setRequestProperty("X-Qlik-Xrfkey", Xrfkey);
  
        
        // Add Certificate
        KeyStore keyStore;
		try {
			keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			
	        FileInputStream instream = new FileInputStream(new File("E://temp//SenseAPI//APIserver2.jks"));
	        keyStore.load(instream, "qlik123".toCharArray()); 
	        

	        instream.close();			
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, "qlik123".toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            request.setSSLSocketFactory(sslSocketFactory); 
            
            
            
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        

        String body = "{'UserDirectory':'" + userdirectory + "', 'UserId':'" + user + "','Attributes': []}";
        byte[] bodyBytes = body.getBytes("UTF-8");


              
        int responseCode = request.getResponseCode();
        
        System.out.println("Response Code : " + responseCode);
        		
		
		return;
	}
	

}
