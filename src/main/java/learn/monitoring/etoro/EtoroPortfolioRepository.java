package learn.monitoring.etoro;

import learn.monitoring.zuulu.ZuluPortfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EtoroPortfolioRepository extends MongoRepository<ZuluPortfolio, String> {
}
