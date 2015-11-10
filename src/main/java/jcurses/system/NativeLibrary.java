package jcurses.system;

import java.io.*;
import java.util.Properties;

public class NativeLibrary {

    public static final String NO_LIB_FOR_PLATFORM_DEFINED = "NO_LIB_FOR_PLATFORM_DEFINED";

    public NativeLibrary(Loader loader, Properties sysProps) {

    }

    public void load() {
        String libraryResourcePath = getLibraryResourcePathForPlatform();
        if (NO_LIB_FOR_PLATFORM_DEFINED.equals(libraryResourcePath)) {
            raiseNoLibraryPackagedError();
        }

        File libraryExtractionLocation = computeLibraryExtractionLocation();
        File extractedLibrary = extractLibraryFromResourcesToDisc(libraryResourcePath, libraryExtractionLocation);

        if (extractedLibrary != null) {
            System.load(extractedLibrary.getAbsolutePath());
            return;
        }

        System.loadLibrary("jcurses");
    }

    private static void raiseNoLibraryPackagedError() {
        throw new RuntimeException("There is native library for your OS packaged w/ this library");
    }

    private static File extractLibraryFromResourcesToDisc(String libraryResourcePath, File dir) {
        InputStream nativeLibraryContent = NativeLibrary.class.getResourceAsStream(libraryResourcePath);

        if (nativeLibraryContent == null) {
            System.err.println("no native library for platform '" + libraryResourcePath + "'");
            return null;
        }

        String libraryFileName = libraryResourcePath.substring(libraryResourcePath.lastIndexOf('/'));
        try {
            return streamToFile(dir, nativeLibraryContent, libraryFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File streamToFile(File dir, InputStream is, String libraryFileName) throws IOException {
        File f = new File(dir, libraryFileName);
        if (!f.exists()) {


            try (FileOutputStream fos = new FileOutputStream(f)) {
                byte[] buffy = new byte[1024];
                int read = 0;
                while ((read = is.read(buffy)) >= 0) {
                    fos.write(buffy, 0, read);
                }
            }
        }
        return f;
    }

    private static String getLibraryResourcePathForPlatform() {
        switch (Os.currentOs()){

            case Unknown:
                return NO_LIB_FOR_PLATFORM_DEFINED;
            case Windows32:
                return "/META-INF/windows32/libjcurses.dll";
            case Windows64:
                return "/META-INF/windows64/libjcurses64.dll";
            case Linux64:
                return "/META-INF/linux64/libjcurses64.so";
            case Linux32:
                return "/META-INF/linux32/libjcurses.so";
            default:
                return NO_LIB_FOR_PLATFORM_DEFINED;
        }
    }

    private static File computeLibraryExtractionLocation() {
        if (System.getProperty("jcurses.lib.dir") != null) {
            return new File(System.getProperty("jcurses.lib.dir"));
        } else {
            return new File(System.getProperty("java.io.tmpdir"));
        }
    }

    public interface Loader {
        void load(String resourcePath);
    }

    public interface LoaderStep {
        void load(Os linux64);
    }
}