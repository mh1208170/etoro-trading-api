package learn.controller;


import learn.monitoring.etoro.Portfolio;
import learn.monitoring.etoro.EtoroPortfolioScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EtoroPortfolioController {

    @Autowired
    private EtoroPortfolioScanner scanner;


    @RequestMapping(value = "/portfolio/{trader}", method = RequestMethod.GET)
    public Portfolio getPortfolioInfo(@PathVariable("trader") String trader) throws InterruptedException {
        return scanner.getPortfolio("people/" + trader +"/portfolio");
    }


}
