import java.awt.image.BufferedImage;

public class Canny {
	
	//Default values for Gaussian filtering
	int k = 5; 						//size of gaussian filter
	float standardDeviation = 1.5f; //standard deviation of kernel values
	float lowThreshold = 0.1f; 		//Value for double threshold low value
	float highThreshold = 0.3f; 	//Value for double threshold high value

	public BufferedImage cannyOperator(BufferedImage inputImage) {
		int [][] A = Util.bufferedImageToInt(Util.toGrayscale(inputImage)); //input image
		int [][] output = new int[A.length][A[0].length]; //output storage
		
		long start = System.currentTimeMillis();
		//Step 1: Apply Gaussian filter to smooth image
		GaussianFilter gaus = new GaussianFilter();   		//Create an instance of the Gaussian filter
		A = gaus.filterGaussian(A, k, standardDeviation);	//Apply the filter to the input image
		
		//Step 2: Find intensity Gradients of the image
		Sobel s = new Sobel();		//Create instance of sobel operator
		float [][][] sobelOutput = s.sobelOperatorWithDirection(A);
		A = Util.float2DtoInt2D(sobelOutput[0]);     //Apply Sobel operator
		
		//Step 3: apply suppression and interpolation
		int [][] direction = roundDirection(sobelOutput[1]); //round the output of the sobel operator's direction calculation
		A = nonMaximumSuppression(A,direction);   //Apply non-maximum suppression
		
		//Step 4: Double threshold 
		int maxGradient = maxGradient(A);
		A = doubleThreshold(A,(int) (maxGradient*lowThreshold), (int) (maxGradient*highThreshold));

		
		output=A;
		System.out.println("TIME: "+(System.currentTimeMillis()-start));
		return Util.intToBufferedImage(output); //Convert output to bufferedImage and return it
		
		
	}
	
	public int [][] roundDirection(float [][] A) {	//find the direction of the pixels and round to the nearest
		int [][] output = new int[A.length][A[0].length]; //output storage
		
		for(int y = 1; y < A.length-1;y++) {
			for(int x = 1; x < A[0].length-1; x++) {
				if ((A[y][x] >= 0 && A[y][x] <= 22.5) || (A[y][x] > 157.5 && A[y][x] <= 202.5) || (A[y][x] > 337.5 && A[y][x] <= 360)){
				    output[y][x] = 0;
				} else if ((A[y][x] > 22.5 && A[y][x] <= 67.5) || (A[y][x] > 202.5 && A[y][x] <= 247.5)) {
				    output[y][x] = 45;
				} else if ((A[y][x] > 67.5 && A[y][x] <= 112.5) || (A[y][x] > 247.5 && A[y][x] <= 292.5)){
				    output[y][x] = 90;
				} else if ((A[y][x] > 112.5 && A[y][x] <= 157.5) || (A[y][x] > 292.5 && A[y][x] <= 337.5)){
				    output[y][x] = 135;
				}
			}
		}
		return output;

	}
	
	public int [][] doubleThreshold(int A [][], int lowThreshold, int highThreshold){
		int [][] output = A; //output storage
		for(int y = 1; y < A.length-1;y++) {
			for(int x = 1; x < A[0].length-1; x++) {
				if (output[y][x] < lowThreshold) {
		            output[y][x] = 0; // Not an edge
		        } else if (output[y][x] < highThreshold) {
		            output[y][x] = 75; // Weak edge
		        } else {
		            output[y][x] = 255; // Strong edge
		        }
			}
		}
		return output;
	}
	public int maxGradient(int [][] A) { //Find the maximum gradient of the image
	    int maxGradient = Integer.MIN_VALUE;  

	    for (int i = 0; i < A.length; i++) {
	        for (int j = 0; j < A[0].length; j++) {
	            if (A[i][j] > maxGradient) {
	                maxGradient = A[i][j];
	            }
	        }
	    }

	    return maxGradient;
	}
	public int[][] nonMaximumSuppression(int[][] A, int[][] direction) {  //
	    int[][] output = new int[A.length][A[0].length]; // output storage

	    for (int y = 1; y < A.length - 1; y++) {
	        for (int x = 1; x < A[0].length - 1; x++) {
	            int dir = direction[y][x];

	            int ip1 = 0; //pixel value for pixel in vicinity 
	            int ip2 = 0; //pixel value for pixel in other direction

	            
	            //based on which direction the pixel is in, find the adjacent pixels in the gradient, which will be interpolated
	            switch (dir) {
	            case 0:   //if the direction is 0
	                ip1 = A[y][x - 1];
	                ip2 = A[y][x + 1];
	                break;
	            case 45: //if the direction is 45
	                ip1 = (A[y - 1][x + 1] + A[y + 1][x - 1]) / 2; 
	                ip2 = (A[y + 1][x + 1] + A[y - 1][x - 1]) / 2;
	                break;
	            case 90: //if the direction is 90
	                ip1 = A[y - 1][x];
	                ip2 = A[y + 1][x];
	                break;
	            case 135: //if the direction is 135
	                ip1 = (A[y - 1][x - 1] + A[y + 1][x + 1]) / 2;
	                ip2 = (A[y + 1][x - 1] + A[y - 1][x + 1]) / 2;
	                break;
	        }

	            
	            if (A[y][x] >= ip1 && A[y][x] >= ip2) {
	                output[y][x] = A[y][x];
	            }
	        }
	    }

	    return output;
	}
	
}
