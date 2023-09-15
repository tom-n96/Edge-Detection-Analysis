
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


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
	public static BufferedImage toGrayscale(BufferedImage input) {
		BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = output.createGraphics();
        g.drawImage(input, 0, 0, null);
        g.dispose();
		
        return output;
	}
}
