package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.bmp.BmpDecoder;
import sample.bmp.BmpFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller {
    @FXML
    private ImageView imageView;

    @FXML
    void initialize() throws IOException {
        String fileName = "50.bmp";

        File file = new File(fileName);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

        BmpDecoder bmpDecoder = new BmpDecoder();
        BmpFile bmp = bmpDecoder.decode(fileName);
        int[][] matrix = bmp.getPixels();
        for (int[] line : matrix) {
            for(int pixel : line) {
                System.out.print(pixel + " ");
            }
            System.out.println();
        }

    }
}
