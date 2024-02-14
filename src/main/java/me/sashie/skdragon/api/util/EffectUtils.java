package me.sashie.skdragon.api.util;

import me.sashie.skdragon.api.effects.EffectProperty;
import me.sashie.skdragon.api.util.pool.ObjectPoolManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.Arrays;

public class EffectUtils {

	public static EffectProperty[] array(final EffectProperty... array) {
		return array;
	}

	public static Object[] array(final Object... array) {
		return array;
	}

/*
	public static EffectProperty[] combineArrays(final EffectProperty[] array1, final EffectProperty[] array2) {
		return (EffectProperty[]) ArrayUtils.addAll(array1, array2);
	}
*/

	/**
	 * Combines two EffectProperty arrays with a null check similar to Apaches ArrayUtils.addAll(array1, array2)
	 *
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static EffectProperty[] combineArrays(EffectProperty[] array1, EffectProperty[] array2) {
		if (array1 == null)
			return array2.clone();
		else if (array2 == null)
			return array1.clone();
		final EffectProperty[] joinedArray = new EffectProperty[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	//not of type Entity or Location and will be ignored
	public static DynamicLocation[] toDynamicLocations(Object[] locations) {
		int length = 0; // Initialize the length to 0

		// First, count how many valid Entity/Location objects are in the input array
		for (Object obj : locations) {
			if (obj instanceof Entity || obj instanceof Location) {
				length++;
			}
		}

		// Create the resulting DynamicLocation array with the computed length
		final DynamicLocation[] output = new DynamicLocation[length];
		int currentIndex = 0; // Initialize a variable to keep track of the current index in the output array

		// Iterate through the input array and convert valid objects to DynamicLocation
		for (Object obj : locations) {
			if (obj instanceof Entity) {
				output[currentIndex++] = ObjectPoolManager.getDynamicLocationPool().acquire((Entity) obj);
			} else if (obj instanceof Location) {
				output[currentIndex++] = ObjectPoolManager.getDynamicLocationPool().acquire((Location) obj);
			}
			// Ignore objects that are not Entity or Location
		}

		return output;
	}

	/**
	 * A helper method to get a value from registered Skript types after checking for null inputs
	 */
	public static <T> boolean arrayContains(T[] array, T element) {
		return Arrays.asList(array).contains(element);
	}

	public static <T> T[] addToArray(T[] array, T newElement) {
		int arrayLength = array.length;

		// Check if the array is full
		if (array[arrayLength - 1] != null) {
			// Resize the array with a moderate increase
			int newSize = arrayLength + (arrayLength / 2);
			array = resizeArray(array, newSize);
		}

		// Find the first null slot and add the element
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null) {
				array[i] = newElement;
				break;
			}
		}

		return array;
	}

	public static <T> T[] addArray(T[] originalArray, T[] elementsToAdd) {
		int originalLength = originalArray.length;
		int elementsToAddLength = elementsToAdd.length;

		// Create a new array with the combined length
		T[] newArray = Arrays.copyOf(originalArray, originalLength + elementsToAddLength);

		// Copy the elements to the new array
		System.arraycopy(elementsToAdd, 0, newArray, originalLength, elementsToAddLength);

		return newArray;
	}

	public static <T> T[] removeFromArray(T[] array, T elementToRemove) {
		int start = 0;
		int end = array.length - 1;

		// Iterate from both ends towards the center
		for (; start <= end; start++, end--) {
			if (array[start] != null && array[start].equals(elementToRemove)) {
				array[start] = array[end];
				array[end] = null;
				break;
			}

			if (array[end] != null && array[end].equals(elementToRemove)) {
				array[end] = null;
				break;
			}
		}

		// Check if the array is significantly empty and resize if necessary
		if (array.length > 16 && countNonNullElements(array) < array.length / 4) {
			int newSize = Math.max(array.length / 2, 16);
			array = resizeArray(array, newSize);
		}

		return array;
	}

	private static <T> T[] resizeArray(T[] array, int newSize) {
		T[] newArray = (T[]) new Object[newSize];
		System.arraycopy(array, 0, newArray, 0, Math.min(array.length, newSize));
		return newArray;
	}

	private static <T> int countNonNullElements(T[] array) {
		int count = 0;
		for (T element : array) {
			if (element != null) {
				count++;
			}
		}
		return count;
	}

	public static double[][] convertLocationArrayToArray(Location[] locationArray) {
		double[][] coordinatesArray = new double[locationArray.length][3];

		for (int i = 0; i < locationArray.length; i++) {
			Location location = locationArray[i];
			coordinatesArray[i][0] = location.getX();
			coordinatesArray[i][1] = location.getY();
			coordinatesArray[i][2] = location.getZ();
		}

		return coordinatesArray;
	}

	public static void convertArrayToLocationArray(DynamicLocation[] locationArray, double[][] coordinatesArray) {
		for (int i = 0; i < coordinatesArray.length; i++) {
			double[] coordinates = coordinatesArray[i];
			locationArray[i].set(coordinates[0], coordinates[1], coordinates[2]);
		}
	}

	public static DynamicLocation[] removeLocations(DynamicLocation[] locations, DynamicLocation[] locationsToRemove) {
		for (DynamicLocation locationToRemove : locationsToRemove) {
			// Check if the location with specified coordinates exists
			for (int i = 0; i < locations.length; i++) {
				DynamicLocation location = locations[i];
				if (location.getX() == locationToRemove.getX()
						&& location.getY() == locationToRemove.getY()
						&& location.getZ() == locationToRemove.getZ()) {
					// Remove the location by shifting the remaining elements
					System.arraycopy(locations, i + 1, locations, i, locations.length - i - 1);
					// Resize the array
					locations = Arrays.copyOf(locations, locations.length - 1);
					break;
				}
			}
		}

		return locations;
	}
}
