package link_shortener;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/urls")
public class UrlController {

	@Autowired
	private UrlService urlService;

	@RequestMapping(method=RequestMethod.POST, value="/")
	public Url createUrl(@RequestBody String requestBody, @RequestParam("originalUrl") String originalUrl, @RequestParam("customUrl") String customUrl, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
		System.out.println("Received request:" + requestBody);
		
		if (customUrl == "") {
			System.out.println("Creating new link" + originalUrl);
			return urlService.createNewUrl(originalUrl);
		}
		
		else if (urlService.getUrlByShortUri(customUrl) == null) {
			System.out.println("Requested alias:" + customUrl);
			System.out.println("Checking if alias is available...");
			// customURL has not been used yet
			return urlService.createNewUrl(originalUrl, customUrl);
		}
		
		else {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			return null;
		}
	
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/")
	public List<Url> allLinks() {
		System.out.println("Showing all links");
		List<Url> links = urlService.getAllLinks();
		return links;
	}

	@RequestMapping(method=RequestMethod.GET, value="/{shortUrl}")
	public Url getLink(@PathVariable String shortUrl) {
		System.out.println(shortUrl);
		return urlService.getUrlByShortUri(shortUrl);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{shortUrl}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void deleteLink(@PathVariable String shortUrl) {
		urlService.deleteUrlByShortUrl(shortUrl);
	}
		
}