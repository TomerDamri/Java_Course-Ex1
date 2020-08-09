package course.java.sdm.engine.schema;

public class Descriptor {

    protected Items items;
    protected Stores stores;

    public Descriptor (Items items, Stores stores) {
        this.items = items;
        this.stores = stores;
    }

    public Items getItems () {
        return items;
    }

    public void setItems (Items items) {
        this.items = items;
    }

    public Stores getStores () {
        return stores;
    }

    public void setStores (Stores stores) {
        this.stores = stores;
    }
}
