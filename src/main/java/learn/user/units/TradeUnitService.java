package learn.user.units;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Getter
@Setter
public class TradeUnitService {

    public static final Double PORTFOLIO_FUNDS = 10000d;

    public static final Integer MAX_UNITS_COUNT = 20;

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

    public void setStdLotSize(Double stdLotSize) {
        TradeUnitsCounter counter = repository.findAll().get(0);
        counter.setStdLotSize(stdLotSize);
        repository.save(counter);
    }

    public void setStdLeverage(String leverage) {
        TradeUnitsCounter counter = repository.findAll().get(0);
        counter.setStdLeverage(leverage);
        repository.save(counter);
    }

    public TradeUnitsCounter getTradeUnitsCounter() {
        return repository.findAll().get(0);
    }

    public boolean canAddPosition() {
        return true;
        //return repository.findAll().get(0).getCurrentUnitsCount() < MAX_UNITS_COUNT;
    }
}
