package learn.units;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TradeUnitRepository extends MongoRepository<TradeUnitsCounter, String> {
}
