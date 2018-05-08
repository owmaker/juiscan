package juiscan.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

import juiscan.Application;
import juiscan.Common;
import juiscan.i18n.I18n;

/**
 *  ласс, обеспечивающий чтение, изменение и сохранение настроек.
 * @author Vorontsov D.S.
 */
public class SettingsManager {

	private final String fileName = "JUISCAN_settings.ini";
	private static String fileFullName;
	
	public static enum ST_TYPE{BOOLEAN, STRING, INTEGER, FLOAT, BOOLEANARRAY, STRINGARRAY, INTEGERARRAY, FLOATARRAY, INTEGERARRAYOFARRAYS};
	
	public static enum SETTINGS_GROUPE{LANG, COMMON};
	
	private Settings settings;
	
	private Ini iniFile;
	

	private Class<?> ApplicationClass;
	private Class<?> I18nClass;
	
	public SettingsManager(){
		
		if(settings == null){
			fileFullName = Application.getTemporaryDirectory() + fileName;
			
			try {
				I18nClass = 						Class.forName("altir.i18n.I18n");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				Common.showErrorMessageDialog(e);
			}
			
			readSettingsFromFile();
			
			settings = new Settings();
			settings.put(SETTINGS_GROUPE.LANG, initLangSettings());

			applyLangSettings();
		}
	}
	
	
	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------
	
	public void initMainSettings(){
		try {
			ApplicationClass = 					Class.forName("juiscan.Application");
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			Common.showErrorMessageDialog(e1);
		}
		
		//settings.put(SETTINGS_GROUPE.COMMON, initCommonSettings());

		applyMainSettings();
	}
	
	private ArrayList<HashMap<String, String>> initLangSettings(){
		String section = SETTINGS_GROUPE.LANG.name();
		ArrayList<HashMap<String, String>> group = new ArrayList<HashMap<String,String>>();
		//-----------------------------------------
		String[] names = new String[]{
				"DEFAULT_LANG"
		};
		String[] titles = new String[]{
				""
		};
		String[] descriptions = new String[]{
				"ru, en"//, de, zh, ja, fr, ko"
		};
		String[] types = new String[]{
				ST_TYPE.STRING.name()
		};
		String[] classNames = new String[]{
				I18nClass.getCanonicalName()
		};
		
		String lang_default_value = "";
		try {
			lang_default_value = getCanonicalValue(getFieldValue(Class.forName(classNames[0]), names[0], null));
		} catch (ClassNotFoundException e1) {}
		switch(lang_default_value){
			case "ru":	titles[0] = "язык интерфейса";												//не локализовать!
						break;
			case "en":	titles[0] = "Interface language";
						break;
			/*case "de":	locale = new Locale("de", "DE");
						break;
			case "zh":	locale = new Locale("zh", "CN");
						break;
			case "ja":	locale = new Locale("ja", "JP");
						break;
			case "fr":	locale = new Locale("fr", "FR");
						break;
			case "ko":	locale = new Locale("ko", "KR");
						break;*/
			default:	titles[0] = "Interface language";
						break;
		}
		
		//-----------------------------------------
		for(int i = 0 ; i < names.length ; i++){
			try {
				String option = names[i];
				group.add(i, new HashMap<String, String>());
				group.get(i).put("name", option);
				group.get(i).put("title", titles[i]);
				group.get(i).put("description", descriptions[i]);
				group.get(i).put("type", types[i]);
				group.get(i).put("className", classNames[i]);
				group.get(i).put("defaultVal", getCanonicalValue(getFieldValue(Class.forName(classNames[i]), option, null)));
				if(iniFile.containsKey(section)){
					if(iniFile.get(section).containsKey(option))
						group.get(i).put("currentVal",iniFile.get(section, option));
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				Common.showErrorMessageDialog(e);
			}
		}
		//-----------------------------------------
		group.trimToSize();
		return group;
	}
	
	/*private ArrayList<HashMap<String, String>> initCommonSettings(){
		
		String section = SETTINGS_GROUPE.COMMON.name();
		ArrayList<HashMap<String, String>> group = new ArrayList<HashMap<String,String>>();
		
		//-----------------------------------------
		String[] names = new String[]{
				"DB_SYNC_INTERVAL"
		};
		String[] titles = new String[]{
				I18n.getString("DB_SYNC_INTERVAL_title")
		};
		String[] descriptions = new String[]{
				""
		};
		String[] types = new String[]{
				ST_TYPE.INTEGER.name()
		};
		String[] classNames = new String[]{
				ApplicationClass.getCanonicalName()
		};
		//-----------------------------------------
		for(int i = 0 ; i < names.length ; i++){
			try {
				String option = names[i];
				group.add(i, new HashMap<String, String>());
				group.get(i).put("name", option);
				group.get(i).put("title", titles[i]);
				group.get(i).put("description", descriptions[i]);
				group.get(i).put("type", types[i]);
				group.get(i).put("className", classNames[i]);
				group.get(i).put("defaultVal", getCanonicalValue(getFieldValue(Class.forName(classNames[i]), option, null)));
				if(iniFile.containsKey(section)){
					if(iniFile.get(section).containsKey(option))
						group.get(i).put("currentVal",iniFile.get(section, option));
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				Common.showErrorMessageDialog(e);
			}
		}
		//-----------------------------------------
		group.trimToSize();
		return group;
	}*/

	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------

	private void applyLangSettings(){
		applySettings(SETTINGS_GROUPE.LANG);
	}
	
	private void applyMainSettings(){
		applySettings(SETTINGS_GROUPE.COMMON);
	}
	
	private void applySettings(SETTINGS_GROUPE section){

		for(HashMap<String, String> optionObj : settings.get(section)){
			String option = optionObj.get("name");
			try {
				if(optionObj.containsKey("currentVal")){
					setFieldValue(Class.forName(optionObj.get("className")), option, null, optionObj.get("currentVal"), optionObj.get("type"));
				}
				else{
					setFieldValue(Class.forName(optionObj.get("className")), option, null, optionObj.get("defaultVal"), optionObj.get("type"));
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				Common.showErrorMessageDialog(e);
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------
	
	private void readSettingsFromFile(){

		iniFile = new Ini();
		
		File f = new File(fileFullName);
		boolean canUse = checkFile(f);
		
		try {
			if(canUse) {
				iniFile.load(f);
			}
		} catch (InvalidFileFormatException e) {
			e.printStackTrace();
			Common.showErrorMessageDialog(e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Common.showErrorMessageDialog(e);
		} catch (IOException e) {
			e.printStackTrace();
			Common.showErrorMessageDialog(e);
		}
	}
	
	private boolean checkFile(File f){
		boolean canUse = true;
		if(f.exists()){
			if(!f.canRead() || !Files.isReadable(f.toPath())){
				Common.showMessageDialog(I18n.getString("settings_file_protected_from_reading_warning"), "", JOptionPane.WARNING_MESSAGE);
				canUse = false;
			}
			if((!f.canWrite() || !Files.isWritable(f.toPath())) && canUse){
				Common.showMessageDialog(I18n.getString("settings_file_protected_from_writing_warning"), "", JOptionPane.WARNING_MESSAGE);
				canUse = false;
			}
		}
		else{
			try {
				f.createNewFile();
			} catch (IOException e) {
				Common.showMessageDialog(I18n.getString("cant_create_settings_file_warning"), "", JOptionPane.WARNING_MESSAGE);
				canUse = false;
				//e.printStackTrace();
			}
		}
		return canUse;
	}
	

	//-----------------------------------------------------------------------------
	
	/**
	 * ¬озвращает значение пол€ (в т.ч. приватного) указанного класса указанного экземпл€ра класса.
	 * „тобы получить значение статического пол€, экземпл€ром класса необходимо указать null.
	 * @param classObj - класс
	 * @param fieldName - наименование пол€
	 * @param classInstanceObj - экземпл€р класса
	 * @return значение пол€
	 */
	@SuppressWarnings("rawtypes")
	private static Object getFieldValue(Class classObj, String fieldName, Object classInstanceObj){
		
		Field f = null;
		try {
			f = classObj.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			e.printStackTrace();
			Common.showErrorMessageDialog(e);
		} catch (NoSuchFieldException e) {
			//e.printStackTrace();
			//Common.showErrorMessageDialog(e);
		}
		Object value = null;
		if(f != null){
			f.setAccessible(true);
			
			try {
				value = f.get(classInstanceObj);
				//System.out.println("<<< " + value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				Common.showErrorMessageDialog(e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				Common.showErrorMessageDialog(e);
			}
		}
		
		return value;
	}
	
	/**
	 * «аписывает значение пол€ (в т.ч. приватного) указанного класса указанного экземпл€ра класса.
	 * „тобы записать значение статического пол€, экземпл€ром класса необходимо указать null.
	 * @param classObj - класс
	 * @param fieldName - наименование пол€
	 * @param classInstanceObj - экземпл€р класса
	 * @param value - значение пол€
	 * @param valueType - тип пол€
	 */
	@SuppressWarnings({ "rawtypes"})
	private static void setFieldValue(Class classObj, String fieldName, Object classInstanceObj, String value, String valueType){
		
		Field f = null;
		try {
			f = classObj.getDeclaredField(fieldName);
			
		} catch (SecurityException e) {
			e.printStackTrace();
			Common.showErrorMessageDialog(e);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			Common.showErrorMessageDialog(e);
		}
		if(f != null){
			f.setAccessible(true);
			
			try {
				if(ST_TYPE.valueOf(valueType)==ST_TYPE.BOOLEAN)					f.setBoolean(classInstanceObj, Boolean.valueOf(value));
				if(ST_TYPE.valueOf(valueType)==ST_TYPE.STRING)					f.set(classInstanceObj, value);
				if(ST_TYPE.valueOf(valueType)==ST_TYPE.INTEGER)					f.setInt(classInstanceObj, Integer.valueOf(value));
				if(ST_TYPE.valueOf(valueType)==ST_TYPE.FLOAT)					f.setDouble(classInstanceObj, Double.valueOf(value));
				if(ST_TYPE.valueOf(valueType)==ST_TYPE.BOOLEANARRAY)			f.set(classInstanceObj, Common.stringToBooleanArray(value, ","));
				if(ST_TYPE.valueOf(valueType)==ST_TYPE.STRINGARRAY)				f.set(classInstanceObj, Common.stringToStringArray(value, ","));
				if(ST_TYPE.valueOf(valueType)==ST_TYPE.INTEGERARRAY)			f.set(classInstanceObj, Common.stringToIntegerArray(value, ","));
				if(ST_TYPE.valueOf(valueType)==ST_TYPE.FLOATARRAY)				f.set(classInstanceObj, Common.stringToDoubleArray(value, ","));
				if(ST_TYPE.valueOf(valueType)==ST_TYPE.INTEGERARRAYOFARRAYS)	f.set(classInstanceObj, Common.stringToIntegerArrayOfArrays(value, "|", ","));
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				Common.showErrorMessageDialog(e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				Common.showErrorMessageDialog(e);
			}
		}
	}
	
	private static String getCanonicalValue(Object obj){
		
		if(	Boolean.class.isInstance(obj)
			|| String.class.isInstance(obj)
			|| Integer.class.isInstance(obj)
			|| Double.class.isInstance(obj)){
			return String.valueOf(obj);
		}
		else{
			if( boolean[].class.isInstance(obj))	return Arrays.toString((boolean[])obj).replaceAll("[\\[\\]]", "");
			else if(String[].class.isInstance(obj))	return Arrays.toString((String[])obj).replaceAll("[\\[\\]]", "");
			else if(int[].class.isInstance(obj))	return Arrays.toString((int[])obj).replaceAll("[\\[\\]]", "");
			else if(double[].class.isInstance(obj))	return Arrays.toString((double[])obj).replaceAll("[\\[\\]]", "");
			else if(int[][].class.isInstance(obj))	{
				int[][] array = (int[][])obj;
				String str = "";
				for(int i = 0 ; i < array.length; i++) str = str.concat(Arrays.toString(array[i])).concat("|");
				str = str.substring(0, str.length()-1).replaceAll("[\\[\\]]", "");
				return str;
			}
			return "";
		}
	}

	//-----------------------------------------------------------------------------

}
