package me.sashie.skdragon.util;

import org.bukkit.util.Vector;

import java.util.Random;

public final class RandomUtils {

    public static final Random random = new Random(System.nanoTime());

    public static double getRandomDouble() {
        return random.nextDouble();
    }

    public static Vector getRandomVector(Vector vector) {
        vector.setX(random.nextDouble() * 2 - 1).setY(random.nextDouble() * 2 - 1).setZ(random.nextDouble() * 2 - 1);
        return vector.normalize();
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

    public static double randomDoubleWithExclusion(double min, double max, double... exclude) {
        double output = min + ((max - min + 1 - exclude.length) * random.nextDouble());
		/*
		for (double i = 0; i < exclude.length; i++) {
			if (output == exclude[i]) {
				output++;
			}
		}
		*/
        for (double v : exclude) {
            if (v > output) {
                return output;
            }
            output++;
        }
        return output;
    }

    public static float random(float range) {
        return random.nextFloat() * range;
    }

    /**
     * Returns -1 or 1, randomly.
     */
    public static int randomSign() {
        return 1 | (random.nextInt() >> 31);
    }

}
