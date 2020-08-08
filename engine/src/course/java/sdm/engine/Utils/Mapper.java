package course.java.sdm.engine.Utils;

import course.java.sdm.engine.Schema.Location;
import course.java.sdm.engine.Schema.*;
import course.java.sdm.engine.exceptions.DuplicateIdsException;
import examples.jaxb.schema.generated.*;

import java.util.Map;
import java.util.Objects;
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

        try {
            Map<Integer, Item> map = generatedItems.getSDMItem().stream().filter(Objects::nonNull)
                    .collect(Collectors.toMap(SDMItem::getId, this::mapToItem));
            // TODO: 08/08/2020 - Do I need to delete this?
            if (map.keySet().size() != generatedItems.getSDMItem().size()) {
//                throw appropriate exception
            }

            return new Items(map);
        } catch (IllegalStateException ex) {
            throw new DuplicateIdsException(Item.class.getSimpleName(), Items.class.getSimpleName(), ex);
        }
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

        try {
            Map<Integer, Sell> map = generatedPrices.getSDMSell().stream().filter(Objects::nonNull).collect(Collectors.toMap(SDMSell::getItemId, this::mapToSell));
            if (map.keySet().size() != generatedPrices.getSDMSell().size()) {
//                throw appropriate exception
            }

            return new Prices(map);
        } catch (IllegalStateException ex) {
            throw new DuplicateIdsException(Sell.class.getSimpleName(), Prices.class.getSimpleName(), ex);
        }
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

        try {
            Map<Integer, Store> map = generatedStores.getSDMStore().stream().filter(Objects::nonNull)
                    .collect(Collectors.toMap(SDMStore::getId, this::mapToStore));
            if (map.keySet().size() != generatedStores.getSDMStore().size()) {
//                throw appropriate exception
            }

            return new Stores(map);
        } catch (IllegalStateException ex) {
            throw new DuplicateIdsException(Store.class.getSimpleName(), Stores.class.getSimpleName(), ex);
        }
    }
}
