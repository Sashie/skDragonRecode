package me.sashie.skdragon.project.util.color;

import java.util.Random;

import ch.njol.skript.util.Color;
import ch.njol.skript.util.ColorRGB;
import me.sashie.skdragon.project.util.DynamicList;

public class ColorUtils {

	public static Color[] complementaryColors(Color color) {
		float[] hsv = new float[3];
		org.bukkit.Color c = color.asBukkitColor();
		hsv = java.awt.Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsv);

		java.awt.Color c1 = java.awt.Color.getHSBColor(hsv[0], (float) (hsv[1] * 5 / 7), (float) (hsv[2]));
		java.awt.Color c2 = java.awt.Color.getHSBColor(hsv[0], (float) (hsv[1]), (float) (hsv[2] * 4 / 5));
		java.awt.Color c3 = java.awt.Color.getHSBColor(ColorUtils.addDegrees(hsv[0], 180), (float) (hsv[1]), (float) (hsv[2]));
		java.awt.Color c4 = java.awt.Color.getHSBColor(ColorUtils.addDegrees(hsv[0], 180), (float) (hsv[1] * 5 / 7), (float) (hsv[2]));

		return new Color[] { new ColorRGB(c1.getRed(), c1.getGreen(), c1.getBlue()),
				new ColorRGB(c2.getRed(), c2.getGreen(), c2.getBlue()),
				new ColorRGB(c3.getRed(), c3.getGreen(), c3.getBlue()),
				new ColorRGB(c4.getRed(), c4.getGreen(), c4.getBlue())
		};
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

	public static String toHTML(Color color) {
		org.bukkit.Color c = color.asBukkitColor();
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
	 * @see java.awt.Color#brighter
	 */
	public static Color brighten(Color color, double factor) {
		org.bukkit.Color c = color.asBukkitColor();
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();

		int i = (int) (1.0 / (1.0 - factor));
		if (r == 0 && g == 0 && b == 0) {
			return new ColorRGB(i, i, i);
		}
		if (r > 0 && r < i)
			r = i;
		if (g > 0 && g < i)
			g = i;
		if (b > 0 && b < i)
			b = i;

		return new ColorRGB(Math.min((int) (r / factor), 255), Math.min((int) (g / factor), 255),
				Math.min((int) (b / factor), 255));
	}

	/**
	 * 
	 * @param color
	 * @param factor
	 * @return
	 * @see java.awt.Color#darker
	 */
	public static Color darken(Color color, double factor) {
		org.bukkit.Color c = color.asBukkitColor();
		return new ColorRGB(Math.max((int) (c.getRed() * factor), 0), Math.max((int) (c.getGreen() * factor), 0),
				Math.max((int) (c.getBlue() * factor), 0));
	}

	public static Color[] generateGradient(final Color one, final Color two, final int numSteps) {
		org.bukkit.Color c1 = one.asBukkitColor();
		int r1 = c1.getRed();
		int g1 = c1.getGreen();
		int b1 = c1.getBlue();

		org.bukkit.Color c2 = two.asBukkitColor();
		int r2 = c2.getRed();
		int g2 = c2.getGreen();
		int b2 = c2.getBlue();

		int newR = 0;
		int newG = 0;
		int newB = 0;

		Color[] gradient = new Color[numSteps];
		double iNorm;
		for (int i = 0; i < numSteps; i++) {
			iNorm = i / (double) numSteps;
			newR = (int) (r1 + iNorm * (r2 - r1));
			newG = (int) (g1 + iNorm * (g2 - g1));
			newB = (int) (b1 + iNorm * (b2 - b1));
			gradient[i] = new ColorRGB(newR, newG, newB);
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

	public static DynamicList<org.bukkit.Color> generateMultiGradient2(Color[] colors, int numSteps) {
		int numSections = colors.length - 1;
		int gradientIndex = 0;
		DynamicList<org.bukkit.Color> gradient = new DynamicList<org.bukkit.Color>();
		Color[] temp;

		if (numSections <= 0)
			throw new IllegalArgumentException("Array requires at least 2 colors!");
		for (int section = 0; section < numSections; section++) {
			temp = generateGradient(colors[section], colors[section + 1], numSteps / numSections);
			for (int i = 0; i < temp.length; i++)
				gradient.add(gradientIndex++, temp[i].asBukkitColor());
		}

		if (gradientIndex < numSteps) {
			for (/* nothing to initialize */; gradientIndex < numSteps; gradientIndex++) {
				gradient.add(gradientIndex, colors[colors.length - 1].asBukkitColor());
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
			org.bukkit.Color c = mix.asBukkitColor();
			red = (red + c.getRed()) / 2;
			green = (green + c.getGreen()) / 2;
			blue = (blue + c.getBlue()) / 2;
		}

		Color color = new ColorRGB(red, green, blue);
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
	 *			the number of colors in the palette.
	 */
	public static Color[] terrain(int n) {
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
			palette[i] = hsv(h, s, v);
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
			palette[i] = hsv(h, s, v);
		}

		return palette;
	}

	/**
	 * Generate topo color palette.
	 * 
	 * @param n
	 *			the number of colors in the palette.
	 */
	public static Color[] topo(int n) {
		int j = n / 3;
		int k = n / 3;
		int i = n - j - k;

		Color[] palette = new Color[n];

		float h = 43 / 60.0f;
		float hw = (31 / 60.0f - h) / (i - 1);
		int l = 0;
		for (; l < i; l++) {
			palette[l] = hsv(h, 1.0f, 1.0f);
			h += hw;
		}

		h = 23 / 60.0f;
		hw = (11 / 60.0f - h) / (j - 1);
		for (; l < i + j; l++) {
			palette[l] = hsv(h, 1.0f, 1.0f);
			h += hw;
		}

		h = 10 / 60.0f;
		hw = (6 / 60.0f - h) / (k - 1);
		float s = 1.0f;
		float sw = (0.3f - s) / (k - 1);
		for (; l < n; l++) {
			palette[l] = hsv(h, s, 1.0f);
			h += hw;
			s += sw;
		}

		return palette;
	}

	/**
	 * Generate jet color palette.
	 * 
	 * @param n
	 *			the number of colors in the palette.
	 */
	public static Color[] jet(int n) {
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
			palette[i] = new ColorRGB((int) u[r[i]], (int) u[g[i]], (int) u[b[i]]);
		}

		return palette;
	}

	/**
	 * Generate red-green color palette.
	 * 
	 * @param n
	 *			the number of colors in the palette.
	 */
	public static Color[] redgreen(int n) {
		Color[] palette = new ColorRGB[n];
		for (int i = 0; i < n; i++) {
			palette[i] = new ColorRGB((int) Math.sqrt((i + 1.0f) / n), (int) Math.sqrt(1 - (i + 1.0f) / n), 0);
		}

		return palette;
	}

	/**
	 * Generate red-blue color palette.
	 * 
	 * @param n
	 *			the number of colors in the palette.
	 * @param alpha
	 *			the parameter in [0,1] for transparency.
	 */
	public static Color[] redblue(int n, float alpha) {
		Color[] palette = new Color[n];
		for (int i = 0; i < n; i++) {
			palette[i] = new ColorRGB((int) Math.sqrt((i + 1.0f) / n), 0, (int) Math.sqrt(1 - (i + 1.0f) / n));
		}

		return palette;
	}

	/**
	 * Generate heat color palette.
	 * 
	 * @param n
	 *			the number of colors in the palette.
	 */
	public static Color[] heat(int n) {
		int j = n / 4;
		int k = n - j;
		float h = 1.0f / 6;

		Color[] c = rainbow(k, 0, h);

		Color[] palette = new Color[n];
		System.arraycopy(c, 0, palette, 0, k);

		float s = 1 - 1.0f / (2 * j);
		float end = 1.0f / (2 * j);
		float w = (end - s) / (j - 1);

		for (int i = k; i < n; i++) {
			palette[i] = hsv(h, s, 1.0f);
			s += w;
		}

		return palette;
	}

	/**
	 * Generate rainbow color palette.
	 * 
	 * @param n
	 *			the number of colors in the palette.
	 */
	public static Color[] rainbow(int n) {
		return rainbow(n, 0.0f, (float) (n - 1) / n);
	}

	/**
	 * Generate rainbow color palette.
	 * 
	 * @param n
	 *			the number of colors in the palette.
	 * @param start
	 *			the start of h in the HSV color model.
	 * @param end
	 *			the start of h in the HSV color model.
	 */
	private static Color[] rainbow(int n, float start, float end) {
		return rainbow(n, start, end, 1.0f, 1.0f);
	}

	/**
	 * Generate rainbow color palette.
	 * 
	 * @param n
	 *			the number of colors in the palette.
	 * @param start
	 *			the start of h in the HSV color model.
	 * @param end
	 *			the start of h in the HSV color model.
	 * @param s
	 *			the s in the HSV color model.
	 * @param v
	 *			the v in the HSV color model.
	 */
	private static Color[] rainbow(int n, float start, float end, float s, float v) {
		Color[] palette = new Color[n];
		float h = start;
		float w = (end - start) / (n - 1);
		for (int i = 0; i < n; i++) {
			palette[i] = hsv(h, s, v);
			h += w;
		}

		return palette;
	}

	/**
	 * Generate a color based on HSV model.
	 */
	private static Color hsv(float h, float s, float v) {
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

		return new ColorRGB((int) r, (int) g, (int) b);
	}
}
