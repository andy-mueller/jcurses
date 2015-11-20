package jcurses.demo;

import jcurses.event.ItemEvent;
import jcurses.event.ItemListener;
import jcurses.system.CharColor;
import jcurses.widgets.*;

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

        MenuList menu = new MenuList();
        menu.setTitle("File");
        menu.add("Exit");
        menu.setTitleColors(new CharColor(menu.getTitleColors().getBackground(), CharColor.NORMAL));
        menu.addListener(new ItemListener() {
            @Override
            public void stateChanged(ItemEvent event) {
                if("Exit".equalsIgnoreCase((String) event.getItem())){

                }
            }
        });
        mgr.addWidget(menu, 0, 0, 50, 3, WidgetsConstants.ALIGNMENT_TOP, WidgetsConstants.ALIGNMENT_LEFT);

        TextArea txt = new TextArea(15, 2, "Example");
        txt.setCursorColors(new CharColor(CharColor.RED, CharColor.WHITE));
        mgr.addWidget(txt, 1, 5, 15, 5, WidgetsConstants.ALIGNMENT_TOP, WidgetsConstants.ALIGNMENT_CENTER);

        jcurses.widgets.TextField txt2 = new TextField(10, "edit me");
        txt2.setCursorColors(new CharColor(CharColor.RED, CharColor.WHITE));
        mgr.addWidget(txt2, 1, 15, 12, 3, WidgetsConstants.ALIGNMENT_TOP, WidgetsConstants.ALIGNMENT_CENTER);

        window.show();

        return 0;
    }
}
