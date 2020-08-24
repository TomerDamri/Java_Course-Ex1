package course.java.sdm.engine.model;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

public class SystemOrdersHistory implements Serializable {

    private Map<UUID, SystemOrder> systemOrders;
    private Map<UUID, DynamicOrder> dynamicOrders;

    public SystemOrdersHistory () {
    }

    public SystemOrdersHistory (Map<UUID, SystemOrder> systemOrders, Map<UUID, DynamicOrder> dynamicOrders) {
        this.systemOrders = systemOrders;
        this.dynamicOrders = dynamicOrders;
    }

    public Map<UUID, DynamicOrder> getDynamicOrders () {
        return dynamicOrders;
    }

    public Map<UUID, SystemOrder> getSystemOrders () {
        return systemOrders;
    }
}