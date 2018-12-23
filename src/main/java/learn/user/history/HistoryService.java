package learn.user.history;

import learn.monitoring.etoro.EtoroPosition;
import learn.monitoring.zuulu.ZuluPosition;
import learn.user.OrderHistory;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private WebDriver authorizedDriver;

    public void addZuluPosition(ZuluPosition p) {
        try {
            OrderHistory history = new OrderHistory();

            history.setClosed(new Date());
            history.setCopiedFrom("zulu");
            history.setType(p.getTradeType());

            authorizedDriver.navigate().to("https://www.etoro.com/portfolio/history");
            Thread.sleep(700);
            Elements parsedRow = Jsoup.parse(authorizedDriver.getPageSource()).select(".ui-table-row").get(0).select(".i-portfolio-table-marker-obj");

            history.setProfitInUSD(new BigDecimal(parsedRow.get(4).text().replace("$", "")));
            history.setOrderId(p.getEtoroRef());
            history.setOpenPrice(new BigDecimal(parsedRow.get(2).text()));
            history.setClosePrice(new BigDecimal(parsedRow.get(3).text()));

            historyRepository.save(history);
            log.info("story {} added!", history);
        } catch (Exception e) {
            log.error("could not add history for position: {}", p);
        }

    }

    public List<OrderHistory> getHistory() {
        return historyRepository.findAll();
    }

    public void addEtoroPosition(EtoroPosition pos) {

    }
}
