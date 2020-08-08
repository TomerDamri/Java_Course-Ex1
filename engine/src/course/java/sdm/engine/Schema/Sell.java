package course.java.sdm.engine.Schema;

public class Sell {

    private int price;
    private int itemId;

    public Sell(int price, int itemId) {
        this.price = price;
        this.itemId = itemId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

//    @Override
//    public String toString() {
//        return "Sell{" +
//                "price=" + price +
//                ", itemId=" + itemId +
//                '}';
//    }
}
