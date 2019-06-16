package sample.bmp;

import sample.tools.BytesConverter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;


public class DecoderBmp {
    private BmpFile bmp;

    public DecoderBmp() {
        bmp = new BmpFile();
    }

    public BmpFile loadBMP (File file) throws Exception {
        long total = file.length();
        if(total > Integer.MAX_VALUE) {
            throw new Exception("The BMP is so big to be loaded");
        }
        byte[] bytes = new byte[(int)total];
        bmp.setImage(bytes);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        in.read(bytes);
        int totalBytes = getDecimalValueLSB(bytes, 2, 5);
        if(!checkBMP(bytes) || totalBytes != (int)total) {
            throw new Exception("This is not an original BMP file");
        }

        int offset = getDecimalValueLSB(bytes, 10, 13);
        bmp.setOffset(offset);

        int width = getDecimalValueLSB(bytes, 18, 21);
        bmp.setWidth(width);
        int height = getDecimalValueLSB(bytes, 22, 25);
        bmp.setHeight(height);

        int depth = getDecimalValueLSB(bytes, 28, 29);
        if(depth != 24) {
            throw new Exception("This decoder accept 24-bits BMP image only");
        }

        int size = getDecimalValueLSB(bytes, 34, 37);
        bmp.setSize(size);

        int[][] matrix = BytesConverter.bytesToMatrix(width, height, bytes, offset);
        bmp.setPixels(matrix);

        return bmp;
    }

    public boolean checkBMP (byte[] bytes) {
        if(bytes[0] == Integer.parseInt("42", 16)  && bytes[1] == Integer.parseInt("4D", 16)) {
            return true;
        } else {
            return false;
        }
    }

    public int getDecimalValueLSB (byte[] bytes, int begin, int end) {
        String hx="";
        for (int i = end; i > begin-1; i--) {
            String n = Integer.toHexString((int)bytes[i] & 0xFF);
            if(n.length() == 1)
                hx += "0";
            hx += n;
        }
        return Integer.parseInt(hx, 16);
    }
}
