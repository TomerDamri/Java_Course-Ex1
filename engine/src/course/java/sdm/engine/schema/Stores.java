package course.java.sdm.engine.schema;

import java.util.Map;

public class Stores {

    Map<Integer, Store> stores;

    public Stores (Map<Integer, Store> stores) {
        this.stores = stores;
    }

    public Map<Integer, Store> getStores () {
        return stores;
    }

    public void setStores (Map<Integer, Store> stores) {
        this.stores = stores;
    }

    // TODO: 07/08/2020 - change the toString implementation
    @Override
    public String toString () {
        StringBuilder stringBuilder = new StringBuilder("Stores: [\n");
        for (Store store : stores.values()) {
            stringBuilder.append(store.toString()).append(",\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}
