package tn.esprit.powerHR.utils;

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
    private static Connection cnx;
    
    private final String URL = "jdbc:mysql://127.0.0.1:3306/maha2";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private MyDataBase() {
        try {
            // Establish connection
            cnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to Database Successfully!");
        } catch (SQLException e) {
            System.err.println("Database Connection Error: " + e.getMessage());
        }
    }

    public static MyDataBase getInstance() {
        if(instance == null) {
            instance = new MyDataBase();
        }
        return instance;
    }

    public Connection getcnx() {
        return cnx;
    }

    public static void closeConnection() {
        if (instance != null && cnx != null) {
            try {
                cnx.close();
                cnx = null;
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
            Statement st = cnx.createStatement();
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