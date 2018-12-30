package learn.controller;

import learn.user.units.TradeUnitService;
import learn.user.units.TradeUnitsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradeUnitController {

    @Autowired
    private TradeUnitService tradeUnitService;

    @RequestMapping(value = "/trade/std/lotSize/{size}", method = RequestMethod.PUT)
    public ResponseEntity setStdLotSize(@PathVariable("size") Double size) {
        tradeUnitService.setStdLotSize(size);
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/trade/std/leverage/{leverage}", method = RequestMethod.PUT)
    public ResponseEntity setStdLeverage(@PathVariable("leverage") String leverage) {
        tradeUnitService.setStdLeverage(leverage);
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/trade/std/info", method = RequestMethod.GET)
    public TradeUnitsCounter getTradeUnitsCounter() {
        return tradeUnitService.getTradeUnitsCounter();
    }


}
