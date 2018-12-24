package learn.controller;

import learn.monitoring.zuulu.ZuluPortfolio;
import learn.monitoring.zuulu.ZuluPosition;
import learn.monitoring.zuulu.ZuluService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @RequestMapping(value = "/zulu/open/positions", method = RequestMethod.GET)
    public List<ZuluPosition> getOpenPositions() {
        List<ZuluPortfolio> portfolios = zuluService.getPortfolios();
        List<ZuluPosition> positions = new ArrayList<>();
        portfolios.forEach(p -> p.getPositionsMap().entrySet().forEach(e -> positions.add(e.getValue())));
        return positions.stream().filter(p -> p.getEtoroRef() != null).collect(Collectors.toList());
    }

    @RequestMapping(value = "/zulu/portfolio", method = RequestMethod.PUT)
    public ResponseEntity setFactorToPortfolio(@RequestBody ZuluPortfolio p) {
        zuluService.setFactor(p.getId(), p.getFactor());
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/zulu/portfolios", method = RequestMethod.GET)
    public List<ZuluPortfolio> getPortfolios() {
       return zuluService.getPortfolios();
    }
}
