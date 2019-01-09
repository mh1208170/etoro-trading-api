package learn.monitoring;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PositionErrorRepository extends MongoRepository<PositionError, String> {
}
