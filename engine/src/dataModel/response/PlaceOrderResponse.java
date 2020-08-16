package dataModel.response;

public class PlaceOrderResponse {
    private Integer orderId;

    public PlaceOrderResponse (Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderId () {
        return orderId;
    }

    public void setOrderId (Integer orderId) {
        this.orderId = orderId;
    }
}
