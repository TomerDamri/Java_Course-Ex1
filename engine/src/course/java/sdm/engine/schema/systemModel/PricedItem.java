package course.java.sdm.engine.schema.systemModel;

import course.java.sdm.engine.schema.Item;

public class PricedItem {
    private final Item item;
    private int price;

    public PricedItem(Item item, int price) {
        this.item = item;
        this.price = price;
    }

    public Item getItem () {
        return item;
    }

    public int getPrice () {
        return price;
    }

    public void setPrice (int price) {
        this.price = price;
    }

    @Override
    public String toString () {
        return new StringBuilder().append(item.toString()).append(",\nPrice in store: ").append(price).append("\n").toString();
    }
}
