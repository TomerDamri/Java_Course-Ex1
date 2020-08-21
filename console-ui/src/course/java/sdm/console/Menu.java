package course.java.sdm.console;

import java.text.SimpleDateFormat;
import java.util.*;

import course.java.sdm.engine.EngineService;
import course.java.sdm.engine.schema.Item;
import course.java.sdm.engine.schema.Location;
import course.java.sdm.engine.schema.systemModel.SystemItem;
import course.java.sdm.engine.schema.systemModel.SystemOrder;
import course.java.sdm.engine.schema.systemModel.SystemStore;
import dataModel.request.PlaceOrderRequest;
import dataModel.response.GetItemsResponse;
import dataModel.response.GetOrdersResponse;
import dataModel.response.GetStoresResponse;
import dataModel.response.PlaceOrderResponse;

public class Menu {

    private static final int COORDINATE_MIN_VALUE = 1;
    private static final int COORDINATE_MAX_VALUE = 50;
    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 5;

    protected boolean quit = false;
    private final Scanner scanner = new Scanner(System.in);
    private final EngineService service = new EngineService();
    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM-hh:mm");
    private Map<Integer, SystemStore> stores;
    private Map<Integer, SystemItem> items;

    public void displayMenu () {
        do {
            printMenuOptions();
            int userChoice = getUserChoice();
            if (!quit) {
                handleUserChoice(userChoice);
            }
        }
        while (!quit);
    }

    private void printMenuOptions () {
        System.out.println("\nPlease choose one of the following options, or press 'q' to quit:\n1. Load system data from file\n2. Display stores\n3. Display items\n4. Place new order\n5. Display orders history\n");
    }

    private int getUserChoice () {
        String userInput;
        int userChoice = 0;
        boolean isValidChoice = true;
        do {
            if (!isValidChoice) {
                System.out.println("Your Choice is not valid, please enter a value between 1-5 or press 'q' to quit");
            }
            userInput = scanner.nextLine();

            if (userInput.equals("q") || userInput.equals("Q")) {
                this.quit = true;
                isValidChoice = true;
            }
            else {
                try {
                    userChoice = Integer.parseInt(userInput);
                    isValidChoice = (userChoice >= MIN_MENU_OPTION && userChoice <= MAX_MENU_OPTION);
                }
                catch (NumberFormatException ex) {
                    isValidChoice = false;
                }
            }
        }
        while (!isValidChoice);
        return userChoice;
    }

    private void handleUserChoice (int userChoice) {
        if (userChoice == 1) {
            handleLoadData();
        }
        else {
            if (!service.isFileLoaded()) {
                System.out.println("Your request could not be processed, please load system data first");
            }
            else {
                switch (userChoice) {
                case 2:
                    handleDisplayStores();
                    break;
                case 3:
                    handleDisplayItems();
                    break;
                case 4:
                    handlePlaceOrder();
                    break;
                case 5:
                    handleDisplayOrders();
                    break;
                default:
                    break;
                }
            }
        }
    }

    private void handleLoadData () {
        System.out.println("Please enter full path of XML data file");
        String userInput = scanner.nextLine();
        try {
            service.loadData(userInput);
            System.out.println("File loaded successfully");
        }
        catch (Exception e) {
            System.out.println("Failed loading file.\n" + e.getMessage());
        }
    }

    private void handleDisplayStores () {
        GetStoresResponse response = service.getStores();
        this.stores = response.getStores();
        if (this.stores != null && !this.stores.isEmpty()) {
            Iterator<SystemStore> iterator = this.stores.values().iterator();
            while (iterator.hasNext()) {
                SystemStore store = iterator.next();
                System.out.print("{" + store + "}");
                if (iterator.hasNext()) {
                    System.out.println(",");
                }
            }
        }
        else {
            System.out.println("No stored have yet been loaded");
        }
    }

    private void handleDisplayItems () {
        GetItemsResponse response = service.getItems();
        this.items = response.getItems();
        if (this.items != null && !this.items.isEmpty()) {
            Iterator<SystemItem> iterator = this.items.values().iterator();
            while (iterator.hasNext()) {
                SystemItem item = iterator.next();
                System.out.print("{" + item + "}");
                if (iterator.hasNext()) {
                    System.out.println(",");
                }
            }
        }
        else {
            System.out.println("No items have yet been loaded");
        }
    }

    private void handleDisplayOrders () {
        GetOrdersResponse response = service.getOrders();
        if (response.getOrders() != null && !response.getOrders().isEmpty()) {
            Iterator<SystemOrder> iterator = response.getOrders().values().iterator();
            while (iterator.hasNext()) {
                SystemOrder order = iterator.next();
                System.out.print("{" + order + "}");
                if (iterator.hasNext()) {
                    System.out.println(",");
                }
            }
        }
        else {
            System.out.println("No orders have yet been placed");
        }
    }

    private void handlePlaceOrder () {
        int orderStoreId = getOrderStore();
        Date date = getOrderDate();
        Location location = getOrderLocation(orderStoreId);
        Map<Integer, Double> orderItemToAmount = getOrderItems(orderStoreId);
        if (orderItemToAmount.isEmpty()) {
            System.out.println("No items were selected, the order is canceled");
        }
        else {
            PlaceOrderRequest request = new PlaceOrderRequest(orderStoreId, date, location, orderItemToAmount);
            printOrderSummary(request);
            System.out.println(("Enter 'Y' to confirm or any other key to cancel the order"));
            String userInput = scanner.nextLine();
            if (userInput.equals("Y") || userInput.equals("y")) {

                try {
                    PlaceOrderResponse response = service.placeOrder(request);
                    System.out.println("Order created successfully\nOrder id:" + response.getOrderId());
                }
                catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
            else {
                System.out.println("Order creation canceled");
            }
        }
    }

    private int getOrderStore () {
        int id = 0;
        boolean validStore = true;
        do {
            if (!validStore) {
                System.out.println("Invalid store id");
            }
            System.out.println("Please enter the id of the store you want to order from:");
            displayStoresBeforePlacingOrder();
            try {
                id = scanner.nextInt();
                scanner.nextLine();
                validStore = validateStoreId(id);
            }
            catch (Exception exception) {
                validStore = false;
                scanner.nextLine();
            }
        }
        while (!validStore);
        return id;
    }

    private void displayStoresBeforePlacingOrder () {
        if (this.stores == null || this.stores.isEmpty()) {
            this.stores = service.getStores().getStores();
        }
        this.stores.values()
                   .forEach(store -> System.out.println(String.format("{Id: %s\nName: %s\nDelivery PPK: %s},",
                                                                      store.getId(),
                                                                      store.getName(),
                                                                      store.getDeliveryPpk())));
    }

    private boolean validateStoreId (int id) {
        if (this.stores == null || this.stores.isEmpty()) {
            this.stores = service.getStores().getStores();
        }
        return stores.containsKey(id);
    }

    private Date getOrderDate () {
        Date date = null;
        boolean stop = true;
        do {
            if (!stop) {
                System.out.println("Invalid date");
            }
            System.out.println(String.format("Please enter the order date in the following format '%s':", format.toPattern()));
            String userInput = scanner.nextLine();
            try {
                date = format.parse(userInput);
                date.setYear(new Date().getYear());
                stop = true;
            }
            catch (Exception exception) {
                stop = false;
            }
        }
        while (!stop);
        return date;
    }

    private Location getOrderLocation (int orderStoreId) {
        boolean validLocation = true;
        Location location;
        do {
            System.out.println("Please enter your location.\nThe x coordinate:");
            int x = getCoordinate();
            System.out.println("Please enter the y coordinate:");
            int y = getCoordinate();
            location = new Location(x, y);
            validLocation = validateLocation(orderStoreId, location);
            if (!validLocation) {
                System.out.println("Invalid location. It is the location of the selected store.");
            }
        }
        while (!validLocation);
        return location;
    }

    private int getCoordinate () {
        int coordinate = 0;
        boolean validCoordinate = true;
        do {
            if (!validCoordinate) {
                System.out.println("Invalid coordinate");
            }
            System.out.println("Please enter a coordinate of range [1,50]");
            try {
                coordinate = scanner.nextInt();
                scanner.nextLine();
                validCoordinate = validateCoordinateRange(coordinate);
            }
            catch (Exception exception) {
                validCoordinate = false;
                scanner.nextLine();
            }
        }
        while (!validCoordinate);
        return coordinate;
    }

    private boolean validateCoordinateRange (int coordinate) {
        return (coordinate >= COORDINATE_MIN_VALUE && coordinate <= COORDINATE_MAX_VALUE);
    }

    private boolean validateLocation (int orderStoreId, Location location) {
        SystemStore store = this.stores.get(orderStoreId);
        return !store.getLocation().equals(location);
    }

    private Map<Integer, Double> getOrderItems (int storeId) {
        Map<Integer, Double> itemsToAmount = new HashMap<>();
        boolean doneSelectingItems = false;
        int itemId = 0;
        double itemAmount = 0;
        do {
            System.out.println("Please select items to order. Press 'q' when done selecting");
            itemId = selectItem(storeId);
            if (itemId == -1) {
                doneSelectingItems = true;
            }
            else {
                itemAmount = getItemAmount(itemId, storeId);
                // Updating amount in case the item is already in the order
                if (itemsToAmount.containsKey(itemId)) {
                    itemAmount = (itemsToAmount.get(itemId) + itemAmount);
                }
                itemsToAmount.put(itemId, itemAmount);
            }
        }
        while (!doneSelectingItems);
        System.out.println("Done selecting items");
        return itemsToAmount;
    }

    private int selectItem (int storeId) {
        boolean isValidItem = true;
        String userInput;
        int itemId = -1;
        do {
            System.out.println("Please enter the id of the item you want to order");
            displayItemsBeforePlacingOrder(storeId);
            userInput = scanner.nextLine();
            if (userInput.equals("q") || userInput.equals("Q")) {
                itemId = -1;
                isValidItem = true;
            }
            else {
                try {
                    itemId = Integer.parseInt(userInput);
                    isValidItem = validateItemId(itemId, storeId);
                    if (!isValidItem) {
                        System.out.println("The item is not supplied by the selected store");
                    }
                }
                catch (Exception exception) {
                    isValidItem = false;
                    System.out.println("Item id should be an integer");
                }
            }
        }
        while (!isValidItem);

        return itemId;
    }

    private void displayItemsBeforePlacingOrder (int storeId) {
        stores = service.getStores().getStores();
        items = service.getItems().getItems();
        SystemStore store = this.stores.get(storeId);
        items.keySet().forEach(itemId -> {
            if (store.getItemIdToStoreItem().containsKey(itemId)) {
                System.out.println("{" + store.getItemIdToStoreItem().get(itemId).getPricedItem().toString() + "}");
            }
            else {
                System.out.print("{" + items.get(itemId).getItem().toString() + ",\nThis item is not supplied in the store}\n");
            }
        });
    }

    private boolean validateItemId (int itemId, int storeId) {
        return this.stores.get(storeId).getItemIdToStoreItem().containsKey(itemId);
    }

    private double getItemAmount (int itemId, int storeId) {
        boolean isValidAmount = true;
        double itemAmount = 0;
        do {
            System.out.println("Please enter the amount that you want to order");
            try {
                itemAmount = scanner.nextDouble();
                scanner.nextLine();
                isValidAmount = validateItemAmount(itemId, itemAmount);
                if (!isValidAmount) {
                    System.out.println("Invalid amount!\nAmount should be positive. For quantity purchase category, amount should also be an integer");
                }
            }
            catch (Exception exception) {
                isValidAmount = false;
                System.out.println("Invalid amount");
                scanner.nextLine();
            }
        }
        while (!isValidAmount);
        return itemAmount;
    }

    private boolean validateItemAmount (int itemId, Double itemAmount) {
        if (itemAmount < 0) {
            return false;
        }
        // Not an integer is ok only if purchase category is weight
        else if (itemAmount.intValue() < itemAmount) {
            return this.items.get(itemId).getPurchaseCategory().equals(Item.PurchaseCategory.WEIGHT);
        }
        return true;
    }

    private void printOrderSummary (PlaceOrderRequest request) {
        System.out.println("Order summary:\nOrder items:");
        Iterator<Integer> iterator = request.getOrderItemToAmount().keySet().iterator();
        while (iterator.hasNext()) {
            Integer itemId = iterator.next();
            SystemItem item = items.get(itemId);
            double amount = request.getOrderItemToAmount().get(itemId);
            int itemPrice = stores.get(request.getStoreId()).getItemIdToStoreItem().get(itemId).getPrice();

            System.out.print(String.format("{Item id: %s,\nItem name: %s,\nPurchase category: %s,\nPrice: %s,\nAmount: %s,\nTotal item price: %s}",
                                           itemId,
                                           item.getName(),
                                           item.getPurchaseCategory(),
                                           itemPrice,
                                           amount,
                                           round(amount * itemPrice, 2)));
            if (iterator.hasNext()) {
                System.out.println(",");
            }
        }
        System.out.println();
        double distance = round(calculateDistance(stores.get(request.getStoreId()).getLocation(), request.getOrderLocation()), 2);
        int ppk = stores.get(request.getStoreId()).getDeliveryPpk();
        System.out.println(String.format("Distance form store: %s\nStore PPK: %s\nDelivery price: %s", distance, ppk, distance * ppk));

    }

    private double calculateDistance (Location storeLocation, Location orderLocation) {
        return Math.sqrt(Math.pow(orderLocation.getX() - storeLocation.getX(), 2)
                    + Math.pow(orderLocation.getY() - storeLocation.getY(), 2));
    }

    public static double round (double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}