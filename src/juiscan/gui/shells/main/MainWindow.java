package juiscan.gui.shells.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import juiscan.Application;
import juiscan.i18n.I18n;
import juiscan.settings.Settings;

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
		sl_settingsPanel.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, settingsPanel);
		settingsPanel.add(scrollPane);
		
		
		
		JPanel aboutPanel = new JPanel();
		tabbedPane.addTab(I18n.getString("main_window_about_tab_title"), null, aboutPanel, null);
		
		
		
	}
}
