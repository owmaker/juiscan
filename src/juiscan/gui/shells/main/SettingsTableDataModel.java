package juiscan.gui.shells.main;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import juiscan.i18n.I18n;

public class SettingsTableDataModel implements TableModel {

	
	
	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
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
