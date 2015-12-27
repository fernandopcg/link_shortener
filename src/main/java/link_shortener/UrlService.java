package link_shortener;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class UrlService {

	static final Logger logger = LoggerFactory.getLogger(UrlService.class);
	
	@Autowired
	private UrlHelper urlHelper;

	@Autowired
	private UrlRepository urlRepository;
	
	public List<Url> getAllLinks() {
		return Lists.newArrayList(urlRepository.findAll());
	}
	
	public Url createNewUrl(String originalUrl) {
		
		String originalUrl_withPrefix = urlHelper.validateUrl(originalUrl);
		
		// First check if the URL is already registered, to return the existing short URI. Otherwise continue as normal
		List<Url> matchingUrlsAlreadyInRepository = getPreviouslyGeneratedShortUrlByOriginalUrl(originalUrl_withPrefix);
		if (matchingUrlsAlreadyInRepository.size() != 0) {		//Using a list, even though there mustn't be more than one already existing element
			logger.info("The repository already contains an entry matching the URL submitted now: " + matchingUrlsAlreadyInRepository.get(0));
			return matchingUrlsAlreadyInRepository.get(0);
		}	// Else continue as normal
		
		// Create and save a new ShortLink
		Url url = new Url();
		
		// Append http (as default) protocol in front of the URL if not present
		url.setOriginalUrl(urlHelper.validateUrl(originalUrl_withPrefix));
		url.setCreationDate(new Date());
		
		// Updating with the Short Link
		url.setId(urlHelper.generateShortUri());
		
		return urlRepository.save(url);
	}

	// Identical to the above, but using provided custom URL instead of generating one. Also, if the original URL already exists, the custom short URL will be created regardless
	// TODO: Optimize and make it just one method for both cases
	public Url createNewUrl(String originalUrl, String customUrl) {

		String originalUrl_withPrefix = urlHelper.validateUrl(originalUrl);
		
		// Create and save a new ShortLink
		Url url = new Url();
		
		// Append http (as default) protocol in front of the URL if not present
		url.setOriginalUrl(urlHelper.validateUrl(originalUrl_withPrefix));
		url.setCreationDate(new Date());
		
		// Updating with the Short Link
		url.setId(customUrl);
		
		return urlRepository.save(url);
	}

	public Url getUrlByShortUri(String shortUrl) {
		Url shortLink = urlRepository.findOne(shortUrl);
		logger.info("Getting URL for requested shortUrl " + shortUrl);
		return shortLink;
	}
	
	public List<Url> getPreviouslyGeneratedShortUrlByOriginalUrl(String longUrl) {
		return urlRepository.findByOriginalUrl(longUrl);
	}

	public void updateUrlOriginalUrl(String shortUrl, String originalUrl) {
		Url shortLink = getUrlByShortUri(shortUrl);
		shortLink.setOriginalUrl(originalUrl);
		urlRepository.save(shortLink);
	}
	
	public void deleteUrlByShortUrl(String shortUrl) {
		logger.info("Deleting shortUrl " + shortUrl + "from the database");
		urlRepository.delete(shortUrl);
	}
		
}
