package course.java.sdm.engine.exceptions;

public class ItemNotExistInStores extends RuntimeException{
//    public ItemNotExistInStores(String itemId, String itemName) {
public ItemNotExistInStores() {
        super("There is an item that doesn't exist in any store");
    }
}
