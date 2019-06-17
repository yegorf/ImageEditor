package sample.canvas;

import javafx.scene.transform.MatrixType;

public class NewCanvas {
    private int[][] matrix = null;
    private long color = 16777215;

    public int[][] getMatrix() {
        return matrix;
    }

    public int getHeight() {
        return matrix.length;
    }

    public int getWidth() {
        return matrix[0].length;
    }

    public int[][] generateCanvas(int width, int height) {
        matrix = new int[height][width];
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                matrix[i][j] = (int)color;
            }
        }
        return matrix;
    }

    public int[][] changeColor(long color) {
        this.color = color;
        if(matrix != null) {
            for(int i=0; i<matrix.length; i++) {
                for(int j=0; j<matrix[0].length; j++) {
                    matrix[i][j] = (int)color;
                }
            }
        }
        return matrix;
    }
}
