package dataModel.response;

import java.util.Map;

import course.java.sdm.engine.schema.systemModel.SystemItem;

public class GetItemsResponse {
    private Map<Integer, SystemItem> items;

    public GetItemsResponse (Map<Integer, SystemItem> items) {
        this.items = items;
    }

    public Map<Integer, SystemItem> getItems () {
        return items;
    }

    public void setItems (Map<Integer, SystemItem> items) {
        this.items = items;
    }
}
