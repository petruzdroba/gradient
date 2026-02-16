package com.pz.demo.rest;

import com.pz.demo.service.GradientService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private final GradientService service;

    public ErrorController(GradientService service) {
        this.service = service;
    }

    @RequestMapping("/error")
    public ResponseEntity<byte[]> handleError() throws Exception {
        BufferedImage image = service.generate("error", 512);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }
}
