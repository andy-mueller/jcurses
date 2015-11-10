package jcurses.system;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class OsTest {
    private final Properties sysProps;
    private final Os expected;

    @Parameterized.Parameters
    public static Object[][] data(){
        return new Object[][]{
                {"Os w/ Linux in name", "amd64", Os.Linux64},
                {"Os w/ Linux in name", "i386", Os.Linux32},
                {"Os w/ Windows in name", "x86", Os.Windows32},
                {"Os w/ Windows in name", "amd64", Os.Windows64},
        };
    }
    public OsTest(String osName, String osArch, Os expected){
        this.expected = expected;
        sysProps = new Properties();
        sysProps.setProperty("os.name", osName);
        sysProps.setProperty("os.arch", osArch);
    }
    @Test
    public void detectOs() throws Exception {
        assertThat(Os.currentOs(sysProps), is(expected));
    }
}
