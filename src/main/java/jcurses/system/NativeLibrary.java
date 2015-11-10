package jcurses.system;

import java.io.*;

public class NativeLibrary {

    public static final String NO_LIB_FOR_PLATFORM_DEFINED = "NO_LIB_FOR_PLATFORM_DEFINED";

    public static void load() {
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
//        if (Os.isWindowsX86()) {
//            return "/META-INF/windows-x86/libjcurses.dll";
//        } else if (Os.isMacOsx()) {
//            return "/META-INF/osx/libjcurses.jnilib";
//        } else if (Os.isLinuxX86()) {
//            return "/META-INF/linux-x86/libjcurses.so";
//        }
        return NO_LIB_FOR_PLATFORM_DEFINED;
    }

    private static File computeLibraryExtractionLocation() {
        if (System.getProperty("jcurses.lib.dir") != null) {
            return new File(System.getProperty("jcurses.lib.dir"));
        } else {
            return new File(System.getProperty("java.io.tmpdir"));
        }
    }
}
