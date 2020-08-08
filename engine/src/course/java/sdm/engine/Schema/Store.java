package course.java.sdm.engine.Schema;


public class Store {

    private String name;
    private int deliveryPpk;
    private Location location;
    private Prices prices;
    private int id;

    public Store(String name, int deliveryPpk, Location location, Prices prices, int id) {
        this.name = name;
        this.deliveryPpk = deliveryPpk;
        this.location = location;
        this.prices = prices;
        this.id = id;
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

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
