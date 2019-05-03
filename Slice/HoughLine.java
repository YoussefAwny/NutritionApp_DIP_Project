import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
public class HoughLine {
	
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
        
        
        
        // Draw the lines
        for (int x = 0; x < lines.rows() -1; x++)
        {
            double rho = lines.get(x, 0)[0],
                    theta = lines.get(x, 0)[1];
            double a = Math.cos(theta), b = Math.sin(theta);
            double x0 = a*rho, y0 = b*rho;
            
            
            
            
            Point pt1 = new Point(Math.round(x0 + 1000*(-b)), Math.round(y0 + 1000*(a)));
            Point pt2 = new Point(Math.round(x0 - 1000*(-b)), Math.round(y0 - 1000*(a)));
            
            
            if(b>0.95)
            {
            
	            double rho1 = lines.get(x+1, 0)[0],
	                    theta1 = lines.get(x+1, 0)[1];
	            
	            double a1 = Math.cos(theta1), b1 = Math.sin(theta1);
	            double x1 = a1*rho1, y1 = b1*rho1;
	            
	            if (b1<0.95)
	            {
	            
		            while (b1<0.95 && x<lines.rows()-2)
		            {
		            	x++;
		            	 rho1 = lines.get(x+1, 0)[0];
			             theta1 = lines.get(x+1, 0)[1];
			            
			             a1 = Math.cos(theta1);
			             
			             b1 = Math.sin(theta1);
			             x1 = a1*rho1;
			             y1 = b1*rho1;
		            }
	            }
	            
	            int X1 = (int)Math.round(x0 + 1000*(-b));
	            double X2 = Math.round(x0 - 1000*(-b));
	            int X3 = (int) Math.round(x1 + 1000*(-b1));
	            double X4 = Math.round(x1 - 1000*(-b1));
	            
	            int Y1 = (int)Math.round(y0 + 1000*(a));
	            double Y2 = Math.round(y0 - 1000*(a));
	            
	            int Y3 = (int) Math.round(y1 + 1000*(a1));
	            double Y4 = Math.round(y1 - 1000*(a1));
	            
	            int cropCenterX = (int)(X4-X1)/2;
	            int cropCenterY = (int)(Y4-Y1)/2;
	            int  cropWidth = (int)(X2-X1);
	            int cropHeight = (int)(Y1-Y3);
	            
	            //System.out.println(x0);
	            //System.out.println(x1);
	            System.out.println(y0);
	            
	            System.out.println(y1);
	            
	            System.out.println("");
	            
	            //System.out.println(src.rows());
	            //System.out.println(src.cols());
	           if(y0>y1 && y1>0)
	           {
		            Rect rect = new Rect(0,(int)y1, src.cols(),(int)(y0-y1));
		            Mat cropped = new Mat(src, rect);
		            HighGui.imshow("res"+Integer.toString(x), cropped);
	           }
	            

		            Imgproc.line(cdst, pt1, pt2, new Scalar(0, 0, 255), 3, Imgproc.LINE_AA, 0);
            }
        }
        
        
        
  
        // Show results
        HighGui.imshow("Source", src);
        HighGui.imshow("Detected Lines (in red) - Standard Hough Line Transform", cdst);
        
        
        // Wait and Exit
        HighGui.waitKey();
        System.exit(0);
    }

}
