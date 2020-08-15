package course.java.sdm.engine.schema;

public class Item {

    public enum PurchaseCategory {
        QUANTITY, WEIGHT;

        private static PurchaseCategory createPurchaseCategory (String purchaseCategoryStr) {
            switch (purchaseCategoryStr) {
            case "Quantity":
                return QUANTITY;
            case "Weight":
                return WEIGHT;
            }

            throw new IllegalArgumentException(String.format("purchase category should be %s or %s and not %s",
                                                             QUANTITY,
                                                             WEIGHT,
                                                             purchaseCategoryStr));
        }
    }

    private int id;
    private String name;
    private PurchaseCategory purchaseCategory;

    public Item (String name, String purchaseCategory, int id) {
        this.id = id;
        this.name = name;
        this.purchaseCategory = PurchaseCategory.createPurchaseCategory(purchaseCategory);
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

    public PurchaseCategory getPurchaseCategory () {
        return purchaseCategory;
    }

    // todo: update toString with the new members if exist
    @Override
    public String toString () {
        return new StringBuilder().append("Id: ")
                                  .append(id)
                                  .append(",\nName: ")
                                  .append(name)
                                  .append(",\nPurchase Category: ")
                                  .append(purchaseCategory)
                                  .toString();

    }

}
