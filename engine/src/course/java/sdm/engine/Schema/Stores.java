package course.java.sdm.engine.Schema;

import course.java.sdm.engine.Utils.Mapper;
import examples.jaxb.schema.generated.SDMStore;
import examples.jaxb.schema.generated.SDMStores;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Stores {

    Map<Integer, Store> stores;

    public Stores(Map<Integer, Store> stores) {
        this.stores = stores;
    }

    public Map<Integer, Store> getStores() {
        return stores;
    }

    public void setStores(Map<Integer, Store> stores) {
        this.stores = stores;
    }

//    // TODO: 07/08/2020 - change the toString implementation
//    @Override
//    public String toString() {
//        return "Stores{" +
//                "stores=" + stores +
//                '}';
//    }
}
