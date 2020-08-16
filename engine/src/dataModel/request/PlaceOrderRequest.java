package dataModel.request;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import course.java.sdm.engine.schema.Location;

public class PlaceOrderRequest {
    private int storeId;
    private Date orderDate;
    private Location orderLocation;
    private Map<Integer, Double> orderItemToAmount;

    public PlaceOrderRequest(int storeId, Date orderDate, Location orderLocation, Map<Integer, Double> orderItemToAmount) {
        this.storeId = storeId;
        this.orderDate = orderDate;
        this.orderLocation = orderLocation;
        this.orderItemToAmount = orderItemToAmount;
    }

    public int getStoreId () {
        return storeId;
    }

    public void setStoreId (int storeId) {
        this.storeId = storeId;
    }

    public Date getOrderDate () {
        return orderDate;
    }

    public void setOrderDate (Date orderDate) {
        this.orderDate = orderDate;
    }

    public Location getOrderLocation () {
        return orderLocation;
    }

    public void setOrderLocation (Location orderLocation) {
        this.orderLocation = orderLocation;
    }

    public Map<Integer, Double> getOrderItemToAmount () {
        return orderItemToAmount;
    }

    public void setOrderItemToAmount (Map<Integer, Double> orderItemToAmount) {
        this.orderItemToAmount = orderItemToAmount;
    }
}
