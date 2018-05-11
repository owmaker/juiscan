package juiscan.gui.shells.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import juiscan.Application;
import juiscan.i18n.I18n;
import juiscan.settings.Settings;

/**
 * @author Vorontsov D.S.
 */
public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable settingsTable;
	private JButton saveButton;

	private Settings settings;
	
	public MainWindow() {

		settings = Application.getSettingsManager().getAllSettings();
		
		setTitle(I18n.getString("application_name"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		sl_contentPane.putConstraint(SpringLayout.NORTH, tabbedPane, -5, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, tabbedPane, -6, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, tabbedPane, 7, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, tabbedPane, 8, SpringLayout.EAST, contentPane);
		contentPane.add(tabbedPane);

		//----------------------------------------------------------------------------------------------------------
		
		JPanel settingsPanel = new JPanel();
		tabbedPane.addTab(I18n.getString("main_window_settings_tab_title"), null, settingsPanel, null);
		SpringLayout sl_settingsPanel = new SpringLayout();
		settingsPanel.setLayout(sl_settingsPanel);
		
		saveButton = new JButton(I18n.getString("save_button_text"));
		sl_settingsPanel.putConstraint(SpringLayout.NORTH, saveButton, -32, SpringLayout.SOUTH, settingsPanel);
		sl_settingsPanel.putConstraint(SpringLayout.WEST, saveButton, -120, SpringLayout.EAST, settingsPanel);
		sl_settingsPanel.putConstraint(SpringLayout.SOUTH, saveButton, -5, SpringLayout.SOUTH, settingsPanel);
		sl_settingsPanel.putConstraint(SpringLayout.EAST, saveButton, -5, SpringLayout.EAST, settingsPanel);
		settingsPanel.add(saveButton);
		
		SettingsTableDataModel stdm = new SettingsTableDataModel(settings);
		settingsTable = new JTable(stdm);
		settingsTable.getColumnModel().getColumn(0).setMinWidth(150);
		settingsTable.getColumnModel().getColumn(1).setMinWidth(100);
		settingsTable.getColumnModel().getColumn(2).setMinWidth(50);
		settingsTable.getColumnModel().getColumn(3).setMinWidth(50);
		settingsTable.getColumnModel().getColumn(4).setMinWidth(100);
		
		JScrollPane scrollPane = new JScrollPane(settingsTable);
		sl_settingsPanel.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, settingsPanel);
		sl_settingsPanel.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, settingsPanel);
		sl_settingsPanel.putConstraint(SpringLayout.SOUTH, scrollPane, -5, SpringLayout.NORTH, saveButton);
		sl_settingsPanel.putConstraint(SpringLayout.EAST, scrollPane, -1, SpringLayout.EAST, settingsPanel);
		settingsPanel.add(scrollPane);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Application.getSettingsManager().setSettings(settings);
			}
		});
		
		//----------------------------------------------------------------------------------------------------------
		
		JPanel aboutPanel = new JPanel();
		tabbedPane.addTab(I18n.getString("main_window_about_tab_title"), null, aboutPanel, null);
		SpringLayout sl_aboutPanel = new SpringLayout();
		aboutPanel.setLayout(sl_aboutPanel);
		
		JTabbedPane aboutTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		sl_aboutPanel.putConstraint(SpringLayout.NORTH, aboutTabbedPane, 0, SpringLayout.NORTH, aboutPanel);
		sl_aboutPanel.putConstraint(SpringLayout.WEST, aboutTabbedPane, -1, SpringLayout.WEST, aboutPanel);
		sl_aboutPanel.putConstraint(SpringLayout.SOUTH, aboutTabbedPane, 2, SpringLayout.SOUTH, aboutPanel);
		sl_aboutPanel.putConstraint(SpringLayout.EAST, aboutTabbedPane, 0, SpringLayout.EAST, aboutPanel);
		aboutPanel.add(aboutTabbedPane);
		

		JPanel descriptionPanel = new JPanel();
		aboutTabbedPane.addTab(I18n.getString("aboutTabItem_0_text"), null, descriptionPanel, null);
		JPanel autorsPanel = new JPanel();
		aboutTabbedPane.addTab(I18n.getString("aboutTabItem_1_text"), null, autorsPanel, null);
		JPanel systemPanel = new JPanel();
		aboutTabbedPane.addTab(I18n.getString("aboutTabItem_2_text"), null, systemPanel, null);
		JPanel environmentPanel = new JPanel();
		aboutTabbedPane.addTab(I18n.getString("aboutTabItem_3_text"), null, environmentPanel, null);
		JPanel libsPanel = new JPanel();
		aboutTabbedPane.addTab(I18n.getString("aboutTabItem_4_text"), null, libsPanel, null);
		JPanel otherPanel = new JPanel();
		aboutTabbedPane.addTab(I18n.getString("aboutTabItem_5_text"), null, otherPanel, null);
		
		//-----------------------------------------------------
		
		SpringLayout sl_descriptionPanel = new SpringLayout();
		descriptionPanel.setLayout(sl_descriptionPanel);
		
		JTextPane description = new JTextPane();
		descriptionPanel.add(description);
		sl_descriptionPanel.putConstraint(SpringLayout.NORTH, description, 0, SpringLayout.NORTH, descriptionPanel);
		sl_descriptionPanel.putConstraint(SpringLayout.WEST, description, 0, SpringLayout.WEST, descriptionPanel);
		sl_descriptionPanel.putConstraint(SpringLayout.SOUTH, description, 0, SpringLayout.SOUTH, descriptionPanel);
		sl_descriptionPanel.putConstraint(SpringLayout.EAST, description, 0, SpringLayout.EAST, descriptionPanel);

		description.setEditable(false);
		
		description.setText(I18n.getString("application_name")
		+ "\r\n" + I18n.getString("application_version_description_text").replaceAll("%application_version%", Application.app_version).replaceAll("%application_build_timestamp%", Application.app_build_timestamp) + "\r\n(c) "
		+ Application.app_development_dates + ". " + I18n.getString("application_rights_description_text") + ".\r\n\r\n" 
		+ I18n.getString("application_description") + ".");
		
		//-----------------------------------------------------
		
		SpringLayout sl_autorsPanel = new SpringLayout();
		autorsPanel.setLayout(sl_autorsPanel);
		
		JTextPane autors = new JTextPane();
		autorsPanel.add(autors);
		sl_autorsPanel.putConstraint(SpringLayout.NORTH, autors, 0, SpringLayout.NORTH, autorsPanel);
		sl_autorsPanel.putConstraint(SpringLayout.WEST, autors, 0, SpringLayout.WEST, autorsPanel);
		sl_autorsPanel.putConstraint(SpringLayout.SOUTH, autors, 0, SpringLayout.SOUTH, autorsPanel);
		sl_autorsPanel.putConstraint(SpringLayout.EAST, autors, 0, SpringLayout.EAST, autorsPanel);

		autors.setEditable(false);
		
		autors.setText(I18n.getString("application_autors").replace(",", "\r\n").replaceAll("[\\[\\]]", ""));

		//-----------------------------------------------------

		String[] tableHeaders = new String[] {I18n.getString("aboutTblColumn_0_text"), I18n.getString("aboutTblColumn_1_text")};
		
		ArrayList <String> keys = new ArrayList<String>();
		for(Object obj : System.getProperties().keySet()) keys.add(obj.toString());
		keys.trimToSize();
		Collections.sort(keys);
		
		int l1 = 0;
		int l2 = 0;
		
		for(String key : keys){
			String k = key.toString();
			if(k.indexOf("os.") == 0 || k.indexOf("user.") == 0){
				l1++;
			}
			if(k.indexOf("java.") == 0 || k.indexOf("sun.") == 0 || k.indexOf("eclipse.") == 0 || k.indexOf("osgi.") == 0){
				l2++;
			}
		}
		
		String[][] sysTableData = new String[l1][2];
		String[][] envTableData = new String[l2][2];
		
		l1 = 0;
		l2 = 0;
		
		for(String key : keys){
			String k = key.toString();
			String t = System.getProperties().getProperty(k).replaceAll("(\r\n|\n)", " ");
			if(k.indexOf("os.") == 0 || k.indexOf("user.") == 0){
				sysTableData[l1][0] = k; sysTableData[l1][1] = t;
				l1++;
			}
			if(k.indexOf("java.") == 0 || k.indexOf("sun.") == 0 || k.indexOf("eclipse.") == 0 || k.indexOf("osgi.") == 0){
				envTableData[l2][0] = k; envTableData[l2][1] = t;
				l2++;
			}
		}

		//-----------------------------------------------------

		SpringLayout sl_systemPanel = new SpringLayout();
		systemPanel.setLayout(sl_systemPanel);

		JTable systemTable = new JTable(sysTableData, tableHeaders);
		systemTable.getColumnModel().getColumn(0).setMinWidth(100);
		systemTable.getColumnModel().getColumn(1).setMinWidth(250);
		systemTable.setDefaultEditor(Object.class, null);
		
		JScrollPane systemScrollPane = new JScrollPane(systemTable);
		sl_systemPanel.putConstraint(SpringLayout.NORTH, systemScrollPane, -1, SpringLayout.NORTH, systemPanel);
		sl_systemPanel.putConstraint(SpringLayout.WEST, systemScrollPane, 0, SpringLayout.WEST, systemPanel);
		sl_systemPanel.putConstraint(SpringLayout.SOUTH, systemScrollPane, 1, SpringLayout.SOUTH, systemPanel);
		sl_systemPanel.putConstraint(SpringLayout.EAST, systemScrollPane, 1, SpringLayout.EAST, systemPanel);
		systemPanel.add(systemScrollPane);
		
		//-----------------------------------------------------
		
		SpringLayout sl_environmentPanel = new SpringLayout();
		environmentPanel.setLayout(sl_environmentPanel);

		JTable environmentTable = new JTable(envTableData, tableHeaders);
		environmentTable.getColumnModel().getColumn(0).setMinWidth(100);
		environmentTable.getColumnModel().getColumn(1).setMinWidth(250);
		environmentTable.setDefaultEditor(Object.class, null);
		
		JScrollPane environmentScrollPane = new JScrollPane(environmentTable);
		sl_environmentPanel.putConstraint(SpringLayout.NORTH, environmentScrollPane, -1, SpringLayout.NORTH, environmentPanel);
		sl_environmentPanel.putConstraint(SpringLayout.WEST, environmentScrollPane, 0, SpringLayout.WEST, environmentPanel);
		sl_environmentPanel.putConstraint(SpringLayout.SOUTH, environmentScrollPane, 1, SpringLayout.SOUTH, environmentPanel);
		sl_environmentPanel.putConstraint(SpringLayout.EAST, environmentScrollPane, 1, SpringLayout.EAST, environmentPanel);
		environmentPanel.add(environmentScrollPane);
		
		//-----------------------------------------------------
		
		SpringLayout sl_libsPanel = new SpringLayout();
		libsPanel.setLayout(sl_libsPanel);
		
		JTextPane libs = new JTextPane();
		libsPanel.add(libs);
		sl_libsPanel.putConstraint(SpringLayout.NORTH, libs, 0, SpringLayout.NORTH, libsPanel);
		sl_libsPanel.putConstraint(SpringLayout.WEST, libs, 0, SpringLayout.WEST, libsPanel);
		sl_libsPanel.putConstraint(SpringLayout.SOUTH, libs, 0, SpringLayout.SOUTH, libsPanel);
		sl_libsPanel.putConstraint(SpringLayout.EAST, libs, 0, SpringLayout.EAST, libsPanel);

		libs.setEditable(false);
		
		libs.setText(Arrays.toString(Application.app_thirdparty_libraries).replace(", ", "\r\n").replaceAll("[\\[\\]]", ""));

		//-----------------------------------------------------
		
		SpringLayout sl_otherPanel = new SpringLayout();
		otherPanel.setLayout(sl_otherPanel);
		
		JTextPane other = new JTextPane();
		otherPanel.add(other);
		sl_otherPanel.putConstraint(SpringLayout.NORTH, other, 0, SpringLayout.NORTH, otherPanel);
		sl_otherPanel.putConstraint(SpringLayout.WEST, other, 0, SpringLayout.WEST, otherPanel);
		sl_otherPanel.putConstraint(SpringLayout.SOUTH, other, 0, SpringLayout.SOUTH, otherPanel);
		sl_otherPanel.putConstraint(SpringLayout.EAST, other, 0, SpringLayout.EAST, otherPanel);

		other.setEditable(false);
		
		other.setText(I18n.getString("about_other_text").replaceAll("%application_mail%", Application.app_mail_hyperlink));
	}
}
