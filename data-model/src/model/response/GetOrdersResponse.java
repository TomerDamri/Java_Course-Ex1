package model.response;

import java.util.Map;
import java.util.UUID;

import model.OrderDTO;

public class GetOrdersResponse {

    private final Map<UUID, OrderDTO> orders;

    public GetOrdersResponse (Map<UUID, OrderDTO> orders) {
        this.orders = orders;
    }

    public Map<UUID, OrderDTO> getOrders () {
        return orders;
    }
}
