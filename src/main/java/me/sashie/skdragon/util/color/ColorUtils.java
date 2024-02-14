package me.sashie.skdragon.util.color;

import ch.njol.skript.util.Color;
import ch.njol.skript.util.ColorRGB;
import me.sashie.skdragon.util.DynamicList;

import java.util.Random;

public class ColorUtils {

	public static Color[] complementaryColors(Color color) {
		float[] hsv = new float[3];
		org.bukkit.Color c = color.asBukkitColor();
		hsv = java.awt.Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsv);

		java.awt.Color c1 = java.awt.Color.getHSBColor(hsv[0], hsv[1] * 5 / 7, hsv[2]);
		java.awt.Color c2 = java.awt.Color.getHSBColor(hsv[0], hsv[1], hsv[2] * 4 / 5);
		java.awt.Color c3 = java.awt.Color.getHSBColor(ColorUtils.addDegrees(hsv[0], 180), hsv[1], hsv[2]);
		java.awt.Color c4 = java.awt.Color.getHSBColor(ColorUtils.addDegrees(hsv[0], 180), hsv[1] * 5 / 7, hsv[2]);

		return new Color[]{new ColorRGB(c1.getRed(), c1.getGreen(), c1.getBlue()),
				new ColorRGB(c2.getRed(), c2.getGreen(), c2.getBlue()),
				new ColorRGB(c3.getRed(), c3.getGreen(), c3.getBlue()),
				new ColorRGB(c4.getRed(), c4.getGreen(), c4.getBlue())
		};
	}

	public static float addDegrees(float addDeg, float staticDeg) {
		staticDeg += addDeg;
		if (staticDeg > 360) {
			return staticDeg - 360;
		} else if (staticDeg < 0) {
			return -1 * staticDeg;
		} else {
			return staticDeg;
		}
	}


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

		return new ColorRGB(red, green, blue);
	}

	public static Color[] generateRandomColors(Color mix, int numColors) {
		Color[] colors = new Color[numColors];
		for (int i = 0; i < numColors; i++) {
			colors[i] = generateRandomColor(mix);
		}
		return colors;
	}


	/**
	 * Generate jet color palette.
	 *
	 * @param n the number of colors in the palette.
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
	 * Generate heat color palette.
	 *
	 * @param n the number of colors in the palette.
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
	 * @param n the number of colors in the palette.
	 */
	public static Color[] rainbow(int n) {
		return rainbow(n, 0.0f, (float) (n - 1) / n);
	}

	/**
	 * Generate rainbow color palette.
	 *
	 * @param n	 the number of colors in the palette.
	 * @param start the start of h in the HSV color model.
	 * @param end   the start of h in the HSV color model.
	 */
	private static Color[] rainbow(int n, float start, float end) {
		return rainbow(n, start, end, 1.0f, 1.0f);
	}

	/**
	 * Generate rainbow color palette.
	 *
	 * @param n	 the number of colors in the palette.
	 * @param start the start of h in the HSV color model.
	 * @param end   the start of h in the HSV color model.
	 * @param s	 the s in the HSV color model.
	 * @param v	 the v in the HSV color model.
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
