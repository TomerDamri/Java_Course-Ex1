package course.java.sdm.engine.schema.systemModel;

import course.java.sdm.engine.schema.Item;

public class SystemItem {

    private Item item;
    private int storesCount;
    private double avgPrice;
    private int ordersCount;

    public SystemItem (Item item) {
        this.item = item;
        this.storesCount = 0;
        this.avgPrice = 0;
        this.ordersCount = 0;
    }

    public int getStoresCount () {
        return storesCount;
    }

    public Item getItem () {
        return item;
    }

    public int getId () {
        return item.getId();
    }

    public String getName () {
        return item.getName();
    }

    public Item.PurchaseCategory getPurchaseCategory () {
        return item.getPurchaseCategory();
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
        return new StringBuilder().append("\n{")
                .append(item.toString())
                                  .append(",\nNumber of stores supplied in: ")
                                  .append(storesCount)
                                  .append(",\nAverage price: ")
                                  .append(avgPrice)
                                  .append(",\nNumber of purchases: ")
                                  .append(ordersCount)
                                  .append("}")
                                  .toString();
    }
}
