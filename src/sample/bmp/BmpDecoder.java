package sample.bmp;

import sample.tools.BytesConverter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BmpDecoder {
    private BmpFile bmp;

    public BmpFile decode(String url) throws IOException {
        InputStream inputStream = new BufferedInputStream(
                new FileInputStream(url));

        bmp = new BmpFile();

        byte[] header = new byte[14];
        inputStream.read(header,0,14);
        getHeaderInfo(header);

        byte[] info = new byte[40];
        inputStream.read(info,0,40);
        getImageInfo(info);

        byte[] colorTable = new byte[1024];
        inputStream.read(colorTable, 0, 1024);
        bmp.setColorTable(colorTable);
        System.out.println(Arrays.toString(colorTable));

        byte[] image = new byte[bmp.getSize()];
        inputStream.read(image, 0, bmp.getSize());
        bmp.setImage(image);
        System.out.println(Arrays.toString(image));
        bmp.setPixels(BytesConverter.bytesToIntMatrix(bmp.getWidth(), bmp.getHeight(), bmp.getImage()));

        return bmp;
    }

    private void getHeaderInfo(byte[] header) {
        System.out.println(Arrays.toString(header));
        checkType(header);
        System.out.println("Размер файла: " + byteArrToLong(Arrays.copyOfRange(header, 2, 6)));
        System.out.println("Смещение пикчи: " + byteArrToLong(Arrays.copyOfRange(header, 10, 14)));
    }

    private void getImageInfo(byte[] info) {
        System.out.println(Arrays.toString(info));
        System.out.println("Размер заголовка: " + info[0]); //4byte

        long width = byteArrToLong(Arrays.copyOfRange(info, 4, 8));
        long height = byteArrToLong(Arrays.copyOfRange(info, 8, 12));
        long size = byteArrToLong(Arrays.copyOfRange(info, 20, 24));
        long bitCount = byteArrToLong(Arrays.copyOfRange(info, 14, 16));

        bmp.setWidth((int) width);
        bmp.setHeight((int) height);
        bmp.setSize((int) size);
        bmp.setBitCount((int) bitCount);

        System.out.println("Ширина " + width);
        System.out.println("Высота " + height);
        System.out.println("Размер " + size);
        System.out.println("Бит на точку: " + bitCount);

    }

    private long byteArrToLong(byte[] bytes) {
        long value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value += ((long) bytes[i] & 0xffL) << (8 * i);
        }
        return value;
    }

    private void checkType(byte header[]) {
        if(header[0] != 66 | header[1] !=77) {
            System.out.println("Это не bmp!");
        }
    }
}
