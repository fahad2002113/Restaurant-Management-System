import Classes.LoginFrame;
import javax.swing.*;



public class Start {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame uLog = new LoginFrame();
            uLog.setVisible(true);
            });
        };
    }

