package com.github.dagger.bean;

import com.github.dagger.lang.Assert;

import java.util.Collection;
import java.util.Objects;

/**
 * @author he peng
 * @date 2020/11/21
 */
public abstract class BeanUtils {

    public static Class<?>[] classArray(Collection<Object> objects) {
        return classArray(objects.toArray());
    }

    public static Class<?>[] classArray(Object ... objects) {
        Assert.notNull(objects , "objects must not be null");
        Class<?>[] classes = new Class[objects.length];
        for (int i = 0 ; i < objects.length ; i++) {
            Object obj = objects[i];
            Assert.notNull(obj , "element must not be null");
            classes[i] = obj.getClass();
        }
        return classes;
    }

    public static Class<?>[] defaultClassArray(Class<?>[] returnWhenNull , Collection<Object> objects) {
        if (Objects.isNull(objects)) {
            return returnWhenNull;
        }
        return classArray(objects);
    }

    public static Class<?>[] defaultClassArray(Class<?>[] returnWhenNull , Object ... objects) {
        if (Objects.isNull(objects)) {
            return returnWhenNull;
        }
        return classArray(objects);
    }


}
