package learn.controller;

import learn.monitoring.zuulu.ZuluPortfolio;
import learn.monitoring.zuulu.ZuluService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZuluController {

    @Autowired
    private ZuluService zuluService;

    @RequestMapping(value = "/zulu", method = RequestMethod.POST)
    public ResponseEntity addNewZuluPortfolio(@RequestBody ZuluPortfolio p) {
        zuluService.addPortfolio(p);
        return ResponseEntity.ok(true);
    }
}
