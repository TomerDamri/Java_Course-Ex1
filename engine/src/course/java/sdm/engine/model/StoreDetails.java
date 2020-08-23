package course.java.sdm.engine.model;

public class StoreDetails {
    private final int id;
    private final String name;
    private final int deliveryPpk;
    private final Location location;

    public StoreDetails (int id, String name, int deliveryPpk, Location location) {
        this.id = id;
        this.name = name;
        this.deliveryPpk = deliveryPpk;
        this.location = location;
    }

    public final int getId () {
        return id;
    }

    public final String getName () {
        return name;
    }

    public final int getDeliveryPpk () {
        return deliveryPpk;
    }

    public final Location getLocation () {
        return location;
    }
}