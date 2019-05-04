import java.util.Vector;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class callBoxAndSlice {

	public static void main(String[] args)  {
		
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//Image Location
		String default_file = "src/test1.png";
        
        String filename = ((args.length > 0) ? args[0] : default_file);
        
        // Load an image
        Mat src = Imgcodecs.imread(filename);
        
        
        //Check that Image loaded correctly
        if( src.empty() ) 
        {
            System.out.println("Error opening image!");
            System.out.println("Program Arguments: [image_name -- default "
                    + default_file +"] \n");
            System.exit(-1);
        }
        
        //Result from box detection
        BoxDetection B =  new BoxDetection();
        Mat result1 =B.findRectangle(src);
        
        
        //Result from Slice after box detection
        Slice S = new Slice();
        Vector <Mat> result2 =S.run(result1);
        
        //Display the Images after cropping from slice
        for (int i = 0 ;i< result2.size() ; i++)
        {
        	HighGui.imshow("Result"+Integer.toString(i), result2.get(i));
        	
        }
        
        //HighGui.imshow("Result", result);
        
        HighGui.waitKey();
        System.exit(0);
	}

}
