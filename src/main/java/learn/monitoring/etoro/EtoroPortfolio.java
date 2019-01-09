package learn.monitoring.etoro;

import learn.monitoring.AbstractPortfolio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Getter
@Setter
@NoArgsConstructor
public class EtoroPortfolio extends AbstractPortfolio {

    protected Map<String, EtoroPosition> positionsMap = new ConcurrentHashMap<>();

    public EtoroPortfolio(String id) {
        this.id = id;
    }


}
