package learn.monitoring.zuulu;


import learn.monitoring.etoro.Position;
import learn.order.EtoroOrderExecuter;
import learn.order.Order;
import learn.units.TradeUnitService;
import learn.units.TradeUnitsCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class ZuluPortfolioMonitor {

    @Autowired
    ZuluService zuluService;

    @Autowired
    EtoroOrderExecuter executer;

    @Autowired
    private ZuluPortfolioRepository portfolioRepository;

    @Autowired
    private TradeUnitService tradeUnitService;

    @PostConstruct
    public void initPortfolioRepository() {

        if(portfolioRepository.findAll().size() == 0) {
            portfolioRepository.save(new ZuluPortfolio("369739"));
            portfolioRepository.save(new ZuluPortfolio("369854"));
            portfolioRepository.save(new ZuluPortfolio("371076"));
            portfolioRepository.save(new ZuluPortfolio("352381"));
            portfolioRepository.save(new ZuluPortfolio("292620"));
            portfolioRepository.save(new ZuluPortfolio("332439"));
        }

    }

    public void getTraderPositions() {
//        log.info("monitoring...");

        portfolioRepository.findAll().forEach(p -> {
            List<ZuluPosition> newPos = zuluService.scanPositions(p.getId());
            p.getPositionsMap().forEach((k,v) -> {
                if(!newPos.contains(v)) {
                    p.getPositionsMap().remove(k);
                    try {
                        onClosePosition(v, p.getId());
                        tradeUnitService.removePositionFromCounter();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });

            newPos.forEach(pos -> {
                if(!p.getPositionsMap().containsKey(pos.getId()) && tradeUnitService.canAddPosition()) {
                    p.getPositionsMap().put(pos.getId(), pos);
                    try {
                        onOpenNewPosition(pos, p.getId());
                        tradeUnitService.addPositionToCounter();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            portfolioRepository.save(p);
//            log.info("saved portfolio from: " + p.getId());
        });

    }

    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    private void scan() {
        getTraderPositions();
    }

    //TODO remove mocked position name
    private void onOpenNewPosition(ZuluPosition p, String trader) throws InterruptedException {
        log.info("Opening new position: tr{} {} {} {} {}", trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
        if((new Date().getTime() - p.getDateTime().getTime()) < 60000) {
            Position etoroP = executer.doOrder(transformToOrder(p));
            p.setEtoroRef(etoroP.getPosId());
            log.info("Opened " + p.getId());
        } else {
            log.info("Position: tr{} {} {} {} {} is too old to be open", trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
        }

    }

    //TODO remove mocked position name
    private void onClosePosition(ZuluPosition p, String trader) throws InterruptedException {
        log.info("Closing position: tr{} {} {} {} {}", trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
        if(p.getEtoroRef() != null) {
            executer.closePositionById(p.getEtoroRef(), p.getCurrencyName());
            executer.closePositionById(p.getEtoroRef(), "btc");
            log.info("deleted " + p.getId());
        } else {
            log.info("Position: tr{} {} {} {} {} was never opened on etoro",trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
        }

    }

    //TODO remove mocked position name
    public static Order transformToOrder(ZuluPosition zp) {
//        Order o = new Order();
//        o.setValue(new BigDecimal(1000d * zp.getStdLotds()));
//        o.setName(zp.getCurrencyName().replace("/", ""));
//        o.setLeverage(30);
//        o.setType(zp.getTradeType());
//        return o;
        Order o = new Order();
        o.setValue(new BigDecimal(140));
        o.setName("btc");
//        o.setLeverage(30);
        o.setType(zp.getTradeType());
        return o;

    }
}
