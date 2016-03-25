package Api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.R.integer;
import android.util.Log;

public class HttpClientApi {
	public static JSONObject getJsonObject(String url,HashMap<String,String> map){	  
		//对参数编码  
		Set<String> keysSet = map.keySet();
		Iterator<String> iterator = keysSet.iterator();
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		while(iterator.hasNext()){
			String nowkey = iterator.next(); 
			params.add(new BasicNameValuePair(nowkey, map.get(nowkey)));  
		}
		
		String param = URLEncodedUtils.format(params, "UTF-8");  
		  
		//115.28.188.195            
		String baseUrl = "http://115.28.188.195:8080/ChemTrackServer/" + url;  
		  
		//将URL与参数拼接  
		HttpGet getMethod = new HttpGet(baseUrl + "?" + param);  
		              
		HttpClient httpClient = new DefaultHttpClient();  
		  
		try {  
		    HttpResponse response = httpClient.execute(getMethod); //发起GET请求  
		    StringBuilder builder = new StringBuilder(); 
            BufferedReader bufferedReader = new BufferedReader( 
                    new InputStreamReader(response.getEntity().getContent())); 
            
            for (String s = bufferedReader.readLine(); s != null; s = bufferedReader 
                    .readLine()) { 
                builder.append(s); 
            } 
            Log.i("cat", ">>>>>>" + builder.toString());
            JSONObject jsonObject = new JSONObject(builder.toString());
            return jsonObject;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
