import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.*;
//Franklin Gothic Heavy, Helvetica Black
public class TextRecognition {
	static char C[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V','W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	public static char bestmatch(Mat image, List<Mat> template) {
		//List<Double> distance = new ArrayList<>();
		double distance = 0;
		double min_distance=20000;
		int min_index = 0;
		for(Mat t: template) {
			distance = Core.norm(image, t);
			if(distance < min_distance)
			{
				min_distance = distance;
				min_index = template.indexOf(t);
			}
		}
		return C[min_index];
	}
	
	
	public static void dilate(Mat img, int amount) {
	    Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_ELLIPSE,
	            new Size(2 * amount + 1, 2 * amount + 1),
	            new Point(amount, amount));
	    Imgproc.dilate(img, img, kernel);
	}
	
	
	public static void savecomponent(Mat image, int i) {
		Imgcodecs imageCodecs = new Imgcodecs();
        imageCodecs.imwrite("E:\\\\Projects4thcse\\\\Java_Image\\\\NutritionTable\\Component" +String.valueOf(i)+".jpg", image);
        
	}
	
	
	public static void showcontours(Mat image, List<MatOfPoint> contours) {
		Random r = new Random();
		for (int i = 0; i < contours.size(); i++) {
			Imgproc.drawContours(image, contours, i, new Scalar(r.nextInt(255), r.nextInt(255), r.nextInt(255)), -1);
        }
	}
	
	
	public static Map<Double, Mat> getComponentMap(Mat image){
		//Imgcodecs imageCodecs = new Imgcodecs();
		Mat grey = new Mat();
        Mat blur = new Mat();
        Mat sharp = new Mat();
        Mat binary = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();
        
        Imgproc.cvtColor(image, grey, Imgproc.COLOR_RGB2GRAY, 0);
        Imgproc.GaussianBlur(grey, blur, new Size(0, 0), 3);
        Core.addWeighted(grey, 2, blur, -1, 0, sharp);
        Imgproc.threshold(sharp,binary,120,255,Imgproc.THRESH_BINARY);
        
        Imgproc.findContours(binary,contours,new Mat(),Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);
        
        Map<Double,Mat> componentmap =new HashMap<Double, Mat>();
        for( MatOfPoint mop: contours ){
        	double max_x = 0;
            double max_y = 0;
            double min_x = image.cols();
            double min_y = image.rows();
        	for( Point p: mop.toList() ){
            	if(p.x > max_x)
            		max_x = p.x;
            	if(p.y > max_y)
            		max_y = p.y;
            	if(p.x < min_x)
            		min_x = p.x;
            	if(p.y < min_y)
            		min_y = p.y;
            }
        	double width = max_x - min_x;
        	double height = max_y - min_y;
        	Rect rect = new Rect((int)min_x, (int)min_y, (int)width, (int)height);
        	if (rect != null && sharp.width() > rect.width && sharp.height() > rect.height)
        		{
        		Mat temp = new Mat(binary, rect);
        		//imageCodecs.imwrite("E:\\\\\\\\Projects4thcse\\\\\\\\Java_Image\\\\\\\\NutritionTable\\\\testout2.JPG",temp);
        		componentmap.put(min_x, temp);
        		}	
        }
        return componentmap;
	}
	
	
	
	public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        //Load Image
        Imgcodecs imageCodecs = new Imgcodecs();
        Mat matrix = imageCodecs.imread("E:\\\\Projects4thcse\\\\Java_Image\\\\NutritionTable\\Fat.JPG");
        System.out.println("Image Loaded");
        
        //get component images (Map structure with the key as the starting point of each image) from original image
        Map<Double, Mat> componentmap =getComponentMap(matrix);
        //change map to set to iterate
        Set componentset=componentmap.entrySet();
        
        // Load all templates
        List<Mat> template = new ArrayList<>();
        for(int i=0; i<52; i++)
        {
        	String template_file;
        	if(i<26)
        		template_file = "E:\\Projects4thcse\\Java_Image\\ocr-Template-matching--master\\Small\\" + C[i] + ".jpg";
        	else
        		template_file = "E:\\Projects4thcse\\Java_Image\\ocr-Template-matching--master\\Capital\\" + C[i] + ".jpg";
        	template.add(imageCodecs.imread(template_file));
        	System.out.println(i);
        	Imgproc.cvtColor(template.get(i), template.get(i), Imgproc.COLOR_RGB2GRAY, 0);
        }
        
        //Iterate over component images
        Iterator itr=componentset.iterator(); 
        int index = 0;
        String Box = "";
        while(itr.hasNext()){   	
        	//Converting to Map.Entry so that we can get key and value separately
        	Map.Entry<Double, Mat> entry=(Map.Entry)itr.next();
        	//get component image (Map value)
        	Mat e = entry.getValue();
        	//resize component image & changing to grey
        	Mat resizeimage = new Mat();
        	Size sz = new Size(50, 50);
        	Imgproc.resize( e, resizeimage, sz );
        	//dilate resized image
        	//dilate(resizeimage, 3);
        	//save component image
        	savecomponent(resizeimage, index);
        
        	Box+= bestmatch(resizeimage, template);
        	
        	//Imgproc.matchTemplate(resizeimage, T_template, op, Imgproc.TM_CCOEFF);
        	//System.out.println(entry.getKey()+" ");
            index++;
        }
        
        System.out.println(Box);
        String file2 = "E:\\\\\\\\Projects4thcse\\\\\\\\Java_Image\\\\\\\\NutritionTable\\\\testout.JPG"; 
        imageCodecs.imwrite(file2,matrix); 
        System.out.println("Image Saved ............");
        
	}
}
