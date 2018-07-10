package com.dbsoftwares.configuration.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Utils {

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isLoaded(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Method getMethod(String name, Class<?> clazz, Class<?>... paramTypes) {
        for (Method m : clazz.getMethods()) {
            Class<?>[] types = m.getParameterTypes();
            if (m.getName().equals(name) && equalsTypeArray(types, paramTypes)) {
                return m;
            }
        }
        return null;
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... args) {
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getName().equals(name) && (args.length == 0 || classList(args, m.getParameterTypes()))) {
                m.setAccessible(true);
                return m;
            }
        }
        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(name) && (args.length == 0 || classList(args, m.getParameterTypes()))) {
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }

    public static Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        return clazz.getConstructor(parameterTypes);
    }

    private static boolean equalsTypeArray(Class<?>[] a, Class<?>[] o) {
        if (a.length != o.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(o[i]) && !a[i].isAssignableFrom(o[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean classList(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length) {
            return false;
        }
        for (int i = 0; i < l1.length; i++) {
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        }
        return equal;
    }

    public static boolean isInteger(Object object) {
        try {
            Integer.parseInt(object.toString());
            return true;
        } catch (Exception exc) {
            return false;
        }
    }

    public static Boolean isShort(Object object) {
        try {
            Short.parseShort(object.toString());
            return true;
        } catch (Exception exc) {
            return false;
        }
    }

    public static boolean isDouble(Object object) {
        try {
            Double.parseDouble(object.toString());
            return true;
        } catch (Exception exc) {
            return false;
        }
    }

    public static boolean isLong(Object object) {
        try {
            Long.parseLong(object.toString());
            return true;
        } catch (Exception exc) {
            return false;
        }
    }

    public static boolean isByte(Object object) {
        try {
            Byte.parseByte(object.toString());
            return true;
        } catch (Exception exc) {
            return false;
        }
    }

    public static boolean isFloat(Object object) {
        try {
            Float.parseFloat(object.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isBigInteger(Object object) {
        try {
            new BigInteger(object.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isBigDecimal(Object object) {
        try {
            new BigDecimal(object.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isBoolean(Object object) {
        try {
            Boolean.parseBoolean(object.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}