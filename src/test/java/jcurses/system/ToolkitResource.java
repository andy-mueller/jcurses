package jcurses.system;

import org.junit.rules.ExternalResource;

class ToolkitResource extends ExternalResource {
    @Override
    protected void before() throws Throwable {
        Toolkit.init();
    }

    @Override
    protected void after() {
        Toolkit.shutdown();
    }
}
