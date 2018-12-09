package learn.monitoring.etoro;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EtoroPortfolioMonitor {

    @Autowired
    WebDriver driver;

    public EtoroPortfolioMonitor(WebDriver driver) {
        this.driver = driver;
        this.url = "https://www.etoro.com/";
    }

    private String url;



    public Portfolio getPortfolio(String trader) throws InterruptedException {
        driver.get(this.url + trader);
        Portfolio portfolio = new Portfolio();
        Thread.sleep(1000);

        List<WebElement> els =  driver.findElements(By.xpath("/html/body/ui-layout/div/div/div[2]/div/tabs/div[3]/tabscontent/tab[3]/div/div/div/div/div/div/div[2]/div/ui-table/ui-table-body/a"));
        for(int j = 0; j < els.size(); j++) {
            PositionGroup pg =  new PositionGroup();
            WebElement pgEl = driver.findElement(By.xpath("/html/body/ui-layout/div/div/div[2]/div/tabs/div[3]/tabscontent/tab[3]/div/div/div/div/div/div/div[2]/div/ui-table/ui-table-body/a[" + (j + 1) + "]"));
            pg.parseInfo(pgEl.getText());
            pgEl.click();
            Thread.sleep(300);
            List<WebElement> posEls = driver.findElements(By.xpath("/html/body/ui-layout/div/div/div[2]/div/tabs/div[3]/tabscontent/tab[3]/div/div/div/div/div/div/div[2]/div[2]/div/ui-table/ui-table-body/div"));
            for(int i = 1; i < posEls.size(); i++) {
                Position p = new Position();
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

}
