package jcurses.system;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;

public class ToolkitTest {
    @Rule
    public TestRule toolkitRule = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            Toolkit.init();
        }

        @Override
        protected void after() {
           Toolkit.shutdown();
        }
    };
    @Test
    public void bootstrap() throws Exception {
        Toolkit.getEncoding();
    }
}
