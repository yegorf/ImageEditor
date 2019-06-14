package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.bmp.BmpDecoder;

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
        System.out.println("kek");
        File file = new File("image.bmp");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

        BmpDecoder bmpDecoder = new BmpDecoder();
        bmpDecoder.decode("image.bmp");

    }
}
