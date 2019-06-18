package sample.tools;

import sample.shapes.Rect;

public class ImageCutter {

    public static int[][] cutOut(int[][] matrix, Rect rect) {
        int[][] area = new int[(int)rect.getHeight()][(int)rect.getWidth()];
        int x = 0;
        int y = 0;

        if(rect.getStartX() < rect.getEndX() && rect.getStartY() < rect.getEndY()) {
            System.out.println("NORM");
            x = (int)rect.getStartX();
            y = (int)rect.getStartY();
        } else if(rect.getStartX() < rect.getEndX() && rect.getStartY() > rect.getEndY()) {
            x = (int)rect.getStartX();
            y = (int)rect.getEndY();
        } else if(rect.getStartX() > rect.getEndX() && rect.getStartY() > rect.getEndY()) {
            x = (int)rect.getEndX();
            y = (int)rect.getEndY();
        } else if(rect.getStartX() > rect.getEndX() && rect.getStartY() < rect.getEndY()) {
            x = (int)rect.getEndX();
            y = (int)rect.getStartY();
        }


        int k=0;
        for(int i = y; i<(y + rect.getHeight()); i++) {
            int l=0;
            for(int j = x; j<(x + rect.getWidth()); j++) {
                area[k][l] = matrix[i][j];
                l++;
            }
            k++;
        }
        return area;
    }
}
