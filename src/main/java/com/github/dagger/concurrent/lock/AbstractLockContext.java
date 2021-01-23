package com.github.dagger.concurrent.lock;


public abstract class AbstractLockContext implements LockContext {

    private static final long serialVersionUID = -1287953831324127175L;

    private final Object visitor;

    private final Object monitor;

    private final Lock lock;

    public AbstractLockContext(Object visitor, Object monitor, Lock lock) {
        this.visitor = visitor;
        this.monitor = monitor;
        this.lock = lock;
    }

    @Override
    public Object getVisitor() {
        return visitor;
    }

    @Override
    public Object getMonitor() {
        return monitor;
    }

    @Override
    public Lock getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" +
                "visitor=" + visitor +
                ", monitor=" + monitor +
                ", lock=" + lock +
                '}';
    }
}
