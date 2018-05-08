package juiscan.settings;

import java.util.ArrayList;
import java.util.HashMap;

import juiscan.settings.SettingsManager.SETTINGS_GROUPE;

/**
 *  = HashMap(SETTINGS_GROUPE, ArrayList(HashMap(String1, String2))) settings</br>
 * SETTINGS_GROUPE > section</br>
 * String1 > option | title | description | type | className | defaultVal | currentVal</br>
 * String2 > value
 * @author Vorontsov D.S.
 */
public class Settings extends HashMap<SETTINGS_GROUPE, ArrayList<HashMap<String, String>>> {
	
	private static final long serialVersionUID = 1L;
	
	public Settings clone(){
		Settings new_settings  = new Settings();
		
		for(SETTINGS_GROUPE groupe : this.keySet()){
			new_settings.put(groupe, new ArrayList<HashMap<String, String>>());
			for(HashMap<String, String> option : this.get(groupe)){
				new_settings.get(groupe).add(new HashMap<String, String>());
				for(String param : option.keySet()){
					int k = new_settings.get(groupe).size()-1;
					new_settings.get(groupe).get(k).put(param, option.get(param));
				}
			}
		}
		return new_settings;
	}

}
