package course.java.sdm.engine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import course.java.sdm.engine.mapper.GeneratedDataMapper;
import course.java.sdm.engine.model.Descriptor;
import course.java.sdm.engine.model.Item;
import course.java.sdm.engine.model.Store;
import examples.jaxb.schema.generated.SuperDuperMarketDescriptor;

public class FileManager {

    private final static String JAXB_XML_PACKAGE_NAME = "examples.jaxb.schema.generated";
    private final static GeneratedDataMapper GENERATED_DATA_MAPPER = new GeneratedDataMapper();
    private final static Validator validator = new Validator();

    private static FileManager singletonFileManager = null;

    private FileManager () {
    }

    public static FileManager getFileManager () {
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
        Map<Integer, Item> items = GENERATED_DATA_MAPPER.generatedItemsToItems(superDuperMarketDescriptor.getSDMItems());
        Map<Integer, Store> stores = GENERATED_DATA_MAPPER.generatedStoresToStores(superDuperMarketDescriptor.getSDMStores(), items);
        validator.validateItemsAndStores(items, stores);

        return GENERATED_DATA_MAPPER.toDescriptor(items, stores);
    }

    private SuperDuperMarketDescriptor deserializeFrom (InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) u.unmarshal(in);
    }
}
