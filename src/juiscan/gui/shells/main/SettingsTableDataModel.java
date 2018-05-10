package juiscan.gui.shells.main;

import java.util.HashMap;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import juiscan.i18n.I18n;
import juiscan.settings.Settings;
import juiscan.settings.SettingsManager.SETTINGS_GROUPE;

public class SettingsTableDataModel implements TableModel {
	
	private Settings settings;
	private SETTINGS_GROUPE[] sections = new SETTINGS_GROUPE[]{SETTINGS_GROUPE.LANG, SETTINGS_GROUPE.COMMON, SETTINGS_GROUPE.SCAN};
	private int rowCount;
	private HashMap<Integer, SETTINGS_GROUPE> rowToSection;
	private HashMap<Integer, HashMap<String, String>> rowToOption;
	
	public SettingsTableDataModel(Settings settings) {
		rowToSection = new HashMap<>();
		rowToOption = new HashMap<>();
		this.settings = settings;
		countRows();
		parseSectionsAndOptions();
	}
	
	private void countRows() {
		rowCount = 0;
		for(int i = 0 ; i < sections.length ; i ++) {
			if(settings.containsKey(sections[i])) {
				rowCount = rowCount + settings.get(sections[i]).size();
			}
		}
	}
	
	private void parseSectionsAndOptions() {
		int k = 0;
		for(int i = 0 ; i < sections.length ; i ++) {
			if(settings.containsKey(sections[i])) {
				for(HashMap<String, String> option : settings.get(sections[i])) {
					rowToSection.put(Integer.valueOf(k), sections[i]);
					rowToOption.put(Integer.valueOf(k), option);
					k++;
				}
			}
		}
	}
	
	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return I18n.getString("col_0_title");
			case 1:
				return I18n.getString("col_1_title");
			case 2:
				return I18n.getString("col_2_title");
			case 3:
				return I18n.getString("col_3_title");
			case 4:
				return I18n.getString("col_4_title");
			default:
				return null;
		}
	}
	
	@Override
	public int getRowCount() {
		return rowCount;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		String str = "";
		HashMap<String, String> option = rowToOption.get(Integer.valueOf(rowIndex));
		switch (columnIndex) {
			case 0:
				str = option.get("title");
				break;
			case 1:
				str = option.get("description");
				break;
			case 2:
				str = option.get("type");
				break;
			case 3:
				str = option.get("defaultVal");
				break;
			case 4:
				str = option.get("currentVal");
				break;
			default:
				break;
		}
		str = (str==null)?"":str;
		return str;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex==4) return true;
		return false;
	}
	
	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}
	
}
