package link_shortener;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BaseController { 
	
	static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private UrlService urlService;

	@RequestMapping(method=RequestMethod.GET, value="/")
	public String getAll(Model model) {
		logger.trace("Main page requested by user");
		return "homepage";
	}
	@RequestMapping(method=RequestMethod.GET, value="/error")
	public String errorPage(Model model) {
		logger.trace("Redirecting to error page");
		return "error";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="{id}")
	public String getUrl(@PathVariable String id, Model model, HttpServletResponse response) {
		logger.trace("Resolving id '" + id + "'");
		
		Url url = urlService.getUrlByShortUri(id);
		
		try {
			String target_url = url.getOriginalUrl();
			
			response.setStatus(301);
			response.setHeader("Location", target_url);
			return null;
		}
		
		catch (NullPointerException e) {
			response.setStatus(404);
			model.addAttribute("shorturl", id);
			return "shortlink_not_found";
		}
		
	}
	
}