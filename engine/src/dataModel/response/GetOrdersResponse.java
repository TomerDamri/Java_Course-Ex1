package dataModel.response;

import java.util.Map;

import course.java.sdm.engine.schema.systemModel.SystemOrder;

public class GetOrdersResponse {
    private Map<Integer, SystemOrder> orders;

    public GetOrdersResponse (Map<Integer, SystemOrder> orders) {
        this.orders = orders;
    }

    public Map<Integer, SystemOrder> getOrders () {
        return orders;
    }

    public void setOrders (Map<Integer, SystemOrder> orders) {
        this.orders = orders;
    }
}
