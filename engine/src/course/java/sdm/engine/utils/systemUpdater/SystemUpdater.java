package course.java.sdm.engine.utils.systemUpdater;

import java.util.Map;
import java.util.Set;

import course.java.sdm.engine.model.*;

public class SystemUpdater {

    private static SystemUpdater singletonSystemUpdater = null;
    private final static SystemUpdaterValidator SYSTEM_UPDATER_VALIDATOR = new SystemUpdaterValidator();

    private SystemUpdater () {
    }

    public static SystemUpdater getSystemUpdater () {
        if (singletonSystemUpdater == null) {
            singletonSystemUpdater = new SystemUpdater();
        }

        return singletonSystemUpdater;
    }

    public void updateSystemAfterDynamicOrder (int dynamicOrderId, boolean toConfirmNewDynamicOrder, Descriptor descriptor) {
        Map<Integer, DynamicOrder> dynamicOrders = descriptor.getDynamicOrders();
        SYSTEM_UPDATER_VALIDATOR.validateDynamicOrderExist(dynamicOrderId, dynamicOrders);

        DynamicOrder dynamicOrder = dynamicOrders.get(dynamicOrderId);
        SYSTEM_UPDATER_VALIDATOR.validateDynamicOrderNotConfirmedYet(dynamicOrderId, dynamicOrder);

        if (toConfirmNewDynamicOrder) {
            dynamicOrder.setConfirmed(toConfirmNewDynamicOrder);
            dynamicOrder.getStaticOrders().forEach( (key, value) -> {
                SystemStore systemStore = descriptor.getSystemStores().get(key.getId());
                updateSystemAfterStaticOrder(systemStore, value, descriptor);
            });
        }
        else {
            dynamicOrders.remove(dynamicOrderId);
        }
    }

    public void updateSystemAfterStaticOrder (SystemStore systemStore, Order newOrder, Descriptor descriptor) {
        updateSystemStore(systemStore, newOrder);
        // add order to store
        systemStore.getOrders().add(newOrder);
        // add order to order collection in descriptor
        SystemOrder newSystemOrder = new SystemOrder(newOrder, systemStore.getName(), systemStore.getId());
        descriptor.getSystemOrders().put(newOrder.getId(), newSystemOrder);
        // update counter of all store items that was included in order
        updateStoreAfterOrderCompletion(systemStore, newOrder);
        // update counter of all system items that was included in order
        updateSystemItemsAfterOrderCompletion(descriptor.getSystemItems(), newOrder);
    }

    private void updateSystemStore (SystemStore systemStore, Order newOrder) {
        updateDeliveriesPayment(systemStore, newOrder);
    }

    private void updateDeliveriesPayment (SystemStore systemStore, Order newOrder) {
        systemStore.setTotalDeliveriesPayment(systemStore.getTotalDeliveriesPayment() + newOrder.getDeliveryPrice());
    }

    private void updateStoreAfterOrderCompletion (SystemStore systemStore, Order newOrder) {
        Map<Integer, StoreItem> storeItems = systemStore.getItemIdToStoreItem();
        Map<PricedItem, Double> allOrderItemsMap = newOrder.getPricedItems();
        Set<PricedItem> orderItems = allOrderItemsMap.keySet();

        for (PricedItem pricedItem : orderItems) {
            int itemId = pricedItem.getId();

            if (storeItems.containsKey(itemId)) {
                StoreItem storeItem = storeItems.get(itemId);
                int prevNumOfPurchases = storeItem.getPurchasesCount();
                storeItem.setPurchasesCount(prevNumOfPurchases + allOrderItemsMap.get(pricedItem).intValue());
            }
        }
    }

    private void updateSystemItemsAfterOrderCompletion (Map<Integer, SystemItem> allSystemItems, Order newOrder) {
        Set<PricedItem> orderItems = newOrder.getPricedItems().keySet();
        for (PricedItem pricedItem : orderItems) {
            int itemId = pricedItem.getId();
            SystemItem systemItem = allSystemItems.get(itemId);
            int numOfItemsToCount = pricedItem.getPurchaseCategory().equals(Item.PurchaseCategory.QUANTITY)
                        ? newOrder.getPricedItems().get(pricedItem).intValue()
                        : 1;
            systemItem.setOrdersCount(systemItem.getOrdersCount() + numOfItemsToCount);
        }
    }
}