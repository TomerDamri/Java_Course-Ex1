package course.java.sdm.engine.utils.systemUpdater;

import java.util.Map;

import course.java.sdm.engine.model.DynamicOrder;

public class SystemUpdaterValidator {

    public void validateDynamicOrderExist (int dynamicOrderId, Map<Integer, DynamicOrder> dynamicOrders) {
        if (!dynamicOrders.containsKey(dynamicOrderId)) {
            throw new RuntimeException(String.format("There is no order with ID: %s", dynamicOrderId));
        }
    }

    public void validateDynamicOrderNotConfirmedYet (int dynamicOrderId, DynamicOrder dynamicOrder) {
        if (dynamicOrder.isConfirmed()) {
            throw new RuntimeException(String.format("The dynamic order with ID: %s already completed", dynamicOrderId));
        }
    }
}