import model.Portfolio;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        EtoroController controller = new EtoroController(new ChromeDriver(), args[0], args[1]);
        controller.init();
        Portfolio pf = controller.getPortfolio();


//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        String chromeProfile = "/home/oleg/.config/chromium/Default";
//        ArrayList<String> switches = new ArrayList<String>();
//        switches.add("--user-data-dir=" + chromeProfile);
//        capabilities.setCapability("chrome.switches", switches);

        while (true) {
            System.out.println(pf);
            pf = controller.getPortfolio();
            Thread.sleep(1000);
        }


    }
}
