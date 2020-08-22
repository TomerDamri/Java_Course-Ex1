package course.java.sdm.engine.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import course.java.sdm.engine.exceptions.IllegalFileExtensionException;
import course.java.sdm.engine.exceptions.ItemNotExistInStores;
import course.java.sdm.engine.exceptions.ItemNotFoundException;
import course.java.sdm.engine.model.Items;
import course.java.sdm.engine.model.Store;
import course.java.sdm.engine.model.Stores;

public class Validator {
    private static final String XML_EXTENSION = ".xml";

    public void validateFile (String filePath) throws FileNotFoundException {
        validateFileExtension(filePath);
        validateFileExists(filePath);
    }

    public void validateItemsAndStores (Items items, Stores stores) {
        Set<Integer> itemsIds = items.getItems().keySet();
        Set<Integer> suppliedItemsIds = new HashSet<>();

        //validate that all the supplied items exist in items
        for (Store store : stores.getStores().values()) {
            Set<Integer> itemsInStoreIds = validateStoreItemsExist(itemsIds, store);
            suppliedItemsIds.addAll(itemsInStoreIds);
        }

        //validate that all items are supplied in at least 1 store
        validateAllItemsSupplied(itemsIds, suppliedItemsIds);
    }

    private void validateFileExists(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(String.format("The file : %s doesn't exist", filePath));
        }
    }

    private Set<Integer> validateStoreItemsExist (Set<Integer> itemsIds, Store store) {
        Set<Integer> itemsInStoreIds = store.getItemIdToStoreItem().keySet();
        if (!itemsIds.containsAll(itemsInStoreIds)) {
            itemsInStoreIds.removeAll(itemsIds);
            throw new ItemNotFoundException(store.getName(), itemsInStoreIds);
        }

        return itemsInStoreIds;
    }

    private void validateAllItemsSupplied(Set<Integer> items, Set<Integer> suppliedItems) {
        if (!items.equals(suppliedItems)) {
         items.removeAll(suppliedItems);
            throw new ItemNotExistInStores(items);
        }
    }

    private void validateFileExtension(String dataPath) {
        if (!dataPath.endsWith(Validator.XML_EXTENSION)) {
            throw new IllegalFileExtensionException(dataPath, Validator.XML_EXTENSION);
        }
    }
}
