package learn.monitoring.etoro;

import learn.monitoring.Monitor;
import learn.monitoring.Position;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EtoroPortfolioMonitor implements Monitor {

    @Autowired
    WebDriver driver;

    public EtoroPortfolioMonitor(WebDriver driver) {
        this.driver = driver;
        this.url = "https://www.etoro.com/";
    }

    private String url;

    @Override
    //@Scheduled(fixedRate = 60000, initialDelay = 5000)
    public void scan() throws InterruptedException {
        log.info("scaning etoro");
        log.info(getPortfolio("people/aimstrader/portfolio").toString());
    }

    public EtoroPortfolio getPortfolio(String trader) throws InterruptedException {
        driver.get(this.url + trader);
        EtoroPortfolio portfolio = new EtoroPortfolio();
        Thread.sleep(1000);
        List<WebElement> els =  driver.findElements(By.xpath("/html/body/ui-layout/div/div/div[2]/div/tabs/div[3]/tabscontent/tab[3]/div/div/div/div/div/div/div[2]/div/ui-table/ui-table-body/a"));
        for(int j = 0; j < els.size(); j++) {
            EtoroPositionGroup pg =  new EtoroPositionGroup();
            WebElement pgEl = driver.findElement(By.xpath("/html/body/ui-layout/div/div/div[2]/div/tabs/div[3]/tabscontent/tab[3]/div/div/div/div/div/div/div[2]/div/ui-table/ui-table-body/a[" + (j + 1) + "]"));
            pg.parseInfo(pgEl.getText());
            pgEl.click();
            Thread.sleep(300);
            List<WebElement> posEls = driver.findElements(By.xpath("/html/body/ui-layout/div/div/div[2]/div/tabs/div[3]/tabscontent/tab[3]/div/div/div/div/div/div/div[2]/div[2]/div/ui-table/ui-table-body/div"));
            for(int i = 1; i < posEls.size(); i++) {
                EtoroPosition p = new EtoroPosition();
                //WebElement pEl = posEls.get(i);
                WebElement pEl = driver.findElement(By.xpath("/html/body/ui-layout/div/div/div[2]/div/tabs/div[3]/tabscontent/tab[3]/div/div/div/div/div/div/div[2]/div[2]/div/ui-table/ui-table-body/div["+(i+1) + "]"));
                p.parseInfo(pEl.getText());
                pg.addPosition(p);
            }
            portfolio.addPositionGroup(pg);
            driver.navigate().back();
            Thread.sleep(300);
        }
        return portfolio;
    }

    @Override
    public void onClosePosition(Position p, String trader) {

    }

    @Override
    public void onOpenNewPosition(Position p, String trader) {

    }


}
