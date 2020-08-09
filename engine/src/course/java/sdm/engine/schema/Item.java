package course.java.sdm.engine.schema;

public class Item {

    private int id;
    private String name;
    private String purchaseCategory;

    public Item (String name, String purchaseCategory, int id) {
        this.id = id;
        this.name = name;
        this.purchaseCategory = purchaseCategory;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getPurchaseCategory () {
        return purchaseCategory;
    }

    public void setPurchaseCategory (String purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    // todo: update toString with the new members if exist
    @Override
    public String toString () {
        return "Id: " + id + ",\nName: " + name + ",\nPurchase Category: " + purchaseCategory;
    }

}
