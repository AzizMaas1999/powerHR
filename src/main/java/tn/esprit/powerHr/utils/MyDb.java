
package tn.esprit.powerHR.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

<<<<<<< HEAD:src/main/java/tn/esprit/powerHr/utils/MyDataBase.java
public class MyDataBase {

    private Connection cnx;

    private static MyDataBase instance;

    private final String URL = "jdbc:mysql://localhost:3306/powerhr";
=======
public class MyDb {
    private static MyDb instance;
    private static Connection connection;
    
    private final String URL = "jdbc:mysql://127.0.0.1:3306/maha2";
>>>>>>> maha3:src/main/java/tn/esprit/powerHr/utils/MyDb.java
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private MyDb() {
        try {
<<<<<<< HEAD:src/main/java/tn/esprit/powerHr/utils/MyDataBase.java
            cnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connecting !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static MyDataBase getInstance() {
        if(instance == null)
            instance = new MyDataBase();
=======
            // Establish connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to Database Successfully!");
            
            // Update schema if needed
            updateSchema();
        } catch (SQLException e) {
            System.err.println("Database Connection Error: " + e.getMessage());
        }
    }

    private void updateSchema() {
        try {
            Statement statement = connection.createStatement();
            
            // Drop email columns
            try {
                statement.executeUpdate("ALTER TABLE entreprise DROP COLUMN email");
            } catch (SQLException e) {
                // Column might not exist, ignore
            }
            try {
                statement.executeUpdate("ALTER TABLE entreprise DROP COLUMN email_verified");
            } catch (SQLException e) {
                // Column might not exist, ignore
            }

            // Add phone columns if they don't exist
            try {
                statement.executeUpdate("ALTER TABLE entreprise ADD COLUMN IF NOT EXISTS phone_number VARCHAR(20)");
            } catch (SQLException e) {
                // Column might already exist, ignore
            }
            try {
                statement.executeUpdate("ALTER TABLE entreprise ADD COLUMN IF NOT EXISTS phone_verified BOOLEAN DEFAULT FALSE");
            } catch (SQLException e) {
                // Column might already exist, ignore
            }

            System.out.println("Database schema updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating schema: " + e.getMessage());
        }
    }

    public static MyDb getInstance() {
        if(instance == null) {
            instance = new MyDb();
        }
>>>>>>> maha3:src/main/java/tn/esprit/powerHr/utils/MyDb.java
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}