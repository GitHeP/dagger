package com.github.dagger.bean;

/**
 * @author he peng
 * @date 2020/11/21
 */
public abstract class BeanUtils {

    public static Class<?>[] getClassArray(Object ... objs) {
        // TODO assert not empty and element not null
        Class<?>[] classes = new Class[objs.length];
        for (int i = 0 ; i < objs.length ; i++) {
            classes[i] = objs[i].getClass();
        }
        return classes;
    }
}
