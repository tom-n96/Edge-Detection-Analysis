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
		long start = System.currentTimeMillis();
        int width = grayScaleArray.length; // get the width
        int height = grayScaleArray[0].length; // get the height
        int [][] outputArray = new int[width][height]; // init the output array

        // loop through each pixel
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height -1; y++) {
                int magnitude = Util.getMagnitude(x, y, dx, dy, grayScaleArray, "Prewitt");
                // apply magnitude
                outputArray[x][y] = magnitude;
            }
        }
		System.out.println("TIME: "+(System.currentTimeMillis()-start));
        // return an image using the Util with the new pixel values
        return Util.intToBufferedImage(outputArray);
    }

}