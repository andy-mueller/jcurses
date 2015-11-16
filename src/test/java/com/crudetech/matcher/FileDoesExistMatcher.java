package com.crudetech.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.io.File;

public class FileDoesExistMatcher extends MemoizingTypeSafeDiagnosingMatcher<File> {
    public static Matcher<File> doesExist() {
        return new FileDoesExistMatcher();
    }

    @Override
    protected boolean doMatchSafely(File file) {
        return file.exists();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("File does exist");
    }

    @Override
    protected void doDescribeMismatch(File item, Description mismatchDescription) {
        String spaces = "\n          ";
        mismatchDescription
                .appendValue(item)
                .appendText(spaces)
                .appendText("was not found");
    }
}
