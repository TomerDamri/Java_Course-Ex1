package course.java.sdm.engine.schema;

import java.util.Map;

public class Items {
    private Map<Integer, Item> items;

    public Items (Map<Integer, Item> sdmItems) {
        this.items = sdmItems;
    }

    public Map<Integer, Item> getItems () {
        return items;
    }
}
