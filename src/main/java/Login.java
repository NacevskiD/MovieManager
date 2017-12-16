import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login extends JFrame{
    private JPanel rootPanel;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel labelPanel;
    private JPanel filedPannel;
    private JPanel buttonPannel;
    private CloudSQL cloudSQL;

    Login(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        setVisible(true);
        setSize(new Dimension(250,200));
        setTitle("Login");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register();
                dispose();

            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    cloudSQL = new CloudSQL();
                }catch (SQLException sqle){
                    JOptionPane.showMessageDialog(null,"Could not connect to database.","Error",JOptionPane.ERROR_MESSAGE);
                }
                String pwd = new String(passwordField.getPassword());
                if (cloudSQL.checkLogin(userNameField.getText(),pwd)){
                    System.out.println("LOGGED IN ");
                    new MovieGUI(userNameField.getText());
                    dispose();
                }else {
                    System.out.println(userNameField.getText() + "123");
                    System.out.println(pwd + "123");


                }
            }
        });
    }
}
