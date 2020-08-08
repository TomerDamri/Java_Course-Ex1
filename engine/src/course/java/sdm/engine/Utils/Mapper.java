package course.java.sdm.engine.Utils;

import course.java.sdm.engine.Schema.*;
import course.java.sdm.engine.Schema.Location;
import examples.jaxb.schema.generated.*;

public class Mapper {

    public static  Descriptor mapToDescriptor(SuperDuperMarketDescriptor generatedDescriptor){
        if(generatedDescriptor == null){
            return null;
        }

        return new Descriptor(generatedDescriptor);
    }

    public static Location mapToLocation(examples.jaxb.schema.generated.Location generatedLocation){
        if(generatedLocation == null){
            return null;
        }

        return new Location(generatedLocation);
    }

    public static Item mapToItem(SDMItem generatedItem){
        if(generatedItem == null){
            return null;
        }

        return new Item(generatedItem);
    }

    public static Items mapToItems(SDMItems generatedItems){
        if(generatedItems == null){
            return null;
        }

        return new Items(generatedItems);
    }

    public static Sell mapToSell(SDMSell generatedSell){
        if(generatedSell == null){
            return null;
        }

        return new Sell(generatedSell);
    }

    public static Prices mapToPrices(SDMPrices generatedPrices){
        if(generatedPrices == null){
            return null;
        }

        return new Prices(generatedPrices);
    }

    public static Store mapToStore(SDMStore generatedStore){
        if(generatedStore == null){
            return null;
        }

        return new Store(generatedStore);
    }

    public static Stores mapToStores(SDMStores generatedStores){
        if(generatedStores == null){
            return null;
        }

        return new Stores(generatedStores);
    }
}
