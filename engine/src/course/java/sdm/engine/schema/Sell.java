package course.java.sdm.engine.schema;

public class Sell {

    private int price;
    private int itemId;

    public Sell (int price, int itemId) {
        this.price = price;
        this.itemId = itemId;
    }

    public int getPrice () {
        return price;
    }

    public void setPrice (int price) {
        this.price = price;
    }

    public int getItemId () {
        return itemId;
    }

    @Override
    public String toString () {
        return new StringBuilder("Sell item: \n{item Id: ").append(itemId).append(" price: ").append(price).append("}\n").toString();
    }
}
