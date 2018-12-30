package learn.monitoring;

import learn.monitoring.etoro.EtoroPortfolioMonitor;
import learn.monitoring.zuulu.ZuluPortfolioMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MonitorScheduler {

    @Autowired
    EtoroPortfolioMonitor etoroPortfolioMonitor;

    @Autowired
    ZuluPortfolioMonitor zuluPortfolioMonitor;

    @Scheduled(fixedRate = 15000, initialDelay = 5000)
    public void executeMonitors() {

        zuluPortfolioMonitor.scan();
//        etoroPortfolioMonitor.scan();
        log.info("----");
    }
}
