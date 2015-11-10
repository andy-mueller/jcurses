package jcurses.system;


import org.junit.Test;

import java.util.Properties;

import static org.mockito.Mockito.mock;

public class NativeLibraryTest {
    @Test
    public void givenProperties_OsIsLookedUp() throws Exception {
        Properties sysProps = new Properties();
        sysProps.setProperty("os.name", "Os w/ Linux in name");
        sysProps.setProperty("os.arch", "amd64");

//        OsLookup osLookup = new OsLooOsLookup()


        NativeLibrary.LoaderStep nextStep = mock(NativeLibrary.LoaderStep.class);
        //verify(nextStep).load(Os.Linux64);
    }
}
