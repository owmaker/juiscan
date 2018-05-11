package juiscan;

import java.awt.EventQueue;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

import javax.swing.UIManager;

import juiscan.gui.shells.main.MainWindow;
import juiscan.gui.shells.main.ProcessBarFrame;
import juiscan.i18n.I18n;
import juiscan.settings.SettingsManager;

/**
 * JUISCAN - ѕриложение дл€ быстрого сканировани€ под Windows.
 * @author Vorontsov D.S.
 */
public class Application {
	
	public static final String app_name = "JUISCAN";
	public static final String app_description = "JUISCAN - Java uninquiring scan utility for Windows";
	public static final String app_version = "0.1";
	public static final String app_build_timestamp = "2018.05.11";
	public static final String app_development_dates = "2018";
	public static final String app_mail_hyperlink = "38765232+owmaker@users.noreply.github.com";
	public static final String[] app_autors = {"Vorontsov D.S."};
	public static final String[] app_thirdparty_libraries = {"ini4j 0.5.4 (Apache License 2.0)"};

	private static ArrayList<String> arguments;
	private static String CD;
	private static String TmpDir;
	private static int mode = 0;				//0 - run main window, 1 - run scanning bar

	private static MainWindow mainWindow;
	private static ProcessBarFrame scanWindow;
	private static SettingsManager settingsManager;
	
	public static void main(String[] args) {
		
		arguments = new ArrayList<> (Arrays.asList(args));
		parseAndApplyArguments();
		
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

		settingsManager = new SettingsManager();
		I18n.initialize();
		settingsManager.initMainSettings();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					if(mode == 0) {
						MainWindow mainWindow = new MainWindow();
						mainWindow.setVisible(true);
					}
					else if(mode == 1) {
						ProcessBarFrame scanWindow = new ProcessBarFrame();
						scanWindow.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Common.showErrorMessageDialog(e);
				}
			}
		});
		
	}
	
	private static void parseAndApplyArguments() {
		try {
			if(arguments.get(0).equals("scan")) mode = 1;
		} catch (IndexOutOfBoundsException e){}
	}

	//---------------------------------------
	public static String getCurrentDirectory(){
		return CD;
	}
	
	public static String getTemporaryDirectory(){
		return TmpDir;
	}
	//---------------------------------------
	public static MainWindow getMainWindow(){
		return mainWindow;
	}
	public static ProcessBarFrame getScanWindow(){
		return scanWindow;
	}
	//---------------------------------------
	public static SettingsManager getSettingsManager(){
		return settingsManager;
	}
	
	public static String getSettingsFileFullName(){
		if(settingsManager != null) return settingsManager.getSettingsFileFullName();
		else return "";
	}
	//---------------------------------------
}
