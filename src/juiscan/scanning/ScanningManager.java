package juiscan.scanning;

import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Vorontsov D.S.
 */
public class ScanningManager {
	
	private static String RESULT_PATH = "";
	private static String FILE_FORMAT = "jpeg";
	private static int DPI = 300;
	private static boolean GRAYSCALE = false;
	
	private ScanningAPIWrapper apiWrapper;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ScanningManager(ScanningAPIWrapper morenaWrapper) {
		
		apiWrapper = morenaWrapper;
		
		Class Manager = apiWrapper.getClassByCanonicalName("eu.gnome.morena.Manager");
		Class Device = apiWrapper.getClassByCanonicalName("eu.gnome.morena.Device");
		Class Scanner = apiWrapper.getClassByCanonicalName("eu.gnome.morena.Scanner");
		Class SynchronousHelper = apiWrapper.getClassByCanonicalName("SynchronousHelper");
		
		Method manger_getInstance;
		Method manager_listDevices;
		try {
			//manger_getInstance = ;
			Object managerInstance = Manager.getDeclaredMethod("getInstance", null).invoke(null, null);
			
			//manager_listDevices = ;
			List<Object> devices = (List<Object>) Manager.getDeclaredMethod("listDevices", null).invoke(managerInstance, null);
			
			
			for(Object device : devices) {
				if(Scanner.isInstance(device)) {
					
					int mode = 0;
					int resolution = 300;
					Rectangle area = new Rectangle(100, 100, 100, 100);
					
					Scanner.getDeclaredMethod("setMode", new Class[] {int.class})
						.invoke(device, new Object[] {mode});
					Scanner.getDeclaredMethod("setResolution", new Class[] {int.class})
						.invoke(device, new Object[] {resolution});
					Scanner.getDeclaredMethod("setFrame", new Class[] {int.class,int.class,int.class,int.class})
						.invoke(device, new Object[] {area.x,area.y,area.width,area.height});
					
					SynchronousHelper.getDeclaredMethod("scanImage", new Class[] {Device}).invoke(SynchronousHelper, new Object[] {device});
					
				}
			}
			
			
			//System.out.println(devices.size());
			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
}
