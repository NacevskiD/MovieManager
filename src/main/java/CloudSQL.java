import java.sql.*;
import java.util.ArrayList;

public class CloudSQL {
    String databaseName = "Logins";
    String instanceConnectionName = "moviemanager-188521:us-central1:moviedatabase";
    String username = "user";
    String password = "password";
    String jdbcUrl = String.format(
            "jdbc:mysql://google/%s?cloudSqlInstance=%s&"
                    + "socketFactory=com.google.cloud.sql.mysql.SocketFactory",
            databaseName,
            instanceConnectionName);


    CloudSQL() throws SQLException{


        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);



    }


    void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS Users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email TEXT NOT NULL,"
                + "firstName TEXT NOT NULL,"
                + "lastName TEXT NOT NULL,"
                + "userName TEXT NOT NULL,"
                + "password TEXT NOT NULL)";

        connectAndExecute(sql);
    }

    void createToWatchTable(String user){
        String baseSql = "CREATE TABLE IF NOT EXISTS %s ("
                + "id INT PRIMARY KEY,"
                + "Title TEXT NOT NULL,"
                + "Year TEXT NOT NULL,"
                + "Adult BOOLEAN,"
                + "Picture TEXT NOT NULL,"
                + "Rating DOUBLE)";

        String sql = String.format(baseSql,user);
        connectAndExecute(sql);
    }

    void addMovieToDB(String user,int id, String title, String year, boolean adult, String image, double rating){
        String baseInsertSQL = "INSERT INTO %s (id, Title, Year, Adult,Picture, Rating)" +
                "VALUES (?,?,?,?,?,?)";

        String insertSQL = String.format(baseInsertSQL,user);

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, this.password)){
            System.out.println("Database connected!");
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,title);
            preparedStatement.setString(3,year);
            preparedStatement.setBoolean(4,adult);
            preparedStatement.setString(5,image);
            preparedStatement.setDouble(6,rating);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    ArrayList<Movie> selectAllToWatch(String user){
        String baseSql = "SELECT * FROM %s";
        String sql = String.format(baseSql,user);
        ArrayList<Movie> resuts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                System.out.println( rs.getInt("id"));
                System.out.println(rs.getString("Title"));
                System.out.println(rs.getString("Year"));
                System.out.println(rs.getBoolean("Adult"));
                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("Title"));
                movie.setDate(rs.getString("Year"));
                movie.setAdult(rs.getBoolean("Adult"));
                movie.setPictureURL(rs.getString("Picture"));
                movie.setUserRating(rs.getDouble("Rating"));
                resuts.add(movie);
            }
            rs.close();


        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return resuts;
    }


    void dropTable() {
        String sql = "DROP TABLE Users";

        connectAndExecute(sql);
    }

    void dropTableMovies(String user) {
        String baseSql = "DROP TABLE %s";
        String sql = String.format(baseSql,user);

        connectAndExecute(sql);
    }


    void addUser(String email, String firstName, String lastName, String userName, String password){
        String insertSQL = "INSERT INTO Users (email, firstName, lastName, userName, password)" +
                "VALUES (?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, this.password)){
            System.out.println("Database connected!");
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,firstName);
            preparedStatement.setString(3,lastName);
            preparedStatement.setString(4,userName);
            preparedStatement.setString(5,password);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    }

    void selectAll(){
        String sql = "SELECT * FROM Users";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            ArrayList<User> resuts = new ArrayList();
            while (rs.next()) {
                System.out.println( rs.getInt("id"));
                System.out.println(rs.getString("firstName"));
                System.out.println(rs.getString("lastName"));
                System.out.println(rs.getString("userName"));
                System.out.println(rs.getString("password"));
                System.out.println(rs.getString("email"));

            }
            rs.close();


        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    boolean checkEmail(String email){
        String sql = "SELECT * FROM Users";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                if (rs.getString("email").equalsIgnoreCase(email)){
                    return true;
                }
            }
            rs.close();
            return false;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return true;

        }
    }

    boolean checkUsername(String username){
        String sql = "SELECT * FROM Users";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, this.username, password);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                if (rs.getString("userName").equalsIgnoreCase(username)){
                    return true;
                }
            }
            rs.close();
            return false;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return true;

        }
    }

    boolean checkLogin(String user,String pass){
        String sql = "SELECT userName,password FROM Users";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, this.username, password);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                System.out.println(rs.getString("userName"));
                System.out.println(rs.getString("password"));
                if (rs.getString("userName").equalsIgnoreCase(user) && rs.getString("password").equals(pass)){
                    return true;
                }
            }
            rs.close();
            return false;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return true;

        }
    }




    private void connectAndExecute(String sql){
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)){
            System.out.println("Database connected!");
            Statement s = connection.createStatement();
            s.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
