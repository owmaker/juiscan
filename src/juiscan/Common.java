package juiscan;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Класс общих методов.
 * @author Vorontsov D.S.
 */
public class Common {
	

	//-----------------------------------------------------------------------------
	
	public static int showConfirmDialog(final String message, final String title, final int button_style, final int icon_style){
		
		int lc = message.split("\n", -1).length;
		
		JTextArea ta = new JTextArea((lc>30)?30:lc, 0);
		ta.setEditable(false);
        ta.setText(message);
		JScrollPane sp = new JScrollPane(ta);
		
		int opt = JOptionPane.showConfirmDialog(null, sp, title, button_style, icon_style);
		return opt;
	}
	
	public static void showMessageDialog(final String message, final String title, final int icon_style){
		
		int lc = message.split("\n", -1).length;
		
		JTextArea ta = new JTextArea((lc>30)?30:lc, 0);
		ta.setEditable(false);
        ta.setText(message);
		JScrollPane sp = new JScrollPane(ta);
		
		JOptionPane.showMessageDialog(null, sp, title, icon_style);
	}
	
	public static void showErrorMessageDialog(Exception e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String str = sw.toString();
		
		showMessageDialog(str, "", JOptionPane.ERROR_MESSAGE);
	}

	//-----------------------------------------------------------------------------
	
	public static boolean[] stringToBooleanArray(String str, String delimiter){
		String[] strings = str.split(Pattern.quote(delimiter));
		boolean[] blns = new boolean[strings.length];
	    for (int i = 0; i < strings.length; i++) {
	        blns[i] = Boolean.parseBoolean(strings[i].trim());
	    }
		return blns;
	}

	public static int[] stringToIntegerArray(String str, String delimiter){
		String[] strings = str.split(Pattern.quote(delimiter));
		int[] ints = new int[strings.length];
	    for (int i = 0; i < strings.length; i++) {
	        ints[i] = Integer.parseInt(strings[i].trim());
	    }
		return ints;
	}

	public static double[] stringToDoubleArray(String str, String delimiter){
		String[] strings = str.split(Pattern.quote(delimiter));
		double[] dbls = new double[strings.length];
	    for (int i = 0; i < strings.length; i++) {
	        dbls[i] = Double.parseDouble(strings[i].trim());
	    }
		return dbls;
	}

	public static String[] stringToStringArray(String str, String delimiter){
		String[] strings = str.split(Pattern.quote(delimiter));
	    for (int i = 0; i < strings.length; i++) {
	    	strings[i] = strings[i].trim();
	    }
		return strings;
	}

	public static int[][] stringToIntegerArrayOfArrays(String str, String arrayDelimiter, String arraysDelimiter){
		String[] strings = str.split(Pattern.quote(arrayDelimiter));
		String[] frst_string = strings[0].split(Pattern.quote(arraysDelimiter));
		int[][] ints = new int[strings.length][frst_string.length];
	    for (int i = 0; i < strings.length; i++) {
	    	String[] string = strings[i].split(Pattern.quote(arraysDelimiter));
	    	for(int j = 0 ; j < frst_string.length; j++ ){
		        ints[i][j] = Integer.parseInt(string[j].trim());
	    	}
	    }
		return ints;
	}
	//-----------------------------------------------------------------------------

}
