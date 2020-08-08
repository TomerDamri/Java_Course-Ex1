package course.java.sdm.engine.Schema;

public class Item {

    private int id;
    private String name;
    private String purchaseCategory;

    private int storesCount;
    private double avgPrice;
    private int ordersCount;

    public Item(String name, String purchaseCategory, int id) {
        this.id = id;
        this.name = name;
        this.purchaseCategory = purchaseCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurchaseCategory() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(String purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    public int getStoresCount() {
        return storesCount;
    }

    public void setStoresCount(int storesCount) {
        this.storesCount = storesCount;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }

    //todo: update toString with the new members if exist
    @Override
    public String toString() {
        return
                "{ Id: " + id +
                        ",\nName: " + name +
                        ",\nPurchase Category: " + purchaseCategory +
                        " }";
    }

}
