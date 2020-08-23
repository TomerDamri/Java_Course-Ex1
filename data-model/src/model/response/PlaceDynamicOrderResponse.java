package model.response;

import java.util.List;

import model.DynamicOrderEntityDTO;

public class PlaceDynamicOrderResponse {

    private List<DynamicOrderEntityDTO> dynamicOrderEntityDTO;

    public PlaceDynamicOrderResponse (List<DynamicOrderEntityDTO> dynamicOrderEntityDTO) {
        this.dynamicOrderEntityDTO = dynamicOrderEntityDTO;
    }

    public List<DynamicOrderEntityDTO> getDynamicOrderEntity () {
        return dynamicOrderEntityDTO;
    }
}