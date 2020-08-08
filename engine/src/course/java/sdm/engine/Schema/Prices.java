package course.java.sdm.engine.Schema;

import course.java.sdm.engine.Utils.Mapper;
import examples.jaxb.schema.generated.SDMPrices;
import examples.jaxb.schema.generated.SDMSell;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Prices {

    private Map<Integer, Sell> sdmSells;

    public Prices(Map<Integer, Sell> sdmSells) {
        this.sdmSells = sdmSells;
    }

    // if id duplication exist,toMap method will throw IllegalStateException (link: https://www.baeldung.com/java-list-to-map)
    public Prices(SDMPrices generatedPrices) {
            Map<Integer, Sell> map = generatedPrices.getSDMSell().stream().filter(Objects::nonNull).collect(Collectors.toMap(SDMSell::getItemId, Mapper::mapToSell));
            if(map.keySet().size() != generatedPrices.getSDMSell().size()){
//                throw appropriate exception
            }
            new Prices(map);
    }

    public Map<Integer, Sell> getSdmSells() {
        return sdmSells;
    }

    public void setSdmSells(Map<Integer, Sell> sdmSells) {
        this.sdmSells = sdmSells;
    }

    // TODO: 07/08/2020 - change the toString implementation
    @Override
    public String toString() {
        return "Prices{" +
                "sdmSells=" + sdmSells +
                '}';
    }
}
