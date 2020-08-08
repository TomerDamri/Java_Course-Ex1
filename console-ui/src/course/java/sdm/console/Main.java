package course.java.sdm.console;

import course.java.sdm.engine.Engine;
import course.java.sdm.engine.Schema.Descriptor;

public class Main {

    public static void main(String[] args) {
        Engine.foo();
        Engine engine = new Engine();
        Descriptor descriptor = engine.loadData("/resources/ex1-small.xml");
        System.out.println(descriptor.getItems().toString());
        System.out.println(descriptor.getStores().toString());
    }
}
