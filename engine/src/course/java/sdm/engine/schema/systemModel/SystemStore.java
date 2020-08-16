package course.java.sdm.engine.schema.systemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import course.java.sdm.engine.schema.Location;
import course.java.sdm.engine.schema.Store;

public class SystemStore {

    private Store store;
    private List<Order> orders;
    private double totalDeliveriesPayment = 0;

    public double getTotalDeliveriesPayment () {
        return totalDeliveriesPayment;
    }

    public void setTotalDeliveriesPayment (double totalDeliveriesPayment) {
        this.totalDeliveriesPayment = totalDeliveriesPayment;
    }

    public SystemStore (Store store) {
        this.store = store;
        this.orders = new ArrayList<>();
    }

    public int getId () {
        return store.getId();
    }

    public String getName () {
        return store.getName();
    }

    public int getDeliveryPpk () {
        return store.getDeliveryPpk();
    }

    public Location getLocation () {
        return store.getLocation();
    }

    public Map<Integer, StoreItem> getItemIdToStoreItem () {
        return store.getItemIdToStoreItem();
    }

    public List<Order> getOrders () {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        return orders;
    }

    @Override
    public String toString () {
        return store.toString() + ",\nStore Orders:" + orders.toString() + "'\nTotal deliveries payment:" + totalDeliveriesPayment;
    }
}
