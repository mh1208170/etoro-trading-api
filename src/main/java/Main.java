import model.Portfolio;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--window-size=1920,1080");

        EtoroController controller = new EtoroController(new ChromeDriver(opt), args[0], args[1]);
        controller.init();
        Portfolio pf = controller.getPortfolio();
        controller.setMode("virtual");
        Thread.sleep(200);

        while (true) {
            System.out.println(pf);
            pf = controller.getPortfolio();

        }

    }
}
