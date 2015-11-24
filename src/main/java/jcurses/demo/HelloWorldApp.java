package jcurses.demo;

import jcurses.event.ItemEvent;
import jcurses.event.ItemListener;
import jcurses.event.ValueChangedEvent;
import jcurses.event.ValueChangedListener;
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
    public static void main(String[] args) throws Exception{
        HelloWorldApp app = new HelloWorldApp();
        System.exit(app.run(asList(args)));
    }

    private int run(List<String> args) throws Exception {
        Window window = new MainWindow(50, 30);
        window.show();
        Thread.sleep(5000L);
        return 0;
    }
    static class MainWindow extends Window {
        private final DefaultLayoutManager layoutManager;
        private TextField textInput;

        MainWindow(int x, int y) {
            super(x, y, true, "Tic Tac Toe");
            layoutManager = createLayoutManager();
            this.textInput = new TextField();
            textInput.addListener(new ValueChangedListener() {
                @Override
                public void valueChanged(ValueChangedEvent event) {
                }
            });
            installMenu(layoutManager);
        }


        private void installMenu(DefaultLayoutManager layoutManager) {
            final String NewEasyGame = "New Easy Game";
            final String NewAdvancedGame = "New Advanced Game";
            final String Exit = "Exit";

            MenuList menu = new MenuList();
            menu.add(NewEasyGame);
            menu.add(NewAdvancedGame);
            menu.add(Exit);
            menu.setSelectable(true);
            menu.setTitle("Games");
            menu.setTitleColors(new CharColor(menu.getTitleColors().getBackground(), CharColor.NORMAL));
            layoutManager.addWidget(menu, 0, 0, 50, 3, WidgetsConstants.ALIGNMENT_TOP, WidgetsConstants.ALIGNMENT_LEFT);
            menu.addListener(new ItemListener() {
                @SuppressWarnings({"CallToStringEqualsIgnoreCase"})
                @Override
                public void stateChanged(ItemEvent itemEvent) {
                    String item = (String) itemEvent.getItem();
                    if (NewEasyGame.equalsIgnoreCase(item)) {
                        onMnuNewEasyGame();
                    } else if (NewAdvancedGame.equalsIgnoreCase(item)) {
                        onMnuNewAdvancedGame();
                    } else if (Exit.equalsIgnoreCase(item)) {
                    }
                }
            });
        }

        private DefaultLayoutManager createLayoutManager() {
            DefaultLayoutManager mgr = new DefaultLayoutManager();
            mgr.bindToContainer(getRootPanel());
            return mgr;
        }

        private void onMnuNewEasyGame() {

        }

        private void onMnuNewAdvancedGame() {
        }

    }
}
