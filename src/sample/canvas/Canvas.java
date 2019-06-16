package sample.canvas;

import java.util.Arrays;

public class Canvas {
    private int size = 0;
    private int[][] matrix;
    private int color = 100;


    public int[][] resize(int s) {
        size = s;
        int width = matrix[0].length;
        int height = matrix.length;

        int[][] newMatrix = new int[height+size*2][width+size*2];

        //Копирование
        int k = 0;
        for(int i=size; i<height + size; i++) {
            int l = 0;
            for(int j=size; j<width + size; j++) {
                newMatrix[i][j] = matrix[k][l];
                l++;
            }
            k++;
        }

        //Горизонтальные
        for(int i=0; i<size; i++) {
            for(int j=0; j<width + 2*size; j++) {
                newMatrix[i][j] = color;
            }
        }
        for(int i=height+size; i<height+size*2; i++) {
            for(int j=0; j<width + 2*size; j++) {
                newMatrix[i][j] = color;
            }
        }

        //Вертикальные
        for(int i=0; i<height + size*2; i++) {
            for(int j=0; j<size; j++) {
                newMatrix[i][j] = color;
            }
        }
        for(int i=0; i<height + size*2; i++) {
            for(int j=width+size; j<width + 2*size; j++) {
                newMatrix[i][j] = color;
            }
        }

        return newMatrix;
    }

    public int[][] changeColor(int[][] matrix, int width, int height, int color) {
        System.out.println(size);
        this.color = color;

        //Горизонтальные
        for(int i=0; i<size; i++) {
            for(int j=0; j<width; j++) {
                matrix[i][j] = color;
            }
        }
        for(int i=height-size; i<height; i++) {
            for(int j=0; j<width; j++) {
                matrix[i][j] = color;
            }
        }

        //Вертикальные
        for(int i=0; i<height; i++) {
            for(int j=0; j<size; j++) {
                matrix[i][j] = color;
            }
        }
        for(int i=0; i<height; i++) {
            for(int j=width-size; j<width; j++) {
                matrix[i][j] = color;
            }
        }

        return matrix;
    }

    public void setMatrix(int[][] pixels) {
        //matrix = pixels;
        matrix = new int[pixels.length][pixels[0].length];
        for (int i=0; i<pixels.length; i++) {
            for(int j=0; j<pixels[0].length; j++) {
                matrix[i][j] = pixels[i][j];
            }
        }
    }
}
