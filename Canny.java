import java.awt.image.BufferedImage;

public class Canny {

	public float gaussianKernelCoord(int i, int j, int k, float sigma) {
	    float output = (float) (1.0/(2 * Math.PI * Math.pow(sigma, 2)));
	    output = (float)  (output * Math.exp(- ((Math.pow((i-(k)/2.0),2) + Math.pow((j-(k)/2.0),2))/(2*Math.pow(sigma,2) ))));
	    return output;
	}

	public float [][] gaussianKernel(int k, float sigma) {
	    float [][] kernel = new float[k][k];
	    float sum = 0;
	    for(int i =0; i < k; i++) {
	        for(int j = 0; j < k;j++) {
	            kernel[i][j]=gaussianKernelCoord(i,j,k,sigma);
	            sum += kernel[i][j];
	        }
	    }
	    // Normalize kernel
	    for(int i =0; i < k; i++) {
	        for(int j = 0; j < k;j++) {
	            kernel[i][j] /= sum;
	        }
	    }
	    return kernel;
	}

	public BufferedImage filterGaussian(BufferedImage inputImage, int k, float sigma) {
	    int [][] A = Util.bufferedImageToInt(Util.toGrayscale(inputImage));
	    int [][] output = new int[A.length][A[0].length];
	    float [][] kernel = gaussianKernel(k,sigma);

	    int pad = k/2;

	    for(int y = pad; y < A.length-pad; y++) {
	        for(int x = pad; x < A[0].length-pad; x++) {
	            float Gx = 0;
	            for(int i = -pad; i <= pad; i++)
	                for(int j = -pad; j <= pad; j++)
	                    Gx += kernel[i+pad][j+pad] * A[y+i][x+j];
	            
	            output[y][x]=(int)Gx;
	        }
	    }
	    return Util.intToBufferedImage(output);
	}
}