package com.github.dagger.reflect;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Collection;

import static org.junit.Assert.*;


/**
 *
 * @author He Peng
 * @create 2020-11-22 12:01
 * @update 2020-11-22 12:01
 * @updateDesc : 更新说明
 */
public class ReflectableTest {

    @Test
    public void isInterface() throws Exception {

        Assert.assertTrue(Collection.class.isInterface() && Reflectable.isInterface(Collection.class));
    }

    @Test
    public void method() throws Exception {
        Method method = Reflectable.method(String.class, "toString", null);
        Assert.assertNotNull(method);
    }


}