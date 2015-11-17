package jcurses.system;

import java.io.*;
import java.util.Properties;

public class NativeLibrary {
    static public void load() {
        Loader<Properties> loaderChain =
                new OsLookup(
                        new ResourcePathLookup(
                                new ResourceExtractor(computeLibraryExtractionLocation(),
                                        new SharedObjectLoader()
                                )
                        )
                );

        loaderChain.load(System.getProperties());
    }

    private static File computeLibraryExtractionLocation() {
        if (System.getProperty("jcurses.lib.dir") != null) {
            return new File(System.getProperty("jcurses.lib.dir"));
        } else {
            return new File(System.getProperty("java.io.tmpdir"));
        }
    }

    interface Loader<TKey> {
        void load(TKey key);
    }

    static abstract class LoaderStep<TThisKey, TNextKey> implements Loader<TThisKey> {
        private final Loader<TNextKey> nextStep;

        LoaderStep(Loader<TNextKey> nextStep) {
            this.nextStep = nextStep;
        }

        @Override
        public void load(TThisKey key) {
            nextStep.load(loadThisStep(key));
        }

        protected abstract TNextKey loadThisStep(TThisKey key);

    }

    static class OsLookup extends LoaderStep<Properties, Os> {
        public OsLookup(Loader<Os> nextStep) {
            super(nextStep);
        }

        @Override
        protected Os loadThisStep(Properties properties) {
            return Os.currentOs(properties);
        }

    }

    static class ResourcePathLookup extends LoaderStep<Os, String> {
        public ResourcePathLookup(Loader<String> nextStep) {
            super(nextStep);
        }

        @Override
        protected String loadThisStep(Os os) {
            return computeResourcePath(os);
        }

        private String computeResourcePath(Os os) {
            switch (os) {
                case Windows32:
                    return "/META-INF/windows32/libjcurses.dll";
                case Windows64:
                    return "/META-INF/windows64/libjcurses64.dll";
                case Linux64:
                    return "/META-INF/linux64/libjcurses64.so";
                case Linux32:
                    return "/META-INF/linux32/libjcurses.so";
                case Unknown:
                default:
                    return noLibraryForOsOfType(os);
            }
        }

        private String noLibraryForOsOfType(Os os) {
            return "NO_PATH_FOR_OS_TYPE:" + os;
        }
    }

    static class ResourceExtractor extends LoaderStep<String, File> {
        private final Extractor extractor;
        private final File extractionLocation;


        public ResourceExtractor(Extractor extractor, File extractionLocation, Loader<File> nextStep) {
            super(nextStep);
            this.extractor = extractor;
            this.extractionLocation = extractionLocation;
        }

        public ResourceExtractor(File extractionLocation, Loader<File> nextStep) {
            this(new Extractor(), extractionLocation, nextStep);
        }


        @Override
        protected File loadThisStep(String resourcePath) {
            final String fileName = computeFileNameFromResourcePath(resourcePath);
            final InputStream resourceStream = getClass().getResourceAsStream(resourcePath);
            final File destinationFile = new File(extractionLocation, fileName);
            extractor.extract(resourceStream, destinationFile);
            return destinationFile;
        }

        private String computeFileNameFromResourcePath(String resourcePath) {
            return resourcePath.substring(resourcePath.lastIndexOf('/') + 1);
        }

        static class Extractor {
            public void extract(InputStream content, File destination) {
                try {
                    doExtract(content, destination);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            private void doExtract(InputStream content, File destination) throws IOException {
                try (FileOutputStream fos = new FileOutputStream(destination)) {
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = content.read(buffer)) >= 0) {
                        fos.write(buffer, 0, read);
                    }
                }
            }
        }
    }

    static class SharedObjectLoader implements Loader<File> {
        private final Runtime runtime;

        public SharedObjectLoader(Runtime runtime) {
            this.runtime = runtime;
        }

        public SharedObjectLoader() {
            this(Runtime.getRuntime());
        }

        @Override
        public void load(File pathToSharedObject) {
            runtime.load(pathToSharedObject.getAbsolutePath());
        }
    }
}
