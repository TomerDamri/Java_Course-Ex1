package course.java.sdm.engine.schema.systemModel;

import course.java.sdm.engine.schema.Item;

public class SystemItem {

    private Item item;
    private int storesCount;
    private double avgPrice;
    private int ordersCount;

    public SystemItem (Item item) {
        this.item = item;
    }

    public int getStoresCount () {
        return storesCount;
    }

    public void setStoresCount (int storesCount) {
        this.storesCount = storesCount;
    }

    public double getAvgPrice () {
        return avgPrice;
    }

    public void setAvgPrice (double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public int getOrdersCount () {
        return ordersCount;
    }

    public void setOrdersCount (int ordersCount) {
        this.ordersCount = ordersCount;
    }

    @Override
    public String toString () {
        return item.toString() + ",\nNumber of stores supplied in: " + storesCount + ",\nAverage price: " + avgPrice
                    + ",\nNumber of purchases: " + ordersCount;
    }
}
