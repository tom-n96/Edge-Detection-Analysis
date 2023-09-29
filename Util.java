
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
	
	
	//Image manipulation methods
	public static BufferedImage toGrayscale(BufferedImage input) {   //converts a bufferedimage into its grayscale equivalent
		BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = output.createGraphics();
		g.drawImage(input, 0, 0, null);
		g.dispose();
		
		return output;
	}
	
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
}
