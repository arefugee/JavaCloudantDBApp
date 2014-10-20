package example.nosql;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller  
public class IndexController {  
    @RequestMapping(value = "/admin-manage/index", method = RequestMethod.GET)  
    public String showIndex() {  
        return "admin-manage/index";  
    }  
  
    @RequestMapping(value = "/admin-login", method = RequestMethod.GET)  
    public String showAdminLogin() {  
        return "admin-login";  
    }  
  
    @RequestMapping(value = "/", method = RequestMethod.GET)  
    public String home(Locale locale, Model model) {  
  
        Date date = new Date();  
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,  
                DateFormat.LONG, locale);  
  
        String formattedDate = dateFormat.format(date);  
  
        model.addAttribute("serverTime", formattedDate);  
  
        return "home";  
    }  
}  