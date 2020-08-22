package course.java.sdm.engine.model;

import java.time.LocalDateTime;
import java.util.Map;

public class SystemOrder {

    private final Order order;
    private final String storeName;
    private final Integer storeId;

    public SystemOrder (Order order, String storeName, Integer storeId) {
        this.order = order;
        this.storeName = storeName;
        this.storeId = storeId;
    }

    public Integer getId () {
        return order.getId();
    }

    public LocalDateTime getOrderDate () {
        return order.getOrderDate();
    }

    public Location getOrderLocation () {
        return order.getOrderLocation();
    }

    public Map<PricedItem, Double> getOrderItems () {
        return order.getPricedItems();
    }

    public Integer getNumOfItemTypes () {
        return order.getNumOfItemTypes();
    }

    public Integer getAmountOfItems () {
        return order.getAmountOfItems();
    }

    public Double getItemsPrice () {
        return order.getItemsPrice();
    }

    public Double getDeliveryPrice () {
        return order.getDeliveryPrice();
    }

    public Double getTotalPrice () {
        return order.getTotalPrice();
    }

    public String getStoreName () {
        return storeName;
    }

    public Integer getStoreId () {
        return storeId;
    }

    @Override
    public String toString () {
        return order.toString() + ",\nStore id: " + storeId + ",\nStore Name: " + storeName;
    }
}
