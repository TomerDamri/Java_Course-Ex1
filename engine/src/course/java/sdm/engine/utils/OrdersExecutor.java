package course.java.sdm.engine.utils;

import java.time.LocalDateTime;
import java.util.Map;

import course.java.sdm.engine.exceptions.BadRequestException;
import course.java.sdm.engine.exceptions.ItemNotFoundException;
import course.java.sdm.engine.model.Descriptor;
import course.java.sdm.engine.model.Item;
import course.java.sdm.engine.model.Location;
import course.java.sdm.engine.model.Order;
import course.java.sdm.engine.model.PricedItem;
import course.java.sdm.engine.model.StoreItem;
import course.java.sdm.engine.model.SystemStore;

public class OrdersExecutor {

    private static OrdersExecutor singletonOrderExecutor = null;

    private OrdersExecutor () {
    }

    public static OrdersExecutor getOrdersExecutor () {
        if (singletonOrderExecutor == null) {
            singletonOrderExecutor = new OrdersExecutor();
        }

        return singletonOrderExecutor;
    }

    public Order createOrder (SystemStore systemStore,
                              LocalDateTime orderDate,
                              Location orderLocation,
                              Map<PricedItem, Double> pricedItemToAmountMap) {
        validateLocation(orderLocation, systemStore);
        Order newOrder = new Order(Descriptor.generateOrderId(), orderDate, orderLocation);
        addItemsToOrder(systemStore, newOrder, pricedItemToAmountMap);

        return newOrder;
    }

    private void validateLocation (Location orderLocation, SystemStore systemStore) {
        if (systemStore.getLocation().equals(orderLocation)) {
            throw new BadRequestException("Invalid location. It is the location of the selected store.");
        }
    }

    private void addItemsToOrder (SystemStore systemStore, Order newOrder, Map<PricedItem, Double> pricedItemToAmountMap) {
        for (Map.Entry<PricedItem, Double> entry : pricedItemToAmountMap.entrySet()) {
            addItem(systemStore, newOrder, entry.getKey(), entry.getValue());
        }
    }

    private void addItem (SystemStore systemStore, Order newOrder, PricedItem pricedItem, Double amount) {
        validateAmount(pricedItem, amount);
        validateItemExistsInStore(pricedItem.getItem(), systemStore);

        double value = amount;
        if (newOrder.getPricedItems().containsKey(pricedItem)) {
            value += newOrder.getPricedItems().get(pricedItem);
        }

        newOrder.getPricedItems().put(pricedItem, value);
    }

    private void validateAmount (PricedItem pricedItem, Double amount) {
        validatePositiveAmount(amount);
        validateAmountToPurchaseCategory(pricedItem, amount);
    }

    private void validateAmountToPurchaseCategory (PricedItem pricedItem, Double amount) {
        if (pricedItem.getPurchaseCategory().equals(Item.PurchaseCategory.QUANTITY)) {
            if (amount.intValue() < amount) {
                throw new IllegalArgumentException(String.format("Invalid amount.\nPurchase category for item id %s is quantity and the amount should be an integer.",
                                                                 pricedItem.getId()));
            }
        }
    }

    private void validatePositiveAmount (Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid amount.\nAmount should be positive");
        }
    }

    private void validateItemExistsInStore (Item item, SystemStore systemStore) {
        Map<Integer, StoreItem> itemIdToStoreItemMap = systemStore.getItemIdToStoreItem();
        if (itemIdToStoreItemMap.get(item.getId()) == null) {
            throw new ItemNotFoundException(item.getName(), systemStore.getName());
        }
    }
}
