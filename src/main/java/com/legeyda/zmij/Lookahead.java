package com.legeyda.zmij;

import java.util.Iterator;

public interface Lookahead<T> extends Iterator<T> {

    /** add savepoint to stack for future reference*/
    void mark();

    /** restore to position */
    void restore();

    /** forget about mark point and restore */
    void forget();
}
