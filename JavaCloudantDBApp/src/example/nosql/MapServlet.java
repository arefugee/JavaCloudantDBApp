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
public class MapServlet {
    
	@RequestMapping(value="/realtime", method = RequestMethod.POST)
	public String getRealtime(@RequestParam(value = "id", required = false)  Long id, 
			@RequestParam(value = "url", required = false)  String url, 
			@RequestParam(value = "cmd", required = false) String cmd ) throws Exception {
		if (url!=null && url.indexOf("demo=true") != -1){
			//for demo start
			String result = "";
			if (System.currentTimeMillis()%2==0){
				
				result = "{\"total_rows\":36,\"bookmark\":\"g1AAAAIzeJzLYWBg4MhgTmFQSklKzi9KdUhJMtRLT81PSc3NNzAw1EvOyS9NScwr0ctLLckBqmVKZEiS____f1YGk5v9BwYwSGRBNcAYrwFJCkAyyR7dDCZUM4zwm-EAMiOegDvweyQpAWRGPUVm5LGA9DQAKaAx81HMYSbdnAUQc_bjcw_-cIGYcwBizn1K3fMAYg5q-DBnAQA3tanV\",\"rows\":[{\"id\":\"2eaa3e6e396869b589242ce4e6f2b613\",\"order\":[1.0,0],\"fields\":{\"long\":116.25424159999998,\"lat\":39.9178331,\"time\":1413912034.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2b613\",\"_rev\":\"1-586c701f9357948fce83ea00c027ae7c\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912034.0,\"creation_date\":\"Wed Oct 22 01:20:34 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f2d301\",\"order\":[1.0,0],\"fields\":{\"long\":116.27424159999998,\"lat\":39.9028331,\"time\":1413912058.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2d301\",\"_rev\":\"1-0c1a526b09fb81bb58fb797785343b71\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912058.0,\"creation_date\":\"Wed Oct 22 01:20:58 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f2cc08\",\"order\":[1.0,1],\"fields\":{\"long\":116.26124159999998,\"lat\":39.9118331,\"time\":1413912047.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2cc08\",\"_rev\":\"1-67bc88038f495a06e6e6bdf7d22953d3\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912047.0,\"creation_date\":\"Wed Oct 22 01:20:47 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f37761\",\"order\":[1.0,1],\"fields\":{\"long\":116.26324159999998,\"lat\":39.9018331,\"time\":1413912216.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f37761\",\"_rev\":\"1-e8951f21cc36f6f1681c42fb20653315\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912216.0,\"creation_date\":\"Wed Oct 22 01:23:36 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f2fec2\",\"order\":[1.0,1],\"fields\":{\"long\":116.26824159999998,\"lat\":39.9108331,\"time\":1413912093.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2fec2\",\"_rev\":\"1-4d1aa16f5fa7261dd7270d6e9e335ba6\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912093.0,\"creation_date\":\"Wed Oct 22 01:21:33 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f2defc\",\"order\":[1.0,1],\"fields\":{\"long\":116.26024159999998,\"lat\":39.9168331,\"time\":1413912066.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2defc\",\"_rev\":\"1-339509e9260b17a8494837ede2df4c4e\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912066.0,\"creation_date\":\"Wed Oct 22 01:21:06 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f2bf0e\",\"order\":[1.0,1],\"fields\":{\"long\":116.26924159999998,\"lat\":39.9048331,\"time\":1413912039.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2bf0e\",\"_rev\":\"1-0657d82186c8ec1bcb38d393f91abda5\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912039.0,\"creation_date\":\"Wed Oct 22 01:20:39 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f3337f\",\"order\":[1.0,2],\"fields\":{\"long\":116.27124159999998,\"lat\":39.9138331,\"time\":1413912128.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f3337f\",\"_rev\":\"1-eff1ad50c8b5e8e72ba5a94b97331b73\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912128.0,\"creation_date\":\"Wed Oct 22 01:22:08 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f3337e\",\"order\":[1.0,2],\"fields\":{\"long\":116.27624159999998,\"lat\":39.9198331,\"time\":1413912128.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f3337e\",\"_rev\":\"1-eff1ad50c8b5e8e72ba5a94b97331b73\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912128.0,\"creation_date\":\"Wed Oct 22 01:22:08 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f3337d\",\"order\":[1.0,2],\"fields\":{\"long\":116.26924159999998,\"lat\":39.9198331,\"time\":1413912128.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f3337d\",\"_rev\":\"1-eff1ad50c8b5e8e72ba5a94b97331b73\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912128.0,\"creation_date\":\"Wed Oct 22 01:22:08 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f2ee78\",\"order\":[1.0,2],\"fields\":{\"long\":116.28024159999998,\"lat\":39.9118331,\"time\":1413912070.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2ee78\",\"_rev\":\"1-5ed2707d5251449703cc6569b3874d31\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912070.0,\"creation_date\":\"Wed Oct 22 01:21:10 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}}]}";
					}else{
				result = "{\"total_rows\":36,\"bookmark\":\"g1AAAAIzeJzLYWBg4MhgTmFQSklKzi9KdUhJMtRLT81PSc3NNzAw1EvOyS9NScwr0ctLLckBqmVKZEiS____f1YGk5v9BwYwSGRBNcAYrwFJCkAyyR7dDCZUM4zwm-EAMiOegDvweyQpAWRGPUVm5LGA9DQAKaAx81HMYSbdnAUQc_bjcw_-cIGYcwBizn1K3fMAYg5q-DBnAQA3tanV\",\"rows\":[{\"id\":\"2eaa3e6e396869b589242ce4e6f2b613\",\"order\":[1.0,0],\"fields\":{\"long\":116.25424159999998,\"lat\":39.9178331,\"time\":1413912034.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2b613\",\"_rev\":\"1-586c701f9357948fce83ea00c027ae7c\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912034.0,\"creation_date\":\"Wed Oct 22 01:20:34 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f2d301\",\"order\":[1.0,0],\"fields\":{\"long\":116.27424159999998,\"lat\":39.9028331,\"time\":1413912058.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2d301\",\"_rev\":\"1-0c1a526b09fb81bb58fb797785343b71\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912058.0,\"creation_date\":\"Wed Oct 22 01:20:58 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f2cc08\",\"order\":[1.0,1],\"fields\":{\"long\":116.26124159999998,\"lat\":39.9118331,\"time\":1413912047.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2cc08\",\"_rev\":\"1-67bc88038f495a06e6e6bdf7d22953d3\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912047.0,\"creation_date\":\"Wed Oct 22 01:20:47 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f37761\",\"order\":[1.0,1],\"fields\":{\"long\":116.26324159999998,\"lat\":39.9018331,\"time\":1413912216.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f37761\",\"_rev\":\"1-e8951f21cc36f6f1681c42fb20653315\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912216.0,\"creation_date\":\"Wed Oct 22 01:23:36 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f2fec2\",\"order\":[1.0,1],\"fields\":{\"long\":116.26824159999998,\"lat\":39.9108331,\"time\":1413912093.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2fec2\",\"_rev\":\"1-4d1aa16f5fa7261dd7270d6e9e335ba6\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912093.0,\"creation_date\":\"Wed Oct 22 01:21:33 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f2defc\",\"order\":[1.0,1],\"fields\":{\"long\":116.26024159999998,\"lat\":39.9168331,\"time\":1413912066.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2defc\",\"_rev\":\"1-339509e9260b17a8494837ede2df4c4e\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912066.0,\"creation_date\":\"Wed Oct 22 01:21:06 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f2bf0e\",\"order\":[1.0,1],\"fields\":{\"long\":116.26924159999998,\"lat\":39.9048331,\"time\":1413912039.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2bf0e\",\"_rev\":\"1-0657d82186c8ec1bcb38d393f91abda5\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912039.0,\"creation_date\":\"Wed Oct 22 01:20:39 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f3337f\",\"order\":[1.0,2],\"fields\":{\"long\":116.27124159999998,\"lat\":39.9138331,\"time\":1413912128.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f3337f\",\"_rev\":\"1-eff1ad50c8b5e8e72ba5a94b97331b73\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912128.0,\"creation_date\":\"Wed Oct 22 01:22:08 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f3337e\",\"order\":[1.0,2],\"fields\":{\"long\":116.27624159999998,\"lat\":39.9198331,\"time\":1413912128.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f3337e\",\"_rev\":\"1-eff1ad50c8b5e8e72ba5a94b97331b73\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912128.0,\"creation_date\":\"Wed Oct 22 01:22:08 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f3337d\",\"order\":[1.0,2],\"fields\":{\"long\":116.26924159999998,\"lat\":39.9198331,\"time\":1413912128.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f3337d\",\"_rev\":\"1-eff1ad50c8b5e8e72ba5a94b97331b73\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912128.0,\"creation_date\":\"Wed Oct 22 01:22:08 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}},{\"id\":\"2eaa3e6e396869b589242ce4e6f2ee79\",\"order\":[1.0,2],\"fields\":{\"long\":116.27724159999998,\"lat\":39.9138331,\"time\":1413912070.0},\"doc\":{\"_id\":\"2eaa3e6e396869b589242ce4e6f2ee79\",\"_rev\":\"1-5ed2707d5251449703cc6569b3874d31\",\"longitude\":116.29184309821846,\"location\":{\"type\":\"Point\",\"coordinates\":[116.29184309821846,39.9078331]},\"time\":1413912070.0,\"creation_date\":\"Wed Oct 22 01:21:10 UTC 2014\",\"latitude\":39.9078331,\"place\":\"N1\"}}]}";
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
		//for demo start
		String result = "{\"rows\":[{\"lng\":116.26914159999998,\"lat\":39.9128331,\"count\":100}, {\"lng\":116.26414159999998,\"lat\":39.9208331,\"count\":60} ]}";
		return result;	
		//for demo end
//		return get(url);
	}
	
	
	 public String get(String url) {  
		 	String result = "";
	        CloseableHttpClient httpclient = HttpClients.createDefault();  
	        try {  
	            // 创建httpget.    
//	            HttpGet httpget = new HttpGet("http://tedgenstheremeseareencee:IgqAjXieWYef1yXkSIpRjsAq@zozowell.cloudant.com/incidents/_design/seindex/_search/loctime?&q=long:" + 
//	            URLEncoder.encode("[116.2906705453363 TO 116.2924293746595]","utf-8") + "&lat:"+ URLEncoder.encode("[40.05284646742972 TO 40.0537483433316]","utf-8") + 
//	            "&include_docs=true");  
	        	HttpGet httpget = new HttpGet(url);
	            System.out.println("executing request " + httpget.getURI());  
	            // 执行get请求.    
	            CloseableHttpResponse response = httpclient.execute(httpget);  
	            try {  
	                // 获取响应实体    
	                HttpEntity entity = response.getEntity();  
	                System.out.println("--------------------------------------");  
	                // 打印响应状态    
	                System.out.println(response.getStatusLine());  
	                if (entity != null) {  
	                    // 打印响应内容长度    
	                    System.out.println("Response content length: " + entity.getContentLength());  
	                    // 打印响应内容    
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
	            // 关闭连接,释放资源    
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
			// TODO Auto-generated catch block
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
