package learn.monitoring.etoro;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class EtoroInstrumenIdConverter {

    private Map<String, String> idMapping = new HashMap<>();

    @PostConstruct
    public void init() {
        idMapping.put("","");

    }


    public String getNameByInstrumentId(String instrumentId) {
        return idMapping.get(instrumentId);
    }
}
