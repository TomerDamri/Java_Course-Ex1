package course.java.sdm.engine.Utils;

import course.java.sdm.engine.exceptions.DuplicateIdsException;
import course.java.sdm.engine.schema.Location;
import course.java.sdm.engine.schema.*;
import examples.jaxb.schema.generated.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Mapper {

    public Descriptor mapToDescriptor(SuperDuperMarketDescriptor generatedDescriptor) {
        if (generatedDescriptor == null) {
            return null;
        }

        Descriptor descriptor = new Descriptor(mapToItems(generatedDescriptor.getSDMItems()), mapToStores(generatedDescriptor.getSDMStores()));

        return descriptor;
    }

    private Location mapToLocation(examples.jaxb.schema.generated.Location generatedLocation) {
        if (generatedLocation == null) {
            return null;
        }

        return new Location(generatedLocation.getX(), generatedLocation.getY());
    }

    private Item mapToItem(SDMItem generatedItem) {
        if (generatedItem == null) {
            return null;
        }

        return new Item(generatedItem.getName(), generatedItem.getPurchaseCategory(), generatedItem.getId());
    }

    private Items mapToItems(SDMItems generatedItems) {
        if (generatedItems == null) {
            return null;
        }

        Map<Integer, Item> map = fromGeneratedListToMap(generatedItems.getSDMItem(), SDMItem::getId, this::mapToItem, Item.class.getSimpleName(), Items.class.getSimpleName());
        return new Items(map);
    }


    private Sell mapToSell(SDMSell generatedSell) {
        if (generatedSell == null) {
            return null;
        }

        return new Sell(generatedSell.getPrice(), generatedSell.getItemId());
    }

    private Prices mapToPrices(SDMPrices generatedPrices) {
        if (generatedPrices == null) {
            return null;
        }
        Map<Integer, Sell> map = fromGeneratedListToMap(generatedPrices.getSDMSell(), SDMSell::getItemId, this::mapToSell, Sell.class.getSimpleName(), Prices.class.getSimpleName());
        return new Prices(map);
    }

    private Store mapToStore(SDMStore generatedStore) {
        if (generatedStore == null) {
            return null;
        }

        return new Store(generatedStore.getName(),
                generatedStore.getDeliveryPpk(),
                mapToLocation(generatedStore.getLocation()),
                mapToPrices(generatedStore.getSDMPrices()),
                generatedStore.getId());
    }

    private Stores mapToStores(SDMStores generatedStores) {
        if (generatedStores == null) {
            return null;
        }
        Map<Integer, Store> map = fromGeneratedListToMap(generatedStores.getSDMStore(), SDMStore::getId, this::mapToStore, Store.class.getSimpleName(), Stores.class.getSimpleName());
        return new Stores(map);
    }

    private <K, V, G> Map<K, V> fromGeneratedListToMap(List<G> list, Function<G, K> getKeyFunction, Function<G, V> getValueFunction, String valuesClassName, String valuesComposeClassName) {
        try {
            Map<K, V> map = list.stream().filter(Objects::nonNull)
                    .collect(Collectors.toMap(getKeyFunction, getValueFunction));
            if (map.keySet().size() != list.size()) {
//                throw appropriate exception
            }

            return map;
        } catch (IllegalStateException ex) {
            throw new DuplicateIdsException(valuesClassName, valuesComposeClassName, ex);
        }
    }
}
