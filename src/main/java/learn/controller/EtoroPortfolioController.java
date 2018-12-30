package learn.controller;


import learn.monitoring.etoro.EtoroPortfolio;
import learn.monitoring.etoro.EtoroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class EtoroPortfolioController {

    @Autowired
    private EtoroService etoroService;

    @RequestMapping(value = "/portfolios", method = RequestMethod.GET)
    public List<EtoroPortfolio> getMyPortfolios() {
        return etoroService.getPortfolios();
    }

    @RequestMapping(value = "/portfolio", method = RequestMethod.POST)
    public EtoroPortfolio addPortfolio(EtoroPortfolio p) {
        return etoroService.addPortfolio(p.getId());
    }

    @RequestMapping(value = "/portfolio", method = RequestMethod.DELETE)
    public ResponseEntity deleteMyPortfolio(EtoroPortfolio p) {
        etoroService.removePortfolio(p.getId());
        return ResponseEntity.ok(true);
    }

}
