package jcurses.system;


import org.junit.Test;

import java.io.*;

import static com.crudetech.matcher.EqualsInputStream.withContent;
import static com.crudetech.matcher.FileDoesExistMatcher.doesExist;
import static com.crudetech.matcher.FileHasContent.hasContent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
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
        File extractionLocation = TempFolder.tempDir();
        NativeLibrary.ResourceExtractor.Extractor extractor =
                mock(NativeLibrary.ResourceExtractor.Extractor.class);
        NativeLibrary.ResourceExtractor resourceExtractor = new NativeLibrary.ResourceExtractor(extractor, extractionLocation, nextStep);

        resourceExtractor.load("/jcurses/test/afile.txt");

        File expectedFile = TempFolder.tempFile("afile.txt");
        verify(nextStep).load(expectedFile);
    }

    @Test
    public void givenResourcePath_ResourceStreamIsOpened() throws Exception {
        NativeLibrary.Loader<File> nextStep = mockLoader();
        File extractionLocation = TempFolder.tempDir();
        NativeLibrary.ResourceExtractor.Extractor extractor =
                mock(NativeLibrary.ResourceExtractor.Extractor.class);
        NativeLibrary.ResourceExtractor resourceExtractor =
                new NativeLibrary.ResourceExtractor(extractor, extractionLocation, nextStep);

        resourceExtractor.load("/jcurses/test/afile.txt");


        verify(extractor).extract(withContent("some data"), eq(extractionLocation));
    }

    @Test
    public void givenStreamAndDestination_FileIsWritten() throws Exception {
        File destination = TempFolder.tempFile();
        NativeLibrary.ResourceExtractor.Extractor extractor =
                new NativeLibrary.ResourceExtractor.Extractor();

        InputStream stream = new StringInputStream("some text");
        extractor.extract(stream, destination);

        assertThat(destination, doesExist());
        assertThat(destination, hasContent("some text"));
    }


    private static class StringInputStream extends ByteArrayInputStream {
        public StringInputStream(String content) throws UnsupportedEncodingException {
            super(content.getBytes("UTF-8"));
        }
    }


}
