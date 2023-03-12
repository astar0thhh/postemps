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
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ItemEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;

public class ForgetPassword {

	JFrame frmForgetPassword;
	private JLabel lblEmail, lblPassword;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JCheckBox chkShowPassword;
	private JButton btnRegister;
	private JLabel label;
	private JLabel lblBackground;
	private JPasswordField txtConfirmPassword;
	private JLabel lblConfirmPassword;
	private JLabel lblForgetPassword;
	
	static ArrayList<String[]> userList = new ArrayList<>();
	private JLabel lblCapsLock;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgetPassword window = new ForgetPassword();
					window.frmForgetPassword.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ForgetPassword() {
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
		frmForgetPassword = new JFrame();
		frmForgetPassword.setBounds(100, 100, 450, 300);
		frmForgetPassword.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmForgetPassword.setResizable(false);
	    frmForgetPassword.setBackground(Color.WHITE);
	    frmForgetPassword.setTitle("Login");
	    frmForgetPassword.setSize(1087, 715);
	    frmForgetPassword.setLocationRelativeTo(null);
	    
		JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);

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

        btnRegister = new JButton("Confirm Changes");
        btnRegister.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(txtConfirmPassword.getText().isBlank() || txtEmail.getText().isBlank() || txtPassword.getText().isEmpty())
        			JOptionPane.showMessageDialog(frmForgetPassword, "Please fill in the necessary information", "Empty Fields Detected", JOptionPane.ERROR_MESSAGE);
        		else if(!(txtPassword.getText().equals(txtConfirmPassword.getText())))
               		JOptionPane.showMessageDialog(frmForgetPassword, "Passwords do not match", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
               	else {
               		boolean isExisting = false;
    				for(String[] user : userList) {
    					if(txtEmail.getText().equals(user[0])) {
    						isExisting = true;
    						user[1] = txtPassword.getText();
    					}
    				}
    				if(!isExisting) {
    					JOptionPane.showMessageDialog(frmForgetPassword, "Username does not Exist", "No Username Found", JOptionPane.ERROR_MESSAGE);
    					txtEmail.grabFocus();
    				}else {
    					BufferedWriter updateUsers;
    					try {
    						updateUsers = new BufferedWriter(new FileWriter("UserCredentials.txt"));
    						for(String[] user : userList) {
    							updateUsers.write(user[0] + "\t" + user[1]);
    							if(user.equals(userList.get(userList.size()-1)))
    								break;
    							else
    								updateUsers.newLine();
    						}
    						updateUsers.close();
    						JOptionPane.showMessageDialog(frmForgetPassword, "Password Modified Successfully!", "Succesful Password Modification", JOptionPane.INFORMATION_MESSAGE);
    						LoginWindow loginWindow = new LoginWindow();
    						loginWindow.frmLoginWindow.setVisible(true);
    						frmForgetPassword.dispose();
    					} catch (IOException e1) {
    						// TODO Auto-generated catch block
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

        frmForgetPassword.setContentPane(panel);
        
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
        
        lblForgetPassword = new JLabel("Forget Password");
        lblForgetPassword.setForeground(new Color(255, 255, 255));
        lblForgetPassword.setFont(new Font("Bebas Neue Bold", Font.PLAIN, 30));
        lblForgetPassword.setBounds(359, 194, 179, 31);
        panel.add(lblForgetPassword);
        
        lblCapsLock = new JLabel("");
        lblCapsLock.setForeground(new Color(255, 0, 0));
        lblCapsLock.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblCapsLock.setBounds(359, 431, 151, 23);
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
