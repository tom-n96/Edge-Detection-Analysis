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
	
	public BufferedImage sobelOperator(BufferedImage inputImage) { //overload for bufferedimage input and output
		int [][] A = Util.bufferedImageToInt(Util.toGrayscale(inputImage)); //input image
		return Util.intToBufferedImage(sobelOperator(A)); //Convert output to bufferedImage and return it
		
	}
	
	public int [][] sobelOperator(int [][] A) {  			//overload for 2d int array input and output
		float sobel [][][] = sobelOperatorWithDirection(A);
		return Util.float2DtoInt2D(sobel[0]); //return output
	}
	
	public float [][][] sobelOperatorWithDirection(int [][] A) { //returns an array element [0] magnitude, element [1] direction
		float [][][] output = new float[2][A.length][A[0].length]; //output storage
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
				float magnitude = (float) Math.sqrt(Math.pow(Gx, 2)+Math.pow(Gy, 2));
				float direction = (float) Math.toDegrees(Math.atan2(Gy, Gx));
				if(direction<0)
					direction+=360;

				output[0][y][x]=magnitude; //save magnitude for pixel in the output array
				output[1][y][x]=direction; //save direction for pixel in the output array
			}
		}
		output[0]=Util.normalize(output[0]); //Normalize the magnitudes
		return output; //return output
	}
	
	
}
