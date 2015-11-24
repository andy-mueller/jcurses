package com.crudetech.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Arrays;

public class FileHasContent extends MemoizingTypeSafeDiagnosingMatcher<File> {
    private final String content;
    private final Charset encoding;

    public FileHasContent(String content, Charset encoding) {
        this.content = content;
        this.encoding = encoding;
    }

    public FileHasContent(String content) {
        this(content, Charset.forName("UTF-8"));
    }

    public static Matcher<? super File> hasContent(String content) {
        return new FileHasContent(content);
    }

    @Override
    protected boolean doMatchSafely(File item) {
        try {
            return match(item);
        } catch (IOException e) {
            return false;
        }
    }

    private boolean match(File item) throws IOException {
        RandomAccessFile f = new RandomAccessFile(item, "r");
        byte[] bytes = new byte[(int)f.length()];
        f.readFully(bytes);
        return Arrays.equals(bytes, content.getBytes(encoding));
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(content);
    }

}
