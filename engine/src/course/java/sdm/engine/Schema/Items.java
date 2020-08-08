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

    // if id duplication exist,toMap method will throw IllegalStateException (link: https://www.baeldung.com/java-list-to-map)
    public Items(SDMItems generatedSDMItems) {
            Map<Integer, Item> map = generatedSDMItems.getSDMItem().stream().filter(Objects::nonNull)
                    .collect(Collectors.toMap(SDMItem::getId, Mapper::mapToItem));
        if(map.keySet().size() != generatedSDMItems.getSDMItem().size()){
//                throw appropriate exception
        }
        new Items(map);
    }

    public Map<Integer, Item> getSdmItem() {
        return sdmItem;
    }

    public void setSdmItem(Map<Integer, Item> sdmItem) {
        this.sdmItem = sdmItem;
    }

    // TODO: 07/08/2020 - change the toString implementation
    @Override
    public String toString() {
        return "Items{" +
                "sdmItem=" + sdmItem +
                '}';
    }
}
