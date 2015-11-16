package com.crudetech.matcher;

import org.hamcrest.Description;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.mockito.Matchers.argThat;

public class EqualsInputStream extends MemoizingTypeSafeDiagnosingMatcher<InputStream> {
    public static InputStream withContent(String content) {
        return argThat(new EqualsInputStream(content));
    }

    private final String content;

    public EqualsInputStream(String content) {
        this.content = content;
    }

    @Override
    protected boolean doMatchSafely(InputStream inputStream) {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        String actualContent = readContentFrom(r);
        return content.equals(actualContent);
    }

    private String readContentFrom(BufferedReader r) {
        try {
            return r.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(content);

    }
}
