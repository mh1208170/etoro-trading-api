package learn.monitoring;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractPosition {
    protected String id;
    protected String tradeType;
    protected Date dateTime;
    protected String etoroRef;
}
