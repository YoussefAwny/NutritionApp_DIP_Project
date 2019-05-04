import java.io.File;
import java.util.Vector;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;


public class getStringFromImage {
	
	
	public Vector <String> run (Mat src) {
		
		Vector <String> out = new Vector <String> ();
	
	 	BoxDetection B =  new BoxDetection();
        Mat result1 =B.findRectangle(src);
        
        
        //Result from Slice after box detection
        Slice S = new Slice();
        Vector <Mat> result2 =S.run(result1);
        
        
        for(int i=0; i < result2.size(); i++)
        {
			Imgcodecs.imwrite("out"+Integer.toString(i)+".png",result2.get(i));
        }
        
        Tesseract Tess = new Tesseract(); //instance of tesseract
        
        try {
		 	Tess.setDatapath("F:\\Tess for java\\Tess4J\\tessdata");
		 	
		 	
		 	//Read all images and write string in each image 
		 	for (int i=0; i < result2.size(); i++)
		 	{
		 		
				String text = Tess.doOCR(new File("out"+Integer.toString(i)+".png"));
				out.add(text);
		 	}
		 } catch (TesseractException e) {		
			e.printStackTrace();
		}
	
	return out;
	
	

}

}
