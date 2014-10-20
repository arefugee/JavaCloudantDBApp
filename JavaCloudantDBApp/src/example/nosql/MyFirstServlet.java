package example.nosql;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.ektorp.AttachmentInputStream;
import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

@RestController
@RequestMapping("/shops")
@Scope(value = "singleton")
public class MyFirstServlet extends ResourceServlet{
	
	@RequestMapping(value="/date")
	public String getDate(){
		return (new Date()).toString();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(@RequestParam(value = "id", required = false)  Long id, @RequestParam(value = "cmd", required = false) String cmd ) throws Exception {
		CouchDbConnector dbConnector = createDbConnector(); 
		
		
		JSONObject resultObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();		
			
		if( id == null ){			
			try
			{
				//get all the document IDs present in database
				List<String> docIds = dbConnector.getAllDocIds();
				
				if(docIds.size()==0)
				{
					docIds = initializeSampleData(dbConnector);
				}
				
				for(String docId : docIds)
				{
					//get the document object by providing doc id
					HashMap<String, Object> obj = dbConnector.get(HashMap.class , docId);
					JSONObject jsonObject = new JSONObject();					
					java.util.LinkedHashMap attachments = (java.util.LinkedHashMap)obj.get("_attachments");
					
					if(attachments!=null && attachments.size()>0)
					{	
						JSONArray attachmentList = getAttachmentList(attachments, obj.get("_id")+"");
						jsonObject.put("id", obj.get("_id"));
						jsonObject.put("name", obj.get("name"));
						jsonObject.put("value", obj.get("value"));
						jsonObject.put("attachements", attachmentList);
						
					}
					else
					{
						jsonObject.put("id", obj.get("_id"));
						jsonObject.put("name", obj.get("name"));
						jsonObject.put("value", obj.get("value"));
					}
					
					//System.out.println("====> "+jsonObject);
					jsonArray.add(jsonObject);
				}
			
			}
			catch(DocumentNotFoundException dnfe)
			{
				System.out.println("Exception thrown : "+ dnfe.getMessage());
			}
			
			resultObject.put("id", "all");
			resultObject.put("body", jsonArray);			
			//close the connection manager
			closeDBConnector();
			return resultObject.toString();			
		}
		
		
		//check if document exists
		HashMap<String, Object> obj = dbConnector.find(HashMap.class , id+"");
				
		//close the connection manager
		closeDBConnector();
		
		if(obj!=null)
		{
			JSONObject jsonObject = new JSONObject();			
			java.util.LinkedHashMap attachments = (java.util.LinkedHashMap)obj.get("_attachments");
			if(attachments!=null && attachments.size()>0)
			{
				JSONArray attachmentList = getAttachmentList(attachments, obj.get("_id")+"");
				jsonObject.put("attachements", attachmentList);
			}
			jsonObject.put("id", obj.get("_id"));
			jsonObject.put("name", obj.get("name"));
			jsonObject.put("value", obj.get("value"));
			return jsonObject.toString();
		}
		else
			return Status.NOT_FOUND+"";
				
	}
	
	/*
	 * Create a document and Initialize with sample data/attachments
	 */
	private List<String> initializeSampleData(CouchDbConnector dbConnector) throws IOException
	{
				
		long id = System.currentTimeMillis();
		String name = "Restaurants List";
		String value = "List of the restaurant";
		
		//create a new document
		System.out.println("Creating new document with id : "+id);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", name);			
		data.put("_id", id+"");
		data.put("value", value);
		data.put("creation_date", new Date().toString());
		dbConnector.create(data);	
		
		//attach the attachment object
		HashMap<String, Object> obj = dbConnector.find(HashMap.class , id+"");		
		
		//attachment#1
		File file = new File("Sample.txt");
		file.createNewFile();		
		PrintWriter writer = new PrintWriter(file);
		writer.write("This is a sample file...");
		writer.flush();
		writer.close();
		FileInputStream fileInputStream = new FileInputStream(file);
		AttachmentInputStream attachmentInputStream = new AttachmentInputStream(file.getName(), fileInputStream, "text/plain");
		dbConnector.createAttachment(id+"",  (String)obj.get("_rev"), attachmentInputStream);		
		
		attachmentInputStream.close();
		fileInputStream.close();
		
		List<String> docIds = new ArrayList<String>();
		docIds.add(id+"");
		return docIds;
			
	}
	
	private JSONArray getAttachmentList(java.util.LinkedHashMap attachmentList, String docID) throws Exception
	{
		
		JSONArray attachmentArray = new JSONArray();
		String URLTemplate = "http://"+user+":"+password+"@"+databaseHost+"/"+databaseName+"/";
		
		for(Object key : attachmentList.keySet())
		{
			java.util.LinkedHashMap attach = (java.util.LinkedHashMap)attachmentList.get(key);	
			JSONObject attachedObject = new JSONObject();
			//set the content type of the attachment
			attachedObject.put("content_type", attach.get("content_type").toString());
			//append the document id and attachment key to the URL
			attachedObject.put("url", URLTemplate+docID+"/"+key);
			//set the key of the attachment
			attachedObject.put("key", key);
			
			//add the attachment object to the array
			attachmentArray.add(attachedObject);
		}
		
		return attachmentArray;
		
	}
	
	protected CouchDbConnector createDbConnector() throws Exception{
		databaseName = "shop_nosql_db";
		return super.createDbConnector();
	}

}
