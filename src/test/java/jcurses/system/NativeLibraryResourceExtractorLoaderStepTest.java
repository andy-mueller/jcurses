package jcurses.system;


import org.junit.Test;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NativeLibraryResourceExtractorLoaderStepTest {
    @SuppressWarnings("unchecked")
    private <T> NativeLibrary.Loader<T> mockLoader() {
        return mock(NativeLibrary.Loader.class);
    }



    @Test
    public void givenResourcePath_ExtractedResourceFileIsPassedOn() throws Exception {
        NativeLibrary.Loader<File> nextStep = mockLoader();

        File extractionLocation = tempDir();
        NativeLibrary.ResourceExtractor resourceExtractor = new NativeLibrary.ResourceExtractor(extractionLocation, nextStep);
        resourceExtractor.load("/jcurses/test/afile.txt");

        File expectedFile = tempFile("afile.txt");
        verify(nextStep).load(expectedFile);
    }

    private File tempDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    private File tempFile(String fileName) {
        return new File(System.getProperty("java.io.tmpdir"), fileName);
    }

}
