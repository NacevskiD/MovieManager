import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

public class Register extends JFrame{
    private JPanel rootPanel;
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JPanel labelPanel;
    private JPanel textFieldPanel;
    private JPanel buttonPanel;
    private JButton registerButton;
    private CloudSQL cloudSQL;


    Register() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(rootPanel);
        setVisible(true);
        setSize(new Dimension(300, 400));
        setTitle("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    cloudSQL = new CloudSQL();
                }catch (SQLException sqle){
                    JOptionPane.showMessageDialog(null,"Could not connect to database. Please Check your internet connection and try again.","Error",JOptionPane.ERROR_MESSAGE);
                }

                if (cloudSQL.checkEmail(emailField.getText())){
                    JOptionPane.showMessageDialog(null,"Email already exist!","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if (cloudSQL.checkUsername(usernameField.getText())){
                    JOptionPane.showMessageDialog(null,"Username already exist!","Error",JOptionPane.ERROR_MESSAGE);
                }
                else if (! Arrays.equals(passwordField.getPassword(),confirmPasswordField.getPassword())){
                    JOptionPane.showMessageDialog(null,"Password doesn't match!","Error",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String pwd = new String(passwordField.getPassword());
                    cloudSQL.addUser(emailField.getText(),firstNameField.getText(),lastNameField.getText(),usernameField.getText(),pwd);
                    JOptionPane.showMessageDialog(null,"User created successfully!");
                    dispose();
                    new Login();
                }





            }
        });
    }
}
