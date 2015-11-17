package jcurses.system;


import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NativeLibraryResourcePathLookupLoaderStepTest {
    @Test
    public void givenOs_ResourcePathIsMapped() throws Exception {
        NativeLibrary.Loader<String> nextStep = mockLoader();

        NativeLibrary.ResourcePathLookup resourcePathLookup = new NativeLibrary.ResourcePathLookup(nextStep);
        resourcePathLookup.load(Os.Linux64);

        verify(nextStep).load("/META-INF/linux64/libjcurses64.so");
    }

    @Test
    public void givenAnotherOs_ResourcePathIsMapped() throws Exception {
        NativeLibrary.Loader<String> nextStep = mockLoader();

        NativeLibrary.ResourcePathLookup resourcePathLookup = new NativeLibrary.ResourcePathLookup(nextStep);
        resourcePathLookup.load(Os.Windows32);

        verify(nextStep).load("/META-INF/windows32/libjcurses.dll");
    }

    @Test
    public void givenWin64_ResourcePathIsMapped() throws Exception {
        NativeLibrary.Loader<String> nextStep = mockLoader();

        NativeLibrary.ResourcePathLookup resourcePathLookup = new NativeLibrary.ResourcePathLookup(nextStep);
        resourcePathLookup.load(Os.Windows64);

        verify(nextStep).load("/META-INF/windows64/libjcurses64.dll");
    }
    @SuppressWarnings("unchecked")
    private <T> NativeLibrary.Loader<T> mockLoader() {
        return mock(NativeLibrary.Loader.class);
    }
}
