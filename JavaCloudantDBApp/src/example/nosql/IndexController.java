package example.nosql;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.ektorp.CouchDbConnector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.json.java.JSONArray;

@Controller  
public class IndexController extends AbstractResourceServlet{ 
    
	@RequestMapping(value = "/admin-manage/index", method = RequestMethod.GET)  
    public String showIndex() {  
        return "admin-manage/index";  
    }  
  
    @RequestMapping(value = "/admin-login", method = RequestMethod.GET)  
    public String showAdminLogin() { 
        return "admin-login";  
    }  
    
    @RequestMapping(value = "/locator", method = RequestMethod.GET)  
    public String showLocator() {  
        return "locatorhome";  
    }
    
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView get(@RequestParam(value = "id", required = false)  Long id, @RequestParam(value = "cmd", required = false) String cmd ) throws Exception {
		CouchDbConnector dbConnector = createDbConnector(); 
		
		Map jsonObject = new HashMap();
		List attachmentList = new ArrayList();
			
		if( id == null ){
			return new ModelAndView("add");
		}else{
			//check if document exists
			HashMap<String, Object> obj = dbConnector.find(HashMap.class , id+"");
					
			//close the connection manager
			closeDBConnector();
			
			if(obj!=null)
			{
				jsonObject = new HashMap();			
				java.util.LinkedHashMap attachments = (java.util.LinkedHashMap)obj.get("_attachments");
				if(attachments!=null && attachments.size()>0)
				{
					attachmentList = getAttachmentList(attachments, obj.get("_id")+"");
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
				return  new ModelAndView("add", "jsonObject", jsonObject);
			}
			else
				return new ModelAndView("add");
		}
	}
    
    @RequestMapping(value = "/map", method = RequestMethod.GET)  
    public String showMap() {  
        return "map";  
    }  
  
    @RequestMapping(value = "/", method = RequestMethod.GET)  
    public String home(Locale locale, Model model) {  
  
        Date date = new Date();  
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,  
                DateFormat.LONG, locale);  
  
        String formattedDate = dateFormat.format(date);  
  
        model.addAttribute("serverTime", formattedDate);  
  
        return "index";  
    }  
}  