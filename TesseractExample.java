
import net.sourceforge.tess4j.*;
import java.util.Vector;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import java.io.File;
		
		







public class TesseractExample {
	
	
	
	

	public static void main(String[] args) {

		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//Image Location
		String default_file = "src/N2.jpg";
        
		
        String filename = ((args.length > 0) ? args[0] : default_file);
        
        // Load an image
        Mat src = Imgcodecs.imread(filename, Imgcodecs.IMREAD_GRAYSCALE);
        
        
        //Check that Image loaded correctly
        if( src.empty() ) 
        {
            System.out.println("Error opening image!");
            System.out.println("Program Arguments: [image_name -- default "
                    + default_file +"] \n");
            System.exit(-1);
        }
        
        //Instance of class slice
        Slice S= new Slice();
        
        //The vector the Holds the return value
        Vector <Mat> result = new Vector<Mat>();
        
        //Run Slice
        result = S.run(src);
		
        //Save image resulting from class slice
        for(int i=0; i < result.size(); i++)
        {
			Imgcodecs.imwrite("out"+Integer.toString(i)+".png",result.get(i));
        }
		
		
		Tesseract Tess = new Tesseract(); //instance of tesseract
		 
		 try {
			 	Tess.setDatapath("F:\\Tess for java\\Tess4J\\tessdata");
			 	
			 	
			 	//Read all images and write string in each image 
			 	for (int i=0; i < result.size(); i++)
			 	{
			 		
					String text = Tess.doOCR(new File("out"+Integer.toString(i)+".png"));
					System.out.print(text);
			 	}
			 } catch (TesseractException e) {		
				e.printStackTrace();
			}


	}

}
