package course.java.sdm.engine.controller;

import java.io.FileNotFoundException;

import model.request.PlaceOrderRequest;
import model.response.GetItemsResponse;
import model.response.GetOrdersResponse;
import model.response.GetStoresResponse;
import model.response.PlaceOrderResponse;

public interface ISDMController {
    void loadFile (String filePath) throws FileNotFoundException;

    GetStoresResponse getStores ();

    GetItemsResponse getItems ();

    GetOrdersResponse getOrders ();

    PlaceOrderResponse placeOrder (PlaceOrderRequest request);

    boolean isFileLoaded ();
}
