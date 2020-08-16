package course.java.sdm.engine.schema;

import java.util.Map;

import course.java.sdm.engine.schema.systemModel.StoreItem;

public class Store {

    private int id;
    private String name;
    private int deliveryPpk;
    private Location location;
    private Map<Integer, StoreItem> itemIdToStoreItem;

    public Store (String name, int deliveryPpk, Location location, Map<Integer, StoreItem> itemIdToStoreItem, int id) {
        this.id = id;
        this.name = name;
        this.deliveryPpk = deliveryPpk;
        this.location = location;
        this.itemIdToStoreItem = itemIdToStoreItem;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getDeliveryPpk () {
        return deliveryPpk;
    }

    public void setDeliveryPpk (int deliveryPpk) {
        this.deliveryPpk = deliveryPpk;
    }

    public Location getLocation () {
        return location;
    }

    public void setLocation (Location location) {
        this.location = location;
    }

    public Map<Integer, StoreItem> getItemIdToStoreItem () {
        return itemIdToStoreItem;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    @Override
    public String toString () {
        return new StringBuilder().append("\n{Id: ")
                                  .append(id)
                                  .append(",\nName: ")
                                  .append(name)
                                  .append(",\nPPK: ")
                                  .append(deliveryPpk)
                                  .append(",\n")
                                  .append(",\nStore Items:")
                                  .append(itemIdToStoreItem.values().toString())
                                  .append(" }")
                                  .toString();
    }
}
