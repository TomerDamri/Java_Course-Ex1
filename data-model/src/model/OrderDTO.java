package model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class OrderDTO {

    private StoreOrderDTO storeOrderDTO;
    private UUID id;
    private LocalDateTime orderDate;
    private int xCoordinate;
    private int yCoordinate;
    private Map<Integer, Double> pricedItems;
    private Integer numOfItemTypes;
    private Integer amountOfItems;
    private Double itemsPrice;
    private Double deliveryPrice;
    private Double totalPrice;
    private String storeName;
    private Integer storeId;

    public OrderDTO (UUID id,
                     LocalDateTime orderDate,
                     int xCoordinate,
                     int yCoordinate,
                     Map<Integer, Double> pricedItems,
                     Integer numOfItemTypes,
                     Integer amountOfItems,
                     Double itemsPrice,
                     Double deliveryPrice,
                     Double totalPrice,
                     String storeName,
                     Integer storeId) {
        this.id = id;
        this.orderDate = orderDate;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.pricedItems = pricedItems;
        this.numOfItemTypes = numOfItemTypes;
        this.amountOfItems = amountOfItems;
        this.itemsPrice = itemsPrice;
        this.deliveryPrice = deliveryPrice;
        this.totalPrice = totalPrice;
        this.storeName = storeName;
        this.storeId = storeId;
    }

    public UUID getId () {
        return id;
    }

    public LocalDateTime getOrderDate () {
        return orderDate;
    }

    public int getxCoordinate () {
        return xCoordinate;
    }

    public int getyCoordinate () {
        return yCoordinate;
    }

    public Map<Integer, Double> getPricedItems () {
        return pricedItems;
    }

    public Integer getNumOfItemTypes () {
        return numOfItemTypes;
    }

    public Integer getAmountOfItems () {
        return amountOfItems;
    }

    public Double getItemsPrice () {
        return itemsPrice;
    }

    public Double getDeliveryPrice () {
        return deliveryPrice;
    }

    public Double getTotalPrice () {
        return totalPrice;
    }

    public String getStoreName () {
        return storeName;
    }

    public Integer getStoreId () {
        return storeId;
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
                                  .append(",\nStore id: ")
                                  .append(storeId)
                                  .append(",\nStore Name: ")
                                  .append(storeName)
                                  .toString();
    }

}
