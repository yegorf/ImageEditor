package sample.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import sample.bmp.BmpFile;
import sample.bmp.DecoderBmp;
import sample.canvas.NewCanvas;
import sample.jpeg.JpegEncoder;
import sample.shapes.Rect;
import sample.tools.ImageCutter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Controller {
    @FXML
    private Pane pane;
    @FXML
    private BorderPane oldPane;
    @FXML
    private BorderPane newPane;
    @FXML
    private ImageView imageOld;
    @FXML
    private ImageView imageNew;
    @FXML
    private Button canvasSizeBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Button canvasColorBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button exitBtn;
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
    private Text hCanvasText;
    @FXML
    private Text wCanvasText;
    @FXML
    private ColorPicker colorPicker;

    private BmpFile bmp = new BmpFile();

    private int preX = 0;
    private int preY = 0;
    private boolean moving;

    @FXML
    void initialize() {
        NewCanvas newCanvas = new NewCanvas();
        hText.setVisible(false);
        wText.setVisible(false);
        hCanvasText.setVisible(false);
        wCanvasText.setVisible(false);

        exitBtn.setOnAction(e -> System.exit(0));

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
                    hCanvasText.setVisible(true);
                    wCanvasText.setVisible(true);
                    hCanvasText.setText("Высота: " + newCanvas.getHeight());
                    wCanvasText.setText("Ширина: " + newCanvas.getWidth());

                    oldPane.setCenter(imageOld);
                    oldPane.setMaxWidth(imageOld.getFitWidth());
                    oldPane.setMaxHeight(imageOld.getFitHeight());
                    newPane.setCenter(imageNew);
                    newPane.setMaxWidth(imageNew.getFitWidth());
                    newPane.setMaxHeight(imageNew.getFitHeight());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        canvasSizeBtn.setOnAction(e -> {
            int height = Integer.parseInt(canvasHeight.getText());
            int width = Integer.parseInt(canvasWidth.getText());
            int[][] canvasMatrix = newCanvas.generateCanvas(width, height);
            newPane.setMaxHeight(height);
            newPane.setMaxWidth(width);
            drawImage(canvasMatrix, imageNew);
            hCanvasText.setVisible(true);
            wCanvasText.setVisible(true);
            hCanvasText.setText("Высота: " + newCanvas.getHeight());
            wCanvasText.setText("Ширина: " + newCanvas.getWidth());
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
            imageNew.setFitWidth(width);
            imageNew.setFitHeight(height);
            newPane.setMaxWidth(width);
            newPane.setMaxHeight(height);
        });

        //Выделение
        Rect rect = new Rect();

        oldPane.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            System.out.println("pressed");
            rect.setStartX(e.getX());
            rect.setStartY(e.getY());
        });

        oldPane.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            System.out.println("released");

            //По этому ректанглу вырезать из матрицы
            System.out.println("rect " + rect.getStartX() + " " + rect.getStartY() + " " + rect.getHeight() + " " + rect.getWidth());
            rect.setEndX(e.getX());
            rect.setEndY(e.getY());

            int[][] area = ImageCutter.cutOut(bmp.getPixels(), rect);
            int[][] matrix = newCanvas.addImage(area);
            drawImage(matrix, imageNew);
            //
            //oldPane.getChildren().removeAll();
        });

        imageOld.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            double x, y, w, h;
            if (e.getX() > (int) rect.getStartX()) {
                x = rect.getStartX();
                w = e.getX() - rect.getStartX();
            } else {
                x = e.getX();
                w = rect.getStartX() - e.getX();
            }

            if (e.getY() > (int) rect.getStartY()) {
                y = rect.getStartY();
                h = e.getY() - rect.getStartY();
            } else {
                y = e.getY();
                h = rect.getStartY() - e.getY();
            }

            Rectangle rectangle = new Rectangle(x,y,w,h);
            rect.setHeight(h);
            rect.setWidth(w);

            rectangle.setStroke(Color.rgb(0, 0, 0));
            rectangle.setStrokeWidth(2);
            rectangle.setFill(Color.rgb(255, 255, 255, 0.001));
            oldPane.getChildren().removeAll();
            drawImage(bmp.getPixels(), imageOld);
            oldPane.getChildren().add(rectangle);
        });

        //Движение области
        newPane.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            System.out.println("NP");
            if(newCanvas.checkPosition((int)e.getX(), (int)e.getY())) {
                preX = (int)e.getX();
                preY = (int)e.getY();
                moving = true;
            }
        });

        newPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            System.out.println("ND");
            if(moving) {
                int[][] matrix = newCanvas.moveArea((int)(e.getX() - preX), (int)(e.getY() - preY));
                drawImage(matrix, imageNew);
            }
            preX = (int)e.getX();
            preY = (int)e.getY();
        });

        newPane.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            System.out.println("REL");
            moving = false;
        });

        //Очищаем полотно
        clearBtn.setOnAction(e -> {
            drawImage(newCanvas.clearCanvas(), imageNew);
        });
    }

    public void decode(String fileName) throws Exception {
        File file = new File(fileName);
        DecoderBmp decoderBmp = new DecoderBmp();
        bmp = decoderBmp.loadBMP(file);
        drawImage(bmp.getPixels(), imageOld);
    }

    public void drawImage(int[][] matrix, ImageView imageView) {
        BufferedImage drawImage = JpegEncoder.encode(matrix, matrix[0].length, matrix.length);
        imageView.setFitWidth(matrix[0].length);
        imageView.setFitHeight(matrix.length);
        imageView.setImage(SwingFXUtils.toFXImage(drawImage, null));
    }

    public void addImage(int[][] matrix, ImageView imageView) {
        BufferedImage drawImage = JpegEncoder.encode(matrix, matrix[0].length, matrix.length);
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
