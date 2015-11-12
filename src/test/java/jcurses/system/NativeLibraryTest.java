package jcurses.system;


import org.junit.Test;

import java.io.File;
import java.util.Properties;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NativeLibraryTest {
    @Test
    public void givenPropertiesForLinux_OsIsLookedUp() throws Exception {
        Properties sysProps = new Properties();
        sysProps.setProperty("os.name", "Os w/ Linux in name");
        sysProps.setProperty("os.arch", "amd64");

        Loader<Os> nextStep = mockLoader();
        OsLookup osLookup = new OsLookup(nextStep);

        osLookup.load(sysProps);

        verify(nextStep).load(Os.Linux64);
    }

    @Test
    public void givenPropertiesForWin_OsIsLookedUp() throws Exception {
        Properties sysProps = new Properties();
        sysProps.setProperty("os.name", "Os w/ Windows in name");
        sysProps.setProperty("os.arch", "amd64");

        Loader<Os> nextStep = mockLoader();
        OsLookup osLookup = new OsLookup(nextStep);

        osLookup.load(sysProps);

        verify(nextStep).load(Os.Windows64);
    }

    @SuppressWarnings("unchecked")
    private <T> Loader<T> mockLoader() {
        return mock(Loader.class);
    }

    @Test
    public void givenOs_ResourcePathIsMapped() throws Exception {
        Loader<String> nextStep = mockLoader();

        ResourcePathLookup resourcePathLookup = new ResourcePathLookup(nextStep);
        resourcePathLookup.load(Os.Linux64);

        verify(nextStep).load("/META-INF/linux64/libjcurses64.so");
    }


    @Test
    public void givenResourcePath_ExtractedResourceFileIsPassedOn() throws Exception {
        Loader<File> nextStep = mockLoader();

        File extractionLocation = tempDir();
        ResourceExtractor resourceExtractor = new ResourceExtractor(extractionLocation, nextStep);
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

    public interface Loader<TKey> {
        void load(TKey key);
    }

    static class OsLookup implements Loader<Properties>{
        private final Loader<Os> nextStep;
        public OsLookup(Loader<Os> nextStep) {
            this.nextStep = nextStep;
        }
        @Override
        public void load(Properties sysProps) {
            nextStep.load(Os.currentOs(sysProps));
        }
    }

    private class ResourcePathLookup implements Loader<Os>{
        private final Loader<String> nextStep;

        public ResourcePathLookup(Loader<String> nextStep) {
            this.nextStep = nextStep;
        }
        @Override
        public void load(Os os) {
            nextStep.load("/META-INF/linux64/libjcurses64.so");
        }
    }

    private class ResourceExtractor implements Loader<String>{
        private final File extractionLocation;
        private final Loader<File> nextStep;

        public ResourceExtractor(File extractionLocation, Loader<File> nextStep) {
            this.extractionLocation = extractionLocation;
            this.nextStep = nextStep;
        }

        @Override
        public void load(String resourcePath) {
            String fileName = computeFileNameFromResourcePath(resourcePath);
            File extractedFile = new File(extractionLocation, fileName);
            nextStep.load(extractedFile);
        }

        private String computeFileNameFromResourcePath(String resourcePath) {
            return resourcePath.substring(resourcePath.lastIndexOf('/')+1);
        }
    }
}
