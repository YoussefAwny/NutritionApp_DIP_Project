import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import java.util.*; 

public class Slice {

	public  Vector <Mat> run(Mat src) {

		// Declare the output variable
		Mat dst = new Mat();
     

        // Edge detection
        Imgproc.Canny(src, dst, 50, 200, 3, false);

        
        //Standard Hough Line Transform
        Mat lines = new Mat(); // will hold the results of the detection
        
        Imgproc.HoughLines(dst, lines, 1, Math.PI/180, 150); // runs the actual detection
        
        
        //Vector the hold all Y value of the lines
        Vector <Integer>y = new Vector<Integer>();
        
        //Get all Y values of all Horizontal line
        for (int x = 0; x < lines.rows() -1; x++)
        {
            double rho = lines.get(x, 0)[0];
            double theta = lines.get(x, 0)[1];
            double  b = Math.sin(theta);
            double  y0 = b*rho;
            
            
            //b>0.95 to get horizontal lines only
            if (b>0.95)
            {
            	y.add((int)y0);
            }
              
        }
        
        
        //Sort the vector that holds all Y values
        Collections.sort(y);
        
        //The Vector the holds the resulting images
        Vector <Mat> result = new Vector<Mat>();
        
        
        
        //Cropping original image according to Y values
        for(int i=0; i < y.size()-1; i++)
        {
        	int y1 = y.get(i);
        	int height = y.get(i+1)-y.get(i);
        	
        	
        	//This condition to skip small images that are nothing
        	if (height>src.rows()/40)
        	{
        	
        		/*The x and the width are always constant as it as the total width of the table
        		 * While the Y we start from current Y till the next one
        		 * */
        		
		        Rect rect = new Rect(0,y1, src.cols(),height);
		        Mat cropped = new Mat(src, rect);
		        result.add(cropped);
        	}
	        
        }
        
        return result;
        
  
    


	}

}
