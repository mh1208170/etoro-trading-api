package learn.controller;

import learn.order.EtoroOrderExecuter;
import learn.order.Order;
import learn.history.OrderHistory;
import learn.history.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    EtoroOrderExecuter executer;

    @Autowired
    HistoryService historyService;

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseEntity createOrder(@RequestBody Order o) throws InterruptedException {
        Thread.sleep(1000);
        return ResponseEntity.ok().body( executer.doOrder(o));
    }

    @RequestMapping(value = "/position/{id}/{name}", method = RequestMethod.DELETE)
    public ResponseEntity deletePositionById(@PathVariable("id") String id, @PathVariable("name") String name) throws InterruptedException {
        boolean res = executer.closePositionById(id, name);
        Thread.sleep(1000);
        return ResponseEntity.ok().body(res);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public List<OrderHistory> getHistory() {
        return historyService.getHistory();
    }
}
