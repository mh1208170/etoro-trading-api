package learn.user.history;

import learn.user.OrderHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryRepository extends MongoRepository<OrderHistory, String> {
}
