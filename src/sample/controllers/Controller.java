package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.File;

public class Controller {
    @FXML
    private ImageView imageView;

    @FXML
    void initialize() {
        System.out.println("kek");
        File file = new File("image.jpg");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

    }
}
