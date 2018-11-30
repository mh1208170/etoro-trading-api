import bootstrap.Initializer;
import controllers.EtoroPortfolioScreen;
import model.portfolio.Portfolio;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(opt);
        Initializer initializer = new Initializer(driver, args[0], args[1]);
        initializer.init();
       // initializer.login();

        EtoroPortfolioScreen controller = new EtoroPortfolioScreen(driver, "https://www.etoro.com/people/alnayef/portfolio");

        Portfolio pf = controller.getPortfolio();
//        controller.setMode("virtual");


        while (true) {
            Thread.sleep(300);
            System.out.println(pf);
            pf = controller.getPortfolio();


        }

    }
}
