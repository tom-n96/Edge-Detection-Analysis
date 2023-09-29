import java.awt.image.BufferedImage;

public class Sobel {
	
	//Kernels
	private final byte kernelX [][] = {
		{1, 0, -1},
		{2, 0, -2},
		{1, 0, -1}
	};
	
	private final byte kernelY [][] = {
			{1, 2, 1},
			{0, 0, 0},
			{-1,-2,-1}
	};
	
	public BufferedImage sobelOperator(BufferedImage inputImage) {
		int [][] A = Util.bufferedImageToInt(Util.toGrayscale(inputImage)); //input image
		int [][] output = new int[A.length][A[0].length]; //output storage
		
		for(int y = 1; y < A.length-1;y++) {
			for(int x = 1; x < A[0].length-1; x++) {
				//Calculate Gx for each iteration, by multiplying input pixels' values with kernel
				int Gx = kernelX[0][0] * A[y-1][x-1] + kernelX[0][1] * A[y-1][x] + kernelX[0][2] * A[y-1][x+1] +
					 	 kernelX[1][0] * A[y]  [x-1] + kernelX[1][1] * A[y]  [x] + kernelX[1][2] * A[y]  [x+1] +
						 kernelX[2][0] * A[y+1][x-1] + kernelX[2][1] * A[y+1][x] + kernelX[2][2] * A[y+1][x+1];
				
				//Calculate Gy for each iteration, by multiplying input pixels' values with kernel
				int Gy = kernelY[0][0] * A[y-1][x-1] + kernelY[0][1] * A[y-1][x] + kernelY[0][2] * A[y-1][x+1] +
					 	 kernelY[1][0] * A[y]  [x-1] + kernelY[1][1] * A[y]  [x] + kernelY[1][2] * A[y]  [x+1] +
						 kernelY[2][0] * A[y+1][x-1] + kernelY[2][1] * A[y+1][x] + kernelY[2][2] * A[y+1][x+1];
				
				//Calculate Magnitude for the pixel
				int magnitude = (int) Math.sqrt(Math.pow(Gx, 2)+Math.pow(Gy, 2));
				output[y][x]=magnitude; //save magnitude for pixel in the output array
			}
		}
		
		return Util.intToBufferedImage(output); //Convert output to bufferedImage and return it
		
		
	}
	
	
	
	
}
