package course.java.sdm.engine.Schema;

public class Item {

    private String name;
    private String purchaseCategory;
    private int id;

    public Item(String name, String purchaseCategory, int id) {
        this.name = name;
        this.purchaseCategory = purchaseCategory;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
