package com.pz.demo.rest;

import com.pz.demo.service.GradientService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@RestController
public class GradientController {
    private final GradientService service;

    public GradientController(GradientService service) {
        this.service = service;
    }

    @GetMapping("/generate-gradient")
    public ResponseEntity<byte[]> generateGradient() throws Exception {
        BufferedImage image = service.createRandomGradient(512);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }
}
