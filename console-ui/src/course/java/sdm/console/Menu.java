package course.java.sdm.console;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 50;
    protected boolean quitSystem = false;
    protected boolean backToMenu = false;
    private final Scanner scanner = new Scanner(System.in);
    private final EngineService service = new EngineService();
    private final SimpleDateFormat format = new SimpleDateFormat("dd/mm-hh:mm");
    private Map<Integer, SystemStore> stores;
    private Map<Integer, SystemItem> items;

    public void displayMenu () {
        do {
            printMenuOptions();
            int userChoice = getUserChoice();
            handleUserChoice(userChoice);
        }
        while (!quitSystem);

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
                    handleDisplayOrdersHistory();
                    break;
                default:
                    break;
                }
            }
        }
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
                this.quitSystem = true;
                isValidChoice = true;
            }
            else {
                try {
                    userChoice = Integer.parseInt(userInput);
                    isValidChoice = (userChoice > 0 && userChoice < 6);
                }
                catch (NumberFormatException ex) {
                    isValidChoice = false;
                }
            }
        }
        while (!isValidChoice);

        return userChoice;
    }

    private void handleLoadData () {
        System.out.println("Please enter full path of XML data file or press 'q' to return to main menu");
        String userInput = scanner.nextLine();
        if (!userInput.equals("q") || userInput.equals("Q")) {
            try {
                service.loadData(userInput);
                System.out.println("File loaded successfully");
            }
            catch (Exception e) {
                System.out.println("Failed loading file.\n" + e.getMessage());
            }
        }
    }

    private void handleDisplayStores () {
        GetStoresResponse response = service.getStores();
        this.stores = response.getStores();
        System.out.println(this.stores.values());
    }

    private void handleDisplayItems () {
        GetItemsResponse response = service.getItems();
        this.items = response.getItems();
        System.out.print(this.items.values());
    }

    private void handlePlaceOrder () {
        int orderStoreId = getOrderStore();
        if (!backToMenu) {
            Date date = getOrderDate();
            if (!backToMenu) {
                boolean validLocation = true;
                Location location;
                do {
                    if (!validLocation) {
                        System.out.println("Invalid location. It is the location of the selected store.");
                    }
                    location = getOrderLocation();
                    validLocation = validateLocation(orderStoreId, location);
                }
                while (!validLocation && !backToMenu);
                if (!backToMenu) {
                    Map<Integer, Double> orderItemToAmount = getOrderItems(orderStoreId);
                    if (orderItemToAmount.isEmpty()) {
                        System.out.println("No items were selected, the order is canceled");
                    }
                    else {
                        System.out.println("The order details:\nStore id:" + orderStoreId + "\nOrder date: " + date + "\nOrder location: "
                                    + location + "\nOrder items:" + orderItemToAmount);
                        System.out.println(("Enter 'Y' to confirm or any other key to cancel the order"));
                        String userInput = scanner.nextLine();
                        if (userInput.equals("Y") || userInput.equals("y")) {

                            try {
                                PlaceOrderResponse response = service.placeOrder(new PlaceOrderRequest(orderStoreId,
                                                                                                       date,
                                                                                                       location,
                                                                                                       orderItemToAmount));
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
            }
        }
    }

    private boolean validateLocation (int orderStoreId, Location location) {
        SystemStore store = this.stores.get(orderStoreId);
        return !store.getLocation().equals(location);
    }

    private Map<Integer, Double> getOrderItems (int storeId) {
        Map<Integer, Double> itemsToAmount = new HashMap<>();
        boolean doneSelectingItems = false;
        String userInput;
        int itemId = 0;
        double itemAmount = 0;
        do {
            System.out.println("Please select items to order.Press 'q' when done selecting");
            boolean isValidItem = true;
            do {
                System.out.println("Please enter the id of the item you want to order or press 'q' to quit");
                displayItemsBeforePlacingOrder(storeId);
                userInput = scanner.nextLine();
                if (userInput.equals("q") || userInput.equals("Q")) {
                    doneSelectingItems = true;
                }
                else {
                    try {
                        itemId = Integer.parseInt(userInput);
                        isValidItem = validateItemId(itemId, storeId);
                        if (!isValidItem) {
                            System.out.println("The item is not supplied in the selected store");
                        }
                    }
                    catch (Exception exception) {
                        isValidItem = false;
                        System.out.println("Item id should be an integer");
                    }
                }
            }
            while (!isValidItem && !doneSelectingItems);

            if (!doneSelectingItems) {
                boolean isValidAmount = true;
                do {
                    System.out.println("Please enter the amount that you want to order or press 'q' to finish order");
                    userInput = scanner.nextLine();
                    if (userInput.equals("q")) {
                        doneSelectingItems = true;
                    }
                    else {
                        try {
                            itemAmount = Double.parseDouble(userInput);
                            isValidAmount = validateItemAmount(itemId, itemAmount);
                            if (!isValidAmount) {
                                System.out.println("Invalid amount!\nAmount should be positive. For quantity purchase category, amount should also be an integer");
                            }
                        }
                        catch (Exception exception) {
                            isValidAmount = false;
                            System.out.println("Invalid amount");
                        }
                    }
                }
                while (!isValidAmount);
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

    private boolean validateItemId (int itemId, int storeId) {
        return this.stores.get(storeId).getItemIdToStoreItem().containsKey(itemId);
    }

    private void displayItemsBeforePlacingOrder (int storeId) {
        stores = service.getStores().getStores();
        items = service.getItems().getItems();

        SystemStore store = this.stores.get(storeId);
        items.keySet().forEach(itemId -> {
            if (store.getItemIdToStoreItem().containsKey(itemId)) {
                System.out.println("{" + store.getItemIdToStoreItem().get(itemId).getPricedItem().toString() + "}\n");
            }
            else {
                System.out.print("{" + items.get(itemId).getItem().toString() + ",\nThis item is not supplied in the store}\n");
            }
        });
    }

    private Date getOrderDate () {
        Date date;
        String userInput;
        boolean stop = true;
        backToMenu = false;
        do {
            if (!stop) {
                System.out.println("Invalid date");
            }
            System.out.println("Please enter the order date in the following format 'dd/mm-hh:mm':");
            userInput = scanner.nextLine();
            if (userInput.equals("q") || userInput.equals("Q")) {
                stop = true;
                backToMenu = true;
            }
            else {
                try {
                    date = format.parse(userInput);

                    stop = true;
                }
                catch (Exception exception) {
                    stop = false;
                }
            }
        }
        while (!stop);
        // date = new LocalDateTime();
        return null;
    }

    private Location getOrderLocation () {
        System.out.println("Please enter your location.\nThe x coordinate:");
        int x = getCoordinate();
        Location location = null;
        if (!backToMenu) {
            System.out.println("Please enter the y coordinate:");
            int y = getCoordinate();
            location = new Location(x, y);
        }
        return location;
    }

    private int getCoordinate () {
        String userInput;
        int coordinate = 0;
        boolean stop = true;
        backToMenu = false;
        do {
            if (!stop) {
                System.out.println("Invalid coordinate");
            }
            System.out.println("Please enter a coordinate of range [1,50]");
            userInput = scanner.nextLine();
            if (userInput.equals("q") || userInput.equals("Q")) {
                stop = true;
                backToMenu = true;
            }
            else {
                try {
                    coordinate = Integer.parseInt(userInput);
                    stop = validateCoordinateRange(coordinate);
                }
                catch (Exception exception) {
                    stop = false;
                }
            }
        }
        while (!stop);
        return coordinate;
    }

    private boolean validateCoordinateRange (int coordinate) {
        return (coordinate >= MIN_VALUE && coordinate <= MAX_VALUE);
    }

    private int getOrderStore () {
        int id = 0;
        String userInput;
        boolean stop = true;
        backToMenu = false;
        do {
            if (!stop) {
                System.out.println("Invalid store id");
            }
            System.out.println("Please enter the id of the store you want to order from:");
            displayStoresBeforePlacingOrder();
            userInput = scanner.nextLine();
            if (userInput.equals("q") || userInput.equals("Q")) {
                stop = true;
                backToMenu = true;
            }
            else {
                try {
                    id = Integer.parseInt(userInput);
                    stop = validateStoreId(id);

                }
                catch (Exception exception) {
                    stop = false;
                }
            }
        }
        while (!stop);
        return id;
    }

    private boolean validateStoreId (int id) {
        if (this.stores == null || this.stores.isEmpty()) {
            this.stores = service.getStores().getStores();
        }
        return stores.containsKey(id);
    }

    private void displayStoresBeforePlacingOrder () {
        if (this.stores == null || this.stores.isEmpty()) {
            this.stores = service.getStores().getStores();
        }
        this.stores.values()
                   .forEach(store -> System.out.println(String.format("{Id: %s\nName: %s\nPPK: %s},\n",
                                                                      store.getId(),
                                                                      store.getName(),
                                                                      store.getDeliveryPpk())));

    }

    private void handleDisplayOrdersHistory () {
        GetOrdersResponse response = service.getOrders();
        if (response.getOrders() != null && !response.getOrders().isEmpty()) {
            System.out.println(response.getOrders());
        }
        else {
            System.out.println("No orders have yet been placed in the system");
        }
    }

}
