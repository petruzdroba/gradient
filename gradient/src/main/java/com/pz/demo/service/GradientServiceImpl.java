package com.pz.demo.service;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Service
public class GradientServiceImpl implements GradientService {

    public BufferedImage generate(int size) {
        Random random = new Random();

        // 1% chance for radial, 99% for bilinear
        if (random.nextInt(100) == 0) {
            return createRadialGradient(size, random);
        } else {
            return createBilinearGradient(size, random);
        }
    }

    @Override
    public BufferedImage generate(String seed, int size) {
        Random random = new Random(seed.hashCode());

        if (random.nextInt(100) == 0) {
            return createRadialGradient(size, random);
        } else {
            return createBilinearGradient(size, random);
        }
    }

    private BufferedImage createBilinearGradient(int size, Random random) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        Color topLeft = getWeightedRandomColor(random);
        Color topRight = getWeightedRandomColor(random);
        Color bottomLeft = getWeightedRandomColor(random);
        Color bottomRight = getWeightedRandomColor(random);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                float tx = (float) x / (size - 1);
                float ty = (float) y / (size - 1);

                int topR = (int) (topLeft.getRed() * (1 - tx) + topRight.getRed() * tx);
                int topG = (int) (topLeft.getGreen() * (1 - tx) + topRight.getGreen() * tx);
                int topB = (int) (topLeft.getBlue() * (1 - tx) + topRight.getBlue() * tx);

                int bottomR = (int) (bottomLeft.getRed() * (1 - tx) + bottomRight.getRed() * tx);
                int bottomG = (int) (bottomLeft.getGreen() * (1 - tx) + bottomRight.getGreen() * tx);
                int bottomB = (int) (bottomLeft.getBlue() * (1 - tx) + bottomRight.getBlue() * tx);

                int r = (int) (topR * (1 - ty) + bottomR * ty);
                int g = (int) (topG * (1 - ty) + bottomG * ty);
                int b = (int) (topB * (1 - ty) + bottomB * ty);

                image.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }

        return image;
    }

    private BufferedImage createRadialGradient(int size, Random random) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        Color centerColor = getWeightedRandomColor(random);
        Color edgeColor = getWeightedRandomColor(random);

        float centerX = size / 2f;
        float centerY = size / 2f;
        float maxDistance = (float) Math.sqrt(centerX * centerX + centerY * centerY);

        float power = 0.5f + random.nextFloat() * 2.5f; // Range: 0.5 to 3.0

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                float dx = x - centerX;
                float dy = y - centerY;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);
                float t = Math.min(distance / maxDistance, 1.0f);

                t = (float) Math.pow(t, power);

                int r = (int) (centerColor.getRed() * (1 - t) + edgeColor.getRed() * t);
                int g = (int) (centerColor.getGreen() * (1 - t) + edgeColor.getGreen() * t);
                int b = (int) (centerColor.getBlue() * (1 - t) + edgeColor.getBlue() * t);

                image.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }

        return image;
    }

    private Color getWeightedRandomColor(Random random) {
        int roll = random.nextInt(100);

        if (roll < 30) {
            return getRandomHexColor(random);
        }
        else {
            int r = getWeightedColorValue(random);
            int g = getWeightedColorValue(random);
            int b = getWeightedColorValue(random);
            return new Color(r, g, b);
        }
    }

    private Color getRandomHexColor(Random random) {
        String hex = String.format("%06x", random.nextInt(0xFFFFFF + 1));

        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        return new Color(r, g, b);
    }

    private int getWeightedColorValue(Random random) {
        int roll = random.nextInt(100);
        if (roll < 80) {
            return 50 + random.nextInt(156); // 50-205
        }
        else {
            return random.nextInt(256); // 0-255
        }
    }
}