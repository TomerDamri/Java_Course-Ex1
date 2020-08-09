package course.java.sdm.engine.schema;

import java.util.Map;

public class Prices {

    private Map<Integer, Sell> sells;

    public Prices(Map<Integer, Sell> sells) {
        this.sells = sells;
    }

    public Map<Integer, Sell> getSells() {
        return sells;
    }

    public void setSells(Map<Integer, Sell> sells) {
        this.sells = sells;
    }
}
