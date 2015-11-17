package jcurses.system;

import org.junit.Test;

public class NativeLibraryTest {
    @Test
    public void bootstrap() throws Exception {
        NativeLibrary.load();
    }
}
