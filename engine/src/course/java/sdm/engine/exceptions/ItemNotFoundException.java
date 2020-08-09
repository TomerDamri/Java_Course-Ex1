package course.java.sdm.engine.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException (String storeName) {
        super(String.format("There is a sell item in %s that doesn't exist", storeName));
    }
}
