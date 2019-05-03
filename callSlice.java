import org.opencv.core.Core;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

import java.util.*; 

public class callSlice {

	public static void main(String[] args) {


		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//Image Location
		String default_file = "src/N1.png";
        
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
        
        
        //Loop through the Vector and Display all the images
        for(int i=0; i < result.size(); i++)
        {
            HighGui.imshow("Result"+Integer.toString(i), result.get(i));
         }
        
        
        //Wait to see images
        HighGui.waitKey();
        System.exit(0);
        

	}

}
