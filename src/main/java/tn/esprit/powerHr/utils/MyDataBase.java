package tn.esprit.powerHr.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MyDataBase {
    private static MyDataBase instance;
    private static Connection connection;
    
    private final String URL = "jdbc:mysql://127.0.0.1:3306/maha2";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private MyDataBase() {
        try {
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

    public static MyDataBase getInstance() {
        if(instance == null) {
            instance = new MyDataBase();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        if (instance != null && connection != null) {
            try {
                connection.close();
                connection = null;
                instance = null;
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection!");
                e.printStackTrace();
            }
        }
    }

    public <T> Stream<T> executeQuery(String query, ResultSetMapper<T> mapper) {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            // Create a Spliterator for the ResultSet
            Spliterator<T> spliterator = new Spliterators.AbstractSpliterator<T>(
                Long.MAX_VALUE, 
                Spliterator.ORDERED
            ) {
                @Override
                public boolean tryAdvance(Consumer<? super T> action) {
                    try {
                        if (!rs.next()) {
                            rs.close();
                            st.close();
                            return false;
                        }
                        action.accept(mapper.map(rs));
                        return true;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            
            return StreamSupport.stream(spliterator, false)
                .onClose(() -> {
                    try {
                        rs.close();
                        st.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }
}