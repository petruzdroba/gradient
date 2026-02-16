package com.pz.demo.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Random;

@RestController
public class GradientController {

    @GetMapping("/generate-gradient")
    public ResponseEntity<byte[]> generateGradient() throws Exception {
        BufferedImage image = createRandomGradient(512);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }

    private BufferedImage createRandomGradient(int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Random random = new Random();

        Color topLeft = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        Color topRight = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        Color bottomLeft = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        Color bottomRight = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));

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
}
