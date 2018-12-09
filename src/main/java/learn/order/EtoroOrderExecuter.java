package learn.order;

import learn.monitoring.etoro.EtoroPosition;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class EtoroOrderExecuter {


    @Autowired
    WebDriver authorizedDriver;

    @PostConstruct
    private void init() throws InterruptedException {
        setMode("virtual");
    }

    public void setMode(String mode) throws InterruptedException {
        Thread.sleep(5000);
        int pos = 2;
        switch (mode) {
            case "real":
                pos = 1;
                break;
            case "virtual":
                pos = 2;
        }
        try {
        } catch (Exception e) {

        }
        authorizedDriver.findElement(By.className("inmplayer-popover-close-button")).click();

        authorizedDriver.findElement(By.cssSelector(".i-menu-link-mode-demo")).click();
        authorizedDriver.findElements(By.cssSelector("div.w-menu > div.w-menu-main > div:nth-child(2) > div > div > div:nth-child(" + pos + ")")).get(0).click();
        authorizedDriver.findElement(By.className("w-sm-footer-button")).click();
    }

    public EtoroPosition doOrder(Order o) throws InterruptedException {
        //TODO
        //distinguish different trade types (they have different forms)

        authorizedDriver.navigate().to("https://www.etoro.com/markets/" + o.getName() + "/");
        Thread.sleep(3000);
        authorizedDriver.findElement(By.xpath("/html/body/ui-layout/div/div/div[2]/market/div/div/div[2]/trade-button/div")).click();
        Thread.sleep(1500);

            List<WebElement> sellBuyBtns = authorizedDriver.findElements(By.className("execution-head-button"));
            if (o.getType().equalsIgnoreCase("sell")) {
                sellBuyBtns.get(0).click();
            } else {
                sellBuyBtns.get(1).click();
            }
            Thread.sleep(200);
            WebElement inValue = authorizedDriver.findElement(By.xpath("//*[@id=\"open-position-view\"]/div[2]/div/div[2]/div[2]/div[1]/div[2]/input"));
            inValue.clear();
            inValue.sendKeys("$" + o.getValue());
            inValue.sendKeys(Keys.ENTER);
            Thread.sleep(50);
            List<WebElement> leverages = authorizedDriver.findElements(By.xpath("//*[@id=\"open-position-view\"]/div[2]/div/div[3]/tabs/div[3]/tabscontent/tab[2]/div/div[1]/a"));
            if (!leverages.isEmpty()) {
                leverages.forEach(l -> {
                    if (l.getText().replaceAll("X", "").equalsIgnoreCase(o.getLeverage() + "")) {
                        l.click();
                    }
                });
            }
            Thread.sleep(2000);
            authorizedDriver.findElement(By.xpath("//*[@id=\"open-position-view\"]/div[2]/div/div[4]/div/button")).click();

        while (true) {
            try {
                //save to database
                EtoroPosition res = new EtoroPosition();
                res.setName(o.getName());
                res.setAmmount(o.getValue());
                res.setLeverage(String.valueOf(o.getLeverage()));
                scanId(res);
                return res;
            } catch (Throwable t) {
                t.printStackTrace();
                return null;
            }
        }


    }

    private EtoroPosition scanId(EtoroPosition p) throws InterruptedException {
        authorizedDriver.navigate().to("https://www.etoro.com/portfolio/" + p.getName() + "/");
        Thread.sleep(1000);
        authorizedDriver.findElement(By.xpath("/html/body/ui-layout/div/div/div[2]/div/div[2]/span/ui-table/ui-table-head/b")).click();
        Thread.sleep(500);
        authorizedDriver.findElement(By.xpath("/html/body/ui-layout/div/div/div[2]/div/div[2]/span/ui-table/ui-table-body/div[1]/div[3]/ui-table-button-cell/div[2]")).click();        Thread.sleep(600);
        //authorizedDriver.findElement(By.xpath("/html/body/ui-layout/div/div/div[2]/div/div[2]/span/ui-table/ui-table-body/div[1]/div[1]/ui-table-button-cell/div[2]")).click();
        Thread.sleep(1000);
        String id = authorizedDriver.findElement(By.cssSelector("div.w-execution-main-head > div.w-sm-position-info-trade > div.w-sm-position-info-trade-value.ng-binding"))
                .getText();
        System.out.println("Order id ist: " + id);
        p.setPosId(id);
        return p;

    }

    public boolean closePositionById(String id, String name) throws InterruptedException {
        id = "#" + id;
        authorizedDriver.navigate().to("https://www.etoro.com/portfolio/" + name + "/");
        Thread.sleep(2000);
        List<WebElement> positions = authorizedDriver.findElements(By.className("ui-table-row"));
        Thread.sleep(500);
        for(int i =1; i < positions.size(); i++) {
            authorizedDriver.findElement(By.xpath(String.format("/html/body/ui-layout/div/div/div[2]/div/div[2]/span/ui-table/ui-table-body/div[%d]/div[3]/ui-table-button-cell/div[2]",i))).click();
            Thread.sleep(2000);
            String currentId = authorizedDriver.findElement(By.cssSelector("div.w-execution-main-head > div.w-sm-position-info-trade > div.w-sm-position-info-trade-value.ng-binding"))
                    .getText();
            Thread.sleep(500);
            if(id.equalsIgnoreCase(currentId)) {
                authorizedDriver.findElement(By.className("e-btn-big")).click();
                log.info("closed " + id);
                return true;
            }
            authorizedDriver.findElement(By.className("w-share-header-nav-button-x")).click();
            Thread.sleep(500);
        }
        return false;
    }


}
