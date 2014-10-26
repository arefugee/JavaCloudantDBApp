package example.nosql;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response.Status;

import org.ektorp.AttachmentInputStream;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

@RestController
@RequestMapping("/location")
@Scope(value = "singleton")
/**
 * CRUD service of favorites application. It uses REST style.
 *
 */
public class LocationDetectorServlet {
	
	protected HttpClient httpClient = null;
	
	// set default db connection credentials 
	protected String databaseHost = "user.cloudant.com";
	protected int port = 443;
	protected String databaseName = "sample_nosql_db";
	protected String user = "user";
	protected String password = "password";
	
	
	
	@RequestMapping(value="/attach", method = RequestMethod.POST)
	public String UploadFile(@RequestParam("file") MultipartFile fileParts, @RequestParam("id")  Long id, 
			@RequestParam("name")  String name, 
			@RequestParam("carnumber")  String carNumber,
			@RequestParam("cartype")  String carType, 
			@RequestParam("value")  String value) throws IOException, Exception {

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
	                return "File upload failed " + name + " => " + e.getMessage();
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
			data.put("name", name);	
			data.put("carnumber", carNumber);	
			data.put("cartype", carType);	
			data.put("_id", id+"");
			data.put("value", value);
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
			obj.put("name", name);
			obj.put("carnumber", carNumber);	
			obj.put("cartype", carType);	
			obj.put("value", value);
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
		resultObject.put("name", name);	
		resultObject.put("carnumber", carNumber);	
		resultObject.put("cartype", carType);	
		resultObject.put("value", value);
		
		closeDBConnector();
		return resultObject.toString();		
	}

			
    @RequestMapping(method = RequestMethod.POST)
	public String create(@RequestParam("location")  String location, 
			@RequestParam("latitude")  String latitude,
			@RequestParam("longitude")  String longitude) throws Exception{
    	
		
		System.out.println("Create invoked...");
		CouchDbConnector dbConnector = createDbConnector();
		JSONObject resultObject = new JSONObject();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("location", location);
		long id = System.currentTimeMillis();
		data.put("_id", id+"");
		data.put("latitude", latitude);
		data.put("longitude", longitude);	
		data.put("creation_date", new Date().toString());
		dbConnector.create(data);	
		System.out.println("Create Successful...");
		resultObject.put("id", id);
		resultObject.put("location", location);
		resultObject.put("latitude", latitude);	
		resultObject.put("longitude", longitude);	
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
			@RequestParam("name")  String name, 
			@RequestParam("carnumber")  String carNumber,
			@RequestParam("cartype")  String carType,
			@RequestParam("value")  String value) throws Exception{
		boolean recordFound = true;
		CouchDbConnector dbConnector = createDbConnector(); 
		//check if document exist
		HashMap<String, Object> obj = dbConnector.find(HashMap.class , id+"");
		
		if(obj==null)
			recordFound = false;
		else
		{
			obj.put("name", name);
			obj.put("carnumber", carNumber);
			obj.put("cartype", carType);
			obj.put("value", value);
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
						jsonObject.put("carnumber", obj.get("carnumber"));
						jsonObject.put("cartype", obj.get("cartype"));
						jsonObject.put("value", obj.get("value"));
						jsonObject.put("attachements", attachmentList);
						
					}
					else
					{
						jsonObject.put("id", obj.get("_id"));
						jsonObject.put("name", obj.get("name"));
						jsonObject.put("carnumber", obj.get("carnumber"));
						jsonObject.put("cartype", obj.get("cartype"));
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
			jsonObject.put("carnumber", obj.get("carnumber"));
			jsonObject.put("cartype", obj.get("cartype"));
			jsonObject.put("value", obj.get("value"));
			return jsonObject.toString();
		}
		else
			return Status.NOT_FOUND+"";
				
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
	
	/*
	 * Create a document and Initialize with sample data/attachments
	 */
	private List<String> initializeSampleData(CouchDbConnector dbConnector) throws IOException
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
	
	protected CouchDbConnector createDbConnector() throws Exception{
		// VCAP_SERVICES is a system environment variable
		// Parse it to obtain the for NoSQL DB connection info
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
		String serviceName = null;

		if (VCAP_SERVICES != null) {
			// parse the VCAP JSON structure
			JSONObject obj =  JSONObject.parse(VCAP_SERVICES);
			String dbKey = null;
			Set<String> keys = obj.keySet();
			// Look for the VCAP key that holds the cloudant no sql db information
			for (String eachkey : keys) {				
				if (eachkey.contains("cloudantNoSQLDB")) {
					dbKey = eachkey;
					break;
				}
			}
			if (dbKey == null) {				
				System.out.println("Could not find cloudantNoSQLDB key in VCAP_SERVICES env variable ");
				return null;
			}

			JSONArray list = (JSONArray)obj.get(dbKey);
			obj = (JSONObject) list.get(0);		
			serviceName = (String)obj.get("name");
			System.out.println("Service Name - "+serviceName);
			
			obj = (JSONObject) obj.get("credentials");

			databaseHost = (String) obj.get("host");
			port = ((Long) obj.get("port")).intValue();
			user = (String) obj.get("username");
			password = (String) obj.get("password");
			//url is not being used
			//url = (String) obj.get("url");
		}
		else {
			System.out
			.println("VCAP_SERVICES not found, using hard-coded defaults");
	databaseHost = "0d63be48-d55a-4c59-acab-b6f753c2791f-bluemix.cloudant.com";
	port = 443;
	user = "0d63be48-d55a-4c59-acab-b6f753c2791f-bluemix";
	password = "b38b43074dfb6ad7af197009c08839bc620d0492dccf7da34076af1cb2e78831";
			
		}

				
		return getDBConnector(databaseHost,
				port,
				user,
				password,
				databaseName, 
				serviceName);



	}
	
	public void closeDBConnector()
	{
		if(httpClient!=null)
			httpClient.shutdown();
	}
	
	public CouchDbConnector getDBConnector(String host, int port, String username, 
			String password, String dbName, String serviceName) {


		CouchDbInstance dbInstance = null;


		System.out.println("Creating couch db instance...");
		httpClient = new StdHttpClient.Builder()
		.host(host)
		.port(port)
		.username(username)
		.password(password)
		.enableSSL(true)
		.relaxedSSLSettings(true)
		.build();

		dbInstance = new StdCouchDbInstance(httpClient);


		CouchDbConnector dbConnector = new StdCouchDbConnector(dbName, dbInstance);
		dbConnector.createDatabaseIfNotExists();

		return dbConnector;
	}

	
}
