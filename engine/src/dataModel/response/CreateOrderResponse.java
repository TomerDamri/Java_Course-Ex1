package dataModel.response;

import course.java.sdm.engine.schema.systemModel.Order;

public class CreateOrderResponse {
    private Order order;
//todo: consider change to SystemOrder
    public CreateOrderResponse (Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
