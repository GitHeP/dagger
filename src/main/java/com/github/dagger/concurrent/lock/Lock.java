package com.github.dagger.concurrent.lock;

public interface Lock {

    LockContext lock(Object visitor , Object monitor);

}
