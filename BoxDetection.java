
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class BoxDetection {
	
	  public Mat findRectangle(Mat src) {
		 Mat blurred = src.clone();
		 Imgproc.medianBlur(src, blurred, 5);

		 Mat gray0 = new Mat(blurred.size(), CvType.CV_8U), gray = new Mat();

		 List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

		 List<Mat> blurredChannel = new ArrayList<Mat>();
		 blurredChannel.add(blurred);
		 List<Mat> gray0Channel = new ArrayList<Mat>();
		 gray0Channel.add(gray0);

		 MatOfPoint2f approxCurve;

		 double maxArea = 0;
		 int maxId = -1;

		 for (int c = 0; c < 3; c++) {
		 int ch[] = { c, 0 };
		 Core.mixChannels(blurredChannel, gray0Channel, new MatOfInt(ch));

		 int thresholdLevel = 1;
		 for (int t = 0; t < thresholdLevel; t++) {
		 if (t == 0) {
		 Imgproc.Canny(gray0, gray, 0, 50, 5);
		 Imgproc.dilate(gray, gray, new Mat(), new Point(-1, -1), 5); // 1
		 // ?
		 } else {
		 Imgproc.adaptiveThreshold(gray0, gray, thresholdLevel,
		 Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
		 Imgproc.THRESH_BINARY,
		 (src.width() + src.height()) / 200, t);
		 }

		 Imgproc.findContours(gray, contours, new Mat(),
		 Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

		 for (MatOfPoint contour : contours) {
		 MatOfPoint2f temp = new MatOfPoint2f(contour.toArray());

		 double area = Imgproc.contourArea(contour);
		 approxCurve = new MatOfPoint2f();
		 Imgproc.approxPolyDP(temp, approxCurve,
		 Imgproc.arcLength(temp, true) * 0.02, true);

		 if (approxCurve.total() == 4 && area >= maxArea) {
		 double maxCosine = 0;

		 List<Point> curves = approxCurve.toList();
		 for (int j = 2; j < 5; j++) {

		 double cosine = Math.abs(angle(curves.get(j % 4),
		 curves.get(j - 2), curves.get(j - 1)));
		 maxCosine = Math.max(maxCosine, cosine);
		 }

		 if (maxCosine < 0.3) {
		 maxArea = area;
		 maxId = contours.indexOf(contour);
		 }
		 }
		 }
		 }
		 }

		 if (maxId >= 0) {
		//  Imgproc.drawContours(src, contours, maxId, new Scalar(255, 0, 0,
		 // .8), 8);
		  
		  MatOfPoint2f temp = new MatOfPoint2f(contours.get(maxId).toArray());
		  approxCurve = new MatOfPoint2f();
		  Imgproc.approxPolyDP(temp, approxCurve,
		  Imgproc.arcLength(temp, true) * 0.02, true);
		  List<Point> curvesFinal = approxCurve.toList();
		  
		  /*
			  * Kiro Code
			  * */
			 
			 //initialize Vector for all x value and all y values
			 Vector <Integer> x = new Vector <Integer> ();
			 Vector <Integer> y = new Vector <Integer> ();
		  
			//add the x and y values to the curve
			 for (int i = 0 ; i<curvesFinal.size() ; i++)
			 {
				x.add((int)curvesFinal.get(i).x);
				y.add((int)curvesFinal.get(i).y); 
			 }
			 
			 
			 //Sort x and y value
			 Collections.sort(x);

			 Collections.sort(y);
			 
			 Rect rect = new Rect (x.get(0), y.get(0) , x.get(2)-x.get(0), y.get(2)-y.get(0));
			 
			 Mat cropped = new Mat(src, rect);
			 
			 return cropped;
			
		
			 /*
			  * Kiro Code
			  * */
		  
		  

		 }
		return src;
		 }

	
	private static double angle(Point p1, Point p2, Point p0) {
		 double dx1 = p1.x - p0.x;
		 double dy1 = p1.y - p0.y;
		 double dx2 = p2.x - p0.x;
		 double dy2 = p2.y - p0.y;
		 return (dx1 * dx2 + dy1 * dy2)
		 / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2)
		 + 1e-10);
		 }





}