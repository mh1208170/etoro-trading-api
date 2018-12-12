package learn.monitoring.etoro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EtoroPositionGroup {

    private String instrumentId;
    private String name;
    private String type;


    private List<EtoroPosition> positions = new ArrayList<>();

    public void addPosition(EtoroPosition p) {
        positions.add(p);
    }

    public void parseInfo(String info) {
//        String split[] =info.split("\n");
//        this.setName(split[0]);
//        String type = (split[1].startsWith("Buying")) ? "buy": "sell";
//        this.setType(type);
//        String valueSplit[] = split[1].replaceAll("(Selling|Buying)", "").split("%");
//        this.setInvested(new BigDecimal( valueSplit[1]));
//        this.setProfitPr(new BigDecimal(valueSplit[2]));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        positions.forEach(sb::append);
        return sb.toString();
    }
}
