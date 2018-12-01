package learn.config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {


    private String uname;
    private String pwd;

    public Config() {
//        this.uname = uname;
//        this.pwd = pwd  ;
    }

    @Bean
    public WebDriver driver() {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--window-size=1920,1920");
        String pathToChrome;
        if (System.getProperty("os.name").startsWith("Mac")) {
            pathToChrome = "drivers/mac/chromedriver";
        } else {
            pathToChrome = "drivers/ubuntu/chromedriver";
        }

        System.setProperty("webdriver.chrome.driver", pathToChrome);
        return new ChromeDriver(opt);
    }


}
