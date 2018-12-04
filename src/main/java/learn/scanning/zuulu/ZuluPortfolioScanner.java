package learn.scanning.zuulu;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;


@Component
@Slf4j
public class ZuluPortfolioScanner {

    // TODO all data must be saved in mongo
    private List<ZuluPortfolio> portfolios;

    @Autowired
    ZuluService zuluService;


    @PostConstruct
    public void getTraderPositions() {
        portfolios = Arrays.asList(new ZuluPortfolio("369739"));
        portfolios.forEach(p -> p.setPositions(zuluService.getPortfolios(p.getTradersId())));
    }

    @Scheduled(fixedRate = 50000, initialDelay = 50000)
    private void scan() {
        // TODO recognize differences (new positions / closed positions)
        portfolios.forEach(p -> {
            log.info("parsing zulu portfolio {}...", p.getTradersId());
        });
    }

    private void onOpenNewPosition() {

    }

    private void onClosePosition() {

    }
}
