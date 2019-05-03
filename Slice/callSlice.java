import org.opencv.core.Core;

public class callSlice {

	public static void main(String[] args) {


		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new Slice().run(args);

	}

}
