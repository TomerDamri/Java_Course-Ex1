package course.java.sdm.engine.service;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.stream.Collectors;

import course.java.sdm.engine.exceptions.FileNotLoadedException;
import course.java.sdm.engine.mapper.DTOMapper;
import course.java.sdm.engine.model.Descriptor;
import course.java.sdm.engine.model.Location;
import course.java.sdm.engine.model.Order;
import course.java.sdm.engine.model.PricedItem;
import course.java.sdm.engine.utils.FileManager;
import course.java.sdm.engine.utils.OrdersExecutor;
import course.java.sdm.engine.utils.SystemUpdater;
import examples.jaxb.schema.generated.SuperDuperMarketDescriptor;
import model.request.PlaceOrderRequest;
import model.response.GetItemsResponse;
import model.response.GetOrdersResponse;
import model.response.GetStoresResponse;
import model.response.PlaceOrderResponse;

public class SDMService {

    private final FileManager fileManager = FileManager.getFileManager();
    private final OrdersExecutor ordersExecutor = OrdersExecutor.getOrdersExecutor();
    private final SystemUpdater systemUpdater = SystemUpdater.getSystemUpdater();
    private final static DTOMapper mapper = new DTOMapper();
    private Descriptor descriptor;

    public void loadData (String xmlDataFileStr) throws FileNotFoundException {
        SuperDuperMarketDescriptor superDuperMarketDescriptor = fileManager.generateDataFromXmlFile(xmlDataFileStr);
        this.descriptor = fileManager.loadDataFromGeneratedData(superDuperMarketDescriptor);
    }

    public boolean isFileLoaded () {
        return descriptor != null;
    }

    public GetItemsResponse getItems () {
        if (descriptor == null) {
            throw new FileNotLoadedException();
        }
        return mapper.toGetItemsResponse(descriptor.getSystemItems());
    }

    public GetOrdersResponse getOrders () {
        if (descriptor == null) {
            throw new FileNotLoadedException();
        }
        return mapper.toGetOrdersResponse(descriptor.getSystemOrders());
    }

    public GetStoresResponse getStores () {
        if (descriptor == null) {
            throw new FileNotLoadedException();
        }
        return mapper.toGetStoresResponse(descriptor.getSystemStores());
    }

    public PlaceOrderResponse placeOrder (PlaceOrderRequest request) {
        if (descriptor == null) {
            throw new FileNotLoadedException();
        }
        Map<PricedItem, Double> pricedItems = getPricedItemFromRequest(request);
        Order newOrder = addNewOrder(request, pricedItems);
        return new PlaceOrderResponse(newOrder.getId());
    }

    private Order addNewOrder (PlaceOrderRequest request, Map<PricedItem, Double> pricedItems) {
        Order newOrder = ordersExecutor.createOrder(descriptor.getSystemStores().get(request.getStoreId()),
                                                    request.getOrderDate(),
                                                    new Location(request.getxCoordinate(), request.getyCoordinate()),
                                                    pricedItems);
        systemUpdater.updateSystem(descriptor.getSystemStores().get(request.getStoreId()), newOrder, descriptor);
        return newOrder;
    }

    private Map<PricedItem, Double> getPricedItemFromRequest (PlaceOrderRequest request) {
        return request.getOrderItemToAmount()
                      .keySet()
                      .stream()
                      .map(itemId -> descriptor.getSystemStores()
                                               .get(request.getStoreId())
                                               .getItemIdToStoreItem()
                                               .get(itemId)
                                               .getPricedItem())
                      .collect(Collectors.toMap(pricedItem -> pricedItem,
                                                pricedItem -> request.getOrderItemToAmount().get(pricedItem.getId())));
    }
}