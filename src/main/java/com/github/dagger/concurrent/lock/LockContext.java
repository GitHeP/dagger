package com.github.dagger.concurrent.lock;


import java.io.Serializable;

public interface LockContext extends Serializable {

    Object getVisitor();

    Object getMonitor();

    Lock getLock();

}
