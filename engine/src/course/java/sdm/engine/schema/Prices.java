package course.java.sdm.engine.schema;

import java.util.Map;

public class Prices {

    private Map<Integer, Sell> sells;

    public Prices (Map<Integer, Sell> sells) {
        this.sells = sells;
    }

    public Map<Integer, Sell> getSells () {
        return sells;
    }

    @Override
    public String toString () {
        return new StringBuilder().append("Sell Items: ").append(sells.values()).append("\n").toString();
    }
}
