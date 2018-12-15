package learn.monitoring.etoro;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EtoroPortfolioRepository extends MongoRepository<EtoroPortfolio, String> {
}
