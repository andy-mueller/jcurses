package jcurses.system;

import org.junit.Test;


import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NativeLibrarySharedObjectLoaderStepTest {
    @Test
    public void xox() throws Exception {
        Runtime rt = mock(Runtime.class);
        NativeLibrary.SharedObjectLoader loader =
                new NativeLibrary.SharedObjectLoader(rt);

        File pathToSharedObject = TempFolder.tempFile();
        loader.load(pathToSharedObject);

        verify(rt).load(pathToSharedObject.getAbsolutePath());
    }
    @SuppressWarnings("unchecked")
    private <T> NativeLibrary.Loader<T> mockLoader() {
        return mock(NativeLibrary.Loader.class);
    }

}
