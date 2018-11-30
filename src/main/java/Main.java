import bootstrap.Initializer;
import controllers.EtoroPortfolioController;
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

        EtoroPortfolioController controller = new EtoroPortfolioController(driver, "https://www.etoro.com/people/alnayef/portfolio");

        Portfolio pf = controller.getPortfolio();
        controller.setMode("virtual");
        Thread.sleep(200);

        while (true) {
            System.out.println(pf);
            pf = controller.getPortfolio();

        }

    }
}
