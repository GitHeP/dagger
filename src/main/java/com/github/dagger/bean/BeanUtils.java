package com.github.dagger.bean;

import com.github.dagger.lang.Assert;

import java.util.Collection;
import java.util.Objects;

/**
 * @author he peng
 * @date 2020/11/21
 */
public abstract class BeanUtils {

    /**
     * 获取所给数组的 Class 类型数组,不允许数组中存在 null 元素
     * @param objects
     * @return
     */
    public static Class<?>[] classArray(Collection<Object> objects) {
        return classArray(objects.toArray());
    }

    /**
     * 获取所给数组的 Class 类型数组,不允许数组中存在 null 元素
     * @param objects
     * @return
     */
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

    /**
     * 获取所给数组的 Class 类型数组,不允许数组中存在 null 元素.
     * 当传入的集合是 null 或者 size = 0 时,会将传入的 Class 数组作为返回值返回。
     * @param defaultValue
     * @param objects
     * @return
     */
    public static Class<?>[] defaultClassArrayIfNull(Class<?>[] defaultValue , Collection<Object> objects) {
        if (Objects.isNull(objects) || objects.size() == 0) {
            return defaultValue;
        }
        return classArray(objects);
    }

    /**
     * 获取所给数组的 Class 类型数组,不允许数组中存在 null 元素.
     * 当传入的数组是 null 或者 length = 0 时,会将传入的 Class 数组作为返回值返回。
     * @param defaultValue
     * @param objects
     * @return
     */
    public static Class<?>[] defaultClassArrayIfNull(Class<?>[] defaultValue , Object ... objects) {
        if (Objects.isNull(objects) || objects.length == 0) {
            return defaultValue;
        }
        return classArray(objects);
    }


}
