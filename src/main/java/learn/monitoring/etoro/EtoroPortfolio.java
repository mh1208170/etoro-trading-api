package learn.monitoring.etoro;

import learn.monitoring.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EtoroPortfolio implements Portfolio {

    String id;

    private String type = "real";

    private List<EtoroPosition> positionGroups = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder presentation = new StringBuilder();
        presentation.append("-----Portfolio------\n");
        //copiedPortfolios.forEach(presentation::append) ;
        positionGroups.forEach(presentation::append);
        presentation.append("---------------------\n");
        return presentation.toString();
    }
}
