package me.sashie.skdragon.util;

/**
 * Utility and fast math functions.
 * <p>
 * Thanks to Riven on JavaGaming.org for the basis of sin/cos/atan2/floor/ceil.
 *
 * @author Nathan Sweet
 */
public final class MathUtils {

	// ---
	static public final float PI = 3.1415927f;
	static public final float PI2 = PI * 2;

	static private final int SIN_BITS = 14; // 16KB. Adjust for accuracy.
	static private final int SIN_MASK = ~(-1 << SIN_BITS);
	static private final int SIN_COUNT = SIN_MASK + 1;

	static private final float radFull = PI * 2;
	static private final float degFull = 360;
	static private final float radToIndex = SIN_COUNT / radFull;
	static private final float degToIndex = SIN_COUNT / degFull;

	/**
	 * multiply by this to convert from degrees to radians
	 */
	static public final float degreesToRadians = PI / 180;

	static private class Sin {

		static final float[] table = new float[SIN_COUNT];

		static {
			for (int i = 0; i < SIN_COUNT; i++) {
				table[i] = (float) Math.sin((i + 0.5f) / SIN_COUNT * radFull);
			}
			for (int i = 0; i < 360; i += 90) {
				table[(int) (i * degToIndex) & SIN_MASK] = (float) Math.sin(i * degreesToRadians);
			}
		}
	}

	/**
	 * Returns the sine in radians from a lookup table.
	 */
	static public float sin(float radians) {
		return Sin.table[(int) (radians * radToIndex) & SIN_MASK];
	}

	/**
	 * Returns the cosine in radians from a lookup table.
	 */
	static public float cos(float radians) {
		return Sin.table[(int) ((radians + PI / 2) * radToIndex) & SIN_MASK];
	}

}
