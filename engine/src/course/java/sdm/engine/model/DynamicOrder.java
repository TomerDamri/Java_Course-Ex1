package course.java.sdm.engine.model;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

public class DynamicOrder implements Serializable {

    private UUID orderId;
    private boolean isConfirmed;
    private Map<StoreDetails, Order> staticOrders;

    public DynamicOrder (Map<StoreDetails, Order> staticOrders) {
        this.orderId = UUID.randomUUID();
        this.staticOrders = staticOrders;
        this.isConfirmed = false;
    }

    public UUID getOrderId () {
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