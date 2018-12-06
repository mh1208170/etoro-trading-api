package learn.order;

import learn.scanning.etoro.Position;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    public void doOrder(Order o) throws InterruptedException {
        //TODO
        //distinguish different trade types (they have different forms)

        authorizedDriver.navigate().to("https://www.etoro.com/markets/" + o.getName() + "/");
        Thread.sleep(3000);
        authorizedDriver.findElement(By.xpath("/html/body/ui-layout/div/div/div[2]/market/div/div/div[2]/trade-button/div")).click();
        Thread.sleep(1500);


        try {

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
            //authorizedDriver.findElement(By.xpath("//*[@id=\"open-position-view\"]/div[2]/div/div[4]/div/button")).click();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //TODO
    // Handle bug with wrong data of position!!!
    public void closePosition(Position closed) throws InterruptedException {
        authorizedDriver.navigate().to("https://www.etoro.com/portfolio/" + closed.getName() + "/");

        List<WebElement> positions = authorizedDriver.findElements(By.xpath("/html/body/ui-layout/div/div/div[2]/div/div[2]/span/ui-table/ui-table-body/div[1]/div"));


        for(int i =1; i < positions.size(); i++) {
            WebElement posEl = authorizedDriver.findElement(By.xpath(String.format("/html/body/ui-layout/div/div/div[2]/div/div[2]/span/ui-table/ui-table-body/div[%d]/div[3]",i)));
            //check posEl
            Position p = new Position();
            Date dt = closed.getMilisOpen();
            closed.setOpenTime(LocalDateTime.of(dt.getYear() + 1900,dt.getMonth()+1,dt.getDay(),dt.getHours(), dt.getMinutes()));
            p.parseInfo(posEl.getText().replaceAll("\\$",""));
            if (closed.getName().equalsIgnoreCase(p.getName()) && closed.getType().equalsIgnoreCase(p.getType()) && closed.getOpenTime().equals(p.getOpenTime())) {
                System.out.println("Closing pos!");

                authorizedDriver.findElements(By.xpath(String.format("/html/body/ui-layout/div/div/div[2]/div/div[2]/span/ui-table/ui-table-body/div[%d]/div[3]/ui-table-button-cell/div[2]",i))).get(0).click();
                Thread.sleep(2000);
                authorizedDriver.findElement(By.className("e-btn-big")).click();
                System.out.println("Closed!");
                return;
            } else {
                System.out.println("not found!");
            }
        }
//        positions.forEach(position -> {
//            String info= position.getText().replaceAll("\\$","");
//            Position p = new Position();
//            p.parseInfo(info);
//            if (closed.getName().equalsIgnoreCase(p.getName()) && closed.getType().equalsIgnoreCase(p.getType()) && closed.getOpenTime().equals(p.getOpenTime())) {
//                // close
//                System.out.println("Closing pos!");
//            }
//        });
    }

}
