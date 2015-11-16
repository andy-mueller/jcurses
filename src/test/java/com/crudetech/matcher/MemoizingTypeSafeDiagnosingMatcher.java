package com.crudetech.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public abstract class MemoizingTypeSafeDiagnosingMatcher<T> extends TypeSafeDiagnosingMatcher<T> {
    private Boolean memo = null;

    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription) {
        if (memo == null) {
            memo = doMatchSafely(item);
        }
        if (!memo) {
            doDescribeMismatch(item, mismatchDescription);
        }
        return memo;
    }

    protected void doDescribeMismatch(T item, Description mismatchDescription) {
        mismatchDescription.appendText(" was ").appendValue(item);
    }

    protected abstract boolean doMatchSafely(T item);
}
