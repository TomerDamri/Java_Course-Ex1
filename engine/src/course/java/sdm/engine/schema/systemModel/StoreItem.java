package course.java.sdm.engine.schema.systemModel;

import course.java.sdm.engine.schema.Item;

public class StoreItem {
    private PricedItem pricedItem;
    private int purchasesCount;

    public StoreItem (Item item, Integer price) {
        this.pricedItem = new PricedItem(item, price);
        this.purchasesCount = 0;
    }

    public int getPrice () {
        return pricedItem.getPrice();
    }

    public void setPrice (int price) {
        pricedItem.setPrice(price);
    }

    public int getPurchasesCount () {
        return purchasesCount;
    }

    public void setPurchasesCount (int purchasesCount) {
        this.purchasesCount = purchasesCount;
    }

    @Override
    public String toString () {
        return new StringBuilder().append("\n{")
                .append(pricedItem.toString())
                                  .append(",\nNumber of purchases in store: ")
                                  .append(purchasesCount)
                                  .append("}")
                                  .toString();
    }
}
