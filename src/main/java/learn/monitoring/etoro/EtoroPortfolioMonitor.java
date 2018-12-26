package learn.monitoring.etoro;

import learn.monitoring.Monitor;
import learn.monitoring.Position;
import learn.order.EtoroOrderExecuter;
import learn.order.Order;
import learn.user.units.TradeUnitService;
import learn.history.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class EtoroPortfolioMonitor implements Monitor {

    @Autowired
    WebDriver driver;

    @Autowired
    EtoroOrderExecuter executer;

    @Autowired
    private EtoroPortfolioRepository portfolioRepository;

    @Autowired
    private TradeUnitService tradeUnitService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private EtoroService etoroService;

    @Autowired
    private EtoroInstrumenIdConverter converter;

    private String url;

    public EtoroPortfolioMonitor() {
        this.url = "https://www.etoro.com/";
    }

    @PostConstruct
    public void init() {
        driver.get(url);
        if (portfolioRepository.findAll().size() == 0) {
            portfolioRepository.save(new EtoroPortfolio("6106336"));

        }
        log.info("started etoro positions monitoring");
    }

    @Override
    //@Scheduled(fixedRate = 60000, initialDelay = 5000)
    public void scan() throws InterruptedException {
        log.info("scaning etoro");
        List<EtoroPortfolio> portfolios = portfolioRepository.findAll();

        for (int i = 0; i < portfolios.size(); i++) {
            List<EtoroPosition> idsToAdd = new ArrayList<>();
            List<EtoroPosition> idsToRemove = new ArrayList<>();
            EtoroPortfolio p = portfolios.get(i);
            List<EtoroPosition> newPos = new ArrayList<>();

            try {
                newPos.addAll(etoroService.scanPositions(p.getId()));
            } catch (Exception e) {
                log.warn("Could not connect to etoro!!!");
                return;
            }

            p.getPositionsMap().forEach((k, v) -> {
                if (!newPos.contains(v)) {
                    idsToRemove.add(v);
                }
            });

            newPos.forEach(pos -> {

                if (!p.getPositionsMap().containsKey(pos.getPosId()) && tradeUnitService.canAddPosition() &&
                        pos.getEtoroRef() == null) {
                    log.info("adding to list " + pos);
                    idsToAdd.add(pos);
                }
            });

            idsToRemove.forEach(pos -> {
                try {
                    onClosePosition(pos, pos.getPosId());
                    p.getPositionsMap().remove(pos.getPosId());
                    tradeUnitService.removePositionFromCounter();
                    historyService.addEtoroPosition(pos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            idsToAdd.forEach(pos -> {
                p.getPositionsMap().put(pos.getPosId(), pos);
                try {
                    //todo transaction
                    onOpenNewPosition(pos, pos.getPosId());
                    p.positionsMap.put(pos.getPosId(), pos);
                    portfolioRepository.save(p);
                    tradeUnitService.addPositionToCounter();
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            });
            portfolioRepository.save(p);
        }

    }


    @Override
    public void onClosePosition(Position p, String trader) {

    }

    @Override
    public void onOpenNewPosition(Position pos, String trader) throws InterruptedException {
        EtoroPosition p = (EtoroPosition) pos;
        log.info("Opening new position: tr{} {} {} {} {}", trader, p.getPosId(), p.getInstrumentId(), p.getOpenTime(), p.getAmmount());
        //if(true) {
        if ((new Date().getTime() - p.getOpenTime().getTime()) < 3 * 20 * 600000 && p.getEtoroRef() == null) {
            EtoroPosition etoroP = executer.doOrder(transformToOrder(p));
            p.setEtoroRef(etoroP.getPosId());
            log.info("Opened " + p.getPosId());
        } else {
            log.info("Position: tr{} {} {} {} {} is too old to be open", trader, p.getPosId(), p.getInstrumentId(), p.getOpenTime(), p.getAmmount());
        }
    }

    private Order transformToOrder(EtoroPosition p) {
        Order o = new Order();
        //o.setOpen(new BigDecimal(p.get()));
        o.setValue(new BigDecimal(140));
        o.setName(converter.getNameByInstrumentId(p.getInstrumentId()));
        o.setLeverage(Integer.parseInt(p.getLeverage()));
        o.setType(p.getType());
        return o;
    }


}
