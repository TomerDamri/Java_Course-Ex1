package course.java.sdm.engine;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import course.java.sdm.engine.Utils.FileManager;
import course.java.sdm.engine.Utils.OrdersExecutor;
import course.java.sdm.engine.Utils.SystemUpdater;
import course.java.sdm.engine.schema.Descriptor;
import course.java.sdm.engine.schema.systemModel.Order;
import course.java.sdm.engine.schema.systemModel.PricedItem;
import dataModel.request.PlaceOrderRequest;
import dataModel.response.GetItemsResponse;
import dataModel.response.GetOrdersResponse;
import dataModel.response.GetStoresResponse;
import dataModel.response.PlaceOrderResponse;
import examples.jaxb.schema.generated.SuperDuperMarketDescriptor;

public class EngineService {

    private final FileManager fileManager = FileManager.getFileManager();
    private final OrdersExecutor ordersExecutor = OrdersExecutor.getOrdersExecutor();
    private final SystemUpdater systemUpdater = SystemUpdater.getSystemUpdater();
    private Descriptor descriptor;

    public void loadData (String xmlDataFileStr) throws FileNotFoundException {
        SuperDuperMarketDescriptor superDuperMarketDescriptor = fileManager.generateDataFromXmlFile(xmlDataFileStr);
        this.descriptor = fileManager.loadDataFromGeneratedData(superDuperMarketDescriptor);
    }

    public PlaceOrderResponse placeOrder (PlaceOrderRequest request) throws Exception {
        if (descriptor == null) {
            throw new Exception();
        }
        Map<PricedItem, Double> pricedItems = request.getOrderItemToAmount()
                .keySet()
                .stream()
                .map(itemId -> descriptor.getSystemStores()
                        .get(request.getStoreId())
                        .getItemIdToStoreItem()
                        .get(itemId)
                        .getPricedItem())
                .collect(Collectors.toMap(pricedItem -> pricedItem, pricedItem->request.getOrderItemToAmount().get(pricedItem.getId())));
//TODO- update date
        Order newOrder = ordersExecutor.createOrder(descriptor.getSystemStores().get(request.getStoreId()),
                                                   new Date(),
                                                    request.getOrderLocation(),
                pricedItems);
         systemUpdater.updateSystem(descriptor.getSystemStores().get(request.getStoreId()),newOrder, descriptor);
        return new PlaceOrderResponse(newOrder.getId());
    }

    public boolean isFileLoaded () {
        return descriptor != null;
    }

    public GetItemsResponse getItems () {
        return new GetItemsResponse(descriptor.getSystemItems());
    }

    public GetOrdersResponse getOrders () {
        return new GetOrdersResponse(descriptor.getSystemOrders());
    }

    public GetStoresResponse getStores () {
        return new GetStoresResponse(descriptor.getSystemStores());
    }
}