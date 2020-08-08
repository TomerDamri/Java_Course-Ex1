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

    // if id duplication exist,toMap method will throw IllegalStateException (link: https://www.baeldung.com/java-list-to-map)
    public Stores(SDMStores generatedStores) {
            Map<Integer, Store> map = generatedStores.getSDMStore().stream().filter(Objects::nonNull)
                    .collect(Collectors.toMap(SDMStore::getId, Mapper::mapToStore));
        if(map.keySet().size() != generatedStores.getSDMStore().size()){
//                throw appropriate exception
        }
            new Stores(map);
    }

    public Map<Integer, Store> getStores() {
        return stores;
    }

    public void setStores(Map<Integer, Store> stores) {
        this.stores = stores;
    }

    // TODO: 07/08/2020 - change the toString implementation
    @Override
    public String toString() {
        return "Stores{" +
                "stores=" + stores +
                '}';
    }
}
