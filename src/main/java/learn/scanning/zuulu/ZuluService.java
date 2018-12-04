package learn.scanning.zuulu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ZuluService {

    @Autowired
    private ZuluClient client;

    public List<ZuluPosition> getPortfolios(String id) {
        return client.getOpenPositions(id);
    }
}
