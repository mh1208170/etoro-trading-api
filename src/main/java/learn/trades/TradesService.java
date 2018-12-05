package learn.trades;

import learn.controller.OrderController;
import learn.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TradesService {

    @Autowired
    OrderController orderController;

    private Map<String, EtoroTrade> trades = new HashMap<>();

    public void openTrade(Order o) throws InterruptedException {
        trades.put(o.getPositionId(), new EtoroTrade(o.getPositionId(), o.toString()));
        orderController.createOrder(o);
    }
}
