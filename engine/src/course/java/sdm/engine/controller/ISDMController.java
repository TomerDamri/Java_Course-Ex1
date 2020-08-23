package course.java.sdm.engine.controller;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Map;

import model.request.PlaceOrderRequest;
import model.response.*;

public interface ISDMController {
    void loadFile (String filePath) throws FileNotFoundException;

    GetStoresResponse getStores ();

    GetItemsResponse getItems ();

    GetOrdersResponse getOrders ();

    PlaceOrderResponse placeStaticOrder (PlaceOrderRequest request);

    boolean isFileLoaded ();

    boolean isValidLocation (final int xCoordinate, final int yCoordinate);

    PlaceDynamicOrderResponse placeDynamicOrder (Map<Integer, Double> orderItemToAmount,
                                                 final int xCoordinate,
                                                 final int yCoordinate,
                                                 LocalDateTime orderDate);

    void completeDynamicOrder (int dynamicOrderId, boolean toConfirmNewDynamicOrder);

    void saveSystemToFile (String path);

    void loadDataFromFile (String path);
}