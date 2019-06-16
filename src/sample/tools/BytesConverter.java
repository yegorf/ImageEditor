package sample.tools;

import java.awt.*;

public class BytesConverter {
    public static int[][] bytesToMatrix(int width, int height, byte[] bytes, int offset) {
        int rest = width%4;
        int[][] matrix = new int[height][width];

        int k = offset;
        for (int i = height-1; i > 0; i--) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = new Color((int)bytes[k+2] & 0xFF, (int)bytes[k+1] & 0xFF, (int)bytes[k] & 0xFF).getRGB();
                k += 3;
            }
            k+= rest;
        }
        return matrix;
    }

    public static byte[] matrixToBytes() {

        return new byte[10];
    }
}
