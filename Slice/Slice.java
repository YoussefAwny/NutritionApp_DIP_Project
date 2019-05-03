import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.util.*; 

public class Slice {

	public void run(String[] args) {


		// Declare the output variables
		Mat dst = new Mat(), cdst = new Mat(), cdstP;
        
        String default_file = "src/N1.png";
        
        String filename = ((args.length > 0) ? args[0] : default_file);
        
        // Load an image
        Mat src = Imgcodecs.imread(filename, Imgcodecs.IMREAD_GRAYSCALE);
        
        // Check if image is loaded fine
        if( src.empty() ) 
        {
            System.out.println("Error opening image!");
            System.out.println("Program Arguments: [image_name -- default "
                    + default_file +"] \n");
            System.exit(-1);
        }
        
        // Edge detection
        Imgproc.Canny(src, dst, 50, 200, 3, false);
        
        
        // Copy edges to the images that will display the results in BGR
        Imgproc.cvtColor(src, cdst, Imgproc.COLOR_GRAY2BGR);
        cdstP = cdst.clone();
        
        
        //Standard Hough Line Transform
        Mat lines = new Mat(); // will hold the results of the detection
        
        Imgproc.HoughLines(dst, lines, 1, Math.PI/180, 150); // runs the actual detection
        
        Vector <Integer>y = new Vector<Integer>();
        
        // Draw the lines
        for (int x = 0; x < lines.rows() -1; x++)
        {
            double rho = lines.get(x, 0)[0],
                    theta = lines.get(x, 0)[1];
            double a = Math.cos(theta), b = Math.sin(theta);
            double x0 = a*rho, y0 = b*rho;
            
            if (b>0.95)
            {
            	y.add((int)y0);
            }
            
            
            /*
		            Rect rect = new Rect(0,(int)y1, src.cols(),(int)(y0-y1));
		            Mat cropped = new Mat(src, rect);
		            HighGui.imshow("res"+Integer.toString(x), cropped);*/
	         
        }
        
        Collections.sort(y);
        
        
        for(int i=0; i < y.size(); i++){
            System.out.println(y.get(i));
         }
        
        for(int i=0; i < y.size()-1; i++)
        {
	        Rect rect = new Rect(0,y.get(i), src.cols(),y.get(i+1)-y.get(i));
	        Mat cropped = new Mat(src, rect);
	        HighGui.imshow("res"+Integer.toString(i), cropped);
	        
        }
        
        
  
        // Show results
        HighGui.imshow("Source", src);
       // HighGui.imshow("Detected Lines (in red) - Standard Hough Line Transform", cdst);
        
        
        // Wait and Exit
        HighGui.waitKey();
        System.exit(0);
    


	}

}
