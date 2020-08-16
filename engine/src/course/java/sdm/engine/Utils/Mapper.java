package course.java.sdm.engine.Utils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import course.java.sdm.engine.exceptions.DuplicateIdsException;
import course.java.sdm.engine.schema.*;
import course.java.sdm.engine.schema.Location;
import course.java.sdm.engine.schema.systemModel.StoreItem;
import course.java.sdm.engine.schema.systemModel.SystemItem;
import course.java.sdm.engine.schema.systemModel.SystemStore;
import examples.jaxb.schema.generated.*;

public class Mapper {

    public Items generatedItemsToItems(SDMItems generatedItems) {
        if (generatedItems == null) {
            return null;
        }

        return toItems(generatedItems);
    }

    public Stores generatedStoresToStores(SDMStores generatedStores, Items items) {
        if (generatedStores == null) {
            return null;
        }
        return toStores(generatedStores, items);
    }

    public Descriptor toDescriptor(Items items, Stores stores) {
        Map<Integer, SystemStore> systemStores = generatedListToMap(new ArrayList<>(stores.getStores().values()),
                                                                        Store::getId,
                                                                        SystemStore::new,
                                                                        Store.class.getSimpleName(),
                                                                        Stores.class.getSimpleName());
        Map<Integer, SystemItem> systemItemS = toSystemItems(items, systemStores.values());

        return new Descriptor(systemStores, systemItemS);
    }

    private Map<Integer, SystemItem> toSystemItems(Items items, Collection<SystemStore> stores) {
        Map<Integer, StoreItem> storeItems;
        SystemItem systemItem;
        int storesCount;
        double avgPrice, sumPrices;
        Map<Integer, SystemItem> systemItems = new HashMap<>();

        for (Map.Entry<Integer, Item> entry : items.getItems().entrySet()) {
            storesCount = 0;
            avgPrice = 0;
            sumPrices = 0;
            for (SystemStore store : stores) {
                storeItems = store.getItemIdToStoreItem();
                if (storeItems.containsKey(entry.getKey())) {
                    storesCount++;
                    sumPrices += storeItems.get(entry.getKey()).getPrice();
                }
            }
            if (storesCount > 0) {
                avgPrice =round(sumPrices / storesCount,2);

            }
            systemItem = new SystemItem(entry.getValue());
            systemItem.setAvgPrice(avgPrice);
            systemItem.setStoresCount(storesCount);
            systemItems.put(entry.getKey(), systemItem);
        }

        return systemItems;
    }

    private Stores toStores(SDMStores sdmStores, Items items) {
        if (sdmStores == null) {
            return null;
        }

        Map<Integer, Store> stores = generatedListToMap(sdmStores.getSDMStore(),
                                                            SDMStore::getId,
                                                            sdmStore -> toStore(sdmStore, items),
                                                            SDMStore.class.getSimpleName(),
                                                            SDMStores.class.getSimpleName());

        return new Stores(stores);

    }

    private Location toLocation(examples.jaxb.schema.generated.Location generatedLocation) {
        if (generatedLocation == null) {
            return null;
        }

        return new Location(generatedLocation.getX(), generatedLocation.getY());
    }

    private Item toItem(SDMItem generatedItem) {
        if (generatedItem == null) {
            return null;
        }

        return new Item(generatedItem.getName(), generatedItem.getPurchaseCategory(), generatedItem.getId());
    }

    private Items toItems(SDMItems generatedItems) {
        if (generatedItems == null) {
            return null;
        }

        Map<Integer, Item> map = generatedListToMap(generatedItems.getSDMItem(),
                                                        SDMItem::getId,
                                                        this::toItem,
                                                        Item.class.getSimpleName(),
                                                        Items.class.getSimpleName());
        return new Items(map);
    }

    private Store toStore(SDMStore generatedStore, Items items) {
        if (generatedStore == null) {
            return null;
        }

        return new Store(generatedStore.getName(),
                         generatedStore.getDeliveryPpk(),
                         toLocation(generatedStore.getLocation()),
                         toStoreItems(generatedStore.getSDMPrices(), items),
                         generatedStore.getId());
    }

    private Map<Integer, StoreItem> toStoreItems(SDMPrices sdmPrices, Items items) {
        return generatedListToMap(sdmPrices.getSDMSell(),
                                      SDMSell::getItemId,
                                      sdmSell -> toStoreItem(sdmSell, items),
                                      SDMSell.class.getSimpleName(),
                                      SDMPrices.class.getSimpleName());
    }

    private StoreItem toStoreItem(SDMSell sdmSell, Items items) {
        if (sdmSell == null) {
            return null;
        }

        Item item = items.getItems().get(sdmSell.getItemId());
        if (item == null) {
            // throw new ItemNotFoundException();
        }

        return new StoreItem(item, sdmSell.getPrice());
    }

    private <K, V, G> Map<K, V> generatedListToMap(List<G> list,
                                                   Function<G, K> getKeyFunction,
                                                   Function<G, V> getValueFunction,
                                                   String valuesClassName,
                                                   String valuesComposeClassName) {
        try {
            Map<K, V> map = list.stream().filter(Objects::nonNull).collect(Collectors.toMap(getKeyFunction, getValueFunction));
            if (map.keySet().size() != list.size()) {
                // throw appropriate exception
            }

            return map;
        }
        catch (IllegalStateException ex) {
            throw new DuplicateIdsException(valuesClassName, valuesComposeClassName, ex);
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
