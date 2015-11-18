package jcurses.demo;

import jcurses.widgets.DefaultLayoutManager;
import jcurses.widgets.Window;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * A simple demo application, that allows you to check if jcurses works on your system. Start up the
 * man function. If th application appears on the screen, the jcursess system is working. If not, the application
 * will trace out diagnostic messages.
 */
public class HelloWorldApp {
    public static void main(String[] args){
        HelloWorldApp app = new HelloWorldApp();
        System.exit(app.run(asList(args)));
    }

    private int run(List<String> args) {
        Window window = new Window(50, 30, true, "\"Hello, World!\" Application");
        DefaultLayoutManager mgr = new DefaultLayoutManager();
        mgr.bindToContainer(window.getRootPanel());
        window.show();

        return 0;
    }
}
