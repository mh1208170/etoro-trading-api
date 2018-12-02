package learn.order;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static learn.constants.Links.OPEN_TRADE_POSITION_BUTTON;
import static learn.constants.Links.SEARCH_STOCKS_INPUT;

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
        } catch (Exception e){

        }
        authorizedDriver.findElement(By.className("inmplayer-popover-close-button")).click();

        authorizedDriver.findElement(By.cssSelector(".i-menu-link-mode-demo")).click();
        authorizedDriver.findElements(By.cssSelector("div.w-menu > div.w-menu-main > div:nth-child(2) > div > div > div:nth-child(" + pos + ")")).get(0).click();
        authorizedDriver.findElement(By.className("w-sm-footer-button")).click();
    }

    public void doOrder(Order o) throws InterruptedException {
        //TODO
        //distinguish different trade types (they have different forms)

        authorizedDriver.navigate().to("https://www.etoro.com/markets/" + o.getName() + "/");
        Thread.sleep(3000);
        authorizedDriver.findElement(By.xpath("/html/body/ui-layout/div/div/div[2]/market/div/div/div[2]/trade-button/div")).click();
        Thread.sleep(1500);
        WebElement inValue = authorizedDriver.findElement(By.xpath("//*[@id=\"open-position-view\"]/div[2]/div/div[2]/div[2]/div[1]/div[2]/input"));
        inValue.clear();
        try {
            inValue.sendKeys("$" + o.getValue());
            inValue.sendKeys(Keys.ENTER);
            Thread.sleep(2000);
            authorizedDriver.findElement(By.xpath("//*[@id=\"open-position-view\"]/div[2]/div/div[4]/div/button")).click();
            authorizedDriver.findElement(By.xpath("//*[@id=\"open-position-view\"]/div[2]/div/div[4]/div/button")).click();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
