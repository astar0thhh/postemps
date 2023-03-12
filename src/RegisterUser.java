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
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;

public class RegisterUser {

	JFrame frmRegister;
	private JLabel lblEmail, lblPassword;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JCheckBox chkShowPassword;
	private JButton btnRegister;
	private JLabel label;
	private JLabel lblBackground;
	private JPasswordField txtConfirmPassword;
	private JLabel lblConfirmPassword;
	private JLabel lblSignUp;
	
	static ArrayList<String[]> userList = new ArrayList<>();
	String emailValidate = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
	private JLabel lblCapsLock;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterUser window = new RegisterUser();
					window.frmRegister.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegisterUser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			BufferedReader readUsers = new BufferedReader(new FileReader("UserCredentials.txt"));
			String currentLine;
			while((currentLine = readUsers.readLine()) != null) {
				userList.add(currentLine.split("\t"));
			}
			readUsers.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		frmRegister = new JFrame();
		frmRegister.setBounds(100, 100, 450, 300);
		frmRegister.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRegister.setResizable(false);
	    frmRegister.setBackground(Color.WHITE);
	    frmRegister.setTitle("Login");
	    frmRegister.setSize(1087, 715);
	    frmRegister.setLocationRelativeTo(null);
	    
		JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);

        lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblEmail.setForeground(Color.RED);
        lblEmail.setBackground(Color.WHITE);
        lblEmail.setBounds(359, 236, 357, 30);
        panel.add(lblEmail);
        
        txtEmail = new JTextField();
        txtEmail.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent e) {
        		if(!(txtEmail.getText().matches(emailValidate))) {
               		JOptionPane.showMessageDialog(frmRegister, "Invalid Email!", "Invalid Email", JOptionPane.ERROR_MESSAGE);
               		txtPassword.setEditable(false);
               		txtConfirmPassword.setEditable(false);
               		txtPassword.setBackground(Color.gray);
               		txtConfirmPassword.setBackground(Color.gray);
               		txtEmail.grabFocus();
               	}else {
               		txtPassword.setEditable(true);
               		txtConfirmPassword.setEditable(true);
               		txtPassword.setBackground(Color.white);
               		txtConfirmPassword.setBackground(Color.white);
               	}
        	}
        });
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
        chkShowPassword.setForeground(new Color(255, 255, 255));
        chkShowPassword.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent e) {
        		if(chkShowPassword.isSelected()) {
					txtPassword.setEchoChar((char) 0);
					txtPassword.setText(String.valueOf(txtPassword.getPassword()));
					txtConfirmPassword.setEchoChar((char) 0);
					txtConfirmPassword.setText(String.valueOf(txtConfirmPassword.getPassword()));
				}else {
					txtPassword.setEchoChar((char) '●');
        			txtConfirmPassword.setEchoChar((char) '●');
				}
        	}
        });
        chkShowPassword.setFont(new Font("SansSerif", Font.PLAIN, 12));
        chkShowPassword.setBackground(Color.RED);
        chkShowPassword.setBounds(596, 431, 120, 30);
        panel.add(chkShowPassword);

        btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(txtConfirmPassword.getText().isBlank() || txtEmail.getText().isBlank() || txtPassword.getText().isEmpty())
        			JOptionPane.showMessageDialog(frmRegister, "Please fill in the necessary information", "Empty Fields Detected", JOptionPane.ERROR_MESSAGE);
        		else if(!(txtPassword.getText().equals(txtConfirmPassword.getText())))
               		JOptionPane.showMessageDialog(frmRegister, "Passwords do not match", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
               	else {
               		boolean isExisting = false;
               		for(String[] user : userList) {
    					if(txtEmail.getText().equals(user[0])) {
    						isExisting = true;
    					}
    				}
               		if(isExisting) {
               			JOptionPane.showMessageDialog(frmRegister, "Email already exists", "Invalid email", JOptionPane.ERROR_MESSAGE);
               			txtEmail.grabFocus();
               		}else {
	               		try {
	    					BufferedWriter updateUsers = new BufferedWriter(new FileWriter("UserCredentials.txt", true));
	    					updateUsers.newLine();
	    					updateUsers.write(txtEmail.getText() + "\t" + txtPassword.getText());
	    					updateUsers.close();
	    					JOptionPane.showMessageDialog(frmRegister, "Registered Successfully!", "Succesful Registration", JOptionPane.INFORMATION_MESSAGE);
	    					LoginWindow loginWindow = new LoginWindow();
	    					loginWindow.frmLoginWindow.setVisible(true);
	    					frmRegister.dispose();
	    				} catch (IOException e1) {
	    					e1.printStackTrace();
	    				}
               		}
               	}
               	
        	}
        });
        btnRegister.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnRegister.setBackground(Color.RED);
        btnRegister.setBounds(359, 482, 357, 30);
        panel.add(btnRegister);

        label = new JLabel("");
        label.setBounds(387, 286, 300, 30);
        panel.add(label);

        frmRegister.setContentPane(panel);
        
        JLabel lblLogo = new JLabel("New label");
        lblLogo.setIcon(new ImageIcon("src/logo.png"));
        lblLogo.setBounds(372, 65, 293, 97);
        panel.add(lblLogo);
        
        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		checkCaps();
        	}
        });
        txtConfirmPassword.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		checkCaps();
        	}
        });
        txtConfirmPassword.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtConfirmPassword.setBounds(359, 366, 357, 30);
        panel.add(txtConfirmPassword);
        
        lblConfirmPassword = new JLabel("Confirm Password:");
        lblConfirmPassword.setForeground(Color.RED);
        lblConfirmPassword.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblConfirmPassword.setBounds(359, 340, 357, 30);
        panel.add(lblConfirmPassword);
        
        lblSignUp = new JLabel("Sign Up");
        lblSignUp.setForeground(new Color(255, 255, 255));
        lblSignUp.setFont(new Font("Bebas Neue Bold", Font.PLAIN, 30));
        lblSignUp.setBounds(359, 194, 89, 31);
        panel.add(lblSignUp);
        
        lblCapsLock = new JLabel("");
        lblCapsLock.setForeground(new Color(255, 0, 0));
        lblCapsLock.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblCapsLock.setBounds(372, 431, 156, 23);
        panel.add(lblCapsLock);
        
        lblBackground = new JLabel("New label");
        lblBackground.setForeground(Color.RED);
        lblBackground.setIcon(new ImageIcon("src/background.png"));
        lblBackground.setBounds(-426, -35, 1551, 797);
        panel.add(lblBackground);
        lblBackground.setVisible(true);
	}
	public void checkCaps() {
		if(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK))
			lblCapsLock.setText("Caps Lock enabled");
		else
			lblCapsLock.setText(null);
	}
}
