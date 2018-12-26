package learn.user.units;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TradeUnitService {

    public static final Double PORTFOLIO_FUNDS = 10000d;

    public static final Double UNIT_VALUE = 140d;

    public static final Integer MAX_UNITS_COUNT = (int)((PORTFOLIO_FUNDS * 0.4) / UNIT_VALUE);

    @Autowired
    private TradeUnitRepository repository;

    @PostConstruct
    public void init() {
        if(repository.findAll().size() == 0) {
            repository.save(new TradeUnitsCounter());
        }
    }


    public boolean addPositionToCounter() {
        TradeUnitsCounter counter = repository.findAll().get(0);
        if(counter.getCurrentUnitsCount() < MAX_UNITS_COUNT) {
            counter.inc();
            repository.save(counter);
            return true;
        }
        return false;
    }

    public boolean removePositionFromCounter() {
        TradeUnitsCounter counter = repository.findAll().get(0);
        if(counter.getCurrentUnitsCount() > 0) {
            counter.dec();
            repository.save(counter);
            return true;
        }
        return false;
    }

    public boolean canAddPosition() {
        return true;
        //return repository.findAll().get(0).getCurrentUnitsCount() < MAX_UNITS_COUNT;
    }
}
