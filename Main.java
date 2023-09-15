import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

public class Main {
    public static void main(String args[]) {
        if(args.length < 2) {  
            System.out.println("Please run with arguments: --<algorithm name> <path> or --help for more info.");
        }
        else if(args[0].equalsIgnoreCase("--help") || args[0].equalsIgnoreCase("-h") ) {
            System.out.println("Usage: --<algorithm name> <path>\nAvailable algorithms are:\n\t--prewitt\n\t--sobel\n\t--canny\n\t--roberts");
        }
        else {


            String algorithm = Util.removeAllChar(args[0],'-');	//Which algorithm to run, provided by arguments
            String path = args[1];	//Which path to load an image from
            
            File toLoad = new File(path);
            String directory = toLoad.getParent();
            
            BufferedImage inputImage = null; 	//Store image for edge detection processing
            BufferedImage outputImage = null;	//Create a space to store the output edge detected image, in the same path 
            
            //Load image from given path
            try {
                inputImage = ImageIO.read(toLoad);
            } catch (IOException e) {
                System.out.println("Image could not be loaded from: " + path);
                e.printStackTrace();
            }
            
            switch(algorithm) {
                case "prewitt":
                    System.out.println("Running prewitt operation on image at " + path);
                    //Prewitt logic here
                    break;
                case "sobel":
                    System.out.println("Running sobel operation on image at " + path);
                    //Sobel logic here
                    break;
                case "canny":
                    System.out.println("Running canny operation on image at " + path);
                    //Canny logic here
                    break;
                case "roberts":
                    System.out.println("Running roberts operation on image at " + path);
                    //Roberts logic here
                    break;
                default:
                    System.out.println("Invalid argument. Use --help for more info.");
            }
            
            //DateTime for file name
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime dt = LocalDateTime.now();
            
            File outputImageFile = new File(directory, algorithm+formatter.format(dt)+".png");
            try {
                ImageIO.write(outputImage, "png", outputImageFile);
                System.out.println("Output saved to " + outputImageFile.getPath());
            } catch (IOException e) {
                System.out.println("Error writing output file.");
                e.printStackTrace();
            }
        }
    }
}
