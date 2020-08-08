package course.java.sdm.engine.Schema;

import java.util.Map;

public class Stores {

    Map<Integer, Store> stores;

    public Stores(Map<Integer, Store> stores) {
        this.stores = stores;
    }

    public Map<Integer, Store> getStores() {
        return stores;
    }

    public void setStores(Map<Integer, Store> stores) {
        this.stores = stores;
    }
}
