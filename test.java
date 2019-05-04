import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class test {

	public static void main(String[] args) {
		
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//Image Location
		String default_file = "src/N1.png";
        
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
        
        Vector<String> result = new Vector <String> ();
		getStringFromImage S = new getStringFromImage();
		result =S.run(src);
		
		for (int i =0 ; i < result.size() ; i++)
		{
			System.out.println(result.get(i));
		}
	}

}
