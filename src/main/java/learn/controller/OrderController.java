package learn.controller;

import learn.order.EtoroOrderExecuter;
import learn.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    EtoroOrderExecuter executer;

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseEntity createOrder(@RequestBody Order o) throws InterruptedException {
        executer.doOrder(o);
        Thread.sleep(1000);
        return ResponseEntity.ok().body(true);
    }
}
