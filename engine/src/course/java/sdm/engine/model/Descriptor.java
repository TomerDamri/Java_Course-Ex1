package course.java.sdm.engine.model;

import java.util.Map;
import java.util.TreeMap;

public class Descriptor {

    private static Integer numOfOrders = 1;
    private static Integer numOfDynamicOrders = 1;
    private Map<Integer, SystemStore> systemStores;
    private Map<Integer, SystemItem> systemItems;
    private Map<Integer, SystemOrder> systemOrders;
    private Map<Integer, DynamicOrder> dynamicOrders;

    public static Integer generateStaticOrderId () {
        Integer num = numOfOrders;
        numOfOrders++;
        return num;
    }

    public static Integer generateDynamicOrderId () {
        Integer num = numOfDynamicOrders;
        numOfDynamicOrders++;
        return num;
    }

    public Descriptor (Map<Integer, SystemStore> systemStores, Map<Integer, SystemItem> systemItems) {
        this.systemStores = systemStores;
        this.systemItems = systemItems;
        this.systemOrders = new TreeMap<>();
        this.dynamicOrders = new TreeMap<>();
    }

    public Map<Integer, SystemStore> getSystemStores () {
        return systemStores;
    }

    public Map<Integer, SystemItem> getSystemItems () {
        return systemItems;
    }

    public Map<Integer, SystemOrder> getSystemOrders () {
        return systemOrders;
    }

    public Map<Integer, DynamicOrder> getDynamicOrders () {
        return dynamicOrders;
    }
}
