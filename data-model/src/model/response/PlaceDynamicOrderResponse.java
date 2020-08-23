package model.response;

import java.util.List;

import model.DynamicOrderEntityDTO;

public class PlaceDynamicOrderResponse {

    private int id;
    private final List<DynamicOrderEntityDTO> dynamicOrderEntityDTO;

    public PlaceDynamicOrderResponse(int id, List<DynamicOrderEntityDTO> dynamicOrderEntityDTO) {
        this.id = id;
        this.dynamicOrderEntityDTO = dynamicOrderEntityDTO;
    }

    public int getId() {
        return id;
    }

    public List<DynamicOrderEntityDTO> getDynamicOrderEntity () {
        return dynamicOrderEntityDTO;
    }
}