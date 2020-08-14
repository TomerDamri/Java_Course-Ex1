package course.java.sdm.engine.schema.systemModel;

import java.time.LocalDateTime;
import java.util.Map;

import course.java.sdm.engine.schema.Location;

public class Order {

    private final Integer orderId;
    private final LocalDateTime orderDate;
    private final Location orderLocation;
    //consider changing the key
    private Map<PricedItem, Double> allOrderItems;
    private Integer numOfItemTypes;
    private Integer amountOfItems;
    private Double allItemsPrice;
    private Double deliveryPrice;
    private Double totalPrice;

    public Order (Integer orderId, LocalDateTime orderDate, Location orderLocation) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderLocation = orderLocation;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Double getAllItemsPrice() {
        return allItemsPrice;
    }

    public void setAllItemsPrice(Double allItemsPrice) {
        this.allItemsPrice = allItemsPrice;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Location getOrderLocation() {
        return orderLocation;
    }

    public Map<PricedItem, Double> getAllOrderItems() {
        return allOrderItems;
    }

    public Integer getNumOfItemTypes() {
        return numOfItemTypes;
    }

    public void setNumOfItemTypes(Integer numOfItemTypes) {
        this.numOfItemTypes = numOfItemTypes;
    }

    public Integer getAmountOfItems() {
        return amountOfItems;
    }

    public void setAmountOfItems(Integer amountOfItems) {
        this.amountOfItems = amountOfItems;
    }
}
