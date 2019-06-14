package sample.bmp;

import java.io.*;
import java.util.Arrays;

public class BmpDecoder {


    public void decode(String url) throws IOException {
        InputStream inputStream = new BufferedInputStream(
                new FileInputStream(url));

        byte[] header = new byte[14];
        inputStream.read(header,0,14);
        getHeaderInfo(header);

        byte[] info = new byte[40];
        inputStream.read(info,0,40);
        getImageInfo(info);
    }

    private void getHeaderInfo(byte[] header) {
        System.out.println(Arrays.toString(header));
        checkType(header);
    }

    private void getImageInfo(byte[] info) {
        System.out.println(Arrays.toString(info));
        System.out.println("Размер заголовка: " + info[0]);
    }

    private void checkType(byte header[]) {
        if(header[0] != 66 | header[1] !=77) {
            System.out.println("Это не bmp!");
        }
    }
}
