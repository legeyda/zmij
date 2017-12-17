package com.legeyda.zmij;

import java.util.Iterator;
import java.util.function.Consumer;

public class IteratorWrap<T> implements Iterator<T> {
    private final Iterator<T> wrap;

    public IteratorWrap(Iterator<T> wrap) {
        this.wrap = wrap;
    }


    public boolean hasNext() {
        return this.wrap.hasNext();
    }

    public T next() {
        return this.wrap.next();
    }

    public void remove() {
        this.wrap.remove();
    }

    public void forEachRemaining(Consumer<? super T> consumer) {
        this.wrap.forEachRemaining(consumer);
    }
}
