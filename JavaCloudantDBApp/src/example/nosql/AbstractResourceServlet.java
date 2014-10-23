package example.nosql;

import java.util.Set;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

public abstract class AbstractResourceServlet {

	protected HttpClient httpClient = null;

	// set default db connection credentials
	protected String databaseHost = "user.cloudant.com";
	protected int port = 443;
	protected String databaseName = "demo_nosql_db";
	protected String user = "user";
	protected String password = "password";
	
	private static CouchDbConnector dbConnector = null;

	protected CouchDbConnector createDbConnector() throws Exception {
		// VCAP_SERVICES is a system environment variable
		// Parse it to obtain the for NoSQL DB connection info
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
		String serviceName = null;

		if (VCAP_SERVICES != null) {
			// parse the VCAP JSON structure
			JSONObject obj = JSONObject.parse(VCAP_SERVICES);
			String dbKey = null;
			Set<String> keys = obj.keySet();
			// Look for the VCAP key that holds the cloudant no sql db
			// information
			for (String eachkey : keys) {
				if (eachkey.contains("cloudantNoSQLDB")) {
					dbKey = eachkey;
					break;
				}
			}
			if (dbKey == null) {
				System.out
						.println("Could not find cloudantNoSQLDB key in VCAP_SERVICES env variable ");
				return null;
			}

			JSONArray list = (JSONArray) obj.get(dbKey);
			obj = (JSONObject) list.get(0);
			serviceName = (String) obj.get("name");
			System.out.println("Service Name - " + serviceName);

			obj = (JSONObject) obj.get("credentials");

			databaseHost = (String) obj.get("host");
			port = ((Long) obj.get("port")).intValue();
			user = (String) obj.get("username");
			password = (String) obj.get("password");
			// url is not being used
			// url = (String) obj.get("url");
		} else {
			System.out
					.println("VCAP_SERVICES not found, using hard-coded defaults");
			databaseHost = "0d63be48-d55a-4c59-acab-b6f753c2791f-bluemix.cloudant.com";
			port = 443;
			user = "0d63be48-d55a-4c59-acab-b6f753c2791f-bluemix";
			password = "b38b43074dfb6ad7af197009c08839bc620d0492dccf7da34076af1cb2e78831";

		}

		return getDBConnector(databaseHost, port, user, password, databaseName,
				serviceName);

	}

	public CouchDbConnector getDBConnector(String host, int port,
			String username, String password, String dbName, String serviceName) {
		
//		if (dbConnector != null){
//			return dbConnector;
//		}

		CouchDbInstance dbInstance = null;

		System.out.println("Creating couch db instance...");
		httpClient = new StdHttpClient.Builder().host(host).port(port)
				.username(username).password(password).enableSSL(true)
				.relaxedSSLSettings(true).build();

		dbInstance = new StdCouchDbInstance(httpClient);

		dbConnector = new StdCouchDbConnector(dbName,
				dbInstance);
		dbConnector.createDatabaseIfNotExists();

		return dbConnector;
	}
	
	public void closeDBConnector()
	{
		if(httpClient!=null)
			httpClient.shutdown();
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

}
