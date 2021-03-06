package com.github.dagger.lang;

import com.github.dagger.reflect.Reflectable;

import java.util.Objects;

/**
 * @author he peng
 * @date 2020/11/26
 */
public abstract class ObjectUtils {

    /**
     * 使用 {@link Objects#equals(java.lang.Object, java.lang.Object)} 对 target 对象和 objects
     * 中的每个元素对象进行比较，如果有任何一次比较得到的结果为 false 那么整个函数调用返回 true, 反之返回 false.
     * @param target
     * @param objects
     * @return
     */
    public static <T> boolean notEqualAny(T target , T ... objects) {
        Assert.notEmpty(objects , "objects must not be empty");
        boolean notEqualAny = false;
        for (Object object : objects) {
            if (Assert.isFalse(Objects.equals(target, object))) {
                notEqualAny = true;
                break;
            }
        }

        return notEqualAny;
    }

    /**
     * 使用 {@link Objects#equals(java.lang.Object, java.lang.Object)} 对 target 对象和 objects
     * 中的每个元素对象进行比较，如果有任何一次比较得到的结果为 true 那么整个函数调用返回 true, 反之返回 false.
     * @param target
     * @param objects
     * @return
     */
    public static <T> boolean equalAny(T target , T ... objects) {
        Assert.notEmpty(objects , "objects must not be empty");
        boolean equalAny = false;
        for (Object object : objects) {
            if (Assert.isTrue(Objects.equals(target, object))) {
                equalAny = true;
                break;
            }
        }
        return equalAny;
    }

    /**
     * 使用 {@link Objects#equals(java.lang.Object, java.lang.Object)} 对 target 对象和 objects 中的每个元素对象进行比较.
     * target 和 objects 中的每个元素比较结果都为 true 则函数调用返回 true , 反之返回 false.
     * @param target
     * @param objects
     * @param <T>
     * @return
     */
    public static <T> boolean equalAll(T target , T ... objects) {
        Assert.notEmpty(objects , "objects must not be empty");
        boolean equalAll = true;
        for (Object object : objects) {
            boolean equals = Objects.equals(target, object);
            if (Assert.isFalse(equals)) {
                equalAll = false;
                break;
            }
            equalAll = equalAll && equals;
        }
        return equalAll;
    }

    /**
     * objects 中包含 null 值就返回 true , 反之返回 false.
     * @param objects
     * @return
     */
    public static boolean containsNull(Object ... objects) {
        Assert.notEmpty(objects , "objects must not be empty");
        boolean containsNull = false;
        for (Object object : objects) {
            if (Objects.isNull(object)) {
                containsNull = true;
                break;
            }
        }

        return containsNull;
    }

    /**
     * objects 中包含 null 值就返回 false , 反之返回 true.
     * @param objects
     * @return
     */
    public static boolean notContainsNull(Object ... objects) {
       return ! containsNull(objects);
    }

    public static boolean isPrimitiveOrWrapper(final Object object) {
        Assert.notNull(object, "object must not be null");
        Class<Object> aClass = Reflectable.toClass(object);
        if (aClass == null) {
            return false;
        }

        return aClass.isPrimitive() || isPrimitiveWrapper(aClass);
    }

    public static boolean isPrimitiveWrapper(Class cls) {

        return true;
    }
}
