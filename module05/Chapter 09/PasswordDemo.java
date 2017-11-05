/**
 * Class PasswordDemo used to test class Password
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static java.awt.BorderLayout.*;				
import static javax.swing.JOptionPane.*;			

public class PasswordDemo
{
    public static void main(String[] argv)
    {
		int width = 400;
		int height = 130;
        final demoFrame f = new demoFrame("PasswordDemo");

        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	f.setSize(width, height);
    	f.centerOnScreen(width, height);
    	f.setVisible(true);
    }
}

