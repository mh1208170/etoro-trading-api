package learn;

import learn.portfolio.Portfolio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import learn.scanning.EtoroPortfolioScanner;

@SpringBootApplication
public class Application {


    public static void main(String[] args) throws InterruptedException {
        SpringApplication application = new SpringApplication(Application.class);
        ConfigurableApplicationContext ctx = application.run(args);
        EtoroPortfolioScanner etoroPortfolioScanner = ctx.getBean(EtoroPortfolioScanner.class);

        //EtoroPortfolioScanner controller = new EtoroPortfolioScanner(driver, "https://www.etoro.com/people/alnayef/portfolio");

//        Portfolio pf = etoroPortfolioScanner.getPortfolio();
//
//
//
//        while (true) {
//            Thread.sleep(300);
//            System.out.println(pf);
//            pf = etoroPortfolioScanner.getPortfolio();
//
//
//        }

    }
}
