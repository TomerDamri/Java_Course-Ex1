package course.java.sdm.engine.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;

import course.java.sdm.engine.mapper.Mapper;
import course.java.sdm.engine.model.*;

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

    public void updateSystem (SystemStore systemStore, Order newOrder, Descriptor descriptor) {
        completeTheOrder(systemStore, newOrder);
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

    private void completeTheOrder (SystemStore systemStore, Order newOrder) {
        setItemTypes(newOrder);
        setItemsAmount(newOrder);
        setItemsPrice(newOrder);
        setDeliveryPrice(systemStore, newOrder);
        setTotalPrice(newOrder);
    }

    private void updateDeliveriesPayment (SystemStore systemStore, Order newOrder) {
        systemStore.setTotalDeliveriesPayment(systemStore.getTotalDeliveriesPayment() + newOrder.getDeliveryPrice());
    }

    private void setItemTypes (Order newOrder) {
        newOrder.setNumOfItemTypes(newOrder.getPricedItems().size());
    }

    private void setItemsAmount (Order newOrder) {
        int amountOfItems = 0;
        for (PricedItem pricedItem : newOrder.getPricedItems().keySet()) {
            amountOfItems += calculateNumOfItemsToAdd(newOrder, pricedItem);
        }

        newOrder.setAmountOfItems(amountOfItems);
    }

    private int calculateNumOfItemsToAdd (Order newOrder, PricedItem pricedItem) {
        int numOfItemsToAdd;
        if (pricedItem.getPurchaseCategory().equals(Item.PurchaseCategory.WEIGHT)) {
            numOfItemsToAdd = 1;
        }
        else {
            numOfItemsToAdd = newOrder.getPricedItems().get(pricedItem).intValue();
        }
        return numOfItemsToAdd;
    }

    private void setItemsPrice (Order newOrder) {
        double allItemPrices = 0;
        for (PricedItem pricedItem : newOrder.getPricedItems().keySet()) {
            allItemPrices += calculateTotalPriceForItem(newOrder, pricedItem);
        }

        BigDecimal bd = new BigDecimal(allItemPrices).setScale(2, RoundingMode.HALF_UP);
        newOrder.setItemsPrice(bd.doubleValue());
    }

    private double calculateTotalPriceForItem (Order newOrder, PricedItem pricedItem) {
        return pricedItem.getPrice() * newOrder.getPricedItems().get(pricedItem);
    }

    private void setDeliveryPrice (SystemStore systemStore, Order newOrder) {
        Location orderLocation = newOrder.getOrderLocation();
        Location storeLocation = systemStore.getLocation();
        double deliveryPrice = calculateDeliveryDistance(orderLocation.getX() - storeLocation.getX(),
                                                         orderLocation.getY() - storeLocation.getY())
                    * systemStore.getDeliveryPpk();
        deliveryPrice = Mapper.round(deliveryPrice, 2);
        newOrder.setDeliveryPrice(deliveryPrice);
    }

    private double calculateDeliveryDistance (int x, int y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    private void setTotalPrice (Order newOrder) {
        newOrder.setTotalPrice(newOrder.getItemsPrice() + newOrder.getDeliveryPrice());
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
