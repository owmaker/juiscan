package juiscan;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

/**
 * @author Vorontsov D.S.
 */
public class ProcessBarFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public ProcessBarFrame() {
		setType(Type.UTILITY);
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		int height = 150;
		int width = 350;
		
		Dimension screen_dimensions = Toolkit.getDefaultToolkit().getScreenSize();
		int screen_width = (int) screen_dimensions.getWidth();
		int screen_height = (int) screen_dimensions.getHeight();
		setBounds(screen_width - width, screen_height - height - 75, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblNewLabel = new JLabel("New label");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 3, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel, 6, SpringLayout.WEST, contentPane);
		contentPane.add(lblNewLabel);
		
		JProgressBar progressBar = new JProgressBar();
		sl_contentPane.putConstraint(SpringLayout.NORTH, progressBar, 6, SpringLayout.SOUTH, lblNewLabel);
		sl_contentPane.putConstraint(SpringLayout.WEST, progressBar, 6, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, progressBar, -6, SpringLayout.EAST, contentPane);
		progressBar.setValue(50);
		contentPane.add(progressBar);
		
		JButton btnNewButton = new JButton("New button");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnNewButton, -3, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnNewButton, -6, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, progressBar, -6, SpringLayout.NORTH, btnNewButton);
		contentPane.add(btnNewButton);
	}
}
