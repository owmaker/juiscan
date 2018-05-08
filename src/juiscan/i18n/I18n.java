package juiscan.i18n;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

import juiscan.Common;

/**
 * Класс, обеспечивающий чтение локализованных строк пользовательского интерфейса.
 * @author Vorontsov D.S.
 */
public class I18n {
	
	private static String DEFAULT_LANG = Locale.getDefault().getLanguage();
	private static ResourceBundle labels;
	
	public static void initialize() {
		
		//System.out.println(Arrays.toString(Locale.getAvailableLocales()));
		Locale locale;
		switch(DEFAULT_LANG){
			case "ru":	locale = new Locale("ru", "RU");
						break;
			case "en":	locale = new Locale("en", "US");
						break;
			case "de":	locale = new Locale("de", "DE");
						break;
			case "zh":	locale = new Locale("zh", "CN");
						break;
			case "ja":	locale = new Locale("ja", "JP");
						break;
			case "fr":	locale = new Locale("fr", "FR");
						break;
			case "ko":	locale = new Locale("ko", "KR");
						break;
			default:	locale = Locale.getDefault();
						break;
		}
		labels = ResourceBundle.getBundle("altir.resources.lang.lang", locale);
	}

	public static String getString(String label){
		if(labels!=null){
			String str = labels.getString(label);
			try {
				str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
				return str;
			} catch (UnsupportedEncodingException e) {
				Common.showErrorMessageDialog(e);
			}
		}
		//System.out.println(label);
		return "";
	}
	
}
