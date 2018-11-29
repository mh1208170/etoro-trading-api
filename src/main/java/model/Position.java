package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Position {
    BigDecimal ammount;
    BigDecimal units;
    BigDecimal open;
    LocalDateTime openTime;
    BigDecimal close;
    LocalDateTime closeTime;
    BigDecimal sl;
    BigDecimal tp;
    BigDecimal profitPr;
    BigDecimal profit$;
}

