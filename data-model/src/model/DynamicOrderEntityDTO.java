package model;

public class DynamicOrderEntityDTO {

    private final Integer storeId;
    private final String storeName;
    private final int xCoordinate;
    private final int yCoordinate;
    private final Double distanceFromCustomerLocation;
    private final Integer ppk;
    private final Integer totalItemTypes;
    private final Double totalDeliveryPrice;
    private final Double totalPrice;

    public DynamicOrderEntityDTO(Integer storeId,
                                 String storeName,
                                 int xCoordinate,
                                 int yCoordinate,
                                 Double distanceFromCustomerLocation,
                                 Integer ppk,
                                 Integer totalItemTypes,
                                 Double totalDeliveryPrice,
                                 Double totalPrice) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.distanceFromCustomerLocation = distanceFromCustomerLocation;
        this.ppk = ppk;
        this.totalItemTypes = totalItemTypes;
        this.totalDeliveryPrice = totalDeliveryPrice;
        this.totalPrice = totalPrice;
    }

    public final Integer getStoreId () {
        return storeId;
    }

    public final String getStoreName () {
        return storeName;
    }

    public int getX_Coordinate() {
        return xCoordinate;
    }

    public int getY_Coordinate() {
        return yCoordinate;
    }

    public final Double getDistanceFromCustomerLocation () {
        return distanceFromCustomerLocation;
    }

    public final Integer getPpk () {
        return ppk;
    }

    public final Integer getTotalItemTypes () {
        return totalItemTypes;
    }

    public final Double getTotalDeliveryPrice () {
        return totalDeliveryPrice;
    }

    public final Double getTotalPrice () {
        return totalPrice;
    }
}