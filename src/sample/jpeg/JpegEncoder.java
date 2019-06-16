package sample.jpeg;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JpegEncoder {
    public static BufferedImage encode(int[][] matrix, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                image.setRGB(j, i, matrix[i][j]);
            }
        }
        return  image;
    }

    public static void saveJpeg(int[][] matrix, int width, int height) throws IOException {
        BufferedImage image = JpegEncoder.encode(matrix, width, height);
        File outputfile = new File("image.jpg");
        ImageIO.write(image, "jpg", outputfile);
    }
}
