package course.java.sdm.engine;

import course.java.sdm.engine.Schema.Descriptor;
import course.java.sdm.engine.Utils.FileManager;
import course.java.sdm.engine.Utils.Validator;

import java.io.FileNotFoundException;

public class EngineService {

    private FileManager fileManager = new FileManager();
    private Validator validator = new Validator();

    public static void foo() {
        System.out.println("At engine#foo");
    }

    public Descriptor loadData(String xmlDataFileStr) throws FileNotFoundException {
        validator.validateFile(xmlDataFileStr);
        Descriptor descriptor = fileManager.loadData(xmlDataFileStr);
        validator.validateItemsAndStores(descriptor.getItems(), descriptor.getStores());

        return descriptor;
    }
}