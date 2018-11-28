package model;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    private String type = "real";
    private List<Asset> assets = new ArrayList<>();

    public void addAsset(Asset a) {
        assets.add(a);
    }

    @Override
    public String toString() {
        StringBuilder presentation = new StringBuilder();
        assets.forEach(presentation::append) ;
        return presentation.toString();
    }
}
