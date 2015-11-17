package jcurses.system;


import com.crudetech.matcher.MemoizingTypeSafeDiagnosingMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.EnumSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NativeLibraryResourcePathLookupLoaderStepTest {
    @Test
    public void givenLinux32_ResourcePathIsMapped() throws Exception {
        NativeLibrary.Loader<String> nextStep = mockLoader();

        NativeLibrary.ResourcePathLookup resourcePathLookup =
                new NativeLibrary.ResourcePathLookup(nextStep);
        resourcePathLookup.load(Os.Linux32);

        verify(nextStep).load("/META-INF/linux32/libjcurses.so");
    }

    @Test
    public void givenLinux64_ResourcePathIsMapped() throws Exception {
        NativeLibrary.Loader<String> nextStep = mockLoader();

        NativeLibrary.ResourcePathLookup resourcePathLookup =
                new NativeLibrary.ResourcePathLookup(nextStep);
        resourcePathLookup.load(Os.Linux64);

        verify(nextStep).load("/META-INF/linux64/libjcurses64.so");
    }

    @Test
    public void givenWin32_ResourcePathIsMapped() throws Exception {
        NativeLibrary.Loader<String> nextStep = mockLoader();

        NativeLibrary.ResourcePathLookup resourcePathLookup =
                new NativeLibrary.ResourcePathLookup(nextStep);
        resourcePathLookup.load(Os.Windows32);

        verify(nextStep).load("/META-INF/windows32/libjcurses.dll");
    }

    @Test
    public void givenWin64_ResourcePathIsMapped() throws Exception {
        NativeLibrary.Loader<String> nextStep = mockLoader();

        NativeLibrary.ResourcePathLookup resourcePathLookup =
                new NativeLibrary.ResourcePathLookup(nextStep);
        resourcePathLookup.load(Os.Windows64);

        verify(nextStep).load("/META-INF/windows64/libjcurses64.dll");
    }

    @Test
    public void forEveryOsIsAMappingDefined() throws Exception {
        EnumSet<Os> allKnownOs = EnumSet.complementOf(EnumSet.of(Os.Unknown));
        for (final Os os : allKnownOs) {
            NativeLibrary.ResourcePathLookup resourcePathLookup =
                    new NativeLibrary.ResourcePathLookup(
                            new NativeLibrary.Loader<String>() {
                                @Override
                                public void load(String resourcePath) {
                                    assertThat(String.format("Resource for %s", os), resourcePath, ResourceDoesExistMatcher.doesExist());
                                }
                            });
            resourcePathLookup.load(os);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> NativeLibrary.Loader<T> mockLoader() {
        return mock(NativeLibrary.Loader.class);
    }

    static class ResourceDoesExistMatcher extends MemoizingTypeSafeDiagnosingMatcher<String> {
        static Matcher<String> doesExist() {
            return new ResourceDoesExistMatcher();
        }

        @Override
        protected boolean doMatchSafely(String resourcePath) {
            return getClass().getResource(resourcePath) != null;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("resource does exist");
        }

        @Override
        protected void doDescribeMismatch(String item, Description mismatchDescription) {
            mismatchDescription.appendText(" no resource found at ").appendValue(item);
        }
    }
}
