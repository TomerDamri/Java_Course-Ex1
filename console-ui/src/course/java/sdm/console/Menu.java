package course.java.sdm.console;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

import course.java.sdm.engine.EngineService;
import course.java.sdm.engine.schema.Location;
import course.java.sdm.engine.schema.systemModel.SystemItem;
import course.java.sdm.engine.schema.systemModel.SystemStore;
import dataModel.response.GetItemsResponse;
import dataModel.response.GetOrdersResponse;
import dataModel.response.GetStoresResponse;

public class Menu {

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 50;
    protected boolean quit = false;
    private final Scanner scanner = new Scanner(System.in);
    private final EngineService service = new EngineService();
    private final SimpleDateFormat format = new SimpleDateFormat("dd/mm-hh:mm");
    private Map<Integer, SystemStore> stores;
    private Map<Integer, SystemItem> items;
    // private Map<Integer, SystemOrder> orders;

    public void displayMenu () {
        do {
            printMenuOptions();
            int userChoice = getUserChoice();
            handleUserChoice(userChoice);
        }
        while (!quit);

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
                    // todo
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
                this.quit = true;
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
        System.out.println("Please enter full path of XML data file:");
        String path = scanner.nextLine();
        try {
            service.loadData(path);
            System.out.println("File loaded successfully");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
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

    // todo- validate each step
    private void handlePlaceOrder () {

        int orderStore = getOrderStore();
        Location location = getOrderLocation();
        Date date = getOrderDate();
        Map<Integer, Double> orderItemToAmount = getOrderItems();


    }

    private Map<Integer, Double> getOrderItems() {

        return null;
    }

    private Date getOrderDate () {
        Date date;
        String dateAsString;
        boolean validInput = true;
        scanner.nextLine();

        do {
            if (!validInput) {
                System.out.println("Invalid date");
            }
            System.out.println("Please enter the order date in the following format 'dd/mm-hh:mm':");

            try {
                dateAsString = scanner.nextLine();
                date = format.parse(dateAsString);

                validInput = true;
            }
            catch (Exception exception) {
                validInput = false;
            }
        }
        while (!validInput);
        // date = new LocalDateTime();
        return null;
    }

    private Location getOrderLocation () {
        System.out.println("Please enter your location.\nThe x coordinate:");
        int x = getCoordinate();
        System.out.println("Please enter the y coordinate:");
        int y = getCoordinate();
        return new Location(x, y);

    }

    private int getCoordinate () {
        int coordinate = 0;
        boolean validInput = true;
        do {
            if (!validInput) {
                System.out.println("Invalid coordinate");
            }
            System.out.println("\"Please enter a coordinate of range [1,50]");
            try {
                coordinate = scanner.nextInt();
                validInput = validateCoordinateRange(coordinate);

            }
            catch (Exception exception) {
                validInput = false;
                scanner.nextLine();
            }
        }
        while (!validInput);
        return coordinate;
    }

    private boolean validateCoordinateRange (int coordinate) {
        return (coordinate >= MIN_VALUE && coordinate <= MAX_VALUE);
    }

    private int getOrderStore () {

        int id = 0;
        boolean validInput = true;
        do {
            if (!validInput) {
                System.out.println("Invalid store id");
            }
            System.out.println("Please enter the id of the store you want to order from:");
            displayStoresBeforePlacingOrder();
            try {
                id = scanner.nextInt();
                validInput = validateStoreId(id);

            }
            catch (Exception exception) {
                validInput = false;
                scanner.nextLine();
            }
        }
        while (!validInput);
        return id;
    }

    private boolean validateStoreId (int id) {
        return stores.containsKey(id);
    }

    private void displayStoresBeforePlacingOrder () {
        if (this.stores == null || this.stores.isEmpty()) {
            this.stores = service.getStores().getStores();
        }
        this.stores.values()
                   .forEach(store -> System.out.println(String.format("{Id: %s\nName: %s\nPPK: %s},\n",
                                                                      store.getStore().getId(),
                                                                      store.getStore().getName(),
                                                                      store.getStore().getDeliveryPpk())));

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