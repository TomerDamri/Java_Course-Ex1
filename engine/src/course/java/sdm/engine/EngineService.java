package course.java.sdm.engine;

import java.io.FileNotFoundException;
import java.util.List;

import course.java.sdm.engine.Utils.FileManager;
import course.java.sdm.engine.Utils.OrdersExecutor;
import course.java.sdm.engine.Utils.SystemUpdater;
import course.java.sdm.engine.schema.Descriptor;
import course.java.sdm.engine.schema.systemModel.SystemItem;
import course.java.sdm.engine.schema.systemModel.SystemStore;
import examples.jaxb.schema.generated.SuperDuperMarketDescriptor;

public class EngineService {

    private FileManager fileManager = FileManager.getFileManager();
    private OrdersExecutor ordersExecutor = OrdersExecutor.getOrdersExecutor();
    private SystemUpdater systemUpdater = SystemUpdater.getSystemUpdater();
    private Descriptor descriptor;

    public void loadData (String xmlDataFileStr) throws FileNotFoundException {
        SuperDuperMarketDescriptor superDuperMarketDescriptor = fileManager.generateDataFromXmlFile(xmlDataFileStr);
        this.descriptor = fileManager.loadDataFromGeneratedData(superDuperMarketDescriptor);
    }
//
//    public void placeOrder(){
//        if(descriptor == null){
//            throw new Exception();
//        }
//        Order newOrder = ordersExecutor.createOrder();
//        systemUpdater.completeTheOrder(newOrder);
//    }

    public List<SystemItem> getItems () {
        // int storesCount;
        // double avgPrice;
        // SystemItem systemItem;
        // // todo maybe add an indicator to prevent all calculations with every call of "getItems"
        // if (systemItems == null) {
        // systemItems = new ArrayList<>();
        // for (Item item : descriptor.getItems().getItems().values()) {
        // storesCount = 0;
        // avgPrice = 0;
        // for (Store store : descriptor.getStores().getStores().values()) {
        // if (store.getPrices().getSells().containsKey(item.getId())) {
        // storesCount++;
        // avgPrice += store.getPrices().getSells().get(item.getId()).getPrice();
        // }
        // }
        // avgPrice = avgPrice / storesCount;
        // systemItem = new SystemItem(item);
        // systemItem.setAvgPrice(avgPrice);
        // systemItem.setStoresCount(storesCount);
        // systemItems.add(systemItem);
        // }
        // }
        // return systemItems;
        return null;
    }

    public List<SystemStore> getStores () {
        // SystemStore systemStore;
        // List<StoreItem> storeItems;
        // StoreItem storeItem;
        // // todo maybe add an indicator to prevent all calculations with every call of "getStores"
        // if (systemStores == null) {
        // systemStores = new ArrayList<>();
        //
        // for (Store store : descriptor.getStores().getStores().values()) {
        // storeItems = new ArrayList<>();
        // systemStore = new SystemStore(store);
        // for (int id : store.getPrices().getSells().keySet()) {
        // storeItem = new StoreItem((descriptor.getItems().getItems().get(id)),
        // store.getPrices().getSells().get(id).getPrice());
        // storeItems.add(storeItem);
        // }
        // // TODO: 09/08/2020 - to remove?
        //// systemStore.setStoreItems(storeItems);
        // systemStores.add(systemStore);
        // }
        // }
        //
        // return systemStores;
        return  null;
    }

}
