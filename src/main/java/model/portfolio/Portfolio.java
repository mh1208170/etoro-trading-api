package model.portfolio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {

    private String type = "real";
    private List<CopiedPortfolio> copiedPortfolios = new ArrayList<>();
    private List<Position> positions = new ArrayList<>();

    public void addCopiedPortfolio(CopiedPortfolio a) {
        copiedPortfolios.add(a);
    }

    public void addPosition(Position p) {
        positions.add(p);
    }

    @Override
    public String toString() {
        StringBuilder presentation = new StringBuilder();
        copiedPortfolios.forEach(presentation::append) ;
        positions.forEach(presentation::append);
        return presentation.toString();
    }
}
