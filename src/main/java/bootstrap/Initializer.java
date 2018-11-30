package bootstrap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class Initializer {

    private WebDriver driver;
    private String uname;
    private String pwd;

    public Initializer(WebDriver driver, String uname, String pwd) {
        this.driver = driver;
        this.uname = uname;
        this.pwd = pwd  ;
    }

    public void init() {
        String pathToChrome;
        if (System.getProperty("os.name").startsWith("Mac")) {
            pathToChrome = "drivers/mac/chromedriver";
        } else {
            pathToChrome = "drivers/ubuntu/chromedriver";
        }

        System.setProperty("webdriver.chrome.driver", pathToChrome);
    }

    public void login() {
        driver.get("https://www.etoro.com/portfolio");

        driver.findElement(By.id("username")).sendKeys(uname);
        driver.findElement(By.id("password")).sendKeys(pwd);

        driver.findElement(By.className("e-btn-big")).click();

        System.out.println();
    }
}
