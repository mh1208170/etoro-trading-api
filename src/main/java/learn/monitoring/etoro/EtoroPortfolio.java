package learn.monitoring.etoro;

import learn.monitoring.Portfolio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@NoArgsConstructor
public class EtoroPortfolio implements Portfolio {

    String id;
    Map<String, EtoroPosition> positionsMap = new ConcurrentHashMap<>();
    private String type = "real";

    public EtoroPortfolio(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
