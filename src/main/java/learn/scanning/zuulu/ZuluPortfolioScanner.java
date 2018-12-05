package learn.scanning.zuulu;


import learn.order.Order;
import learn.scanning.etoro.Portfolio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;



@Component
@Slf4j
public class ZuluPortfolioScanner {

    // TODO all data must be saved in mongo
    private List<ZuluPortfolio> portfolios = Arrays.asList(new ZuluPortfolio("369739"), new ZuluPortfolio("369854"),
            new ZuluPortfolio("367416"), new ZuluPortfolio("364967"), new ZuluPortfolio("352381"), new ZuluPortfolio("373484"));

    @Autowired
    ZuluService zuluService;

    public void getTraderPositions() {
        log.info("scanning...");
        for(int i = 0; i < portfolios.size(); i++) {
            ZuluPortfolio p = portfolios.get(i);
            List<ZuluPosition> newPos = zuluService.getPositions(p.getTradersId());
            p.positionsMap.forEach((opK, opV) -> {
                if(!newPos.contains(opV)) {
                    //close than

                    p.positionsMap.remove(opK);
                    onClosePosition(opV, p.getTradersId());
                }
            });
            newPos.forEach(pos -> {
                if(!p.positionsMap.containsKey(pos.getId())) {
                    //open new pos
                    p.positionsMap.put(pos.getId(), pos);
                    onOpenNewPosition(pos, p.getTradersId());
                }
            });
        }

    }

    @Scheduled(fixedRate = 50000, initialDelay = 5000)
    private void scan() {
        // TODO recognize differences (new positions / closed positions)
        getTraderPositions();
    }

    private void onOpenNewPosition(ZuluPosition p, String trader) {
        log.info("Opening new position: tr{} {} {} {} {}", trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
    }

    private void onClosePosition(ZuluPosition p, String trader) {
        log.info("Closing position: tr{} {} {} {} {}", trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
    }

    public static Order transformToOrder(ZuluPosition zp) {
        Order o = new Order();
        o.setValue(new BigDecimal(1000d * zp.getStdLotds()));
        o.setName(zp.getCurrencyName());
        o.setLeverage(30);
        o.setType(zp.getTradeType());
        return o;

    }
}
