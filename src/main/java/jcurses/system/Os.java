package jcurses.system;

import java.util.Properties;

public enum Os {
    Unknown, Windows32, Windows64, Linux64, Linux32;

    public static Os currentOs() {
        return currentOs(System.getProperties());
    }

    static Os currentOs(Properties sysProps) {
        final String currentOsName = sysProps.getProperty("os.name", "unknown");
        final String currentOsArch = sysProps.getProperty("os.arch");
        if (currentOsName.toLowerCase().indexOf("windows") >= 0) {
            if (is64Bit(currentOsArch)) {
                return Windows64;
            }
            if (is32bit(currentOsArch)) {
                return Windows32;
            }
        } else if (currentOsName.toLowerCase().indexOf("linux") >= 0) {
            if (is64Bit(currentOsArch)) {
                return Linux64;
            }
            if (is32bit(currentOsArch)) {
                return Linux32;
            }
        }
        return Unknown;
    }

    private static boolean is32bit(String currentOsArch) {
        return currentOsArch.toLowerCase().indexOf("x86") >= 0
            || currentOsArch.toLowerCase().indexOf("i386") >= 0;
    }

    private static boolean is64Bit(String currentOsArch) {
        return currentOsArch.toLowerCase().indexOf("amd64") >= 0;
    }
}
