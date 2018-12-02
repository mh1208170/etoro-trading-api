package learn.portfolio;

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
    String info;
    String type;
    String name;
    BigDecimal ammount;
    BigDecimal units;
    BigDecimal open;
    LocalDateTime openTime;
    BigDecimal close;
    LocalDateTime closeTime;
    LocalDateTime lastUpdated;
    BigDecimal sl;
    BigDecimal tp;
    BigDecimal profitPr;
    BigDecimal profit$;

    public Position(String s) {
        this.info = s;
        parseInfo(s);
    }

    private void parseInfo(String info) {
        String arr[] = info.split("\n");
        this.type = arr[0].split(" ")[0];
        this.name = arr[0].split(" ")[1];
        String posInfo[] = arr[2].split(" ");
        this.lastUpdated = LocalDateTime.parse(arr[1], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.info = arr[2];

    }

    @Override
    public String toString() {
        return String.format("------- %s %s %s ------\n", name, type, info);
    }
}

