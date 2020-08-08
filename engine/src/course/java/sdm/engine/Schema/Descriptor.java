package course.java.sdm.engine.Schema;

import course.java.sdm.engine.Utils.Mapper;
import examples.jaxb.schema.generated.SuperDuperMarketDescriptor;

public class Descriptor {

    protected Items items;
    protected Stores stores;

    public Descriptor(Items items, Stores stores) {
        this.items = items;
        this.stores = stores;
    }

    public Descriptor(SuperDuperMarketDescriptor generatedDescriptor){
        new Descriptor(Mapper.mapToItems(generatedDescriptor.getSDMItems()), Mapper.mapToStores(generatedDescriptor.getSDMStores()));
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public Stores getStores() {
        return stores;
    }

    public void setStores(Stores stores) {
        this.stores = stores;
    }

    @Override
    public String toString() {
        return "Descriptor{" +
                "sdmItems=" + items.toString() +
                ", sdmStores=" + stores.toString() +
                '}';
    }
}
