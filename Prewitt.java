import java.awt.image.BufferedImage;

public class Prewitt {
    // Kernels for x and y-axis's
    private final int[] dx =
            {
                    -1, 0, 1,
                    -1, 0, 1,
                    -1, 0, 1
            };

    private final int[] dy =
            {
                    -1, -1, -1,
                    0, 0, 0,
                    1, 1, 1
            };

    public BufferedImage prewittOperator(BufferedImage inputImage) {
        // convert the input image into two arrays, one for the input and output
        int [][] grayScaleArray = Util.bufferedImageToInt(Util.toGrayscale(inputImage));
        int width = grayScaleArray.length; // get the width
        int height = grayScaleArray[0].length; // get the height
        int [][] outputArray = new int[width][height]; // init the output array

        // loop through each pixel
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height -1; y++) {
                int magnitude = getMagnitude(x, y, grayScaleArray);

                /*
                // apply 5-tiered threshold
                if (magnitude >= 200) {
                    outputArray[x][y] = 255;  // very strong edge
                } else if (magnitude >= 150) {
                    outputArray[x][y] = 200;  // strong edge
                } else if (magnitude >= 100) {
                    outputArray[x][y] = 150;  // medium edge
                } else if (magnitude >= 50) {
                    outputArray[x][y] = 100;  // weak edge
                } else {
                    outputArray[x][y] = 0;    // very weak or no edge
                }
                 */
                // apply magnitude
                outputArray[x][y] = magnitude;
            }
        }

        // return an image using the Util with the new pixel values
        return Util.intToBufferedImage(outputArray);
    }

    private int getMagnitude(int x, int y, int[][] grayScaleArray) {
        int Gx = 0, Gy = 0;

        // apply the mask to the current pixel
        // and calculate the gradient magnitudes
        for (int i = 0; i < 9; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            Gx += grayScaleArray[newX][newY] * dx[i];
            Gy += grayScaleArray[newX][newY] * dy[i];
        }

        // combine the gradient magnitudes with the Euclidean norm
        return (int) Math.sqrt(Gx * Gx + Gy * Gy);
    }
}