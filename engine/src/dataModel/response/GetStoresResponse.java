package dataModel.response;

import java.util.Map;

import course.java.sdm.engine.schema.systemModel.SystemStore;

public class GetStoresResponse {
    private Map<Integer, SystemStore> stores;

    public GetStoresResponse (Map<Integer, SystemStore> stores) {
        this.stores = stores;
    }

    public Map<Integer, SystemStore> getStores () {
        return stores;
    }

    public void setStores (Map<Integer, SystemStore> stores) {
        this.stores = stores;
    }
}
