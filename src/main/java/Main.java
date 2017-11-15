import java.sql.ResultSet;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        System.out.println("Hello World!");
        Database db = new Database();
        db.createTable();
        db.addUser("David","Nacevski","nacevskiD","password");
        ArrayList<User> results = db.selectAll();
        for(User user:results){
            System.out.println(user.getId());
            System.out.println(user.getFirstName());
            System.out.println(user.getLastName());
            System.out.println(user.getUserName());
            System.out.println(user.getPassword());
        }
    }
}
