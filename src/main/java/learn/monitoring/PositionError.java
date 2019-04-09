package learn.monitoring;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PositionError {

    String id;
    AbstractPosition position;


    public PositionError(String id, AbstractPosition position) {
        this.id = id;
        this.position = position;
    }
}
