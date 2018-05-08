package juiscan;

import java.awt.EventQueue;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;

import juiscan.gui.shells.main.ProcessBarFrame;

/**
 * JUISCAN - ѕриложение дл€ быстрого сканировани€ под Windows.
 * @author Vorontsov D.S.
 */
public class Application {

	public static final String app_name = "JUISCAN";
	public static final String app_description = "JUISCAN - Java uninquiring scan utility for Windows";
	public static final String app_version = "0.1";
	public static final String app_build_timestamp = "2018.05.05";
	public static final String app_development_dates = "2018";
	public static final String app_mail_hyperlink = "38765232+owmaker@users.noreply.github.com";
	public static final String[] app_autors = {"Vorontsov D.S."};

	private static String CD;
	private static String TmpDir;
	
	public static void main(String[] args) {
		
		try {
			String dir = Class.forName("juiscan.Application").getProtectionDomain().getCodeSource().getLocation().getPath();
			dir = URLDecoder.decode(dir, "UTF-8");
			CD = dir.substring(0, dir.lastIndexOf("/")+1);
			CD = CD.replaceAll("[/\\\\]+", Matcher.quoteReplacement(File.separator));
			TmpDir = System.getProperty("java.io.tmpdir");
			TmpDir = TmpDir.replaceAll("[/\\\\]+", Matcher.quoteReplacement(File.separator));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Common.showErrorMessageDialog(e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Common.showErrorMessageDialog(e);
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProcessBarFrame pbf = new ProcessBarFrame();
					pbf.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					Common.showErrorMessageDialog(e);
				}
			}
		});
		
	}

	//---------------------------------------
	public static String getCurrentDirectory(){
		return CD;
	}
	
	public static String getTemporaryDirectory(){
		return TmpDir;
	}
	//---------------------------------------

}
