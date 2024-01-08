package me.sashie.skdragon.util.color;

import java.awt.Color;
import java.util.Random;

import me.sashie.skdragon.util.DynamicList;

// https://github.com/MatthewYork/Colours
public class ColorUtils {

	public static Color[] analagousColors(Color color) {
		float[] hsv = new float[3];
		hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);

		Color c1 = Color.getHSBColor(ColorUtils.addDegrees(hsv[0], 15), (float) (hsv[1] - 0.05), (float) (hsv[2] - 0.05));
		Color c2 = Color.getHSBColor(ColorUtils.addDegrees(hsv[0], 30), (float) (hsv[1] - 0.05), (float) (hsv[2] - 0.1));
		Color c3 = Color.getHSBColor(ColorUtils.addDegrees(hsv[0], -15), (float) (hsv[1] - 0.05), (float) (hsv[2] - 0.05));
		Color c4 = Color.getHSBColor(ColorUtils.addDegrees(hsv[0], -30), (float) (hsv[1] - 0.05), (float) (hsv[2] - 0.1));

		return new Color[] { c1, c2, c3, c4 };
	}

	public static Color[] monochromaticColors(Color color) {
		float[] hsv = new float[3];
		hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);

		Color c1 = Color.getHSBColor(hsv[0], (float) (hsv[1]), (float) (hsv[2] / 2));
		Color c2 = Color.getHSBColor(hsv[0], (float) (hsv[1] / 2), (float) (hsv[2] / 3));
		Color c3 = Color.getHSBColor(hsv[0], (float) (hsv[1] / 3), (float) (hsv[2] * 2 / 3));
		Color c4 = Color.getHSBColor(hsv[0], (float) (hsv[1]), (float) (hsv[2] * 4 / 5));

		return new Color[] { c1, c2, c3, c4 };
	}

	public static Color[] triadColors(Color color) {
		float[] hsv = new float[3];
		hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);

		Color c1 = Color.getHSBColor(ColorUtils.addDegrees(hsv[0], 120), (float) (hsv[1]), (float) (hsv[2]));
		Color c2 = Color.getHSBColor(ColorUtils.addDegrees(hsv[0], 120), (float) (hsv[1] * 7 / 6), (float) (hsv[2] - 0.05));
		Color c3 = Color.getHSBColor(ColorUtils.addDegrees(hsv[0], 240), (float) (hsv[1]), (float) (hsv[2]));
		Color c4 = Color.getHSBColor(ColorUtils.addDegrees(hsv[0], 240), (float) (hsv[1] * 7 / 6), (float) (hsv[2] - 0.05));

		return new Color[] { c1, c2, c3, c4 };
	}

	public static Color[] complementaryColors(Color color) {
		float[] hsv = new float[3];
		hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);

		Color c1 = Color.getHSBColor(hsv[0], (float) (hsv[1] * 5 / 7), (float) (hsv[2]));
		Color c2 = Color.getHSBColor(hsv[0], (float) (hsv[1]), (float) (hsv[2] * 4 / 5));
		Color c3 = Color.getHSBColor(ColorUtils.addDegrees(hsv[0], 180), (float) (hsv[1]), (float) (hsv[2]));
		Color c4 = Color.getHSBColor(ColorUtils.addDegrees(hsv[0], 180), (float) (hsv[1] * 5 / 7), (float) (hsv[2]));

		return new Color[] { c1, c2, c3, c4 };
	}

	public static Color complementaryColor(Color color) {
		float[] hsv = new float[3];
		hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
		float newH = ColorUtils.addDegrees(180, hsv[0]);
		hsv[0] = newH;

		return Color.getHSBColor(hsv[0], hsv[1], hsv[2]);
	}

	public static float addDegrees(float addDeg, float staticDeg) {
		staticDeg += addDeg;
		if (staticDeg > 360) {
			float offset = staticDeg - 360;
			return offset;
		} else if (staticDeg < 0) {
			return -1 * staticDeg;
		} else {
			return staticDeg;
		}
	}

	public static String toHTML(Color c) {
		String ret = "#";
		String hex;
		hex = Integer.toHexString(c.getRed());
		if (hex.length() < 2)
			hex = "0" + hex;
		ret += hex;
		hex = Integer.toHexString(c.getGreen());
		if (hex.length() < 2)
			hex = "0" + hex;
		ret += hex;
		hex = Integer.toHexString(c.getBlue());
		if (hex.length() < 2)
			hex = "0" + hex;
		ret += hex;
		return ret;
	}

	/**
	 * 
	 * 
	 * @param color
	 * @param factor
	 * @return
	 * @see Color#brighter
	 */
	public static Color brighten(Color color, double factor) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int alpha = color.getAlpha();

		int i = (int) (1.0 / (1.0 - factor));
		if (r == 0 && g == 0 && b == 0) {
			return new Color(i, i, i, alpha);
		}
		if (r > 0 && r < i)
			r = i;
		if (g > 0 && g < i)
			g = i;
		if (b > 0 && b < i)
			b = i;

		return new Color(Math.min((int) (r / factor), 255), Math.min((int) (g / factor), 255),
				Math.min((int) (b / factor), 255), alpha);
	}

	/**
	 * 
	 * @param color
	 * @param factor
	 * @return
	 * @see Color#darker
	 */
	public static Color darken(Color color, double factor) {
		return new Color(Math.max((int) (color.getRed() * factor), 0), Math.max((int) (color.getGreen() * factor), 0),
				Math.max((int) (color.getBlue() * factor), 0), color.getAlpha());
	}

	public static Color[] generateGradient(final Color one, final Color two, final int numSteps) {
		int r1 = one.getRed();
		int g1 = one.getGreen();
		int b1 = one.getBlue();
		int a1 = one.getAlpha();

		int r2 = two.getRed();
		int g2 = two.getGreen();
		int b2 = two.getBlue();
		int a2 = two.getAlpha();

		int newR = 0;
		int newG = 0;
		int newB = 0;
		int newA = 0;

		Color[] gradient = new Color[numSteps];
		double iNorm;
		for (int i = 0; i < numSteps; i++) {
			iNorm = i / (double) numSteps;
			newR = (int) (r1 + iNorm * (r2 - r1));
			newG = (int) (g1 + iNorm * (g2 - g1));
			newB = (int) (b1 + iNorm * (b2 - b1));
			newA = (int) (a1 + iNorm * (a2 - a1));
			gradient[i] = new Color(newR, newG, newB, newA);
		}

		return gradient;
	}

	public static Color[] generateMultiGradient(Color[] colors, int numSteps) {
		int numSections = colors.length - 1;
		int gradientIndex = 0;
		Color[] gradient = new Color[numSteps];
		Color[] temp;

		if (numSections <= 0)
			throw new IllegalArgumentException("Array requires at least 2 colors!");
		for (int section = 0; section < numSections; section++) {
			temp = generateGradient(colors[section], colors[section + 1], numSteps / numSections);
			for (int i = 0; i < temp.length; i++)
				gradient[gradientIndex++] = temp[i];
		}

		if (gradientIndex < numSteps) {
			for (/* nothing to initialize */; gradientIndex < numSteps; gradientIndex++) {
				gradient[gradientIndex] = colors[colors.length - 1];
			}
		}

		return gradient;
	}

	public static DynamicList<Color> generateMultiGradient2(Color[] colors, int numSteps) {
		int numSections = colors.length - 1;
		int gradientIndex = 0;
		DynamicList<Color> gradient = new DynamicList<Color>();
		Color[] temp;

		if (numSections <= 0)
			throw new IllegalArgumentException("Array requires at least 2 colors!");
		for (int section = 0; section < numSections; section++) {
			temp = generateGradient(colors[section], colors[section + 1], numSteps / numSections);
			for (int i = 0; i < temp.length; i++)
				gradient.add(gradientIndex++, temp[i]);
		}

		if (gradientIndex < numSteps) {
			for (/* nothing to initialize */; gradientIndex < numSteps; gradientIndex++) {
				gradient.add(gradientIndex, colors[colors.length - 1]);
			}
		}

		return gradient;
	}

	/**
	 * Generates a random color based on the average of a constant color
	 * 
	 * @param mix
	 * @return
	 */
	public static Color generateRandomColor(Color mix) {
		Random random = new Random();
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);

		if (mix != null) {
			red = (red + mix.getRed()) / 2;
			green = (green + mix.getGreen()) / 2;
			blue = (blue + mix.getBlue()) / 2;
		}

		Color color = new Color(red, green, blue);
		return color;
	}

	public static Color[] generateRandomColors(Color mix, int numColors) {
		Color[] colors = new Color[numColors];
		for (int i = 0; i < numColors; i++) {
			colors[i] = generateRandomColor(mix);
		}
		return colors;
	}

	/**
	 * Generate terrain color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 */
	public static Color[] terrain(int n) {
		return terrain(n, 1.0f);
	}

	/**
	 * Generate terrain color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 * @param alpha
	 *            the parameter in [0,1] for transparency.
	 */
	public static Color[] terrain(int n, float alpha) {
		int k = n / 2;
		float[] H = { 4 / 12f, 2 / 12f, 0 / 12f };
		float[] S = { 1f, 1f, 0f };
		float[] V = { 0.65f, 0.9f, 0.95f };

		Color[] palette = new Color[n];

		float h = H[0];
		float hw = (H[1] - H[0]) / (k - 1);

		float s = S[0];
		float sw = (S[1] - S[0]) / (k - 1);

		float v = V[0];
		float vw = (V[1] - V[0]) / (k - 1);

		for (int i = 0; i < k; i++) {
			palette[i] = hsv(h, s, v, alpha);
			h += hw;
			s += sw;
			v += vw;
		}

		h = H[1];
		hw = (H[2] - H[1]) / (n - k);

		s = S[1];
		sw = (S[2] - S[1]) / (n - k);

		v = V[1];
		vw = (V[2] - V[1]) / (n - k);

		for (int i = k; i < n; i++) {
			h += hw;
			s += sw;
			v += vw;
			palette[i] = hsv(h, s, v, alpha);
		}

		return palette;
	}

	/**
	 * Generate topo color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 */
	public static Color[] topo(int n) {
		return topo(n, 1.0f);
	}

	/**
	 * Generate topo color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 * @param alpha
	 *            the parameter in [0,1] for transparency.
	 */
	public static Color[] topo(int n, float alpha) {
		int j = n / 3;
		int k = n / 3;
		int i = n - j - k;

		Color[] palette = new Color[n];

		float h = 43 / 60.0f;
		float hw = (31 / 60.0f - h) / (i - 1);
		int l = 0;
		for (; l < i; l++) {
			palette[l] = hsv(h, 1.0f, 1.0f, alpha);
			h += hw;
		}

		h = 23 / 60.0f;
		hw = (11 / 60.0f - h) / (j - 1);
		for (; l < i + j; l++) {
			palette[l] = hsv(h, 1.0f, 1.0f, alpha);
			h += hw;
		}

		h = 10 / 60.0f;
		hw = (6 / 60.0f - h) / (k - 1);
		float s = 1.0f;
		float sw = (0.3f - s) / (k - 1);
		for (; l < n; l++) {
			palette[l] = hsv(h, s, 1.0f, alpha);
			h += hw;
			s += sw;
		}

		return palette;
	}

	/**
	 * Generate jet color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 */
	public static Color[] jet(int n) {
		return jet(n, 1.0f);
	}

	/**
	 * Generate jet color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 * @param alpha
	 *            the parameter in [0,1] for transparency.
	 */
	public static Color[] jet(int n, float alpha) {
		int m = (int) Math.ceil(n / 4);

		float[] u = new float[3 * m];
		for (int i = 0; i < u.length; i++) {
			if (i == 0) {
				u[i] = 0.0f;
			} else if (i <= m) {
				u[i] = i / (float) m;
			} else if (i <= 2 * m - 1) {
				u[i] = 1.0f;
			} else {
				u[i] = (3 * m - i) / (float) m;
			}

		}

		int m2 = m / 2 + m % 2;
		int mod = n % 4;
		int[] r = new int[n];
		int[] g = new int[n];
		int[] b = new int[n];
		for (int i = 0; i < u.length - 1; i++) {
			if (m2 - mod + i < n) {
				g[m2 - mod + i] = i + 1;
			}
			if (m2 - mod + i + m < n) {
				r[m2 - mod + i + m] = i + 1;
			}
			if (i > 0 && m2 - mod + i < u.length) {
				b[i] = m2 - mod + i;
			}
		}

		Color[] palette = new Color[n];
		for (int i = 0; i < n; i++) {
			palette[i] = new Color(u[r[i]], u[g[i]], u[b[i]], alpha);
		}

		return palette;
	}

	/**
	 * Generate red-green color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 */
	public static Color[] redgreen(int n) {
		return redgreen(n, 1.0f);
	}

	/**
	 * Generate red-green color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 * @param alpha
	 *            the parameter in [0,1] for transparency.
	 */
	public static Color[] redgreen(int n, float alpha) {
		Color[] palette = new Color[n];
		for (int i = 0; i < n; i++) {
			palette[i] = new Color((float) Math.sqrt((i + 1.0f) / n), (float) Math.sqrt(1 - (i + 1.0f) / n), 0.0f,
					alpha);
		}

		return palette;
	}

	/**
	 * Generate red-blue color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 */
	public static Color[] redblue(int n) {
		return redblue(n, 1.0f);
	}

	/**
	 * Generate red-blue color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 * @param alpha
	 *            the parameter in [0,1] for transparency.
	 */
	public static Color[] redblue(int n, float alpha) {
		Color[] palette = new Color[n];
		for (int i = 0; i < n; i++) {
			palette[i] = new Color((float) Math.sqrt((i + 1.0f) / n), 0.0f, (float) Math.sqrt(1 - (i + 1.0f) / n),
					alpha);
		}

		return palette;
	}

	/**
	 * Generate heat color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 */
	public static Color[] heat(int n) {
		return heat(n, 1.0f);
	}

	/**
	 * Generate heat color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 * @param alpha
	 *            the parameter in [0,1] for transparency.
	 */
	public static Color[] heat(int n, float alpha) {
		int j = n / 4;
		int k = n - j;
		float h = 1.0f / 6;

		Color[] c = rainbow(k, 0, h, alpha);

		Color[] palette = new Color[n];
		System.arraycopy(c, 0, palette, 0, k);

		float s = 1 - 1.0f / (2 * j);
		float end = 1.0f / (2 * j);
		float w = (end - s) / (j - 1);

		for (int i = k; i < n; i++) {
			palette[i] = hsv(h, s, 1.0f, alpha);
			s += w;
		}

		return palette;
	}

	/**
	 * Generate rainbow color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 */
	public static Color[] rainbow(int n) {
		return rainbow(n, 1.0f);
	}

	/**
	 * Generate rainbow color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 * @param alpha
	 *            the parameter in [0,1] for transparency.
	 */
	public static Color[] rainbow(int n, float alpha) {
		return rainbow(n, 0.0f, (float) (n - 1) / n, alpha);
	}

	/**
	 * Generate rainbow color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 * @param start
	 *            the start of h in the HSV color model.
	 * @param end
	 *            the start of h in the HSV color model.
	 * @param alpha
	 *            the parameter in [0,1] for transparency.
	 */
	private static Color[] rainbow(int n, float start, float end, float alpha) {
		return rainbow(n, start, end, 1.0f, 1.0f, alpha);
	}

	/**
	 * Generate rainbow color palette.
	 * 
	 * @param n
	 *            the number of colors in the palette.
	 * @param start
	 *            the start of h in the HSV color model.
	 * @param end
	 *            the start of h in the HSV color model.
	 * @param s
	 *            the s in the HSV color model.
	 * @param v
	 *            the v in the HSV color model.
	 * @param alpha
	 *            the parameter in [0,1] for transparency.
	 */
	private static Color[] rainbow(int n, float start, float end, float s, float v, float alpha) {
		Color[] palette = new Color[n];
		float h = start;
		float w = (end - start) / (n - 1);
		for (int i = 0; i < n; i++) {
			palette[i] = hsv(h, s, v, alpha);
			h += w;
		}

		return palette;
	}

	/**
	 * Generate a color based on HSV model.
	 */
	private static Color hsv(float h, float s, float v, float alpha) {
		float r = 0;
		float g = 0;
		float b = 0;

		if (s == 0) {
			// this color in on the black white center line <=> h = UNDEFINED
			// Achromatic color, there is no hue
			r = v;
			g = v;
			b = v;
		} else {
			if (h == 1.0f) {
				h = 0.0f;
			}

			// h is now in [0,6)
			h *= 6;

			int i = (int) Math.floor(h);
			float f = h - i; // f is fractional part of h
			float p = v * (1 - s);
			float q = v * (1 - (s * f));
			float t = v * (1 - (s * (1 - f)));

			switch (i) {
			case 0:
				r = v;
				g = t;
				b = p;
				break;

			case 1:
				r = q;
				g = v;
				b = p;
				break;

			case 2:
				r = p;
				g = v;
				b = t;
				break;

			case 3:
				r = p;
				g = q;
				b = v;
				break;

			case 4:
				r = t;
				g = p;
				b = v;
				break;

			case 5:
				r = v;
				g = p;
				b = q;
				break;

			}
		}

		return new Color(r, g, b, alpha);
	}
}
