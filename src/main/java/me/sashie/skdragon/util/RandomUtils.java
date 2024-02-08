package me.sashie.skdragon.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public final class RandomUtils {

	public static final Random random = new Random(System.nanoTime());
	
	private RandomUtils() {
		// No instance allowed
	}

	public static double getRandomDouble() {
		return random.nextDouble();
	}

	public static Vector getRandomVector(Vector vector) {
		vector.setX(random.nextDouble() * 2 - 1).setY(random.nextDouble() * 2 - 1).setZ(random.nextDouble() * 2 - 1);
		return vector.normalize();
	}
	
	public static Vector getRandomCircleVector(Vector vector) {
		double rnd, x, z;
		rnd = getRandomAngle();
		x = Math.cos(rnd);
		z = Math.sin(rnd);

		vector.setX(x).setY(0).setZ(z);
		return vector;
	}

	public static Vector getRandomVectorAlongLine(Vector vector, Location begin, Location end) {
		// Generate a random point along the line between 'begin' and 'end'
		double randomT = random.nextDouble();
		double x = begin.getX() + (end.getX() - begin.getX()) * randomT;
		double y = begin.getY() + (end.getY() - begin.getY()) * randomT;
		double z = begin.getZ() + (end.getZ() - begin.getZ()) * randomT;

		// Calculate the vector from 'begin' to the random point
		vector.setX(x - begin.getX()).setY(y - begin.getY()).setZ(z - begin.getZ());

		// Normalize the vector
		return vector.normalize();
	}

	public static Vector getRandomVectorLine(Vector vector) {
		final double minX = -5.0;
		final double maxX = 5.0;
		final double minY = -5.0;
		final double maxY = -1.0;
		final double minZ = -5.0;
		final double maxZ = 5.0;

		double randomX = Math.random() * (maxX - minX) + minX;
		double randomY = Math.random() * (maxY - minY) + minY;
		double randomZ = Math.random() * (maxZ - minZ) + minZ;

		vector.setX(randomX).setY(randomY).setZ(randomZ);
		return vector.normalize();
	}

	public static Material getRandomMaterial(Material[] materials) {
		return materials[random.nextInt(materials.length)];
	}
	
	public static double getRandomAngle() {
		return random.nextDouble() * (2 * Math.PI);
	}

    public static float randomRangeFloat(float min, float max) {
    	if (min >= max)
    		return max;
        return random.nextFloat() * (max - min) + min;
    }

    public static double randomRangeDouble(double min, double max) {
    	if (min >= max)
    		return max;
        return random.nextDouble() * (max - min) + min;
    }
    
    public static int randomRangeInt(int min, int max) {
    	if (min >= max)
    		return max;
       	if (min < 0) {
    		min = 0;
    	}
       	if (max < 0) {
       		max = 0;
    	}
        //return (int)((Math.random() < 0.5) ? ((1.0 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min));
        return random.nextInt(max - min + 1) + min;
    }
    
    public static double randomExcludedDouble(double min, double max, double... array) {
    	double out = min + (max - min) * random.nextDouble();
    	double output = (Math.random() < 0.5) ? ((1.0 - Math.random()) * (max - min) + min + 1 - array.length) : (Math.random() * (max - min) + min + 1 - array.length);
        for (int length = array.length, n = 0; n < length && output >= array[n]; ++output, ++n) {}
        return output;
    }

    public static double randomDoubleWithExclusion(double min, double max, double... exclude) {
    	double output = min + ((max - min + 1 - exclude.length) * random.nextDouble());
    	/*
    	for (double i = 0; i < exclude.length; i++) {
    		if (output == exclude[i]) {
    			output++;
    		}
    	}
    	*/
    	for(int i = 0; i < exclude.length; i++) {
            if(exclude[i] > output) {
                return output;
            }
            output++;
        }
        return output;
    }

	public static int randomIntWithExclusion(int min, int max, int... exclude) {
		int range = max - min + 1 - exclude.length;
		if (range <= 0) {
			throw new IllegalArgumentException("Invalid range or exclusions.");
		}

		int randomValue = random.nextInt(range);
		for (int excluded : exclude) {
			if (randomValue < excluded - min) {
				break;
			}
			randomValue++;
		}

		return min + randomValue;
	}

	public static int randomIntWithExclusion2(int min, int max, int... exclude) {
		// Calculate the number of excluded values
		int numExcluded = exclude.length;

		// Calculate the range size after excluding values
		int rangeSize = (max - min + 1) - numExcluded;

		// If the range is empty after excluding values, return a default value (e.g., -1)
		if (rangeSize <= 0) {
			return -1;
		}

		// Generate a random integer within the range
		int randomValue = min + random.nextInt(rangeSize);

		// Adjust the generated value to account for excluded values
		for (int excludedValue : exclude) {
			if (randomValue >= excludedValue) {
				randomValue++; // Shift the value to avoid the excluded value
			}
		}

		return randomValue;
	}

	public static int[] randomIntegersWithExclusions(int min, int max, int count, int... exclude) {
		if (count > (max - min + 1 - exclude.length)) {
			throw new IllegalArgumentException("Not enough valid values to generate the specified count.");
		}

		int[] randomValues = new int[count];
		int currentMin = min;

		for (int i = 0; i < count; i++) {
			randomValues[i] = randomIntWithExclusion(currentMin, max, exclude);
			exclude = Arrays.copyOf(exclude, exclude.length + 1);
			exclude[exclude.length - 1] = randomValues[i];
			currentMin = randomValues[i] + 1;
		}

		return randomValues;
	}

	public static float random(float range) {
		return random.nextFloat() * range;
	}

    /** Returns -1 or 1, randomly. */
	public static int randomSign() {
		return 1 | (random.nextInt() >> 31);
	}
	
	public static boolean getRandomBoolean() {
        return random.nextBoolean();
    }
	
	public static String getRandomStringFromArray(String... strings) {
	    List<String> list = Arrays.asList(strings);
	    return list.get(random.nextInt(list.size()));
	}
	
	public static boolean chancePercent(int percent) {
		return random.nextDouble() < (percent / 100);
	}

	public static class WeightedList<T extends Object> {

	    private class Entry {
	        double accumulatedWeight;
	        T object;
	    }

	    private List<Entry> entries = new ArrayList<>();
	    private double accumulatedWeight;
	    private Random rand = new Random();

	    public void addEntry(T object, double weight) {
	        accumulatedWeight += weight;
	        Entry e = new Entry();
	        e.object = object;
	        e.accumulatedWeight = accumulatedWeight;
	        entries.add(e);
	    }

	    public T getRandom() {
	        double r = rand.nextDouble() * accumulatedWeight;

	        for (Entry entry: entries) {
	            if (entry.accumulatedWeight >= r) {
	                return entry.object;
	            }
	        }
	        return null; //should only happen when there are no entries
	    }
	}
	
}
