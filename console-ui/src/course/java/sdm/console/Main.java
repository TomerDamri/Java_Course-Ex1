package course.java.sdm.console;

import course.java.sdm.engine.EngineService;
import course.java.sdm.engine.Schema.Descriptor;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        EngineService engineService = new EngineService();
        Descriptor descriptor = engineService.loadData("C:\\Users\\97205\\Downloads\\ex1-big.xml");
        System.out.println("Bye");
    }
}
