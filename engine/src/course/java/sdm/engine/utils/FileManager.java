package course.java.sdm.engine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import course.java.sdm.engine.mapper.Mapper;
import course.java.sdm.engine.model.Descriptor;
import course.java.sdm.engine.model.Items;
import course.java.sdm.engine.model.Stores;
import examples.jaxb.schema.generated.SuperDuperMarketDescriptor;

public class FileManager {

    private final static String JAXB_XML_PACKAGE_NAME = "examples.jaxb.schema.generated";
    private final static Mapper mapper = new Mapper();
    private final static Validator validator = new Validator();

    private static FileManager singletonFileManager = null;

    private FileManager () {
    }

    public static FileManager getFileManager() {
        if (singletonFileManager == null) {
            singletonFileManager = new FileManager();
        }

        return singletonFileManager;
    }

    public SuperDuperMarketDescriptor generateDataFromXmlFile (String xml_file_path) throws FileNotFoundException {
        validator.validateFile(xml_file_path);
        InputStream inputStream = new FileInputStream(new File(xml_file_path));
        SuperDuperMarketDescriptor superDuperMarketDescriptor = null;
        try {
            superDuperMarketDescriptor = deserializeFrom(inputStream);
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
        return superDuperMarketDescriptor;
    }

    public Descriptor loadDataFromGeneratedData (SuperDuperMarketDescriptor superDuperMarketDescriptor) {
        Items items = mapper.generatedItemsToItems(superDuperMarketDescriptor.getSDMItems());
        Stores stores = mapper.generatedStoresToStores(superDuperMarketDescriptor.getSDMStores(), items);
        validator.validateItemsAndStores(items, stores);

        return mapper.toDescriptor(items, stores);
    }

    private SuperDuperMarketDescriptor deserializeFrom (InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) u.unmarshal(in);
    }
}
