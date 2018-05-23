package juiscan.scanning;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import juiscan.Application;
import juiscan.Common;

/**
 * @author Vorontsov D.S.
 */
public class ScanningAPIWrapper {
	
	private final String libFileName = "morena7.jar";
	private final String nativeLibFileName = "morena7_win.jar";
	private final String licenseFileName = "morena_license.jar";
	private URLClassLoader classLoader;
	@SuppressWarnings("rawtypes")
	private HashMap<String, Class> classes;
	
	private boolean loaded = false;
	
	
	public ScanningAPIWrapper() {

		classes = new HashMap<>();
		
		String pathToLicenseFile = Application.getCurrentDirectory() + licenseFileName;
		String nativeLibFile = Application.getCurrentDirectory() + nativeLibFileName;
		String libFile = Application.getCurrentDirectory() + libFileName;
		
		try {
			URL[] urls = new URL[] {	new URL("jar:file:" + pathToLicenseFile + "!/"),
										new URL("jar:file:" + nativeLibFile + "!/"),
										new URL("jar:file:" + libFile + "!/")
									};
			loadJars(urls);
			loadClasses(pathToLicenseFile);
			loadClasses(nativeLibFile);
			loadClasses(libFile);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		test();
	}
	
	private void loadJars(URL[] urls) {
		classLoader = URLClassLoader.newInstance(urls);
	}
	
	private void loadClasses(String pathToJar) {
		
		try {
			JarFile morenaJar = new JarFile(pathToJar);
			Enumeration<JarEntry> e = morenaJar.entries();
			
			while (e.hasMoreElements()) {
			    JarEntry je = e.nextElement();
			    if(je.isDirectory()) {
			        continue;
			    }
			    else if(!je.getName().endsWith(".class")) {
			    	if(je.getName().contains("license") || je.getName().contains(".dll")) {
			    		
			    		//InputStream in = classLoader.getResourceAsStream("/"+je.getName()); 
			    		//BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			    	}
			    	
			    }
			    else {
				    String className = je.getName().substring(0,je.getName().length()-6);
				    className = className.replace('/', '.');
				    @SuppressWarnings("rawtypes")
					Class c = classLoader.loadClass(className);
				    
				    String classCanonicalName = c.getCanonicalName();
				    
				    if(classCanonicalName != null) {
				    	if(!classes.containsKey(classCanonicalName)) classes.put(classCanonicalName, c);
				    	else System.out.println("123");
				    	System.out.println(className + " --- " + c.getCanonicalName());
				    }
			    }
			    
			}
			morenaJar.close();
		} catch (IOException | ClassNotFoundException e) {
			Common.showErrorMessageDialog(e);
		}
		
		if(classes.size()>0) loaded = true;
	}
	
	private void test() {
		
		
		
	}


	public boolean isLoaded() {
		return loaded;
	}
	

	@SuppressWarnings("rawtypes")
	public Class getClassByCanonicalName(String name) {
		return classes.get(name);
	}

	/*private class Manager{
		
		public List<Device> listDevices() {
			return null;
		}
	}
	
	private class Device extends Object{
		
	}
	
	private class Scaner extends Device{
		
	}
	
	private class Camera extends Device{
		
	}
	
	private class SynchronousHelper{
		
		public BufferedImage scanImage(Device device) {
			return null;
		}
	}*/
	
	
}
