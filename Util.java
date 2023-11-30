
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.WritableRaster;


public class Util {
	public static String removeAllChar(String in, char toRemove) { //Creates a string with all instance of a given char removed
		String output="";
		for(int i = 0; i < in.length(); i++) {
			if(in.charAt(i) != toRemove)
				output+=in.charAt(i);
		}
		return output;
	}
	
	
	public static int [][] float2DtoInt2D(float[][] A){
		int [][] output = new int[A.length][A[0].length];
		for(int y = 1; y < A.length-1;y++) {
			for(int x = 1; x < A[0].length-1; x++) {
				output[y][x]=(int) A[y][x];
			}
		}
		return output;
	}
	
	//Image manipulation methods
	
	public static int [][] normalize(int [][] A){
		int [][] output = A;
		float max = 0;

		// Find the Max
		for (int y = 0; y < A.length; y++) {
		    for (int x = 0; x < A[0].length; x++) {
		        if(output[y][x]> max)
		            max = output[y][x];
		    }
		}

		// Normalize to max
		for (int y = 0; y < A.length; y++) {
		    for (int x = 0; x < A[0].length; x++) {
		        output[y][x] = (int) ((output[y][x] / max) * 255);
		    }
		}
		
		return output;
	}
	
	public static float [][] normalize(float [][] A){
		float [][] output = A;
		float max = 0;

		// Find the Max
		for (int y = 0; y < A.length; y++) {
		    for (int x = 0; x < A[0].length; x++) {
		        if(output[y][x]> max)
		            max = output[y][x];
		    }
		}

		// Normalize to max
		for (int y = 0; y < A.length; y++) {
		    for (int x = 0; x < A[0].length; x++) {
		        output[y][x] = (output[y][x] / max) * 255;
		    }
		}
		
		return output;
	}
	
	public static BufferedImage toGrayscale(BufferedImage source) {
	    BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
	    BufferedImage image = op.filter(source, null);
	    return image;
	}
	
	/*public static BufferedImage toGrayscale(BufferedImage input) {   //converts a bufferedimage into its grayscale equivalent
		BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = output.createGraphics();
		g.drawImage(input, 0, 0, null);
		g.dispose();
		
		return output;
	}*/
	
	public static int [][] bufferedImageToInt(BufferedImage input){    //takes GRAYSCALE bufferedimage and converts to a 2d array of ints
		int [][] output = new int[input.getHeight()][input.getWidth()]; //create space for all pixel values to be stored in int format
		
		for(int i = 0; i < output.length;i++) {
			for(int j = 0; j < output[i].length; j++) {
				output[i][j]=input.getRGB(j, i) & 0xFF; 
			}
		}
		
		return output;
	}
	
	public static BufferedImage intToBufferedImage(int [][] input){ //converts int array back to grayscale bufferedImage
		BufferedImage output = new BufferedImage(input[0].length,input.length,BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster r = output.getRaster();
		
		for(int i = 0; i < input.length;i++) {
			for(int j = 0; j < input[i].length; j++) {
				r.setSample(j, i, 0, input[i][j]);
			}
		}
		
		return output;
	}

	public static int getMagnitude(int x, int y, int[] dx, int[] dy, int[][] grayScaleArray, String type) {
		int Gx = 0, Gy = 0;
		int kernelSize = type.equals("Prewitt") ? 3 : 2;

		for (int i = 0; i < kernelSize; i++) {
			for (int j = 0; j < kernelSize; j++) {
				int newX = x + i - 1; // Adjusted for kernel center
				int newY = y + j - 1; // Adjusted for kernel center

				// Check bounds
				if (newX < 0 || newX >= grayScaleArray.length || newY < 0 || newY >= grayScaleArray[0].length) {
					continue;
				}

				int index = i * kernelSize + j;
				Gx += grayScaleArray[newX][newY] * dx[index];
				Gy += grayScaleArray[newX][newY] * dy[index];
			}
		}

		// combine the gradient magnitudes with the Euclidean norm
		return (int) Math.sqrt(Gx * Gx + Gy * Gy);
	}


}
