package sample.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import sample.bmp.BmpFile;
import sample.bmp.DecoderBmp;
import sample.canvas.Canvas;
import sample.canvas.NewCanvas;
import sample.jpeg.JpegEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Controller {
    @FXML
    private Pane pane;
    @FXML
    private ImageView imageOld;
    @FXML
    private ImageView imageNew;
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
    private TextField canvasHeight;
    @FXML
    private TextField canvasWidth;
    @FXML
    private TextField canvasColor;
    @FXML
    private TextField imageWidth;
    @FXML
    private TextField imageHeight;
    @FXML
    private Text hText;
    @FXML
    private Text wText;
    @FXML
    private ColorPicker colorPicker;

    private BmpFile bmp = new BmpFile();

    @FXML
    void initialize() {
        NewCanvas newCanvas = new NewCanvas();
        hText.setVisible(false);
        wText.setVisible(false);

        colorPicker.setOnAction(e -> {
            String hex = String.copyValueOf(colorPicker.getValue().toString().toCharArray(), 2, 6);
            int color = Integer.parseInt(hex, 16);
            int[][] matrix = newCanvas.changeColor(color);
            drawImage(matrix, imageNew);
        });

        chooseBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            StringBuilder fileName = new StringBuilder();

            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("BMP images (*.bmp)", "*.bmp"));
            File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
            if (file != null) {
                fileName.append(file.getAbsolutePath());
                try {
                    decode(fileName.toString());
                    hText.setText("Высота: " + bmp.getHeight());
                    wText.setText("Ширина: " + bmp.getWidth());
                    hText.setVisible(true);
                    wText.setVisible(true);

                    int[][] canvasMatrix = newCanvas.generateCanvas(bmp.getWidth(), bmp.getHeight());
                    drawImage(canvasMatrix, imageNew);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        canvasSizeBtn.setOnAction(e -> {
            int height = Integer.parseInt(canvasHeight.getText());
            int width = Integer.parseInt(canvasWidth.getText());
            int[][] canvasMatrix = newCanvas.generateCanvas(width, height);
            drawImage(canvasMatrix, imageNew);
        });

        canvasColorBtn.setOnAction(e -> {
            int color = Integer.parseInt(canvasColor.getText());
            int[][] matrix = newCanvas.changeColor(color);
            drawImage(matrix, imageNew);
        });

        saveBtn.setOnAction(e -> {
            try {
                JpegEncoder.saveJpeg(newCanvas.getMatrix(), newCanvas.getWidth(), newCanvas.getHeight());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        imgSizeBtn.setOnAction(e -> {
            int width = Integer.parseInt(imageWidth.getText());
            int height = Integer.parseInt(imageHeight.getText());

            imageOld.setFitWidth(width);
            imageOld.setFitWidth(height);
        });
    }

    public void decode(String fileName) throws Exception {
        File file = new File(fileName);

        DecoderBmp decoderBmp = new DecoderBmp();
        bmp = decoderBmp.loadBMP(file);
        System.out.println("Width: " + bmp.getWidth());
        System.out.println("Height: " + bmp.getHeight());

        printMatrix(bmp.getPixels());
        drawImage(bmp.getPixels(), imageOld);
    }

    public void drawImage(int[][] matrix, ImageView imageView) {
        BufferedImage drawImage = JpegEncoder.encode(matrix, matrix[0].length, matrix.length);
        imageView.setFitWidth(matrix[0].length);
        imageView.setFitHeight(matrix.length);
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
