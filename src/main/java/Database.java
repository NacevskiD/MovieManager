import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Database {
    String DB = "jdbc:sqlite:Movie.db";
    private static final int DATABASE_VERSION = 2;


    Database() {
        try (Connection conn = DriverManager.getConnection(DB)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


        void createTable(){
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Users ("
                + "id integer PRIMARY KEY,"
                + "firstName TEXT NOT NULL,"
                + "lastName TEXT NOT NULL,"
                + "userName TEXT NOT NULL,"
                + "password TEXT NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(DB);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("Created a new table");

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    void dropTable() {
        String sql = "DROP TABLE";

        try (Connection conn = DriverManager.getConnection(DB);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    ArrayList selectAll(){
        String sql = "SELECT * FROM Users";
        try (Connection conn = DriverManager.getConnection(DB);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            ArrayList<User> resuts = new ArrayList();
            User user = new User();
            while (rs.next()) {
                user.setId( rs.getInt("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                resuts.add(user);
            }
            rs.close();

            return resuts;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            ArrayList results = new ArrayList();
            return results;
        }

    }
    void addUser(String firstName, String lastName, String userName, String password){
        String sql = "INSERT INTO Users(firstName,lastName,userName,password) VALUES(?,?,?,?);";

        try (Connection conn = DriverManager.getConnection(DB);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, userName);
            pstmt.setString(4, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}






