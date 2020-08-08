package course.java.sdm.engine.Utils;

import course.java.sdm.engine.Schema.Items;
import course.java.sdm.engine.Schema.Store;
import course.java.sdm.engine.Schema.Stores;
import course.java.sdm.engine.exceptions.IllegalFileExtensionException;
import course.java.sdm.engine.exceptions.ItemNotExistInStores;
import course.java.sdm.engine.exceptions.ItemNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Validator {
    private static final String XML_EXTENSION = ".xml";

    public void validateFile(String dataPath) throws FileNotFoundException {
        validatePathExtension(dataPath, XML_EXTENSION);
        validateIfFileExist(dataPath);
    }

    private void validateIfFileExist(String dataPath) throws FileNotFoundException {
        File file = new File(dataPath);
        if(!file.exists()){
            throw new FileNotFoundException(String.format("The file : %s doesn't exist", dataPath));
        }
    }

    public void validateItemsAndStores(Items items, Stores stores){
        Set<Integer> allItemsIds = items.getItems().keySet();
        Set<Integer> allStoresItemsIds = new HashSet<>();

        for (Store currStore : stores.getStores().values()) {
            Set<Integer> allItemIdsInCurrStore = validateStoreItemsExist(allItemsIds, currStore);
            allStoresItemsIds.addAll(allItemIdsInCurrStore);
        }

        validateItemsExistInStores(allItemsIds, allStoresItemsIds);
    }

    private Set<Integer> validateStoreItemsExist(Set<Integer> allItemsIds, Store currStore) {
        Set<Integer> allItemIdsInCurrStore = currStore.getPrices().getSells().keySet();
        if (!allItemsIds.containsAll(allItemIdsInCurrStore)) {
            // TODO: 08/08/2020 - add item Ids of all items that don't exist in items
            throw new ItemNotFoundException(currStore.getName());
        }

        return allItemIdsInCurrStore;
    }

    private void validateItemsExistInStores(Set<Integer> allItemsIds, Set<Integer> allStoresItemsIds) {
        if (!allItemsIds.equals(allStoresItemsIds)) {
            // TODO: 08/08/2020 - add item Ids + name of all items that don't exist in any store
            throw new ItemNotExistInStores();
        }
    }

    private void validatePathExtension(String dataPath, String expectedExtension) {
        if (!dataPath.endsWith(expectedExtension)) {
            throw new IllegalFileExtensionException(dataPath, expectedExtension);
        }
    }
}
