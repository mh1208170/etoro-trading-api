package learn.scanning.zuulu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZuluService {

    @Autowired
    private ZuluClient client;

    public List<ZuluPosition> getPositions(String id) {
        return client.getOpenPositions(id);
    }
}
