package course.java.sdm.engine.model;

import java.util.Map;

public class DynamicOrder {

    private int orderId;
    private boolean isConfirmed;
    private Map<StoreDetails, Order> staticOrders;

    public DynamicOrder (int orderId, Map<StoreDetails, Order> staticOrders) {
        this.orderId = orderId;
        this.staticOrders = staticOrders;
        this.isConfirmed = false;
    }

    public int getOrderId () {
        return orderId;
    }

    public Map<StoreDetails, Order> getStaticOrders () {
        return staticOrders;
    }

    public boolean isConfirmed () {
        return isConfirmed;
    }

    public void setConfirmed (boolean confirmed) {
        isConfirmed = confirmed;
    }
}