import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JFormattedTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
class Student{
	// declaration of Student attributes
	private String idNumber;
	private String firstName;
	private String lastName;
	private String studentType;
	private String courseName;
	
	final double[][] feesPerCourse = { // initialization of course fees
			{10000.00, 22500.00}, 
			{20000.00, 11000.00}, 
			{30000.00, 25000.00}, 
			{15000.00, 15000.00}, 
			{25000.00, 23000.00}
			};
	
	Student(String idNumber, String firstName, String lastName, String studentType, String courseName) { // parametized constructor
		this.idNumber = idNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentType = studentType;
		this.courseName = courseName;
	}
	
	public Student() { // implicit constructor
		
	}
	
	double getDiscount() { // returns discount of student based on student type
		double discount = 0;
		switch (studentType) {
			case "GOLD":
				discount = 0.5;
				break;
			case "BLUE":
				discount = 0.3;
				break;
			case "WHITE":
			case "CONTINUING":
				discount = 0.1;
				break;
			case "WALK-IN":
				return discount;
		}
		return discount;
	}
	
	String feesBreakdown(double tuition, double misc, double percent) { // returns fees breakdown
		double computedTuition = 0;
		if(studentType == "CONTINUING")
			computedTuition = tuition * (1 - percent) + misc;
		else
			computedTuition = (tuition + misc) * (1 - percent);
		return "ID Number: " + idNumber + 
				"\nFirst Name: " + firstName + 
				"\nLast Name: " + lastName + 
				"\nStudent Type: Discounted (" + studentType + ")" +
				"\nCourse Name: " + courseName + 
				"\n\nTuition Fee (Computation) : Php " + String.format("%.2f", computedTuition);
	}
}

public class DiscountSystem1 extends Student{

	JFrame frmDiscountSystem;
	private JTextField txtIDNumber;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtTuition;
	private JTextField txtMiscellaneous;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DiscountSystem1 window = new DiscountSystem1();
					window.frmDiscountSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DiscountSystem1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDiscountSystem = new JFrame();
		frmDiscountSystem.setResizable(false);
		frmDiscountSystem.getContentPane().setBackground(new Color(0, 0, 0));
		frmDiscountSystem.setIconImage(Toolkit.getDefaultToolkit().getImage("UniversityIcon.png"));
		frmDiscountSystem.setTitle("Honorato National School - Student Enrolment");
		frmDiscountSystem.setBounds(100, 100, 800, 600);
		frmDiscountSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDiscountSystem.getContentPane().setLayout(null);
		
		JLabel lblHeader = new JLabel("HONORATO NATIONAL SCHOOL");
		lblHeader.setBounds(95, 1, 594, 178);
		lblHeader.setBackground(new Color(0, 0, 0));
		lblHeader.setForeground(new Color(255, 0, 0));
		lblHeader.setIcon(new ImageIcon("UniversityIcon.png"));
		lblHeader.setFont(new Font("Bebas Neue", Font.BOLD, 42));
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeader.setHorizontalTextPosition(SwingConstants.CENTER);
		lblHeader.setVerticalTextPosition(SwingConstants.BOTTOM);
		frmDiscountSystem.getContentPane().add(lblHeader);
		
		JPanel pnlUserInformation = new JPanel();
		pnlUserInformation.setBounds(133, 190, 491, 350);
		pnlUserInformation.setBackground(new Color(128, 128, 128));
		frmDiscountSystem.getContentPane().add(pnlUserInformation);
		pnlUserInformation.setLayout(null);
		
		JLabel lblIdNumber = new JLabel("ID Number");
		lblIdNumber.setForeground(new Color(255, 255, 255));
		lblIdNumber.setBounds(10, 11, 122, 30);
		lblIdNumber.setFont(new Font("Consolas", Font.BOLD, 18));
		lblIdNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		txtIDNumber = new JTextField();
		txtIDNumber.setBackground(new Color(255, 255, 255));
		txtIDNumber.setBounds(142, 11, 339, 30);
		txtIDNumber.setFont(new Font("Consolas", Font.PLAIN, 18));
		txtIDNumber.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setForeground(new Color(255, 255, 255));
		lblFirstName.setBounds(10, 43, 122, 30);
		lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstName.setFont(new Font("Consolas", Font.BOLD, 18));
		
		
		txtFirstName = new JTextField();
		txtFirstName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!(Character.isAlphabetic(e.getKeyChar()) || e.getKeyChar() == KeyEvent.VK_SPACE)) {
						e.consume();
				}
			}
		});
		txtFirstName.setBackground(new Color(255, 255, 255));
		txtFirstName.setBounds(142, 43, 339, 30);
		txtFirstName.setFont(new Font("Consolas", Font.PLAIN, 18));
		txtFirstName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(new Color(255, 255, 255));
		lblLastName.setBounds(10, 75, 122, 30);
		lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLastName.setFont(new Font("Consolas", Font.BOLD, 18));
		
		
		txtLastName = new JTextField();
		txtLastName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!(Character.isAlphabetic(e.getKeyChar()) || e.getKeyChar() == KeyEvent.VK_SPACE)) {
					e.consume();
			}
			}
		});
		txtLastName.setBackground(new Color(255, 255, 255));
		txtLastName.setBounds(142, 75, 339, 30);
		txtLastName.setFont(new Font("Consolas", Font.PLAIN, 18));
		txtLastName.setColumns(10);
		
		JLabel lblStudentType = new JLabel("Student Type");
		lblStudentType.setForeground(new Color(255, 255, 255));
		lblStudentType.setBounds(10, 107, 122, 30);
		lblStudentType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStudentType.setFont(new Font("Consolas", Font.BOLD, 18));
		
		
		JComboBox cmbStudentType = new JComboBox();
		cmbStudentType.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
				popupMenuWillBecomeInvisible(e);	
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				switch(cmbStudentType.getSelectedIndex()) {
					case 0:
					case 3:
					case 4:
					case 5:
						cmbStudentType.setBackground(new Color(255, 255, 255));
						break;
					case 1:
						cmbStudentType.setBackground(new Color(255, 223, 0));
						cmbStudentType.setForeground(new Color(0, 0, 128));
						break;
					case 2:
						cmbStudentType.setBackground(new Color(0, 0, 128));
						cmbStudentType.setForeground(Color.WHITE);
						break;
				}
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				cmbStudentType.setBackground(new Color(255, 255, 255));
				cmbStudentType.setForeground(Color.BLACK);
			}
		});
		cmbStudentType.setBackground(new Color(255, 255, 255));
		
		
		cmbStudentType.setBounds(142, 107, 339, 30);
		cmbStudentType.setFont(new Font("Consolas", Font.PLAIN, 18));
		cmbStudentType.setModel(new DefaultComboBoxModel(new String[] {"- Select Discount Type -", "GOLD", "BLUE", "WHITE", "WALK-IN", "CONTINUING"}));
		cmbStudentType.setSelectedIndex(0);
		cmbStudentType.setMaximumRowCount(6);
		
		
		JLabel lblCourseName = new JLabel("Course Name");
		lblCourseName.setForeground(new Color(255, 255, 255));
		lblCourseName.setBounds(10, 139, 122, 30);
		lblCourseName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCourseName.setFont(new Font("Consolas", Font.BOLD, 18));
		
		
		JComboBox cmbCourseName = new JComboBox();
		cmbCourseName.setToolTipText("");
		cmbCourseName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cmbCourseName.getSelectedIndex() > 0) {
					txtTuition.setText(String.format("%.2f",feesPerCourse[cmbCourseName.getSelectedIndex()-1][0]));
					txtMiscellaneous.setText(String.format("%.2f",feesPerCourse[cmbCourseName.getSelectedIndex()-1][1]));
				}
			}
		});
		cmbCourseName.setBounds(142, 139, 339, 30);
		cmbCourseName.setFont(new Font("Consolas", Font.PLAIN, 18));
		cmbCourseName.setModel(new DefaultComboBoxModel(new String[] {"- Select Course -", "TOURISM", "COMPUTER SCIENCE", "ENGINEERING", "NURSING", "ARCHITECTURE"}));
		cmbCourseName.setSelectedIndex(0);
		
		
		JButton btnCompute = new JButton("Compute");
		btnCompute.setBackground(new Color(255, 0, 0));
		btnCompute.setForeground(new Color(255, 255, 255));
		btnCompute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cmbStudentType.getSelectedIndex() == 0 || cmbCourseName.getSelectedIndex() == 0)
					JOptionPane.showMessageDialog(frmDiscountSystem, "Please complete the selections", "Course/Student Type not selected!",JOptionPane.ERROR_MESSAGE);
				if(txtFirstName.getText().isBlank()|| txtIDNumber.getText().isBlank() || txtLastName.getText().trim().isBlank())
					JOptionPane.showMessageDialog(frmDiscountSystem, "Please fill in the necessary information", "Empty fields detected!", JOptionPane.ERROR_MESSAGE);
				else if(cmbStudentType.getSelectedIndex() != 0 && cmbCourseName.getSelectedIndex() != 0){
					Student enrollee = new Student(txtIDNumber.getText(), txtFirstName.getText(), txtLastName.getText(), cmbStudentType.getSelectedItem().toString(), cmbCourseName.getSelectedItem().toString());
					double tuition = Double.parseDouble(txtTuition.getText());
					double misc = Double.parseDouble(txtMiscellaneous.getText());
					double discount = enrollee.getDiscount();
					
					JOptionPane.showMessageDialog(frmDiscountSystem, enrollee.feesBreakdown(tuition, misc, discount), "Summary", JOptionPane.INFORMATION_MESSAGE);
					
				}
			}
		});
		btnCompute.setFont(new Font("Segoe UI Black", Font.PLAIN, 18));
		btnCompute.setBounds(85, 291, 150, 30);
		
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBackground(new Color(255, 0, 0));
		btnReset.setForeground(new Color(255, 255, 255));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtIDNumber.setText(null);
				txtFirstName.setText(null);
				txtLastName.setText(null);
				txtMiscellaneous.setText(null);
				txtTuition.setText(null);
				cmbStudentType.setSelectedIndex(0);
				cmbStudentType.setBackground(new Color(255,255,255));
				cmbStudentType.setForeground(Color.BLACK);
				cmbCourseName.setSelectedIndex(0);
				txtIDNumber.grabFocus();
			}
		});
		btnReset.setFont(new Font("Segoe UI Black", Font.PLAIN, 18));
		btnReset.setBounds(255, 291, 150, 30);
		
		
		JLabel lblTuitionFee = new JLabel("Tuition Fee");
		lblTuitionFee.setForeground(new Color(255, 255, 255));
		lblTuitionFee.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTuitionFee.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 18));
		lblTuitionFee.setBounds(85, 186, 122, 30);
		
		
		txtTuition = new JTextField();
		txtTuition.setEditable(false);
		txtTuition.setFont(new Font("Consolas", Font.PLAIN, 18));
		txtTuition.setColumns(10);
		txtTuition.setBounds(217, 186, 204, 30);
		
		
		JLabel lblMiscellaneous = new JLabel("Miscellaneous");
		lblMiscellaneous.setForeground(new Color(255, 255, 255));
		lblMiscellaneous.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMiscellaneous.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 18));
		lblMiscellaneous.setBounds(70, 218, 137, 30);
		
		txtMiscellaneous = new JTextField();
		txtMiscellaneous.setEditable(false);
		txtMiscellaneous.setFont(new Font("Consolas", Font.PLAIN, 18));
		txtMiscellaneous.setColumns(10);
		txtMiscellaneous.setBounds(217, 218, 204, 30);
		
		pnlUserInformation.add(lblIdNumber);
		pnlUserInformation.add(lblFirstName);
		pnlUserInformation.add(lblLastName);
		pnlUserInformation.add(lblStudentType);
		pnlUserInformation.add(lblCourseName);
		pnlUserInformation.add(lblTuitionFee);
		pnlUserInformation.add(lblMiscellaneous);
		
		pnlUserInformation.add(cmbStudentType);
		pnlUserInformation.add(cmbCourseName);
		
		pnlUserInformation.add(txtIDNumber);
		pnlUserInformation.add(txtFirstName);
		pnlUserInformation.add(txtLastName);
		pnlUserInformation.add(txtTuition);
		pnlUserInformation.add(txtMiscellaneous);
		
		pnlUserInformation.add(btnCompute);
		pnlUserInformation.add(btnReset);
		
		JButton btnLogout = new JButton("LOGOUT");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginWindow loginWindow = new LoginWindow();
				loginWindow.frmLoginWindow.setVisible(true);
				frmDiscountSystem.dispose();
			}
		});
		btnLogout.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnLogout.setBounds(10, 11, 89, 23);
		frmDiscountSystem.getContentPane().add(btnLogout);

		
	}
}
