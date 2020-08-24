package course.java.sdm.engine.model;

import java.io.Serializable;
import java.util.Map;

public class SystemOrdersHistory implements Serializable {

    private Map<Integer, SystemOrder> systemOrders;
    private Map<Integer, DynamicOrder> dynamicOrders;

    public SystemOrdersHistory () {
    }

    public SystemOrdersHistory (Map<Integer, SystemOrder> systemOrders, Map<Integer, DynamicOrder> dynamicOrders) {
        this.systemOrders = systemOrders;
        this.dynamicOrders = dynamicOrders;
    }

    public Map<Integer, DynamicOrder> getDynamicOrders () {
        return dynamicOrders;
    }

    public Map<Integer, SystemOrder> getSystemOrders () {
        return systemOrders;
    }
}