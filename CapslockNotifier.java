import javax.swing.*;
import java.awt.event.*;

public class CapsLockNotifier extends JFrame implements KeyListener {

    public CapsLockNotifier() {
        setTitle("Caps Lock Notifier");
        setSize(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField textField = new JTextField(20);
        textField.addKeyListener(this);
        add(textField);

        setVisible(true);
    }

    public static void main(String[] args) {
        new CapsLockNotifier();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {
            JOptionPane.showMessageDialog(this, "Caps Lock is on!", "Caps Lock Notifier", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {
            JOptionPane.showMessageDialog(this, "Caps Lock is off.", "Caps Lock Notifier", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}
