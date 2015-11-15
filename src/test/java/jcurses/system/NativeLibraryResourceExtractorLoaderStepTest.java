package jcurses.system;


import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import java.io.*;

import static org.mockito.Matchers.argThat;
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
        NativeLibrary.ResourceExtractor.Extractor extractor =
                mock( NativeLibrary.ResourceExtractor.Extractor.class);
        NativeLibrary.ResourceExtractor resourceExtractor = new NativeLibrary.ResourceExtractor(extractor, extractionLocation, nextStep);

        resourceExtractor.load("/jcurses/test/afile.txt");

        File expectedFile = tempFile("afile.txt");
        verify(nextStep).load(expectedFile);
    }
    @Test
    public void givenResourcePath_ResourceStreamIsOpened() throws Exception {
        NativeLibrary.Loader<File> nextStep = mockLoader();
        File extractionLocation = tempDir();
        NativeLibrary.ResourceExtractor.Extractor extractor =
                mock( NativeLibrary.ResourceExtractor.Extractor.class);
        NativeLibrary.ResourceExtractor resourceExtractor =
                new NativeLibrary.ResourceExtractor(extractor, extractionLocation, nextStep);

        resourceExtractor.load("/jcurses/test/afile.txt");


        verify(extractor).extract(withContent("some data"));
    }

    private InputStream withContent(String content) {
        return argThat(new EqualsInputStream(content));
    }

    private static abstract class MemoizingTypeSafeDiagnosingMatcher<T> extends TypeSafeDiagnosingMatcher<T>{
        private Boolean memo = null;
        @Override
        protected boolean matchesSafely(T item, Description mismatchDescription) {
            if(memo == null){
                memo = doMatchSafely(item);
            }
            if(!memo){
                doDescribeMismatch(item, mismatchDescription);
            }
            return memo;
        }

        protected void doDescribeMismatch(T item, Description mismatchDescription) {
            mismatchDescription.appendText(" was ").appendValue(item);
        }
        protected abstract boolean doMatchSafely(T item);
    }

    private class EqualsInputStream extends MemoizingTypeSafeDiagnosingMatcher<InputStream> {
        private final String content;

        public EqualsInputStream(String content) {
            this.content = content;
        }

        @Override
        protected boolean doMatchSafely(InputStream inputStream) {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            String actualContent = readContentFrom(r);
            return content.equals(actualContent);
        }

        private String readContentFrom(BufferedReader r) {
            try {
                return r.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(content);

        }
    }

    private File tempDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    private File tempFile(String fileName) {
        return new File(System.getProperty("java.io.tmpdir"), fileName);
    }


}
