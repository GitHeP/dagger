package com.github.dagger.bean;

import com.github.dagger.lang.Assert;

import java.util.Collection;

/**
 * @author he peng
 * @date 2020/11/21
 */
public abstract class BeanUtils {

    public static Class<?>[] classArray(Collection<Object> objs) {
        Assert.notNull(objs , "objList must not be null");
        return classArray(objs.toArray());
    }

    public static Class<?>[] classArray(Object ... objs) {
        Assert.notNull(objs , "objs must not be null");
        Class<?>[] classes = new Class[objs.length];
        for (int i = 0 ; i < objs.length ; i++) {
            Object obj = objs[i];
            Assert.notNull(obj , "element must not be null");
            classes[i] = obj.getClass();
        }
        return classes;
    }
}
