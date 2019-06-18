package sample.canvas;

import javafx.scene.transform.MatrixType;

public class NewCanvas {
    private int[][] matrix = null;
    private int[][] area = null;
    private long color = 16777215;

    private int areaX = 0;
    private int areaY = 0;

    public int[][] getMatrix() {
        return matrix;
    }

    public int getHeight() {
        return matrix.length;
    }

    public int getWidth() {
        return matrix[0].length;
    }

    public int[][] clearCanvas() {
        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                matrix[i][j] = (int)color;
            }
        }
        return matrix;
    }

    public int[][] generateCanvas(int width, int height) {
        matrix = new int[height][width];
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                matrix[i][j] = (int)color;
            }
        }
        if(area!=null) {
            matrix = addImage(area);
            moveArea(0,0);
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
        if(area!=null) {
            matrix = addImage(area);
            moveArea(0,0);
        }
        return matrix;
    }

    public int[][] addImage(int[][] area) {
        this.area = area;
        for(int i=areaY; i<area.length; i++) {
            for(int j=areaX; j<area[0].length; j++) {
                matrix[i][j] = area[i][j];
            }
        }
        return matrix;
    }

    public boolean checkPosition(int x, int y) {
        if(y < area.length + areaY && y > areaY && x < area[0].length + areaX && x > areaX) {
            return true;
        } else {
            return false;
        }
    }

    public int[][] moveArea(int tempX, int tempY) {
        if(areaX+tempX < 0
                || areaY+tempY < 0
                || areaX+tempX+area[0].length > matrix[0].length
                || areaY+tempY+area.length > matrix.length) {
            return matrix;
        } else {
            this.areaX += tempX;
            this.areaY += tempY;
            matrix = clearCanvas();

            int k = 0;
            for (int i = areaY; i < areaY + area.length; i++) {
                int l = 0;
                for (int j = areaX; j < areaX + area[0].length; j++) {
                    matrix[i][j] = area[k][l];
                    l++;
                }
                k++;
            }
            return matrix;
        }
    }
}
