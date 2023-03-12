import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class LoginWindow {

	JFrame frmLoginWindow;
	private JLabel lblEmail, lblPassword;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JCheckBox chkShowPassword;
	private JButton btnLogin, btnRegister, btnForgotPassword;
	private JLabel lblBackground;
	private JLabel lblNewLabel_2;
	private JLabel lblSignIn;
	static ArrayList<String[]> userList = new ArrayList<>();
	private JLabel lblCapsLock;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frmLoginWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		String[] predefinedUser = {"admin@gmail.com", "password"};
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			userList.clear();
			BufferedWriter writePredefinedUser = new BufferedWriter(new FileWriter("UserCredentials.txt", true));
			BufferedReader readUsers = new BufferedReader(new FileReader("UserCredentials.txt"));
			String currentLine;
			if((currentLine = readUsers.readLine()) == null) {
				writePredefinedUser.write("admin@gmail.com\tpassword"); //predefined user credentials
				writePredefinedUser.close();
			}else {
				userList.add(currentLine.split("\t"));
			}
			while((currentLine = readUsers.readLine()) != null) {
				userList.add(currentLine.split("\t"));
			}
			readUsers.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		frmLoginWindow = new JFrame();
		frmLoginWindow.setBounds(100, 100, 450, 300);
		frmLoginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLoginWindow.setResizable(false);
	    frmLoginWindow.setBackground(Color.WHITE);
	    frmLoginWindow.setTitle("Login");
	    frmLoginWindow.setSize(1087, 715);
	    frmLoginWindow.setLocationRelativeTo(null);
	    
		JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);
        
        lblNewLabel_2 = new JLabel("Not a member yet?");
        lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblNewLabel_2.setForeground(Color.RED);
        lblNewLabel_2.setBounds(423, 498, 110, 20);
        panel.add(lblNewLabel_2);

        lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblEmail.setForeground(Color.RED);
        lblEmail.setBackground(Color.WHITE);
        lblEmail.setBounds(359, 236, 357, 30);
        panel.add(lblEmail);
        
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtEmail.setBounds(359, 258, 357, 30);
        panel.add(txtEmail);

        lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblPassword.setForeground(Color.RED);
        lblPassword.setBounds(359, 286, 357, 30);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		checkCaps();
        	}
        });
        txtPassword.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		checkCaps();
        	}
        });
        txtPassword.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtPassword.setBounds(359, 312, 357, 30);
        panel.add(txtPassword);

        chkShowPassword = new JCheckBox("Show Password");
        chkShowPassword.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent e) {
        		if(chkShowPassword.isSelected()) {
					txtPassword.setEchoChar((char) 0);
					txtPassword.setText(String.valueOf(txtPassword.getPassword()));
				}else {
					txtPassword.setEchoChar((char) '●');
				}
        	}
        });
        chkShowPassword.setForeground(new Color(255, 255, 255));
        chkShowPassword.setFont(new Font("SansSerif", Font.PLAIN, 12));
        chkShowPassword.setBackground(Color.RED);
        chkShowPassword.setBounds(596, 348, 120, 30);
        panel.add(chkShowPassword);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		boolean isExisting = false;
				for(String[] userCredentials: userList) {
					if(txtEmail.getText().equals(userCredentials[0])) {
						isExisting = true;
						if(txtPassword.getText().equals(userCredentials[1])) {
							
							DiscountSystem1 discount = new DiscountSystem1();
							discount.frmDiscountSystem.setVisible(true);
							frmLoginWindow.dispose();
							
						}
						else
							JOptionPane.showMessageDialog(frmLoginWindow, "Incorrect Password. Please try again.", "Login Unsuccessful", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				if(!isExisting) {
					JOptionPane.showMessageDialog(frmLoginWindow, "Email does not Exist!", "Invalid Email", JOptionPane.WARNING_MESSAGE);
					txtEmail.grabFocus();
				}
        	}
        });
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnLogin.setBackground(Color.RED);
        btnLogin.setBounds(359, 400, 357, 30);
        panel.add(btnLogin);

        btnRegister = new JButton("Register Here!");
        btnRegister.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		RegisterUser registerUser = new RegisterUser();
        		registerUser.frmRegister.setVisible(true);
        		frmLoginWindow.dispose();
        	}
        });
        btnRegister.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnRegister.setBackground(Color.RED);
        btnRegister.setBounds(543, 491, 173, 30);
        panel.add(btnRegister);
        
        btnForgotPassword = new JButton("Forgot Password?");
        btnForgotPassword.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ForgetPassword forgetPassword = new ForgetPassword();
        		forgetPassword.frmForgetPassword.setVisible(true);
        		frmLoginWindow.dispose();
        	}
        });
        btnForgotPassword.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnForgotPassword.setBackground(Color.RED);
        btnForgotPassword.setBounds(359, 440, 357, 30);
        panel.add(btnForgotPassword);

        frmLoginWindow.setContentPane(panel);
        
        JLabel lblLogo = new JLabel("New label");
        lblLogo.setIcon(new ImageIcon("src/logo.png"));
        lblLogo.setBounds(372, 65, 293, 97);
        panel.add(lblLogo);
        
        lblSignIn = new JLabel("Sign In");
        lblSignIn.setFont(new Font("Bebas Neue Bold", Font.PLAIN, 30));
        lblSignIn.setForeground(new Color(255, 255, 255));
        lblSignIn.setBounds(359, 194, 89, 31);
        panel.add(lblSignIn);
        
        lblCapsLock = new JLabel("");
        lblCapsLock.setForeground(new Color(255, 0, 0));
        lblCapsLock.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblCapsLock.setBounds(359, 357, 173, 21);
        panel.add(lblCapsLock);
        
        lblBackground = new JLabel("New label");
        lblBackground.setForeground(Color.RED);
        lblBackground.setIcon(new ImageIcon("src/background.png"));
        lblBackground.setBounds(-426, -35, 1551, 797);
        panel.add(lblBackground);
        lblBackground.setVisible(true);
	}
	public void checkCaps() { // method to check if CapsLock is Enabled
		if(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK))
			lblCapsLock.setText("Caps Lock enabled");
		else
			lblCapsLock.setText(null);
	}

}
