package jcurses.system;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

public class ToolkitTest {
    @Rule
    public TestRule toolkitRule = new ToolkitResource();
    @Test
    public void bootstrap() throws Exception {
        Toolkit.getEncoding();
    }

}
