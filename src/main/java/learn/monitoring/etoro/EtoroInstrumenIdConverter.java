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
        // TODO add new InstrumentIDs
        idMapping.put("32","ger30");
        idMapping.put("28","nsdq100");
        idMapping.put("30","uk100");
        idMapping.put("36","jpn225");
        idMapping.put("27","spx500");
        idMapping.put("38","hkg50");
        idMapping.put("31","fra40");
        idMapping.put("43","eustx50");
        idMapping.put("34","esp35");
        idMapping.put("29","dj30");
        idMapping.put("26","china50");
        idMapping.put("33","aus200");
    }


    public String getNameByInstrumentId(String instrumentId) {
        return idMapping.get(instrumentId);
    }
}
