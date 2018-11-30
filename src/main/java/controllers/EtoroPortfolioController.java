package controllers;

import model.portfolio.CopiedPortfolio;
import model.portfolio.Portfolio;
import model.portfolio.Position;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static constants.Links.MY_PORTFOLIO_ITEM;
import static constants.Links.TRADERS_PORTFOLIO_ITEM;

public class EtoroPortfolioController {

    public EtoroPortfolioController(WebDriver driver, String url) {
        this.driver = driver;
        this.url = url;
        driver.get(this.url);
    }
    public EtoroPortfolioController(WebDriver driver) {
        this.driver = driver;
        //login
    }


    private WebDriver driver;
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

    public Portfolio getPortfolio() throws InterruptedException {
        Portfolio p = new Portfolio();
        p.setType(mode);
        Thread.sleep(1000);
        List<WebElement> els = driver.findElements(By.xpath(TRADERS_PORTFOLIO_ITEM));

        for(WebElement w: els) {
            String arr[] = w.getText().split("\n");
            if (arr.length > 3) {
                p.addPosition(new Position(arr[0],arr[3]));
            } else {
                p.addCopiedPortfolio(new CopiedPortfolio(arr[0],arr[2],""));
            }

        }
        return p;
    }

}
