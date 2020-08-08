package course.java.sdm.engine;

import course.java.sdm.engine.Schema.Descriptor;
import course.java.sdm.engine.Utils.Mapper;
import examples.jaxb.schema.generated.SuperDuperMarketDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class Engine {

    private final static String JAXB_XML_PACKAGE_NAME = "examples.jaxb.schema.generated";

    public static void foo() {
        System.out.println("At engine#foo");
    }

    public Descriptor loadData(String dataPath){
        InputStream inputStream = Engine.class.getResourceAsStream(dataPath);
        Mapper mapper = new Mapper();
        Descriptor descriptor = null;
        try {
            SuperDuperMarketDescriptor superDuperMarketDescriptor = deserializeFrom(inputStream);
            descriptor = mapper.mapToDescriptor(superDuperMarketDescriptor);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return descriptor;
    }



    private SuperDuperMarketDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) u.unmarshal(in);
    }
}