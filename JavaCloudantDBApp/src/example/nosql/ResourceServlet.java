package example.nosql;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.web.multipart.MultipartFile;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

@RestController
@RequestMapping("/favorites")
@Scope(value = "singleton")
/**
 * CRUD service of favorites application. It uses REST style.
 *
 */
public class ResourceServlet extends AbstractResourceServlet{
	
	
	@RequestMapping(value="/attach", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("file") MultipartFile fileParts, @RequestParam("id")  Long id, 
			@RequestParam("ownerName1")  String ownerName1, 
			@RequestParam("ownerName2")  String ownerName2,
			@RequestParam("carNumber1")  String carNumber1, 
			@RequestParam("carNumber2")  String carNumber2,
			@RequestParam("dateTime")  String dateTime, 
			@RequestParam("caseStatus")  String caseStatus, 
			@RequestParam("latitude")  String latitude,
			@RequestParam("longitude")  String longitude, 
			@RequestParam("acc_description")  String acc_description) throws IOException, Exception {

		byte[] bytes = null;
		JSONObject resultObject = new JSONObject();
		String fileName = null;
		String contentType = null;
		
		 if (!fileParts.isEmpty()) {
	            try {
	                bytes = fileParts.getBytes();
	                contentType = fileParts.getContentType();
	                fileName = fileParts.getOriginalFilename();	              
	            }
	            catch (Exception e) {
	                return "File upload failed " + id + " => " + e.getMessage();
	            }
		 }
		
		
		InputStream fileInputStream = new ByteArrayInputStream(bytes);

		AttachmentInputStream attachmentInputStream = new AttachmentInputStream(fileName, fileInputStream, contentType);

		CouchDbConnector dbConnector = createDbConnector();

		//check if document exist
		HashMap<String, Object> obj = (id==-1?null:dbConnector.find(HashMap.class , id+""));

		if(obj==null)
		{ // if new document
			
			id = System.currentTimeMillis();
			
			//create a new document
			System.out.println("Creating new document with id : "+id);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("ownerName1", ownerName1);	
			data.put("ownerName2", ownerName2);	
			data.put("carNumber1", carNumber1);	
			data.put("carNumber2", carNumber2);	
			data.put("dateTime", dateTime);	
			data.put("caseStatus", caseStatus);	
			data.put("latitude", Float.parseFloat(latitude));	
			data.put("longitude", Float.parseFloat(longitude));
			
			data.put("acc_description", acc_description);
			data.put("time", id);
			data.put("_id", id+"");
			data.put("creation_date", new Date().toString());
			dbConnector.create(data);	
			
			//attach the attachment object
			obj = dbConnector.find(HashMap.class , id+"");
			dbConnector.createAttachment(id+"",  (String)obj.get("_rev"), attachmentInputStream);
		}
		else
		{ // if existing document
			//attach the attachment object
			dbConnector.createAttachment(id+"",  (String)obj.get("_rev"), attachmentInputStream);
			
			//update other fields in the document
			obj = dbConnector.find(HashMap.class , id+"");
			obj.put("ownerName1", ownerName1);	
			obj.put("ownerName2", ownerName2);	
			obj.put("carNumber1", carNumber1);	
			obj.put("carNumber2", carNumber2);	
			obj.put("dateTime", dateTime);
			obj.put("caseStatus", caseStatus);
			obj.put("latitude", Float.parseFloat(latitude));	
			obj.put("longitude", Float.parseFloat(longitude));	
			obj.put("time", id);
			obj.put("acc_description", acc_description);	
			dbConnector.update(obj);
			
		}	
		
		attachmentInputStream.close();
		fileInputStream.close();
		
						
		System.out.println("Upload completed....");
		
		//get attachments
		obj = dbConnector.find(HashMap.class , id+"");		
		java.util.LinkedHashMap attachments = (java.util.LinkedHashMap)obj.get("_attachments");
		
		if(attachments!=null && attachments.size()>0)
		{
			JSONArray attachmentList = getAttachmentList(attachments, id+"");
			resultObject.put("attachements", attachmentList);
		}
		resultObject.put("id", id);
		resultObject.put("time", id);
		resultObject.put("ownerName1", ownerName1);	
		resultObject.put("ownerName2", ownerName2);	
		resultObject.put("carNumber1", carNumber1);	
		resultObject.put("carNumber2", carNumber2);	
		resultObject.put("dateTime", dateTime);	
		resultObject.put("caseStatus", caseStatus);
		resultObject.put("latitude", latitude);	
		resultObject.put("longitude", longitude);	
		resultObject.put("acc_description", acc_description);	
		
		closeDBConnector();
		return resultObject.toString();		
	}

			
    @RequestMapping(method = RequestMethod.POST)
	public String create(@RequestParam("ownerName1")  String ownerName1, 
			@RequestParam("ownerName2")  String ownerName2,
			@RequestParam("carNumber1")  String carNumber1, 
			@RequestParam("carNumber2")  String carNumber2,
			@RequestParam("dateTime")  String dateTime, 
			@RequestParam("caseStatus")  String caseStatus, 
			@RequestParam("latitude")  String latitude,
			@RequestParam("longitude")  String longitude, 
			@RequestParam("acc_description")  String acc_description) throws Exception{
		
		System.out.println("Create invoked...");
		CouchDbConnector dbConnector = createDbConnector();
		JSONObject resultObject = new JSONObject();
		Map<String, Object> data = new HashMap<String, Object>();
		long id = System.currentTimeMillis()/1000;
		data.put("_id", id+"");
		data.put("time", id);
		data.put("ownerName1", ownerName1);	
		data.put("ownerName2", ownerName2);	
		data.put("carNumber1", carNumber1);	
		data.put("carNumber2", carNumber2);	
		data.put("dateTime", dateTime);	
		data.put("caseStatus", caseStatus);	
		data.put("latitude", Float.parseFloat(latitude));	
		data.put("longitude", Float.parseFloat(longitude));	
		data.put("acc_description", acc_description);
		data.put("creation_date", new Date().toString());
		dbConnector.create(data);	
		
		System.out.println("Create Successful...");
		resultObject.put("id", id);
		resultObject.put("ownerName1", ownerName1);	
		resultObject.put("ownerName2", ownerName2);	
		resultObject.put("carNumber1", carNumber1);	
		resultObject.put("carNumber2", carNumber2);	
		resultObject.put("dateTime", dateTime);	
		resultObject.put("caseStatus", caseStatus);	
		resultObject.put("latitude", latitude);	
		resultObject.put("longitude", longitude);
		resultObject.put("time", id);
		resultObject.put("acc_description", acc_description);
		closeDBConnector();
		return resultObject.toString();	
	}
	
    @RequestMapping(method = RequestMethod.DELETE)
	public String delete(@RequestParam("id")  Long id) throws Exception{
		boolean recordFound = true;
		CouchDbConnector dbConnector = createDbConnector(); 
		//check if document exist
		HashMap<String, Object> obj = dbConnector.find(HashMap.class , id+"");
		
		if(obj==null)
			recordFound = false;
		else
		dbConnector.delete(obj);
		System.out.println("Delete Successful...");
		//close the connection manager
		closeDBConnector();
		
		if(recordFound){			
			return Status.OK+"";
		} else
			return Status.NOT_FOUND+"";
	}
	
    @RequestMapping(method = RequestMethod.PUT)
	public String update(@RequestParam("id")  long id, 
			@RequestParam("ownerName1")  String ownerName1, 
			@RequestParam("ownerName2")  String ownerName2,
			@RequestParam("carNumber1")  String carNumber1, 
			@RequestParam("carNumber2")  String carNumber2,
			@RequestParam("dateTime")  String dateTime, 
			@RequestParam("caseStatus")  String caseStatus, 
			@RequestParam("latitude")  String latitude,
			@RequestParam("longitude")  String longitude, 
			@RequestParam("acc_description")  String acc_description) throws Exception{
		boolean recordFound = true;
		CouchDbConnector dbConnector = createDbConnector(); 
		//check if document exist
		HashMap<String, Object> obj = dbConnector.find(HashMap.class , id+"");
		
		if(obj==null)
			recordFound = false;
		else
		{
			obj.put("ownerName1", ownerName1);	
			obj.put("ownerName2", ownerName2);	
			obj.put("carNumber1", carNumber1);	
			obj.put("carNumber2", carNumber2);	
			obj.put("dateTime", dateTime);	
			obj.put("caseStatus", caseStatus);	
			obj.put("latitude", Float.parseFloat(latitude));	
			obj.put("longitude", Float.parseFloat(longitude));	
			obj.put("acc_description", acc_description);
		}
		
		dbConnector.update(obj);
		System.out.println("Update Successful...");
		//close the connection manager
		closeDBConnector();
				
			
		if(recordFound){			
			return Status.OK+"";
		} else
			return Status.NOT_FOUND+"";
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
				docIds = sortDocIds(docIds);
				if(docIds.size()==0)
				{
//					docIds = initializeSampleData(dbConnector);
				}
				
//				for(String docId : docIds)
				for (int i=0; i<(docIds.size()<10 ?docIds.size():10); i++)
				{	String docId = docIds.get(i);
					//get the document object by providing doc id
					HashMap<String, Object> obj = dbConnector.get(HashMap.class , docId);
					JSONObject jsonObject = new JSONObject();					
					java.util.LinkedHashMap attachments = (java.util.LinkedHashMap)obj.get("_attachments");
					
					if(attachments!=null && attachments.size()>0)
					{	
						JSONArray attachmentList = getAttachmentList(attachments, obj.get("_id")+"");
						jsonObject.put("id", obj.get("_id"));
						jsonObject.put("time", obj.get("time"));
						jsonObject.put("ownerName1", obj.get("ownerName1"));
						jsonObject.put("ownerName2", obj.get("ownerName2"));
						jsonObject.put("carNumber1", obj.get("carNumber1"));
						jsonObject.put("carNumber2", obj.get("carNumber2"));
						jsonObject.put("dateTime", obj.get("dateTime"));
						jsonObject.put("caseStatus", obj.get("caseStatus"));
						jsonObject.put("latitude", obj.get("latitude"));
						jsonObject.put("longitude", obj.get("longitude"));
						jsonObject.put("acc_description", obj.get("acc_description"));
						jsonObject.put("attachements", attachmentList);
						
					}
					else
					{
						jsonObject.put("id", obj.get("_id"));
						jsonObject.put("time", obj.get("time"));
						jsonObject.put("ownerName1", obj.get("ownerName1"));
						jsonObject.put("ownerName2", obj.get("ownerName2"));
						jsonObject.put("carNumber1", obj.get("carNumber1"));
						jsonObject.put("carNumber2", obj.get("carNumber2"));
						jsonObject.put("dateTime", obj.get("dateTime"));
						jsonObject.put("caseStatus", obj.get("caseStatus"));
						jsonObject.put("latitude", obj.get("latitude"));
						jsonObject.put("longitude", obj.get("longitude"));
						jsonObject.put("acc_description", obj.get("acc_description"));
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
			jsonObject.put("time", obj.get("time"));
			jsonObject.put("ownerName1", obj.get("ownerName1"));
			jsonObject.put("ownerName2", obj.get("ownerName2"));
			jsonObject.put("carNumber1", obj.get("carNumber1"));
			jsonObject.put("carNumber2", obj.get("carNumber2"));
			jsonObject.put("dateTime", obj.get("dateTime"));
			jsonObject.put("caseStatus", obj.get("caseStatus"));
			jsonObject.put("latitude", obj.get("latitude"));
			jsonObject.put("longitude", obj.get("longitude"));
			jsonObject.put("acc_description", obj.get("acc_description"));
			return jsonObject.toString();
		}
		else
			return Status.NOT_FOUND+"";
				
	}
	
	protected JSONArray getAttachmentList(java.util.LinkedHashMap attachmentList, String docID) throws Exception
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
	
	/*
	 * Create a document and Initialize with sample data/attachments
	 */
	protected List<String> initializeSampleData(CouchDbConnector dbConnector) throws IOException
	{
				
		long id = System.currentTimeMillis();
		String name = "Jim";
		String carNumber = "N39192";
		String carType = "BMW M5";
		String value = "List of sample files";
		
		//create a new document
		System.out.println("Creating new document with id : "+id);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", name);		
		data.put("carnumber", carNumber);
		data.put("cartype", carType);
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
	
	private List sortDocIds(List<String> docIDs){
		List resList = new ArrayList<String>();
		for (int i=0; i<docIDs.size(); i++){
			String docID = docIDs.get(i);
			try{
				Long id = Long.parseLong(docID);
				resList.add(docID);
			}catch(Exception e){
				
			}
		}
		
		Collections.sort(resList, new Comparator<String>() {
            public int compare(String arg0, String arg1) {
            	long i1, i2;
            	i1 = Long.parseLong(arg0);
            	i2 = Long.parseLong(arg1);
                return i1<i2?1:-1;
            }
          });
		return resList;
	}
	
}
