package course.java.sdm.engine.Schema;

import java.util.Map;

public class Items {
    private Map<Integer, Item> items;

    public Items(Map<Integer, Item> sdmItems) {
        this.items = sdmItems;
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public void setItems(Map<Integer, Item> items) {
        this.items = items;
    }

//    // TODO: 07/08/2020 - change the toString implementation
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Items: [\n");
        for (Item item : items.values()) {
            stringBuilder.append(item.toString()).append(",\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}
