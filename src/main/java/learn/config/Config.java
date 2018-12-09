package learn.config;

import learn.auth.Authentication;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"learn.monitoring.zuulu"})
public class Config {

    @Autowired
    Authentication authentication;

    private ChromeOptions initDriver() {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--window-size=1920,1920");
        String pathToChrome;
        if (System.getProperty("os.name").startsWith("Mac")) {
            pathToChrome = "drivers/mac/chromedriver";
        } else {
            pathToChrome = "drivers/ubuntu/chromedriver";
        }

        System.setProperty("webdriver.chrome.driver", pathToChrome);
        return opt;
    }


    @Bean
    public WebDriver driver() {
        return new ChromeDriver(initDriver());
    }

    @Bean(name = "authorizedDriver")
    public WebDriver authorizedDriver() throws InterruptedException {
        WebDriver driver = new ChromeDriver(initDriver());
        authentication.login(driver);
        return driver;

    }


}
