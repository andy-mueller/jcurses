package jcurses.system;

import java.io.File;
import java.util.UUID;

class TempFolder {
    public static File tempDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    public static File tempFile(String fileName) {
        return new File(System.getProperty("java.io.tmpdir"), fileName);
    }

    public static File tempFile() {
        return tempFile(UUID.randomUUID().toString());
    }
}
