
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

//import com.ibm.json.java.JSONArray;
//import com.ibm.json.java.JSONObject;

import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
public class machineTranslation {
	private static String baseURL = "https://gateway.watsonplatform.net/machine-translation-beta/api/v1/smt/0";
	private static String username = "a5f339b4-eaf7-480f-ab0c-040f6268372e";
	private static String password = "yaiQNKPucHNT";
	
	public static void main(String[] args) throws ClientProtocolException, URISyntaxException, IOException {
			doStuff();
	}
	
	static void doStuff() throws URISyntaxException, ClientProtocolException, IOException{
		System.out.println("starting");
		 
		//String text = FileUtils.readFileToString(new File("bio.txt"), "UTF-8");
		String text = "Hey, IBM Blue mix is working!";
		System.out.println("text:"+text);
		String sid = "mt-enus-eses";
		
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("txt",text));
		qparams.add(new BasicNameValuePair("sid",sid ));
		qparams.add(new BasicNameValuePair("rt","text" ));
		try{
			Executor executor = Executor.newInstance().auth(username, password);
			URI serviceURI = new URI(baseURL).normalize();
		    String auth = username + ":" + password;
			String response = executor.execute(Request.Post(serviceURI)
				.addHeader("Authorization", "Basic "+ Base64.encodeBase64String(auth.getBytes()))
			    .bodyString(URLEncodedUtils.format(qparams, "utf-8"), 
			    		ContentType.APPLICATION_FORM_URLENCODED)
			    ).returnContent().asString();
	
			//JSONObject lang = JSONObject.parse(response);
			System.out.println(response);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
