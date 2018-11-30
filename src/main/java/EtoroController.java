import model.Asset;
import model.Portfolio;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class EtoroController {

    private WebDriver driver;
    private String uname;
    private String pwd;

    public EtoroController(WebDriver driver, String uname, String pwd) {
        this.driver = driver;
        this.uname = uname;
        this.pwd = pwd;
    }

    public void init() {
        String pathToChrome;
        if (System.getProperty("os.name").startsWith("Mac")) {
            pathToChrome = "drivers/mac/chromedriver";
        } else {
            pathToChrome = "drivers/ubuntu/chromedriver";
        }

        System.setProperty("webdriver.chrome.driver", pathToChrome);
        driver.get("https://www.etoro.com/portfolio");

        driver.findElement(By.id("username")).sendKeys(uname);
        driver.findElement(By.id("password")).sendKeys(pwd);

        driver.findElement(By.className("e-btn-big")).click();

        System.out.println();
    }

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
    }

    public Portfolio getPortfolio() throws InterruptedException {
        Portfolio p = new Portfolio();
       // String type = driver.findElement(By.className("i-menu-link-mode-demo")).getText();
        Thread.sleep(1000);
        List<WebElement> names = driver.findElements(By.className("table-first-name"));

        List<WebElement> profits = driver.findElements(By.className("i-ptc-rate-value"));
        List<WebElement> invested = driver.findElements(By.className("portfolio-overview-table-body-cell-invested-value"));
        Thread.sleep(1000);
        for(int i = 0; i < names.size(); i++) {
            p.addAsset(new Asset(names.get(i).getText(), "",
                    ""));
        }
        return p;
    }
}
