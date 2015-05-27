import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.json.simple.JSONObject;
//refer this link to see the docs of JSON lib used
//https://code.google.com/p/json-simple/wiki/EncodingExamples

//import com.ibm.json.java.JSONArray;
//import com.ibm.json.java.JSONObject;

public class QuestionAndAns {
	private static String baseURL = "https://gateway.watsonplatform.net/question-and-answer-beta/api";
	private static String username = "a6b896cd-7edc-4286-8e6a-4e707059d438";
	private static String password = "pNiSiFBOnsS0";
	
	public static void main(String[] args) throws ClientProtocolException, URISyntaxException, IOException {
		doStuff();
	}
	
	static void doStuff() throws URISyntaxException, ClientProtocolException, IOException{
		System.out.println("starting");
		
		String question = new String("What causes a heart attack?");
		String dataset = new String("healthcare");
		
		//create the { 'question' : {
		//	'questionText:'...',		
		//  'evidenceRequest': { 'items': 5} } json as requested by the service
		JSONObject questionJson = new JSONObject();
		questionJson.put("questionText",question);
		JSONObject evidenceRequest = new JSONObject();
		evidenceRequest.put("items",1);
		questionJson.put("evidenceRequest",evidenceRequest);
		JSONObject postData = new JSONObject();
    	postData.put("question",questionJson);
    	System.out.println(postData.toString());
    	try{
    		Executor executor = Executor.newInstance().auth(username, password);
    		URI serviceURI = new URI(baseURL+ "/v1/question/"+dataset).normalize();
    		String answersJson = executor.execute(Request.Post(serviceURI)
			    .addHeader("Accept", "application/json")
			    .addHeader("X-SyncTimeout", "30")
			    .bodyString(postData.toString(), ContentType.APPLICATION_JSON)
			    ).returnContent().asString();
    		System.out.println(answersJson);
    		
			//List<Map<String, String>> answers = formatAnswers(answersJson);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
	}
	
	/*private static List<Map<String,String>> formatAnswers(String resultJson) {
		List<Map<String,String>> ret = new ArrayList<Map<String,String>>();
		try {
			JSONArray pipelines = JSONArray.parse(resultJson);
			// the response has two pipelines, lets use the first one
			JSONObject answersJson = (JSONObject) pipelines.get(0);
			JSONArray answers = (JSONArray) ((JSONObject) answersJson.get("question")).get("evidencelist");

			for(int i = 0; i < answers.size();i++) {
				JSONObject answer = (JSONObject) answers.get(i);
				Map<String, String> map = new HashMap<String, String>();

				double p = Double.parseDouble((String)answer.get("value"));
				p = Math.floor(p * 100);
				map.put("confidence",  Double.toString(p) + "%");
				map.put("text", (String)answer.get("text"));

				ret.add(map);
			}
		} catch (IOException e) {
			e.printStackTrace();
       }
		return ret;
	}*/
}
