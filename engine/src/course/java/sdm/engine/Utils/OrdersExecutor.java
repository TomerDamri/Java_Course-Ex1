package course.java.sdm.engine.Utils;

import java.time.LocalDateTime;
import java.util.Map;

import course.java.sdm.engine.exceptions.ItemNotFoundException;
import course.java.sdm.engine.schema.Descriptor;
import course.java.sdm.engine.schema.Item;
import course.java.sdm.engine.schema.Location;
import course.java.sdm.engine.schema.systemModel.Order;
import course.java.sdm.engine.schema.systemModel.PricedItem;
import course.java.sdm.engine.schema.systemModel.StoreItem;
import course.java.sdm.engine.schema.systemModel.SystemStore;

public class OrdersExecutor {

    private static OrdersExecutor singletonOrderExecutor = null;

    private OrdersExecutor() {
    }

    public static OrdersExecutor getOrdersExecutor() {
        if (singletonOrderExecutor == null) {
            singletonOrderExecutor = new OrdersExecutor();
        }

        return singletonOrderExecutor;
    }

    public Order createOrder (SystemStore systemStore,
                              LocalDateTime orderDate,
                              Location orderLocation,
                              Map<PricedItem, Double> orderItemToAmount) {
        Order newOrder = new Order(Descriptor.generateOrderId(), orderDate, orderLocation);
        addItemsToOrder(systemStore, newOrder, orderItemToAmount);

        return newOrder;
    }

    private void addItemsToOrder (SystemStore systemStore, Order newOrder, Map<PricedItem, Double> orderItemToAmount) {
        for (Map.Entry<PricedItem, Double> entry : orderItemToAmount.entrySet()) {
            addItem(systemStore, newOrder, entry.getKey(), entry.getValue());
        }
    }

    private void addItem (SystemStore systemStore, Order newOrder, PricedItem pricedItem, Double amount) {
        validateAmount(pricedItem, amount);
        validateItemExistInStore(pricedItem.getItem(), systemStore); // is exist?

        double value = amount;
        if (newOrder.getAllOrderItems().containsKey(pricedItem)) {
            value += newOrder.getAllOrderItems().get(pricedItem);
        }

        newOrder.getAllOrderItems().put(pricedItem, value);
    }

    private void validateAmount (PricedItem pricedItem, Double amount) {
        validatePositiveAmount(amount);
        validateAppropriateAmountForPurchaseCategory(pricedItem, amount);
    }

    private void validateAppropriateAmountForPurchaseCategory (PricedItem pricedItem, Double amount) {
        if (pricedItem.getItem().getPurchaseCategory().equals(Item.PurchaseCategory.QUANTITY)) {
            if (amount.intValue() < amount) {
                throw new IllegalArgumentException(String.format("You enter a floating point number as amount instead of an integer number for item: %s",
                                                                 pricedItem.toString()));
            }
        }
    }

    private void validatePositiveAmount (Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("You enter a negative number as amount");
        }
    }

    private void validateItemExistInStore (Item item, SystemStore systemStore) {
        Map<Integer, StoreItem> itemIdToStoreItem = systemStore.getStore().getItemIdToStoreItem();
        if (itemIdToStoreItem.get(item.getId()) == null) {
            throw new ItemNotFoundException(item.getName(), systemStore.getStore().getName());
        }
    }
}
