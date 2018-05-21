package juiscan.scanning;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Vorontsov D.S.
 */
public class ScanningManager {
	
	private static String RESULT_PATH = "";
	private static String FILE_FORMAT = "jpeg";
	private static int DPI = 300;
	private static boolean GRAYSCALE = false;
	
	private MorenaWrapper mw;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ScanningManager(MorenaWrapper morenaWrapper) {
		
		mw = morenaWrapper;
		
		Class Manager = mw.getClassByCanonicalName("eu.gnome.morena.Manager");
		
		Method method;
		try {
			method = Manager.getDeclaredMethod("getInstance", null);
			method.invoke(null, null);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		/*Manager manager = Manager.getInstance();
		List devices = manager.listDevices();*/
		
	}
	
	
	
}
