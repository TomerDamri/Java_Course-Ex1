package course.java.sdm.console;

import course.java.sdm.engine.EngineService;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        EngineService engineService = new EngineService();
        engineService.loadData("C:\\Users\\einav\\Downloads\\ex1-big.xml");
//        System.out.println(engineService.getItems());
        System.out.println(engineService.getStores());
    }
}
