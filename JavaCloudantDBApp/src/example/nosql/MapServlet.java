package example.nosql;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope(value = "singleton")
public class MapServlet extends AbstractResourceServlet{
    
	@RequestMapping(value="/realtime", method = RequestMethod.POST)
	public String getRealtime(@RequestParam(value = "id", required = false)  Long id, 
			@RequestParam(value = "url", required = false)  String url, 
			@RequestParam(value = "cmd", required = false) String cmd ) throws Exception {
		if (url!=null && url.indexOf("demo=true") != -1){
			//for demo start
			String result = "";
			if (System.currentTimeMillis()%2==0){
				result = "{\"total_rows\":26,\"bookmark\":\"g1AAAADWeJzLYWBgYM5gTmGQS0lKzi9KdUhJMtLLTMo1MDDRS87JL01JzCvRy0styQGqY0pSAJJJ9v___88C890crwrf_-fAwJAonEW0GQ4gM-JRzHjI1AA0Q5xoM_JYgCTDAiAFNGY_kjkeB4Dm8GdlAQD2O0Fd\",\"rows\":[{\"id\":\"1414502679\",\"order\":[1414502679.0,24],\"fields\":{\"long\":116.26428,\"lat\":39.907845,\"time\":1414502679.0},\"doc\":{\"_id\":\"1414502679\",\"_rev\":\"1-07cc1ebb8e4af553eabdee5a7ff06f52\",\"caseStatus\":\"submit\",\"ownerName1\":\"ER333\",\"carNumber1\":\"\",\"ownerName2\":\"dd\",\"carNumber2\":\"\",\"dateTime\":\"\",\"latitude\":39.9178331,\"time\":1414502679,\"creation_date\":\"Tue Oct 28 21:24:39 CST 2014\",\"acc_description\":\"\",\"longitude\":116.25424159}},{\"id\":\"1414502398\",\"order\":[1414502398.0,16],\"fields\":{\"long\":116.26426,\"lat\":39.90782,\"time\":1414502398.0},\"doc\":{\"_id\":\"1414502398\",\"_rev\":\"1-8ab01d3e5144436cb7a9d701abec2a6c\",\"caseStatus\":\"submit\",\"ownerName1\":\"Er1\",\"carNumber1\":\"\",\"ownerName2\":\"Re2\",\"carNumber2\":\"\",\"dateTime\":\"10/01/2014 21:19\",\"latitude\":39.9028331,\"time\":1414502398,\"creation_date\":\"Tue Oct 28 21:19:58 CST 2014\",\"acc_description\":\"\",\"longitude\":116.27424159999998}},{\"id\":\"1414497571\",\"order\":[1414497571.0,15],\"fields\":{\"long\":116.28621,\"lat\":40.044685,\"time\":1414497571.0},\"doc\":{\"_id\":\"1414497571\",\"_rev\":\"1-6a021be80f61ff0957206c254019acb8\",\"creation_date\":\"Tue Oct 28 11:59:31 UTC 2014\",\"time\":1414497571,\"dateTime\":\"\",\"caseStatus\":\"submit\",\"ownerName2\":\"Eric\",\"acc_description\":\"\",\"longitude\":116.26124159999998,\"latitude\":39.9118331,\"carNumber1\":\"äº¬ABBCC\",\"carNumber2\":\"äº¬N66GGA\",\"ownerName1\":\"Stony\"}},{\"id\":\"1414497290\",\"order\":[1414497290.0,23],\"fields\":{\"long\":116.28933,\"lat\":40.044697,\"time\":1414497290.0},\"doc\":{\"_id\":\"1414497290\",\"_rev\":\"1-04e63d225c9385097c0c1caf3e996b7b\",\"creation_date\":\"Tue Oct 28 11:54:50 UTC 2014\",\"time\":1414497290,\"dateTime\":\"\",\"caseStatus\":\"processing\",\"ownerName2\":\"Stony\",\"acc_description\":\"\",\"longitude\":116.26824159999998,\"latitude\":39.9018331,\"carNumber1\":\"\",\"carNumber2\":\"\",\"ownerName1\":\"Eric\"}},{\"id\":\"1414496249\",\"order\":[1414496249.0,19],\"fields\":{\"long\":116.28331,\"lat\":40.044807,\"time\":1414496249.0},\"doc\":{\"_id\":\"1414496249\",\"_rev\":\"1-d8eb162aafa91e99b4ab8279d140c8e8\",\"caseStatus\":\"submit\",\"ownerName1\":\"adsf\",\"carNumber1\":\"a23\",\"ownerName2\":\"asdf\",\"carNumber2\":\"32\",\"dateTime\":\"10/02/2014 19:37\",\"latitude\":39.9138331,\"time\":1414496249,\"creation_date\":\"Tue Oct 28 19:37:29 CST 2014\",\"acc_description\":\"adsf\",\"longitude\":116.25724159999998}}]}";
			}else{
				result = "{\"total_rows\":26,\"bookmark\":\"g1AAAADWeJzLYWBgYM5gTmGQS0lKzi9KdUhJMtLLTMo1MDDRS87JL01JzCvRy0styQGqY0pSAJJJ9v___88C890crwrf_-fAwJAonEW0GQ4gM-JRzHjI1AA0Q5xoM_JYgCTDAiAFNGY_kjkeB4Dm8GdlAQD2O0Fd\",\"rows\":[{\"id\":\"1414502679\",\"order\":[1414502679.0,24],\"fields\":{\"long\":116.26428,\"lat\":39.907845,\"time\":1414502679.0},\"doc\":{\"_id\":\"1414502679\",\"_rev\":\"1-07cc1ebb8e4af553eabdee5a7ff06f52\",\"caseStatus\":\"submit\",\"ownerName1\":\"ER333\",\"carNumber1\":\"\",\"ownerName2\":\"dd\",\"carNumber2\":\"\",\"dateTime\":\"\",\"latitude\":39.9178331,\"time\":1414502679,\"creation_date\":\"Tue Oct 28 21:24:39 CST 2014\",\"acc_description\":\"\",\"longitude\":116.25424159}},{\"id\":\"1414502398\",\"order\":[1414502398.0,16],\"fields\":{\"long\":116.26426,\"lat\":39.90782,\"time\":1414502398.0},\"doc\":{\"_id\":\"1414502398\",\"_rev\":\"1-8ab01d3e5144436cb7a9d701abec2a6c\",\"caseStatus\":\"submit\",\"ownerName1\":\"Er1\",\"carNumber1\":\"\",\"ownerName2\":\"Re2\",\"carNumber2\":\"\",\"dateTime\":\"10/01/2014 21:19\",\"latitude\":39.9028331,\"time\":1414502398,\"creation_date\":\"Tue Oct 28 21:19:58 CST 2014\",\"acc_description\":\"\",\"longitude\":116.27424159999998}},{\"id\":\"1414497571\",\"order\":[1414497571.0,15],\"fields\":{\"long\":116.28621,\"lat\":40.044685,\"time\":1414497571.0},\"doc\":{\"_id\":\"1414497571\",\"_rev\":\"1-6a021be80f61ff0957206c254019acb8\",\"creation_date\":\"Tue Oct 28 11:59:31 UTC 2014\",\"time\":1414497571,\"dateTime\":\"\",\"caseStatus\":\"submit\",\"ownerName2\":\"Eric\",\"acc_description\":\"\",\"longitude\":116.26124159999998,\"latitude\":39.9118331,\"carNumber1\":\"äº¬ABBCC\",\"carNumber2\":\"äº¬N66GGA\",\"ownerName1\":\"Stony\"}},{\"id\":\"1414497290\",\"order\":[1414497290.0,23],\"fields\":{\"long\":116.28933,\"lat\":40.044697,\"time\":1414497290.0},\"doc\":{\"_id\":\"1414497290\",\"_rev\":\"1-04e63d225c9385097c0c1caf3e996b7b\",\"creation_date\":\"Tue Oct 28 11:54:50 UTC 2014\",\"time\":1414497290,\"dateTime\":\"\",\"caseStatus\":\"processing\",\"ownerName2\":\"Stony\",\"acc_description\":\"\",\"longitude\":116.26824159999998,\"latitude\":39.9018331,\"carNumber1\":\"\",\"carNumber2\":\"\",\"ownerName1\":\"Eric\"}},{\"id\":\"1414496250\",\"order\":[1414496249.0,19],\"fields\":{\"long\":116.28331,\"lat\":40.044807,\"time\":1414496249.0},\"doc\":{\"_id\":\"1414496250\",\"_rev\":\"1-d8eb162aafa91e99b4ab8279d140c8e8\",\"caseStatus\":\"submit\",\"ownerName1\":\"adsf\",\"carNumber1\":\"a23\",\"ownerName2\":\"asdf\",\"carNumber2\":\"32\",\"dateTime\":\"10/02/2014 19:37\",\"latitude\":39.9178331,\"time\":1414496249,\"creation_date\":\"Tue Oct 28 19:37:29 CST 2014\",\"acc_description\":\"adsf\",\"longitude\":116.26924159999998}}]}";
			}
			return result;	
			//for demo end
		}else{
			return get(url);
		}

		
	}
	
	@RequestMapping(value="/heatmap", method = RequestMethod.POST)
	public String getHeatMap(@RequestParam(value = "id", required = false)  Long id,
			@RequestParam(value = "url", required = false)  String url, 
			@RequestParam(value = "cmd", required = false) String cmd ) throws Exception {
		
		if (url!=null && url.indexOf("demo=true") != -1){
			//for demo start
			String result = "{\"total_rows\":20,\"offset\":17,\"rows\":[{\"id\":\"71bfd92b6b789b5c1b80bde88190c3cc\",\"key\":12,\"value\":{\"lng\":116.26724159999998,\"lat\":39.9108331,\"count\":100}},{\"id\":\"71bfd92b6b789b5c1b80bde88190a55c\",\"key\":13,\"value\":{\"lng\":116.26414159999998,\"lat\":39.9208331,\"count\":60}}]}";
			return result;	
			//for demo end
		}else{
			return get(url);
		}
	}
	
	
	 public String get(String url) {  
		 	String result = "";
	        CloseableHttpClient httpclient = HttpClients.createDefault();  
	        try {  
	        	HttpGet httpget = new HttpGet(url);
	            System.out.println("executing request " + httpget.getURI());  
	            CloseableHttpResponse response = httpclient.execute(httpget);  
	            try {  
	                HttpEntity entity = response.getEntity();  
	                System.out.println("--------------------------------------");  
	                System.out.println(response.getStatusLine());  
	                if (entity != null) {  
	                    System.out.println("Response content length: " + entity.getContentLength());  
	                    result = EntityUtils.toString(entity);
	                    System.out.println("Response content: " + result);
	                }  
	                System.out.println("------------------------------------");  
	            } finally {  
	                response.close();  
	            }  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (Exception e){
	        	e.printStackTrace();  
	        }
	        finally {  
	            try {  
	                httpclient.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }
	        return result;
		
	    }
	 
	 public static void main(String[] args){
		 MapServlet servlet = new MapServlet();
		 String url = null;
		try {
			url = "http://tedgenstheremeseareencee:IgqAjXieWYef1yXkSIpRjsAq@zozowell.cloudant.com/incidents/_design/seindex/_search/loctime?&q=long:" + 
			            URLEncoder.encode("[116.2906705453363 TO 116.2924293746595]","utf-8") + "&lat:"+ 
			            URLEncoder.encode("[40.05284646742972 TO 40.0537483433316]","utf-8") + 
			            "&include_docs=true";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 String result = servlet.get(url);
		 System.out.println(result);
	 }
	 
	
	//Mock data list:
	//longitude:116.26924159999998 latitude:39.9178331
	//longitude:116.26024159999998 latitude:39.9028331
	//longitude:116.25424159999998 latitude:39.9118331
	//longitude:116.27424159999998 latitude:39.9018331
	//longitude:116.26124159999998 latitude:39.9108331
	//longitude:116.26224159999998 latitude:39.9048331
	//longitude:116.26324159999998 latitude:39.9168331
	//longitude:116.26824159999998 latitude:39.9158331
	//longitude:116.27024159999998 latitude:39.9148331
	//longitude:116.26524159999998 latitude:39.9138331
	//longitude:116.26424159999998 latitude:39.9128331
	//longitude:116.27124159999998 latitude:39.9118331
	//longitude:116.28024159999998 latitude:39.9078331
	
	//home location: longitude:116.26424159999998 latitude:39.9078331
	 
	

}
