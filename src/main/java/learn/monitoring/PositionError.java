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

    @Override
    public String toString() {
        return String.format("Error at id: %s etoroRef: %s timestamp: %s", id, position.getEtoroRef(), position.getDateTime().toString() );
    }
}
