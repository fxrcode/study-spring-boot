package com.example.aqs.current;

import com.example.aqs.util.UnsafeInstance;
import sun.misc.Unsafe;

/**
 * @author: Turing-Yang Guo
 * @version: V1.0
 */
public class TuringLock {
    // the state of lock. 0->free, 1->used
    private volatile int state = 0;
    /**
     * The thread that hold lock
     */
    private Thread lockHolder;

    /**
     * Lock it
     */
    public void lock() {

    }

    /**
     * Unlock it
     */
    public void unlock() {

    }

    /**
     * Atomic action
     */
    public final boolean compareAndSwapState(int expect, int update) {
        assert unsafe != null;
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    private static final Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();
    private static final long stateOffset;

    static {
        try {
            assert unsafe != null;
            stateOffset = unsafe.objectFieldOffset(TuringLock.class.getDeclaredField("state"));
        } catch (Exception e) {
            throw new Error();
        }
    }

}
