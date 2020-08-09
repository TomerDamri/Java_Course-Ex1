package course.java.sdm.engine.Utils;

import course.java.sdm.engine.schema.Descriptor;
import examples.jaxb.schema.generated.SuperDuperMarketDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileManager {

    private final static String JAXB_XML_PACKAGE_NAME = "examples.jaxb.schema.generated";

    public Descriptor loadData(String xml_file_path) throws FileNotFoundException {
        InputStream inputStream =  new FileInputStream(new File(xml_file_path));
        Mapper mapper = new Mapper();
        Descriptor descriptor = null;
        try {
            SuperDuperMarketDescriptor superDuperMarketDescriptor = deserializeFrom(inputStream);
            descriptor = mapper.mapToDescriptor(superDuperMarketDescriptor);
        }
        catch (JAXBException e) {
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
