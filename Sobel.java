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
				int Gx = 0;
				for(int i = 0; i<=2;i++)
					for(int j = 0; j <=2;j++)
						Gx+=kernelX[i][j] * A[y+(i-1)][x+(j-1)];
				
				//Calculate Gy for each iteration, by multiplying input pixels' values with kernel
				int Gy = 0;
				for(int i = 0; i<=2;i++)
					for(int j = 0; j <=2;j++)
						Gy+=kernelY[i][j] * A[y+(i-1)][x+(j-1)];
				
				
				//Calculate Magnitude for the pixel
				int magnitude = (int) Math.sqrt(Math.pow(Gx, 2)+Math.pow(Gy, 2));
				output[y][x]=magnitude; //save magnitude for pixel in the output array
			}
		}
		
		return Util.intToBufferedImage(output); //Convert output to bufferedImage and return it
		
		
	}
	
	
	
	
}
