package com.github.dagger.concurrent.lock;

import com.github.dagger.lang.NamedThreadLocal;

public abstract class LockContexts {

    private static final NamedThreadLocal<LockContext> LOCK_CONTEXT_THREAD_LOCAL = new NamedThreadLocal<LockContext>("DaggerLockContextThreadLocal") {
        @Override
        protected LockContext initialValue() {
            return new LockContext() {
                @Override
                public Object getVisitor() {
                    return null;
                }

                @Override
                public Object getMonitor() {
                    return null;
                }

                @Override
                public Lock getLock() {
                    return null;
                }
            };
        }
    };

    public static void setLockContext(LockContext lockContext) {
        LOCK_CONTEXT_THREAD_LOCAL.set(lockContext);
    }

    public static LockContext getLockContext() {
        return LOCK_CONTEXT_THREAD_LOCAL.get();
    }

    public static Object getVisitor() {
        return getLockContext().getVisitor();
    }


    public static Object getMonitor() {
        return getLockContext().getMonitor();
    }

    public static Lock getLock() {
        return getLockContext().getLock();
    }
}
