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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
public class ConceptInsights {
	private static String baseURL = "https://gateway.watsonplatform.net/concept-insights-beta/api/v1/graph/wikipedia/en-20120601?func=annotateText";
	private static String username = "cb9c0c6c-a515-4c93-9f47-b9119d6f2157";
	private static String password = "EQ5ZeRBxY26q";
	
	public static void main(String[] args) throws ClientProtocolException, URISyntaxException, IOException {
			doStuff();
	}
	
	static void doStuff() throws URISyntaxException, ClientProtocolException, IOException{
		System.out.println("starting");
		 
		//String text = FileUtils.readFileToString(new File("bio.txt"), "UTF-8");
		String text = "Hey, IBM Bluemix is working!";
		System.out.println("text:"+text);
		String sid = "mt-enus-eses";
		
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("body",text));
		qparams.add(new BasicNameValuePair("func","annotateText" ));
		qparams.add(new BasicNameValuePair("rt","text" ));
		JSONObject postData = new JSONObject();
    	postData.put("body",text);
		try{
			Executor executor = Executor.newInstance().auth(username, password);
			URI serviceURI = new URI(baseURL).normalize();	
		    String auth = username + ":" + password;
			String response = executor.execute(Request.Post(serviceURI)
				.addHeader("Authorization", "Basic "+ Base64.encodeBase64String(auth.getBytes()))
			    //.bodyString(URLEncodedUtils.format(qparams, "utf-8"), 
			    //		ContentType.APPLICATION_FORM_URLENCODED)
				.bodyString(postData.toString(), ContentType.APPLICATION_JSON)
				    ).returnContent().asString();
				//).returnContent().asString();
	
			//JSONObject lang = JSONObject.parse(response);
			System.out.println(response);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
