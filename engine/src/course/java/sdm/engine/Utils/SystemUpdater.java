package course.java.sdm.engine.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;

import course.java.sdm.engine.schema.Descriptor;
import course.java.sdm.engine.schema.Item;
import course.java.sdm.engine.schema.Location;
import course.java.sdm.engine.schema.systemModel.*;

public class SystemUpdater {

    private static SystemUpdater singletonSystemUpdater = null;

    private SystemUpdater () {
    }

    public static SystemUpdater getSystemUpdater () {
        if (singletonSystemUpdater == null) {
            singletonSystemUpdater = new SystemUpdater();
        }

        return singletonSystemUpdater;
    }

    /*
     * it will happen after user confirmation
     */

    public void completeTheOrder (SystemStore systemStore, Order newOrder, Descriptor descriptor) {
        calculateAllDetails(systemStore, newOrder);
        // add order to store
        systemStore.getOrders().add(newOrder);
        // add order to order collection in descriptor
        SystemOrder newSystemOrder = new SystemOrder(newOrder, systemStore.getStore().getName(), systemStore.getStore().getId());
        descriptor.getSystemOrders().put(newOrder.getOrderId(), newSystemOrder);
        // update counter of all store items that was included in order
        updateStoreAfterOrderCompletion(systemStore, newOrder);
        // update counter of all system items that was included in order
        updateSystemItemsAfterOrderCompletion(descriptor.getSystemItems(), newOrder);
    }

    private void calculateAllDetails (SystemStore systemStore, Order newOrder) {
        // to join the two first methods?
        calculateAmountOfItems(newOrder);
        calculateAllItemsPrice(newOrder);
        calculateDeliveryPrice(systemStore, newOrder);
        calculateTotalPrice(newOrder);
    }

    private void calculateAmountOfItems (Order newOrder) {
        int amountOfItems = 0;
        for (PricedItem pricedItem : newOrder.getAllOrderItems().keySet()) {
            amountOfItems += calculateNumOfItemsToAdd(newOrder, pricedItem);
        }

        newOrder.setAmountOfItems(amountOfItems);
    }

    private int calculateNumOfItemsToAdd (Order newOrder, PricedItem pricedItem) {
        int numOfItemsToAdd;
        if (pricedItem.getItem().getPurchaseCategory().equals(Item.PurchaseCategory.QUANTITY)) {
            numOfItemsToAdd = 1;
        }
        else {
            numOfItemsToAdd = newOrder.getAllOrderItems().get(pricedItem).intValue();
        }
        return numOfItemsToAdd;
    }

    private void calculateAllItemsPrice (Order newOrder) {
        double allItemPrices = 0;
        for (PricedItem pricedItem : newOrder.getAllOrderItems().keySet()) {
            allItemPrices += calculateTotalPriceForItem(newOrder, pricedItem);
        }

        BigDecimal bd = new BigDecimal(allItemPrices).setScale(2, RoundingMode.HALF_UP);
        newOrder.setAllItemsPrice(bd.doubleValue());
    }

    private double calculateTotalPriceForItem (Order newOrder, PricedItem pricedItem) {
        return pricedItem.getPrice() * newOrder.getAllOrderItems().get(pricedItem);
    }

    private void calculateDeliveryPrice (SystemStore systemStore, Order newOrder) {
        Location orderLocation = newOrder.getOrderLocation();
        Location storeLocation = systemStore.getStore().getLocation();
        double deliveryPrice = calculateDeliveryDistance(orderLocation.getX() - storeLocation.getX(),
                                                         orderLocation.getY() - storeLocation.getY())
                    * systemStore.getStore().getDeliveryPpk();
        newOrder.setDeliveryPrice(deliveryPrice);
    }

    private double calculateDeliveryDistance (int x, int y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    private void calculateTotalPrice (Order newOrder) {
        newOrder.setTotalPrice(newOrder.getAllItemsPrice() + newOrder.getDeliveryPrice());
    }

    private void updateStoreAfterOrderCompletion (SystemStore systemStore, Order newOrder) {
        Map<Integer, StoreItem> storeItems = systemStore.getStore().getItemIdToStoreItem();
        Map<PricedItem, Double> allOrderItemsMap = newOrder.getAllOrderItems();
        Set<PricedItem> orderItems = allOrderItemsMap.keySet();

        for (PricedItem pricedItem : orderItems) {
            int itemId = pricedItem.getItem().getId();

            if (storeItems.containsKey(itemId)) {
                StoreItem storeItem = storeItems.get(itemId);
                Integer prevNumOfPurchases = storeItem.getPurchasesCount();
                storeItem.setPurchasesCount(prevNumOfPurchases + allOrderItemsMap.get(pricedItem).intValue());
            }
        }
    }

    private void updateSystemItemsAfterOrderCompletion (Map<Integer, SystemItem> allSystemItems, Order newOrder) {
        Set<PricedItem> orderItems = newOrder.getAllOrderItems().keySet();
        for (PricedItem pricedItem : orderItems) {
            int itemId = pricedItem.getItem().getId();
            SystemItem systemItem = allSystemItems.get(itemId);
            systemItem.setOrdersCount(systemItem.getOrdersCount() + 1);
        }
    }
}
