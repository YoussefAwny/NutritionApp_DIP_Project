import org.opencv.core.*;




class HoughLineCall {
	
	
    public static void main(String[] args)
    {
    	// Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new HoughLine().run(args);
    }
}
