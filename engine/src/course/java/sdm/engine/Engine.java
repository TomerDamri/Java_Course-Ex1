package course.java.sdm.engine;

import course.java.sdm.engine.Schema.Descriptor;
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



    private SuperDuperMarketDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) u.unmarshal(in);
    }
}