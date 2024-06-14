package mainPackage;

public class NativeClass {

	static {
        System.loadLibrary("poop");
    }
	
	public native void izvrsiUpit(String upit);
	
}
