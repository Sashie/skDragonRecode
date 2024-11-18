package me.sashie.skdragon.util;

import ch.njol.skript.Skript;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ReflectionUtils {
    public static Constructor getConstructor(Class clazz, Class... parameterTypes) throws NoSuchMethodException {
        Class[] primitiveTypes = DataType.getPrimitive(parameterTypes);
        Constructor[] constructors;
        int length = (constructors = clazz.getConstructors()).length;

        for(int i = 0; i < length; ++i) {
            Constructor constructor = constructors[i];
            if (DataType.compare(DataType.getPrimitive(constructor.getParameterTypes()), primitiveTypes)) {
                return constructor;
            }
        }

        throw new NoSuchMethodException("There is no such constructor in this class with the specified parameter types");
    }

    public static Constructor getConstructor(String className, PackageType packageType, Class... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
        return getConstructor(packageType.getClass(className), parameterTypes);
    }

    public static Object instantiateObject(Class clazz, Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getConstructor(clazz, DataType.getPrimitive(arguments)).newInstance(arguments);
    }

    public static Object instantiateObject(String className, PackageType packageType, Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        return instantiateObject(packageType.getClass(className), arguments);
    }

    public static Method getMethod(Class clazz, String methodName, Class... parameterTypes) throws NoSuchMethodException {
        Class[] primitiveTypes = DataType.getPrimitive(parameterTypes);
        Method[] methods;
        int length = (methods = clazz.getMethods()).length;

        for(int i = 0; i < length; ++i) {
            Method method = methods[i];

            if (method.getName().equals(methodName) && DataType.compare(DataType.getPrimitive(method.getParameterTypes()), primitiveTypes)) {
                return method;
            }
        }

        throw new NoSuchMethodException("There is no such method in this class with the specified name and parameter types");
    }

    public static Method getMethod(String className, PackageType packageType, String methodName, Class... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
        return getMethod(packageType.getClass(className), methodName, parameterTypes);
    }

    public static Object invokeMethod(Object instance, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getMethod(instance.getClass(), methodName, DataType.getPrimitive(arguments)).invoke(instance, arguments);
    }

    public static Object invokeMethod(Object instance, Class clazz, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getMethod(clazz, methodName, DataType.getPrimitive(arguments)).invoke(instance, arguments);
    }

    public static Object invokeMethod(Object instance, String className, PackageType packageType, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        return invokeMethod(instance, packageType.getClass(className), methodName, arguments);
    }

    public static Object invokeMethod(Object handle, String methodName, Class[] parameterClasses, Object... args) {
        return invokeMethod(handle.getClass(), handle, methodName, parameterClasses, args);
    }

    public static Object invokeMethod(Class clazz, Object handle, String methodName, Class[] parameterClasses, Object... args) {
        Optional methodOptional = getMethods(clazz, methodName, parameterClasses);
        if (!methodOptional.isPresent()) {
            return null;
        } else {
            Method method = (Method)methodOptional.get();

            try {
                return method.invoke(handle, args);
            } catch (InvocationTargetException | IllegalAccessException var8) {
                var8.printStackTrace();
                return null;
            }
        }
    }

    public static Optional getMethods(Class clazz, String name, Class... params) {
        try {
            return Optional.of(clazz.getMethod(name, params));
        } catch (NoSuchMethodException var5) {
            var5.printStackTrace();

            try {
                return Optional.of(clazz.getDeclaredMethod(name, params));
            } catch (NoSuchMethodException var4) {
                var4.printStackTrace();
                return Optional.empty();
            }
        }
    }

    public static boolean setField(Class from, Object obj, String field, Object newValue) {
        try {
            Field f = from.getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, newValue);
            return true;
        } catch (Exception var5) {
            return false;
        }
    }

    public static Object getField(Class from, Object obj, String field) {
        try {
            Field f = from.getDeclaredField(field);
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception var4) {
            return null;
        }
    }

    public static Field getField(Class clazz, String s, Class clazz2, int n) {
        Field[] declaredFields;
        int length = (declaredFields = clazz.getDeclaredFields()).length;

        for(int i = 0; i < length; ++i) {
            Field field = declaredFields[i];
            if ((s == null || field.getName().equals(s)) && clazz2.isAssignableFrom(field.getType()) && n-- <= 0) {
                field.setAccessible(true);
                return field;
            }
        }

        if (clazz.getSuperclass() != null) {
            return getField(clazz.getSuperclass(), s, clazz2, n);
        } else {
            throw new IllegalArgumentException("Field with type " + clazz2 + " not found");
        }
    }

    public static Field getField(Class clazz, boolean declared, String fieldName) throws NoSuchFieldException, SecurityException {
        Field field = declared ? clazz.getDeclaredField(fieldName) : clazz.getField(fieldName);
        field.setAccessible(true);
        return field;
    }

    public static Field getField(String className, PackageType packageType, boolean declared, String fieldName) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
        return getField(packageType.getClass(className), declared, fieldName);
    }

    public static Object getValue(Object instance, Class clazz, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return getField(clazz, declared, fieldName).get(instance);
    }

    public static Object getValue(Object instance, String className, PackageType packageType, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        return getValue(instance, packageType.getClass(className), declared, fieldName);
    }

    public static Object getValue(Object instance, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return getValue(instance, instance.getClass(), declared, fieldName);
    }

    public static void setValue(Object instance, Class clazz, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        getField(clazz, declared, fieldName).set(instance, value);
    }

    public static void setValue(Object instance, String className, PackageType packageType, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        setValue(instance, packageType.getClass(className), declared, fieldName, value);
    }

    public static void setValue(Object instance, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        setValue(instance, instance.getClass(), declared, fieldName, value);
    }

    public enum DataType {
        BYTE(Byte.TYPE, Byte.class),
        SHORT(Short.TYPE, Short.class),
        INTEGER(Integer.TYPE, Integer.class),
        LONG(Long.TYPE, Long.class),
        CHARACTER(Character.TYPE, Character.class),
        FLOAT(Float.TYPE, Float.class),
        DOUBLE(Double.TYPE, Double.class),
        BOOLEAN(Boolean.TYPE, Boolean.class);

        private static final Map CLASS_MAP = new HashMap();
        private final Class primitive;
        private final Class reference;

        static {
            DataType[] values;
            int length = (values = values()).length;

            for(int i = 0; i < length; ++i) {
                DataType type = values[i];
                CLASS_MAP.put(type.primitive, type);
                CLASS_MAP.put(type.reference, type);
            }

        }

        DataType(Class primitive, Class reference) {
            this.primitive = primitive;
            this.reference = reference;
        }

        public Class getPrimitive() {
            return this.primitive;
        }

        public Class getReference() {
            return this.reference;
        }

        public static DataType fromClass(Class clazz) {
            return (DataType)CLASS_MAP.get(clazz);
        }

        public static Class getPrimitive(Class clazz) {
            DataType type = fromClass(clazz);
            return type == null ? clazz : type.getPrimitive();
        }

        public static Class getReference(Class clazz) {
            DataType type = fromClass(clazz);
            return type == null ? clazz : type.getReference();
        }

        public static Class[] getPrimitive(Class[] classes) {
            int length = classes == null ? 0 : classes.length;
            Class[] types = new Class[length];

            for(int index = 0; index < length; ++index) {
                types[index] = getPrimitive(classes[index]);
            }

            return types;
        }

        public static Class[] getReference(Class[] classes) {
            int length = classes == null ? 0 : classes.length;
            Class[] types = new Class[length];

            for(int index = 0; index < length; ++index) {
                types[index] = getReference(classes[index]);
            }

            return types;
        }

        public static Class[] getPrimitive(Object[] objects) {
            int length = objects == null ? 0 : objects.length;
            Class[] types = new Class[length];

            for(int index = 0; index < length; ++index) {
                types[index] = getPrimitive(objects[index].getClass());
            }

            return types;
        }

        public static Class[] getReference(Object[] objects) {
            int length = objects == null ? 0 : objects.length;
            Class[] types = new Class[length];

            for(int index = 0; index < length; ++index) {
                types[index] = getReference(objects[index].getClass());
            }

            return types;
        }

        public static boolean compare(Class[] primary, Class[] secondary) {
            if (primary != null && secondary != null && primary.length == secondary.length) {
                for(int index = 0; index < primary.length; ++index) {
                    Class primaryClass = primary[index];
                    Class secondaryClass = secondary[index];
                    if (!primaryClass.equals(secondaryClass) && !primaryClass.isAssignableFrom(secondaryClass)) {
                        return false;
                    }
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public enum PackageType {
        MINECRAFT_SERVER("net.minecraft.server." + getServerVersion()),
        CRAFTBUKKIT("org.bukkit.craftbukkit." + getServerVersion()),
        CRAFTBUKKIT_BLOCK(CRAFTBUKKIT, "block"),
        CRAFTBUKKIT_CHUNKIO(CRAFTBUKKIT, "chunkio"),
        CRAFTBUKKIT_COMMAND(CRAFTBUKKIT, "command"),
        CRAFTBUKKIT_CONVERSATIONS(CRAFTBUKKIT, "conversations"),
        CRAFTBUKKIT_ENCHANTMENTS(CRAFTBUKKIT, "enchantments"),
        CRAFTBUKKIT_ENTITY(CRAFTBUKKIT, "entity"),
        CRAFTBUKKIT_EVENT(CRAFTBUKKIT, "event"),
        CRAFTBUKKIT_GENERATOR(CRAFTBUKKIT, "generator"),
        CRAFTBUKKIT_HELP(CRAFTBUKKIT, "help"),
        CRAFTBUKKIT_INVENTORY(CRAFTBUKKIT, "inventory"),
        CRAFTBUKKIT_MAP(CRAFTBUKKIT, "map"),
        CRAFTBUKKIT_METADATA(CRAFTBUKKIT, "metadata"),
        CRAFTBUKKIT_POTION(CRAFTBUKKIT, "potion"),
        CRAFTBUKKIT_PROJECTILES(CRAFTBUKKIT, "projectiles"),
        CRAFTBUKKIT_SCHEDULER(CRAFTBUKKIT, "scheduler"),
        CRAFTBUKKIT_SCOREBOARD(CRAFTBUKKIT, "scoreboard"),
        CRAFTBUKKIT_UPDATER(CRAFTBUKKIT, "updater"),
        CRAFTBUKKIT_UTIL(CRAFTBUKKIT, "util"),
        BUKKIT("org.bukkit"),
        BUKKIT_ADVANCEMENT(BUKKIT, "advancement"),
        BUKKIT_ATTRIBUTE(BUKKIT, "attribute"),
        BUKKIT_COMMAND(BUKKIT, "command"),
        BUKKIT_BLOCK(BUKKIT, "block"),
        BUKKIT_BLOCK_DATA(BUKKIT, "block.data"),
        BUKKIT_BLOCK_TYPE(BUKKIT, "block.data.type"),
        BUKKIT_MATERIAL(BUKKIT, "material"),
        MINECRAFT_NETWORK_SYNCER("net.minecraft.network.syncher"),
        MINECRAFT_SERVER_LEVEL("net.minecraft.server.level"),
        MINECRAFT_SERVER_NETWORK("net.minecraft.server.network"),
        MINECRAFT_CORE_PARTICLES("net.minecraft.core.particles"),
        MINECRAFT_NETWORK("net.minecraft.network"),
        MINECRAFT_NETWORK_PROTOCOL("net.minecraft.network.protocol"),
        MINECRAFT_NETWORK_PROTOCOL_GAME("net.minecraft.network.protocol.game"),
        MOJANG_MATH("com.mojang.math"),
        MINECRAFT_CORE("net.minecraft.core"),
        MINECRAFT_WORLD_LEVEL("net.minecraft.world.level"),
        MINECRAFT_WORLD_LEVEL_GAMEEVENT("net.minecraft.world.level.gameevent"),
        MINECRAFT_WORLD_LEVEL_GAMEEVENT_VIBRATIONS("net.minecraft.world.level.gameevent.vibrations"),
        MINECRAFT_WORLD_ENTITY("net.minecraft.world.entity"),
        MINECRAFT_WORLD_ITEM("net.minecraft.world.item"),
        MINECRAFT_WORLD_ENTITY_ITEM("net.minecraft.world.entity.item"),
        MINECRAFT_WORLD_PHYS("net.minecraft.world.phys");

        private final String path;

        PackageType(String path) {
            this.path = path;
        }

        PackageType(PackageType parent, String path) {
            this(parent + "." + path);
        }

        public String getPath() {
            return this.path;
        }

        public Class getClass(String className) throws ClassNotFoundException {
            return Class.forName(this + "." + className);
        }

        public Class getClazz() throws ClassNotFoundException {
            return Class.forName(this.path);
        }

        public String toString() {
            return this.path;
        }

        public static String getServerVersion() {
            String name = Bukkit.getServer().getClass().getPackage().getName();
            return name.substring(name.lastIndexOf(".") + 1);
        }

        public static int getServerVersionMinor() {
            try {
                return Integer.parseInt(getServerVersion().split("_")[1]);
            } catch (Exception ex) {
                return Skript.getMinecraftVersion().getMinor();
            }
        }

        public static int getServerVersionRevision() {
            try {
                return Integer.parseInt(getServerVersion().split("_")[2].replace("R", ""));
            } catch (Exception ex) {
                return Skript.getMinecraftVersion().getRevision();
            }
        }
    }
}