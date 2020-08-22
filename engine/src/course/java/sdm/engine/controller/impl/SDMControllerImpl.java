package course.java.sdm.engine.controller.impl;

import java.io.FileNotFoundException;

import course.java.sdm.engine.service.SDMService;
import course.java.sdm.engine.controller.ISDMController;
import model.request.PlaceOrderRequest;
import model.response.GetItemsResponse;
import model.response.GetOrdersResponse;
import model.response.GetStoresResponse;
import model.response.PlaceOrderResponse;

public class SDMControllerImpl implements ISDMController {
    private SDMService service = new SDMService();

    public void loadFile (String filePath) throws FileNotFoundException {
        service.loadData(filePath);
    }

    public GetStoresResponse getStores () {
        return service.getStores();
    }

    public GetItemsResponse getItems () {
        return service.getItems();
    }

    public GetOrdersResponse getOrders ()  {
        return service.getOrders();
    }

    public PlaceOrderResponse placeOrder (PlaceOrderRequest request){
        return service.placeOrder(request);
    }

    public boolean isFileLoaded () {
        return service.isFileLoaded();
    }
}
