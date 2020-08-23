package course.java.sdm.console;

public class Main {

    public static void main (String[] args) {
        Menu menu = new Menu();
        menu.displayMenu();
        menu.saveDataToFile();
        menu.loadDataFromFile();
        // engineService.loadData("C:\\Users\\einav\\Downloads\\ex 1 - big.xml");
    }
}