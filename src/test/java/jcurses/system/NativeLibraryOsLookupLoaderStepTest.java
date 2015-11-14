package jcurses.system;

import org.junit.Test;

import java.util.Properties;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NativeLibraryOsLookupLoaderStepTest {
    @Test
    public void givenPropertiesForLinux_OsIsLookedUp() throws Exception {
        Properties sysProps = new Properties();
        sysProps.setProperty("os.name", "Os w/ Linux in name");
        sysProps.setProperty("os.arch", "amd64");

        NativeLibrary.Loader<Os> nextStep = mockLoader();
        NativeLibrary.OsLookup osLookup = new NativeLibrary.OsLookup(nextStep);

        osLookup.load(sysProps);

        verify(nextStep).load(Os.Linux64);
    }

    @Test
    public void givenPropertiesForWin_OsIsLookedUp() throws Exception {
        Properties sysProps = new Properties();
        sysProps.setProperty("os.name", "Os w/ Windows in name");
        sysProps.setProperty("os.arch", "amd64");

        NativeLibrary.Loader<Os> nextStep = mockLoader();
        NativeLibrary.OsLookup osLookup = new NativeLibrary.OsLookup(nextStep);

        osLookup.load(sysProps);

        verify(nextStep).load(Os.Windows64);
    }

    @SuppressWarnings("unchecked")
    private <T> NativeLibrary.Loader<T> mockLoader() {
        return mock(NativeLibrary.Loader.class);
    }
}
