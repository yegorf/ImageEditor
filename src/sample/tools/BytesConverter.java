package sample.tools;

import java.awt.*;

public class BytesConverter {
    public static int[][] bytesToIntMatrix(int width, int height, byte[] image) {
        int[][] matrix = new int[width][height];
        int count = 0;
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                Color color = new Color(image[count]);
                matrix[i][j] =
                        (new Color(image[count]).getRed()) +
                        (new Color(image[count+1]).getGreen()) +
                        (new Color(image[count+2]).getBlue());
                count+=3;
            }
        }
        return matrix;
    }
}
