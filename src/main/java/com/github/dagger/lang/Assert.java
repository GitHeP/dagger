package com.github.dagger.lang;

import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * @author he peng
 * @date 2020/11/21
 */
public abstract class Assert {

    public static void notBlank(CharSequence charSequence , String msg) {
        notNull(charSequence , msg);
        if (charSequence.length() == 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notNull(Object obj , String msg) {
        if (Objects.isNull(obj)) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notEmpty(Object[] array , String msg) {
        notNull(array , msg);
        if (array.length == 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static <T> void notEmpty(String msg , T ... t) {
        notNull(t , msg);
        if (t.length == 0) {
            throw new IllegalArgumentException(msg);
        }
    }



    public static boolean isFalse(BooleanSupplier supplier) {
        return ! isTrue(supplier);
    }

    public static boolean isTrue(BooleanSupplier supplier) {
        return supplier.getAsBoolean();
    }

    public static boolean isFalse(boolean expression) {
        return isFalse(() -> expression);
    }

    public static boolean isTrue(boolean expression) {
        return isTrue(() -> expression);
    }

    public static boolean not(boolean expression) {
        return !expression;
    }

    /**
     * 逻辑与
     * @param expression
     * @return
     */
    public static boolean and(boolean ... expression) {
        notNull(expression , "expression must not be null");
        notEmpty("expression must not be empty" , expression);
        boolean result = true;
        for (boolean current : expression) {
            if (isFalse(current)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 逻辑或
     * @param expression
     * @return
     */
    public static boolean or(boolean ... expression) {
        notNull(expression , "expression must not be null");
        notEmpty("expression must not be empty" , expression);
        boolean result = false;
        for (boolean current : expression) {
            if (isTrue(current)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 逻辑异或
     * @param expression
     * @return
     */
    public static boolean xor(boolean ... expression) {
        notNull(expression , "expression must not be null");
        notEmpty("expression must not be empty" , expression);
        boolean result = expression[0];
        for (int i = 1 ; i < expression.length ; i++) {
            boolean current = expression[i];
            result = result ^ current;
            if (isFalse(result)) {
                break;
            }
        }
        return result;
    }

}
