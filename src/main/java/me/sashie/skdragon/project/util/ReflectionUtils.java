package me.sashie.skdragon.project.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;

public final class ReflectionUtils
{
	public static Constructor<?> getConstructor(final Class<?> clazz, final Class<?>... parameterTypes) throws NoSuchMethodException {
		final Class<?>[] primitiveTypes = DataType.getPrimitive(parameterTypes);
		Constructor<?>[] constructors;
		for (int length = (constructors = clazz.getConstructors()).length, i = 0; i < length; ++i) {
			final Constructor<?> constructor = constructors[i];
			if (DataType.compare(DataType.getPrimitive(constructor.getParameterTypes()), primitiveTypes)) {
				return constructor;
			}
		}
		throw new NoSuchMethodException("There is no such constructor in this class with the specified parameter types");
	}
	
	public static Constructor<?> getConstructor(final String className, final PackageType packageType, final Class<?>... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
		return getConstructor(packageType.getClass(className), parameterTypes);
	}
	
	public static Object instantiateObject(final Class<?> clazz, final Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return getConstructor(clazz, DataType.getPrimitive(arguments)).newInstance(arguments);
	}
	
	public static Object instantiateObject(final String className, final PackageType packageType, final Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
		return instantiateObject(packageType.getClass(className), arguments);
	}
	
	public static Method getMethod(final Class<?> clazz, final String methodName, final Class<?>... parameterTypes) throws NoSuchMethodException {
		final Class<?>[] primitiveTypes = DataType.getPrimitive(parameterTypes);
		Method[] methods;
		for (int length = (methods = clazz.getMethods()).length, i = 0; i < length; ++i) {
			final Method method = methods[i];
			if (method.getName().equals(methodName) && DataType.compare(DataType.getPrimitive(method.getParameterTypes()), primitiveTypes)) {
				return method;
			}
		}
		throw new NoSuchMethodException("There is no such method in this class with the specified name and parameter types");
	}
	
	public static Method getMethod(final String className, final PackageType packageType, final String methodName, final Class<?>... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
		return getMethod(packageType.getClass(className), methodName, parameterTypes);
	}
	
	public static Object invokeMethod(final Object instance, final String methodName, final Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return getMethod(instance.getClass(), methodName, DataType.getPrimitive(arguments)).invoke(instance, arguments);
	}
	
	public static Object invokeMethod(final Object instance, final Class<?> clazz, final String methodName, final Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		return getMethod(clazz, methodName, DataType.getPrimitive(arguments)).invoke(instance, arguments);
	}
	
	public static Object invokeMethod(final Object instance, final String className, final PackageType packageType, final String methodName, final Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
		return invokeMethod(instance, packageType.getClass(className), methodName, arguments);
	}
	
	/**
	 * Invokes the method
	 * 
	 * @param handle
	 *			The handle to invoke it on
	 * @param methodName
	 *			The name of the method
	 * @param parameterClasses
	 *			The parameter types
	 * @param args
	 *			The arguments
	 * 
	 * @return The resulting object or null if an error occurred / the
	 *		 method didn't return a thing
	 */
	@SuppressWarnings("rawtypes")
	public static Object invokeMethod(Object handle, String methodName,
			Class[] parameterClasses, Object... args) {
		return invokeMethod(handle.getClass(), handle, methodName,
				parameterClasses, args);
	}

	/**
	 * Invokes the method
	 * 
	 * @param clazz
	 *			The class to invoke it from
	 * @param handle
	 *			The handle to invoke it on
	 * @param methodName
	 *			The name of the method
	 * @param parameterClasses
	 *			The parameter types
	 * @param args
	 *			The arguments
	 * 
	 * @return The resulting object or null if an error occurred / the
	 *		 method didn't return a thing
	 */
	@SuppressWarnings("rawtypes")
	public static Object invokeMethod(Class<?> clazz, Object handle,
			String methodName, Class[] parameterClasses, Object... args) {
		Optional<Method> methodOptional = getMethods(clazz, methodName,
				parameterClasses);

		if (!methodOptional.isPresent()) {
			return null;
		}

		Method method = methodOptional.get();

		try {
			return method.invoke(handle, args);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Optional<Method> getMethods(Class<?> clazz, String name,
			Class<?>... params) {
		try {
			return Optional.of(clazz.getMethod(name, params));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			return Optional.of(clazz.getDeclaredMethod(name, params));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	/**
	 * Use to set a object from a private field.
	 *
	 * @param from  - The class to set the field
	 * @param obj   - The instance of class, you can use null if the field is static.
	 * @param field - The field name
	 * @return True if successful.
	 */
	public static <T> boolean setField(Class<T> from, Object obj, String field, Object newValue) {
		try {
			Field f = from.getDeclaredField(field);
			f.setAccessible(true);
			f.set(obj, newValue);
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	/**
	 * Use to get a object from a private field. If it will return null in case it was unsuccessful.
	 *
	 * @param from  - The class to get the field
	 * @param obj   - The instance of class, you can use null if the field is static.
	 * @param field - The field name
	 * @return The object value.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getField(Class<?> from, Object obj, String field) {
		try {
			Field f = from.getDeclaredField(field);
			f.setAccessible(true);
			return (T) f.get(obj);
		} catch (Exception e) {

		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public static <T> Field getField(final Class<?> clazz, final String s, final Class<T> clazz2, int n) {
		Field[] declaredFields;
		for (int length = (declaredFields = clazz.getDeclaredFields()).length, i = 0; i < length; ++i) {
			final Field field = declaredFields[i];
			if ((s == null || field.getName().equals(s)) && clazz2.isAssignableFrom(field.getType()) && n-- <= 0) {
				field.setAccessible(true);

				return field;

			}
		}
		if (clazz.getSuperclass() != null) {
			return getField(clazz.getSuperclass(), s, (Class<Object>)clazz2, n);
		}
		throw new IllegalArgumentException("Field with type " + clazz2 + " not found");
	}

	public static Field getField(final Class<?> clazz, final boolean declared, final String fieldName) throws NoSuchFieldException, SecurityException {
		final Field field = declared ? clazz.getDeclaredField(fieldName) : clazz.getField(fieldName);
		field.setAccessible(true);
		return field;
	}

	public static Field getField(final String className, final PackageType packageType, final boolean declared, final String fieldName) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		return getField(packageType.getClass(className), declared, fieldName);
	}
	
	public static Object getValue(final Object instance, final Class<?> clazz, final boolean declared, final String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return getField(clazz, declared, fieldName).get(instance);
	}
	
	public static Object getValue(final Object instance, final String className, final PackageType packageType, final boolean declared, final String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
		return getValue(instance, packageType.getClass(className), declared, fieldName);
	}
	
	public static Object getValue(final Object instance, final boolean declared, final String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return getValue(instance, instance.getClass(), declared, fieldName);
	}
	
	public static void setValue(final Object instance, final Class<?> clazz, final boolean declared, final String fieldName, final Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		getField(clazz, declared, fieldName).set(instance, value);
	}
	
	public static void setValue(final Object instance, final String className, final PackageType packageType, final boolean declared, final String fieldName, final Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
		setValue(instance, packageType.getClass(className), declared, fieldName, value);
	}
	
	public static void setValue(final Object instance, final boolean declared, final String fieldName, final Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		setValue(instance, instance.getClass(), declared, fieldName, value);
	}
	
	public enum DataType
	{
		BYTE((Class<?>)Byte.TYPE, (Class<?>)Byte.class), 
		SHORT((Class<?>)Short.TYPE, (Class<?>)Short.class), 
		INTEGER((Class<?>)Integer.TYPE, (Class<?>)Integer.class), 
		LONG((Class<?>)Long.TYPE, (Class<?>)Long.class), 
		CHARACTER((Class<?>)Character.TYPE, (Class<?>)Character.class), 
		FLOAT((Class<?>)Float.TYPE, (Class<?>)Float.class), 
		DOUBLE((Class<?>)Double.TYPE, (Class<?>)Double.class), 
		BOOLEAN((Class<?>)Boolean.TYPE, (Class<?>)Boolean.class);
		
		private static final Map<Class<?>, DataType> CLASS_MAP;
		private final Class<?> primitive;
		private final Class<?> reference;
		
		static {
			CLASS_MAP = new HashMap<Class<?>, DataType>();
			DataType[] values;
			for (int length = (values = values()).length, i = 0; i < length; ++i) {
				final DataType type = values[i];
				DataType.CLASS_MAP.put(type.primitive, type);
				DataType.CLASS_MAP.put(type.reference, type);
			}
		}
		
		private DataType(final Class<?> primitive, final Class<?> reference) {
			this.primitive = primitive;
			this.reference = reference;
		}
		
		public Class<?> getPrimitive() {
			return this.primitive;
		}
		
		public Class<?> getReference() {
			return this.reference;
		}
		
		public static DataType fromClass(final Class<?> clazz) {
			return DataType.CLASS_MAP.get(clazz);
		}
		
		public static Class<?> getPrimitive(final Class<?> clazz) {
			final DataType type = fromClass(clazz);
			return (type == null) ? clazz : type.getPrimitive();
		}
		
		public static Class<?> getReference(final Class<?> clazz) {
			final DataType type = fromClass(clazz);
			return (type == null) ? clazz : type.getReference();
		}
		
		public static Class<?>[] getPrimitive(final Class<?>[] classes) {
			final int length = (classes == null) ? 0 : classes.length;
			final Class<?>[] types = new Class[length];
			for (int index = 0; index < length; ++index) {
				types[index] = getPrimitive(classes[index]);
			}
			return (Class<?>[])types;
		}
		
		public static Class<?>[] getReference(final Class<?>[] classes) {
			final int length = (classes == null) ? 0 : classes.length;
			final Class<?>[] types = new Class[length];
			for (int index = 0; index < length; ++index) {
				types[index] = getReference(classes[index]);
			}
			return (Class<?>[])types;
		}
		
		public static Class<?>[] getPrimitive(final Object[] objects) {
			final int length = (objects == null) ? 0 : objects.length;
			final Class<?>[] types = new Class[length];
			for (int index = 0; index < length; ++index) {
				types[index] = getPrimitive(objects[index].getClass());
			}
			return (Class<?>[])types;
		}
		
		public static Class<?>[] getReference(final Object[] objects) {
			final int length = (objects == null) ? 0 : objects.length;
			final Class<?>[] types = new Class[length];
			for (int index = 0; index < length; ++index) {
				types[index] = getReference(objects[index].getClass());
			}
			return (Class<?>[])types;
		}
		
		public static boolean compare(final Class<?>[] primary, final Class<?>[] secondary) {
			if (primary == null || secondary == null || primary.length != secondary.length) {
				return false;
			}
			for (int index = 0; index < primary.length; ++index) {
				final Class<?> primaryClass = primary[index];
				final Class<?> secondaryClass = secondary[index];
				if (!primaryClass.equals(secondaryClass) && !primaryClass.isAssignableFrom(secondaryClass)) {
					return false;
				}
			}
			return true;
		}
	}
	
	public enum PackageType
	{
		MINECRAFT_SERVER("MINECRAFT_SERVER", 0, "net.minecraft.server." + getServerVersion()), 
		CRAFTBUKKIT("CRAFTBUKKIT", 1, "org.bukkit.craftbukkit." + getServerVersion()), 
		CRAFTBUKKIT_BLOCK("CRAFTBUKKIT_BLOCK", 2, PackageType.CRAFTBUKKIT, "block"), 
		CRAFTBUKKIT_CHUNKIO("CRAFTBUKKIT_CHUNKIO", 3, PackageType.CRAFTBUKKIT, "chunkio"), 
		CRAFTBUKKIT_COMMAND("CRAFTBUKKIT_COMMAND", 4, PackageType.CRAFTBUKKIT, "command"), 
		CRAFTBUKKIT_CONVERSATIONS("CRAFTBUKKIT_CONVERSATIONS", 5, PackageType.CRAFTBUKKIT, "conversations"), 
		CRAFTBUKKIT_ENCHANTMENS("CRAFTBUKKIT_ENCHANTMENS", 6, PackageType.CRAFTBUKKIT, "enchantments"), 
		CRAFTBUKKIT_ENTITY("CRAFTBUKKIT_ENTITY", 7, PackageType.CRAFTBUKKIT, "entity"), 
		CRAFTBUKKIT_EVENT("CRAFTBUKKIT_EVENT", 8, PackageType.CRAFTBUKKIT, "event"), 
		CRAFTBUKKIT_GENERATOR("CRAFTBUKKIT_GENERATOR", 9, PackageType.CRAFTBUKKIT, "generator"), 
		CRAFTBUKKIT_HELP("CRAFTBUKKIT_HELP", 10, PackageType.CRAFTBUKKIT, "help"), 
		CRAFTBUKKIT_INVENTORY("CRAFTBUKKIT_INVENTORY", 11, PackageType.CRAFTBUKKIT, "inventory"), 
		CRAFTBUKKIT_MAP("CRAFTBUKKIT_MAP", 12, PackageType.CRAFTBUKKIT, "map"), 
		CRAFTBUKKIT_METADATA("CRAFTBUKKIT_METADATA", 13, PackageType.CRAFTBUKKIT, "metadata"),
		CRAFTBUKKIT_POTION("CRAFTBUKKIT_POTION", 14, PackageType.CRAFTBUKKIT, "potion"),
		CRAFTBUKKIT_PROJECTILES("CRAFTBUKKIT_PROJECTILES", 15, PackageType.CRAFTBUKKIT, "projectiles"), 
		CRAFTBUKKIT_SCHEDULER("CRAFTBUKKIT_SCHEDULER", 16, PackageType.CRAFTBUKKIT, "scheduler"), 
		CRAFTBUKKIT_SCOREBOARD("CRAFTBUKKIT_SCOREBOARD", 17, PackageType.CRAFTBUKKIT, "scoreboard"), 
		CRAFTBUKKIT_UPDATER("CRAFTBUKKIT_UPDATER", 18, PackageType.CRAFTBUKKIT, "updater"), 
		CRAFTBUKKIT_UTIL("CRAFTBUKKIT_UTIL", 19, PackageType.CRAFTBUKKIT, "util"),
		BUKKIT("BUKKIT", 20, "org.bukkit"),
		BUKKIT_ADVANCEMENT("BUKKIT_ADVANCEMENT", 21, PackageType.BUKKIT, "advancement"), 
		BUKKIT_ATTRIBUTE("BUKKIT_ATTRIBUTE", 22, PackageType.BUKKIT, "attribute"), 
		BUKKIT_COMMAND("BUKKIT_COMMAND", 23, PackageType.BUKKIT, "command"), 
		BUKKIT_BLOCK("BUKKIT_BLOCK", 24, PackageType.BUKKIT, "block"), 
		BUKKIT_BLOCK_DATA("BUKKIT_BLOCK_DATA", 25, PackageType.BUKKIT, "block.data"), 
		BUKKIT_BLOCK_TYPE("BUKKIT_BLOCK_TYPE", 26, PackageType.BUKKIT, "block.data.type"), 
		BUKKIT_MATERIAL("BUKKIT_MATERIAL", 27, PackageType.BUKKIT, "material");
		
		
		private final String path;
		
		private PackageType(final String s, final int n, final String path) {
			this.path = path;
		}
		
		private PackageType(final String s, final int n, final PackageType parent, final String path) {
			this(s, n, parent + "." + path);
		}
		
		public String getPath() {
			return this.path;
		}
		
		public Class<?> getClass(final String className) throws ClassNotFoundException {
			return Class.forName(this + "." + className);
		}
		
		@Override
		public String toString() {
			return this.path;
		}
		
		public static String getServerVersion() {
			return Bukkit.getServer().getClass().getPackage().getName().substring(23);
		}
	}
	

}