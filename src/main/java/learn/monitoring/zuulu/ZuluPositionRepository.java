package learn.monitoring.zuulu;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZuluPositionRepository extends MongoRepository<ZuluPosition, String> {

    ZuluPosition findZuluPositionById(String id);
}
