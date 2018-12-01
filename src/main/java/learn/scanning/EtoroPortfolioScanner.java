package learn.scanning;

import learn.auth.Authentication;
import learn.portfolio.Portfolio;
import learn.portfolio.Position;
import learn.portfolio.PositionGroup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class EtoroPortfolioScanner {

    @Autowired
    WebDriver driver;

    @Autowired
    Authentication authentication;

//    @PostConstruct
    public void init() {
        authentication.login();
    }

    public EtoroPortfolioScanner(WebDriver driver) {
        this.driver = driver;
        this.url = "https://www.etoro.com/";
    }
//    public EtoroPortfolioScanner(WebDriver driver) {
//        this.driver = driver;
//        //login
//    }


    private String mode;
    private String url;

    public void setMode(String mode) {
        int pos = 2;
        switch (mode) {
            case "real":
                pos = 1;
                break;
            case "virtual":
                pos = 2;
        }
        driver.findElement(By.cssSelector(".i-menu-link-mode-demo")).click();
        driver.findElements(By.cssSelector("div.w-menu > div.w-menu-main > div:nth-child(2) > div > div > div:nth-child(" + pos + ")")).get(0).click();
        driver.findElement(By.className("w-sm-footer-button")).click();
        this.mode = mode;
    }

    public Portfolio getPortfolio(String trader) throws InterruptedException {
        driver.get(this.url + trader);
        Portfolio portfolio = new Portfolio();
        portfolio.setType(mode);
        Thread.sleep(1000);

        List<WebElement> els =  driver.findElements(By.xpath("/html/body/ui-layout/div/div/div[2]/div/tabs/div[3]/tabscontent/tab[3]/div/div/div/div/div/div/div[2]/div/ui-table/ui-table-body/a"));
        for(int j = 0; j < els.size(); j++) {
            PositionGroup pg =  new PositionGroup();
            WebElement pgEl = driver.findElement(By.xpath("/html/body/ui-layout/div/div/div[2]/div/tabs/div[3]/tabscontent/tab[3]/div/div/div/div/div/div/div[2]/div/ui-table/ui-table-body/a[" + (j + 1) + "]"));
            pg.setName(pgEl.getText());
            pgEl.click();
            Thread.sleep(300);
            List<WebElement> posEls = driver.findElements(By.xpath("/html/body/ui-layout/div/div/div[2]/div/tabs/div[3]/tabscontent/tab[3]/div/div/div/div/div/div/div[2]/div[2]/div/ui-table/ui-table-body/div"));
            for(int i = 1; i < posEls.size(); i++) {
                Position p = new Position();
                //WebElement pEl = posEls.get(i);
                WebElement pEl = driver.findElement(By.xpath("/html/body/ui-layout/div/div/div[2]/div/tabs/div[3]/tabscontent/tab[3]/div/div/div/div/div/div/div[2]/div[2]/div/ui-table/ui-table-body/div["+(i+1) + "]"));
                p.setName(pEl.getText());
                pg.addPosition(p);
            }
            portfolio.addPositionGroup(pg);
            driver.navigate().back();
            Thread.sleep(300);

        }

        return portfolio;
    }

}
