package course.java.sdm.engine.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import course.java.sdm.engine.exceptions.IllegalFileExtensionException;
import course.java.sdm.engine.exceptions.ItemNotExistInStores;
import course.java.sdm.engine.exceptions.ItemNotFoundException;
import course.java.sdm.engine.schema.Items;
import course.java.sdm.engine.schema.Store;
import course.java.sdm.engine.schema.Stores;

public class Validator {
    private static final String XML_EXTENSION = ".xml";

    public void validateFile (String dataPath) throws FileNotFoundException {
        validatePathExtension(dataPath, XML_EXTENSION);
        validateIfFileExist(dataPath);
    }

    public void validateItemsAndStores (Items items, Stores stores) {
        Set<Integer> allItemsIds = items.getItems().keySet();
        Set<Integer> allStoresItemsIds = new HashSet<>();

        //validate that all store items exist in items
        for (Store currStore : stores.getStores().values()) {
            Set<Integer> allItemIdsInCurrStore = validateStoreItemsExist(allItemsIds, currStore);
            allStoresItemsIds.addAll(allItemIdsInCurrStore);
        }

        //validate that all items have (at least one) store that sell it
        validateItemsExistInStores(allItemsIds, allStoresItemsIds);
    }

    private void validateIfFileExist (String dataPath) throws FileNotFoundException {
        File file = new File(dataPath);
        if (!file.exists()) {
            throw new FileNotFoundException(String.format("The file : %s doesn't exist", dataPath));
        }
    }

    private Set<Integer> validateStoreItemsExist (Set<Integer> allItemsIds, Store currStore) {
        Set<Integer> allItemIdsInCurrStore = currStore.getItemIdToStoreItem().keySet();
        if (!allItemsIds.containsAll(allItemIdsInCurrStore)) {
            // TODO: 08/08/2020 - add item Ids of all items that don't exist in items
            throw new ItemNotFoundException(currStore.getName());
        }

        return allItemIdsInCurrStore;
    }

    private void validateItemsExistInStores (Set<Integer> allItemsIds, Set<Integer> allStoresItemsIds) {
        if (!allItemsIds.equals(allStoresItemsIds)) {
            // TODO: 08/08/2020 - add item Ids + name of all items that don't exist in any store
            throw new ItemNotExistInStores();
        }
    }

    private void validatePathExtension (String dataPath, String expectedExtension) {
        if (!dataPath.endsWith(expectedExtension)) {
            throw new IllegalFileExtensionException(dataPath, expectedExtension);
        }
    }
}
