package learn.portfolio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PositionGroup {

    private String name;

    private List<Position> positions = new ArrayList<>();

    public void addPosition(Position p) {
        positions.add(p);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        positions.forEach(sb::append);
        return sb.toString();
    }
}
