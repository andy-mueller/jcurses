package jcurses.system;


import jcurses.widgets.Window;
import org.junit.Test;

public class ToolkitTest {
    @Test
    public void bootstrap() throws Exception {
        Toolkit.getEncoding();
        Window w = new Window(2,2,false, "None");
        w.show();
        w.close();
    }

}
