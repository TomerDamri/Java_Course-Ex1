package course.java.sdm.engine.Schema;

import java.util.Map;

public class Items {
    private Map<Integer, Item> Items;

    public Items(Map<Integer, Item> sdmItems) {
        this.Items = sdmItems;
    }

    public Map<Integer, Item> getItems() {
        return Items;
    }

    public void setItems(Map<Integer, Item> items) {
        this.Items = items;
    }

}
