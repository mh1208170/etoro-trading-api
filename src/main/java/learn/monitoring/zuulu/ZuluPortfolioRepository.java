package learn.monitoring.zuulu;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ZuluPortfolioRepository extends MongoRepository<ZuluPortfolio, String> {
}

