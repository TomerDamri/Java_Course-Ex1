package course.java.sdm.engine.Schema;


import course.java.sdm.engine.Utils.Mapper;
import examples.jaxb.schema.generated.SDMStore;

public class Store {

    private String name;
    private int deliveryPpk;
    private Location location;
    private Prices sdmPrices;
    private int id;

    public Store(String name, int deliveryPpk, Location location, Prices sdmPrices, int id) {
        this.name = name;
        this.deliveryPpk = deliveryPpk;
        this.location = location;
        this.sdmPrices = sdmPrices;
        this.id = id;
    }

    public Store(SDMStore generatedStore) {
        new Store(generatedStore.getName(),
                generatedStore.getDeliveryPpk(),
                Mapper.mapToLocation(generatedStore.getLocation()),
                Mapper.mapToPrices(generatedStore.getSDMPrices()),
                generatedStore.getId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeliveryPpk() {
        return deliveryPpk;
    }

    public void setDeliveryPpk(int deliveryPpk) {
        this.deliveryPpk = deliveryPpk;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Prices getSdmPrices() {
        return sdmPrices;
    }

    public void setSdmPrices(Prices sdmPrices) {
        this.sdmPrices = sdmPrices;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    // TODO: 07/08/2020 - change the toString implementation
    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", deliveryPpk=" + deliveryPpk +
                ", location=" + location.toString() +
                ", sdmPrices=" + sdmPrices.toString() +
                ", id=" + id +
                '}';
    }
}
