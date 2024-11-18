package me.sashie.skdragon.util;

public class MathUtils {

    public static final float PI = 3.1415927f;
    public static final float PI2 = PI * 2;

    private static final int SIN_BITS = 14; // Determines the table size, 16KB for SIN_BITS = 14
    private static final int SIN_MASK = ~(-1 << SIN_BITS);
    private static final int SIN_COUNT = SIN_MASK + 1;
    private static final float radToIndex = SIN_COUNT / PI2;

    // Lookup tables
    private static final float[] SIN_TABLE = new float[SIN_COUNT];
    private static final float[] COS_TABLE = new float[SIN_COUNT];

    static {
        // Initialize the sine and cosine tables
        for (int i = 0; i < SIN_COUNT; i++) {
            float radians = (i + 0.5f) / SIN_COUNT * PI2;
            SIN_TABLE[i] = (float) Math.sin(radians);
            COS_TABLE[i] = (float) Math.cos(radians);
        }

        // Exact values for key angles to minimize rounding errors
        SIN_TABLE[0] = 0.0f;
        COS_TABLE[0] = 1.0f;
        SIN_TABLE[(int) (PI * radToIndex) & SIN_MASK] = 0.0f;
        COS_TABLE[(int) (PI * radToIndex) & SIN_MASK] = -1.0f;
    }

    /**
     * Returns the sine of the given angle in radians using the lookup table.
     */
    public static float sin(float radians) {
        return SIN_TABLE[(int) (radians * radToIndex) & SIN_MASK];
    }

    /**
     * Returns the cosine of the given angle in radians using the lookup table.
     */
    public static float cos(float radians) {
        return COS_TABLE[(int) (radians * radToIndex) & SIN_MASK];
    }
}

