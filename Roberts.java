import java.awt.image.BufferedImage;

public class Roberts {
    // kernels for x and y-axis's
    private final int[] dx =
            {
                    1, 0,
                    0, -1
            };

    private final int[] dy =
            {
                    0, -1,
                    1, 0
            };

    public BufferedImage robertsOperator(BufferedImage inputImage) {
        // convert the input image into two arrays, one for the input and output
        int[][] grayScaleArray = Util.bufferedImageToInt(Util.toGrayscale(inputImage));
		long start = System.currentTimeMillis();
        int width = grayScaleArray.length;
        int height = grayScaleArray[0].length;
        int[][] outputArray = new int[width][height];

        // loop through each pixel
        for (int x = 0; x < width - 1; x++) {
            for (int y = 0; y < height - 1; y++) {
                int magnitude = Util.getMagnitude(x, y, dx, dy, grayScaleArray, "Roberts");

                // apply magnitude
                outputArray[x][y] = magnitude;
            }
        }

        // return an image using the Util with the new pixel values
		System.out.println("TIME: "+(System.currentTimeMillis()-start));
        return Util.intToBufferedImage(outputArray);
    }

}
