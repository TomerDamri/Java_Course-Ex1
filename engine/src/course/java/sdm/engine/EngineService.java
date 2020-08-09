package course.java.sdm.engine;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import course.java.sdm.engine.Utils.FileManager;
import course.java.sdm.engine.Utils.Validator;
import course.java.sdm.engine.schema.Descriptor;
import course.java.sdm.engine.schema.Item;
import course.java.sdm.engine.schema.Store;
import course.java.sdm.engine.schema.systemModel.StoreItem;
import course.java.sdm.engine.schema.systemModel.SystemItem;
import course.java.sdm.engine.schema.systemModel.SystemStore;

public class EngineService {

    private FileManager fileManager = new FileManager();
    private Validator validator = new Validator();
    private Descriptor descriptor;
    private List<SystemItem> systemItems;
    private List<SystemStore> systemStores;

    public static void foo () {
        System.out.println("At engine#foo");
    }

    public void loadData (String xmlDataFileStr) throws FileNotFoundException {
        validator.validateFile(xmlDataFileStr);
        Descriptor descriptor = fileManager.loadData(xmlDataFileStr);
        validator.validateItemsAndStores(descriptor.getItems(), descriptor.getStores());

        this.descriptor = descriptor;
    }

    public List<SystemItem> getItems () {
        int storesCount;
        double avgPrice;
        SystemItem systemItem;
        // todo maybe add an indicator to prevent all calculations with every call of "getItems"
        if (systemItems == null) {
            systemItems = new ArrayList<>();
            for (Item item : descriptor.getItems().getItems().values()) {
                storesCount = 0;
                avgPrice = 0;
                for (Store store : descriptor.getStores().getStores().values()) {
                    if (store.getPrices().getSells().containsKey(item.getId())) {
                        storesCount++;
                        avgPrice += store.getPrices().getSells().get(item.getId()).getPrice();
                    }
                }
                avgPrice = avgPrice / storesCount;
                systemItem = new SystemItem(item);
                systemItem.setAvgPrice(avgPrice);
                systemItem.setStoresCount(storesCount);
                systemItems.add(systemItem);
            }
        }
        return systemItems;
    }

    public List<SystemStore> getStores () {
        SystemStore systemStore;
        List<StoreItem> storeItems;
        StoreItem storeItem;
        // todo maybe add an indicator to prevent all calculations with every call of "getStores"
        if (systemStores == null) {
            systemStores = new ArrayList<>();

            for (Store store : descriptor.getStores().getStores().values()) {
                storeItems = new ArrayList<>();
                systemStore = new SystemStore(store);
                for (int id : store.getPrices().getSells().keySet()) {
                    storeItem = new StoreItem((descriptor.getItems().getItems().get(id)));
                    storeItem.setPrice(store.getPrices().getSells().get(id).getPrice());
                    storeItems.add(storeItem);
                    systemStore.setStoreItems(storeItems);

                }
                systemStores.add(systemStore);
            }
        }

        return systemStores;
    }

}
