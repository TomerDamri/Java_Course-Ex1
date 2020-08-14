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

    public Items mapGeneratedItemsToItems (SDMItems generatedItems) {
        if (generatedItems == null) {
            return null;
        }

        return mapToItems(generatedItems);
    }

    public Stores mapGeneratedStoresToStores (SDMStores generatedStores, Items items) {
        if (generatedStores == null) {
            return null;
        }
        return mapToStores(generatedStores, items);
    }

    public Descriptor mapToDescriptor (Items items, Stores stores) {
        Map<Integer, SystemStore> systemStores = fromGeneratedListToMap(new ArrayList<>(stores.getStores().values()),
                                                                        Store::getId,
                                                                        SystemStore::new,
                                                                        Store.class.getSimpleName(),
                                                                        Stores.class.getSimpleName());
        Map<Integer, SystemItem> systemItemS = mapToSystemItems(items, systemStores.values());

        Descriptor descriptor = new Descriptor(systemStores, systemItemS);

        return descriptor;
    }

    private Map<Integer, SystemItem> mapToSystemItems (Items items, Collection<SystemStore> stores) {
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
                storeItems = store.getStore().getItemIdToStoreItem();
                if (storeItems.containsKey(entry.getKey())) {
                    storesCount++;
                    sumPrices += storeItems.get(entry.getKey()).getPrice();
                }
            }
            if (storesCount > 0) {
                avgPrice = sumPrices / storesCount;
            }
            systemItem = new SystemItem(entry.getValue());
            systemItem.setAvgPrice(avgPrice);
            systemItem.setStoresCount(storesCount);
            systemItems.put(entry.getKey(), systemItem);
        }

        return systemItems;
    }

    private Stores mapToStores (SDMStores sdmStores, Items items) {
        if (sdmStores == null) {
            return null;
        }

        Map<Integer, Store> stores = fromGeneratedListToMap(sdmStores.getSDMStore(),
                                                            SDMStore::getId,
                                                            sdmStore -> mapToStore(sdmStore, items),
                                                            SDMStore.class.getSimpleName(),
                                                            SDMStores.class.getSimpleName());

        return new Stores(stores);

    }

    private Location mapToLocation (examples.jaxb.schema.generated.Location generatedLocation) {
        if (generatedLocation == null) {
            return null;
        }

        return new Location(generatedLocation.getX(), generatedLocation.getY());
    }

    private Item mapToItem (SDMItem generatedItem) {
        if (generatedItem == null) {
            return null;
        }

        return new Item(generatedItem.getName(), generatedItem.getPurchaseCategory(), generatedItem.getId());
    }

    private Items mapToItems (SDMItems generatedItems) {
        if (generatedItems == null) {
            return null;
        }

        Map<Integer, Item> map = fromGeneratedListToMap(generatedItems.getSDMItem(),
                                                        SDMItem::getId,
                                                        this::mapToItem,
                                                        Item.class.getSimpleName(),
                                                        Items.class.getSimpleName());
        return new Items(map);
    }

    private Store mapToStore (SDMStore generatedStore, Items items) {
        if (generatedStore == null) {
            return null;
        }

        return new Store(generatedStore.getName(),
                         generatedStore.getDeliveryPpk(),
                         mapToLocation(generatedStore.getLocation()),
                         mapToStoreItems(generatedStore.getSDMPrices(), items),
                         generatedStore.getId());
    }

    private Map<Integer, StoreItem> mapToStoreItems (SDMPrices sdmPrices, Items items) {
        return fromGeneratedListToMap(sdmPrices.getSDMSell(),
                                      SDMSell::getItemId,
                                      sdmSell -> mapToStoreItem(sdmSell, items),
                                      SDMSell.class.getSimpleName(),
                                      SDMPrices.class.getSimpleName());
    }

    private StoreItem mapToStoreItem (SDMSell sdmSell, Items items) {
        if (sdmSell == null) {
            return null;
        }

        Item item = items.getItems().get(sdmSell.getItemId());
        if (item == null) {
            // throw new ItemNotFoundException();
        }

        return new StoreItem(item, sdmSell.getPrice());
    }

    private <K, V, G> Map<K, V> fromGeneratedListToMap (List<G> list,
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
}
