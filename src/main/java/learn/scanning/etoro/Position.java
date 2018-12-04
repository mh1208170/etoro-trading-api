package learn.scanning.etoro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Position {

    String type;
    String name;
    BigDecimal ammount;
    String leverage;
    BigDecimal open;
    LocalDateTime openTime;
    LocalDateTime lastUpdated;
    BigDecimal sl;
    BigDecimal tp;
    BigDecimal profitPr;


    public void parseInfo(String info) {
        String split[] = info.split("\n");
        String nameSplit[] = split[0].split(" ");
        this.setName(nameSplit[1]);
        this.setType(nameSplit[0]);
        this.setOpenTime(LocalDateTime.parse(split[1], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        String valueSplit[] = split[2].split(" ");
        this.setAmmount(new BigDecimal(valueSplit[0].replaceAll("%", "")));
        this.setLeverage(valueSplit[1]);
        this.setProfitPr(new BigDecimal(valueSplit[5].replaceAll("%", "")));

    }

    @Override
    public String toString() {
        return String.format("------- %s %s %s %s ------\n", name, type, ammount, profitPr);
    }
}

