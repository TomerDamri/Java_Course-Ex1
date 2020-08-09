package course.java.sdm.engine.schema.systemModel;

import java.util.List;

import course.java.sdm.engine.schema.Store;

public class SystemStore {

    private Store store;
    private List<StoreItem> storeItems;

    public Store getStore () {
        return store;
    }

    public void setStore (Store store) {
        this.store = store;
    }

    public List<StoreItem> getStoreItems () {
        return storeItems;
    }

    public void setStoreItems (List<StoreItem> storeItems) {
        this.storeItems = storeItems;
    }

    public SystemStore (Store store) {
        this.store = store;
    }

    @Override
    public String toString () {
        StringBuilder stringBuilder = new StringBuilder("Id: " + store.getId() + ",\nName: " + store.getName() + ",\nPPK: "
                    + store.getDeliveryPpk() + ",\nItems in store: [");
        for (StoreItem item : storeItems) {
            stringBuilder.append("\n{" + item.toString() + "},");
        }
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }

    // todo: add other parameters (orders, deliveries)
}
