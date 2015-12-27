package link_shortener;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String> {
	public List <Url> findByOriginalUrl(String originalUrl);

}