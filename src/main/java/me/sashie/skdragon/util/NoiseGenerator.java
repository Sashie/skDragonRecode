package me.sashie.skdragon.util;

import org.bukkit.util.noise.PerlinNoiseGenerator;

import java.util.Random;

public class NoiseGenerator {
	private final PerlinNoiseGenerator perlinGenerator;

	public NoiseGenerator() {
		perlinGenerator = new PerlinNoiseGenerator(new Random());
	}

	// Generate Perlin noise at a specific location
	public double getPerlinNoise(double x, double y, double z) {
		return perlinGenerator.noise(x, y, z);
	}

	// Generate fractal noise at a specific location
	public double getFractalNoise(double x, double y, double z, int octaves, double persistence) {
		double total = 0;
		double frequency = 1.0;
		double amplitude = 1.0;
		double maxValue = 0;

		for (int i = 0; i < octaves; i++) {
			total += perlinGenerator.noise(x * frequency, y * frequency, z * frequency) * amplitude;
			maxValue += amplitude;
			amplitude *= persistence;
			frequency *= 2.0;
		}

		return total / maxValue;
	}
}
