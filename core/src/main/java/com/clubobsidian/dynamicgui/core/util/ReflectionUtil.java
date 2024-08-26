/*
 *    Copyright 2018-2023 virustotalop
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clubobsidian.dynamicgui.core.util;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public final class ReflectionUtil {

    private ReflectionUtil() {
    }

    public static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }


    public static Class<?> getClassIfExists(String... clazzes) {
        for (String className : clazzes) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
            }
        }
        return null;
    }

    public static Class<?> classForName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getMethodLossy(Class<?> cl, String... methods) {
        for (Method m : cl.getMethods()) {
            for (String methodName : methods) {
                if (m.getName().equals(methodName)) {
                    m.setAccessible(true);
                    return m;
                }
            }
        }
        return null;
    }

    public static Method getMethod(@Nullable Class<?> cl, String... methods) {
        if (cl == null) {
            return null;
        }
        for (Method m : cl.getDeclaredMethods()) {
            for (String methodName : methods) {
                if (m.getName().equals(methodName)) {
                    m.setAccessible(true);
                    return m;
                }
            }
        }
        return null;
    }

    public static Method getMethodByParams(Class<?> cl, String methodName, Class<?>... params) {
        try {
            Method method = cl.getDeclaredMethod(methodName, params);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getMethodByParams(Class<?> cl, Class<?>... params) {
        for (Method method : cl.getDeclaredMethods()) {
            if (method.getParameterTypes().equals(params)) {
                return method;
            }
        }
        return null;
    }

    public static Method getMethodByReturnType(Class<?> searchIn, Class<?> returnType) {
        for (Method m : searchIn.getDeclaredMethods()) {
            if (m.getReturnType().equals(returnType)) {
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }

    public static Method getMethodByReturnType(Class<?> searchIn, Class<?> returnType, Class<?>... params) {
        if (searchIn == null) {
            return null;
        }
        for (Method m : searchIn.getDeclaredMethods()) {
            if (m.getReturnType().equals(returnType) && Arrays.equals(m.getParameterTypes(), params)) {
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }

    public static <T> T get(Object getFrom, Class<?> searchIn, String name) {
        try {
            return (T) getFieldByName(searchIn, name).get(getFrom);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Field getFieldByName(Class<?> searchIn, String name) {
        try {
            Field f = searchIn.getDeclaredField(name);
            f.setAccessible(true);
            return f;
        } catch (NoSuchFieldException | SecurityException e) {
            return null;
        }
    }

    public static Field getFieldByType(Class<?> searchIn, Class<?> fieldType) {
        for (Field f : searchIn.getDeclaredFields()) {
            if (f.getType().equals(fieldType)) {
                f.setAccessible(true);
                return f;
            }
        }
        return null;
    }

    public static Field getDeclaredField(Class<?> clazz, String... fields) {
        for (String fieldName : fields) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    public static Field getDeclaredField(Object fieldIn, Class<?> clazz, Class<?> returnType, String... fields) {
        for (Field field : clazz.getDeclaredFields()) {
            for (String fieldName : fields) {
                try {
                    if (fieldName.equals(field.getName())) {
                        field.setAccessible(true);
                        Object got = field.get(fieldIn);
                        if (got.getClass().equals(returnType)) {
                            return field;
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Method getStaticMethod(Class<?> searchIn, Class<?> returnType) {
        if (searchIn == null) {
            return null;
        }
        for (Method m : searchIn.getDeclaredMethods()) {
            if (Modifier.isStatic(m.getModifiers()) && m.getReturnType().equals(returnType)) {
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }

    public static Method getStaticMethod(Class<?> searchIn, Class<?> returnType, Class<?>... params) {
        if (searchIn == null) {
            return null;
        }
        for (Method m : searchIn.getDeclaredMethods()) {
            if (Modifier.isStatic(m.getModifiers())
                    && m.getReturnType().equals(returnType)
                    && Arrays.equals(m.getParameterTypes(), params)) {
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }

    public static Method getMethod(Class<?> searchIn, Class<Void> returnType, Class<?>... params) {
        for (Method m : searchIn.getDeclaredMethods()) {
            if (m.getReturnType().equals(returnType)) {
                if (Arrays.equals(m.getParameterTypes(), params)) {
                    m.setAccessible(true);
                    return m;
                }
            }
        }
        return null;
    }

    public static @Nullable Constructor<?> getConstructor(@Nullable Class<?> searchIn, Class<?>... params) {
        if (searchIn == null) {
            return null;
        }
        for (Constructor<?> con : searchIn.getDeclaredConstructors()) {
            if (Arrays.equals(con.getParameterTypes(), params)) {
                return con;
            }
        }
        return null;
    }
}