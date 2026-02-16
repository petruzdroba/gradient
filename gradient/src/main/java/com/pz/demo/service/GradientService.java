package com.pz.demo.service;

import java.awt.image.BufferedImage;

public interface GradientService {
     BufferedImage generate(int size);

     BufferedImage generate(String seed, int size);
}
