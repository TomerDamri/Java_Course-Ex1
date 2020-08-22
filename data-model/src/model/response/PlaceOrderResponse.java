package model.response;

public class PlaceOrderResponse {
    private final Integer orderId;

    public PlaceOrderResponse(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }
}
