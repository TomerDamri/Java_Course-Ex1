package course.java.sdm.engine.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {

    private final Integer id;
    private final LocalDateTime orderDate;
    private final Location orderLocation;
    private Map<PricedItem, Double> pricedItems;
    private Integer numOfItemTypes;
    private Integer amountOfItems;
    private Double itemsPrice;
    private Double deliveryPrice;
    private Double totalPrice;

    public Order (Integer orderId, LocalDateTime orderDate, Location orderLocation) {
        this.id = orderId;
        this.orderDate = orderDate;
        this.orderLocation = orderLocation;
        this.pricedItems = new HashMap<>();
    }

    public Double getTotalPrice () {
        return totalPrice;
    }

    public void setTotalPrice (Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDeliveryPrice () {
        return deliveryPrice;
    }

    public void setDeliveryPrice (Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Double getItemsPrice() {
        return itemsPrice;
    }

    public void setItemsPrice(Double itemsPrice) {
        this.itemsPrice = itemsPrice;
    }

    public Integer getId () {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Location getOrderLocation () {
        return orderLocation;
    }

    public Map<PricedItem, Double> getPricedItems() {
        return pricedItems;
    }

    public Integer getNumOfItemTypes () {
        return numOfItemTypes;
    }

    public void setNumOfItemTypes (Integer numOfItemTypes) {
        this.numOfItemTypes = numOfItemTypes;
    }

    public Integer getAmountOfItems () {
        return amountOfItems;
    }

    public void setAmountOfItems (Integer amountOfItems) {
        this.amountOfItems = amountOfItems;
    }

    @Override
    public String toString () {
        return new StringBuilder().append("Order id: ")
                                  .append(id)
                                  .append(",\nDate: ")
                                  .append(orderDate)
                                  .append(",\nNumber of item types: ")
                                  .append(numOfItemTypes)
                                  .append(",\nTotal number of items: ")
                                  .append(amountOfItems)
                                  .append(",\nTotal items cost: ")
                                  .append(itemsPrice)
                                  .append(",\nDelivery price: ")
                                  .append(deliveryPrice)
                                  .append(",\nTotal price of the order: ")
                                  .append(totalPrice)
                                  .toString();

    }
}
