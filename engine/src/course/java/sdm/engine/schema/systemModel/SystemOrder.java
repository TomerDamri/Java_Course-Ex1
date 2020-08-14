package course.java.sdm.engine.schema.systemModel;

public class SystemOrder {

    private Order order;
    private final String StoreName;
    private final Integer storeId;

    public SystemOrder(Order order, String storeName, Integer storeId) {
        this.order = order;
        StoreName = storeName;
        this.storeId = storeId;
    }

    private void getNumOfItemsTypes () {
        order.getAllOrderItems().size();
    }
}
