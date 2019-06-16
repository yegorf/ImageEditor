package sample.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import sample.bmp.BmpFile;
import sample.bmp.DecoderBmp;
import sample.canvas.Canvas;
import sample.jpeg.JpegEncoder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Controller {
    @FXML
    private Pane pane;

    @FXML
    private ImageView imageView;

    @FXML
    private Button canvasSizeBtn;

    @FXML
    private Button canvasColorBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button chooseBtn;

    @FXML
    private Button imgSizeBtn;

    @FXML
    private TextField canvasSize;

    @FXML
    private TextField canvasColor;

    @FXML
    private TextField imageWidth;

    @FXML
    private TextField imageHeight;

    private BmpFile bmp = new BmpFile();

    @FXML
    void initialize() throws Exception {
        Canvas canvas = new Canvas();

        chooseBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            StringBuilder fileName = new StringBuilder();

            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("BMP images (*.bmp)", "*.bmp"));
            File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
            if (file != null) {
                //System.out.println(file.getName());
                fileName.append(file.getAbsolutePath());
                try {
                    decode(fileName.toString());
                    canvas.setMatrix(bmp.getPixels());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        canvasSizeBtn.setOnAction(e -> {
            int size = Integer.parseInt(canvasSize.getText());
            int[][] matrix = canvas.resize(size);
            bmp.setHeight(matrix.length);
            bmp.setWidth(matrix[0].length);
            bmp.setPixels(matrix);
            drawImage(bmp, imageView);
        });

        canvasColorBtn.setOnAction(e -> {
            int color = Integer.parseInt(canvasColor.getText());
            int[][] matrix = canvas.changeColor(bmp.getPixels(), bmp.getWidth(), bmp.getHeight(), color);
            bmp.setPixels(matrix);
            drawImage(bmp, imageView);
        });

        saveBtn.setOnAction(e -> {
            try {
                JpegEncoder.saveJpeg(bmp.getPixels(), bmp.getWidth(), bmp.getHeight());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        imgSizeBtn.setOnAction(e -> {
            int width = Integer.parseInt(imageWidth.getText());
            int height = Integer.parseInt(imageHeight.getText());

            imageView.setFitWidth(width);
            imageView.setFitWidth(height);
        });
    }

    public void decode(String fileName) throws Exception {
        File file = new File(fileName);

        DecoderBmp decoderBmp = new DecoderBmp();
        bmp = decoderBmp.loadBMP(file);
        System.out.println("Width: " + bmp.getWidth());
        System.out.println("Height: " + bmp.getHeight());

        printMatrix(bmp.getPixels());
        drawImage(bmp, imageView);
    }

    public void drawImage(BmpFile bmp, ImageView imageView) {
        BufferedImage drawImage = JpegEncoder.encode(bmp.getPixels(), bmp.getWidth(), bmp.getHeight());
        imageView.setFitWidth(bmp.getWidth());
        imageView.setFitHeight(bmp.getHeight());
        imageView.setImage(SwingFXUtils.toFXImage(drawImage, null));
    }

    public void printMatrix(int[][] matrix) {
        for(int[] line : matrix) {
            for(int i : line) {
                System.out.print(i);
            }
            System.out.println();
        }
    }
}
