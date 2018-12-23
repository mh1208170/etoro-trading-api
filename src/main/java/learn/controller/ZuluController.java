package learn.controller;

import learn.monitoring.zuulu.ZuluPortfolio;
import learn.monitoring.zuulu.ZuluService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ZuluController {

    @Autowired
    private ZuluService zuluService;

    @RequestMapping(value = "/zulu", method = RequestMethod.POST)
    public ResponseEntity addNewZuluPortfolio(@RequestBody ZuluPortfolio p) {
        zuluService.addPortfolio(p);
        log.info("Adding new zulu trader " + p.getId());
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/zulu", method = RequestMethod.DELETE)
    public ResponseEntity deleteZuluPortfolio(@RequestBody ZuluPortfolio p) {
        zuluService.deletePortfolio(p);
        log.info("Removing zulu trader " + p.getId());
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/zulu/open/portfolios", method = RequestMethod.GET)
    public List<ZuluPortfolio> getPortfoliosInfo() {
        return zuluService.getPortfolios();
    }
}
