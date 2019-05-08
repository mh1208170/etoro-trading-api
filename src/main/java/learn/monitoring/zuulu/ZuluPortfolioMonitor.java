package learn.monitoring.zuulu;


import learn.monitoring.*;
import learn.monitoring.etoro.EtoroPosition;
import learn.order.EtoroOrderExecuter;
import learn.order.Order;
import learn.user.notification.ErrorNotifier;
import learn.user.units.TradeUnitService;
import learn.history.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PositionErrorRepository positionErrorRepository;

    @Autowired
    private ErrorNotifier notifier;

    private Set<String> ignoreList = new HashSet<>();

    @PostConstruct
    public void initPortfolioRepository() {
        ignoreList.add("XAU/USD");
        if(portfolioRepository.findAll().size() == 0) {

//            portfolioRepository.save(new ZuluPortfolio("369854"));
//            portfolioRepository.save(new ZuluPortfolio("364967"));
//            portfolioRepository.save(new ZuluPortfolio("371076"));
//            portfolioRepository.save(new ZuluPortfolio("352381"));
//            portfolioRepository.save(new ZuluPortfolio("374478"));
//            portfolioRepository.save(new ZuluPortfolio("375788"));
//            portfolioRepository.save(new ZuluPortfolio("378004"));
        }
        log.info("started zulu position monitoring");
    }

    public void scanAndCopyPortfolios() {
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
                    boolean closed = onClosePosition(pos, p);
                    p.getPositionsMap().remove(pos.getId());
                    tradeUnitService.removePositionFromCounter();
                    if (closed) {
                        historyService.addZuluPosition(pos);
                    }
                    portfolioRepository.save(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            idsToAdd.forEach(pos -> {
                p.getPositionsMap().put(pos.getId(), pos);
                try {
                    onOpenNewPosition(pos, p);
                    p.positionsMap.put(pos.getId(), pos);
                    portfolioRepository.save(p);
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            });

        }

    }


    public void scan() {
        scanAndCopyPortfolios();
    }

    @Override
    public boolean onOpenNewPosition(AbstractPosition pos, AbstractPortfolio trader) throws InterruptedException {
        ZuluPosition p = (ZuluPosition) pos;
        log.info("Opening new position: tr{} {} {} {} {}", trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
        //if(true) {
        if((new Date().getTime() - p.getDateTime().getTime()) < 1 * 20 * 600000 && p.getEtoroRef() == null) {
            EtoroPosition etoroP = executer.doOrder(transformToOrder(p, portfolioRepository.findOne(pos.getId().split(":")[0]).getFactor()));
            p.setEtoroRef(etoroP.getId());
            tradeUnitService.addPositionToCounter();
            log.info("Opened " + p.getId());
            return true;
        } else {
            log.info("Position: tr{} {} {} {} {} is too old to be open", trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
            return false;
        }

    }

    @Override
    public boolean onClosePosition(AbstractPosition pos, AbstractPortfolio trader) throws InterruptedException {
        ZuluPosition p = (ZuluPosition) pos;
        log.info("Closing position: tr{} {} {} {} {}", trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
        if (p.getEtoroRef() != null) {
            if(executer.closePositionById(p.getEtoroRef(), p.getCurrencyName().replace("/",""))) {
                log.info("Closed position: tr{} {} {} {} {}",trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
                return true;
            } else {
                log.error("error while closing pos: {}, was probably closed by SL or TP", pos);
                PositionError error = new PositionError(pos.getId(), pos);
                positionErrorRepository.save(error);
                notifier.sendErrorNotification(error);
               // throw new RuntimeException("could not close position" + pos + " will try again");
                return true;
            }

        } else {
            log.info("Position: tr{} {} {} {} {} was never opened on etoro...",trader, p.getId(), p.getCurrencyName(), p.getDateTime(), p.getStdLotds());
            return false;
        }
    }

    public Order transformToOrder(ZuluPosition zp, double factor) {
        Order o = new Order();
        o.setOpen(new BigDecimal(zp.getEntryRate()));
        o.setValue(new BigDecimal(200).multiply(new BigDecimal(factor)));
        o.setName(zp.getCurrencyName().replace("/",""));
        o.setLeverage(20);
        o.setType(zp.getTradeType());
        o.setRealTime(false);
        o.setPlatform("zulu");
        return o;

    }
}
