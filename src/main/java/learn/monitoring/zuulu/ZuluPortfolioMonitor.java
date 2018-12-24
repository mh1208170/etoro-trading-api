package learn.monitoring.zuulu;


import learn.monitoring.Monitor;
import learn.monitoring.Position;
import learn.monitoring.etoro.EtoroPosition;
import learn.order.EtoroOrderExecuter;
import learn.order.Order;
import learn.units.TradeUnitService;
import learn.user.history.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;


@Component
@Slf4j
public class ZuluPortfolioMonitor implements Monitor {

    @Autowired
    ZuluService zuluService;

    @Autowired
    EtoroOrderExecuter executer;

    @Autowired
    private ZuluPortfolioRepository portfolioRepository;

    @Autowired
    private TradeUnitService tradeUnitService;

    @Autowired
    private HistoryService historyService;

    private Set<String> ignoreList = new HashSet<>();

    @PostConstruct
    public void initPortfolioRepository() {
        ignoreList.add("XAU/USD");
        if(portfolioRepository.findAll().size() == 0) {

            portfolioRepository.save(new ZuluPortfolio("369854"));
            portfolioRepository.save(new ZuluPortfolio("364967"));
            portfolioRepository.save(new ZuluPortfolio("371076"));
            portfolioRepository.save(new ZuluPortfolio("352381"));
            portfolioRepository.save(new ZuluPortfolio("374478"));
            portfolioRepository.save(new ZuluPortfolio("375788"));
            portfolioRepository.save(new ZuluPortfolio("378004"));
        }
        log.info("started zulu position monitoring");
    }

    public void getTraderPositions() {

        List<ZuluPortfolio> portfolios = portfolioRepository.findAll();

        for(int i = 0; i < portfolios.size(); i++) {
            List<ZuluPosition> idsToAdd = new ArrayList<>();
            List<ZuluPosition> idsToRemove = new ArrayList<>();
            ZuluPortfolio p = portfolios.get(i);
            List<ZuluPosition> newPos = new ArrayList<>();

            try {
               newPos.addAll(zuluService.scanPositions(p.getId()));
            } catch (Exception e) {
                log.warn("Could not connect to zulu!!!");
                return;
            }

            p.getPositionsMap().forEach((k,v) -> {
                if(!newPos.contains(v)) {
                    idsToRemove.add(v);
                }
            });

            newPos.forEach(pos -> {

                if(!p.getPositionsMap().containsKey(pos.getId()) && tradeUnitService.canAddPosition() &&
                        pos.getEtoroRef() == null && !ignoreList.contains(pos.getCurrencyName()) ) {
                    log.debug("adding to list " + pos);
                    idsToAdd.add(pos);
                }
            });

            idsToRemove.forEach(pos -> {
                try {
                    onClosePosition(pos, pos.getId());
                    p.getPositionsMap().remove(pos.getId());
                    tradeUnitService.removePositionFromCounter();
                    historyService.addZuluPosition(pos);
                    portfolioRepository.save(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            idsToAdd.forEach(pos -> {
                p.getPositionsMap().put(pos.getId(), pos);
                try {
                    onOpenNewPosition(pos, pos.getId());
                    p.positionsMap.put(pos.getId(), pos);
                    portfolioRepository.save(p);
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            });

        }

    }

    @Scheduled(fixedRate = 15000, initialDelay = 5000)
    public void scan() {
        getTraderPositions();
    }

    //TODO remove mocked position name
    @Override
    public void onOpenNewPosition(Position pos, String trader) throws InterruptedException {
        ZuluPosition p = (ZuluPosition) pos;
        log.info("Opening new position: tr{} {} {} {} {}", trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
        //if(true) {
        if((new Date().getTime() - p.getDateTime().getTime()) < 1 * 20 * 600000 && p.getEtoroRef() == null) {
            EtoroPosition etoroP = executer.doOrder(transformToOrder(p, portfolioRepository.findOne(trader).getFactor()));
            p.setEtoroRef(etoroP.getPosId());
            tradeUnitService.addPositionToCounter();
            log.info("Opened " + p.getId());
        } else {
            log.info("Position: tr{} {} {} {} {} is too old to be open", trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
        }

    }

    //TODO remove mocked position name
    @Override
    public void onClosePosition(Position pos, String trader) throws InterruptedException {
        ZuluPosition p = (ZuluPosition) pos;
        log.info("Closing position: tr{} {} {} {} {}", trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
        if (p.getEtoroRef() != null) {
            if(executer.closePositionById(p.getEtoroRef(), p.getCurrencyName().replace("/",""))) {
                log.info("Closed position: tr{} {} {} {} {}",trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
            } else {
                log.error("error while opening pos: {}", pos);
                throw new RuntimeException("could not closed position" + pos + " will try again");
            }

        } else {
            log.info("Position: tr{} {} {} {} {} was never opened on etoro...",trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
        }
    }

    //TODO remove mocked position name
    public  Order transformToOrder(ZuluPosition zp, double factor) {
        Order o = new Order();
        o.setOpen(new BigDecimal(zp.getEntryRate()));
        o.setValue(new BigDecimal(140).multiply(new BigDecimal(factor)));
        o.setName(zp.getCurrencyName().replace("/",""));
        o.setLeverage(30);
        o.setType(zp.getTradeType());
        return o;

    }
}
