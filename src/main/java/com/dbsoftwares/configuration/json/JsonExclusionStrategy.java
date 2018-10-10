package com.dbsoftwares.configuration.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.lang.reflect.Field;

public class JsonExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipClass(Class<?> args) {
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes attributes) {
        String fieldName = attributes.getName();
        Class<?> clazz = attributes.getDeclaringClass();

        return isFieldInSuperclass(clazz, fieldName);
    }

    private boolean isFieldInSuperclass(Class<?> subClass, String fieldName) {
        Class<?> superClass = subClass.getSuperclass();
        Field field;

        while (superClass != null) {
            field = getField(superClass, fieldName);

            if (field != null)
                return true;

            superClass = superClass.getSuperclass();
        }

        return false;
    }

    private Field getField(Class<?> clazz, String field) {
        try {
            return clazz.getDeclaredField(field);
        } catch (Exception e) {
            return null;
        }
    }
}