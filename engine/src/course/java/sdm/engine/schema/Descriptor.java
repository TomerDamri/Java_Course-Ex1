package course.java.sdm.engine.schema;

import course.java.sdm.engine.schema.systemModel.SystemItem;
import course.java.sdm.engine.schema.systemModel.SystemOrder;
import course.java.sdm.engine.schema.systemModel.SystemStore;

import java.util.HashMap;
import java.util.Map;

public class Descriptor {

    private static Integer numOfOrders = 0;
    private Map<Integer, SystemStore> systemStores;
    private Map<Integer, SystemItem> systemItems;
    private Map<Integer, SystemOrder> systemOrders;

    public static Integer generateOrderId(){
        Integer num = numOfOrders;
        numOfOrders ++;
        return num;
    }

    public Descriptor(Map<Integer, SystemStore> systemStores, Map<Integer, SystemItem> systemItems) {
        this.systemStores = systemStores;
        this.systemItems = systemItems;
        this.systemOrders = new HashMap<>();
    }

    public Map<Integer, SystemStore> getSystemStores() {
        return systemStores;
    }

    public void setSystemStores(Map<Integer, SystemStore> systemStores) {
        this.systemStores = systemStores;
    }

    public Map<Integer, SystemItem> getSystemItems() {
        return systemItems;
    }

    public void setSystemItems(Map<Integer, SystemItem> systemItems) {
        this.systemItems = systemItems;
    }

    public Map<Integer, SystemOrder> getSystemOrders() {
        return systemOrders;
    }

    public void setSystemOrders(Map<Integer, SystemOrder> systemOrders) {
        this.systemOrders = systemOrders;
    }
}
