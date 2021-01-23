package com.github.dagger.concurrent.lock;

import com.github.dagger.lang.Assert;

import java.util.Objects;

public abstract class AbstractLock implements Lock {

    @Override
    public final LockContext lock(Object visitor, Object monitor) {
        Assert.notNull(visitor , "a visitor must be provided");
        Assert.notNull(monitor , "a monitor must be provided");

        LockContext lockContext = null;
        try {
            lockContext = lock0(visitor, monitor);
        } finally {
            if (Objects.nonNull(lockContext)) {
                LockContexts.setLockContext(lockContext);
            }
        }
        return lockContext;
    }


    protected abstract LockContext lock0(Object visitor, Object monitor);
    
}
