package course.java.sdm.engine.Schema;

import course.java.sdm.engine.Utils.Mapper;
import examples.jaxb.schema.generated.SDMItem;
import examples.jaxb.schema.generated.SDMItems;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Items {
    private Map<Integer, Item> sdmItem;

    public Items(Map<Integer, Item> sdmItems) {
        this.sdmItem = sdmItems;
    }

    public Map<Integer, Item> getSdmItem() {
        return sdmItem;
    }

    public void setSdmItem(Map<Integer, Item> sdmItem) {
        this.sdmItem = sdmItem;
    }

//    // TODO: 07/08/2020 - change the toString implementation
//    @Override
//    public String toString() {
//        return "Items{" +
//                "sdmItem=" + sdmItem +
//                '}';
//    }
}
