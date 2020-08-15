package course.java.sdm.engine.schema.systemModel;

import java.util.ArrayList;
import java.util.List;

import course.java.sdm.engine.schema.Store;

public class SystemStore {

    private Store store;
    private List<Order> orders;

    public SystemStore (Store store) {
        this.store = store;
        this.orders = new ArrayList<>();
    }

    public Store getStore () {
        return store;
    }

    public List<Order> getOrders () {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        return orders;
    }

    @Override
    public String toString () {
        return new StringBuilder()
                .append(store.toString())
                .append(",\nStore Orders:")
                .append(orders.toString()).toString();
    }
}
