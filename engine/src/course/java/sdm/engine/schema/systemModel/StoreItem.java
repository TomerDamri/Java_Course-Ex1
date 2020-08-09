package course.java.sdm.engine.schema.systemModel;

import course.java.sdm.engine.schema.Item;

public class StoreItem {
    private Item item;
    private int price;
    private int purchasesCount;

    public StoreItem(Item item) {
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPurchasesCount() {
        return purchasesCount;
    }

    public void setPurchasesCount(int purchasesCount) {
        this.purchasesCount = purchasesCount;
    }


    @Override
    public String toString() {
        return  item.toString() +
                ",\nPrice in store: " + price +
                ",\nNumber of purchases in store: " + purchasesCount;
    }
}
