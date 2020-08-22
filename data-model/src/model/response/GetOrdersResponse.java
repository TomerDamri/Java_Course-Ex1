package model.response;

import java.util.Map;

import model.OrderDTO;

public class GetOrdersResponse {

    private final Map<Integer, OrderDTO> orders;

    public GetOrdersResponse (Map<Integer, OrderDTO> orders) {
        this.orders = orders;
    }

    public Map<Integer, OrderDTO> getOrders () {
        return orders;
    }
}
